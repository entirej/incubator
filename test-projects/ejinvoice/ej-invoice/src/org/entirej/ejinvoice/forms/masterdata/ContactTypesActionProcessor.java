package org.entirej.ejinvoice.forms.masterdata;

import java.util.Locale;

import org.apache.commons.validator.routines.EmailValidator;
import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.constants.F_CONTACT_TYPES;
import org.entirej.ejinvoice.forms.constants.F_CONTACT_TYPES;
import org.entirej.ejinvoice.forms.timeentry.Customer;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.EJScreenItem;
import org.entirej.framework.core.enumerations.EJRecordType;
import org.entirej.framework.core.enumerations.EJScreenType;

public class ContactTypesActionProcessor extends DefaultFormActionProcessor
{
    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_CONTACT_TYPES.AC_CREATE_CONTACT_TYPE.equals(command))
        {
            form.getBlock(F_CONTACT_TYPES.B_CONTACT_TYPES.ID).clear(true);
            EJRecord insertRecord = form.getBlock(F_CONTACT_TYPES.B_CONTACT_TYPES_INSERT.ID).getFocusedRecord();

            insertRecord.setValue(F_CONTACT_TYPES.B_CONTACT_TYPES_INSERT.I_PAGE_TITLE, "Create a new Contact Type");
            form.showStackedCanvasPage(F_CONTACT_TYPES.C_MAIN_STACK, F_CONTACT_TYPES.C_MAIN_STACK_PAGES.INSERT);
        }
        else if (F_CONTACT_TYPES.AC_INSERT_SAVE.equals(command))
        {
            clearError(form, F_CONTACT_TYPES.B_CONTACT_TYPES_INSERT.ID, F_CONTACT_TYPES.B_CONTACT_TYPES_INSERT.I_TYPE);

            String type = (String) record.getValue(F_CONTACT_TYPES.B_CONTACT_TYPES_INSERT.I_TYPE);

            boolean error = false;
            if (type == null || type.trim().length() == 0)
            {
                error = true;
                setError(form, F_CONTACT_TYPES.B_CONTACT_TYPES_INSERT.ID, F_CONTACT_TYPES.B_CONTACT_TYPES_INSERT.I_TYPE, "Please enter a type");
            }
            if (type != null && ContactTypesService.contactTypeExists(form, type, null))
            {
                error = true;
                setError(form, F_CONTACT_TYPES.B_CONTACT_TYPES_INSERT.ID, F_CONTACT_TYPES.B_CONTACT_TYPES_INSERT.I_TYPE, "Type already exists");
            }
            if (error)
            {
                throw new EJApplicationException();
            }
            Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
            EJRecord newRecord = form.getBlock(F_CONTACT_TYPES.B_CONTACT_TYPES.ID).createRecord();

            int idSeqNextval = PKSequenceService.getPKSequence(form.getConnection());
            newRecord.setValue(F_CONTACT_TYPES.B_CONTACT_TYPES.I_ID, idSeqNextval);
            newRecord.setValue(F_CONTACT_TYPES.B_CONTACT_TYPES.I_COMPANY_ID, companyId);
            newRecord.setValue(F_CONTACT_TYPES.B_CONTACT_TYPES.I_TYPE, record.getValue(F_CONTACT_TYPES.B_CONTACT_TYPES_INSERT.I_TYPE));
            newRecord.setValue(F_CONTACT_TYPES.B_CONTACT_TYPES.I_DESCRIPTION, record.getValue(F_CONTACT_TYPES.B_CONTACT_TYPES_INSERT.I_DESCRIPTION));

            ContactType contactType = (ContactType) newRecord.getBlockServicePojo();
            newRecord.setValue(F_CONTACT_TYPES.B_CONTACT_TYPES.I_DISPLAY_TEXT, contactType.getDisplayText());

            form.getBlock(F_CONTACT_TYPES.B_CONTACT_TYPES.ID).insertRecord(newRecord);
            form.saveChanges();
            form.getBlock(F_CONTACT_TYPES.B_CONTACT_TYPES_INSERT.ID).clear(true);
            form.showStackedCanvasPage(F_CONTACT_TYPES.C_MAIN_STACK, F_CONTACT_TYPES.C_MAIN_STACK_PAGES.MAIN);
        }
        else if (F_CONTACT_TYPES.AC_MODIFY_CONTACT.equals(command))
        {
            form.getBlock(F_CONTACT_TYPES.B_CONTACT_TYPES_UPDATE.ID).clear(true);
            EJRecord editRecord = form.getBlock(F_CONTACT_TYPES.B_CONTACT_TYPES_UPDATE.ID).getFocusedRecord();

            editRecord.setValue(F_CONTACT_TYPES.B_CONTACT_TYPES_UPDATE.I_PAGE_TITLE, record.getValue(F_CONTACT_TYPES.B_CONTACT_TYPES.I_TYPE) + " - Edit");
            editRecord.setValue(F_CONTACT_TYPES.B_CONTACT_TYPES_UPDATE.I_TYPE, record.getValue(F_CONTACT_TYPES.B_CONTACT_TYPES.I_TYPE));
            editRecord.setValue(F_CONTACT_TYPES.B_CONTACT_TYPES_UPDATE.I_DESCRIPTION, record.getValue(F_CONTACT_TYPES.B_CONTACT_TYPES.I_DESCRIPTION));
            editRecord.setValue(F_CONTACT_TYPES.B_CONTACT_TYPES_UPDATE.I_ID, record.getValue(F_CONTACT_TYPES.B_CONTACT_TYPES.I_ID));

            form.showStackedCanvasPage(F_CONTACT_TYPES.C_MAIN_STACK, F_CONTACT_TYPES.C_MAIN_STACK_PAGES.EDIT);
        }
        else if (F_CONTACT_TYPES.AC_EDIT_SAVE.equals(command))
        {
            clearError(form, F_CONTACT_TYPES.B_CONTACT_TYPES_UPDATE.ID, F_CONTACT_TYPES.B_CONTACT_TYPES_UPDATE.I_TYPE);

            String type = (String) record.getValue(F_CONTACT_TYPES.B_CONTACT_TYPES_UPDATE.I_TYPE);
            Integer id = (Integer) record.getValue(F_CONTACT_TYPES.B_CONTACT_TYPES_UPDATE.I_ID);

            boolean error = false;
            if (type == null || type.trim().length() == 0)
            {
                error = true;
                setError(form, F_CONTACT_TYPES.B_CONTACT_TYPES_UPDATE.ID, F_CONTACT_TYPES.B_CONTACT_TYPES_UPDATE.I_TYPE, "Please enter a type");
            }
            if (type != null && ContactTypesService.contactTypeExists(form, type, id))
            {
                error = true;
                setError(form, F_CONTACT_TYPES.B_CONTACT_TYPES_UPDATE.ID, F_CONTACT_TYPES.B_CONTACT_TYPES_UPDATE.I_TYPE, "Type already exists");
            }
            if (error)
            {
                throw new EJActionProcessorException();
            }
            EJRecord baseRecord = form.getBlock(F_CONTACT_TYPES.B_CONTACT_TYPES.ID).getFocusedRecord();

            baseRecord.setValue(F_CONTACT_TYPES.B_CONTACT_TYPES.I_TYPE, record.getValue(F_CONTACT_TYPES.B_CONTACT_TYPES_UPDATE.I_TYPE));
            baseRecord.setValue(F_CONTACT_TYPES.B_CONTACT_TYPES.I_DESCRIPTION, record.getValue(F_CONTACT_TYPES.B_CONTACT_TYPES_UPDATE.I_DESCRIPTION));

            ContactType contactType = (ContactType) baseRecord.getBlockServicePojo();
            baseRecord.setValue(F_CONTACT_TYPES.B_CONTACT_TYPES.I_DISPLAY_TEXT, contactType.getDisplayText());

            form.getBlock(F_CONTACT_TYPES.B_CONTACT_TYPES.ID).updateRecord(baseRecord);
            form.saveChanges();

            form.getBlock(F_CONTACT_TYPES.B_CONTACT_TYPES_UPDATE.ID).clear(true);

            form.showStackedCanvasPage(F_CONTACT_TYPES.C_MAIN_STACK, F_CONTACT_TYPES.C_MAIN_STACK_PAGES.MAIN);
        }
        else if (F_CONTACT_TYPES.AC_EDIT_CANCEL.equals(command))
        {
            clearError(form, F_CONTACT_TYPES.B_CONTACT_TYPES_UPDATE.ID, F_CONTACT_TYPES.B_CONTACT_TYPES_UPDATE.I_TYPE);

            form.getBlock(F_CONTACT_TYPES.B_CONTACT_TYPES_UPDATE.ID).clear(true);
            form.showStackedCanvasPage(F_CONTACT_TYPES.C_MAIN_STACK, F_CONTACT_TYPES.C_MAIN_STACK_PAGES.MAIN);
        }
        else if (F_CONTACT_TYPES.AC_INSERT_CANCEL.equals(command))
        {
            clearError(form, F_CONTACT_TYPES.B_CONTACT_TYPES_INSERT.ID, F_CONTACT_TYPES.B_CONTACT_TYPES_INSERT.I_TYPE);

            form.getBlock(F_CONTACT_TYPES.B_CONTACT_TYPES_INSERT.ID).clear(true);
            form.showStackedCanvasPage(F_CONTACT_TYPES.C_MAIN_STACK, F_CONTACT_TYPES.C_MAIN_STACK_PAGES.MAIN);
        }
        else if (F_CONTACT_TYPES.AC_DELETE_CONTACT_TYPE.equals(command))
        {
            // before deleting the selected record from database validate
            // and check if the record to be deleted has any FK constraints
            // usage with other table data and if so throw an exception and
            // block physical delete
            try
            {
                ServiceRetriever.getDBService(form).validateDeleteRecordUsage(form, form.getBlock(F_CONTACT_TYPES.B_CONTACT_TYPES.ID).getFocusedRecord(), "CONTACT_TYPES");
                form.getBlock(F_CONTACT_TYPES.B_CONTACT_TYPES.ID).askToDeleteCurrentRecord("Are you sure you want to delete this contact type?");
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
