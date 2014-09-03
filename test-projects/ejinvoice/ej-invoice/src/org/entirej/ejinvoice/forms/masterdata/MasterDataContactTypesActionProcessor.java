package org.entirej.ejinvoice.forms.masterdata;

import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.constants.F_MASTER_DATA_CONTACT_TYPES;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.EJScreenItem;
import org.entirej.framework.core.enumerations.EJRecordType;
import org.entirej.framework.core.enumerations.EJScreenType;

public class MasterDataContactTypesActionProcessor extends DefaultFormActionProcessor
{
    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_MASTER_DATA_CONTACT_TYPES.AC_DELETE_CONTACT_TYPE.equals(command))
        {
            // before deleting the selected record from database validate
            // and check if the record to be deleted has any FK constraints
            // usage with other table data and if so throw an exception and
            // block physical delete
            try
            {
                ServiceRetriever.getDBService(form).validateDeleteRecordUsage(form, form.getBlock(F_MASTER_DATA_CONTACT_TYPES.B_CONTACT_TYPES.ID).getFocusedRecord(), "CONTACT_TYPES");
                form.getBlock(F_MASTER_DATA_CONTACT_TYPES.B_CONTACT_TYPES.ID).askToDeleteCurrentRecord("Are you sure you want to delete this contact type?");
            }
            catch (Exception e)
            {
                form.handleException(e);
            }
        }
        else if (F_MASTER_DATA_CONTACT_TYPES.AC_MODIFY_CONTACT.equals(command))
        {
            form.getBlock(F_MASTER_DATA_CONTACT_TYPES.B_CONTACT_TYPES.ID).enterUpdate();
        }
        else if (F_MASTER_DATA_CONTACT_TYPES.AC_CREATE_CONTACT_TYPE.equals(command))
        {
            form.getBlock(F_MASTER_DATA_CONTACT_TYPES.B_CONTACT_TYPES.ID).enterInsert(false);
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
        // validate the contact types screen
        if (F_MASTER_DATA_CONTACT_TYPES.B_CONTACT_TYPES.ID.equals(record.getBlockName()))
        {
            Object type = record.getValue(F_MASTER_DATA_CONTACT_TYPES.B_CONTACT_TYPES.I_TYPE);
            Object id = record.getValue(F_MASTER_DATA_CONTACT_TYPES.B_CONTACT_TYPES.I_ID);

            final EJScreenItem typeItem = form.getBlock(F_MASTER_DATA_CONTACT_TYPES.B_CONTACT_TYPES.ID).getScreenItem(EJScreenType.MAIN, record.getItem(F_MASTER_DATA_CONTACT_TYPES.B_CONTACT_TYPES.I_TYPE).getName());
            final String typeLabel = typeItem.getLabel();

            if (recordType == EJRecordType.INSERT || recordType == EJRecordType.UPDATE)
            {

                if (type == null || ((String) type).trim().length() == 0)
                {
                    throw new EJActionProcessorException(String.format("%s cannot be Empty!", typeLabel));

                }

                if (ContactTypesService.contactTypeExists(form, (String) type, (Integer) id))

                {
                    throw new EJActionProcessorException(String.format("Entered %s already Exist!", typeLabel));
                }
            }
        }
    }

}
