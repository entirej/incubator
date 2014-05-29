package org.entirej.ejinvoice.forms.company;

import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.constants.F_COMPANY;
import org.entirej.ejinvoice.forms.constants.F_LAUNCH_PAGE;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.EJScreenItem;
import org.entirej.framework.core.enumerations.EJRecordType;
import org.entirej.framework.core.enumerations.EJScreenType;

public class CompaniesActionProcessor extends DefaultFormActionProcessor
{
    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        form.getBlock(F_COMPANY.B_COMPANIES.ID).executeQuery();
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {

        if (F_COMPANY.AC_TOOLBAR_NEW.equals(command))
        {
            form.getBlock(F_COMPANY.B_COMPANIES_DETAIL.ID).enterInsert(false);
            return;
        }
        if (F_COMPANY.AC_TOOLBAR_EDIT.equals(command))
        {
            form.getBlock(F_COMPANY.B_COMPANIES_DETAIL.ID).enterUpdate();
            return;
        }
        if (F_COMPANY.AC_TOOLBAR_DELETE.equals(command) && form.getBlock(F_COMPANY.B_COMPANIES_DETAIL.ID).getFocusedRecord() != null)
        {
            // before deleting the selected record from database validation and
            // check if the record to be deleted has any FK constraints usage
            // with other table data and if so throw an exception and block
            // physical delete
            ServiceRetriever.getDBService(form).validateDeleteRecordUsage(form.getBlock(F_COMPANY.B_COMPANIES_DETAIL.ID).getFocusedRecord(), "COMPANY_INFORMATION");
            form.getBlock(F_COMPANY.B_COMPANIES_DETAIL.ID).askToDeleteCurrentRecord("Are you sure you want to delete this company?");
            return;
        }
        if (F_COMPANY.AC_TOOLBAR_HOME.equals(command))
        {
            form.openForm(F_LAUNCH_PAGE.ID);
            return;
        }
    }

    @Override
    public void postDelete(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        super.postDelete(form, record);
        form.saveChanges();
        // validate the company information toolbar state after deleting company
        // information record
        if (F_COMPANY.B_COMPANIES.ID.equals(record.getBlockName()) || record.getBlockName().equals(F_COMPANY.B_COMPANIES_DETAIL.ID))
        {
            validateToolbarState(form.getBlock(F_COMPANY.B_COMPANY_TOOLBAR.ID), form.getBlock(F_COMPANY.B_COMPANIES.ID).getFocusedRecord() != null);
        }

    }

    @Override
    public void postInsert(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_COMPANY.B_COMPANIES.ID.equals(record.getBlockName()) || record.getBlockName().equals(F_COMPANY.B_COMPANIES_DETAIL.ID))
        {
            validateToolbarState(form.getBlock(F_COMPANY.B_COMPANY_TOOLBAR.ID), form.getBlock(F_COMPANY.B_COMPANIES.ID).getFocusedRecord() != null);
        }
    }

    @Override
    public void newRecordInstance(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        // validate the toolbar states when
        // entering new record to the company information form
        if (record.getBlockName().equals(F_COMPANY.B_COMPANIES.ID) || record.getBlockName().equals(F_COMPANY.B_COMPANIES_DETAIL.ID))
        {
            validateToolbarState(form.getBlock(F_COMPANY.B_COMPANY_TOOLBAR.ID), record != null);

        }
    }

    @Override
    public void postBlockQuery(EJForm form, EJBlock block) throws EJActionProcessorException
    {
        // validate the toolbar states after
        // a record is updated, deleted or newly added to the company
        // information screen
        if (block.getName().equals(F_COMPANY.B_COMPANIES.ID) || block.getName().equals(F_COMPANY.B_COMPANIES_DETAIL.ID))
        {
            validateToolbarState(form.getBlock(F_COMPANY.B_COMPANY_TOOLBAR.ID), form.getBlock(F_COMPANY.B_COMPANIES.ID).getFocusedRecord() != null);

        }
    }

}
