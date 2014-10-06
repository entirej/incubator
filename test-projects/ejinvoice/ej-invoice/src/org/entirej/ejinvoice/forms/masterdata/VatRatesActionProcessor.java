package org.entirej.ejinvoice.forms.masterdata;

import java.math.BigDecimal;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.constants.F_VAT_RATES;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.enumerations.EJScreenType;

public class VatRatesActionProcessor extends DefaultFormActionProcessor
{
    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_VAT_RATES.AC_CREATE_VAT_RATE.equals(command))
        {
            form.getBlock(F_VAT_RATES.B_VAT_RATES_INSERT.ID).clear(true);
            EJRecord insertRecord = form.getBlock(F_VAT_RATES.B_VAT_RATES_INSERT.ID).getFocusedRecord();

            insertRecord.setValue(F_VAT_RATES.B_VAT_RATES_INSERT.I_PAGE_TITLE, "Create a new VAT rate");
            form.showStackedCanvasPage(F_VAT_RATES.C_MAIN_STACK, F_VAT_RATES.C_MAIN_STACK_PAGES.INSERT);
            
            form.setFormParameter(F_VAT_RATES.P_IN_EDIT_MODE, true);
        }
        else if (F_VAT_RATES.AC_INSERT_SAVE.equals(command))
        {
            clearError(form, F_VAT_RATES.B_VAT_RATES_INSERT.ID, F_VAT_RATES.B_VAT_RATES_INSERT.I_RATE);
            clearError(form, F_VAT_RATES.B_VAT_RATES_INSERT.ID, F_VAT_RATES.B_VAT_RATES_INSERT.I_NAME);

            BigDecimal rate = (BigDecimal) record.getValue(F_VAT_RATES.B_VAT_RATES_INSERT.I_RATE);
            String name = (String) record.getValue(F_VAT_RATES.B_VAT_RATES_INSERT.I_NAME);

            boolean error = false;
            if (rate == null)
            {
                error = true;
                setError(form, F_VAT_RATES.B_VAT_RATES_INSERT.ID, F_VAT_RATES.B_VAT_RATES_INSERT.I_RATE, "Please enter a salutation");
            }
            if (rate != null && rate.signum() == -1)
            {
                error = true;
                setError(form, F_VAT_RATES.B_VAT_RATES_INSERT.ID, F_VAT_RATES.B_VAT_RATES_INSERT.I_NAME, "Please enter a rate greater than zero");
            }
            if (name == null || name.trim().length() == 0)
            {
                error = true;
                setError(form, F_VAT_RATES.B_VAT_RATES_INSERT.ID, F_VAT_RATES.B_VAT_RATES_INSERT.I_NAME, "Please enter a name for this rate");
            }
            if (name != null && SalutationService.salutationExists(form, name, null))
            {
                error = true;
                setError(form, F_VAT_RATES.B_VAT_RATES_INSERT.ID, F_VAT_RATES.B_VAT_RATES_INSERT.I_NAME, "There is already a rate with this name");
            }
            if (error)
            {
                throw new EJApplicationException();
            }
            Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
            EJRecord newRecord = form.getBlock(F_VAT_RATES.B_VAT_RATES.ID).createRecord();

            int idSeqNextval = PKSequenceService.getPKSequence(form.getConnection());
            newRecord.setValue(F_VAT_RATES.B_VAT_RATES.I_ID, idSeqNextval);
            newRecord.setValue(F_VAT_RATES.B_VAT_RATES.I_COMPANY_ID, companyId);
            newRecord.setValue(F_VAT_RATES.B_VAT_RATES.I_NAME, record.getValue(F_VAT_RATES.B_VAT_RATES.I_NAME));
            newRecord.setValue(F_VAT_RATES.B_VAT_RATES.I_RATE, record.getValue(F_VAT_RATES.B_VAT_RATES.I_RATE));
            newRecord.setValue(F_VAT_RATES.B_VAT_RATES.I_NOTES, record.getValue(F_VAT_RATES.B_VAT_RATES.I_NOTES));

            VatRate vatRate = (VatRate) newRecord.getBlockServicePojo();
            newRecord.setValue(F_VAT_RATES.B_VAT_RATES.I_DISPLAY_TEXT, vatRate.getDisplayText());

            form.getBlock(F_VAT_RATES.B_VAT_RATES.ID).insertRecord(newRecord);
            form.saveChanges();
            form.getBlock(F_VAT_RATES.B_VAT_RATES_INSERT.ID).clear(true);
            form.showStackedCanvasPage(F_VAT_RATES.C_MAIN_STACK, F_VAT_RATES.C_MAIN_STACK_PAGES.MAIN);
            form.setFormParameter(F_VAT_RATES.P_IN_EDIT_MODE, false);
        }
        else if (F_VAT_RATES.AC_INSERT_CANCEL.equals(command))
        {
            clearError(form, F_VAT_RATES.B_VAT_RATES_INSERT.ID, F_VAT_RATES.B_VAT_RATES_INSERT.I_RATE);
            clearError(form, F_VAT_RATES.B_VAT_RATES_INSERT.ID, F_VAT_RATES.B_VAT_RATES_INSERT.I_NAME);

            form.getBlock(F_VAT_RATES.B_VAT_RATES_INSERT.ID).clear(true);
            form.showStackedCanvasPage(F_VAT_RATES.C_MAIN_STACK, F_VAT_RATES.C_MAIN_STACK_PAGES.MAIN);
            form.setFormParameter(F_VAT_RATES.P_IN_EDIT_MODE, false);
        }
        else if (F_VAT_RATES.AC_MODIFY_VAT_RATE.equals(command))
        {
            form.getBlock(F_VAT_RATES.B_VAT_RATES_EDIT.ID).clear(true);
            EJRecord editRecord = form.getBlock(F_VAT_RATES.B_VAT_RATES_EDIT.ID).getFocusedRecord();

            editRecord.setValue(F_VAT_RATES.B_VAT_RATES_EDIT.I_PAGE_TITLE, record.getValue(F_VAT_RATES.B_VAT_RATES.I_NAME) +" : " + record.getValue(F_VAT_RATES.B_VAT_RATES.I_RATE) + "% - Edit");
            editRecord.setValue(F_VAT_RATES.B_VAT_RATES_EDIT.I_ID, record.getValue(F_VAT_RATES.B_VAT_RATES.I_ID));
            editRecord.setValue(F_VAT_RATES.B_VAT_RATES_EDIT.I_RATE, record.getValue(F_VAT_RATES.B_VAT_RATES.I_RATE));
            editRecord.setValue(F_VAT_RATES.B_VAT_RATES_EDIT.I_NAME, record.getValue(F_VAT_RATES.B_VAT_RATES.I_NAME));
            editRecord.setValue(F_VAT_RATES.B_VAT_RATES_EDIT.I_NOTES, record.getValue(F_VAT_RATES.B_VAT_RATES.I_NOTES));

            form.showStackedCanvasPage(F_VAT_RATES.C_MAIN_STACK, F_VAT_RATES.C_MAIN_STACK_PAGES.EDIT);
            form.setFormParameter(F_VAT_RATES.P_IN_EDIT_MODE, true);
        }
        else if (F_VAT_RATES.AC_EDIT_SAVE.equals(command))
        {
            clearError(form, F_VAT_RATES.B_VAT_RATES_EDIT.ID, F_VAT_RATES.B_VAT_RATES_EDIT.I_RATE);
            clearError(form, F_VAT_RATES.B_VAT_RATES_EDIT.ID, F_VAT_RATES.B_VAT_RATES_EDIT.I_NAME);

            BigDecimal rate = (BigDecimal) record.getValue(F_VAT_RATES.B_VAT_RATES_EDIT.I_RATE);
            String name = (String) record.getValue(F_VAT_RATES.B_VAT_RATES_EDIT.I_NAME);
            Integer id = (Integer) record.getValue(F_VAT_RATES.B_VAT_RATES_EDIT.I_ID);

            boolean error = false;
            if (rate == null)
            {
                error = true;
                setError(form, F_VAT_RATES.B_VAT_RATES_EDIT.ID, F_VAT_RATES.B_VAT_RATES_EDIT.I_RATE, "Please enter a VAT rate");
            }
            if (name == null || name.trim().length() == 0)
            {
                error = true;
                setError(form, F_VAT_RATES.B_VAT_RATES_EDIT.ID, F_VAT_RATES.B_VAT_RATES_EDIT.I_NAME, "Please enter a name for this rate");
            }
            if (rate != null && rate.signum() == -1)
            {
                error = true;
                setError(form, F_VAT_RATES.B_VAT_RATES_INSERT.ID, F_VAT_RATES.B_VAT_RATES_INSERT.I_NAME, "Please enter a rate greater than zero");
            }
            if (name != null && VATRatesService.vatRateNameExists(form, name, id))
            {
                error = true;
                setError(form, F_VAT_RATES.B_VAT_RATES_EDIT.ID, F_VAT_RATES.B_VAT_RATES_EDIT.I_NAME, "There is already a rate with this name");
            }
            if (error)
            {
                throw new EJActionProcessorException();
            }
            EJRecord baseRecord = form.getBlock(F_VAT_RATES.B_VAT_RATES.ID).getFocusedRecord();

            baseRecord.setValue(F_VAT_RATES.B_VAT_RATES.I_RATE, record.getValue(F_VAT_RATES.B_VAT_RATES_EDIT.I_RATE));
            baseRecord.setValue(F_VAT_RATES.B_VAT_RATES.I_NAME, record.getValue(F_VAT_RATES.B_VAT_RATES_EDIT.I_NAME));
            baseRecord.setValue(F_VAT_RATES.B_VAT_RATES.I_NOTES, record.getValue(F_VAT_RATES.B_VAT_RATES_EDIT.I_NOTES));

            VatRate vatRate = (VatRate) baseRecord.getBlockServicePojo();
            baseRecord.setValue(F_VAT_RATES.B_VAT_RATES.I_DISPLAY_TEXT, vatRate.getDisplayText());

            form.getBlock(F_VAT_RATES.B_VAT_RATES.ID).updateRecord(baseRecord);
            form.saveChanges();

            form.getBlock(F_VAT_RATES.B_VAT_RATES_EDIT.ID).clear(true);

            form.showStackedCanvasPage(F_VAT_RATES.C_MAIN_STACK, F_VAT_RATES.C_MAIN_STACK_PAGES.MAIN);
            form.setFormParameter(F_VAT_RATES.P_IN_EDIT_MODE, false);
        }
        else if (F_VAT_RATES.AC_EDIT_CANCEL.equals(command))
        {
            clearError(form, F_VAT_RATES.B_VAT_RATES_EDIT.ID, F_VAT_RATES.B_VAT_RATES_EDIT.I_RATE);
            clearError(form, F_VAT_RATES.B_VAT_RATES_EDIT.ID, F_VAT_RATES.B_VAT_RATES_EDIT.I_NAME);

            form.getBlock(F_VAT_RATES.B_VAT_RATES_EDIT.ID).clear(true);
            form.showStackedCanvasPage(F_VAT_RATES.C_MAIN_STACK, F_VAT_RATES.C_MAIN_STACK_PAGES.MAIN);
            form.setFormParameter(F_VAT_RATES.P_IN_EDIT_MODE, false);
        }
        else if (F_VAT_RATES.AC_MODIFY_VAT_RATE.equals(command))
        {
            form.getBlock(F_VAT_RATES.B_VAT_RATES.ID).enterUpdate();
            return;
        }
        else if (F_VAT_RATES.AC_DELETE_VAT_RATE.equals(command))
        {
            // before deleting the selected record from database validate and
            // check if the record to be deleted has any FK constraints usage
            // with other table data and if so throw an exception and block
            // physical delete
            try
            {
                ServiceRetriever.getDBService(form).validateDeleteRecordUsage(form, form.getBlock(F_VAT_RATES.B_VAT_RATES.ID).getFocusedRecord(), "VAT_RATES");
                form.getBlock(F_VAT_RATES.B_VAT_RATES.ID).askToDeleteCurrentRecord("Are you sure you want to delete this rate?");
            }
            catch (Exception e)
            {
                form.handleException(e);
            }
        }
    }

    @Override
    public void postDelete(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        super.postDelete(form, record);
        form.saveChanges();
    }

}
