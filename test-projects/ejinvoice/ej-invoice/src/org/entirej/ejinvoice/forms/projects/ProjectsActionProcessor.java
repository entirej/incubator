package org.entirej.ejinvoice.forms.projects;

import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY;
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
            form.openForm(F_TIME_ENTRY.ID);
            return;
        }
    }

    @Override
    public void postDelete(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        super.postDelete(form, record);
        form.saveChanges();
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
