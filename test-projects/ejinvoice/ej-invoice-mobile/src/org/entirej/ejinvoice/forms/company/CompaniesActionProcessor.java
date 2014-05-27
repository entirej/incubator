package org.entirej.ejinvoice.forms.company;

import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.constants.F_COMPANY;
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
        if (F_COMPANY.AC_SHOW_BANK_DETAILS.equals(command))
        {
            form.showPopupCanvas(F_COMPANY.C_COMPANY_DETAIL_POPUP);
        }

        if (F_COMPANY.AC_NEW.equals(command))
        {
            form.getBlock(F_COMPANY.B_COMPANIES_DETAIL.ID).enterInsert(false);
            return;
        }
        if (F_COMPANY.AC_EDIT.equals(command) && form.getBlock(F_COMPANY.B_COMPANIES_DETAIL.ID).getFocusedRecord() != null)
        {
            form.getBlock(F_COMPANY.B_COMPANIES_DETAIL.ID).enterUpdate();
            return;
        }
        if (F_COMPANY.AC_DELETE.equals(command) && form.getBlock(F_COMPANY.B_COMPANIES_DETAIL.ID).getFocusedRecord() != null)
        {
            // before deleting the selected record from database validation and
            // check if the record to be deleted has any FK constraints usage
            // with other table data and if so throw an exception and block
            // physical delete
            ServiceRetriever.getDBService(form).validateDeleteRecordUsage(form.getBlock(F_COMPANY.B_COMPANIES_DETAIL.ID).getFocusedRecord(), "COMPANY_INFORMATION");
            form.getBlock(F_COMPANY.B_COMPANIES_DETAIL.ID).askToDeleteCurrentRecord("Are you sure you want to delete this company?");
            return;
        }
    }

    @Override
    public void postDelete(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        
         form.saveChanges();
   

    }

    

   
}
