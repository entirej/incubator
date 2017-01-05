package org.entirej.ejinvoice.forms.invoice;

import java.math.BigDecimal;
import java.sql.Date;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.forms.constants.F_INVOICE_PLANNING;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY;
import org.entirej.ejinvoice.forms.projects.OpenProjectItem;
import org.entirej.ejinvoice.forms.projects.PlannedProjectItem;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.data.controllers.EJQuestion;
import org.entirej.framework.core.enumerations.EJPopupButton;
import org.entirej.framework.core.enumerations.EJQuestionButton;
import org.entirej.framework.core.enumerations.EJRecordType;
import org.entirej.framework.core.enumerations.EJScreenType;

public class InvoicePlanningActionProcessor extends DefaultFormActionProcessor
{
    private boolean    refreshProjectLists    = false;

    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
    }

    @Override
    public void validateRecord(EJForm form, EJRecord record, EJRecordType recordType) throws EJActionProcessorException
    {
        if (F_INVOICE_PLANNING.B_PLANNED_PROJECT_ITEMS.ID.equals(record.getBlockName()) && EJRecordType.UPDATE.equals(recordType))
        {
            Integer projectId = (Integer) record.getValue(F_INVOICE_PLANNING.B_PLANNED_PROJECT_ITEMS.I_PROJECT_ID);
            Integer invpId = (Integer) record.getValue(F_INVOICE_PLANNING.B_PLANNED_PROJECT_ITEMS.I_INVP_ID);
            Date periodFrom = (Date) record.getValue(F_INVOICE_PLANNING.B_PLANNED_PROJECT_ITEMS.I_PERIOD_FROM);
            Date periodTo = (Date) record.getValue(F_INVOICE_PLANNING.B_PLANNED_PROJECT_ITEMS.I_PERIOD_TO);

            InvoiceService.validateInvoicePeriod(form, projectId, invpId, periodFrom, periodTo);
        }
    }

    @Override
    public void executeActionCommand(EJForm form, String blockName, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        EJRecord record = form.getBlock(blockName).getFocusedRecord();
        
        if (F_INVOICE_PLANNING.AC_REFRESH_BLOCKS.equals(command))
        {
            if (record.getValue(F_INVOICE_PLANNING.B_INVOICE_HEADER.I_CUSTOMER_ID) != null)
            {
                form.getBlock(F_INVOICE_PLANNING.B_PLANNED_PROJECT_ITEMS.ID).executeQuery();
                form.getBlock(F_INVOICE_PLANNING.B_OPEN_PROJECT_ITEMS.ID).executeQuery();
            }
            
//            if (record.getValue(F_INVOICE_PLANNING.B_INVOICE_HEADER.I_PROJECTS) == null)
//            {
//                form.getBlock(F_INVOICE_PLANNING.B_OPEN_PROJECT_ITEMS.ID).clear(true);
//            }
//            else
//            {
//                
//            }
        }
        if (F_INVOICE_PLANNING.AC_CREATE_INVOICE_POSITION.equals(command))
        {
            form.showPopupCanvas(F_INVOICE_PLANNING.C_NEW_INVOICE_ITEM_POPUP);
        }
        else if (F_INVOICE_PLANNING.AC_DELETE_PLANNED_ITEM.equals(command))
        {
            EJQuestion question = new EJQuestion(form, "ASK_DELETE_PLANNED_POSITION");
            question.setMessage(new EJMessage("Are you sure you want to remove this planned position?"));
            question.setButtonText(EJQuestionButton.ONE, "Yes");
            question.setButtonText(EJQuestionButton.TWO, "Cancel");
            form.askQuestion(question);
        }
        else if (F_INVOICE_PLANNING.AC_EDIT_PLANNED_ITEM.equals(command))
        {
            form.getBlock(F_INVOICE_PLANNING.B_PLANNED_PROJECT_ITEMS.ID).enterUpdate();
        }
        else if (F_INVOICE_PLANNING.AC_APPROVE_INV_POS.equals(command))
        {
            PlannedProjectItem projectItem = (PlannedProjectItem) record.getBlockServicePojo();
            new InvoiceService().approveInvoicePosition(form, projectItem);
            form.getBlock(F_INVOICE_PLANNING.B_PLANNED_PROJECT_ITEMS.ID).executeQuery();
        }
    }

    @Override
    public void questionAnswered(EJQuestion question) throws EJActionProcessorException
    {
        if (question.getName().equals("ASK_DELETE_PLANNED_POSITION") && question.getAnswer().equals(EJQuestionButton.ONE))
        {
            new InvoiceService().deletePlannedPosition(question.getForm(), (PlannedProjectItem) question.getForm().getBlock(F_INVOICE_PLANNING.B_PLANNED_PROJECT_ITEMS.ID).getFocusedRecord().getBlockServicePojo());
            question.getForm().getBlock(F_INVOICE_PLANNING.B_PLANNED_PROJECT_ITEMS.ID).executeQuery();
            question.getForm().getBlock(F_INVOICE_PLANNING.B_OPEN_PROJECT_ITEMS.ID).executeQuery();
        }
    }

    @Override
    public void postFormSave(EJForm form) throws EJActionProcessorException
    {
        if (refreshProjectLists)
        {
            refreshProjectLists = false;
            form.getForm(F_TIME_ENTRY.ID).getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_PROJECT).refreshItemRenderer();
        }
    }

    @Override
    public void preOpenPopupCanvas(EJForm form, String popupCanvasName) throws EJActionProcessorException
    {
        if (F_INVOICE_PLANNING.C_NEW_INVOICE_ITEM_POPUP.equals(popupCanvasName))
        {
            OpenProjectItem openItem = (OpenProjectItem) form.getBlock(F_INVOICE_PLANNING.B_OPEN_PROJECT_ITEMS.ID).getFocusedRecord().getBlockServicePojo();

            StringBuilder builder = new StringBuilder();
            builder.append(openItem.getProjectName()).append("\n");
            builder.append(openItem.getTaskName()).append("\n");

            form.getBlock(F_INVOICE_PLANNING.B_NEW_INVOICE_ITEM.ID).clear(true);
            form.getBlock(F_INVOICE_PLANNING.B_NEW_INVOICE_ITEM.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_PLANNING.B_NEW_INVOICE_ITEM.I_PERIOD_FROM).setValue(openItem.getTeFirstDay());
            form.getBlock(F_INVOICE_PLANNING.B_NEW_INVOICE_ITEM.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_PLANNING.B_NEW_INVOICE_ITEM.I_PERIOD_TO).setValue(openItem.getTeLastDay());
            form.getBlock(F_INVOICE_PLANNING.B_NEW_INVOICE_ITEM.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_PLANNING.B_NEW_INVOICE_ITEM.I_TEXT).setValue(builder.toString());
        }
    }

    @Override
    public void popupCanvasClosing(EJForm form, String popupCanvasName, EJPopupButton button) throws EJActionProcessorException
    {
        if (F_INVOICE_PLANNING.C_NEW_INVOICE_ITEM_POPUP.equals(popupCanvasName) && button.equals(EJPopupButton.ONE))
        {
            Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
            final int invpId = PKSequenceService.getPKSequence(form.getConnection());

            Integer projectId = (Integer) form.getBlock(F_INVOICE_PLANNING.B_OPEN_PROJECT_ITEMS.ID).getFocusedRecord().getValue(F_INVOICE_PLANNING.B_OPEN_PROJECT_ITEMS.I_PROJECT_ID);
            String projectName = (String) form.getBlock(F_INVOICE_PLANNING.B_OPEN_PROJECT_ITEMS.ID).getFocusedRecord().getValue(F_INVOICE_PLANNING.B_OPEN_PROJECT_ITEMS.I_PROJECT_NAME);
            Integer taskId = (Integer) form.getBlock(F_INVOICE_PLANNING.B_OPEN_PROJECT_ITEMS.ID).getFocusedRecord().getValue(F_INVOICE_PLANNING.B_OPEN_PROJECT_ITEMS.I_TASK_ID);
            String taskName = (String) form.getBlock(F_INVOICE_PLANNING.B_OPEN_PROJECT_ITEMS.ID).getFocusedRecord().getValue(F_INVOICE_PLANNING.B_OPEN_PROJECT_ITEMS.I_TASK_NAME);
            BigDecimal workHours = (BigDecimal) form.getBlock(F_INVOICE_PLANNING.B_OPEN_PROJECT_ITEMS.ID).getFocusedRecord().getValue(F_INVOICE_PLANNING.B_OPEN_PROJECT_ITEMS.I_WORK_HOURS);
            BigDecimal payRate = (BigDecimal) form.getBlock(F_INVOICE_PLANNING.B_OPEN_PROJECT_ITEMS.ID).getFocusedRecord().getValue(F_INVOICE_PLANNING.B_OPEN_PROJECT_ITEMS.I_PAY_RATE);

            Date periodFrom = (Date) form.getBlock(F_INVOICE_PLANNING.B_NEW_INVOICE_ITEM.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_PLANNING.B_NEW_INVOICE_ITEM.I_PERIOD_FROM).getValue();
            Date periodTo = (Date) form.getBlock(F_INVOICE_PLANNING.B_NEW_INVOICE_ITEM.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_PLANNING.B_NEW_INVOICE_ITEM.I_PERIOD_TO).getValue();
            String status = "PLANNED";
            String text = (String) form.getBlock(F_INVOICE_PLANNING.B_NEW_INVOICE_ITEM.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_PLANNING.B_NEW_INVOICE_ITEM.I_TEXT).getValue();

            InvoiceService.validateInvoicePeriod(form, projectId, taskId, periodFrom, periodTo);

            InvoicePosition position = new InvoicePosition();

            position.setCuprId(projectId);
            position.setCuptId(taskId);
            position.setId(invpId);
            position.setCompanyId(companyId);
            position.setPeriodFrom(periodFrom);
            position.setPeriodTo(periodTo);
            position.setStatus(status);
            position.setText(text);
            position.setProjectName(projectName);
            position.setTaskName(taskName);
            position.setHoursWorked(workHours);
            position.setPayRate(payRate);

            InvoiceService.planInvoicePosition(form, position);

            form.saveChanges();
            form.getBlock(F_INVOICE_PLANNING.B_OPEN_PROJECT_ITEMS.ID).executeQuery();
            form.getBlock(F_INVOICE_PLANNING.B_PLANNED_PROJECT_ITEMS.ID).executeQuery();
        }
    }

}
