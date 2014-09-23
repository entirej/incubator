package org.entirej.ejinvoice.forms.customer;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.custom.renderers.WorkWeekBlockRenderer;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.constants.F_CUSTOMERS;
import org.entirej.ejinvoice.forms.constants.F_CUSTOMER_CONTACTS;
import org.entirej.ejinvoice.forms.constants.F_CUSTOMERS;
import org.entirej.ejinvoice.forms.constants.F_MASTER_DATA_SALUTATION;
import org.entirej.ejinvoice.forms.constants.F_PROJECTS;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY;
import org.entirej.ejinvoice.forms.timeentry.Customer;
import org.entirej.ejinvoice.forms.timeentry.CustomerBlockService;
import org.entirej.ejinvoice.forms.timeentry.FormHandler;
import org.entirej.ejinvoice.forms.timeentry.TimeEntry;
import org.entirej.ejinvoice.forms.timeentry.TimeEntryBlockService;
import org.entirej.ejinvoice.menu.MenuBlockService;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJParameterList;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.actionprocessor.EJDefaultFormActionProcessor;
import org.entirej.framework.core.actionprocessor.interfaces.EJFormActionProcessor;
import org.entirej.framework.core.data.controllers.EJFormParameter;
import org.entirej.framework.core.data.controllers.EJQuestion;
import org.entirej.framework.core.enumerations.EJQuestionButton;
import org.entirej.framework.core.enumerations.EJScreenType;
import org.entirej.framework.core.service.EJQueryCriteria;

