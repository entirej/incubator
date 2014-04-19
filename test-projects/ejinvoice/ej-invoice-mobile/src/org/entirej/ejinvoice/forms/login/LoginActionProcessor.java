package org.entirej.ejinvoice.forms.login;

import org.entirej.applicationframework.tmt.application.launcher.EJTMTContext;
import org.entirej.applicationframework.tmt.pages.EJTMTFormPageActions;
import org.entirej.ejinvoice.ApplicationParameters;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.constants.F_LOGIN;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.data.controllers.EJApplicationLevelParameter;
import org.entirej.framework.core.enumerations.EJMessageLevel;
import org.entirej.framework.core.enumerations.EJScreenType;

public class LoginActionProcessor extends DefaultFormActionProcessor
{
    private static final String LOGIN_PAGE_ID = "LOGIN";


    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        super.newFormInstance(form);
        EJApplicationLevelParameter userParameter = form.getApplicationLevelParameter(ApplicationParameters.PARAM_USER);
        if(userParameter!=null && userParameter.getValue()!=null)
        {
            form.showStackedCanvasPage( F_LOGIN.C_STACKED, F_LOGIN.C_STACKED_PAGES.LAUNCHER);
        }
        else
        {
            form.showStackedCanvasPage( F_LOGIN.C_STACKED, F_LOGIN.C_STACKED_PAGES.LOGON);
        }
        
    }
    
    @Override
    public void stackedPageChanged(EJForm form, String stackedCanvasName, String stackedPageName) throws EJActionProcessorException
    {
        
        if(F_LOGIN.C_STACKED.equals(stackedCanvasName))
        {
            String pageTitle = "Login";
            boolean actionLogin = true;
            boolean actionSignup = true;
            boolean actionForgot = true;
            boolean actionLogout = false;
            if(F_LOGIN.C_STACKED_PAGES.LAUNCHER.equals(stackedPageName))
            {
                 actionLogin = false;
                 actionSignup = false;
                 actionForgot = false;
                 actionForgot = false;
                 actionLogout = true;
                 pageTitle ="Launcher";
            }
            else if(F_LOGIN.C_STACKED_PAGES.FORGOT.equals(stackedPageName)) 
            {
                actionForgot = false;
                pageTitle ="Forgot Password";
            }
            else if(F_LOGIN.C_STACKED_PAGES.LOGON.equals(stackedPageName)) 
            {
                actionLogin = false;
            }
            else if(F_LOGIN.C_STACKED_PAGES.REGISTER.equals(stackedPageName)) 
            {
                actionSignup = false;
                pageTitle ="Signup Account";
            }
            
            EJTMTFormPageActions.setActionVisibleByPageId(LOGIN_PAGE_ID, F_LOGIN.AC_LOGIN, actionLogin);
            EJTMTFormPageActions.setActionVisibleByPageId(LOGIN_PAGE_ID, F_LOGIN.AC_SIGNUP, actionSignup);
            EJTMTFormPageActions.setActionVisibleByPageId(LOGIN_PAGE_ID, F_LOGIN.AC_FORGOT, actionForgot);
            EJTMTFormPageActions.setActionVisibleByPageId(LOGIN_PAGE_ID, F_LOGIN.AC_LOGOUT, actionLogout);
            EJTMTContext.getTabrisUI().getPageOperator().setCurrentPageTitle(pageTitle);
            
        }
    }
    
    
    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_LOGIN.AC_LOGIN_EJ.equals(command))
        {
            String username = "paul.harrison@mojave-i.com";
            String password = "entirej";
            
            User user = ServiceRetriever.getUserService(form).getUser(username, password);

            if (user != null)
            {
                
                form.setApplicationLevelParameter(ApplicationParameters.PARAM_USER, user);
                form.setApplicationLevelParameter(ApplicationParameters.PARAM_EMAIL, user.getEmail());
                form.setApplicationLevelParameter(ApplicationParameters.PARAM_NAME, user.getFirstName()+' '+user.getLastName());
              
                form.showStackedCanvasPage(F_LOGIN.C_STACKED, F_LOGIN.C_STACKED_PAGES.LAUNCHER);
                return;
            }
            else
            {
                throw new EJActionProcessorException(new EJMessage(EJMessageLevel.ERROR, "The username or password you entered is incorrect."));
            }
        }
        
        if (F_LOGIN.AC_LOGON.equals(command))
        {
            String username = (String) record.getValue(F_LOGIN.B_LOGON.I_EMAIL);
            String password = (String) record.getValue(F_LOGIN.B_LOGON.I_PASSWORD);

            User user = ServiceRetriever.getUserService(form).getUser(username, password);

            if (user != null)
            {
                
                form.setApplicationLevelParameter(ApplicationParameters.PARAM_USER, user);
                form.setApplicationLevelParameter(ApplicationParameters.PARAM_EMAIL, user.getEmail());
                form.setApplicationLevelParameter(ApplicationParameters.PARAM_NAME, user.getFirstName()+' '+user.getLastName());
                
                form.showStackedCanvasPage( F_LOGIN.C_STACKED, F_LOGIN.C_STACKED_PAGES.LAUNCHER);
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
        else if (F_LOGIN.AC_LOGIN.equals(command))
        {
            form.getBlock(F_LOGIN.B_LOGON.ID).clear(true);
            form.showStackedCanvasPage(F_LOGIN.C_STACKED, F_LOGIN.C_STACKED_PAGES.LOGON);
        }
        else if (F_LOGIN.AC_LOGOUT.equals(command))
        {
            form.setApplicationLevelParameter(ApplicationParameters.PARAM_USER, null);
            form.setApplicationLevelParameter(ApplicationParameters.PARAM_EMAIL, null);
            form.setApplicationLevelParameter(ApplicationParameters.PARAM_NAME, null);
          
            form.showStackedCanvasPage(F_LOGIN.C_STACKED, F_LOGIN.C_STACKED_PAGES.LOGON);
        }
        else if (F_LOGIN.AC_SIGNUP.equals(command))
        {
            form.getBlock(F_LOGIN.B_REGISTER.ID).clear(true);
            form.showStackedCanvasPage(F_LOGIN.C_STACKED, F_LOGIN.C_STACKED_PAGES.REGISTER);
        }
    }

}
