package org.entirej.ejinvoice.forms.masterdata;

import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.constants.F_SALUTATIONS;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.EJScreenItem;
import org.entirej.framework.core.enumerations.EJRecordType;
import org.entirej.framework.core.enumerations.EJScreenType;

public class MasterDataSalutationsActionProcessor extends DefaultFormActionProcessor
{
    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_SALUTATIONS.AC_CREATE_SALUTATION.equals(command))
        {
            form.getBlock(F_SALUTATIONS.B_SALUTATIONS.ID).enterInsert(false);
        }
        else if (F_SALUTATIONS.AC_MODIFY_SALUTATION.equals(command))
        {
            form.getBlock(F_SALUTATIONS.B_SALUTATIONS.ID).enterUpdate();
        }
        else if (F_SALUTATIONS.AC_DELETE_SALUTATION.equals(command))
        {
            // before deleting the selected record from database validate and
            // check if the record to be deleted has any FK constraints usage
            // with other table data and if so throw an exception and block
            // physical delete
            try
            {
                ServiceRetriever.getDBService(form).validateDeleteRecordUsage(form, form.getBlock(F_SALUTATIONS.B_SALUTATIONS.ID).getFocusedRecord(), "SALUTATIONS");
                form.getBlock(F_SALUTATIONS.B_SALUTATIONS.ID).askToDeleteCurrentRecord("Are you sure you want to delete this salutation?");
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
        // validate the salutations screen
        if (F_SALUTATIONS.B_SALUTATIONS.ID.equals(record.getBlockName()))
        {
            Object value = record.getValue(F_SALUTATIONS.B_SALUTATIONS.I_VALUE);
            Object id = record.getValue(F_SALUTATIONS.B_SALUTATIONS.I_ID);
            final EJScreenItem screenItem = form.getBlock(F_SALUTATIONS.B_SALUTATIONS.ID).getScreenItem(EJScreenType.MAIN, record.getItem(F_SALUTATIONS.B_SALUTATIONS.I_VALUE).getName());
            final String salutationLabel = screenItem.getLabel();

            if (recordType == EJRecordType.INSERT || recordType == EJRecordType.UPDATE)
            {

                if (value == null || ((String) value).trim().length() == 0)
                {
                    throw new EJActionProcessorException(String.format("%s cannot be Empty!", salutationLabel));
                }

                String salutation = (String) value;

                if (SalutationService.salutationExists(form, salutation, (Integer) id))
                {
                    throw new EJActionProcessorException(String.format("Entered %s already Exist!", salutationLabel));
                }
            }
        }
    }
}
