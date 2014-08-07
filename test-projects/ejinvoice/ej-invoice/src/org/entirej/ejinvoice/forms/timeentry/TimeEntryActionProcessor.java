package org.entirej.ejinvoice.forms.timeentry;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.custom.renderers.WorkWeekBlockRenderer;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.forms.constants.F_CUSTOMER;
import org.entirej.ejinvoice.forms.constants.F_INVOICE_OVERVIEW;
import org.entirej.ejinvoice.forms.constants.F_MASTER_DATA;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJParameterList;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.EJScreenItem;
import org.entirej.framework.core.data.controllers.EJFormParameter;
import org.entirej.framework.core.enumerations.EJMessageLevel;
import org.entirej.framework.core.enumerations.EJRecordType;
import org.entirej.framework.core.enumerations.EJScreenType;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;

public class TimeEntryActionProcessor extends DefaultFormActionProcessor
{
    private boolean timeEntryInserted = false;
    private boolean customerInserted  = false;
    private Integer projectId         = null;
    private Integer customerId        = null;

    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        form.getBlock(F_TIME_ENTRY.B_COMPANY.ID).executeQuery();
        form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID).executeQuery(TimeEntryBlockService.getWeeKQueryCriteria(new EJQueryCriteria(form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID)), TimeEntryBlockService.getCurrentWeek()));

        EJScreenItem startTime = form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_START_TIME);
        EJScreenItem endTime = form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_END_TIME);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        startTime.setValue(timestamp);
        endTime.setValue(timestamp);

        recalcluateWorkingHours(form, timestamp, timestamp);

        form.openEmbeddedForm(F_MASTER_DATA.ID, F_TIME_ENTRY.C_MASTER_DATA_CANVAS, null);
        form.openEmbeddedForm(F_INVOICE_OVERVIEW.ID, F_TIME_ENTRY.C_INVOICE, null);

    }

    @Override
    public void validateItem(EJForm form, EJRecord record, String itemName, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_PROJECT.equals(itemName))
        {
            form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(screenType, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_TASK).refreshItemRenderer();
        }
        else if (F_TIME_ENTRY.B_TIME_ENTRY.I_CUPR_ID.equals(itemName))
        {
            form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID).getScreenItem(screenType, F_TIME_ENTRY.B_TIME_ENTRY.I_CUPT_ID).refreshItemRenderer();
        }
        else if (F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_START_TIME.equals(itemName) || F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_END_TIME.equals(itemName))
        {
            Timestamp start = (Timestamp) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_START_TIME).getValue();
            Timestamp end = (Timestamp) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_END_TIME).getValue();

            recalcluateWorkingHours(form, start, end);
        }
        else if (F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_WORK_DATE.equals(itemName))
        {
            Date workDate = (Date) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_WORK_DATE).getValue();

            form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID).executeQuery(TimeEntryBlockService.getWeeKQueryCriteria(new EJQueryCriteria(form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID)), TimeEntryBlockService.getWeek(workDate)));
        }
        else if (F_TIME_ENTRY.B_PROJECTS.I_FIX_PRICE.equals(itemName) && EJScreenType.INSERT.equals(screenType))
        {
            BigDecimal fixPrice = (BigDecimal) record.getValue(F_TIME_ENTRY.B_PROJECTS.I_FIX_PRICE);
            if (fixPrice == null)
            {
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_PROJECTS.I_TASK_FIX_PRICE).setEditable(true);
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_PROJECTS.I_TASK_PAY_RATE).setEditable(true);
            }
            else
            {
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_PROJECTS.I_TASK_FIX_PRICE).setValue(null);
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_PROJECTS.I_TASK_PAY_RATE).setValue(null);
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_PROJECTS.I_TASK_INVOICEABLE).setValue("Y");
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_PROJECTS.I_TASK_INVOICEABLE).setEditable(false);
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_PROJECTS.I_TASK_FIX_PRICE).setEditable(false);
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_PROJECTS.I_TASK_PAY_RATE).setEditable(false);
            }
        }
    }

    @Override
    public void validateRecord(EJForm form, EJRecord record, EJRecordType recordType) throws EJActionProcessorException
    {

        if ((EJRecordType.INSERT.equals(recordType) || EJRecordType.UPDATE.equals(recordType)) && F_TIME_ENTRY.B_PROJECTS.ID.equals(record.getBlockName()))
        {
            String invoiceable = (String) record.getValue(F_TIME_ENTRY.B_PROJECTS.I_INVOICEABLE);
            Integer ccyId = (Integer) record.getValue(F_TIME_ENTRY.B_PROJECTS.I_CCY_ID);
            Integer vat = (Integer) record.getValue(F_TIME_ENTRY.B_PROJECTS.I_VAT_ID);
            
            if ("Y".equals(invoiceable))
            {
                if (ccyId == null || vat == null)
                {
                    EJMessage message = new EJMessage(EJMessageLevel.ERROR, "If a project is invoiceable then a Currency and a VAT % must be entered");
                    throw new EJActionProcessorException(message);
                }
            }
            
        }
        else if ((EJRecordType.INSERT.equals(recordType) || EJRecordType.UPDATE.equals(recordType)) && F_TIME_ENTRY.B_PROJECT_TASKS.ID.equals(record.getBlockName()))
        {
            BigDecimal projectFixPrice = (BigDecimal) form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getFocusedRecord().getValue(F_TIME_ENTRY.B_PROJECTS.I_FIX_PRICE);
            String taskInvoiceable = (String) record.getValue(F_TIME_ENTRY.B_PROJECT_TASKS.I_INVOICEABLE);
            BigDecimal taskFixPrice = (BigDecimal) record.getValue(F_TIME_ENTRY.B_PROJECT_TASKS.I_FIX_PRICE);
            BigDecimal taskRate = (BigDecimal) record.getValue(F_TIME_ENTRY.B_PROJECT_TASKS.I_PAY_RATE);

            if (projectFixPrice == null)
            {
                if (taskInvoiceable.equals("Y") && (taskFixPrice == null && taskRate == null))
                {
                    EJMessage message = new EJMessage(EJMessageLevel.ERROR, "Either a Fix Price or an Hourly Rate must be entered for this invoicable task");
                    throw new EJActionProcessorException(message);
                }
            }

            if (record.getValue(F_TIME_ENTRY.B_PROJECT_TASKS.I_INVOICEABLE).equals("Y"))
            {
                record.setValue(F_TIME_ENTRY.B_PROJECT_TASKS.I_INVOICEABLE_IMAGE, "/icons/coins.png");
            }
            else
            {
                record.setValue(F_TIME_ENTRY.B_PROJECT_TASKS.I_INVOICEABLE_IMAGE, null);
            }
        }
    }

    private String getDiffMinutesString(Timestamp start, Timestamp end)
    {
        long diff = end.getTime() - start.getTime();
        long diffHours = diff / (60 * 60 * 1000);
        long diffMinutes = (diff / (60 * 1000)) - (diffHours * 60);

        String diffMinutesString = String.format("%02d", diffMinutes);

        return diffHours + ":" + diffMinutesString;
    }

    private void recalcluateWorkingHours(EJForm form, Timestamp start, Timestamp end)
    {
        form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_HOURS).setValue(getDiffMinutesString(start, end));
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {

        if (F_TIME_ENTRY.AC_INVOICEABLE.equals(command) && EJScreenType.INSERT.equals(screenType))
        {
            if (record.getValue(F_TIME_ENTRY.B_PROJECTS.I_INVOICEABLE).equals("Y"))
            {
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_PROJECTS.I_FIX_PRICE).setEditable(true);
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_PROJECTS.I_VAT_ID).setEditable(true);
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_PROJECTS.I_TASK_FIX_PRICE).setEditable(true);
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_PROJECTS.I_TASK_PAY_RATE).setEditable(true);
            }
            else
            {
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_PROJECTS.I_FIX_PRICE).setValue(null);
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_PROJECTS.I_VAT_ID).setValue(null);
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_PROJECTS.I_TASK_FIX_PRICE).setValue(null);
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_PROJECTS.I_TASK_PAY_RATE).setValue(null);

                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_PROJECTS.I_FIX_PRICE).setEditable(false);
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_PROJECTS.I_VAT_ID).setEditable(false);
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_PROJECTS.I_TASK_FIX_PRICE).setEditable(false);
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_PROJECTS.I_TASK_PAY_RATE).setEditable(false);
            }
        }
        else if (F_TIME_ENTRY.B_PROJECT_TASKS.ID.equals(record.getBlockName()) && F_TIME_ENTRY.AC_INVOICEABLE_TASK.equals(command))
        {
            if (record.getValue(F_TIME_ENTRY.B_PROJECT_TASKS.I_INVOICEABLE).equals("Y"))
            {
                form.getBlock(F_TIME_ENTRY.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_TIME_ENTRY.B_PROJECT_TASKS.I_FIX_PRICE).setEditable(true);
                form.getBlock(F_TIME_ENTRY.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_TIME_ENTRY.B_PROJECT_TASKS.I_PAY_RATE).setEditable(true);
            }
            else
            {
                form.getBlock(F_TIME_ENTRY.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_TIME_ENTRY.B_PROJECT_TASKS.I_FIX_PRICE).setValue(null);
                form.getBlock(F_TIME_ENTRY.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_TIME_ENTRY.B_PROJECT_TASKS.I_PAY_RATE).setValue(null);

                form.getBlock(F_TIME_ENTRY.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_TIME_ENTRY.B_PROJECT_TASKS.I_FIX_PRICE).setEditable(false);
                form.getBlock(F_TIME_ENTRY.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_TIME_ENTRY.B_PROJECT_TASKS.I_PAY_RATE).setEditable(false);
            }
        }
        else if (F_TIME_ENTRY.AC_PROJECT_DETAILS.equals(command))
        {
            form.showStackedCanvasPage(F_TIME_ENTRY.C_PROJECTS_STACK, F_TIME_ENTRY.C_PROJECTS_STACK_PAGES.TASKS);

            Integer projectId = (Integer) record.getValue(F_TIME_ENTRY.B_PROJECTS.I_ID);

            EJQueryCriteria criteria = form.getBlock(F_TIME_ENTRY.B_PROJECTS_DETAIL.ID).createQueryCriteria();
            criteria.add(EJRestrictions.equals(F_TIME_ENTRY.B_PROJECTS_DETAIL.I_ID, projectId));

            form.getBlock(F_TIME_ENTRY.B_PROJECTS_DETAIL.ID).executeQuery(criteria);
        }
        else
        {
            EJBlock timeEntryBlock = form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID);
            if (F_TIME_ENTRY.AC_DELETE_TIME_ENTRY.equals(command))
            {
                timeEntryBlock.askToDeleteCurrentRecord();
                return;
            }
            else if (WorkWeekBlockRenderer.isWeekSelectionAction(command))
            {

                timeEntryBlock.executeQuery(TimeEntryBlockService.getWeeKQueryCriteria(new EJQueryCriteria(timeEntryBlock), WorkWeekBlockRenderer.getWeekSelection(command)));
                return;
            }
            else if (WorkWeekBlockRenderer.isDaySelectionAction(command))
            {

                java.util.Date daySelection = WorkWeekBlockRenderer.getDaySelection(command);
                if (daySelection != null)
                {
                    form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_WORK_DATE).setValue(new Date(daySelection.getTime()));

                }
                return;
            }
            else if (F_TIME_ENTRY.AC_BACK_TO_PROJECT_OVERVIEW.equals(command))
            {
                form.showStackedCanvasPage(F_TIME_ENTRY.C_PROJECTS_STACK, F_TIME_ENTRY.C_PROJECTS_STACK_PAGES.PROJECTS);
                form.getBlock(F_TIME_ENTRY.B_PROJECTS_DETAIL.ID).clear(true);
            }
            else if (F_TIME_ENTRY.AC_ADD_NEW_TASK.equals(command))
            {
                form.getBlock(F_TIME_ENTRY.B_PROJECT_TASKS.ID).enterInsert(false);
            }
            else if (F_TIME_ENTRY.AC_MODIFY_PROCESS.equals(command))
            {
                form.getBlock(F_TIME_ENTRY.B_PROJECT_TASKS.ID).enterUpdate();
            }
            else if (F_TIME_ENTRY.AC_DELETE_PROJECT_TASK.equals(command))
            {
                form.getBlock(F_TIME_ENTRY.B_PROJECT_TASKS.ID).askToDeleteCurrentRecord("Are you sure you want to delete this task?");
            }

            else if (F_TIME_ENTRY.AC_MODIFY_PROJECT.equals(command))
            {
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).enterUpdate();
            }
            else if (F_TIME_ENTRY.AC_CREATE_NEW_PROJECT.equals(command))
            {
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).enterInsert(false);
            }
            else if (F_TIME_ENTRY.AC_CREATE_NEW_CUSTOMER.equals(command))
            {
                form.getBlock(F_TIME_ENTRY.B_CUSTOMERS.ID).enterInsert(false);
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
            else if (F_TIME_ENTRY.AC_OPEN_CUSTOMER.equals(command))
            {
                EJParameterList paramList = new EJParameterList();
                EJFormParameter cstParam = new EJFormParameter(F_CUSTOMER.P_CST_ID, Integer.class);
                cstParam.setValue(record.getValue(F_TIME_ENTRY.B_PROJECTS.I_CUSTOMER_ID));
                paramList.addParameter(cstParam);

                form.showTabCanvasPage(F_TIME_ENTRY.C_MAIN, F_TIME_ENTRY.C_MAIN_PAGES.CUSTOMERS);
                form.showStackedCanvasPage(F_TIME_ENTRY.C_CUSTOMER_STACK, F_TIME_ENTRY.C_CUSTOMER_STACK_PAGES.CUSTOMER_DETAILS);
                form.openEmbeddedForm(F_CUSTOMER.ID, F_TIME_ENTRY.C_CUSTOMER_DETAILS_FORM, paramList);
            }
            else if (F_TIME_ENTRY.AC_ADD_TIME_ENTRY.equals(command))
            {
                Integer userId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_USER_ID).getValue();
                Timestamp start = (Timestamp) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_START_TIME).getValue();
                Timestamp end = (Timestamp) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_END_TIME).getValue();
                Date workDay = (Date) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_WORK_DATE).getValue();
                String workDescription = (String) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_NOTES).getValue();
                Integer cuptId = (Integer) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_TASK).getValue();

                int idSeqNextval = PKSequenceService.getPKSequence(form.getConnection());

                if (start.after(end))
                {
                    EJMessage message = new EJMessage(EJMessageLevel.ERROR, "Please enter an End Time later than the given Start Time");
                    form.showMessage(message);
                    return;
                }

                if (workDescription == null || workDescription.trim().length() == 0)
                {
                    EJMessage message = new EJMessage(EJMessageLevel.ERROR, "Please enter a work description");
                    form.showMessage(message);
                    return;
                }

                if (cuptId == null)
                {
                    EJMessage message = new EJMessage(EJMessageLevel.ERROR, "Please choose a project and a project task");
                    form.showMessage(message);
                    return;
                }

                TimeEntry timeEntry = new TimeEntry();

                timeEntry.setId(idSeqNextval);
                timeEntry.setUserId(userId);
                timeEntry.setCuptId(cuptId);
                timeEntry.setEndTime(new Time(end.getTime()));
                timeEntry.setStartTime(new Time(start.getTime()));
                timeEntry.setWorkDescription(workDescription);
                timeEntry.setWorkDate(workDay);

                TimeEntryBlockService service = new TimeEntryBlockService();
                service.enterTimeEntry(form, timeEntry);

                timeEntryBlock.executeLastQuery();

                form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_START_TIME).setValue(end);
                form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_NOTES).setValue(null);
                form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_NOTES).gainFocus();

                recalcluateWorkingHours(form, end, end);
            }
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
        else if (F_TIME_ENTRY.B_CUSTOMERS.ID.equals(record.getBlockName()))
        {
            customerInserted = true;
            customerId = (Integer) record.getValue(F_TIME_ENTRY.B_CUSTOMERS.I_ID);
        }
    }

    @Override
    public void postDelete(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        form.saveChanges();
    }

    @Override
    public void postFormSave(EJForm form) throws EJActionProcessorException
    {
        if (timeEntryInserted)
        {
            timeEntryInserted = false;

            EJQueryCriteria criteria = form.getBlock(F_TIME_ENTRY.B_PROJECTS_DETAIL.ID).createQueryCriteria();
            criteria.add(EJRestrictions.equals(F_TIME_ENTRY.B_PROJECTS_DETAIL.I_ID, projectId));

            form.showStackedCanvasPage(F_TIME_ENTRY.C_PROJECTS_STACK, F_TIME_ENTRY.C_PROJECTS_STACK_PAGES.TASKS);
            form.getBlock(F_TIME_ENTRY.B_PROJECTS_DETAIL.ID).executeQuery(criteria);

            EJMessage message = new EJMessage(EJMessageLevel.MESSAGE, "Before you can book time against your project you need a project process. Please enter one here before continuing.");

            form.showMessage(message);
        }
        else if (customerInserted)
        {
            EJParameterList paramList = new EJParameterList();
            EJFormParameter cstParam = new EJFormParameter(F_CUSTOMER.P_CST_ID, Integer.class);
            cstParam.setValue(customerId);
            paramList.addParameter(cstParam);

            form.showTabCanvasPage(F_TIME_ENTRY.C_MAIN, F_TIME_ENTRY.C_MAIN_PAGES.CUSTOMERS);
            form.showStackedCanvasPage(F_TIME_ENTRY.C_CUSTOMER_STACK, F_TIME_ENTRY.C_CUSTOMER_STACK_PAGES.CUSTOMER_DETAILS);
            form.openEmbeddedForm(F_CUSTOMER.ID, F_TIME_ENTRY.C_CUSTOMER_DETAILS_FORM, paramList);
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
                form.showStackedCanvasPage(F_TIME_ENTRY.C_CUSTOMER_STACK, F_TIME_ENTRY.C_CUSTOMER_STACK_PAGES.CUSTOMER_OVERVIEW);
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
            String address = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_ADDRESS);
            String zip = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_POST_CODE);
            String town = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_TOWN);
            String country = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_COUNTRY);
            String vatNr = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_VAT_NR);

            StringBuilder str = new StringBuilder();
            str.append(name);
            str.append("\n");
            if (address != null)
            {
                str.append(address);
                str.append("\n");
            }
            str.append(zip);
            str.append(" ");
            str.append(town);
            str.append(" ");
            str.append(country);
            str.append("\n");
            str.append(vatNr);

            record.setValue(F_TIME_ENTRY.B_COMPANY.I_DISPLAY_ADDRESS, str.toString());
        }
        else if (F_TIME_ENTRY.B_PROJECTS.ID.equals(record.getBlockName()))
        {
            if (record.getValue(F_TIME_ENTRY.B_PROJECTS.I_INVOICEABLE).equals("Y"))
            {
                record.setValue(F_TIME_ENTRY.B_PROJECTS.I_INVOICEABLE_ICON, "/icons/coins.png");
            }
        }
        else if (F_TIME_ENTRY.B_PROJECT_TASKS.ID.equals(record.getBlockName()))
        {
            if (record.getValue(F_TIME_ENTRY.B_PROJECT_TASKS.I_INVOICEABLE).equals("Y"))
            {
                record.setValue(F_TIME_ENTRY.B_PROJECT_TASKS.I_INVOICEABLE_IMAGE, "/icons/coins.png");
            }
        }
    }

    @Override
    public void preOpenScreen(EJBlock block, EJRecord record, EJScreenType screenType) throws EJActionProcessorException
    {
        if (screenType.equals(EJScreenType.UPDATE) && F_TIME_ENTRY.B_TIME_ENTRY.ID.equals(block.getName()))
        {
            block.getScreenItem(EJScreenType.UPDATE, F_TIME_ENTRY.B_TIME_ENTRY.I_CUPT_ID).refreshItemRenderer();
        }
        if ((screenType.equals(EJScreenType.INSERT) || screenType.equals(EJScreenType.UPDATE)) && F_TIME_ENTRY.B_PROJECT_TASKS.ID.equals(block.getName()))
        {
            String message = "Each invoicable task requires either an Hourly Rate or a Fixed price, If the project is already a fixed price project then no price can be set on the project tasks";
            record.setValue(F_TIME_ENTRY.B_PROJECT_TASKS.I_MESSAGE_LABEL, message);

            if (block.getForm().getBlock(F_TIME_ENTRY.B_PROJECTS.ID).getFocusedRecord().getValue(F_TIME_ENTRY.B_PROJECTS.I_FIX_PRICE) != null)
            {
                block.getForm().getBlock(F_TIME_ENTRY.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_TIME_ENTRY.B_PROJECT_TASKS.I_INVOICEABLE).setValue("Y");
                block.getForm().getBlock(F_TIME_ENTRY.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_TIME_ENTRY.B_PROJECT_TASKS.I_INVOICEABLE).setEditable(false);
                block.getForm().getBlock(F_TIME_ENTRY.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_TIME_ENTRY.B_PROJECT_TASKS.I_FIX_PRICE).setEditable(false);
                block.getForm().getBlock(F_TIME_ENTRY.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_TIME_ENTRY.B_PROJECT_TASKS.I_PAY_RATE).setEditable(false);
            }
            else
            {
                block.getForm().getBlock(F_TIME_ENTRY.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_TIME_ENTRY.B_PROJECT_TASKS.I_INVOICEABLE).setEditable(true);
                block.getForm().getBlock(F_TIME_ENTRY.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_TIME_ENTRY.B_PROJECT_TASKS.I_FIX_PRICE).setEditable(true);
                block.getForm().getBlock(F_TIME_ENTRY.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_TIME_ENTRY.B_PROJECT_TASKS.I_PAY_RATE).setEditable(true);
            }
        }
    }

}
