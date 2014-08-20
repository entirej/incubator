package org.entirej.ejinvoice.forms.timeentry;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.custom.renderers.WorkWeekBlockRenderer;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.forms.constants.F_CUSTOMER;
import org.entirej.ejinvoice.forms.constants.F_MASTER_DATA;
import org.entirej.ejinvoice.forms.constants.F_PROJECTS;
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
import org.entirej.framework.core.enumerations.EJScreenType;
import org.entirej.framework.core.service.EJQueryCriteria;

public class TimeEntryActionProcessor extends DefaultFormActionProcessor
{
    private boolean customerInserted = false;
    private Integer customerId       = null;

    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        form.getBlock(F_TIME_ENTRY.B_COMPANY.ID).executeQuery();
        form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID).executeQuery(
                TimeEntryBlockService.getWeeKQueryCriteria(new EJQueryCriteria(form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID)),
                        TimeEntryBlockService.getCurrentWeek()));

        EJScreenItem startTime = form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN,
                F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_START_TIME);
        EJScreenItem endTime = form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_END_TIME);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        startTime.setValue(timestamp);
        endTime.setValue(timestamp);

        form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_HOURS)
                .setValue(getDiffMinutesString(timestamp.getTime(), timestamp.getTime()));

        form.openEmbeddedForm(F_MASTER_DATA.ID, F_TIME_ENTRY.C_MASTER_DATA_CANVAS, null);
        form.openEmbeddedForm(F_PROJECTS.ID, F_TIME_ENTRY.C_PROJECT_FORM, null);
    }

    @Override
    public void validateItem(EJForm form, EJRecord record, String itemName, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID.equals(record.getBlockName()))
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
                Timestamp start = (Timestamp) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID)
                        .getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_START_TIME).getValue();
                Timestamp end = (Timestamp) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID)
                        .getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_END_TIME).getValue();

                form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_HOURS)
                        .setValue(getDiffMinutesString(start.getTime(), end.getTime()));
            }
            else if (F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_WORK_DATE.equals(itemName))
            {
                Date workDate = (Date) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID)
                        .getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_WORK_DATE).getValue();

                form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID).executeQuery(
                        TimeEntryBlockService.getWeeKQueryCriteria(new EJQueryCriteria(form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID)),
                                TimeEntryBlockService.getWeek(workDate)));
            }
        }
        if (F_TIME_ENTRY.B_TIME_ENTRY.ID.equals(record.getBlockName()))
        {
            if (F_TIME_ENTRY.B_TIME_ENTRY.I_START_TIME.equals(itemName) || F_TIME_ENTRY.B_TIME_ENTRY.I_END_TIME.equals(itemName))
            {
                Time start = (Time) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID)
                        .getScreenItem(EJScreenType.UPDATE, F_TIME_ENTRY.B_TIME_ENTRY.I_START_TIME).getValue();
                Time end = (Time) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID).getScreenItem(EJScreenType.UPDATE, F_TIME_ENTRY.B_TIME_ENTRY.I_END_TIME)
                        .getValue();

                record.setValue(F_TIME_ENTRY.B_TIME_ENTRY.I_HOURS_WORKED, getDiffMinutesString(start.getTime(), end.getTime()));
            }
        }
    }

    private String getDiffMinutesString(Long start, Long end)
    {
        long diff = end - start;
        long diffHours = diff / (60 * 60 * 1000);
        long diffMinutes = (diff / (60 * 1000)) - (diffHours * 60);

        String diffMinutesString = String.format("%02d", diffMinutes);

        return diffHours + ":" + diffMinutesString;
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        EJBlock timeEntryBlock = form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID);
        if (F_TIME_ENTRY.AC_DELETE_TIME_ENTRY.equals(command))
        {
            timeEntryBlock.askToDeleteCurrentRecord();
            return;
        }
        else if (F_TIME_ENTRY.AC_EDIT_TIME_ENTRY.equals(command))
        {
            form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID).getScreenItem(EJScreenType.UPDATE, F_TIME_ENTRY.B_TIME_ENTRY.I_CUPR_ID).refreshItemRenderer();
            form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID).getScreenItem(EJScreenType.UPDATE, F_TIME_ENTRY.B_TIME_ENTRY.I_CUPT_ID).refreshItemRenderer();
            timeEntryBlock.enterUpdate();
            return;
        }
        else if (WorkWeekBlockRenderer.isWeekSelectionAction(command))
        {

            timeEntryBlock.executeQuery(TimeEntryBlockService.getWeeKQueryCriteria(new EJQueryCriteria(timeEntryBlock),
                    WorkWeekBlockRenderer.getWeekSelection(command)));
            return;
        }
        else if (WorkWeekBlockRenderer.isDaySelectionAction(command))
        {

            java.util.Date daySelection = WorkWeekBlockRenderer.getDaySelection(command);
            if (daySelection != null)
            {
                form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_WORK_DATE)
                        .setValue(new Date(daySelection.getTime()));

            }
            return;
        }
        else if (F_TIME_ENTRY.AC_CREATE_NEW_CUSTOMER.equals(command))
        {
            form.getBlock(F_TIME_ENTRY.B_CUSTOMERS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_CUSTOMERS.I_SALUTATIONS_ID).refreshItemRenderer();
            form.getBlock(F_TIME_ENTRY.B_CUSTOMERS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_CUSTOMERS.I_CONTACT_TYPES_ID).refreshItemRenderer();
            form.getBlock(F_TIME_ENTRY.B_CUSTOMERS.ID).enterInsert(false);
        }
        else if (F_TIME_ENTRY.AC_EDIT_CUSTOMER.equals(command))
        {
            form.getBlock(F_TIME_ENTRY.B_CUSTOMERS.ID).enterUpdate();
        }
        else if (F_TIME_ENTRY.AC_SHOW_CUSTOMER_DETAILS.equals(command))
        {
            EJParameterList paramList = new EJParameterList();
            EJFormParameter cstParam = new EJFormParameter(F_CUSTOMER.P_CST_ID, Integer.class);
            cstParam.setValue(record.getValue(F_TIME_ENTRY.B_CUSTOMERS.I_ID));
            
            EJFormParameter cstInfoParam = new EJFormParameter(F_CUSTOMER.P_CUSTOMER_INFORMATION, String.class);
            cstInfoParam.setValue("Customer: "+record.getValue(F_TIME_ENTRY.B_CUSTOMERS.I_CUSTOMER_NUMBER)+" ("+record.getValue(F_TIME_ENTRY.B_CUSTOMERS.I_NAME)+")");
            
            paramList.addParameter(cstParam);
            paramList.addParameter(cstInfoParam);

            form.showStackedCanvasPage(F_TIME_ENTRY.C_CUSTOMER_STACK, F_TIME_ENTRY.C_CUSTOMER_STACK_PAGES.CUSTOMER_DETAILS);
            form.openEmbeddedForm(F_CUSTOMER.ID, F_TIME_ENTRY.C_CUSTOMER_DETAILS_FORM, paramList);
        }
        else if (F_TIME_ENTRY.AC_ADD_TIME_ENTRY.equals(command))
        {
            Integer userId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_USER_ID).getValue();
            Timestamp start = (Timestamp) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID)
                    .getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_START_TIME).getValue();
            Timestamp end = (Timestamp) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID)
                    .getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_END_TIME).getValue();
            Date workDay = (Date) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID)
                    .getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_WORK_DATE).getValue();
            String workDescription = (String) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID)
                    .getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_NOTES).getValue();
            Integer cuptId = (Integer) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID)
                    .getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_TASK).getValue();

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

            Integer companyId = (Integer)form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
            TimeEntry timeEntry = new TimeEntry();

            timeEntry.setId(idSeqNextval);
            timeEntry.setUserId(userId);
            timeEntry.setCuptId(cuptId);
            timeEntry.setEndTime(new Time(end.getTime()));
            timeEntry.setStartTime(new Time(start.getTime()));
            timeEntry.setWorkDescription(workDescription);
            timeEntry.setWorkDate(workDay);
            timeEntry.setCompanyId(companyId);

            TimeEntryBlockService service = new TimeEntryBlockService();
            service.enterTimeEntry(form, timeEntry);

            timeEntryBlock.executeLastQuery();

            form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_START_TIME).setValue(end);
            form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_NOTES).setValue(null);
            form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_NOTES).gainFocus();

            form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_HOURS)
                    .setValue(getDiffMinutesString(end.getTime(), end.getTime()));

        }
    }

    @Override
    public void postInsert(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_TIME_ENTRY.B_CUSTOMERS.ID.equals(record.getBlockName()))
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
        if (customerInserted)
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
            if (F_TIME_ENTRY.C_MAIN_PAGES.CUSTOMERS.equals(tabPageName))
            {
                form.showStackedCanvasPage(F_TIME_ENTRY.C_CUSTOMER_STACK, F_TIME_ENTRY.C_CUSTOMER_STACK_PAGES.CUSTOMER_OVERVIEW);
                form.getBlock(F_TIME_ENTRY.B_CUSTOMERS.ID).executeQuery();
            }
            else if (F_TIME_ENTRY.C_MAIN_PAGES.PROJECTS.equals(tabPageName))
            {
                EJForm projectForm = form.getEmbeddedForm(F_PROJECTS.ID, F_TIME_ENTRY.C_PROJECT_FORM);
                if (projectForm.getDisplayedStackedCanvasPage(F_PROJECTS.C_PROJECT_STACK).equals(F_PROJECTS.C_PROJECT_STACK_PAGES.PROJECTS))
                {
                    projectForm.getBlock(F_PROJECTS.B_PROJECTS.ID).executeQuery();
                }
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
        if (F_TIME_ENTRY.B_TIME_ENTRY.ID.equals(record.getBlockName()))
        {
            if (record.getValue(F_TIME_ENTRY.B_TIME_ENTRY.I_INVP_ID) == null)
            {
                record.setValue(F_TIME_ENTRY.B_TIME_ENTRY.I__EDIT, "/icons/edit10.gif");
                record.setValue(F_TIME_ENTRY.B_TIME_ENTRY.I__DELETE, "/icons/delete10.png");
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
    }

}
