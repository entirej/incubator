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
    private ContactTypesActionHandler  _contactTypesActionHanlder  = new ContactTypesActionHandler();
    private SalutationActionHandler    _salutationsActionHandler   = new SalutationActionHandler();
    private VatRatesActionHandler      _vatRatesActionHandler      = new VatRatesActionHandler();

    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        _contactTypesActionHanlder.newFormInstance(form);
        _salutationsActionHandler.newFormInstance(form);
        _vatRatesActionHandler.newFormInstance(form);
    }
    
    @Override
    public void tabPageChanged(EJForm form, String tabCanvasName, String tabPageName) throws EJActionProcessorException
    {
        if (F_MASTER_DATA.C_MAIN_TAB_PAGES.CONTACT__TYPES.equals(tabPageName))
        {
            if (form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES.ID).getBlockRecords().size() <= 0)
            {
                form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES.ID).executeQuery();
            }
        }
        else if (F_MASTER_DATA.C_MAIN_TAB_PAGES.SALUTATION.equals(tabPageName))
        {
            if (form.getBlock(F_MASTER_DATA.B_SALUTATIONS.ID).getBlockRecords().size() <= 0)
            {
                form.getBlock(F_MASTER_DATA.B_SALUTATIONS.ID).executeQuery();
            }
        }
        else if (F_MASTER_DATA.C_MAIN_TAB_PAGES.VAT_RATES.equals(tabPageName))
        {
            if (form.getBlock(F_MASTER_DATA.B_VAT_RATES.ID).getBlockRecords().size() <= 0)
            {
                form.getBlock(F_MASTER_DATA.B_VAT_RATES.ID).executeQuery();
            }            
        }
    }




    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        _contactTypesActionHanlder.executeActionCommand(form, record, command, screenType);
        _salutationsActionHandler.executeActionCommand(form, record, command, screenType);
        _vatRatesActionHandler.executeActionCommand(form, record, command, screenType);
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
        _contactTypesActionHanlder.validateRecord(form, record, recordType);
        _salutationsActionHandler.validateRecord(form, record, recordType);
        _vatRatesActionHandler.validateRecord(form, record, recordType);
    }

    @Override
    public void newRecordInstance(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        _contactTypesActionHanlder.newRecordInstance(form, record);
        _salutationsActionHandler.newRecordInstance(form, record);
        _vatRatesActionHandler.newRecordInstance(form, record);
    }

    @Override
    public void postBlockQuery(EJForm form, EJBlock block) throws EJActionProcessorException
    {
        _contactTypesActionHanlder.postBlockQuery(form, block);
        _salutationsActionHandler.postBlockQuery(form, block);
        _vatRatesActionHandler.postBlockQuery(form, block);
    }

    @Override
    public void postQuery(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        _contactTypesActionHanlder.postQuery(form, record);
        _salutationsActionHandler.postQuery(form, record);
        _vatRatesActionHandler.postQuery(form, record);
    }
}
