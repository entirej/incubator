package org.entirej.ejinvoice.forms.masterdata;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.constants.F_PROJECT_TASKS;
import org.entirej.ejinvoice.forms.constants.F_SALUTATIONS;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.enumerations.EJScreenType;

public class SalutationsActionProcessor extends DefaultFormActionProcessor
{
    @Override
    public void executeActionCommand(EJForm form, String blockName, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        EJRecord record = form.getBlock(blockName).getFocusedRecord();
        
        if (F_SALUTATIONS.AC_CREATE_SALUTATION.equals(command))
        {
            form.getBlock(F_SALUTATIONS.B_SALUTATIONS_INSERT.ID).clear(true);
            EJRecord insertRecord = form.getBlock(F_SALUTATIONS.B_SALUTATIONS_INSERT.ID).getFocusedRecord();

            insertRecord.setValue(F_SALUTATIONS.B_SALUTATIONS_INSERT.I_PAGE_TITLE, "Create a new Salutation");
            form.showStackedCanvasPage(F_SALUTATIONS.C_MAIN_STACK, F_SALUTATIONS.C_MAIN_STACK_PAGES.INSERT);
            form.setFormParameter(F_SALUTATIONS.P_IN_EDIT_MODE, true);
        }
        else if (F_SALUTATIONS.AC_INSERT_SAVE.equals(command))
        {
            clearError(form, F_SALUTATIONS.B_SALUTATIONS_INSERT.ID, F_SALUTATIONS.B_SALUTATIONS_INSERT.I_VALUE);

            String value = (String) record.getValue(F_SALUTATIONS.B_SALUTATIONS_INSERT.I_VALUE);

            boolean error = false;
            if (value == null || value.trim().length() == 0)
            {
                error = true;
                setError(form, F_SALUTATIONS.B_SALUTATIONS_INSERT.ID, F_SALUTATIONS.B_SALUTATIONS_INSERT.I_VALUE, "Please enter a salutation");
            }
            if (value != null && SalutationService.salutationExists(form, value, null))
            {
                error = true;
                setError(form, F_SALUTATIONS.B_SALUTATIONS_INSERT.ID, F_SALUTATIONS.B_SALUTATIONS_INSERT.I_VALUE, "Salutation already exists");
            }
            if (error)
            {
                throw new EJApplicationException();
            }
            Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
            EJRecord newRecord = form.getBlock(F_SALUTATIONS.B_SALUTATIONS.ID).createRecord();

            int idSeqNextval = PKSequenceService.getPKSequence(form.getConnection());
            newRecord.setValue(F_SALUTATIONS.B_SALUTATIONS.I_ID, idSeqNextval);
            newRecord.setValue(F_SALUTATIONS.B_SALUTATIONS.I_COMPANY_ID, companyId);
            newRecord.setValue(F_SALUTATIONS.B_SALUTATIONS.I_VALUE, record.getValue(F_SALUTATIONS.B_SALUTATIONS_INSERT.I_VALUE));

            Salutation salutation = (Salutation) newRecord.getBlockServicePojo();
            newRecord.setValue(F_SALUTATIONS.B_SALUTATIONS.I_DISPLAY_TEXT, salutation.getDisplayText());

            form.getBlock(F_SALUTATIONS.B_SALUTATIONS.ID).insertRecord(newRecord);
            form.saveChanges();
            form.getBlock(F_SALUTATIONS.B_SALUTATIONS_INSERT.ID).clear(true);
            form.showStackedCanvasPage(F_SALUTATIONS.C_MAIN_STACK, F_SALUTATIONS.C_MAIN_STACK_PAGES.MAIN);
            form.setFormParameter(F_SALUTATIONS.P_IN_EDIT_MODE, false);
        }
        else if (F_SALUTATIONS.AC_MODIFY_SALUTATION.equals(command))
        {
            form.getBlock(F_SALUTATIONS.B_SALUTATIONS_EDIT.ID).clear(true);
            EJRecord editRecord = form.getBlock(F_SALUTATIONS.B_SALUTATIONS_EDIT.ID).getFocusedRecord();

            editRecord.setValue(F_SALUTATIONS.B_SALUTATIONS_EDIT.I_PAGE_TITLE, record.getValue(F_SALUTATIONS.B_SALUTATIONS.I_VALUE) + " - Edit");
            editRecord.setValue(F_SALUTATIONS.B_SALUTATIONS_EDIT.I_ID, record.getValue(F_SALUTATIONS.B_SALUTATIONS.I_ID));
            editRecord.setValue(F_SALUTATIONS.B_SALUTATIONS_EDIT.I_VALUE, record.getValue(F_SALUTATIONS.B_SALUTATIONS.I_VALUE));

            form.showStackedCanvasPage(F_SALUTATIONS.C_MAIN_STACK, F_SALUTATIONS.C_MAIN_STACK_PAGES.EDIT);
            form.setFormParameter(F_SALUTATIONS.P_IN_EDIT_MODE, true);
        }
        else if (F_SALUTATIONS.AC_EDIT_SAVE.equals(command))
        {
            clearError(form, F_SALUTATIONS.B_SALUTATIONS_EDIT.ID, F_SALUTATIONS.B_SALUTATIONS_EDIT.I_VALUE);

            String value = (String) record.getValue(F_SALUTATIONS.B_SALUTATIONS_EDIT.I_VALUE);
            Integer id = (Integer) record.getValue(F_SALUTATIONS.B_SALUTATIONS_EDIT.I_ID);

            boolean error = false;
            if (value == null || value.trim().length() == 0)
            {
                error = true;
                setError(form, F_SALUTATIONS.B_SALUTATIONS_EDIT.ID, F_SALUTATIONS.B_SALUTATIONS_EDIT.I_VALUE, "Please enter a salutation");
            }
            if (value != null && SalutationService.salutationExists(form, value, id))
            {
                error = true;
                setError(form, F_SALUTATIONS.B_SALUTATIONS_EDIT.ID, F_SALUTATIONS.B_SALUTATIONS_EDIT.I_VALUE, "Salutation already exists");
            }
            if (error)
            {
                throw new EJActionProcessorException();
            }
            EJRecord baseRecord = form.getBlock(F_SALUTATIONS.B_SALUTATIONS.ID).getFocusedRecord();

            baseRecord.setValue(F_SALUTATIONS.B_SALUTATIONS.I_VALUE, record.getValue(F_SALUTATIONS.B_SALUTATIONS_EDIT.I_VALUE));

            Salutation salutation = (Salutation) baseRecord.getBlockServicePojo();
            baseRecord.setValue(F_SALUTATIONS.B_SALUTATIONS.I_DISPLAY_TEXT, salutation.getDisplayText());

            form.getBlock(F_SALUTATIONS.B_SALUTATIONS.ID).updateRecord(baseRecord);
            form.saveChanges();

            form.getBlock(F_SALUTATIONS.B_SALUTATIONS_EDIT.ID).clear(true);

            form.showStackedCanvasPage(F_SALUTATIONS.C_MAIN_STACK, F_SALUTATIONS.C_MAIN_STACK_PAGES.MAIN);
            form.setFormParameter(F_SALUTATIONS.P_IN_EDIT_MODE, false);
        }
        else if (F_SALUTATIONS.AC_EDIT_CANCEL.equals(command))
        {
            clearError(form, F_SALUTATIONS.B_SALUTATIONS_EDIT.ID, F_SALUTATIONS.B_SALUTATIONS_EDIT.I_VALUE);

            form.getBlock(F_SALUTATIONS.B_SALUTATIONS_EDIT.ID).clear(true);
            form.showStackedCanvasPage(F_SALUTATIONS.C_MAIN_STACK, F_SALUTATIONS.C_MAIN_STACK_PAGES.MAIN);
            form.setFormParameter(F_SALUTATIONS.P_IN_EDIT_MODE, false);
        }
        else if (F_SALUTATIONS.AC_INSERT_CANCEL.equals(command))
        {
            clearError(form, F_SALUTATIONS.B_SALUTATIONS_INSERT.ID, F_SALUTATIONS.B_SALUTATIONS_INSERT.I_VALUE);

            form.getBlock(F_SALUTATIONS.B_SALUTATIONS_INSERT.ID).clear(true);
            form.showStackedCanvasPage(F_SALUTATIONS.C_MAIN_STACK, F_SALUTATIONS.C_MAIN_STACK_PAGES.MAIN);
            form.setFormParameter(F_SALUTATIONS.P_IN_EDIT_MODE, false);
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

}
