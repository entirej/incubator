package org.entirej.ejinvoice.forms.company;

import java.util.ArrayList;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.enums.UserRole;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY;
import org.entirej.ejinvoice.forms.constants.F_USERS;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.data.controllers.EJQuestion;
import org.entirej.framework.core.enumerations.EJQuestionButton;
import org.entirej.framework.core.enumerations.EJRecordType;
import org.entirej.framework.core.enumerations.EJScreenType;

public class UsersActionProcessor extends DefaultFormActionProcessor
{
    @Override
    public void postQuery(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_USERS.B_USERS.ID.equals(record.getBlockName()))
        {
            record.setValue(F_USERS.B_USERS.I_FIRST_LAST_NAME, record.getValue(F_USERS.B_USERS.I_FIRST_NAME)+" "+record.getValue(F_USERS.B_USERS.I_LAST_NAME));
            record.setValue(F_USERS.B_USERS.I_ROLE_DISPLAY, record.getValue(F_USERS.B_USERS.I_ROLE));

            if (record.getValue(F_USERS.B_USERS.I_ROLE).equals(UserRole.OWNER.toString()))
            {
                // Cannot delete the owner
                record.setValue(F_USERS.B_USERS.I_EDIT, "/icons/edit10.gif");
            }
            else
            {
                record.setValue(F_USERS.B_USERS.I_EDIT, "/icons/edit10.gif");
                record.setValue(F_USERS.B_USERS.I_DELETE, "/icons/delete10.png");
            }
        }
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_USERS.AC_EDIT_USER.equals(command))
        {
            form.getBlock(F_USERS.B_USERS_EDIT.ID).clear(true);
            EJRecord editRecord = form.getBlock(F_USERS.B_USERS_EDIT.ID).getFocusedRecord();
            
            editRecord.setValue(F_USERS.B_USERS_EDIT.I_ID, record.getValue(F_USERS.B_USERS.I_ID));
            editRecord.setValue(F_USERS.B_USERS_EDIT.I_PAGE_TITLE, record.getValue(F_USERS.B_USERS.I_FIRST_LAST_NAME)+ " - Edit Profile");
            editRecord.setValue(F_USERS.B_USERS_EDIT.I_ACTIVE, record.getValue(F_USERS.B_USERS.I_ACTIVE));
            editRecord.setValue(F_USERS.B_USERS_EDIT.I_ADDRESS, record.getValue(F_USERS.B_USERS.I_ADDRESS));
            editRecord.setValue(F_USERS.B_USERS_EDIT.I_EMAIL, record.getValue(F_USERS.B_USERS.I_EMAIL));
            editRecord.setValue(F_USERS.B_USERS_EDIT.I_FIRST_NAME, record.getValue(F_USERS.B_USERS.I_FIRST_NAME));
            editRecord.setValue(F_USERS.B_USERS_EDIT.I_LAST_NAME, record.getValue(F_USERS.B_USERS.I_LAST_NAME));
            editRecord.setValue(F_USERS.B_USERS_EDIT.I_LOCALE, record.getValue(F_USERS.B_USERS.I_LOCALE));
            editRecord.setValue(F_USERS.B_USERS_EDIT.I_LOCALE_LANGUAGE, record.getValue(F_USERS.B_USERS.I_LOCALE_LANGUAGE));
            editRecord.setValue(F_USERS.B_USERS_EDIT.I_LOCALE_COUNTRY, record.getValue(F_USERS.B_USERS.I_LOCALE_COUNTRY));
            editRecord.setValue(F_USERS.B_USERS_EDIT.I_NOTES, record.getValue(F_USERS.B_USERS.I_NOTES));
            editRecord.setValue(F_USERS.B_USERS_EDIT.I_POST_CODE, record.getValue(F_USERS.B_USERS.I_POST_CODE));
            editRecord.setValue(F_USERS.B_USERS_EDIT.I_ROLE, record.getValue(F_USERS.B_USERS.I_ROLE));
            editRecord.setValue(F_USERS.B_USERS_EDIT.I_TOWN, record.getValue(F_USERS.B_USERS.I_TOWN));
            
            form.showStackedCanvasPage(F_USERS.C_MAIN_STACK, F_USERS.C_MAIN_STACK_PAGES.EDIT);
            
//            form.getBlock(F_USERS.B_USERS.ID).enterUpdate();
        }
        else if (F_USERS.AC_EDIT_SAVE.equals(command))
        {
            EJRecord baseRecord = form.getBlock(F_USERS.B_USERS.ID).getFocusedRecord();
//            baseRecord.setValue(F_USERS.B_USERS.I_ID, record.getValue(F_USERS.B_USERS.I_ID));
            baseRecord.setValue(F_USERS.B_USERS.I_ACTIVE, record.getValue(F_USERS.B_USERS_EDIT.I_ACTIVE));
            baseRecord.setValue(F_USERS.B_USERS.I_ADDRESS, record.getValue(F_USERS.B_USERS_EDIT.I_ADDRESS));
            baseRecord.setValue(F_USERS.B_USERS.I_EMAIL, record.getValue(F_USERS.B_USERS_EDIT.I_EMAIL));
            baseRecord.setValue(F_USERS.B_USERS.I_FIRST_NAME, record.getValue(F_USERS.B_USERS_EDIT.I_FIRST_NAME));
            baseRecord.setValue(F_USERS.B_USERS.I_LAST_NAME, record.getValue(F_USERS.B_USERS_EDIT.I_LAST_NAME));
            baseRecord.setValue(F_USERS.B_USERS.I_LOCALE, record.getValue(F_USERS.B_USERS_EDIT.I_LOCALE));
            baseRecord.setValue(F_USERS.B_USERS.I_LOCALE_LANGUAGE, record.getValue(F_USERS.B_USERS_EDIT.I_LOCALE_LANGUAGE));
            baseRecord.setValue(F_USERS.B_USERS.I_LOCALE_COUNTRY, record.getValue(F_USERS.B_USERS_EDIT.I_LOCALE_COUNTRY));
            baseRecord.setValue(F_USERS.B_USERS.I_NOTES, record.getValue(F_USERS.B_USERS_EDIT.I_NOTES));
            baseRecord.setValue(F_USERS.B_USERS.I_POST_CODE, record.getValue(F_USERS.B_USERS_EDIT.I_POST_CODE));
            
            baseRecord.setValue(F_USERS.B_USERS.I_TOWN, record.getValue(F_USERS.B_USERS_EDIT.I_TOWN));
            
            User user = (User) baseRecord.getBlockServicePojo();
            if (!user.getInitialRole().equals(UserRole.OWNER))
            {
                baseRecord.setValue(F_USERS.B_USERS.I_ROLE, record.getValue(F_USERS.B_USERS_EDIT.I_ROLE));
            }
            
            form.getBlock(F_USERS.B_USERS.ID).updateRecord(baseRecord);
            form.saveChanges();
            
            form.getBlock(F_USERS.B_USERS_EDIT.ID).clear(true);
            form.showStackedCanvasPage(F_USERS.C_MAIN_STACK, F_USERS.C_MAIN_STACK_PAGES.USERS);
        }
        else if (F_USERS.AC_EDIT_CANCEL.equals(command))
        {
            form.getBlock(F_USERS.B_USERS_EDIT.ID).clear(true);
            form.showStackedCanvasPage(F_USERS.C_MAIN_STACK, F_USERS.C_MAIN_STACK_PAGES.USERS);
        }
        else if (F_USERS.AC_DELETE_USER.equals(command))
        {
            EJQuestion question = new EJQuestion(form, "ASK_DELETE_USER");
            question.setMessage(new EJMessage("Are you sure you want to delete this user?"));
            question.setButtonText(EJQuestionButton.ONE, "Yes");
            question.setButtonText(EJQuestionButton.TWO, "Cancel");
            question.setRecord(record);
            form.askQuestion(question);
        }
        else if (F_USERS.AC_CHANGE_EMAIL.equals(command) && (screenType.equals(EJScreenType.UPDATE) || screenType.equals(EJScreenType.INSERT)))
        {
            if (record.getValue(F_USERS.B_USERS.I_CHANGE_EMAIL).equals("Y"))
            {
                form.getBlock(F_USERS.B_USERS.ID).getScreenItem(screenType, F_USERS.B_USERS.I_EMAIL).setEditable(true);
                form.getBlock(F_USERS.B_USERS.ID).getScreenItem(screenType, F_USERS.B_USERS.I_CONFIRM_EMAIL).setEditable(true);
            }
            else
            {
                form.getBlock(F_USERS.B_USERS.ID).getScreenItem(screenType, F_USERS.B_USERS.I_EMAIL).setEditable(false);
                form.getBlock(F_USERS.B_USERS.ID).getScreenItem(screenType, F_USERS.B_USERS.I_CONFIRM_EMAIL).setEditable(false);
            }
        }
        else if (F_USERS.AC_CHANGE_PASSWORD.equals(command) && screenType.equals(EJScreenType.UPDATE))
        {
            if (record.getValue(F_USERS.B_USERS.I_CHANGE_PASSWORD).equals("Y") && (screenType.equals(EJScreenType.UPDATE) || screenType.equals(EJScreenType.INSERT)))
            {
                form.getBlock(F_USERS.B_USERS.ID).getScreenItem(screenType, F_USERS.B_USERS.I_INITIAL_PASSWORD).setEditable(true);
                form.getBlock(F_USERS.B_USERS.ID).getScreenItem(screenType, F_USERS.B_USERS.I_CONFIRM_PASSWORD).setEditable(true);
            }
            else
            {
                form.getBlock(F_USERS.B_USERS.ID).getScreenItem(screenType, F_USERS.B_USERS.I_INITIAL_PASSWORD).setEditable(false);
                form.getBlock(F_USERS.B_USERS.ID).getScreenItem(screenType, F_USERS.B_USERS.I_CONFIRM_PASSWORD).setEditable(false);
            }
        }
        else if (F_USERS.AC_CREATE_NEW_USER.equals(command))
        {
            form.getBlock(F_USERS.B_USERS.ID).enterInsert(false);
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

    @Override
    public void preOpenScreen(EJBlock block, EJRecord record, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_USERS.B_USERS.ID.equals(block.getName()) && EJScreenType.UPDATE.equals(screenType))
        {
            record.setValue(F_USERS.B_USERS.I_CHANGE_EMAIL, "N");
            record.setValue(F_USERS.B_USERS.I_CHANGE_PASSWORD, "N");
            record.setValue(F_USERS.B_USERS.I_CONFIRM_EMAIL, null);
            record.setValue(F_USERS.B_USERS.I_INITIAL_PASSWORD, null);
            record.setValue(F_USERS.B_USERS.I_CONFIRM_PASSWORD, null);

            block.getScreenItem(screenType, F_USERS.B_USERS.I_EMAIL).setEditable(false);
            block.getScreenItem(screenType, F_USERS.B_USERS.I_CONFIRM_EMAIL).setEditable(false);
            block.getScreenItem(screenType, F_USERS.B_USERS.I_INITIAL_PASSWORD).setEditable(false);
            block.getScreenItem(screenType, F_USERS.B_USERS.I_CONFIRM_PASSWORD).setEditable(false);

            if (record.getValue(F_USERS.B_USERS.I_ROLE).equals("OWNER"))
            {
                block.getScreenItem(screenType, F_USERS.B_USERS.I_ROLE).setVisible(false);
            }
            else
            {
                block.getScreenItem(screenType, F_USERS.B_USERS.I_ROLE).setVisible(true);
            }
        }
    }

    @Override
    public void validateRecord(EJForm form, EJRecord record, EJRecordType recordType) throws EJActionProcessorException
    {
        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();

        if (F_USERS.B_USERS.ID.equals(record.getBlockName()))
        {
            if (EJRecordType.UPDATE.equals(recordType))
            {
                if (record.getValue(F_USERS.B_USERS.I_CHANGE_PASSWORD).equals("Y"))
                {
                    String hashPassword = ServiceRetriever.getUserService(form).validatePassword((String) record.getValue(F_USERS.B_USERS.I_INITIAL_PASSWORD), (String) record.getValue(F_USERS.B_USERS.I_CONFIRM_PASSWORD));
                    record.setValue(F_USERS.B_USERS.I_PASSWORD, hashPassword);
                }
                if (record.getValue(F_USERS.B_USERS.I_CHANGE_EMAIL).equals("Y"))
                {
                    String email = (String) record.getValue(F_USERS.B_USERS.I_EMAIL);
                    String confirmEmail = (String) record.getValue(F_USERS.B_USERS.I_CONFIRM_EMAIL);

                    ServiceRetriever.getUserService(form).validateEmailAddress(form, email, confirmEmail, companyId, (Integer) record.getValue(F_USERS.B_USERS.I_ID));
                }

                User user = (User) record.getBlockServicePojo();
                if (user.getInitialRole().equals(UserRole.OWNER))
                {
                    // The role is automatically set to EMPLOYEE because of the
                    // Radio Group not containing the value Owner
                    record.setValue(F_USERS.B_USERS.I_ROLE, UserRole.OWNER.toString());
                }
            }
            else if (EJRecordType.INSERT.equals(recordType))
            {
                String email = (String) record.getValue(F_USERS.B_USERS.I_EMAIL);
                String confirmEmail = (String) record.getValue(F_USERS.B_USERS.I_CONFIRM_EMAIL);

                String hashPassword = ServiceRetriever.getUserService(form).validatePassword((String) record.getValue(F_USERS.B_USERS.I_INITIAL_PASSWORD), (String) record.getValue(F_USERS.B_USERS.I_CONFIRM_PASSWORD));
                record.setValue(F_USERS.B_USERS.I_PASSWORD, hashPassword);
                ServiceRetriever.getUserService(form).validateEmailAddress(form, email, confirmEmail, companyId, (Integer) record.getValue(F_USERS.B_USERS.I_ID));
            }
        }
    }

}
