package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;

import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.forms.constants.F_PROJECT_TASKS;
import org.entirej.framework.core.EJActionProcessorException;
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
            Integer cprId = (Integer)record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_CPR_ID);
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

        if (F_PROJECT_TASKS.B_PROJECT_TASKS.ID.equals(record.getBlockName()) && F_PROJECT_TASKS.AC_INVOICEABLE_TASK.equals(command))
        {
            if (record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE).equals("Y"))
            {
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_PROJECT_TASKS.B_PROJECT_TASKS.I_FIX_PRICE).setEditable(true);
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_PROJECT_TASKS.B_PROJECT_TASKS.I_PAY_RATE).setEditable(true);
            }
            else
            {
                record.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_FIX_PRICE, null);
                record.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_PAY_RATE, null);

                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_PROJECT_TASKS.B_PROJECT_TASKS.I_FIX_PRICE).setEditable(false);
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_PROJECT_TASKS.B_PROJECT_TASKS.I_PAY_RATE).setEditable(false);
            }
        }
        else if (F_PROJECT_TASKS.AC_QUERY_PROJECT_TASKS.equals(command))
        {
            form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).executeQuery();
        }
        else if (F_PROJECT_TASKS.AC_ADD_NEW_TASK.equals(command))
        {
            form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).enterInsert(false);
        }
        else if (F_PROJECT_TASKS.AC_EDIT_PROJECT_TASK.equals(command))
        {
            form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).enterUpdate();
        }
        else if (F_PROJECT_TASKS.AC_DELETE_PROJECT_TASK.equals(command))
        {
            form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).askToDeleteCurrentRecord("Are you sure you want to delete this task?");
        }
        else if (F_PROJECT_TASKS.AC_INSERT_PROJECT_FOR_TASK.equals(command))
        {
            BigDecimal projectFixPrice = (BigDecimal)record.getValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_PROJECT_FIX_PRICE);
            
            if (projectFixPrice != null)
            {
                record.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE, "Y");
                record.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_FIX_PRICE, null);
                record.setValue(F_PROJECT_TASKS.B_PROJECT_TASKS.I_PAY_RATE, null);
                
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE).setEditable(false);
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_PROJECT_TASKS.B_PROJECT_TASKS.I_FIX_PRICE).setEditable(false);
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_PROJECT_TASKS.B_PROJECT_TASKS.I_PAY_RATE).setEditable(false);
            }
            else
            {
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_PROJECT_TASKS.B_PROJECT_TASKS.I_INVOICEABLE).setEditable(true);
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_PROJECT_TASKS.B_PROJECT_TASKS.I_FIX_PRICE).setEditable(true);
                form.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).getScreenItem(screenType, F_PROJECT_TASKS.B_PROJECT_TASKS.I_PAY_RATE).setEditable(true);
            }
        }
    }
}
