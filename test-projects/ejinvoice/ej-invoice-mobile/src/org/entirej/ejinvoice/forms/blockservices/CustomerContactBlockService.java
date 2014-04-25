package org.entirej.ejinvoice.forms.blockservices;

import java.util.ArrayList;
import java.util.List;

import org.entirej.ejinvoice.ApplicationParameters;
import org.entirej.ejinvoice.forms.blockservices.pojos.CustomerContact;
import org.entirej.ejinvoice.forms.constants.F_CUSTOMER;
import org.entirej.ejinvoice.forms.login.User;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class CustomerContactBlockService implements EJBlockService<CustomerContact>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT CONTACT_TYPES_ID,CUSTOMER_ID,EMAIL,FIRST_NAME,ID,LAST_NAME,MOBILE,PHONE,SALUTATIONS_ID,USER_ID FROM customer_contact";

    public CustomerContactBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<CustomerContact> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        User user = (User)form.getApplicationLevelParameter(ApplicationParameters.PARAM_USER).getValue();
        queryCriteria.add(EJRestrictions.equals("USER_ID", user.getId()));
        
        return _statementExecutor.executeQuery(CustomerContact.class, form, _selectStatement, queryCriteria);
    }

    @Override
    public void executeInsert(EJForm form, List<CustomerContact> newRecords)
    {
        User user = (User)form.getApplicationLevelParameter(ApplicationParameters.PARAM_USER).getValue();
        
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;
        for (CustomerContact record : newRecords)
        {
            // Initialise the value list
            parameters.clear();
            record.setUserId(user.getId());
            parameters.add(new EJStatementParameter("CONTACT_TYPES_ID", Integer.class, record.getContactTypesId()));
            parameters.add(new EJStatementParameter("CUSTOMER_ID", Integer.class, record.getCustomerId()));
            parameters.add(new EJStatementParameter("EMAIL", String.class, record.getEmail()));
            parameters.add(new EJStatementParameter("FIRST_NAME", String.class, record.getFirstName()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("LAST_NAME", String.class, record.getLastName()));
            parameters.add(new EJStatementParameter("MOBILE", String.class, record.getMobile()));
            parameters.add(new EJStatementParameter("PHONE", String.class, record.getPhone()));
            parameters.add(new EJStatementParameter("SALUTATIONS_ID", Integer.class, record.getSalutationsId()));
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, user.getId()));
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "customer_contact", parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != newRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: " + newRecords.size() + ". Inserted: " + recordsProcessed);
        }
    }

    @Override
    public void executeUpdate(EJForm form, List<CustomerContact> updateRecords)
    {
        User user = (User)form.getApplicationLevelParameter(ApplicationParameters.PARAM_USER).getValue();
        
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (CustomerContact record : updateRecords)
        {
            parameters.clear();

            // First add the new values
            parameters.add(new EJStatementParameter("CONTACT_TYPES_ID", Integer.class, record.getContactTypesId()));
            parameters.add(new EJStatementParameter("CUSTOMER_ID", Integer.class, record.getCustomerId()));
            parameters.add(new EJStatementParameter("EMAIL", String.class, record.getEmail()));
            parameters.add(new EJStatementParameter("FIRST_NAME", String.class, record.getFirstName()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("LAST_NAME", String.class, record.getLastName()));
            parameters.add(new EJStatementParameter("MOBILE", String.class, record.getMobile()));
            parameters.add(new EJStatementParameter("PHONE", String.class, record.getPhone()));
            parameters.add(new EJStatementParameter("SALUTATIONS_ID", Integer.class, record.getSalutationsId()));
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, record.getUserId()));

            EJStatementCriteria criteria = new EJStatementCriteria();
            criteria.add(EJRestrictions.equals("USER_ID", user.getId()));
            if (record.getInitialContactTypesId() == null)
            {
                criteria.add(EJRestrictions.isNull("CONTACT_TYPES_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CONTACT_TYPES_ID", record.getInitialContactTypesId()));
            }
            if (record.getInitialCustomerId() == null)
            {
                criteria.add(EJRestrictions.isNull("CUSTOMER_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CUSTOMER_ID", record.getInitialCustomerId()));
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
            if (record.getInitialMobile() == null)
            {
                criteria.add(EJRestrictions.isNull("MOBILE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("MOBILE", record.getInitialMobile()));
            }
            if (record.getInitialPhone() == null)
            {
                criteria.add(EJRestrictions.isNull("PHONE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PHONE", record.getInitialPhone()));
            }
            if (record.getInitialSalutationsId() == null)
            {
                criteria.add(EJRestrictions.isNull("SALUTATIONS_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("SALUTATIONS_ID", record.getInitialSalutationsId()));
            }
            if (record.getInitialUserId() == null)
            {
                criteria.add(EJRestrictions.isNull("USER_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("USER_ID", record.getInitialUserId()));
            }
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeUpdate(form, "customer_contact", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != updateRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in update. Expected: " + updateRecords.size() + ". Updated: " + recordsProcessed);
        }
    }

    @Override
    public void executeDelete(EJForm form, List<CustomerContact> recordsToDelete)
    {
        User user = (User)form.getApplicationLevelParameter(ApplicationParameters.PARAM_USER).getValue();
        ArrayList<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (CustomerContact record : recordsToDelete)
        {
            parameters.clear();

            EJStatementCriteria criteria = new EJStatementCriteria();
            criteria.add(EJRestrictions.equals("USER_ID", user.getId()));
            
            if (record.getInitialContactTypesId() == null)
            {
                criteria.add(EJRestrictions.isNull("CONTACT_TYPES_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CONTACT_TYPES_ID", record.getInitialContactTypesId()));
            }
            if (record.getInitialCustomerId() == null)
            {
                criteria.add(EJRestrictions.isNull("CUSTOMER_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CUSTOMER_ID", record.getInitialCustomerId()));
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
            if (record.getInitialMobile() == null)
            {
                criteria.add(EJRestrictions.isNull("MOBILE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("MOBILE", record.getInitialMobile()));
            }
            if (record.getInitialPhone() == null)
            {
                criteria.add(EJRestrictions.isNull("PHONE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PHONE", record.getInitialPhone()));
            }
            if (record.getInitialSalutationsId() == null)
            {
                criteria.add(EJRestrictions.isNull("SALUTATIONS_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("SALUTATIONS_ID", record.getInitialSalutationsId()));
            }
            if (record.getInitialUserId() == null)
            {
                criteria.add(EJRestrictions.isNull("USER_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("USER_ID", record.getInitialUserId()));
            }
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeDelete(form, "customer_contact", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != recordsToDelete.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in delete. Expected: " + recordsToDelete.size() + ". Deleted: " + recordsProcessed);
        }
    }

}