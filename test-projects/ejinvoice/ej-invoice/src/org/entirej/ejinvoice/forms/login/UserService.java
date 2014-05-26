package org.entirej.ejinvoice.forms.login;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.routines.EmailValidator;
import org.entirej.ejinvoice.EJContextProvider;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.forms.constants.F_LOGIN;
import org.entirej.ejinvoice.forms.masterdata.ContactType;
import org.entirej.ejinvoice.forms.masterdata.ContactTypeBlockService;
import org.entirej.ejinvoice.forms.masterdata.PaymentTerm;
import org.entirej.ejinvoice.forms.masterdata.PaymentTermBlockService;
import org.entirej.ejinvoice.forms.masterdata.Salutation;
import org.entirej.ejinvoice.forms.masterdata.SalutationBlockService;
import org.entirej.ejinvoice.forms.masterdata.VatRate;
import org.entirej.ejinvoice.forms.masterdata.VatRateBlockService;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.enumerations.EJMessageLevel;
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

    public void validateEmailAddress(String email) throws EJApplicationException
    {
        if (!EmailValidator.getInstance().isValid(email))
        {
            throw new EJApplicationException(new EJMessage(EJMessageLevel.ERROR, "The email address you have entered is not a valid email address."));
        }
    }

    public boolean doesEmailExist(String email)
    {
        final String stmt = "SELECT EMAIL FROM USER ";

        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("EMAIL", email));
        List<EJSelectResult> results = new EJStatementExecutor().executeQuery(contextProvider.getConnection(), stmt, criteria);

        return !results.isEmpty();
    }

    public User getUser(String email, String password)
    {
        final String stmt = "SELECT EMAIL,FIRST_NAME,ID,LAST_NAME,NOTES,PASSWORD FROM user";

        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("email", email));
        criteria.add(EJRestrictions.equals("password", PasswordHashGen.toHash(password)));

        List<User> list = new EJStatementExecutor().executeQuery(User.class, contextProvider.getConnection(), stmt, criteria);
        if (!list.isEmpty())
        {
            return list.get(0);
        }
        return null;
    }

    public User getUser(String email)
    {
        final String stmt = "SELECT EMAIL,FIRST_NAME,ID,LAST_NAME,NOTES,PASSWORD FROM user";

        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("email", email));

        List<User> list = new EJStatementExecutor().executeQuery(User.class, contextProvider.getConnection(), stmt, criteria);
        if (!list.isEmpty())
        {
            return list.get(0);
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

    public void setupDefaultData(int userId)
    {
        // Default Data User
        User user = getUser("template@ej.org");
        if (user == null)
            return;

        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("USER_ID", user.getId()));

        {// create Contact Types

            ContactTypeBlockService blockService = new ContactTypeBlockService();

            List<ContactType> contactTypes = blockService.getContactTypes(contextProvider.getConnection(), criteria);
            for (ContactType contactType : contactTypes)
            {
                contactType.setId(PKSequenceService.getPKSequence(contextProvider.getConnection()));
                contactType.setUserId(userId);
            }
            if (contactTypes.size() > 0)
            {
                blockService.insertContactTypes(contextProvider.getConnection(), contactTypes);
            }

        }
        {// create Payment Term

            PaymentTermBlockService blockService = new PaymentTermBlockService();

            List<PaymentTerm> paymentTerms = blockService.getPaymentTerms(contextProvider.getConnection(), criteria);
            for (PaymentTerm paymentTerm : paymentTerms)
            {
                paymentTerm.setId(PKSequenceService.getPKSequence(contextProvider.getConnection()));
                paymentTerm.setUserId(userId);
            }
            if (paymentTerms.size() > 0)
            {
                blockService.insertPaymentTerms(contextProvider.getConnection(), paymentTerms);
            }

        }
        {// create Salutation

            SalutationBlockService blockService = new SalutationBlockService();

            List<Salutation> salutations = blockService.getSalutations(contextProvider.getConnection(), criteria);
            for (Salutation salutation : salutations)
            {
                salutation.setId(PKSequenceService.getPKSequence(contextProvider.getConnection()));
                salutation.setUserId(userId);
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
                rate.setUserId(userId);
            }

            if (vatRates.size() > 0)
            {
                blockService.insertVatRates(contextProvider.getConnection(), vatRates);
            }

        }

    }

}
