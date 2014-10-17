package org.entirej.ejinvoice.forms.login;

import java.io.IOException;
import java.util.Locale;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rwt.EJ_RWT;
import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.ApplicationParameters;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.email.EmailUtil;
import org.entirej.ejinvoice.enums.UserRole;
import org.entirej.ejinvoice.forms.company.Company;
import org.entirej.ejinvoice.forms.company.User;
import org.entirej.ejinvoice.forms.constants.F_LOGIN;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.EJScreenItem;
import org.entirej.framework.core.enumerations.EJMessageLevel;
import org.entirej.framework.core.enumerations.EJScreenType;

public class LoginActionProcessor extends DefaultFormActionProcessor
{

    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        super.newFormInstance(form);

        String email = RWT.getSettingStore().getAttribute(ApplicationParameters.PARAM_EMAIL);
        if (email != null)
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
            User user = ServiceRetriever.getUserService(form).getUser(form, username, password);

            if (user != null)
            {

                form.setApplicationLevelParameter(EJ_PROPERTIES.P_USER, user);
                form.setApplicationLevelParameter(EJ_PROPERTIES.P_USER_ID, user.getId());
                form.setApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID, user.getCompanyId());
                form.setApplicationLevelParameter(EJ_PROPERTIES.P_EMAIL, user.getEmail());
                form.setApplicationLevelParameter(EJ_PROPERTIES.P_NAME, user.getFirstName() + ' ' + user.getLastName());

                form.changeLocale(new Locale("en", "GB"));

                form.openForm(F_TIME_ENTRY.ID);
            }
            else
            {
                EJScreenItem screenItem = form.getBlock(F_LOGIN.B_LOGON.ID).getScreenItem(EJScreenType.MAIN, F_LOGIN.B_LOGON.I_PASSWORD);
                screenItem.setItemRendererProperty(EJ_RWT.PROPERTY_CSS_KEY , "error");
                throw new EJActionProcessorException(new EJMessage(EJMessageLevel.ERROR, "The username or password you entered is incorrect."));
            }
        }
        else if (F_LOGIN.AC_REGISTER.equals(command))
        {
            EJBlock compBlock = form.getBlock(F_LOGIN.B_COMPANY.ID);

            int newCompanyId = PKSequenceService.getPKSequence(form.getConnection());
            int newUserId = PKSequenceService.getPKSequence(form.getConnection());

            String email = (String) compBlock.getScreenItem(EJScreenType.MAIN, F_LOGIN.B_COMPANY.I_EMAIL_ADDRESS).getValue();
            String confirmEmail = (String) compBlock.getScreenItem(EJScreenType.MAIN, F_LOGIN.B_COMPANY.I_EMAIL_ADDRESS_CONFIRM).getValue();
            String password = (String) compBlock.getScreenItem(EJScreenType.MAIN, F_LOGIN.B_COMPANY.I_PASSWORD).getValue();
            String confirmPassword = (String) compBlock.getScreenItem(EJScreenType.MAIN, F_LOGIN.B_COMPANY.I_PASSWORD_CONFIRM).getValue();

            String firstName = (String) compBlock.getScreenItem(EJScreenType.MAIN, F_LOGIN.B_COMPANY.I_FIRST_NAME).getValue();
            String lastName = (String) compBlock.getScreenItem(EJScreenType.MAIN, F_LOGIN.B_COMPANY.I_LAST_NAME).getValue();
            String address = (String) compBlock.getScreenItem(EJScreenType.MAIN, F_LOGIN.B_COMPANY.I_ADDRESS).getValue();
            String country = (String) compBlock.getScreenItem(EJScreenType.MAIN, F_LOGIN.B_COMPANY.I_COUNTRY).getValue();
            String companyName = (String) compBlock.getScreenItem(EJScreenType.MAIN, F_LOGIN.B_COMPANY.I_NAME).getValue();
            String postCode = (String) compBlock.getScreenItem(EJScreenType.MAIN, F_LOGIN.B_COMPANY.I_POST_CODE).getValue();
            String town = (String) compBlock.getScreenItem(EJScreenType.MAIN, F_LOGIN.B_COMPANY.I_TOWN).getValue();

            ServiceRetriever.getUserService(form).validateEmailAddress(form, email, confirmEmail, null, newUserId);
            String hashPassword = ServiceRetriever.getUserService(form).validatePassword(password, confirmPassword);

            Company company = new Company();
            company.setId(newCompanyId);
            company.setAddress(address);
            company.setCountry(country);
            company.setName(companyName);
            company.setPostCode(postCode);
            company.setTown(town);

            User user = new User();
            user.setId(newUserId);
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(hashPassword);
            user.setRole(UserRole.OWNER.toString());
            user.setActive(1);
            user.setCompanyId(newCompanyId);
            user.setLocaleCountry(Locale.getDefault().getCountry());
            user.setLocaleLanguage(Locale.getDefault().getLanguage());

            ServiceRetriever.getUserService(form).register(form, company, user);

            form.showStackedCanvasPage(F_LOGIN.C_STACKED, F_LOGIN.C_STACKED_PAGES.LOGON);
        }
        else if (F_LOGIN.AC_SIGNUP.equals(command))
        {
            form.getBlock(F_LOGIN.B_COMPANY.ID).clear(true);
            form.getBlock(F_LOGIN.B_COMPANY.ID).getScreenItem(EJScreenType.MAIN, F_LOGIN.B_COMPANY.I_BIZI_BO_LOGO).setValue("/icons/Bizibo.png");
            
            form.showStackedCanvasPage(F_LOGIN.C_STACKED, F_LOGIN.C_STACKED_PAGES.REGISTER);
        }
        else if (F_LOGIN.AC_CANCEL_REGISTRATION.equals(command))
        {
            form.showStackedCanvasPage(F_LOGIN.C_STACKED, F_LOGIN.C_STACKED_PAGES.LOGON);
            form.getBlock(F_LOGIN.B_COMPANY.ID).clear(true);
        }
        else if (F_LOGIN.AC_FORGOT_PASSWORD.equals(command))
        {
            form.getBlock(F_LOGIN.B_FORGOT.ID).clear(true);
            form.showStackedCanvasPage(F_LOGIN.C_STACKED, F_LOGIN.C_STACKED_PAGES.FORGOT);
        }
        else if (F_LOGIN.AC_FORGOT_PASSWORD_CANCEL.equals(command))
        {
            form.getBlock(F_LOGIN.B_FORGOT.ID).clear(true);
            form.showStackedCanvasPage(F_LOGIN.C_STACKED, F_LOGIN.C_STACKED_PAGES.LOGON);
        }
        else if (F_LOGIN.AC_REQUEST_PASSWORD.equals(command))
        {
            Integer companyId = (Integer)form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
            String email = (String)record.getValue(F_LOGIN.B_FORGOT.I_EMAIL);
            String confirmEmail = (String)record.getValue(F_LOGIN.B_FORGOT.I_CONFIRM_EMAIL);
            
            try
            {
                ServiceRetriever.getUserService(form).validateEmailAddress(form, email, confirmEmail);    
            }
            catch (Exception e)
            {
                record.setValue(F_LOGIN.B_FORGOT.I_EMAIL_ERROR, e.getMessage());
            }
            
            if (!ServiceRetriever.getUserService(form).doesEmailExist(form, confirmEmail, companyId, null))
            {
                record.setValue(F_LOGIN.B_FORGOT.I_EMAIL_ERROR, "This email does not exist!");
            }
            
            EmailUtil.sendRequestPasswordMail(form, email);
            
        }
    }

}
