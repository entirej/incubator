package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.forms.constants.F_PROJECTS;
import org.entirej.ejinvoice.forms.invoice.InvoicePosition;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.actionprocessor.EJDefaultFormActionProcessor;
import org.entirej.framework.core.actionprocessor.interfaces.EJFormActionProcessor;
import org.entirej.framework.core.data.controllers.EJQuestion;
import org.entirej.framework.core.enumerations.EJMessageLevel;
import org.entirej.framework.core.enumerations.EJPopupButton;
import org.entirej.framework.core.enumerations.EJQuestionButton;
import org.entirej.framework.core.enumerations.EJRecordType;
import org.entirej.framework.core.enumerations.EJScreenType;

public class ProjectsActionProcessor extends EJDefaultFormActionProcessor implements EJFormActionProcessor
{
    private boolean timeEntryInserted = false;

    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
    }

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
            else
            {
                record.setValue(F_PROJECTS.B_PROJECT_TASKS.I_INVOICEABLE_IMAGE, null);
            }

        }
        else if ((EJRecordType.INSERT.equals(recordType) || EJRecordType.UPDATE.equals(recordType)) && F_PROJECTS.B_PROJECT_TASKS.ID.equals(record.getBlockName()))
        {
            BigDecimal projectFixPrice = (BigDecimal) form.getBlock(F_PROJECTS.B_PROJECTS.ID).getFocusedRecord().getValue(F_PROJECTS.B_PROJECTS.I_FIX_PRICE);
            String taskInvoiceable = (String) record.getValue(F_PROJECTS.B_PROJECT_TASKS.I_INVOICEABLE);
            BigDecimal taskFixPrice = (BigDecimal) record.getValue(F_PROJECTS.B_PROJECT_TASKS.I_FIX_PRICE);
            BigDecimal taskRate = (BigDecimal) record.getValue(F_PROJECTS.B_PROJECT_TASKS.I_PAY_RATE);

            if (projectFixPrice == null)
            {
                if (taskInvoiceable.equals("Y") && (taskFixPrice == null && taskRate == null))
                {
                    EJMessage message = new EJMessage(EJMessageLevel.ERROR, "Either a Fix Price or an Hourly Rate must be entered for this invoicable task");
                    throw new EJActionProcessorException(message);
                }
            }

            if (record.getValue(F_PROJECTS.B_PROJECT_TASKS.I_INVOICEABLE).equals("Y"))
            {
                record.setValue(F_PROJECTS.B_PROJECT_TASKS.I_INVOICEABLE_IMAGE, "/icons/coins.png");
            }
            else
            {
                record.setValue(F_PROJECTS.B_PROJECT_TASKS.I_INVOICEABLE_IMAGE, null);
            }
        }
        else if (F_PROJECTS.B_PLANNED_PROJECT_ITEMS.ID.equals(record.getBlockName()) && EJRecordType.UPDATE.equals(recordType))
        {
            Integer projectId = (Integer) record.getValue(F_PROJECTS.B_PLANNED_PROJECT_ITEMS.I_PROJECT_ID);
            Integer invpId = (Integer) record.getValue(F_PROJECTS.B_PLANNED_PROJECT_ITEMS.I_INVP_ID);
            Date periodFrom = (Date) record.getValue(F_PROJECTS.B_PLANNED_PROJECT_ITEMS.I_PERIOD_FROM);
            Date periodTo = (Date) record.getValue(F_PROJECTS.B_PLANNED_PROJECT_ITEMS.I_PERIOD_TO);

            ProjectService.validateInvoicePeriod(form, projectId, invpId, periodFrom, periodTo);
        }
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {

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
        else if (F_PROJECTS.B_PROJECT_TASKS.ID.equals(record.getBlockName()) && F_PROJECTS.AC_INVOICEABLE_TASK.equals(command))
        {
            if (record.getValue(F_PROJECTS.B_PROJECT_TASKS.I_INVOICEABLE).equals("Y"))
            {
                form.getBlock(F_PROJECTS.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_PROJECTS.B_PROJECT_TASKS.I_FIX_PRICE).setEditable(true);
                form.getBlock(F_PROJECTS.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_PROJECTS.B_PROJECT_TASKS.I_PAY_RATE).setEditable(true);
            }
            else
            {
                record.setValue(F_PROJECTS.B_PROJECT_TASKS.I_FIX_PRICE, null);
                record.setValue(F_PROJECTS.B_PROJECT_TASKS.I_PAY_RATE, null);

                form.getBlock(F_PROJECTS.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_PROJECTS.B_PROJECT_TASKS.I_FIX_PRICE).setEditable(false);
                form.getBlock(F_PROJECTS.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_PROJECTS.B_PROJECT_TASKS.I_PAY_RATE).setEditable(false);
            }
        }
        else if (F_PROJECTS.AC_PROJECT_DETAILS.equals(command))
        {
            form.showStackedCanvasPage(F_PROJECTS.C_PROJECT_STACK, F_PROJECTS.C_PROJECT_STACK_PAGES.DETAILS);
            form.getBlock(F_PROJECTS.B_PROJECTS_DETAIL.ID).gainFocus();
            form.getBlock(F_PROJECTS.B_OPEN_PROJECT_ITEMS.ID).executeQuery();
            form.getBlock(F_PROJECTS.B_PLANNED_PROJECT_ITEMS.ID).executeQuery();
        }
        else if (F_PROJECTS.AC_BACK_TO_PROJECT_OVERVIEW.equals(command))
        {
            form.showStackedCanvasPage(F_PROJECTS.C_PROJECT_STACK, F_PROJECTS.C_PROJECT_STACK_PAGES.PROJECTS);
            form.getBlock(F_PROJECTS.B_PROJECTS.ID).executeLastQuery();
        }
        else if (F_PROJECTS.AC_ADD_NEW_TASK.equals(command))
        {
            form.getBlock(F_PROJECTS.B_PROJECT_TASKS.ID).enterInsert(false);
        }
        else if (F_PROJECTS.AC_EDIT_PROJECT_TASK.equals(command))
        {
            form.getBlock(F_PROJECTS.B_PROJECT_TASKS.ID).enterUpdate();
        }
        else if (F_PROJECTS.AC_DELETE_PROJECT_TASK.equals(command))
        {
            form.getBlock(F_PROJECTS.B_PROJECT_TASKS.ID).askToDeleteCurrentRecord("Are you sure you want to delete this task?");
        }
        else if (F_PROJECTS.AC_MODIFY_PROJECT.equals(command))
        {
            form.getBlock(F_PROJECTS.B_PROJECTS.ID).enterUpdate();
        }
        else if (F_PROJECTS.AC_CREATE_NEW_PROJECT.equals(command))
        {
            form.getBlock(F_PROJECTS.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_PROJECTS.B_PROJECTS.I_STATUS).refreshItemRenderer();
            form.getBlock(F_PROJECTS.B_PROJECTS.ID).getScreenItem(EJScreenType.INSERT, F_PROJECTS.B_PROJECTS.I_TASK_STATUS).refreshItemRenderer();

            form.getBlock(F_PROJECTS.B_PROJECTS.ID).enterInsert(false);
        }
        else if (F_PROJECTS.AC_CREATE_INVOICE_POSITION.equals(command))
        {
            form.showPopupCanvas(F_PROJECTS.C_NEW_INVOICE_ITEM_POPUP);
        }
        else if (F_PROJECTS.AC_DELETE_PLANNED_ITEM.equals(command))
        {
            EJQuestion question = new EJQuestion(form, "ASK_DELETE_PLANNED_POSITION");
            question.setMessage(new EJMessage("Are you sure you want to remove this planned position?"));
            question.setButtonText(EJQuestionButton.ONE, "Yes");
            question.setButtonText(EJQuestionButton.TWO, "Cancel");
            form.askQuestion(question);
        }
        else if (F_PROJECTS.AC_DELETE_APPROVED_ITEM.equals(command))
        {
            EJQuestion question = new EJQuestion(form, "ASK_DELETE_APPROVED_POSITION");
            question.setMessage(new EJMessage("Are you sure you want to remove this approved position?"));
            question.setButtonText(EJQuestionButton.ONE, "Yes");
            question.setButtonText(EJQuestionButton.TWO, "Cancel");
            form.askQuestion(question);
        }
        else if (F_PROJECTS.AC_DELETE_MARKED_FOR_INVOICE__ITEM.equals(command))
        {
            EJQuestion question = new EJQuestion(form, "ASK_DELETE_MARKED_POSITION");
            question.setMessage(new EJMessage("Are you sure you want to remove this position from the invoice?"));
            question.setButtonText(EJQuestionButton.ONE, "Yes");
            question.setButtonText(EJQuestionButton.TWO, "Cancel");
            form.askQuestion(question);
        }
        else if (F_PROJECTS.AC_EDIT_PLANNED_ITEM.equals(command))
        {
            form.getBlock(F_PROJECTS.B_PLANNED_PROJECT_ITEMS.ID).enterUpdate();
        }
        else if (F_PROJECTS.AC_REFRESH_PROJECT_LIST.equals(command))
        {
            form.getBlock(F_PROJECTS.B_PROJECTS.ID).executeQuery();
        }
        else if (F_PROJECTS.AC_APPROVE_INV_POS.equals(command))
        {
            PlannedProjectItem projectItem = (PlannedProjectItem) record.getBlockServicePojo();
            new ProjectService().approveInvoicePosition(form, projectItem);
            form.getBlock(F_PROJECTS.B_PLANNED_PROJECT_ITEMS.ID).executeQuery();
            form.getBlock(F_PROJECTS.B_APPROVED_PROJECT_ITEMS.ID).executeQuery();
        }
        else if (F_PROJECTS.AC_ADD_TO_INVOICE.equals(command))
        {
            ApprovedProjectItem projectItem = (ApprovedProjectItem) record.getBlockServicePojo();
            new ProjectService().addPositionToInvoice(form, projectItem);
            form.getBlock(F_PROJECTS.B_APPROVED_PROJECT_ITEMS.ID).executeQuery();
            form.getBlock(F_PROJECTS.B_MARKED_FOR_INVOICE_PROJECT_ITEMS.ID).executeQuery();
        }
        
    }

    @Override
    public void questionAnswered(EJQuestion question) throws EJActionProcessorException
    {
        if (question.getName().equals("ASK_DELETE_PLANNED_POSITION") && question.getAnswer().equals(EJQuestionButton.ONE))
        {
            new ProjectService().deletePlannedPosition(question.getForm(), (PlannedProjectItem) question.getForm().getBlock(F_PROJECTS.B_PLANNED_PROJECT_ITEMS.ID).getFocusedRecord().getBlockServicePojo());
            question.getForm().getBlock(F_PROJECTS.B_PLANNED_PROJECT_ITEMS.ID).executeQuery();
            question.getForm().getBlock(F_PROJECTS.B_OPEN_PROJECT_ITEMS.ID).executeQuery();
        }
        else if (question.getName().equals("ASK_DELETE_APPROVED_POSITION") && question.getAnswer().equals(EJQuestionButton.ONE))
        {
            new ProjectService().deleteApprovedPosition(question.getForm(), (ApprovedProjectItem) question.getForm().getBlock(F_PROJECTS.B_APPROVED_PROJECT_ITEMS.ID).getFocusedRecord().getBlockServicePojo());
            question.getForm().getBlock(F_PROJECTS.B_APPROVED_PROJECT_ITEMS.ID).executeQuery();
            question.getForm().getBlock(F_PROJECTS.B_PLANNED_PROJECT_ITEMS.ID).executeQuery();
        }
        else if (question.getName().equals("ASK_DELETE_MARKED_POSITION") && question.getAnswer().equals(EJQuestionButton.ONE))
        {
            new ProjectService().deleteMarkedForInvoicedPosition(question.getForm(), (MarkedForInvoiceProjectItem) question.getForm().getBlock(F_PROJECTS.B_MARKED_FOR_INVOICE_PROJECT_ITEMS.ID).getFocusedRecord().getBlockServicePojo());
            question.getForm().getBlock(F_PROJECTS.B_APPROVED_PROJECT_ITEMS.ID).executeQuery();
            question.getForm().getBlock(F_PROJECTS.B_MARKED_FOR_INVOICE_PROJECT_ITEMS.ID).executeQuery();

        }
    }

    @Override
    public void postUpdate(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_PROJECTS.B_PROJECTS.ID.equals(record.getBlockName()))
        {
            form.getBlock(F_PROJECTS.B_PROJECTS_DETAIL.ID).executeLastQuery();
        }
    }

    @Override
    public void postFormSave(EJForm form) throws EJActionProcessorException
    {
        if (timeEntryInserted)
        {
            timeEntryInserted = false;

            form.showStackedCanvasPage(F_PROJECTS.C_PROJECT_STACK, F_PROJECTS.C_PROJECT_STACK_PAGES.DETAILS);
            form.getBlock(F_PROJECTS.B_PROJECT_TASKS.ID).executeQuery();

            EJMessage message = new EJMessage(EJMessageLevel.MESSAGE, "Before you can book time against your project you need a project task. Please enter one here before continuing.");

            form.showMessage(message);
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
        }
        else if (F_PROJECTS.B_PROJECT_TASKS.ID.equals(record.getBlockName()))
        {
            if (record.getValue(F_PROJECTS.B_PROJECT_TASKS.I_INVOICEABLE).equals("Y"))
            {
                record.setValue(F_PROJECTS.B_PROJECT_TASKS.I_INVOICEABLE_IMAGE, "/icons/coins.png");
            }
        }
    }

    @Override
    public void preOpenScreen(EJBlock block, EJRecord record, EJScreenType screenType) throws EJActionProcessorException
    {
        if ((screenType.equals(EJScreenType.INSERT) || screenType.equals(EJScreenType.UPDATE)) && F_PROJECTS.B_PROJECT_TASKS.ID.equals(block.getName()))
        {
            String message = "Each invoicable task requires either an Hourly Rate or a Fixed price, If the project is already a fixed price project then no price can be set on the project tasks";
            record.setValue(F_PROJECTS.B_PROJECT_TASKS.I_MESSAGE_LABEL, message);

            if (block.getForm().getBlock(F_PROJECTS.B_PROJECTS.ID).getFocusedRecord().getValue(F_PROJECTS.B_PROJECTS.I_FIX_PRICE) != null)
            {
                record.setValue(F_PROJECTS.B_PROJECT_TASKS.I_INVOICEABLE, "Y");
                block.getForm().getBlock(F_PROJECTS.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_PROJECTS.B_PROJECT_TASKS.I_INVOICEABLE).setEditable(false);
                block.getForm().getBlock(F_PROJECTS.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_PROJECTS.B_PROJECT_TASKS.I_FIX_PRICE).setEditable(false);
                block.getForm().getBlock(F_PROJECTS.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_PROJECTS.B_PROJECT_TASKS.I_PAY_RATE).setEditable(false);
            }
            else
            {
                block.getForm().getBlock(F_PROJECTS.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_PROJECTS.B_PROJECT_TASKS.I_INVOICEABLE).setEditable(true);
                block.getForm().getBlock(F_PROJECTS.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_PROJECTS.B_PROJECT_TASKS.I_FIX_PRICE).setEditable(true);
                block.getForm().getBlock(F_PROJECTS.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_PROJECTS.B_PROJECT_TASKS.I_PAY_RATE).setEditable(true);
            }
        }
    }

    @Override
    public void preOpenPopupCanvas(EJForm form, String popupCanvasName) throws EJActionProcessorException
    {
        if (F_PROJECTS.C_NEW_INVOICE_ITEM_POPUP.equals(popupCanvasName))
        {
            OpenProjectItem openItem = (OpenProjectItem) form.getBlock(F_PROJECTS.B_OPEN_PROJECT_ITEMS.ID).getFocusedRecord().getBlockServicePojo();

            StringBuilder builder = new StringBuilder();
            builder.append(openItem.getProjectName()).append("\n");
            builder.append(openItem.getTaskName()).append("\n");

            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, form.getCurrentLocale());
            builder.append(dateFormat.format(openItem.getTeFirstDay())).append(" - ");
            builder.append(dateFormat.format(openItem.getTeLastDay()));

            form.getBlock(F_PROJECTS.B_NEW_INVOICE_ITEM.ID).clear(true);
            form.getBlock(F_PROJECTS.B_NEW_INVOICE_ITEM.ID).getScreenItem(EJScreenType.MAIN, F_PROJECTS.B_NEW_INVOICE_ITEM.I_PERIOD_FROM).setValue(openItem.getTeFirstDay());
            form.getBlock(F_PROJECTS.B_NEW_INVOICE_ITEM.ID).getScreenItem(EJScreenType.MAIN, F_PROJECTS.B_NEW_INVOICE_ITEM.I_PERIOD_TO).setValue(openItem.getTeLastDay());
            form.getBlock(F_PROJECTS.B_NEW_INVOICE_ITEM.ID).getScreenItem(EJScreenType.MAIN, F_PROJECTS.B_NEW_INVOICE_ITEM.I_STATUS).setValue("PLANNED");
            form.getBlock(F_PROJECTS.B_NEW_INVOICE_ITEM.ID).getScreenItem(EJScreenType.MAIN, F_PROJECTS.B_NEW_INVOICE_ITEM.I_TEXT).setValue(builder.toString());
        }
    }

    @Override
    public void popupCanvasClosing(EJForm form, String popupCanvasName, EJPopupButton button) throws EJActionProcessorException
    {
        if (F_PROJECTS.C_NEW_INVOICE_ITEM_POPUP.equals(popupCanvasName) && button.equals(EJPopupButton.ONE))
        {
            Integer userId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_USER_ID).getValue();
            final int invpId = PKSequenceService.getPKSequence(form.getConnection());

            Integer projectId = (Integer) form.getBlock(F_PROJECTS.B_OPEN_PROJECT_ITEMS.ID).getFocusedRecord().getValue(F_PROJECTS.B_OPEN_PROJECT_ITEMS.I_PROJECT_ID);
            String projectName = (String) form.getBlock(F_PROJECTS.B_OPEN_PROJECT_ITEMS.ID).getFocusedRecord().getValue(F_PROJECTS.B_OPEN_PROJECT_ITEMS.I_PROJECT_NAME);
            Integer taskId = (Integer) form.getBlock(F_PROJECTS.B_OPEN_PROJECT_ITEMS.ID).getFocusedRecord().getValue(F_PROJECTS.B_OPEN_PROJECT_ITEMS.I_TASK_ID);
            String taskName = (String) form.getBlock(F_PROJECTS.B_OPEN_PROJECT_ITEMS.ID).getFocusedRecord().getValue(F_PROJECTS.B_OPEN_PROJECT_ITEMS.I_TASK_NAME);
            BigDecimal workHours = (BigDecimal) form.getBlock(F_PROJECTS.B_OPEN_PROJECT_ITEMS.ID).getFocusedRecord().getValue(F_PROJECTS.B_OPEN_PROJECT_ITEMS.I_WORK_HOURS);
            BigDecimal payRate = (BigDecimal) form.getBlock(F_PROJECTS.B_OPEN_PROJECT_ITEMS.ID).getFocusedRecord().getValue(F_PROJECTS.B_OPEN_PROJECT_ITEMS.I_PAY_RATE);

            Date periodFrom = (Date) form.getBlock(F_PROJECTS.B_NEW_INVOICE_ITEM.ID).getScreenItem(EJScreenType.MAIN, F_PROJECTS.B_NEW_INVOICE_ITEM.I_PERIOD_FROM).getValue();
            Date periodTo = (Date) form.getBlock(F_PROJECTS.B_NEW_INVOICE_ITEM.ID).getScreenItem(EJScreenType.MAIN, F_PROJECTS.B_NEW_INVOICE_ITEM.I_PERIOD_TO).getValue();
            String status = (String) form.getBlock(F_PROJECTS.B_NEW_INVOICE_ITEM.ID).getScreenItem(EJScreenType.MAIN, F_PROJECTS.B_NEW_INVOICE_ITEM.I_STATUS).getValue();
            String text = (String) form.getBlock(F_PROJECTS.B_NEW_INVOICE_ITEM.ID).getScreenItem(EJScreenType.MAIN, F_PROJECTS.B_NEW_INVOICE_ITEM.I_TEXT).getValue();

            ProjectService.validateInvoicePeriod(form, projectId, periodFrom, periodTo);

            InvoicePosition position = new InvoicePosition();

            position.setCuprId(projectId);
            position.setCuptId(taskId);
            position.setId(invpId);
            position.setUserId(userId);
            position.setPeriodFrom(periodFrom);
            position.setPeriodTo(periodTo);
            position.setStatus(status);
            position.setText(text);
            position.setProjectName(projectName);
            position.setTaskName(taskName);
            position.setHoursWorked(workHours);
            position.setPayRate(payRate);

            ProjectService.planInvoicePosition(form, position);

            form.saveChanges();
            form.getBlock(F_PROJECTS.B_OPEN_PROJECT_ITEMS.ID).executeQuery();
            form.getBlock(F_PROJECTS.B_PLANNED_PROJECT_ITEMS.ID).executeQuery();
        }
    }

    @Override
    public void tabPageChanged(EJForm form, String tabCanvasName, String tabPageName) throws EJActionProcessorException
    {
        if (F_PROJECTS.C_DETAILS_TAB.equals(tabCanvasName))
        {
            if (F_PROJECTS.C_DETAILS_TAB_PAGES.INVOICE_CREATION.equals(tabPageName))
            {
                if (form.getBlock(F_PROJECTS.B_APPROVED_PROJECT_ITEMS.ID).getBlockRecords().size() == 0)
                {
                    form.getBlock(F_PROJECTS.B_APPROVED_PROJECT_ITEMS.ID).executeQuery();
                }
                if (form.getBlock(F_PROJECTS.B_MARKED_FOR_INVOICE_PROJECT_ITEMS.ID).getBlockRecords().size() == 0)
                {
                    form.getBlock(F_PROJECTS.B_MARKED_FOR_INVOICE_PROJECT_ITEMS.ID).executeQuery();
                }
            }
            else if (F_PROJECTS.C_DETAILS_TAB_PAGES.PROJECT_TASKS.equals(tabPageName) && form.getBlock(F_PROJECTS.B_PROJECT_TASKS.ID).getBlockRecords().size() == 0)
            {
                form.getBlock(F_PROJECTS.B_PROJECT_TASKS.ID).executeQuery();
            }
        }
    }

}
