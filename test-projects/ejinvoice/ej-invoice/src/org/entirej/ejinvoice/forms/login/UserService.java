package org.entirej.ejinvoice.forms.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale.Builder;

import org.apache.commons.validator.routines.EmailValidator;
import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.EJContextProvider;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.forms.company.Company;
import org.entirej.ejinvoice.forms.company.User;
import org.entirej.ejinvoice.forms.constants.F_LOGIN;
import org.entirej.ejinvoice.forms.customer.CustomerContact;
import org.entirej.ejinvoice.forms.customer.CustomerContactBlockService;
import org.entirej.ejinvoice.forms.masterdata.ContactType;
import org.entirej.ejinvoice.forms.masterdata.ContactTypeBlockService;
import org.entirej.ejinvoice.forms.masterdata.Salutation;
import org.entirej.ejinvoice.forms.masterdata.SalutationBlockService;
import org.entirej.ejinvoice.forms.masterdata.VatRate;
import org.entirej.ejinvoice.forms.masterdata.VatRateBlockService;
import org.entirej.ejinvoice.forms.projects.Project;
import org.entirej.ejinvoice.forms.projects.ProjectBlockService;
import org.entirej.ejinvoice.forms.projects.ProjectService;
import org.entirej.ejinvoice.forms.projects.ProjectTasks;
import org.entirej.ejinvoice.forms.projects.ProjectTasksBlockService;
import org.entirej.ejinvoice.forms.timeentry.Customer;
import org.entirej.ejinvoice.forms.timeentry.CustomerBlockService;
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
    public UserService()
    {
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
            if (((Long) result.getItemValue("entry_count")) > 0)
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

    public void validateEmailAddress(EJForm form, String email, String confirmEmail) throws EJApplicationException
    {
        validateEmailAddress(form, email, confirmEmail, null, null);
    }

    public void validateEmailAddress(EJForm form, String email, String confirmEmail, Integer companyId, Integer userId) throws EJApplicationException
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

        if (userId != null)
        {
            if (doesEmailExist(form, email, companyId, userId))
            {
                if (companyId != null)
                {
                    EJMessage message = new EJMessage(EJMessageLevel.ERROR, "There is already a user within your company with this email address");
                    throw new EJApplicationException(message);
                }
                else
                {
                    EJMessage message = new EJMessage(EJMessageLevel.ERROR,
                            "A user with this email address has already been registered, please choose a different email address");
                    throw new EJApplicationException(message);
                }
            }
        }
    }

    public boolean doesEmailExist(EJForm form, String email, Integer companyId, Integer userId)
    {
        final String stmt = "SELECT EMAIL FROM USER";

        EJQueryCriteria criteria = new EJQueryCriteria();
        if (companyId != null)
        {
            criteria.add(EJRestrictions.equals("COMPANY_ID", companyId));
        }

        criteria.add(EJRestrictions.equals("EMAIL", email));

        if (userId != null)
        {
            criteria.add(EJRestrictions.notEquals("ID", userId));
        }
        
        List<EJSelectResult> results = new EJStatementExecutor().executeQuery(form.getConnection(), stmt, criteria);

        return !results.isEmpty();
    }

    public User getUser(EJForm form, String email, String password)
    {
        final String stmt = "SELECT ADDRESS,COMPANY_ID,EMAIL,FIRST_NAME,ID,LAST_NAME,LOCALE_COUNTRY,LOCALE_LANGUAGE,NOTES,PASSWORD,POST_CODE,TOWN, ROLE, ACTIVE FROM user";

        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("email", email));
        criteria.add(EJRestrictions.equals("password", PasswordHashGen.toHash(password)));

        List<User> list = new EJStatementExecutor().executeQuery(User.class, form.getConnection(), stmt, criteria);
        if (!list.isEmpty())
        {
            User usr = list.get(0);
            usr.setLocale(new Builder().setLanguage(usr.getLocaleLanguage()).setRegion(usr.getLocaleCountry()).build());
            return usr;
        }
        return null;
    }

    public User getUser(EJForm form, String email)
    {
        final String stmt = "SELECT ADDRESS,COMPANY_ID,EMAIL,FIRST_NAME,ID,LAST_NAME,LOCALE_COUNTRY,LOCALE_LANGUAGE,NOTES,PASSWORD,POST_CODE,TOWN, ROLE, ACTIVE FROM user";

        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("email", email));

        List<User> list = new EJStatementExecutor().executeQuery(User.class, form.getConnection(), stmt, criteria);
        if (!list.isEmpty())
        {
            User usr = list.get(0);
            usr.setLocale(new Builder().setLanguage(usr.getLocaleLanguage()).setRegion(usr.getLocaleCountry()).build());
            return usr;
        }
        return null;
    }

    public void register(EJForm form, Company company, User user)
    {
        EJStatementExecutor executor = new EJStatementExecutor();

        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;

        parameters.clear();
        parameters.add(new EJStatementParameter("ADDRESS", String.class, company.getAddress()));
        parameters.add(new EJStatementParameter("INVOICE_FOOTER", String.class, company.getInvoiceFooter()));
        parameters.add(new EJStatementParameter("INVOICE_SUMMARY", String.class, company.getInvoiceSummary()));
        parameters.add(new EJStatementParameter("COUNTRY", String.class, company.getCountry()));
        parameters.add(new EJStatementParameter("ID", Integer.class, company.getId()));
        parameters.add(new EJStatementParameter("LOGO", Object.class, company.getLogo()));
        parameters.add(new EJStatementParameter("NAME", String.class, company.getName()));
        parameters.add(new EJStatementParameter("POST_CODE", String.class, company.getPostCode()));
        parameters.add(new EJStatementParameter("TOWN", String.class, company.getTown()));
        parameters.add(new EJStatementParameter("VAT_NR", String.class, company.getVatNr()));

        EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
        executor.executeInsert(form, "company_information", parameters.toArray(paramArray));

        // Initialise the value list
        parameters.clear();
        parameters.add(new EJStatementParameter("ID", Integer.class, user.getId()));
        parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, company.getId()));
        parameters.add(new EJStatementParameter("EMAIL", String.class, user.getEmail()));
        parameters.add(new EJStatementParameter("FIRST_NAME", String.class, user.getFirstName()));
        parameters.add(new EJStatementParameter("LAST_NAME", String.class, user.getLastName()));
        parameters.add(new EJStatementParameter("NOTES", String.class, user.getNotes()));
        parameters.add(new EJStatementParameter("PASSWORD", String.class, user.getPassword()));
        parameters.add(new EJStatementParameter("ACTIVE", Integer.class, user.getActive()));
        parameters.add(new EJStatementParameter("ROLE", String.class, user.getRole().toString()));
        parameters.add(new EJStatementParameter("LOCALE_LANGUAGE", Integer.class, user.getLocaleLanguage()));
        parameters.add(new EJStatementParameter("LOCALE_COUNTRY", Integer.class, user.getLocaleCountry()));

        paramArray = new EJStatementParameter[parameters.size()];
        recordsProcessed += executor.executeInsert(form, "user", parameters.toArray(paramArray));
        user.clearInitialValues();

        if (recordsProcessed != 1)
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: 1. Inserted: " + recordsProcessed);
        }
        else
        {
            setupDefaultData(form, company);
            form.getBlock(F_LOGIN.B_LOGON.ID).clear(true);
            form.showMessage(new EJMessage(EJMessageLevel.MESSAGE, "Registration Successful, please log in..."));
        }
    }

    public void setupDefaultData(EJForm form, Company company)
    {
        Integer defaultsCompanyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_NEW_COMPANY_DEFAULTS_COMPANY_ID).getValue();

        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("COMPANY_ID", defaultsCompanyId));

        HashMap<Integer, Salutation> _salutations = new HashMap<Integer, Salutation>();
        HashMap<Integer, VatRate> _vatRates = new HashMap<Integer, VatRate>();
        HashMap<Integer, ContactType> _contactTypes = new HashMap<Integer, ContactType>();

        {// create Salutation

            SalutationBlockService blockService = new SalutationBlockService();

            List<Salutation> salutations = blockService.getSalutations(form.getConnection(), criteria);
            for (Salutation salutation : salutations)
            {
                _salutations.put(salutation.getId(), salutation);

                salutation.setId(PKSequenceService.getPKSequence(form.getConnection()));
                salutation.setCompanyId(company.getId());
            }

            if (salutations.size() > 0)
            {
                blockService.insertSalutations(form.getConnection(), salutations);
            }

        }

        {// create VAT rates

            VatRateBlockService blockService = new VatRateBlockService();

            List<VatRate> vatRates = blockService.getVatRates(form.getConnection(), criteria);
            for (VatRate rate : vatRates)
            {
                _vatRates.put(rate.getId(), rate);

                rate.setId(PKSequenceService.getPKSequence(form.getConnection()));
                rate.setCompanyId(company.getId());
            }

            if (vatRates.size() > 0)
            {
                blockService.insertVatRates(form.getConnection(), vatRates);
            }
        }

        {// contact Types

            ContactTypeBlockService blockService = new ContactTypeBlockService();

            List<ContactType> contactTypes = blockService.getContactTypes(form.getConnection(), criteria);
            for (ContactType type : contactTypes)
            {
                _contactTypes.put(type.getId(), type);

                type.setId(PKSequenceService.getPKSequence(form.getConnection()));
                type.setCompanyId(company.getId());
            }

            if (contactTypes.size() > 0)
            {
                blockService.insertContactTypes(form.getConnection(), contactTypes);
            }
        }

        {// example customer

            List<CustomerContact> allCustomerContacts = new ArrayList<CustomerContact>();

            CustomerContactBlockService custContactService = new CustomerContactBlockService();
            CustomerBlockService blockService = new CustomerBlockService();

            List<Customer> customers = blockService.executeQuery(form, criteria);
            for (Customer customer : customers)
            {
                EJQueryCriteria custContactCriteria = new EJQueryCriteria();
                custContactCriteria.add(EJRestrictions.equals("COMPANY_ID", defaultsCompanyId));
                custContactCriteria.add(EJRestrictions.equals("CUSTOMER_ID", customer.getId()));
                List<CustomerContact> customerContacts = custContactService.executeQuery(form, custContactCriteria);

                customer.setId(PKSequenceService.getPKSequence(form.getConnection()));
                customer.setCompanyId(company.getId());
                customer.setVatId(_vatRates.get(customer.getVatId()).getId());

                for (CustomerContact contact : customerContacts)
                {
                    contact.setId(PKSequenceService.getPKSequence(form.getConnection()));
                    contact.setCompanyId(customer.getCompanyId());
                    contact.setCustomerId(customer.getId());

                    contact.setContactTypesId(_contactTypes.get(contact.getContactTypesId()).getId());
                    contact.setSalutationsId(_salutations.get(contact.getSalutationsId()).getId());

                    allCustomerContacts.add(contact);
                }
            }

            if (customers.size() > 0)
            {
                blockService.registerCustomer(form, customers);
            }

            if (allCustomerContacts.size() > 0)
            {
                custContactService.registerCustomerContact(form, allCustomerContacts);
            }

        }

        {// example project

            List<ProjectTasks> allProjectTasks = new ArrayList<ProjectTasks>();

            ProjectTasksBlockService projectTasksService = new ProjectTasksBlockService();
            ProjectBlockService blockService = new ProjectBlockService();

            List<Project> projects = new ProjectService().getAllProjects(form, defaultsCompanyId);
            for (Project project : projects)
            {
                EJQueryCriteria tasksCriteria = new EJQueryCriteria();
                tasksCriteria.add(EJRestrictions.equals("COMPANY_ID", defaultsCompanyId));
                tasksCriteria.add(EJRestrictions.equals("CPR_ID", project.getId()));
                List<ProjectTasks> projectTasks = projectTasksService.executeQuery(form, tasksCriteria);

                project.setId(PKSequenceService.getPKSequence(form.getConnection()));
                project.setCompanyId(company.getId());

                for (ProjectTasks task : projectTasks)
                {
                    task.setId(PKSequenceService.getPKSequence(form.getConnection()));
                    task.setCompanyId(project.getCompanyId());
                    task.setCprId(project.getId());

                    allProjectTasks.add(task);
                }
            }

            if (projects.size() > 0)
            {
                blockService.registerProject(form, projects);
            }

            if (allProjectTasks.size() > 0)
            {
                projectTasksService.executeInsert(form, allProjectTasks);
            }

        }

    }

}
