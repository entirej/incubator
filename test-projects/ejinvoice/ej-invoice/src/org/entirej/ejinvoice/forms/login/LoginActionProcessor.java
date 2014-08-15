package org.entirej.ejinvoice.forms.login;

import java.io.IOException;
import java.util.Locale;

import org.eclipse.rap.rwt.RWT;
import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.ApplicationParameters;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY;
import org.entirej.ejinvoice.forms.constants.F_LOGIN;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.enumerations.EJMessageLevel;
import org.entirej.framework.core.enumerations.EJScreenType;

public class LoginActionProcessor extends DefaultFormActionProcessor
{
    
    
    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        super.newFormInstance(form);
        
        String email = RWT.getSettingStore().getAttribute(ApplicationParameters.PARAM_EMAIL);
        if(email!=null)
        {
            form.getBlock(F_LOGIN.B_LOGON.ID).getScreenItem(EJScreenType.MAIN, F_LOGIN.B_LOGON.I_EMAIL).setValue(email);
        }
    }
    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_LOGIN.AC_LOGON.equals(command))
        {
            String username = (String) record.getValue(F_LOGIN.B_LOGON.I_EMAIL);
            String password = (String) record.getValue(F_LOGIN.B_LOGON.I_PASSWORD);
            try
            {
                RWT.getSettingStore().setAttribute(ApplicationParameters.PARAM_EMAIL, username);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            User user = ServiceRetriever.getUserService(form).getUser(username, password);

            if (user != null)
            {
                
                form.setApplicationLevelParameter(EJ_PROPERTIES.P_USER, user);
                form.setApplicationLevelParameter(EJ_PROPERTIES.P_USER_ID, user.getId());
                form.setApplicationLevelParameter(EJ_PROPERTIES.P_EMAIL, user.getEmail());
                form.setApplicationLevelParameter(EJ_PROPERTIES.P_NAME, user.getFirstName()+' '+user.getLastName());
                
                form.changeLocale(Locale.ENGLISH);
                
                form.openForm(F_TIME_ENTRY.ID);
            }
            else
            {
                throw new EJActionProcessorException(new EJMessage(EJMessageLevel.ERROR, "The username or password you entered is incorrect."));
            }
        }
        else if (F_LOGIN.AC_REGISTER.equals(command))
        {
            String email = (String) record.getValue(F_LOGIN.B_REGISTER.I_EMAIL);
            String firstName = (String) record.getValue(F_LOGIN.B_REGISTER.I_FIRST_NAME);
            String lastName = (String) record.getValue(F_LOGIN.B_REGISTER.I_LAST_NAME);
            
            String password = (String) record.getValue(F_LOGIN.B_LOGON.I_PASSWORD);
            String confirmPassword = (String) record.getValue(F_LOGIN.B_REGISTER.I_PASSWORD_RETYPE);
            
            if (ServiceRetriever.getUserService(form).doesEmailExist(email))
            {
                throw new EJActionProcessorException(new EJMessage(EJMessageLevel.ERROR, "This email address has already been registered."));
            }
            
            ServiceRetriever.getUserService(form).validateEmailAddress(email);
            String hashPassword = ServiceRetriever.getUserService(form).validatePassword(password, confirmPassword);

            User user = new User();
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(hashPassword);
            
            ServiceRetriever.getUserService(form).registerUser(form, user);
            
            form.showStackedCanvasPage(F_LOGIN.C_STACKED, F_LOGIN.C_STACKED_PAGES.LOGON);
        }
        else if (F_LOGIN.AC_SIGNUP.equals(command))
        {
            form.getBlock(F_LOGIN.B_REGISTER.ID).clear(true);
            form.showStackedCanvasPage(F_LOGIN.C_STACKED, F_LOGIN.C_STACKED_PAGES.REGISTER);
        }
        else if (F_LOGIN.AC_CANCEL_SIGNUP.equals(command))
        {
            form.getBlock(F_LOGIN.B_LOGON.ID).clear(true);
            form.showStackedCanvasPage(F_LOGIN.C_STACKED, F_LOGIN.C_STACKED_PAGES.LOGON);
        }
    }

}
