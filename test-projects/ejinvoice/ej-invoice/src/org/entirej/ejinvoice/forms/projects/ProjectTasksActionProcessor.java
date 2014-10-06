package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.commons.validator.routines.EmailValidator;
import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.forms.constants.F_CUSTOMERS;
import org.entirej.ejinvoice.forms.constants.F_PROJECTS;
import org.entirej.ejinvoice.forms.constants.F_PROJECT_TASKS;
import org.entirej.ejinvoice.forms.timeentry.Customer;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.enumerations.EJMessageLevel;
import org.entirej.framework.core.enumerations.EJRecordType;
import org.entirej.framework.core.enumerations.EJScreenType;

public class ProjectTasksActionProcessor extends DefaultFormActionProcessor
{

    @Override
    public void postQuery(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE).equals("Y"))
        {
            record.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE_IMAGE, "/icons/coins.png");
        }
        else
        {
            record.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE_IMAGE, null);
        }
    }

    @Override
    public void validateRecord(EJForm form, EJRecord record, EJRecordType recordType) throws EJActionProcessorException
    {
        if ((EJRecordType.INSERT.equals(recordType) || EJRecordType.UPDATE.equals(recordType)) && F_PROJECT_TASKS.B_PROJECT_TASKS.ID.equals(record.getBlockName()))
        {
            Integer cprId = (Integer) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_CPR_ID);
            BigDecimal projectFixPrice = (BigDecimal) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_PROJECT_FIX_PRICE);
            String taskInvoiceable = (String) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE);
            BigDecimal taskFixPrice = (BigDecimal) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_FIX_PRICE);
            BigDecimal taskRate = (BigDecimal) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_PAY_RATE);

            if (cprId == null)
            {
                EJMessage message = new EJMessage(EJMessageLevel.ERROR, "Please choose a project to which this task belongs");
                throw new EJActionProcessorException(message);
            }
            if (projectFixPrice == null)
            {
                if (taskInvoiceable.equals("Y") && (taskFixPrice == null && taskRate == null))
                {
                    EJMessage message = new EJMessage(EJMessageLevel.ERROR, "Either a Fix Price or an Hourly Rate must be entered for this invoicable task");
                    throw new EJActionProcessorException(message);
                }
            }

            if (record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE).equals("Y"))
            {
                record.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE_IMAGE, "/icons/coins.png");
            }
            else
            {
                record.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE_IMAGE, null);
            }
        }
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_PROJECT_TASKS.AC_ADD_NEW_TASK.equals(command))
        {
            form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID).clear(true);
            EJRecord insertRecord = form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID).getFocusedRecord();

            Integer projectId = (Integer) form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS_TOOLBAR.ID).getFocusedRecord().getValue(F_PROJECT_TASKS.B_PROJECT_TASKS_TOOLBAR.I_PROJECTS);
            String projectName = (String) form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS_TOOLBAR.ID).getFocusedRecord().getValue(F_PROJECT_TASKS.B_PROJECT_TASKS_TOOLBAR.I_PROJECT_NAME);
            BigDecimal projectFixPrice = (BigDecimal) form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS_TOOLBAR.ID).getFocusedRecord().getValue(F_PROJECT_TASKS.B_PROJECT_TASKS_TOOLBAR.I_FIX_PRICE);
            if (projectId != null)
            {
                insertRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PROJECT_ID, projectId);
                insertRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PROJECT_NAME, projectName);
                insertRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PROJECT_FIX_PRICE, projectFixPrice);
            }

            insertRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PAGE_TITLE, "Create a new Task");
            insertRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_CUSTOMER_NAME, form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).getFocusedRecord().getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_CUSTOMER_NAME));

            if (projectFixPrice != null)
            {
                record.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_INVOICEABLE, "Y");
                record.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_FIX_PRICE, null);
                record.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PAY_RATE, null);

                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_INVOICEABLE).setEditable(false);
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_FIX_PRICE).setEditable(false);
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PAY_RATE).setEditable(false);
            }
            else
            {
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_INVOICEABLE).setEditable(true);
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_FIX_PRICE).setEditable(true);
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PAY_RATE).setEditable(true);
            }
            form.showStackedCanvasPage(F_PROJECT_TASKS.C_MAIN_STACK, F_PROJECT_TASKS.C_MAIN_STACK_PAGES.INSERT);
            form.setFormParameter(F_PROJECT_TASKS.P_IN_EDIT_MODE, true);
        }
        else if (F_PROJECT_TASKS.AC_INSERT_SAVE.equals(command))
        {
            clearError(form, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PROJECT_ID);
            clearError(form, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_NAME);
            clearError(form, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_STATUS);
            clearError(form, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_FIX_PRICE);
            clearError(form, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PAY_RATE);

            Integer projectId = (Integer) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PROJECT_ID);
            String name = (String) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_NAME);
            String status = (String) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_STATUS);

            BigDecimal projectFixPrice = (BigDecimal) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PROJECT_FIX_PRICE);
            String taskInvoiceable = (String) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_INVOICEABLE);
            BigDecimal taskFixPrice = (BigDecimal) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_FIX_PRICE);
            BigDecimal taskRate = (BigDecimal) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_PAY_RATE);

            boolean error = false;
            if (projectId == null)
            {
                error = true;
                setError(form, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PROJECT_ID, "Please choose a project");
            }
            if (name == null || name.trim().length() == 0)
            {
                error = true;
                setError(form, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_NAME, "Please enter a name");
            }
            if (status == null || status.trim().length() == 0)
            {
                error = true;
                setError(form, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_STATUS, "Please choose a status");
            }
            if (projectFixPrice == null)
            {
                if (taskInvoiceable.equals("Y") && (taskFixPrice == null && taskRate == null))
                {
                    error = true;
                    setError(form, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PAY_RATE, "Either a Fix Price or an Hourly Rate must be entered for this invoicable task");
                    setError(form, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_FIX_PRICE, "");
                }
            }
            if (error)
            {
                throw new EJApplicationException();
            }

            Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
            EJRecord newRecord = form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).createRecord();

            int idSeqNextval = PKSequenceService.getPKSequence(form.getConnection());
            newRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_ID, idSeqNextval);
            newRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_COMPANY_ID, companyId);

            newRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_CPR_ID, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PROJECT_ID));
            newRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_FIX_PRICE, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_FIX_PRICE));
            newRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_INVOICEABLE));
            newRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_NAME, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_NAME));
            newRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_NOTES, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_NOTES));
            newRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_PAY_RATE, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PAY_RATE));
            newRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_STATUS, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_STATUS));

            if (record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE).equals("Y"))
            {
                newRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE_IMAGE, "/icons/coins.png");
            }
            else
            {
                newRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE_IMAGE, null);
            }

            ProjectTasks task = (ProjectTasks) newRecord.getBlockServicePojo();
            task.setProjectName((String) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PROJECT_NAME));
            task.setCustomerName((String) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_CUSTOMER_NAME));
            newRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_DISPLAY_TEXT_1, task.getDisplayText1());

            form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).insertRecord(newRecord);
            form.saveChanges();
            form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID).clear(true);
            form.showStackedCanvasPage(F_PROJECT_TASKS.C_MAIN_STACK, F_PROJECT_TASKS.C_MAIN_STACK_PAGES.MAIN);
            form.setFormParameter(F_PROJECT_TASKS.P_IN_EDIT_MODE, false);
        }
        else if (F_PROJECT_TASKS.AC_EDIT_PROJECT_TASK.equals(command))
        {
            form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID).clear(true);
            EJRecord editRecord = form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID).getFocusedRecord();

            editRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_PAGE_TITLE, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_PROJECT_NAME) + " (" + record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_NAME) + ") - Edit");

            if (record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_PROJECT_FIX_PRICE) != null)
            {
                editRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_MESSAGE_LABEL, "A task that belongs to a fix price project cannot be priced seperately");
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_INVOICEABLE).setEditable(false);
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_FIX_PRICE).setEditable(false);
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_PAY_RATE).setEditable(false);
            }
            else
            {
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_INVOICEABLE).setEditable(true);
                if ("Y".equals(record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE)))
                {
                    form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_FIX_PRICE).setEditable(true);
                    form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_PAY_RATE).setEditable(true);
                }
                else
                {
                    form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_FIX_PRICE).setEditable(false);
                    form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_PAY_RATE).setEditable(false);
                }
            }

            editRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_CUSTOMER_NAME, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_CUSTOMER_NAME));
            editRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_FIX_PRICE, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_FIX_PRICE));
            editRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_INVOICEABLE, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE));
            editRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_NAME, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_NAME));
            editRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_NOTES, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_NOTES));
            editRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_PAY_RATE, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_PAY_RATE));
            editRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_STATUS, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_STATUS));
            editRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_STATUS_NAME, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_STATUS_NAME));
            editRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_PROJECT_FIX_PRICE, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_PROJECT_FIX_PRICE));

            form.showStackedCanvasPage(F_PROJECT_TASKS.C_MAIN_STACK, F_PROJECT_TASKS.C_MAIN_STACK_PAGES.EDIT);
            form.setFormParameter(F_PROJECT_TASKS.P_IN_EDIT_MODE, true);
        }
        else if (F_PROJECT_TASKS.AC_EDIT_SAVE.equals(command))
        {
            clearError(form, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_NAME);
            clearError(form, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_STATUS);
            clearError(form, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_FIX_PRICE);
            clearError(form, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_PAY_RATE);

            BigDecimal projectFixPrice = (BigDecimal) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_PROJECT_FIX_PRICE);
            String taskInvoiceable = (String) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_INVOICEABLE);
            BigDecimal taskFixPrice = (BigDecimal) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_FIX_PRICE);
            BigDecimal taskRate = (BigDecimal) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_PAY_RATE);

            String name = (String) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_NAME);
            String status = (String) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_STATUS);

            boolean error = false;
            if (name == null || name.trim().length() == 0)
            {
                error = true;
                setError(form, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_NAME, "Please enter a name");
            }
            if (status == null || status.trim().length() == 0)
            {
                error = true;
                setError(form, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_STATUS, "Please choose a status");
            }
            if (projectFixPrice == null)
            {
                if (taskInvoiceable.equals("Y") && (taskFixPrice == null && taskRate == null))
                {
                    error = true;
                    setError(form, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_PAY_RATE, "Either a Fix Price or an Hourly Rate must be entered for this invoicable task");
                    setError(form, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_FIX_PRICE, "");
                }
            }
            if (error)
            {
                throw new EJActionProcessorException();
            }

            EJRecord baseRecord = form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).getFocusedRecord();

            baseRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_FIX_PRICE, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_FIX_PRICE));
            baseRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_INVOICEABLE));
            baseRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_NAME, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_NAME));
            baseRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_NOTES, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_NOTES));
            baseRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_PAY_RATE, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_PAY_RATE));
            baseRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_STATUS, record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_STATUS));

            if (record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE).equals("Y"))
            {
                baseRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE_IMAGE, "/icons/coins.png");
            }
            else
            {
                baseRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE_IMAGE, null);
            }

            ProjectTasks task = (ProjectTasks) baseRecord.getBlockServicePojo();
            task.setCustomerName((String) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_CUSTOMER_NAME));
            baseRecord.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_DISPLAY_TEXT_1, task.getDisplayText1());

            form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).updateRecord(baseRecord);
            form.saveChanges();

            form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID).clear(true);

            form.showStackedCanvasPage(F_PROJECT_TASKS.C_MAIN_STACK, F_PROJECT_TASKS.C_MAIN_STACK_PAGES.MAIN);
            form.setFormParameter(F_PROJECT_TASKS.P_IN_EDIT_MODE, false);
        }
        else if (F_PROJECT_TASKS.AC_EDIT_CANCEL.equals(command))
        {
            clearError(form, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_NAME);
            clearError(form, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_STATUS);
            clearError(form, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_FIX_PRICE);
            clearError(form, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_PAY_RATE);

            form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID).clear(true);
            form.showStackedCanvasPage(F_PROJECT_TASKS.C_MAIN_STACK, F_PROJECT_TASKS.C_MAIN_STACK_PAGES.MAIN);
            form.setFormParameter(F_PROJECT_TASKS.P_IN_EDIT_MODE, false);
        }
        else if (F_PROJECT_TASKS.AC_INSERT_CANCEL.equals(command))
        {
            clearError(form, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PROJECT_ID);
            clearError(form, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_NAME);
            clearError(form, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_NAME);
            clearError(form, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_STATUS);
            clearError(form, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_FIX_PRICE);
            clearError(form, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PAY_RATE);

            form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID).clear(true);
            form.showStackedCanvasPage(F_PROJECT_TASKS.C_MAIN_STACK, F_PROJECT_TASKS.C_MAIN_STACK_PAGES.MAIN);
            form.setFormParameter(F_PROJECT_TASKS.P_IN_EDIT_MODE, false);
        }
        else if (F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID.equals(record.getBlockName()) && F_PROJECT_TASKS.AC_INVOICEABLE_TASK.equals(command))
        {
            if (record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_INVOICEABLE).equals("Y"))
            {
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_FIX_PRICE).setEditable(true);
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PAY_RATE).setEditable(true);
            }
            else
            {
                record.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_FIX_PRICE, null);
                record.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PAY_RATE, null);

                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID).getScreenItem(screenType, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_FIX_PRICE).setEditable(false);
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID).getScreenItem(screenType, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PAY_RATE).setEditable(false);
            }
        }
        else if (F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID.equals(record.getBlockName()) && F_PROJECT_TASKS.AC_INVOICEABLE_TASK.equals(command))
        {
            if (record.getValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_INVOICEABLE).equals("Y"))
            {
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_FIX_PRICE).setEditable(true);
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_PAY_RATE).setEditable(true);
            }
            else
            {
                record.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_FIX_PRICE, null);
                record.setValue(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_PAY_RATE, null);

                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID).getScreenItem(screenType, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_FIX_PRICE).setEditable(false);
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.ID).getScreenItem(screenType, F_PROJECT_TASKS.B_PROJECT_TASK_UPDATE.I_PAY_RATE).setEditable(false);
            }
        }
        else if (F_PROJECT_TASKS.AC_QUERY_PROJECT_TASKS.equals(command))
        {
            form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).executeQuery();
        }
        else if (F_PROJECT_TASKS.AC_DELETE_PROJECT_TASK.equals(command))
        {
            form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).askToDeleteCurrentRecord("Are you sure you want to delete this task?");
        }
        else if (F_PROJECT_TASKS.AC_INSERT_PROJECT_FOR_TASK.equals(command))
        {
            BigDecimal projectFixPrice = (BigDecimal) record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_PROJECT_FIX_PRICE);

            if (projectFixPrice != null)
            {
                record.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE, "Y");
                record.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_FIX_PRICE, null);
                record.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_PAY_RATE, null);

                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_INVOICEABLE).setEditable(false);
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_FIX_PRICE).setEditable(false);
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PAY_RATE).setEditable(false);
            }
            else
            {
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_INVOICEABLE).setEditable(true);
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_FIX_PRICE).setEditable(true);
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.ID).getScreenItem(EJScreenType.MAIN, F_PROJECT_TASKS.B_PROJECT_TASK_INSERT.I_PAY_RATE).setEditable(true);
            }
        }
    }
}
