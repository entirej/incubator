package org.entirej.ejinvoice.forms.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import org.entirej.applicationframework.rwt.file.EJRWTFileUpload;
import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.enums.UserRole;
import org.entirej.ejinvoice.forms.constants.F_COMPANY;
import org.entirej.ejinvoice.forms.constants.F_CONTACT_TYPES;
import org.entirej.ejinvoice.forms.constants.F_SALUTATIONS;
import org.entirej.ejinvoice.forms.constants.F_USERS;
import org.entirej.ejinvoice.forms.constants.F_VAT_RATES;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY;
import org.entirej.ejinvoice.forms.masterdata.SalutationService;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.data.controllers.EJQuestion;
import org.entirej.framework.core.enumerations.EJQuestionButton;
import org.entirej.framework.core.enumerations.EJRecordType;
import org.entirej.framework.core.enumerations.EJScreenType;

public class CompaniesActionProcessor extends DefaultFormActionProcessor
{
    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        form.getBlock(F_COMPANY.B_COMPANIES.ID).executeQuery();

        if (form.getBlock(F_COMPANY.B_COMPANIES.ID).getBlockRecords().size() == 0)
        {
            throw new EJApplicationException();
        }

        form.getBlock(F_COMPANY.B_COMPANIES_EDIT.ID).clear(true);
        EJRecord companyRecord = form.getBlock(F_COMPANY.B_COMPANIES.ID).getFocusedRecord();
        EJRecord editRecord = form.getBlock(F_COMPANY.B_COMPANIES_EDIT.ID).getFocusedRecord();

        editRecord.setValue(F_COMPANY.B_COMPANIES_EDIT.I_PAGE_TITLE, "Company Details");
        editRecord.setValue(F_COMPANY.B_COMPANIES_EDIT.I_INVOICE_INFORMATION_TITLE, "Invoice Information");
        editRecord.setValue(F_COMPANY.B_COMPANIES_EDIT.I_COMPANY_LOGO_TITLE, "Company Logo");

        editRecord.setValue(F_COMPANY.B_COMPANIES_EDIT.I_ID, companyRecord.getValue(F_COMPANY.B_COMPANIES.I_ID));
        editRecord.setValue(F_COMPANY.B_COMPANIES_EDIT.I_ADDRESS, companyRecord.getValue(F_COMPANY.B_COMPANIES.I_ADDRESS));
        editRecord.setValue(F_COMPANY.B_COMPANIES_EDIT.I_COUNTRY, companyRecord.getValue(F_COMPANY.B_COMPANIES.I_COUNTRY));
        editRecord.setValue(F_COMPANY.B_COMPANIES_EDIT.I_INVOICE_FOOTER, companyRecord.getValue(F_COMPANY.B_COMPANIES.I_INVOICE_FOOTER));
        editRecord.setValue(F_COMPANY.B_COMPANIES_EDIT.I_INVOICE_SUMMARY, companyRecord.getValue(F_COMPANY.B_COMPANIES.I_INVOICE_SUMMARY));
        editRecord.setValue(F_COMPANY.B_COMPANIES_EDIT.I_LOGO, companyRecord.getValue(F_COMPANY.B_COMPANIES.I_LOGO));
        editRecord.setValue(F_COMPANY.B_COMPANIES_EDIT.I_NAME, companyRecord.getValue(F_COMPANY.B_COMPANIES.I_NAME));
        editRecord.setValue(F_COMPANY.B_COMPANIES_EDIT.I_POST_CODE, companyRecord.getValue(F_COMPANY.B_COMPANIES.I_POST_CODE));
        editRecord.setValue(F_COMPANY.B_COMPANIES_EDIT.I_TOWN, companyRecord.getValue(F_COMPANY.B_COMPANIES.I_TOWN));
        editRecord.setValue(F_COMPANY.B_COMPANIES_EDIT.I_VAT_NR, companyRecord.getValue(F_COMPANY.B_COMPANIES.I_VAT_NR));
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_COMPANY.AC_EDIT_SAVE.equals(command))
        {
            String name = (String) record.getValue(F_COMPANY.B_COMPANIES_EDIT.I_NAME);

            boolean error = false;
            if (name == null || name.trim().length() == 0)
            {
                error = true;
                setError(form, F_COMPANY.B_COMPANIES.ID, F_COMPANY.B_COMPANIES.I_NAME, "Please enter a name for your company");
            }
            if (error)
            {
                throw new EJActionProcessorException();
            }
            
            EJRecord companyRecord = form.getBlock(F_COMPANY.B_COMPANIES.ID).getFocusedRecord();

            companyRecord.setValue(F_COMPANY.B_COMPANIES.I_NAME, record.getValue(F_COMPANY.B_COMPANIES.I_NAME));
            companyRecord.setValue(F_COMPANY.B_COMPANIES.I_ADDRESS, record.getValue(F_COMPANY.B_COMPANIES.I_ADDRESS));
            companyRecord.setValue(F_COMPANY.B_COMPANIES.I_COUNTRY, record.getValue(F_COMPANY.B_COMPANIES.I_COUNTRY));
            companyRecord.setValue(F_COMPANY.B_COMPANIES.I_INVOICE_FOOTER, record.getValue(F_COMPANY.B_COMPANIES.I_INVOICE_FOOTER));
            companyRecord.setValue(F_COMPANY.B_COMPANIES.I_INVOICE_SUMMARY, record.getValue(F_COMPANY.B_COMPANIES.I_INVOICE_SUMMARY));
            companyRecord.setValue(F_COMPANY.B_COMPANIES.I_LOGO, record.getValue(F_COMPANY.B_COMPANIES.I_LOGO));
            companyRecord.setValue(F_COMPANY.B_COMPANIES.I_POST_CODE, record.getValue(F_COMPANY.B_COMPANIES.I_POST_CODE));
            companyRecord.setValue(F_COMPANY.B_COMPANIES.I_TOWN, record.getValue(F_COMPANY.B_COMPANIES.I_TOWN));
            companyRecord.setValue(F_COMPANY.B_COMPANIES.I_VAT_NR, record.getValue(F_COMPANY.B_COMPANIES.I_VAT_NR));
            
            form.getBlock(F_COMPANY.B_COMPANIES.ID).updateRecord(companyRecord);
            form.saveChanges();
            
            form.getForm(F_TIME_ENTRY.ID).getBlock(F_TIME_ENTRY.B_COMPANY.ID).enterQuery();
        }
        else if (F_COMPANY.AC_ADD_LOGO.equals(command))
        {
            try
            {
                String filePath = EJRWTFileUpload.promptFileUpload("Logo");

                if (filePath == null || filePath.length() == 0)
                    return;
                // File image = new File(filePath);

                Path path = Paths.get(filePath);
                byte[] data = Files.readAllBytes(path);

                record.setValue(F_COMPANY.B_COMPANIES.I_LOGO, data);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void postFormSave(EJForm form) throws EJActionProcessorException
    {
        super.postFormSave(form);

        form.getForm(F_TIME_ENTRY.ID).getBlock(F_TIME_ENTRY.B_COMPANY.ID).executeQuery();
    }
}
