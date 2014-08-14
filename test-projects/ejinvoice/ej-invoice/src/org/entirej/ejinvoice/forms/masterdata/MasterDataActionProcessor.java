package org.entirej.ejinvoice.forms.masterdata;

import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.forms.constants.F_COMPANY;
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
    private PaymentTermsActionHandler  _paymentTermsActionHandler  = new PaymentTermsActionHandler();
    private SalutationActionHandler    _salutationsActionHandler   = new SalutationActionHandler();
    private VatRatesActionHandler      _vatRatesActionHandler      = new VatRatesActionHandler();
    private ProjectStatusActionHandler _projectStatusActionHandler = new ProjectStatusActionHandler();
    private CurrencyActionHandler      _currencyActionHandler      = new CurrencyActionHandler();

    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        form.openEmbeddedForm(F_COMPANY.ID, F_MASTER_DATA.C_COMPANY, null);
        
        _contactTypesActionHanlder.newFormInstance(form);
        _paymentTermsActionHandler.newFormInstance(form);
        _salutationsActionHandler.newFormInstance(form);
        _vatRatesActionHandler.newFormInstance(form);
        _projectStatusActionHandler.newFormInstance(form);
        _currencyActionHandler.newFormInstance(form);
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
        else if (F_MASTER_DATA.C_MAIN_TAB_PAGES.CURRENCIES.equals(tabPageName))
        {
            if (form.getBlock(F_MASTER_DATA.B_CURRENCIES.ID).getBlockRecords().size() <= 0)
            {
                form.getBlock(F_MASTER_DATA.B_CURRENCIES.ID).executeQuery();
            }
        }
        else if (F_MASTER_DATA.C_MAIN_TAB_PAGES.PAYMENT_TERMS.equals(tabPageName))
        {
            if (form.getBlock(F_MASTER_DATA.B_PAYMENT_TERMS.ID).getBlockRecords().size() <= 0)
            {
                form.getBlock(F_MASTER_DATA.B_PAYMENT_TERMS.ID).executeQuery();
            }
        }
        else if (F_MASTER_DATA.C_MAIN_TAB_PAGES.PROJECT_STATUS.equals(tabPageName))
        {
            if (form.getBlock(F_MASTER_DATA.B_PROJECT_STATUS.ID).getBlockRecords().size() <= 0)
            {
                form.getBlock(F_MASTER_DATA.B_PROJECT_STATUS.ID).executeQuery();
            }
        }
        else if (F_MASTER_DATA.C_MAIN_TAB_PAGES.SALUTATION.equals(tabPageName))
        {
            if (form.getBlock(F_MASTER_DATA.B_SALUTATIONS.ID).getBlockRecords().size() <= 0)
            {
                form.getBlock(F_MASTER_DATA.B_SALUTATIONS.ID).executeQuery();
            }
        }
        else if (F_MASTER_DATA.C_MAIN_TAB_PAGES.TASK_STATUS.equals(tabPageName))
        {
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
        _paymentTermsActionHandler.executeActionCommand(form, record, command, screenType);
        _salutationsActionHandler.executeActionCommand(form, record, command, screenType);
        _vatRatesActionHandler.executeActionCommand(form, record, command, screenType);
        _projectStatusActionHandler.executeActionCommand(form, record, command, screenType);
        _currencyActionHandler.executeActionCommand(form, record, command, screenType);
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
        _paymentTermsActionHandler.validateRecord(form, record, recordType);
        _salutationsActionHandler.validateRecord(form, record, recordType);
        _vatRatesActionHandler.validateRecord(form, record, recordType);
        _projectStatusActionHandler.validateRecord(form, record, recordType);
        _currencyActionHandler.validateRecord(form, record, recordType);
    }

    @Override
    public void newRecordInstance(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        _contactTypesActionHanlder.newRecordInstance(form, record);
        _paymentTermsActionHandler.newRecordInstance(form, record);
        _salutationsActionHandler.newRecordInstance(form, record);
        _vatRatesActionHandler.newRecordInstance(form, record);
        _projectStatusActionHandler.newRecordInstance(form, record);
        _currencyActionHandler.newRecordInstance(form, record);
    }

    @Override
    public void postBlockQuery(EJForm form, EJBlock block) throws EJActionProcessorException
    {
        _contactTypesActionHanlder.postBlockQuery(form, block);
        _paymentTermsActionHandler.postBlockQuery(form, block);
        _salutationsActionHandler.postBlockQuery(form, block);
        _vatRatesActionHandler.postBlockQuery(form, block);
        _projectStatusActionHandler.postBlockQuery(form, block);
        _currencyActionHandler.postBlockQuery(form, block);
    }

    @Override
    public void postQuery(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        _contactTypesActionHanlder.postQuery(form, record);
        _paymentTermsActionHandler.postQuery(form, record);
        _salutationsActionHandler.postQuery(form, record);
        _vatRatesActionHandler.postQuery(form, record);
        _projectStatusActionHandler.postQuery(form, record);
        _currencyActionHandler.postQuery(form, record);
    }
}