public class CustomerActionProcessor extends EJDefaultFormActionProcessor implements EJFormActionProcessor
{
    private boolean      customerInserted                   = false;
    private boolean      customerUpdated                    = false;
    private Integer      customerId                         = null;

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_CUSTOMERS.AC_CREATE_NEW_CUSTOMER.equals(command))
        {
            form.getBlock(F_CUSTOMERS.B_CUSTOMERS.ID).getScreenItem(EJScreenType.INSERT, F_CUSTOMERS.B_CUSTOMERS.I_SALUTATIONS_ID).refreshItemRenderer();
            form.getBlock(F_CUSTOMERS.B_CUSTOMERS.ID).getScreenItem(EJScreenType.INSERT, F_CUSTOMERS.B_CUSTOMERS.I_CONTACT_TYPES_ID).refreshItemRenderer();
            form.getBlock(F_CUSTOMERS.B_CUSTOMERS.ID).enterInsert(false);
        }
        else if (F_CUSTOMERS.AC_EDIT_CUSTOMER.equals(command))
        {
            form.getBlock(F_CUSTOMERS.B_CUSTOMERS.ID).enterUpdate();
        }
        else if (F_CUSTOMERS.AC_DELETE_CUSTOMER.equals(command))
        {
            EJQuestion question = new EJQuestion(form, "ASK_DELETE_CUSTOMER");
            question.setTitle("Delete Customer");
            question.setMessage(new EJMessage("Are you sure you want to delete this customer?"));
            question.setButtonText(EJQuestionButton.ONE, "Yes");
            question.setButtonText(EJQuestionButton.TWO, "Cancel");
            question.setRecord(record);
            form.askQuestion(question);
        }
        else if (F_CUSTOMERS.AC_SHOW_CUSTOMER_DETAILS.equals(command))
        {
            Integer customerId = (Integer)record.getValue(F_CUSTOMERS.B_CUSTOMERS.I_ID);
            new FormHandler().synchronizeMenu(form.getForm(F_TIME_ENTRY.ID), MenuBlockService.CONTACT_PEOPLE);
            new FormHandler().openCustomerContacts(form, customerId);
            
        }
        else if (F_CUSTOMERS.AC_QUERY_CUSTOMERS.equals(command))
        {
            form.getBlock(F_CUSTOMERS.B_CUSTOMERS.ID).executeQuery();
        }
    }
    
    @Override
    public void postUpdate(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_CUSTOMERS.B_CUSTOMERS.ID.equals(record.getBlockName()))
        {
            customerUpdated = true;
        }
    }

    @Override
    public void postInsert(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_CUSTOMERS.B_CUSTOMERS.ID.equals(record.getBlockName()))
        {
            customerInserted = true;
            customerId = (Integer) record.getValue(F_CUSTOMERS.B_CUSTOMERS.I_ID);
        }

    }
    
    @Override
    public void questionAnswered(EJQuestion question) throws EJActionProcessorException
    {
        if (question.getName().equals("ASK_DELETE_CUSTOMER") && question.getAnswer().equals(EJQuestionButton.ONE))
        {
            boolean canBeDeleted = true;
            try
            {
                ServiceRetriever.getDBService(question.getForm()).validateDeleteRecordUsage(question.getForm(),
                        question.getForm().getBlock(F_MASTER_DATA_SALUTATION.B_SALUTATIONS.ID).getFocusedRecord(), "CUSTOMER");
            }
            catch (Exception e)
            {
                canBeDeleted = false;
            }

            if (canBeDeleted)
            {
                ArrayList<Customer> customers = new ArrayList<Customer>();
                customers.add((Customer) question.getRecord().getBlockServicePojo());
                new CustomerBlockService().executeDelete(question.getForm(), customers);

                question.getForm().getBlock(F_CUSTOMERS.B_CUSTOMERS.ID).executeQuery();
            }
            else
            {
                EJQuestion deactivateQuestion = new EJQuestion(question.getForm(), "ASK_DEACTIVATE_CUSTOMER");
                deactivateQuestion.setTitle("Deactivate Customer");
                deactivateQuestion.setMessage(new EJMessage(
                        "This customer has data depedencies and cannot be permanently deleted. Do you want to set the active flag of this customer to false?"));
                deactivateQuestion.setButtonText(EJQuestionButton.ONE, "Yes");
                deactivateQuestion.setButtonText(EJQuestionButton.TWO, "Cancel");
                deactivateQuestion.setRecord(question.getRecord());
                deactivateQuestion.getForm().askQuestion(deactivateQuestion);
            }
        }
        if (question.getName().equals("ASK_DEACTIVATE_CUSTOMER") && question.getAnswer().equals(EJQuestionButton.ONE))
        {
            ArrayList<Customer> customers = new ArrayList<Customer>();
            Customer cust = (Customer) question.getRecord().getBlockServicePojo();
            cust.setActive(0);
            customers.add(cust);
            new CustomerBlockService().executeUpdate(question.getForm(), customers);

            question.getForm().getBlock(F_CUSTOMERS.B_CUSTOMERS.ID).executeQuery();
        }
    }

    @Override
    public void preOpenScreen(EJBlock block, EJRecord record, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_CUSTOMERS.B_CUSTOMERS.ID.equals(block.getName()))
        {
            block.getScreenItem(screenType, F_CUSTOMERS.B_CUSTOMERS.I_VAT_ID).refreshItemRenderer();

            if (EJScreenType.INSERT.equals(screenType))
            {
                block.getScreenItem(screenType, F_CUSTOMERS.B_CUSTOMERS.I_CONTACT_TYPES_ID).refreshItemRenderer();
                block.getScreenItem(screenType, F_CUSTOMERS.B_CUSTOMERS.I_SALUTATIONS_ID).refreshItemRenderer();
            }
        }
    }
    
    @Override
    public void postFormSave(EJForm form) throws EJActionProcessorException
    {
        if (customerInserted)
        {
//            EJParameterList paramList = new EJParameterList();
//            EJFormParameter cstParam = new EJFormParameter(F_CUSTOMER_CONTACTS.P_CST_ID, Integer.class);
//            cstParam.setValue(customerId);
//            paramList.addParameter(cstParam);

//            form.showTabCanvasPage(F_CUSTOMERS.C_MAIN, F_CUSTOMERS.C_MAIN_PAGES.CUSTOMERS);
//            form.showStackedCanvasPage(F_CUSTOMERS.C_CUSTOMER_STACK, F_CUSTOMERS.C_CUSTOMER_STACK_PAGES.CUSTOMER_DETAILS);
//            form.openEmbeddedForm(F_CUSTOMER_CONTACTS.ID, F_CUSTOMERS.C_CUSTOMER_DETAILS_FORM, paramList);
            customerInserted = false;
        }
        else if (customerUpdated)
        {
            customerUpdated = false;
            form.getBlock(F_CUSTOMERS.B_CUSTOMERS.ID).executeQuery();
        }
    }

}
