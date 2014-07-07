package org.entirej.ejinvoice.forms.timeentry;

import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.forms.constants.F_COMPANY;
import org.entirej.ejinvoice.forms.constants.F_CUSTOMER;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJParameterList;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.data.controllers.EJFormParameter;
import org.entirej.framework.core.enumerations.EJMessageLevel;
import org.entirej.framework.core.enumerations.EJScreenType;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;

public class TimeEntryActionProcessor extends DefaultFormActionProcessor
{

    private boolean timeEntryInserted = false;
    private Integer projectId         = null;

    @Override
    public void validateItem(EJForm form, EJRecord record, String itemName, EJScreenType screenType) throws EJActionProcessorException
    {
        System.err.println("item: " + itemName);
        if (screenType == EJScreenType.MAIN && F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_PROJECT.equals(itemName))
        {
            form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_PROCESS).refreshItemRenderer();
        }
    }

    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        form.getBlock(F_TIME_ENTRY.B_COMPANY.ID).executeQuery();
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {

        if (F_TIME_ENTRY.AC_EDIT_COMPANY_INFORMATION.equals(command))
        {
            form.openForm(F_COMPANY.ID);
        }
        else if (F_TIME_ENTRY.AC_PROJECT_DETAILS.equals(command))
        {
            form.showStackedCanvasPage(F_TIME_ENTRY.C_PROJECTS_STACK, F_TIME_ENTRY.C_PROJECTS_STACK_PAGES.PROCESS);

            Integer projectId = (Integer) record.getValue(F_TIME_ENTRY.B_PROJECTS.I_ID);

            EJQueryCriteria criteria = form.getBlock(F_TIME_ENTRY.B_PROJECTS_DETAIL.ID).createQueryCriteria();
            criteria.add(EJRestrictions.equals(F_TIME_ENTRY.B_PROJECTS_DETAIL.I_ID, projectId));

            form.getBlock(F_TIME_ENTRY.B_PROJECTS_DETAIL.ID).executeQuery(criteria);
        }
        else if (F_TIME_ENTRY.AC_BACK_TO_PROJECT_OVERVIEW.equals(command))
        {
            form.showStackedCanvasPage(F_TIME_ENTRY.C_PROJECTS_STACK, F_TIME_ENTRY.C_PROJECTS_STACK_PAGES.PROJECTS);
            form.getBlock(F_TIME_ENTRY.B_PROJECTS_DETAIL.ID).clear(true);
        }
        else if (F_TIME_ENTRY.AC_ADD_NEW_PROCESS.equals(command))
        {
            form.getBlock(F_TIME_ENTRY.B_PROJECT_PROCESS.ID).enterInsert(false);
        }
        else if (F_TIME_ENTRY.AC_CREATE_NEW_PROJECT.equals(command))
        {
            form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).enterInsert(false);
        }
        else if (F_TIME_ENTRY.AC_SHOW_CUSTOMER_DETAILS.equals(command))
        {
            EJParameterList paramList = new EJParameterList();
            EJFormParameter cstParam = new EJFormParameter(F_CUSTOMER.P_CST_ID, Integer.class);
            cstParam.setValue(record.getValue(F_TIME_ENTRY.B_CUSTOMERS.I_ID));
            paramList.addParameter(cstParam);
            
            form.showStackedCanvasPage(F_TIME_ENTRY.C_CUSTOMER_STACK, F_TIME_ENTRY.C_CUSTOMER_STACK_PAGES.CUSTOMER_DETAILS);
            form.openEmbeddedForm(F_CUSTOMER.ID, F_TIME_ENTRY.C_CUSTOMER_DETAILS_FORM, paramList);
        }

    }

    @Override
    public void postInsert(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_TIME_ENTRY.B_PROJECTS.ID.equals(record.getBlockName()))
        {
            timeEntryInserted = true;
            projectId = (Integer) record.getValue(F_TIME_ENTRY.B_PROJECTS.I_ID);
        }
    }

    @Override
    public void postFormSave(EJForm form) throws EJActionProcessorException
    {
        if (timeEntryInserted)
        {
            timeEntryInserted = false;

            EJQueryCriteria criteria = form.getBlock(F_TIME_ENTRY.B_PROJECTS_DETAIL.ID).createQueryCriteria();
            criteria.add(EJRestrictions.equals(F_TIME_ENTRY.B_PROJECTS_DETAIL.I_ID, projectId));

            form.showStackedCanvasPage(F_TIME_ENTRY.C_PROJECTS_STACK, F_TIME_ENTRY.C_PROJECTS_STACK_PAGES.PROCESS);
            form.getBlock(F_TIME_ENTRY.B_PROJECTS_DETAIL.ID).executeQuery(criteria);
            
            EJMessage message = new EJMessage(EJMessageLevel.MESSAGE, "Before you can book time against your project you need a project process. Please enter one here before continuing.");
            
            form.showMessage(message);
        }
    }

    @Override
    public void tabPageChanged(EJForm form, String tabCanvasName, String tabPageName) throws EJActionProcessorException
    {
        if (F_TIME_ENTRY.C_MAIN.equals(tabCanvasName))
        {
            if (F_TIME_ENTRY.C_MAIN_PAGES.PROJECTS.equals(tabPageName))
            {
                form.showStackedCanvasPage(F_TIME_ENTRY.C_PROJECTS_STACK, F_TIME_ENTRY.C_PROJECTS_STACK_PAGES.PROJECTS);
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).executeQuery();
            }
            else if (F_TIME_ENTRY.C_MAIN_PAGES.CUSTOMERS.equals(tabPageName))
            {
                form.getBlock(F_TIME_ENTRY.B_CUSTOMERS.ID).executeQuery();
            }
        }
    }

    @Override
    public void postQuery(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_TIME_ENTRY.B_COMPANY.ID.equals(record.getBlockName()))
        {
            String name = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_NAME);
            String address1 = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_ADDRESS_LINE_1);
            String address2 = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_ADDRESS_LINE_2);
            String address3 = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_ADDRESS_LINE_3);
            String zip = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_POST_CODE);
            String town = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_TOWN);
            String country = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_COUNTRY);
            String vatNr = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_VAT_NR);

            StringBuilder str = new StringBuilder();
            str.append(name);
            str.append("\n");
            if (address1 != null)
            {
                str.append(address1);
                str.append("\n");
            }
            if (address2 != null)
            {
                str.append(address2);
                str.append("\n");
            }
            if (address3 != null)
            {
                str.append(address3);
                str.append("\n");
            }
            str.append(zip);
            str.append(" ");
            str.append(town);
            str.append(" ");
            str.append(country);
            str.append("\n");
            str.append(vatNr);

            record.setValue(F_TIME_ENTRY.B_COMPANY.I_ADDRESS, str.toString());
        }
    }

}
