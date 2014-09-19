package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.sql.Date;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.forms.constants.F_PROJECTS;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY;
import org.entirej.ejinvoice.forms.invoice.InvoicePosition;
import org.entirej.ejinvoice.forms.invoice.InvoiceService;
import org.entirej.ejinvoice.forms.timeentry.FormHandler;
import org.entirej.framework.core.EJActionProcessorException;
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
//                record.setValue(F_PROJECTS.B_PROJECT_TASKS.I_INVOICEABLE_IMAGE, null);
            }

        }
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_PROJECTS.AC_SHOW_PROJECT_TASKS.equals(command))
        {
            Integer projectId = (Integer)record.getValue(F_PROJECTS.B_PROJECTS.I_ID);
            new FormHandler().openProjectTasks(form, "INWORK", projectId);
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
        else if (F_PROJECTS.AC_PROJECT_DETAILS_CREATION.equals(command))
        {
//            form.getForm(F_TIME_ENTRY.ID).getActionController().executeActionCommand(form, record, command, screenType);
//            StringBuilder builder = new StringBuilder();
//            builder.append((String) record.getValue(F_PROJECTS.B_PROJECTS.I_CUSTOMER_NAME));
//            builder.append(" (").append((String) record.getValue(F_PROJECTS.B_PROJECTS.I_NAME)).append(")");
//
//            form.getBlock(F_PROJECTS.B_INVOICE_HEADER.ID).getScreenItem(EJScreenType.MAIN, F_PROJECTS.B_INVOICE_HEADER.I_PROJECT_INFORMATION).setValue(builder.toString());
//
//            form.showStackedCanvasPage(F_PROJECTS.C_PROJECT_STACK, F_PROJECTS.C_PROJECT_STACK_PAGES.INVOICE);
//            form.getBlock(F_PROJECTS.B_OPEN_PROJECT_ITEMS.ID).executeQuery();
//            form.getBlock(F_PROJECTS.B_PLANNED_PROJECT_ITEMS.ID).executeQuery();
        }


//        else if (F_PROJECTS.AC_BACK_TO_PROJECT_OVERVIEW.equals(command))
//        {
//            form.showStackedCanvasPage(F_PROJECTS.C_PROJECT_STACK, F_PROJECTS.C_PROJECT_STACK_PAGES.PROJECTS);
//
//            if (!record.getBlockName().equals(F_PROJECTS.B_PROJECT_TASKS.ID))
//            {
//                form.getBlock(F_PROJECTS.B_PROJECTS.ID).executeQuery();
//            }
//        }
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

//            form.showStackedCanvasPage(F_PROJECTS.C_PROJECT_TAB, F_PROJECTS.C_PROJECT_TAB_PAGES.INVOICE__PLANNING);
//            form.getBlock(F_PROJECTS.B_PROJECT_TASKS.ID).executeQuery();

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
