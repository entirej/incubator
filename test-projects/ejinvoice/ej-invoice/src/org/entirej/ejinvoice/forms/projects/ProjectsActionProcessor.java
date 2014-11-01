package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Locale;

import org.apache.commons.validator.routines.EmailValidator;
import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.forms.constants.F_CUSTOMERS;
import org.entirej.ejinvoice.forms.constants.F_PROJECTS;
import org.entirej.ejinvoice.forms.constants.F_PROJECTS;
import org.entirej.ejinvoice.forms.constants.F_PROJECTS;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY;
import org.entirej.ejinvoice.forms.invoice.InvoicePosition;
import org.entirej.ejinvoice.forms.invoice.InvoiceService;
import org.entirej.ejinvoice.forms.timeentry.Customer;
import org.entirej.ejinvoice.forms.timeentry.FormHandler;
import org.entirej.ejinvoice.menu.MenuBlockService;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.data.controllers.EJQuestion;
import org.entirej.framework.core.enumerations.EJMessageLevel;
import org.entirej.framework.core.enumerations.EJPopupButton;
import org.entirej.framework.core.enumerations.EJQuestionButton;
import org.entirej.framework.core.enumerations.EJRecordType;
import org.entirej.framework.core.enumerations.EJScreenType;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;

public class ProjectsActionProcessor extends DefaultFormActionProcessor
{
    private boolean    refreshProjectLists    = false;
    private boolean    timeEntryInserted      = false;
    private BigDecimal markedForInvoiceAmount = new BigDecimal(0);

    @Override
    public void validateItem(EJForm form, EJRecord record, String itemName, EJScreenType screenType) throws EJActionProcessorException
    {

        if (F_PROJECTS.B_PROJECTS.I_FIX_PRICE.equals(itemName) && EJScreenType.INSERT.equals(screenType))
        {
            BigDecimal fixPrice = (BigDecimal) record.getValue(F_PROJECTS.B_PROJECTS.I_FIX_PRICE);
            if (fixPrice == null)
            {
                form.getBlock(F_PROJECTS.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_PROJECTS.B_PROJECTS.I_TASK_FIX_PRICE).setEditable(true);
                form.getBlock(F_PROJECTS.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_PROJECTS.B_PROJECTS.I_TASK_PAY_RATE).setEditable(true);
            }
            else
            {
                form.getBlock(F_PROJECTS.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_PROJECTS.B_PROJECTS.I_TASK_FIX_PRICE).setValue(null);
                form.getBlock(F_PROJECTS.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_PROJECTS.B_PROJECTS.I_TASK_PAY_RATE).setValue(null);
                form.getBlock(F_PROJECTS.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_PROJECTS.B_PROJECTS.I_TASK_INVOICEABLE).setValue("Y");
                form.getBlock(F_PROJECTS.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_PROJECTS.B_PROJECTS.I_TASK_INVOICEABLE).setEditable(false);
                form.getBlock(F_PROJECTS.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_PROJECTS.B_PROJECTS.I_TASK_FIX_PRICE).setEditable(false);
                form.getBlock(F_PROJECTS.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_PROJECTS.B_PROJECTS.I_TASK_PAY_RATE).setEditable(false);
            }
        }
    }

