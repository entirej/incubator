package org.entirej.ejinvoice.forms.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.entirej.applicationframework.rwt.file.EJRWTFileUpload;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.forms.constants.F_COMPANY;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.enumerations.EJScreenType;

public class CompaniesActionProcessor extends DefaultFormActionProcessor
{
    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
//        form.getBlock(F_COMPANY.B_COMPANIES.ID).executeQuery();
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {

        if (F_COMPANY.AC_ADD_LOGO.equals(command))
        {
            try
            {
                String filePath = EJRWTFileUpload.promptFileUpload("Logo");

                if(filePath==null || filePath.length()==0)
                    return;
                //File image = new File(filePath);

                Path path = Paths.get(filePath);
                byte[] data = Files.readAllBytes(path);
                
                record.setValue(F_COMPANY.B_COMPANIES.I_LOGO, data);
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (F_COMPANY.AC_EDIT_COMPANY_DETAILS.equals(command))
        {
            form.getBlock(F_COMPANY.B_COMPANIES.ID).enterUpdate();
            return;
        }
    }
}
