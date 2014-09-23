package org.entirej.ejinvoice.forms.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.entirej.applicationframework.rwt.file.EJRWTFileUpload;
import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.enums.UserRole;
import org.entirej.ejinvoice.forms.constants.F_COMPANY;
import org.entirej.ejinvoice.forms.constants.F_MASTER_DATA_CONTACT_TYPES;
import org.entirej.ejinvoice.forms.constants.F_MASTER_DATA_SALUTATION;
import org.entirej.ejinvoice.forms.constants.F_MASTER_DATA_VAT_RATES;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY;
import org.entirej.framework.core.EJActionProcessorException;
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
    }

    @Override
    public void tabPageChanged(EJForm form, String tabCanvasName, String tabPageName) throws EJActionProcessorException
    {
        if (F_COMPANY.C_MAIN_TAB_PAGES.CONTACT_TYPES.equals(tabPageName))
        {
            EJForm embeddedForm = form.getEmbeddedForm(F_MASTER_DATA_CONTACT_TYPES.ID, F_COMPANY.C_CONTACT_TYPES_FORM);
           
            EJBlock embeddedBlock = embeddedForm.getBlock(F_MASTER_DATA_CONTACT_TYPES.B_CONTACT_TYPES.ID);
            if (embeddedBlock.getBlockRecords().size() == 0)
            {
                embeddedBlock.executeQuery();
            }
            
        }
        else if (F_COMPANY.C_MAIN_TAB_PAGES.SALUTATIONS.equals(tabPageName))
        {
            EJForm embeddedForm = form.getEmbeddedForm(F_MASTER_DATA_SALUTATION.ID, F_COMPANY.C_SALUTATIONS_FORM);
            
            EJBlock embeddedBlock = embeddedForm.getBlock(F_MASTER_DATA_SALUTATION.B_SALUTATIONS.ID);
            if (embeddedBlock.getBlockRecords().size() == 0)
            {
                embeddedBlock.executeQuery();
            }  
        }
        else if (F_COMPANY.C_MAIN_TAB_PAGES.VAT_RATES.equals(tabPageName))
        {
            EJForm embeddedForm = form.getEmbeddedForm(F_MASTER_DATA_VAT_RATES.ID, F_COMPANY.C_VAT_RATES_FORM);
            
            EJBlock embeddedBlock = embeddedForm.getBlock(F_MASTER_DATA_VAT_RATES.B_VAT_RATES.ID);
            if (embeddedBlock.getBlockRecords().size() == 0)
            {
                embeddedBlock.executeQuery();
            } 
        }
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {

        if (F_COMPANY.AC_ADD_LOGO.equals(command))
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
        else if (F_COMPANY.AC_EDIT_COMPANY_DETAILS.equals(command))
        {
            form.getBlock(F_COMPANY.B_COMPANIES.ID).enterUpdate();
        }
    }

    @Override
    public void postFormSave(EJForm form) throws EJActionProcessorException
    {
        super.postFormSave(form);
        
        form.getForm(F_TIME_ENTRY.ID).getBlock(F_TIME_ENTRY.B_COMPANY.ID).executeQuery();
    }

    @Override
    public void questionAnswered(EJQuestion question) throws EJActionProcessorException
    {
        if (question.getName().equals("ASK_DELETE_USER") && question.getAnswer().equals(EJQuestionButton.ONE))
        {
            if (ServiceRetriever.getUserService(question.getForm()).canDeleteUser(question.getForm(), (User) question.getRecord().getBlockServicePojo()))
            {
                ArrayList<User> users = new ArrayList<User>();
                users.add((User) question.getRecord().getBlockServicePojo());
                new UserBlockService().executeDelete(question.getForm(), users);
            }
            else
            {
                EJQuestion inactivateUser = new EJQuestion(question.getForm(), "ASK_INACTIVATE_USER");
                inactivateUser.setMessage(new EJMessage("This user has entered working hours and cannot be deleted, do you want to de-activate this user?"));
                inactivateUser.setButtonText(EJQuestionButton.ONE, "Yes");
                inactivateUser.setButtonText(EJQuestionButton.TWO, "Cancel");
                inactivateUser.setRecord(question.getRecord());
                inactivateUser.getForm().askQuestion(inactivateUser);
            }
        }
        else if (question.getName().equals("ASK_INACTIVATE_USER") && question.getAnswer().equals(EJQuestionButton.ONE))
        {
            User user = (User) question.getRecord().getBlockServicePojo();
            user.setActive(0);
            ArrayList<User> users = new ArrayList<User>();
            users.add(user);
            new UserBlockService().executeUpdate(question.getForm(), users);
        }
    }

}
