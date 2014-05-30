package org.entirej.ejinvoice.forms.projects;

import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.constants.F_LAUNCH_PAGE;
import org.entirej.ejinvoice.forms.constants.F_PROJECT;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.EJScreenItem;
import org.entirej.framework.core.enumerations.EJScreenType;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;

public class ProjectsActionProcessor extends DefaultFormActionProcessor
{
    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        form.getBlock(F_PROJECT.B_PROJECTS.ID).executeQuery();
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {

        if (F_PROJECT.AC_TOOLBAR_NEW.equals(command))
        {
            form.getBlock(F_PROJECT.B_PROJECTS.ID).enterInsert(false);
            return;
        }
        if (F_PROJECT.AC_TOOLBAR_EDIT.equals(command))
        {
            form.getBlock(F_PROJECT.B_PROJECTS.ID).enterUpdate();
            return;
        }
        if (F_PROJECT.AC_TOOLBAR_DELETE.equals(command) && form.getBlock(F_PROJECT.B_PROJECTS.ID).getFocusedRecord() != null)
        {
            // before deleting the selected record from database validation and
            // check if the record to be deleted has any FK constraints usage
            // with other table data and if so throw an exception and block
            // physical delete
            ServiceRetriever.getDBService(form).validateDeleteRecordUsage(form.getBlock(F_PROJECT.B_PROJECTS.ID).getFocusedRecord(), "PROJECT_INFOMATION");
            form.getBlock(F_PROJECT.B_PROJECTS.ID).askToDeleteCurrentRecord("Are you sure you want to delete this project?");
            return;
        }
        if (F_PROJECT.AC_TOOLBAR_HOME.equals(command))
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
        if (F_PROJECT.B_PROJECTS.ID.equals(record.getBlockName()))
        {
            validateToolbarState(form.getBlock(F_PROJECT.B_PROJECT_TOOL_BAR.ID), form.getBlock(F_PROJECT.B_PROJECTS.ID).getFocusedRecord() != null);
        }

    }

    @Override
    public void postInsert(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_PROJECT.B_PROJECTS.ID.equals(record.getBlockName()))
        {
            validateToolbarState(form.getBlock(F_PROJECT.B_PROJECT_TOOL_BAR.ID), form.getBlock(F_PROJECT.B_PROJECTS.ID).getFocusedRecord() != null);
        }
    }

    @Override
    public void newRecordInstance(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        // validate the toolbar states when
        // entering new record to the company information form
        if (record.getBlockName().equals(F_PROJECT.B_PROJECTS.ID))
        {
            validateToolbarState(form.getBlock(F_PROJECT.B_PROJECT_TOOL_BAR.ID), record != null);

        }
    }

    @Override
    public void postBlockQuery(EJForm form, EJBlock block) throws EJActionProcessorException
    {
        // validate the toolbar states after
        // a record is updated, deleted or newly added to the company
        // information screen
        if (block.getName().equals(F_PROJECT.B_PROJECTS.ID))
        {
            validateToolbarState(form.getBlock(F_PROJECT.B_PROJECT_TOOL_BAR.ID), form.getBlock(F_PROJECT.B_PROJECTS.ID).getFocusedRecord() != null);

        }
    }
    
    @Override
    public void preQuery(EJForm form, EJQueryCriteria queryCriteria) throws EJActionProcessorException
    {
        if (F_PROJECT.B_PROJECTS.ID.equals(queryCriteria.getBlockName()))
        {
            EJBlock filterBlock = form.getBlock(F_PROJECT.B_PROJECT_TOOL_BAR.ID);
            Object customerFIlter = filterBlock.getFocusedRecord().getValue(F_PROJECT.B_PROJECT_TOOL_BAR.I_CUSTOMERID);
            if (customerFIlter != null)
            {
                queryCriteria.add(EJRestrictions.equals( F_PROJECT.B_PROJECTS.I_CUSTOMER_ID, customerFIlter));
            }
        }
    }

    @Override
    public void validateItem(EJForm form, EJRecord record, String itemName, EJScreenType screenType) throws EJActionProcessorException
    {
        if (record.getBlockName().equals(F_PROJECT.B_PROJECT_TOOL_BAR.ID) && F_PROJECT.B_PROJECT_TOOL_BAR.I_CUSTOMER.equals(itemName))
        {
            form.getBlock(F_PROJECT.B_PROJECTS.ID).executeQuery();
        }
    }

}
