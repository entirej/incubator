package org.entirej.ejinvoice.forms.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.entirej.applicationframework.rwt.file.EJRWTFileUpload;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.forms.constants.F_COMPANY;
import org.entirej.ejinvoice.forms.constants.F_PROJECTS;
import org.entirej.ejinvoice.forms.projects.ApprovedProjectItem;
import org.entirej.ejinvoice.forms.projects.MarkedForInvoiceProjectItem;
import org.entirej.ejinvoice.forms.projects.PlannedProjectItem;
import org.entirej.ejinvoice.forms.projects.ProjectService;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.data.controllers.EJQuestion;
import org.entirej.framework.core.enumerations.EJQuestionButton;
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
                e.printStackTrace();
            }
        }
        else if (F_COMPANY.AC_EDIT_COMPANY_DETAILS.equals(command))
        {
            form.getBlock(F_COMPANY.B_COMPANIES.ID).enterUpdate();
        }
        else if (F_COMPANY.AC_EDIT_USER.equals(command))
        {
            form.getBlock(F_COMPANY.B_USERS.ID).enterUpdate();
        }
        else if (F_COMPANY.AC_DELETE_USER.equals(command))
        {
            EJQuestion question = new EJQuestion(form, "ASK_DELETE_USER");
            question.setMessage(new EJMessage("Are you sure you want to delete this user?"));
            question.setButtonText(EJQuestionButton.ONE, "Yes");
            question.setButtonText(EJQuestionButton.TWO, "Cancel");
            question.setRecord(record);
            form.askQuestion(question);
        }
    }
    
    @Override
    public void questionAnswered(EJQuestion question) throws EJActionProcessorException
    {
        if (question.getName().equals("ASK_DELETE_USER") && question.getAnswer().equals(EJQuestionButton.ONE))
        {
            if (new UserService().canDeleteUser(question.getForm(), (User)question.getRecord().getBlockServicePojo()))
            {
                ArrayList<User> users = new ArrayList<User>();
                users.add((User)question.getRecord().getBlockServicePojo());
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
            User user = (User)question.getRecord().getBlockServicePojo();
            user.setActive(0);
            ArrayList<User> users = new ArrayList<User>();
            users.add(user);
            new UserBlockService().executeUpdate(question.getForm(), users);
        }
    }
}
