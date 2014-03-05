package org.entirej.ejinvoice.forms.masterdata;

import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.forms.constants.F_MASTER_DATA;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.enumerations.EJRecordType;
import org.entirej.framework.core.enumerations.EJScreenType;

public class MasterDataActionProcessor extends DefaultFormActionProcessor
{
    private ContactTypesActionHandler _contactTypesActionHanlder = new ContactTypesActionHandler();
    
    
    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        _contactTypesActionHanlder.newFormInstance(form);
    }
    
    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (record.getBlockName() != null
                && ((record.getBlockName().equals(F_MASTER_DATA.B_CONTACT_TYPES.ID)) || record.getBlockName().equals(F_MASTER_DATA.B_CONTACT_TYPES_TOOLBAR.ID)))
        {
            _contactTypesActionHanlder.executeActionCommand(form, record, command, screenType);
        }
    }
    
    @Override
    public void postDelete(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        super.postDelete(form, record);
        form.saveChanges();
        
        // validate the contact types toolbar state after deleting contact type
        // record
        if (F_MASTER_DATA.B_CONTACT_TYPES.ID.equals(form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES.ID).getName()))
        {
            _contactTypesActionHanlder.postDelete(form, record);
        }
    }
    
    @Override
    public void validateRecord(EJForm form, EJRecord record, EJRecordType recordType) throws EJActionProcessorException
    {
        // validate the contact types screen
        if (F_MASTER_DATA.B_CONTACT_TYPES.ID.equals(record.getBlockName()))
        {
            _contactTypesActionHanlder.validateRecord(form, record, recordType);
        }
    }
    
    
    @Override
    public void newRecordInstance(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        // validate the toolbar states when
        // entering new record to thecontact types screen
        if (record.getBlockName().equals(F_MASTER_DATA.B_CONTACT_TYPES.ID))
        {
            _contactTypesActionHanlder.newRecordInstance(form, record);
        }
    }

    @Override
    public void postBlockQuery(EJForm form, EJBlock block) throws EJActionProcessorException
    {
        // validate the toolbar states after
        // a record is updated, deleted or newly added to the contact types
        // screen
        if (block.getName().equals(F_MASTER_DATA.B_CONTACT_TYPES.ID))
        {
            _contactTypesActionHanlder.postBlockQuery(form, block);
        }
    }
    
    
}
