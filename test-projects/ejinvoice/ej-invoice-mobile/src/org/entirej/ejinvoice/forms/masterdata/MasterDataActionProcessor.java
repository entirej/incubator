package org.entirej.ejinvoice.forms.masterdata;

import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.enumerations.EJRecordType;
import org.entirej.framework.core.enumerations.EJScreenType;

public class MasterDataActionProcessor extends DefaultFormActionProcessor
{
    private ContactTypesActionHandler _contactTypesActionHanlder = new ContactTypesActionHandler();
    private PaymentTermsActionHandler _paymentTermsActionHandler = new PaymentTermsActionHandler();
    private SalutationActionHandler   _salutationsActionHandler  = new SalutationActionHandler();
    private VatRatesActionHandler     _vatRatesActionHandler     = new VatRatesActionHandler();

    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        _contactTypesActionHanlder.newFormInstance(form);
        _paymentTermsActionHandler.newFormInstance(form);
        _salutationsActionHandler.newFormInstance(form);
        _vatRatesActionHandler.newFormInstance(form);
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        System.out.println("Command: "+command+", Block: "+record.getBlockName());
        _contactTypesActionHanlder.executeActionCommand(form, record, command, screenType);
        _paymentTermsActionHandler.executeActionCommand(form, record, command, screenType);
        _salutationsActionHandler.executeActionCommand(form, record, command, screenType);
        _vatRatesActionHandler.executeActionCommand(form, record, command, screenType);
    }

    @Override
    public void postDelete(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        super.postDelete(form, record);
        form.saveChanges();

        // validate the contact types toolbar state after deleting contact type
        // record
        _contactTypesActionHanlder.postDelete(form, record);
        _paymentTermsActionHandler.postDelete(form, record);
        _salutationsActionHandler.postDelete(form, record);
        _vatRatesActionHandler.postDelete(form, record);
    }

    @Override
    public void validateRecord(EJForm form, EJRecord record, EJRecordType recordType) throws EJActionProcessorException
    {
        // validate the contact types screen
        _contactTypesActionHanlder.validateRecord(form, record, recordType);
        _paymentTermsActionHandler.validateRecord(form, record, recordType);
        _salutationsActionHandler.validateRecord(form, record, recordType);
        _vatRatesActionHandler.validateRecord(form, record, recordType);
    }

    @Override
    public void newRecordInstance(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        // validate the toolbar states when entering new record to thecontact
        // types screen
        _contactTypesActionHanlder.newRecordInstance(form, record);
        _paymentTermsActionHandler.newRecordInstance(form, record);
        _salutationsActionHandler.newRecordInstance(form, record);
        _vatRatesActionHandler.newRecordInstance(form, record);
    }

    @Override
    public void postBlockQuery(EJForm form, EJBlock block) throws EJActionProcessorException
    {
        // validate the toolbar states after a record is updated, deleted or
        // newly added to the contact types screen
        _contactTypesActionHanlder.postBlockQuery(form, block);
        _paymentTermsActionHandler.postBlockQuery(form, block);
        _salutationsActionHandler.postBlockQuery(form, block);
        _vatRatesActionHandler.postBlockQuery(form, block);
    }

}