    @Override
    public void validateRecord(EJForm form, EJRecord record, EJRecordType recordType) throws EJActionProcessorException
    {
        if ((EJRecordType.INSERT.equals(recordType) || EJRecordType.UPDATE.equals(recordType)) && F_PROJECTS.B_PROJECTS.ID.equals(record.getBlockName()))
        {
            if (record.getValue(F_PROJECTS.B_PROJECTS.I_INVOICEABLE).equals("Y"))
            {
                record.setValue(F_PROJECTS.B_PROJECTS.I_INVOICEABLE_IMAGE, "/icons/coins.png");
            }
        }
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_PROJECTS.AC_SHOW_PROJECT_TASKS.equals(command))
        {
            Integer projectId = (Integer) record.getValue(F_PROJECTS.B_PROJECTS.I_ID);
            new FormHandler().openProjectTasks(form, "INWORK", projectId);
            new FormHandler().synchronizeMenu(form.getForm(F_TIME_ENTRY.ID), MenuBlockService.TASKS_INWORK);
        }
        if (F_PROJECTS.AC_INVOICEABLE.equals(command) && EJScreenType.INSERT.equals(screenType))
        {
            if (record.getValue(F_PROJECTS.B_PROJECTS.I_INVOICEABLE).equals("Y"))
            {
                form.getBlock(F_PROJECTS.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_PROJECTS.B_PROJECTS.I_FIX_PRICE).setEditable(true);
                form.getBlock(F_PROJECTS.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_PROJECTS.B_PROJECTS.I_TASK_FIX_PRICE).setEditable(true);
                form.getBlock(F_PROJECTS.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_PROJECTS.B_PROJECTS.I_TASK_PAY_RATE).setEditable(true);
                form.getBlock(F_PROJECTS.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_PROJECTS.B_PROJECTS.I_TASK_INVOICEABLE).setEditable(true);
            }
            else
            {
                record.setValue(F_PROJECTS.B_PROJECTS.I_FIX_PRICE, null);
                record.setValue(F_PROJECTS.B_PROJECTS.I_TASK_FIX_PRICE, null);
                record.setValue(F_PROJECTS.B_PROJECTS.I_TASK_PAY_RATE, null);
                record.setValue(F_PROJECTS.B_PROJECTS.I_TASK_INVOICEABLE, "N");

                form.getBlock(F_PROJECTS.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_PROJECTS.B_PROJECTS.I_FIX_PRICE).setEditable(false);
                form.getBlock(F_PROJECTS.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_PROJECTS.B_PROJECTS.I_TASK_FIX_PRICE).setEditable(false);
                form.getBlock(F_PROJECTS.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_PROJECTS.B_PROJECTS.I_TASK_PAY_RATE).setEditable(false);
                form.getBlock(F_PROJECTS.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_PROJECTS.B_PROJECTS.I_TASK_INVOICEABLE).setEditable(false);
            }
        }
        else if (F_PROJECTS.AC_MODIFY_PROJECT.equals(command))
        {
            form.getBlock(F_PROJECTS.B_PROJECTS_EDIT.ID).clear(true);
            EJRecord editRecord = form.getBlock(F_PROJECTS.B_PROJECTS_EDIT.ID).getFocusedRecord();

            editRecord.setValue(F_PROJECTS.B_PROJECTS_EDIT.I_PAGE_TITLE, record.getValue(F_PROJECTS.B_PROJECTS.I_NAME) + " - Edit");

            editRecord.setValue(F_PROJECTS.B_PROJECTS_EDIT.I_CUSTOMER_ID, record.getValue(F_PROJECTS.B_PROJECTS.I_CUSTOMER_ID));
            editRecord.setValue(F_PROJECTS.B_PROJECTS_EDIT.I_CUSTOMER_NAME, record.getValue(F_PROJECTS.B_PROJECTS.I_CUSTOMER_NAME));
            editRecord.setValue(F_PROJECTS.B_PROJECTS_EDIT.I_DESCRIPTION, record.getValue(F_PROJECTS.B_PROJECTS.I_DESCRIPTION));
            editRecord.setValue(F_PROJECTS.B_PROJECTS_EDIT.I_END_DATE, record.getValue(F_PROJECTS.B_PROJECTS.I_END_DATE));
            editRecord.setValue(F_PROJECTS.B_PROJECTS_EDIT.I_FIX_PRICE, record.getValue(F_PROJECTS.B_PROJECTS.I_FIX_PRICE));
            editRecord.setValue(F_PROJECTS.B_PROJECTS_EDIT.I_MAXIMUM_HOURS, record.getValue(F_PROJECTS.B_PROJECTS.I_MAXIMUM_HOURS));
            editRecord.setValue(F_PROJECTS.B_PROJECTS_EDIT.I_BOOKABLE_HOURS, record.getValue(F_PROJECTS.B_PROJECTS.I_BOOKABLE_HOURS));
            editRecord.setValue(F_PROJECTS.B_PROJECTS_EDIT.I_INVOICEABLE, record.getValue(F_PROJECTS.B_PROJECTS.I_INVOICEABLE));
            editRecord.setValue(F_PROJECTS.B_PROJECTS_EDIT.I_NAME, record.getValue(F_PROJECTS.B_PROJECTS.I_NAME));
            editRecord.setValue(F_PROJECTS.B_PROJECTS_EDIT.I_NOTES, record.getValue(F_PROJECTS.B_PROJECTS.I_NOTES));
            editRecord.setValue(F_PROJECTS.B_PROJECTS_EDIT.I_START_DATE, record.getValue(F_PROJECTS.B_PROJECTS.I_START_DATE));
            editRecord.setValue(F_PROJECTS.B_PROJECTS_EDIT.I_STATUS, record.getValue(F_PROJECTS.B_PROJECTS.I_STATUS));

            form.showStackedCanvasPage(F_PROJECTS.C_MAIN_STACK, F_PROJECTS.C_MAIN_STACK_PAGES.EDIT);
            form.setFormParameter(F_PROJECTS.P_IN_EDIT_MODE, true);
        }
        else if (F_PROJECTS.AC_EDIT_SAVE.equals(command))
        {
            clearError(form, F_PROJECTS.B_PROJECTS_EDIT.ID, F_PROJECTS.B_PROJECTS_EDIT.I_CUSTOMER_ID);
            clearError(form, F_PROJECTS.B_PROJECTS_EDIT.ID, F_PROJECTS.B_PROJECTS_EDIT.I_NAME);
            clearError(form, F_PROJECTS.B_PROJECTS_EDIT.ID, F_PROJECTS.B_PROJECTS_EDIT.I_STATUS);

            Integer customerId = (Integer) record.getValue(F_PROJECTS.B_PROJECTS_EDIT.I_CUSTOMER_ID);
            String name = (String) record.getValue(F_PROJECTS.B_PROJECTS_EDIT.I_NAME);
            String status = (String) record.getValue(F_PROJECTS.B_PROJECTS_EDIT.I_STATUS);
            Integer maximumHours = (Integer)record.getValue(F_PROJECTS.B_PROJECTS_EDIT.I_MAXIMUM_HOURS);
            Integer bookableHours = (Integer)record.getValue(F_PROJECTS.B_PROJECTS_EDIT.I_BOOKABLE_HOURS);

            if (bookableHours == null) { bookableHours = 0;};
            if (maximumHours == null) { maximumHours = 0;};
            
            boolean error = false;
            if (customerId == null)
            {
                error = true;
                setError(form, F_PROJECTS.B_PROJECTS_EDIT.ID, F_PROJECTS.B_PROJECTS_EDIT.I_CUSTOMER_ID, "Please choose a customer");
            }
            if (name == null || name.trim().length() == 0)
            {
                error = true;
                setError(form, F_PROJECTS.B_PROJECTS_EDIT.ID, F_PROJECTS.B_PROJECTS_EDIT.I_NAME, "Please enter a name");
            }
            if (status == null || status.trim().length() == 0)
            {
                error = true;
                setError(form, F_PROJECTS.B_PROJECTS_EDIT.ID, F_PROJECTS.B_PROJECTS_EDIT.I_STATUS, "Please choose a project status");
            }
            if (bookableHours > maximumHours)
            {
                error = true;
                setError(form, F_PROJECTS.B_PROJECTS_EDIT.ID, F_PROJECTS.B_PROJECTS_EDIT.I_BOOKABLE_HOURS, "The bookable hours cannot be more than the maximum allowed hours");
            }
            if (error)
            {
                throw new EJActionProcessorException();
            }

            EJRecord baseRecord = form.getBlock(F_PROJECTS.B_PROJECTS.ID).getFocusedRecord();

            baseRecord.setValue(F_PROJECTS.B_PROJECTS.I_CUSTOMER_ID, record.getValue(F_PROJECTS.B_PROJECTS_EDIT.I_CUSTOMER_ID));
            baseRecord.setValue(F_PROJECTS.B_PROJECTS.I_DESCRIPTION, record.getValue(F_PROJECTS.B_PROJECTS_EDIT.I_DESCRIPTION));
            baseRecord.setValue(F_PROJECTS.B_PROJECTS.I_END_DATE, record.getValue(F_PROJECTS.B_PROJECTS_EDIT.I_END_DATE));
            baseRecord.setValue(F_PROJECTS.B_PROJECTS.I_FIX_PRICE, record.getValue(F_PROJECTS.B_PROJECTS_EDIT.I_FIX_PRICE));
            baseRecord.setValue(F_PROJECTS.B_PROJECTS.I_BOOKABLE_HOURS, record.getValue(F_PROJECTS.B_PROJECTS_EDIT.I_BOOKABLE_HOURS));
            baseRecord.setValue(F_PROJECTS.B_PROJECTS.I_MAXIMUM_HOURS, record.getValue(F_PROJECTS.B_PROJECTS_EDIT.I_MAXIMUM_HOURS));
            baseRecord.setValue(F_PROJECTS.B_PROJECTS.I_INVOICEABLE, record.getValue(F_PROJECTS.B_PROJECTS_EDIT.I_INVOICEABLE));
            baseRecord.setValue(F_PROJECTS.B_PROJECTS.I_NAME, record.getValue(F_PROJECTS.B_PROJECTS_EDIT.I_NAME));
            baseRecord.setValue(F_PROJECTS.B_PROJECTS.I_NOTES, record.getValue(F_PROJECTS.B_PROJECTS_EDIT.I_NOTES));
            baseRecord.setValue(F_PROJECTS.B_PROJECTS.I_START_DATE, record.getValue(F_PROJECTS.B_PROJECTS_EDIT.I_START_DATE));
            baseRecord.setValue(F_PROJECTS.B_PROJECTS.I_STATUS, record.getValue(F_PROJECTS.B_PROJECTS_EDIT.I_STATUS));

            Project project = (Project) baseRecord.getBlockServicePojo();
            project.setCustomerName((String) record.getValue(F_PROJECTS.B_PROJECTS_EDIT.I_CUSTOMER_NAME));

            baseRecord.setValue(F_PROJECTS.B_PROJECTS.I_DISPLAY_TEXT, project.getDisplayText());

            form.getBlock(F_PROJECTS.B_PROJECTS.ID).updateRecord(baseRecord);
            form.saveChanges();
            form.getBlock(F_PROJECTS.B_PROJECTS_EDIT.ID).clear(true);
            form.showStackedCanvasPage(F_PROJECTS.C_MAIN_STACK, F_PROJECTS.C_MAIN_STACK_PAGES.MAIN);
            form.setFormParameter(F_PROJECTS.P_IN_EDIT_MODE, false);
        }
        else if (F_PROJECTS.AC_INSERT_SAVE.equals(command))
        {
            clearError(form, F_PROJECTS.B_PROJECT_INSERT.ID, F_PROJECTS.B_PROJECT_INSERT.I_CUSTOMER_ID);
            clearError(form, F_PROJECTS.B_PROJECT_INSERT.ID, F_PROJECTS.B_PROJECT_INSERT.I_NAME);
            clearError(form, F_PROJECTS.B_PROJECT_INSERT.ID, F_PROJECTS.B_PROJECT_INSERT.I_STATUS);
            clearError(form, F_PROJECTS.B_PROJECT_INSERT.ID, F_PROJECTS.B_PROJECT_INSERT.I_TASK_NAME);
            clearError(form, F_PROJECTS.B_PROJECT_INSERT.ID, F_PROJECTS.B_PROJECT_INSERT.I_TASK_STATUS);

            Integer customerId = (Integer) record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_CUSTOMER_ID);
            String name = (String) record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_NAME);
            String status = (String) record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_STATUS);
            String taskName = (String) record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_TASK_NAME);
            String taskStatus = (String) record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_TASK_STATUS);
            Integer maximumHours = (Integer)record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_MAXIMUM_HOURS);
            Integer bookableHours = (Integer)record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_BOOKABLE_HOURS);
            
            boolean error = false;
            if (customerId == null)
            {
                error = true;
                setError(form, F_PROJECTS.B_PROJECT_INSERT.ID, F_PROJECTS.B_PROJECT_INSERT.I_CUSTOMER_ID, "Please choose a customer");
            }
            if (name == null || name.trim().length() == 0)
            {
                error = true;
                setError(form, F_PROJECTS.B_PROJECT_INSERT.ID, F_PROJECTS.B_PROJECT_INSERT.I_NAME, "Please enter a project name");
            }
            if (status == null || status.trim().length() == 0)
            {
                error = true;
                setError(form, F_PROJECTS.B_PROJECT_INSERT.ID, F_PROJECTS.B_PROJECT_INSERT.I_STATUS, "Please choose a project status");
            }
            if (taskName == null || taskName.trim().length() == 0)
            {
                error = true;
                setError(form, F_PROJECTS.B_PROJECT_INSERT.ID, F_PROJECTS.B_PROJECT_INSERT.I_TASK_NAME, "Please enter a task name");
            }
            if (taskStatus == null || taskStatus.trim().length() == 0)
            {
                error = true;
                setError(form, F_PROJECTS.B_PROJECT_INSERT.ID, F_PROJECTS.B_PROJECT_INSERT.I_TASK_STATUS, "Please choose a task status");
            }
            if (bookableHours > maximumHours)
            {
                error = true;
                setError(form, F_PROJECTS.B_PROJECT_INSERT.ID, F_PROJECTS.B_PROJECT_INSERT.I_BOOKABLE_HOURS, "The bookable hours cannot be more than the maximum allowed hours");
            }
            
            if (error)
            {
                throw new EJApplicationException();
            }

            Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
            EJRecord newRecord = form.getBlock(F_PROJECTS.B_PROJECTS.ID).createRecord();

            int idSeqNextval = PKSequenceService.getPKSequence(form.getConnection());
            newRecord.setValue(F_PROJECTS.B_PROJECTS.I_ID, idSeqNextval);
            newRecord.setValue(F_PROJECTS.B_PROJECTS.I_COMPANY_ID, companyId);
            newRecord.setValue(F_PROJECTS.B_PROJECTS.I_CUSTOMER_ID, customerId);
            newRecord.setValue(F_PROJECTS.B_PROJECTS.I_DESCRIPTION, record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_DESCRIPTION));
            newRecord.setValue(F_PROJECTS.B_PROJECTS.I_END_DATE, record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_END_DATE));
            newRecord.setValue(F_PROJECTS.B_PROJECTS.I_FIX_PRICE, record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_FIX_PRICE));
            newRecord.setValue(F_PROJECTS.B_PROJECTS.I_BOOKABLE_HOURS, record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_BOOKABLE_HOURS));
            newRecord.setValue(F_PROJECTS.B_PROJECTS.I_MAXIMUM_HOURS, record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_MAXIMUM_HOURS));
            newRecord.setValue(F_PROJECTS.B_PROJECTS.I_INVOICEABLE, record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_INVOICEABLE));
            newRecord.setValue(F_PROJECTS.B_PROJECTS.I_NAME, record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_NAME));
            newRecord.setValue(F_PROJECTS.B_PROJECTS.I_NOTES, record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_NOTES));
            newRecord.setValue(F_PROJECTS.B_PROJECTS.I_START_DATE, record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_START_DATE));
            newRecord.setValue(F_PROJECTS.B_PROJECTS.I_STATUS, record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_STATUS));

            newRecord.setValue(F_PROJECTS.B_PROJECTS.I_TASK_CPR_ID, idSeqNextval);

            newRecord.setValue(F_PROJECTS.B_PROJECTS.I_TASK_FIX_PRICE, record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_TASK_FIX_PRICE));
            newRecord.setValue(F_PROJECTS.B_PROJECTS.I_TASK_INVOICEABLE, record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_TASK_INVOICEABLE));
            newRecord.setValue(F_PROJECTS.B_PROJECTS.I_TASK_NAME, record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_TASK_NAME));
            newRecord.setValue(F_PROJECTS.B_PROJECTS.I_TASK_NOTES, record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_TASK_NOTES));
            newRecord.setValue(F_PROJECTS.B_PROJECTS.I_TASK_PAY_RATE, record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_TASK_HOURLY_RATE));
            newRecord.setValue(F_PROJECTS.B_PROJECTS.I_TASK_STATUS, record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_TASK_STATUS));

            Project project = (Project) newRecord.getBlockServicePojo();
            project.setCustomerName((String) record.getValue(F_PROJECTS.B_PROJECT_INSERT.I_CUSTOMER_NAME));

            newRecord.setValue(F_PROJECTS.B_PROJECTS.I_DISPLAY_TEXT, project.getDisplayText());

            form.getBlock(F_PROJECTS.B_PROJECTS.ID).insertRecord(newRecord);
            form.saveChanges();
            form.getBlock(F_PROJECTS.B_PROJECT_INSERT.ID).clear(true);
            form.showStackedCanvasPage(F_PROJECTS.C_MAIN_STACK, F_PROJECTS.C_MAIN_STACK_PAGES.MAIN);
            form.setFormParameter(F_PROJECTS.P_IN_EDIT_MODE, false);
        }
        else if (F_PROJECTS.AC_EDIT_CANCEL.equals(command))
        {
            clearError(form, F_PROJECTS.B_PROJECTS_EDIT.ID, F_PROJECTS.B_PROJECTS_EDIT.I_CUSTOMER_ID);
            clearError(form, F_PROJECTS.B_PROJECTS_EDIT.ID, F_PROJECTS.B_PROJECTS_EDIT.I_NAME);
            clearError(form, F_PROJECTS.B_PROJECTS_EDIT.ID, F_PROJECTS.B_PROJECTS_EDIT.I_STATUS);

            form.getBlock(F_PROJECTS.B_PROJECTS_EDIT.ID).clear(true);
            form.showStackedCanvasPage(F_PROJECTS.C_MAIN_STACK, F_PROJECTS.C_MAIN_STACK_PAGES.MAIN);
            form.setFormParameter(F_PROJECTS.P_IN_EDIT_MODE, true);
        }
        else if (F_PROJECTS.AC_INSERT_CANCEL.equals(command))
        {
            clearError(form, F_PROJECTS.B_PROJECT_INSERT.ID, F_PROJECTS.B_PROJECT_INSERT.I_CUSTOMER_ID);
            clearError(form, F_PROJECTS.B_PROJECT_INSERT.ID, F_PROJECTS.B_PROJECT_INSERT.I_NAME);
            clearError(form, F_PROJECTS.B_PROJECT_INSERT.ID, F_PROJECTS.B_PROJECT_INSERT.I_STATUS);
            clearError(form, F_PROJECTS.B_PROJECT_INSERT.ID, F_PROJECTS.B_PROJECT_INSERT.I_TASK_NAME);
            clearError(form, F_PROJECTS.B_PROJECT_INSERT.ID, F_PROJECTS.B_PROJECT_INSERT.I_TASK_STATUS);

            form.getBlock(F_PROJECTS.B_PROJECT_INSERT.ID).clear(true);
            form.showStackedCanvasPage(F_PROJECTS.C_MAIN_STACK, F_PROJECTS.C_MAIN_STACK_PAGES.MAIN);
            form.setFormParameter(F_PROJECTS.P_IN_EDIT_MODE, false);
        }
        else if (F_PROJECTS.AC_CREATE_NEW_PROJECT.equals(command))
        {
            form.getBlock(F_PROJECTS.B_PROJECT_INSERT.ID).clear(true);
            EJRecord insertRecord = form.getBlock(F_PROJECTS.B_PROJECT_INSERT.ID).getFocusedRecord();

            insertRecord.setValue(F_PROJECTS.B_PROJECT_INSERT.I_PAGE_TITLE, "Create a new Project");
            insertRecord.setValue(F_PROJECTS.B_PROJECT_INSERT.I_TASK_TITLE, "Add a new Task");

            form.showStackedCanvasPage(F_PROJECTS.C_MAIN_STACK, F_PROJECTS.C_MAIN_STACK_PAGES.INSERT);
            form.setFormParameter(F_PROJECTS.P_IN_EDIT_MODE, true);
        }
        else if (F_PROJECTS.AC_REFRESH_PROJECT_LIST.equals(command))
        {
            form.getBlock(F_PROJECTS.B_PROJECTS.ID).executeQuery();
        }
    }

    @Override
    public void postFormSave(EJForm form) throws EJActionProcessorException
    {
        if (timeEntryInserted)
        {
            timeEntryInserted = false;

            // form.showStackedCanvasPage(F_PROJECTS.C_PROJECT_TAB,
            // F_PROJECTS.C_PROJECT_TAB_PAGES.INVOICE__PLANNING);
            // form.getBlock(F_PROJECTS.B_PROJECT_TASKS.ID).executeQuery();

            EJMessage message = new EJMessage(EJMessageLevel.MESSAGE, "Before you can book time against your project you need a project task. Please enter one here before continuing.");

            form.showMessage(message);
        }
        if (refreshProjectLists)
        {
            refreshProjectLists = false;
            form.getForm(F_TIME_ENTRY.ID).getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_PROJECT).refreshItemRenderer();
        }
    }

    @Override
    public void postQuery(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_PROJECTS.B_PROJECTS.ID.equals(record.getBlockName()))
        {
            if (record.getValue(F_PROJECTS.B_PROJECTS.I_INVOICEABLE).equals("Y"))
            {
                record.setValue(F_PROJECTS.B_PROJECTS.I_INVOICEABLE_IMAGE, "/icons/coins.png");
            }
            else
            {
                record.setValue(F_PROJECTS.B_PROJECTS.I_INVOICEABLE_IMAGE, null);
            }

            if (((Long) record.getValue(F_PROJECTS.B_PROJECTS.I_OPEN_ITEMS)).intValue() > 0)
            {
                record.setValue(F_PROJECTS.B_PROJECTS.I_OPEN_ITEMS_IMAGE, "/icons/openhours.png");
            }
            else
            {
                record.setValue(F_PROJECTS.B_PROJECTS.I_OPEN_ITEMS_IMAGE, null);
            }

            if (((Long) record.getValue(F_PROJECTS.B_PROJECTS.I_PLANNED_ITEMS)).intValue() > 0)
            {
                record.setValue(F_PROJECTS.B_PROJECTS.I_PLANNED_ITEMS_IMAGE, "/icons/plannedhours.png");
            }
            else
            {
                record.setValue(F_PROJECTS.B_PROJECTS.I_PLANNED_ITEMS_IMAGE, null);
            }

            if (((Long) record.getValue(F_PROJECTS.B_PROJECTS.I_APPROVED_ITEMS)).intValue() > 0)
            {
                record.setValue(F_PROJECTS.B_PROJECTS.I_APPROVED_ITEMS_IMAGE, "/icons/approvedhours.png");
            }
            else
            {
                record.setValue(F_PROJECTS.B_PROJECTS.I_APPROVED_ITEMS_IMAGE, null);
            }

            if (((Long) record.getValue(F_PROJECTS.B_PROJECTS.I_MARKED_FOR_INVOICE_ITEMS)).intValue() > 0)
            {
                record.setValue(F_PROJECTS.B_PROJECTS.I_MARKED_FOR_INVOICE_ITEMS_IMAGE, "/icons/invoice.png");
            }
            else
            {
                record.setValue(F_PROJECTS.B_PROJECTS.I_MARKED_FOR_INVOICE_ITEMS_IMAGE, null);
            }
        }
    }

    @Override
    public void postInsert(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_PROJECTS.B_PROJECTS.ID.equals(record.getBlockName()))
        {
            refreshProjectLists = true;
        }
    }

    @Override
    public void postUpdate(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_PROJECTS.B_PROJECTS.ID.equals(record.getBlockName()))
        {
            refreshProjectLists = true;
        }
    }

    @Override
    public void preOpenScreen(EJBlock block, EJRecord record, EJScreenType screenType) throws EJActionProcessorException
    {
        if (screenType.equals(EJScreenType.INSERT) && F_PROJECTS.B_PROJECTS.ID.equals(block.getName()))
        {
            block.getForm().getBlock(F_PROJECTS.B_PROJECTS.ID).getScreenItem(screenType, F_PROJECTS.B_PROJECTS.I_TASK_STATUS).refreshItemRenderer();
            block.getForm().getBlock(F_PROJECTS.B_PROJECTS.ID).getScreenItem(screenType, F_PROJECTS.B_PROJECTS.I_STATUS).refreshItemRenderer();
        }
    }

}
