package org.entirej.ejinvoice.forms.company;

import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.login.PasswordHashGen;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.actionprocessor.EJDefaultFormActionProcessor;
import org.entirej.framework.core.enumerations.EJRecordType;
import org.entirej.framework.core.enumerations.EJScreenType;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;

public class ME001FormActionProcessor extends EJDefaultFormActionProcessor
{

    private static final String TF_PASSWORD = "PASSWORD";
    private static final String TF_USERNAME = "USERNAME";

    @Override
    public void preQuery(EJForm form, EJQueryCriteria queryCriteria) throws EJActionProcessorException
    {

//        queryCriteria.add(EJRestrictions.isNull(F_ME001.B_USER_BLOCK.I_ORGA_ID));
//        queryCriteria.add(EJRestrictions.isNull(F_ME001.B_USER_BLOCK.I_CLUB_ID));
    }

    @Override
    public void validateItem(EJForm form, EJRecord record, String itemName, EJScreenType screenType) throws EJActionProcessorException
    {
        super.validateItem(form, record, itemName, screenType);
    }

    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        super.newFormInstance(form);
//        validateToolbarState(form.getBlock(F_ME001.B_USERS_TOOLBAR.ID), false);
//        form.getBlock(F_ME001.B_USER_BLOCK.ID).executeQuery();
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
//        if (F_ME001.AC_TOOLBAR_ADD.equals(command))
//        {
//            form.getBlock(F_ME001.B_USER_BLOCK.ID).enterInsert(false);
//            return;
//        }
//        if (F_ME001.AC_TOOLBAR_UPDATE.equals(command))
//        {
//
//            form.getBlock(F_ME001.B_USER_BLOCK.ID).enterUpdate();
//            return;
//        }
//        if (F_ME001.AC_TOOLBAR_SEARCH.equals(command))
//        {
//            form.getBlock(F_ME001.B_USER_BLOCK.ID).enterQuery();
//            return;
//        }
//        if (F_ME001.AC_CHANGE_PWD.equals(command) && screenType == EJScreenType.UPDATE && record != null)
//        {
//            Object editPwd = record.getValue(F_ME001.B_USER_BLOCK.I__EDIT_PASSWORD);
//            boolean enable = editPwd != null && ((Integer) editPwd) == 1;
//            EJBlock block = form.getBlock(F_ME001.B_USER_BLOCK.ID);
//            block.getScreenItem(screenType, F_ME001.B_USER_BLOCK.I__NEW_PASSWORD).setEditable(enable);
//            block.getScreenItem(screenType, F_ME001.B_USER_BLOCK.I__NEW_PASSWORD).setMandatory(enable);
//            block.getScreenItem(screenType, F_ME001.B_USER_BLOCK.I__CONFIRM_PASSWORD).setEditable(enable);
//            block.getScreenItem(screenType, F_ME001.B_USER_BLOCK.I__CONFIRM_PASSWORD).setMandatory(enable);
//            block.getScreenItem(screenType, F_ME001.B_USER_BLOCK.I_CHANGE_PWD).setEditable(enable);
//            if (!enable)
//            {
//                block.getScreenItem(screenType, F_ME001.B_USER_BLOCK.I__NEW_PASSWORD).setValue(null);
//                block.getScreenItem(screenType, F_ME001.B_USER_BLOCK.I__CONFIRM_PASSWORD).setValue(null);
//            }
//            return;
//        }

    }

    @Override
    public void postDelete(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        super.postDelete(form, record);
        form.saveChanges();
    }

    @Override
    public void validateRecord(EJForm form, EJRecord record, EJRecordType recordType) throws EJActionProcessorException
    {

//        String error = null;
//        String errorItem = null;
//
//        if (F_ME001.B_USER_BLOCK.ID.equals(record.getBlockName()))
//        {
//
//            try
//            {
//                Object pwd = record.getValue(F_ME001.B_USER_BLOCK.I__NEW_PASSWORD);
//                Object confirmPwd = record.getValue(F_ME001.B_USER_BLOCK.I__CONFIRM_PASSWORD);
//
//                if (recordType == EJRecordType.INSERT)
//                {
//                    error = validatePassword(pwd, confirmPwd);
//                    if (error != null && error.length() > 0)
//                    {
//                        errorItem = F_ME001.B_USER_BLOCK.I__NEW_PASSWORD;
//                        return;
//                    }
//                    String username = (String) record.getValue(TF_USERNAME);
//                    if (ServiceRetriever.getUserService(form).doesUsernameExist(username))
//                    {
//                        error = "Entered Username already Exist!";
//                        errorItem = TF_USERNAME;
//                        return;
//                    }
//
//                    record.setValue(TF_PASSWORD, PasswordHashGen.toHash((String) pwd));
//
//                }
//                else if (recordType == EJRecordType.UPDATE)
//                {
//                    String username = (String) record.getValue(TF_USERNAME);
//                    Integer id = (Integer) record.getValue(F_ME001.B_USER_BLOCK.I_ID);
//                    if (ServiceRetriever.getUserService(form).doesUsernameExist(username, id))
//                    {
//                        error = "Entered Username already Exist!";
//                        errorItem = TF_USERNAME;
//                        return;
//                    }
//
//                    Object editPwd = record.getValue(F_ME001.B_USER_BLOCK.I__EDIT_PASSWORD);
//                    if (editPwd != null && ((Integer) editPwd) == 1)
//                    {
//                        error = validatePassword(pwd, confirmPwd);
//                        if (error != null && error.length() > 0)
//                        {
//                            errorItem = F_ME001.B_USER_BLOCK.I__NEW_PASSWORD;
//                            return;
//                        }
//                        record.setValue(TF_PASSWORD, PasswordHashGen.toHash((String) pwd));
//                    }
//
//                }
//            }
//            finally
//            {
//
//                if (error != null && error.length() > 0)
//                {
//
//                    throw new EJActionProcessorException(error);
//                }
//            }
//
//        }

    }

    private String validatePassword(final Object pwd, final Object confirmPwd)
    {

        String error = null;
        if (pwd == null || ((String) pwd).trim().length() == 0)
        {
            error = ("Password cannot be Empty!");

        }

        if (pwd != null && (!pwd.equals(confirmPwd)))
        {

            error = ("Entered passwords don't match!, Please Try again.");

        }
        return error;
    }


}
