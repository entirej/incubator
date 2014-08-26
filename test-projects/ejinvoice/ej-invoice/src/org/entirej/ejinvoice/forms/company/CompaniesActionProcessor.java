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
import org.entirej.ejinvoice.forms.constants.F_COMPANY;
import org.entirej.ejinvoice.forms.constants.F_PROJECTS;
import org.entirej.ejinvoice.forms.login.UserService;
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
        // form.getBlock(F_COMPANY.B_COMPANIES.ID).executeQuery();
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
        else if (F_COMPANY.AC_CHANGE_EMAIL.equals(command) && (screenType.equals(EJScreenType.UPDATE) || screenType.equals(EJScreenType.INSERT)))
        {
            if (record.getValue(F_COMPANY.B_USERS.I_CHANGE_EMAIL).equals("Y"))
            {
                form.getBlock(F_COMPANY.B_USERS.ID).getScreenItem(screenType, F_COMPANY.B_USERS.I_EMAIL).setEditable(true);
                form.getBlock(F_COMPANY.B_USERS.ID).getScreenItem(screenType, F_COMPANY.B_USERS.I_CONFIRM_EMAIL).setEditable(true);
            }
            else
            {
                form.getBlock(F_COMPANY.B_USERS.ID).getScreenItem(screenType, F_COMPANY.B_USERS.I_EMAIL).setEditable(false);
                form.getBlock(F_COMPANY.B_USERS.ID).getScreenItem(screenType, F_COMPANY.B_USERS.I_CONFIRM_EMAIL).setEditable(false);
            }
        }
        else if (F_COMPANY.AC_CHANGE_PASSWORD.equals(command) && screenType.equals(EJScreenType.UPDATE))
        {
            if (record.getValue(F_COMPANY.B_USERS.I_CHANGE_PASSWORD).equals("Y") && (screenType.equals(EJScreenType.UPDATE) || screenType.equals(EJScreenType.INSERT)))
            {
                form.getBlock(F_COMPANY.B_USERS.ID).getScreenItem(screenType, F_COMPANY.B_USERS.I_INITIAL_PASSWORD).setEditable(true);
                form.getBlock(F_COMPANY.B_USERS.ID).getScreenItem(screenType, F_COMPANY.B_USERS.I_CONFIRM_PASSWORD).setEditable(true);
            }
            else
            {
                form.getBlock(F_COMPANY.B_USERS.ID).getScreenItem(screenType, F_COMPANY.B_USERS.I_INITIAL_PASSWORD).setEditable(false);
                form.getBlock(F_COMPANY.B_USERS.ID).getScreenItem(screenType, F_COMPANY.B_USERS.I_CONFIRM_PASSWORD).setEditable(false);
            }
        }
        else if (F_COMPANY.AC_CREATE_NEW_USER.equals(command))
        {
            form.getBlock(F_COMPANY.B_USERS.ID).enterInsert(false);
        }
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

    @Override
    public void preOpenScreen(EJBlock block, EJRecord record, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_COMPANY.B_USERS.ID.equals(block.getName()) && EJScreenType.UPDATE.equals(screenType))
        {
            record.setValue(F_COMPANY.B_USERS.I_CHANGE_EMAIL, "N");
            record.setValue(F_COMPANY.B_USERS.I_CHANGE_PASSWORD, "N");
            record.setValue(F_COMPANY.B_USERS.I_CONFIRM_EMAIL, null);
            record.setValue(F_COMPANY.B_USERS.I_INITIAL_PASSWORD, null);
            record.setValue(F_COMPANY.B_USERS.I_CONFIRM_PASSWORD, null);
            
            block.getScreenItem(screenType, F_COMPANY.B_USERS.I_EMAIL).setEditable(false);
            block.getScreenItem(screenType, F_COMPANY.B_USERS.I_CONFIRM_EMAIL).setEditable(false);
            block.getScreenItem(screenType, F_COMPANY.B_USERS.I_INITIAL_PASSWORD).setEditable(false);
            block.getScreenItem(screenType, F_COMPANY.B_USERS.I_CONFIRM_PASSWORD).setEditable(false);
        }
    }

    @Override
    public void validateRecord(EJForm form, EJRecord record, EJRecordType recordType) throws EJActionProcessorException
    {
        Integer companyId = (Integer)form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
        

        if (F_COMPANY.B_USERS.ID.equals(record.getBlockName()))
        {
            if (EJRecordType.UPDATE.equals(recordType))
            {
                if (record.getValue(F_COMPANY.B_USERS.I_CHANGE_PASSWORD).equals("Y"))
                {
                    String hashPassword = ServiceRetriever.getUserService(form).validatePassword((String) record.getValue(F_COMPANY.B_USERS.I_INITIAL_PASSWORD), (String) record.getValue(F_COMPANY.B_USERS.I_CONFIRM_PASSWORD));
                    record.setValue(F_COMPANY.B_USERS.I_PASSWORD, hashPassword);
                }
                if (record.getValue(F_COMPANY.B_USERS.I_CHANGE_EMAIL).equals("Y"))
                {
                    String email = (String) record.getValue(F_COMPANY.B_USERS.I_EMAIL);
                    String confirmEmail = (String) record.getValue(F_COMPANY.B_USERS.I_CONFIRM_EMAIL);

                    ServiceRetriever.getUserService(form).validateEmailAddress(form, email, confirmEmail, companyId, (Integer) record.getValue(F_COMPANY.B_USERS.I_ID));
                }
            }
            else if (EJRecordType.INSERT.equals(recordType))
            {
                String email = (String) record.getValue(F_COMPANY.B_USERS.I_EMAIL);
                String confirmEmail = (String) record.getValue(F_COMPANY.B_USERS.I_CONFIRM_EMAIL);

                String hashPassword = ServiceRetriever.getUserService(form).validatePassword((String) record.getValue(F_COMPANY.B_USERS.I_INITIAL_PASSWORD), (String) record.getValue(F_COMPANY.B_USERS.I_CONFIRM_PASSWORD));
                record.setValue(F_COMPANY.B_USERS.I_PASSWORD, hashPassword);
                ServiceRetriever.getUserService(form).validateEmailAddress(form, email, confirmEmail, companyId, (Integer) record.getValue(F_COMPANY.B_USERS.I_ID));
            }
        }
    }

}
