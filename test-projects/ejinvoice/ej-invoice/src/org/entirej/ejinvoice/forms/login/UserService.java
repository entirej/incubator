package org.entirej.ejinvoice.forms.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Builder;

import org.apache.commons.validator.routines.EmailValidator;
import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.EJContextProvider;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.forms.company.User;
import org.entirej.ejinvoice.forms.constants.F_LOGIN;
import org.entirej.ejinvoice.forms.masterdata.ContactType;
import org.entirej.ejinvoice.forms.masterdata.ContactTypeBlockService;
import org.entirej.ejinvoice.forms.masterdata.Salutation;
import org.entirej.ejinvoice.forms.masterdata.SalutationBlockService;
import org.entirej.ejinvoice.forms.masterdata.VatRate;
import org.entirej.ejinvoice.forms.masterdata.VatRateBlockService;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.enumerations.EJMessageLevel;
import org.entirej.framework.core.service.EJParameterType;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJSelectResult;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class UserService
{
    private final EJContextProvider contextProvider;

    public UserService(EJContextProvider contextProvider)
    {
        this.contextProvider = contextProvider;
    }

    public boolean canDeleteUser(EJForm form, User user)
    {
        String selectStmt = "select count(*) as entry_count from customer_project_timeentry where user_id = ? ";

        EJStatementExecutor executor = new EJStatementExecutor();
        EJStatementParameter userIdParam = new EJStatementParameter(EJParameterType.IN);
        userIdParam.setValue(user.getId());

        List<EJSelectResult> results = executor.executeQuery(form.getConnection(), selectStmt.toString(), userIdParam);
        if (results.size() > 0)
        {
            EJSelectResult result = results.get(0);
            if (((Long)result.getItemValue("entry_count")) > 0)
            {
                return false;
            }
        }

        return true;
    }
    public String validatePassword(String password, String confirmPassword) throws EJApplicationException
    {
        if (password == null || ((String) password).trim().length() == 0)
        {
            throw new EJApplicationException("Password cannot be Empty!");

        }

        if (password != null && (!password.equals(confirmPassword)))
        {

            throw new EJApplicationException("Entered passwords don't match!, Please Try again.");

        }

        return PasswordHashGen.toHash((String) password);
    }

    public void validateEmailAddress(EJForm form, String email, String confirmEmail, Integer userId) throws EJApplicationException
    {
        if (email == null || ((String) email).trim().length() == 0)
        {
            throw new EJApplicationException("Email cannot be Empty!");

        }

        if (email != null && (!email.equals(confirmEmail)))
        {

            throw new EJApplicationException("Entered email addresses don't match!, Please Try again.");

        }
        if (!EmailValidator.getInstance().isValid(email))
        {
            throw new EJApplicationException(new EJMessage(EJMessageLevel.ERROR, "The email address you have entered is not a valid email address."));
        }
        
        Integer companyId = (Integer)form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
        
        if (doesEmailExist(email, companyId, userId))
        {
            EJMessage message = new EJMessage(EJMessageLevel.ERROR, "There is already a user within your company with this email address");
            throw new EJApplicationException(message);
        }
    }

    public boolean doesEmailExist(String email, Integer companyId, Integer userId)
    {
        final String stmt = "SELECT EMAIL FROM USER";

        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("COMPANY_ID", companyId));
        criteria.add(EJRestrictions.notEquals("ID", userId));
        
        List<EJSelectResult> results = new EJStatementExecutor().executeQuery(contextProvider.getConnection(), stmt, criteria);

        return !results.isEmpty();
    }

    public User getUser(String email, String password)
    {
        final String stmt = "SELECT ADDRESS,COMPANY_ID,EMAIL,FIRST_NAME,ID,LAST_NAME,LOCALE_COUNTRY,LOCALE_LANGUAGE,NOTES,PASSWORD,POST_CODE,TOWN, ROLE, ACTIVE FROM user";

        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("email", email));
        criteria.add(EJRestrictions.equals("password", PasswordHashGen.toHash(password)));

        List<User> list = new EJStatementExecutor().executeQuery(User.class, contextProvider.getConnection(), stmt, criteria);
        if (!list.isEmpty())
        {
            User usr = list.get(0);
            usr.setLocale(new Builder().setLanguage(usr.getLocaleLanguage()).setRegion(usr.getLocaleCountry()).build());
            return usr;
        }
        return null;
    }

    public User getUser(String email)
    {
        final String stmt = "SELECT ADDRESS,COMPANY_ID,EMAIL,FIRST_NAME,ID,LAST_NAME,LOCALE_COUNTRY,LOCALE_LANGUAGE,NOTES,PASSWORD,POST_CODE,TOWN, ROLE, ACTIVE FROM user";

        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("email", email));

        List<User> list = new EJStatementExecutor().executeQuery(User.class, contextProvider.getConnection(), stmt, criteria);
        if (!list.isEmpty())
        {
            User usr = list.get(0);
            usr.setLocale(new Builder().setLanguage(usr.getLocaleLanguage()).setRegion(usr.getLocaleCountry()).build());
            return usr;
        }
        return null;
    }

    public void registerUser(EJForm form, User user)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;

        // Initialise the value list
        parameters.clear();
        int pkSequence = PKSequenceService.getPKSequence(form.getConnection());
        parameters.add(new EJStatementParameter("ID", Integer.class, pkSequence));
        parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, pkSequence));
        parameters.add(new EJStatementParameter("EMAIL", String.class, user.getEmail()));
        parameters.add(new EJStatementParameter("FIRST_NAME", String.class, user.getFirstName()));
        parameters.add(new EJStatementParameter("LAST_NAME", String.class, user.getLastName()));
        parameters.add(new EJStatementParameter("NOTES", String.class, user.getNotes()));
        parameters.add(new EJStatementParameter("PASSWORD", String.class, user.getPassword()));

        EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
        recordsProcessed += new EJStatementExecutor().executeInsert(form, "user", parameters.toArray(paramArray));
        user.clearInitialValues();

        if (recordsProcessed != 1)
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: 1. Inserted: " + recordsProcessed);
        }
        else
        {
            setupDefaultData(pkSequence);
            form.getBlock(F_LOGIN.B_LOGON.ID).clear(true);
            form.showMessage(new EJMessage(EJMessageLevel.MESSAGE, "Registration Successful, please log in..."));
        }
    }

    public void setupDefaultData(int companyId)
    {
        // Default Data User
        User user = getUser("template@ej.org");
        if (user == null)
            return;

        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("COMPANY_ID", user.getCompanyId()));

        {// create Contact Types

            ContactTypeBlockService blockService = new ContactTypeBlockService();

            List<ContactType> contactTypes = blockService.getContactTypes(contextProvider.getConnection(), criteria);
            for (ContactType contactType : contactTypes)
            {
                contactType.setId(PKSequenceService.getPKSequence(contextProvider.getConnection()));
                contactType.setCompanyId(companyId);
            }
            if (contactTypes.size() > 0)
            {
                blockService.insertContactTypes(contextProvider.getConnection(), contactTypes);
            }

        }
        {// create Salutation

            SalutationBlockService blockService = new SalutationBlockService();

            List<Salutation> salutations = blockService.getSalutations(contextProvider.getConnection(), criteria);
            for (Salutation salutation : salutations)
            {
                salutation.setId(PKSequenceService.getPKSequence(contextProvider.getConnection()));
                salutation.setCompanyId(companyId);
            }

            if (salutations.size() > 0)
            {
                blockService.insertSalutations(contextProvider.getConnection(), salutations);
            }

        }

        {// create VAT rates

            VatRateBlockService blockService = new VatRateBlockService();

            List<VatRate> vatRates = blockService.getVatRates(contextProvider.getConnection(), criteria);
            for (VatRate rate : vatRates)
            {
                rate.setId(PKSequenceService.getPKSequence(contextProvider.getConnection()));
                rate.setCompanyId(companyId);
            }

            if (vatRates.size() > 0)
            {
                blockService.insertVatRates(contextProvider.getConnection(), vatRates);
            }

        }

    }

}
