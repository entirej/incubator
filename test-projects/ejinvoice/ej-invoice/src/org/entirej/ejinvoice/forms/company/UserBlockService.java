package org.entirej.ejinvoice.forms.company;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale.Builder;

import org.entirej.ejinvoice.forms.company.User;
import org.entirej.ejinvoice.forms.timeentry.Customer;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJQuerySort;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class UserBlockService implements EJBlockService<User>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT ADDRESS,COMPANY_ID,EMAIL,FIRST_NAME,ID,LAST_NAME,LOCALE_COUNTRY,LOCALE_LANGUAGE,NOTES,PASSWORD,POST_CODE,TOWN, ROLE, ACTIVE FROM user";

    public UserBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<User> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        queryCriteria.add(EJQuerySort.ASC("case when (ROLE='OWNER')=true then 1 when (ROLE='ADMINISTRATOR')=true then 2 when (ROLE='EMPLOYEE')=true then 3 else 4 END"));
        
        List<User> users = _statementExecutor.executeQuery(User.class, form, _selectStatement, queryCriteria);
        for (User user : users)
        {
            user.setLocale(new Builder().setLanguage(user.getLocaleLanguage()).setRegion(user.getLocaleCountry()).build());
        }
        
        return users;
    }

    @Override
    public void executeInsert(EJForm form, List<User> newRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;
        for (User record : newRecords)
        {
            // Initialise the value list
            parameters.clear();
            parameters.add(new EJStatementParameter("ADDRESS", String.class, record.getAddress()));
            parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, record.getCompanyId()));
            parameters.add(new EJStatementParameter("EMAIL", String.class, record.getEmail()));
            parameters.add(new EJStatementParameter("FIRST_NAME", String.class, record.getFirstName()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("LAST_NAME", String.class, record.getLastName()));
            parameters.add(new EJStatementParameter("LOCALE_COUNTRY", String.class, record.getLocaleCountry()));
            parameters.add(new EJStatementParameter("LOCALE_LANGUAGE", String.class, record.getLocaleLanguage()));
            parameters.add(new EJStatementParameter("NOTES", String.class, record.getNotes()));
            parameters.add(new EJStatementParameter("PASSWORD", String.class, record.getPassword()));
            parameters.add(new EJStatementParameter("POST_CODE", String.class, record.getPostCode()));
            parameters.add(new EJStatementParameter("TOWN", String.class, record.getTown()));
            parameters.add(new EJStatementParameter("ROLE", String.class, record.getRole()));
            parameters.add(new EJStatementParameter("ACTIVE", Integer.class, 1));
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "user", parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != newRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: " + newRecords.size() + ". Inserted: " + recordsProcessed);
        }
    }

    @Override
    public void executeUpdate(EJForm form, List<User> updateRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (User record : updateRecords)
        {
            parameters.clear();

            // First add the new values
            parameters.add(new EJStatementParameter("ADDRESS", String.class, record.getAddress()));
            parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, record.getCompanyId()));
            parameters.add(new EJStatementParameter("EMAIL", String.class, record.getEmail()));
            parameters.add(new EJStatementParameter("FIRST_NAME", String.class, record.getFirstName()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("LAST_NAME", String.class, record.getLastName()));
            parameters.add(new EJStatementParameter("LOCALE_COUNTRY", String.class, record.getLocaleCountry()));
            parameters.add(new EJStatementParameter("LOCALE_LANGUAGE", String.class, record.getLocaleLanguage()));
            parameters.add(new EJStatementParameter("NOTES", String.class, record.getNotes()));
            parameters.add(new EJStatementParameter("PASSWORD", String.class, record.getPassword()));
            parameters.add(new EJStatementParameter("POST_CODE", String.class, record.getPostCode()));
            parameters.add(new EJStatementParameter("TOWN", String.class, record.getTown()));
            parameters.add(new EJStatementParameter("ROLE", String.class, record.getRole()));
            parameters.add(new EJStatementParameter("ACTIVE", Integer.class, record.getActive()));
            
            EJStatementCriteria criteria = new EJStatementCriteria();
            if (record.getInitialAddress() == null)
            {
                criteria.add(EJRestrictions.isNull("ADDRESS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ADDRESS", record.getInitialAddress()));
            }
            if (record.getInitialCompanyId() == null)
            {
                criteria.add(EJRestrictions.isNull("COMPANY_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("COMPANY_ID", record.getInitialCompanyId()));
            }
            if (record.getInitialEmail() == null)
            {
                criteria.add(EJRestrictions.isNull("EMAIL"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("EMAIL", record.getInitialEmail()));
            }
            if (record.getInitialFirstName() == null)
            {
                criteria.add(EJRestrictions.isNull("FIRST_NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("FIRST_NAME", record.getInitialFirstName()));
            }
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialLastName() == null)
            {
                criteria.add(EJRestrictions.isNull("LAST_NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("LAST_NAME", record.getInitialLastName()));
            }
            if (record.getInitialLocaleCountry() == null)
            {
                criteria.add(EJRestrictions.isNull("LOCALE_COUNTRY"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("LOCALE_COUNTRY", record.getInitialLocaleCountry()));
            }
            if (record.getInitialLocaleLanguage() == null)
            {
                criteria.add(EJRestrictions.isNull("LOCALE_LANGUAGE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("LOCALE_LANGUAGE", record.getInitialLocaleLanguage()));
            }
            if (record.getInitialNotes() == null)
            {
                criteria.add(EJRestrictions.isNull("NOTES"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NOTES", record.getInitialNotes()));
            }
            if (record.getInitialPassword() == null)
            {
                criteria.add(EJRestrictions.isNull("PASSWORD"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PASSWORD", record.getInitialPassword()));
            }
            if (record.getInitialPostCode() == null)
            {
                criteria.add(EJRestrictions.isNull("POST_CODE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("POST_CODE", record.getInitialPostCode()));
            }
            if (record.getInitialTown() == null)
            {
                criteria.add(EJRestrictions.isNull("TOWN"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("TOWN", record.getInitialTown()));
            }
            if (record.getInitialRole() == null)
            {
                criteria.add(EJRestrictions.isNull("ROLE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ROLE", record.getInitialRole()));
            }
            if (record.getInitialActive() == null)
            {
                criteria.add(EJRestrictions.isNull("ACTIVE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ACTIVE", record.getInitialActive()));
            }
            
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeUpdate(form, "user", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != updateRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in update. Expected: " + updateRecords.size() + ". Updated: " + recordsProcessed);
        }
    }

    @Override
    public void executeDelete(EJForm form, List<User> recordsToDelete)
    {
        ArrayList<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (User record : recordsToDelete)
        {
            parameters.clear();

            EJStatementCriteria criteria = new EJStatementCriteria();

            if (record.getInitialAddress() == null)
            {
                criteria.add(EJRestrictions.isNull("ADDRESS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ADDRESS", record.getInitialAddress()));
            }
            if (record.getInitialCompanyId() == null)
            {
                criteria.add(EJRestrictions.isNull("COMPANY_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("COMPANY_ID", record.getInitialCompanyId()));
            }
            if (record.getInitialEmail() == null)
            {
                criteria.add(EJRestrictions.isNull("EMAIL"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("EMAIL", record.getInitialEmail()));
            }
            if (record.getInitialFirstName() == null)
            {
                criteria.add(EJRestrictions.isNull("FIRST_NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("FIRST_NAME", record.getInitialFirstName()));
            }
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialLastName() == null)
            {
                criteria.add(EJRestrictions.isNull("LAST_NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("LAST_NAME", record.getInitialLastName()));
            }
            if (record.getInitialLocaleCountry() == null)
            {
                criteria.add(EJRestrictions.isNull("LOCALE_COUNTRY"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("LOCALE_COUNTRY", record.getInitialLocaleCountry()));
            }
            if (record.getInitialLocaleLanguage() == null)
            {
                criteria.add(EJRestrictions.isNull("LOCALE_LANGUAGE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("LOCALE_LANGUAGE", record.getInitialLocaleLanguage()));
            }
            if (record.getInitialNotes() == null)
            {
                criteria.add(EJRestrictions.isNull("NOTES"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NOTES", record.getInitialNotes()));
            }
            if (record.getInitialPassword() == null)
            {
                criteria.add(EJRestrictions.isNull("PASSWORD"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PASSWORD", record.getInitialPassword()));
            }
            if (record.getInitialPostCode() == null)
            {
                criteria.add(EJRestrictions.isNull("POST_CODE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("POST_CODE", record.getInitialPostCode()));
            }
            if (record.getInitialTown() == null)
            {
                criteria.add(EJRestrictions.isNull("TOWN"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("TOWN", record.getInitialTown()));
            }
            if (record.getInitialRole() == null)
            {
                criteria.add(EJRestrictions.isNull("ROLE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ROLE", record.getInitialRole()));
            }
            if (record.getInitialActive() == null)
            {
                criteria.add(EJRestrictions.isNull("ACTIVE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ACTIVE", record.getInitialActive()));
            }
            
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeDelete(form, "user", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != recordsToDelete.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in delete. Expected: " + recordsToDelete.size() + ". Deleted: " + recordsProcessed);
        }
    }

}