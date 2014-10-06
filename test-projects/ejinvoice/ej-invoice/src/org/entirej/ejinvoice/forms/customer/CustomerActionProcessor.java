package org.entirej.ejinvoice.forms.customer;

import java.util.ArrayList;
import java.util.Locale;

import org.apache.commons.validator.routines.EmailValidator;
import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.constants.F_CUSTOMERS;
import org.entirej.ejinvoice.forms.constants.F_CUSTOMER_CONTACTS;
import org.entirej.ejinvoice.forms.constants.F_SALUTATIONS;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY;
import org.entirej.ejinvoice.forms.timeentry.Customer;
import org.entirej.ejinvoice.forms.timeentry.CustomerBlockService;
import org.entirej.ejinvoice.forms.timeentry.FormHandler;
import org.entirej.ejinvoice.menu.MenuBlockService;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.actionprocessor.interfaces.EJFormActionProcessor;
import org.entirej.framework.core.data.controllers.EJQuestion;
import org.entirej.framework.core.enumerations.EJMessageLevel;
import org.entirej.framework.core.enumerations.EJQuestionButton;
import org.entirej.framework.core.enumerations.EJScreenType;

public class CustomerActionProcessor extends DefaultFormActionProcessor implements EJFormActionProcessor
{
    private boolean customerInserted = false;
    private boolean customerUpdated  = false;
    private Integer customerId       = null;

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_CUSTOMERS.AC_CREATE_NEW_CUSTOMER.equals(command))
        {
            form.getBlock(F_CUSTOMERS.B_CUSTOMERS_INSERT.ID).clear(true);
            EJRecord insertRecord = form.getBlock(F_CUSTOMERS.B_CUSTOMERS_INSERT.ID).getFocusedRecord();

            insertRecord.setValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_PAGE_TITLE, "Create a new Customer");
            form.showStackedCanvasPage(F_CUSTOMERS.C_MAIN_STACK, F_CUSTOMERS.C_MAIN_STACK_PAGES.INSERT);
            form.setFormParameter(F_CUSTOMERS.P_IN_EDIT_MODE, true);
        }
        else if (F_CUSTOMERS.AC_INSERT_SAVE.equals(command))
        {
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_ADDRESS);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_EMAIL);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_LAST_NAME);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_SALUTATIONS_ID);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_CONTACT_TYPES_ID);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_COUNTRY);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_CUSTOMER_NUMBER);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_LOCALE);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_NAME);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_PAYMENT_DAYS);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_VAT_ID);
            
            String customerNumber = (String) record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_CUSTOMER_NUMBER);
            String name = (String) record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_NAME);
            String address = (String) record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_ADDRESS);
            String country = (String) record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_COUNTRY);
            Locale locale = (Locale) record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_LOCALE);
            Integer vatId = (Integer) record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_VAT_ID);
            Integer paymentDays = (Integer) record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_PAYMENT_DAYS);

            Integer contactTypeId = (Integer) record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_CONTACT_TYPES_ID);
            Integer salutationsId = (Integer) record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_SALUTATIONS_ID);
            String contactLastName = (String) record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_LAST_NAME);
            String email = (String) record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_EMAIL);

            boolean error = false;
            if (customerNumber == null || customerNumber.trim().length() == 0)
            {
                error = true;
                setError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_CUSTOMER_NUMBER, "Please enter a customer number");
            }
            if (name == null || name.trim().length() == 0)
            {
                error = true;
                setError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_NAME,  "Please enter a name");
            }
            if (address == null || address.trim().length() == 0)
            {
                error = true;
                setError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_ADDRESS,  "Please enter an address");
            }
            if (country == null || country.trim().length() == 0)
            {
                error = true;
                setError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_COUNTRY,  "Please enter a country");
            }
            if (locale == null)
            {
                error = true;
                setError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_LOCALE,  "Please choose a locale");
            }
            if (vatId == null)
            {
                error = true;
                setError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_VAT_ID,  "Please choose a default VAT%");
            }
            if (paymentDays == null)
            {
                error = true;
                setError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_PAYMENT_DAYS,  "Please enter a default amount for the payment days");
            }
            if (contactTypeId == null)
            {
                error = true;
                setError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_CONTACT_TYPES_ID,  "Please choose a type for the default contact");
            }
            if (salutationsId == null)
            {
                error = true;
                setError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_SALUTATIONS_ID, "Please enter a salutation for the default contact");
            }
            if (contactLastName == null)
            {
                error = true;
                setError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_LAST_NAME,  "Please enter a last name for the default contact");
            }
            if (!EmailValidator.getInstance().isValid(email))
            {
                error = true;
                setError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_EMAIL,  "The email address you have entered is not a valid email address");
            }

            if (error)
            {
                throw new EJApplicationException();
            }
            Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
            EJRecord newRecord = form.getBlock(F_CUSTOMERS.B_CUSTOMERS.ID).createRecord();

            int idSeqNextval = PKSequenceService.getPKSequence(form.getConnection());
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_ID, idSeqNextval);
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_COMPANY_ID, companyId);
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_ACTIVE, record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_ACTIVE));
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_ADDRESS, record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_ADDRESS));
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_COUNTRY, record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_COUNTRY));
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_CUSTOMER_NUMBER, record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_CUSTOMER_NUMBER));
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_LOCALE, record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_LOCALE));
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_LOCALE_COUNTRY, record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_LOCALE_COUNTRY));
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_LOCALE_LANGUAGE, record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_LOCALE_LANGUAGE));
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_NAME, record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_NAME));
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_PAYMENT_DAYS, record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_PAYMENT_DAYS));
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_POST_CODE, record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_POST_CODE));
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_TOWN, record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_TOWN));
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_VAT_ID, record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_VAT_ID));

            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_CONTACT_TYPES_ID, record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_CONTACT_TYPES_ID));
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_CUSTOMER_ID, idSeqNextval);
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_EMAIL,      email);
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_FIRST_NAME, record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_FIRST_NAME));
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_LAST_NAME, record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_LAST_NAME));
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_MOBILE, record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_MOBILE));
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_PHONE, record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_PHONE));
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_SALUTATIONS_ID, record.getValue(F_CUSTOMERS.B_CUSTOMERS_INSERT.I_SALUTATIONS_ID));
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_COMPANY_ID, companyId);

            Customer customer = (Customer)newRecord.getBlockServicePojo();
            newRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_DISPLAY_TEXT, customer.getDisplayText());
            
            form.getBlock(F_CUSTOMERS.B_CUSTOMERS.ID).insertRecord(newRecord);
            form.saveChanges();
            form.getBlock(F_CUSTOMERS.B_CUSTOMERS_INSERT.ID).clear(true);
            form.showStackedCanvasPage(F_CUSTOMERS.C_MAIN_STACK, F_CUSTOMERS.C_MAIN_STACK_PAGES.MAIN);
            form.setFormParameter(F_CUSTOMERS.P_IN_EDIT_MODE, false);
        }
        else if (F_CUSTOMERS.AC_EDIT_CUSTOMER.equals(command))
        {
            form.getBlock(F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID).clear(true);
            EJRecord editRecord = form.getBlock(F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID).getFocusedRecord();

            editRecord.setValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_PAGE_TITLE, record.getValue(F_CUSTOMERS.B_CUSTOMERS.I_NAME) + " - Edit");

            editRecord.setValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_ACTIVE, record.getValue(F_CUSTOMERS.B_CUSTOMERS.I_ACTIVE));
            editRecord.setValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_ADDRESS, record.getValue(F_CUSTOMERS.B_CUSTOMERS.I_ADDRESS));
            editRecord.setValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_COUNTRY, record.getValue(F_CUSTOMERS.B_CUSTOMERS.I_COUNTRY));
            editRecord.setValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_CUSTOMER_NUMBER, record.getValue(F_CUSTOMERS.B_CUSTOMERS.I_CUSTOMER_NUMBER));
            editRecord.setValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_ID, record.getValue(F_CUSTOMERS.B_CUSTOMERS.I_ID));
            editRecord.setValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_LOCALE, record.getValue(F_CUSTOMERS.B_CUSTOMERS.I_LOCALE));
            editRecord.setValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_LOCALE_COUNTRY, record.getValue(F_CUSTOMERS.B_CUSTOMERS.I_LOCALE_COUNTRY));
            editRecord.setValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_LOCALE_LANGUAGE, record.getValue(F_CUSTOMERS.B_CUSTOMERS.I_LOCALE_LANGUAGE));
            editRecord.setValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_NAME, record.getValue(F_CUSTOMERS.B_CUSTOMERS.I_NAME));
            editRecord.setValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_PAYMENT_DAYS, record.getValue(F_CUSTOMERS.B_CUSTOMERS.I_PAYMENT_DAYS));
            editRecord.setValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_POST_CODE, record.getValue(F_CUSTOMERS.B_CUSTOMERS.I_POST_CODE));
            editRecord.setValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_TOWN, record.getValue(F_CUSTOMERS.B_CUSTOMERS.I_TOWN));
            editRecord.setValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_VAT_ID, record.getValue(F_CUSTOMERS.B_CUSTOMERS.I_VAT_ID));

            form.showStackedCanvasPage(F_CUSTOMERS.C_MAIN_STACK, F_CUSTOMERS.C_MAIN_STACK_PAGES.UPDATE);
            form.setFormParameter(F_CUSTOMERS.P_IN_EDIT_MODE, true);
        }
        else if (F_CUSTOMERS.AC_EDIT_SAVE.equals(command))
        {
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID, F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_CUSTOMER_NUMBER);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID, F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_NAME);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID, F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_ADDRESS);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID, F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_COUNTRY);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID, F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_LOCALE);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID, F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_VAT_ID);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID, F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_PAYMENT_DAYS);
            
            String customerNumber = (String) record.getValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_CUSTOMER_NUMBER);
            String name = (String) record.getValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_NAME);
            String address = (String) record.getValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_ADDRESS);
            String country = (String) record.getValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_COUNTRY);
            Locale locale = (Locale) record.getValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_LOCALE);
            Integer vatId = (Integer) record.getValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_VAT_ID);
            Integer paymentDays = (Integer) record.getValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_PAYMENT_DAYS);

            boolean error = false;
            if (customerNumber == null || customerNumber.trim().length() == 0)
            {
                error = true;
                setError(form, F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID, F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_CUSTOMER_NUMBER, "Please enter a customer number");
            }
            if (name == null || name.trim().length() == 0)
            {
                error = true;
                setError(form, F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID, F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_NAME, "Please enter a name");
            }
            if (address == null || address.trim().length() == 0)
            {
                error = true;
                setError(form, F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID, F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_ADDRESS, "Please enter an address");
            }
            if (country == null || country.trim().length() == 0)
            {
                error = true;
                setError(form, F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID, F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_COUNTRY, "Please enter a country");
            }
            if (locale == null)
            {
                error = true;
                setError(form, F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID, F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_LOCALE, "Please choose a locale");
            }
            if (vatId == null)
            {
                error = true;
                setError(form, F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID, F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_VAT_ID, "Please choose a default VAT%");
            }
            if (paymentDays == null)
            {
                error = true;
                setError(form, F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID, F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_PAYMENT_DAYS, "Please enter a default amount for the payment days");
            }
            if (error)
            {
                throw new EJActionProcessorException();
            }
            EJRecord baseRecord = form.getBlock(F_CUSTOMERS.B_CUSTOMERS.ID).getFocusedRecord();

            baseRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_ACTIVE, record.getValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_ACTIVE));
            baseRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_ADDRESS, record.getValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_ADDRESS));
            baseRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_COUNTRY, record.getValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_COUNTRY));
            baseRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_CUSTOMER_NUMBER, record.getValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_CUSTOMER_NUMBER));
            baseRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_LOCALE, record.getValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_LOCALE));
            baseRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_LOCALE_COUNTRY, record.getValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_LOCALE_COUNTRY));
            baseRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_LOCALE_LANGUAGE, record.getValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_LOCALE_LANGUAGE));
            baseRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_NAME, record.getValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_NAME));
            baseRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_PAYMENT_DAYS, record.getValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_PAYMENT_DAYS));
            baseRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_POST_CODE, record.getValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_POST_CODE));
            baseRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_TOWN, record.getValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_TOWN));
            baseRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_VAT_ID, record.getValue(F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_VAT_ID));

            Customer customer = (Customer)baseRecord.getBlockServicePojo();
            baseRecord.setValue(F_CUSTOMERS.B_CUSTOMERS.I_DISPLAY_TEXT, customer.getDisplayText());

            form.getBlock(F_CUSTOMERS.B_CUSTOMERS.ID).updateRecord(baseRecord);
            form.saveChanges();

            form.getBlock(F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID).clear(true);

            form.showStackedCanvasPage(F_CUSTOMERS.C_MAIN_STACK, F_CUSTOMERS.C_MAIN_STACK_PAGES.MAIN);
            form.setFormParameter(F_CUSTOMERS.P_IN_EDIT_MODE, false);
        }
        else if (F_CUSTOMERS.AC_EDIT_CANCEL.equals(command))
        {
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID, F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_CUSTOMER_NUMBER);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID, F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_NAME);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID, F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_ADDRESS);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID, F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_COUNTRY);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID, F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_LOCALE);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID, F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_VAT_ID);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID, F_CUSTOMERS.B_CUSTOMERS_UPDATE.I_PAYMENT_DAYS);
            
            form.getBlock(F_CUSTOMERS.B_CUSTOMERS_UPDATE.ID).clear(true);
            form.showStackedCanvasPage(F_CUSTOMERS.C_MAIN_STACK, F_CUSTOMERS.C_MAIN_STACK_PAGES.MAIN);
            form.setFormParameter(F_CUSTOMERS.P_IN_EDIT_MODE, false);
        }
        else if (F_CUSTOMERS.AC_INSERT_CANCEL.equals(command))
        {
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_ADDRESS);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_EMAIL);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_LAST_NAME);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_SALUTATIONS_ID);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_CONTACT_TYPES_ID);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_COUNTRY);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_CUSTOMER_NUMBER);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_LOCALE);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_NAME);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_PAYMENT_DAYS);
            clearError(form, F_CUSTOMERS.B_CUSTOMERS_INSERT.ID, F_CUSTOMERS.B_CUSTOMERS_INSERT.I_VAT_ID);
            
            form.getBlock(F_CUSTOMERS.B_CUSTOMERS_INSERT.ID).clear(true);
            form.showStackedCanvasPage(F_CUSTOMERS.C_MAIN_STACK, F_CUSTOMERS.C_MAIN_STACK_PAGES.MAIN);
            form.setFormParameter(F_CUSTOMERS.P_IN_EDIT_MODE, false);
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
            if ((Boolean)form.getFormParameter(F_CUSTOMERS.P_IN_EDIT_MODE).getValue())
            {
                throw new EJApplicationException(new EJMessage(EJMessageLevel.WARNING, "Please complete your changes before continuing"));
            }
            Integer customerId = (Integer) record.getValue(F_CUSTOMERS.B_CUSTOMERS.I_ID);
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
                ServiceRetriever.getDBService(question.getForm()).validateDeleteRecordUsage(question.getForm(), question.getForm().getBlock(F_SALUTATIONS.B_SALUTATIONS.ID).getFocusedRecord(), "CUSTOMER");
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
                deactivateQuestion.setMessage(new EJMessage("This customer has data depedencies and cannot be permanently deleted. Do you want to set the active flag of this customer to false?"));
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
            // EJParameterList paramList = new EJParameterList();
            // EJFormParameter cstParam = new
            // EJFormParameter(F_CUSTOMER_CONTACTS.P_CST_ID, Integer.class);
            // cstParam.setValue(customerId);
            // paramList.addParameter(cstParam);

            // form.showTabCanvasPage(F_CUSTOMERS.C_MAIN,
            // F_CUSTOMERS.C_MAIN_PAGES.CUSTOMERS);
            // form.showStackedCanvasPage(F_CUSTOMERS.C_CUSTOMER_STACK,
            // F_CUSTOMERS.C_CUSTOMER_STACK_PAGES.CUSTOMER_DETAILS);
            // form.openEmbeddedForm(F_CUSTOMER_CONTACTS.ID,
            // F_CUSTOMERS.C_CUSTOMER_DETAILS_FORM, paramList);
            customerInserted = false;
        }
        else if (customerUpdated)
        {
            customerUpdated = false;
            form.getBlock(F_CUSTOMERS.B_CUSTOMERS.ID).executeQuery();
        }
    }

}
