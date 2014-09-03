package org.entirej.ejinvoice.forms.masterdata;

import java.math.BigDecimal;

import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.constants.F_MASTER_DATA_VAT_RATES;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.EJScreenItem;
import org.entirej.framework.core.enumerations.EJRecordType;
import org.entirej.framework.core.enumerations.EJScreenType;

public class MasterDataVatRatesActionProcessor extends DefaultFormActionProcessor
{
    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_MASTER_DATA_VAT_RATES.AC_CREATE_VAT_RATE.equals(command))
        {
            form.getBlock(F_MASTER_DATA_VAT_RATES.B_VAT_RATES.ID).enterInsert(false);
            return;
        }
        else if (F_MASTER_DATA_VAT_RATES.AC_MODIFY_VAT_RATE.equals(command))
        {
            form.getBlock(F_MASTER_DATA_VAT_RATES.B_VAT_RATES.ID).enterUpdate();
            return;
        }
        else if (F_MASTER_DATA_VAT_RATES.AC_DELETE_VAT_RATE.equals(command))
        {
            // before deleting the selected record from database validate and
            // check if the record to be deleted has any FK constraints usage
            // with other table data and if so throw an exception and block
            // physical delete
            try
            {
                ServiceRetriever.getDBService(form).validateDeleteRecordUsage(form, form.getBlock(F_MASTER_DATA_VAT_RATES.B_VAT_RATES.ID).getFocusedRecord(), "VAT_RATES");
                form.getBlock(F_MASTER_DATA_VAT_RATES.B_VAT_RATES.ID).askToDeleteCurrentRecord("Are you sure you want to delete this rate?");
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

    @Override
    public void validateRecord(EJForm form, EJRecord record, EJRecordType recordType) throws EJActionProcessorException
    {
        // validate the vat rates screen
        if (F_MASTER_DATA_VAT_RATES.B_VAT_RATES.ID.equals(record.getBlockName()))
        {
            Object rate = record.getValue(F_MASTER_DATA_VAT_RATES.B_VAT_RATES.I_RATE);
            Object name = record.getValue(F_MASTER_DATA_VAT_RATES.B_VAT_RATES.I_NAME);
            Object id = record.getValue(F_MASTER_DATA_VAT_RATES.B_VAT_RATES.I_ID);

            final EJScreenItem vatRateItem = form.getBlock(F_MASTER_DATA_VAT_RATES.B_VAT_RATES.ID).getScreenItem(EJScreenType.MAIN, record.getItem(F_MASTER_DATA_VAT_RATES.B_VAT_RATES.I_RATE).getName());
            final String vatRateLabel = vatRateItem.getLabel();
            final EJScreenItem nameItem = form.getBlock(F_MASTER_DATA_VAT_RATES.B_VAT_RATES.ID).getScreenItem(EJScreenType.MAIN, record.getItem(F_MASTER_DATA_VAT_RATES.B_VAT_RATES.I_NAME).getName());
            final String nameLabel = nameItem.getLabel();

            if (recordType == EJRecordType.INSERT || recordType == EJRecordType.UPDATE)
            {

                if (rate == null)
                {
                    throw new EJActionProcessorException(String.format("%s cannot be Empty!", vatRateLabel));
                }
                BigDecimal vatRate = (BigDecimal) rate;
                if (vatRate.signum() == -1)
                {
                    throw new EJActionProcessorException(String.format("%s should be greater than zero!", vatRateLabel));
                }
                if (name == null || ((String) name).trim().length() == 0)
                {
                    throw new EJActionProcessorException(String.format("%s cannot be Empty!", nameLabel));
                }

                if (VATRatesService.vatRateNameExists(form, (String) name, (Integer) id))
                {
                    throw new EJActionProcessorException(String.format("Entered %s already Exist!", nameLabel));
                }
            }
        }
    }

}
