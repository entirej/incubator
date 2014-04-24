package org.entirej.ejinvoice.forms.blockservices;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.entirej.ejinvoice.ApplicationParameters;
import org.entirej.ejinvoice.forms.blockservices.pojos.Project;
import org.entirej.ejinvoice.forms.login.User;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class ProjectBlockService implements EJBlockService<Project>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT CUSTOMER_ID,DESCRIPTION,ID,NAME,PAY_RATE,USER_ID FROM customer_projects";

    public ProjectBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<Project> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        User usr = (User) form.getApplicationLevelParameter(ApplicationParameters.PARAM_USER).getValue();
        queryCriteria.add(EJRestrictions.equals("USER_ID", usr.getId()));
        return _statementExecutor.executeQuery(Project.class, form, _selectStatement, queryCriteria);
    }

    @Override
    public void executeInsert(EJForm form, List<Project> newRecords)
    {
        User usr = (User) form.getApplicationLevelParameter(ApplicationParameters.PARAM_USER).getValue();
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;
        for (Project record : newRecords)
        {
            // Initialise the value list
            parameters.clear();
            parameters.add(new EJStatementParameter("CUSTOMER_ID", Integer.class, record.getCustomerId()));
            parameters.add(new EJStatementParameter("DESCRIPTION", String.class, record.getDescription()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getName()));
            parameters.add(new EJStatementParameter("PAY_RATE", BigDecimal.class, record.getPayRate()));
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, usr.getId()));
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "customer_projects", parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != newRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: " + newRecords.size() + ". Inserted: " + recordsProcessed);
        }
    }

    @Override
    public void executeUpdate(EJForm form, List<Project> updateRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (Project record : updateRecords)
        {
            parameters.clear();

            // First add the new values
            parameters.add(new EJStatementParameter("CUSTOMER_ID", Integer.class, record.getCustomerId()));
            parameters.add(new EJStatementParameter("DESCRIPTION", String.class, record.getDescription()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getName()));
            parameters.add(new EJStatementParameter("PAY_RATE", BigDecimal.class, record.getPayRate()));
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, record.getUserId()));

            EJStatementCriteria criteria = new EJStatementCriteria();
            if (record.getInitialCustomerId() == null)
            {
                criteria.add(EJRestrictions.isNull("CUSTOMER_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CUSTOMER_ID", record.getInitialCustomerId()));
            }
            if (record.getInitialDescription() == null)
            {
                criteria.add(EJRestrictions.isNull("DESCRIPTION"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("DESCRIPTION", record.getInitialDescription()));
            }
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialName() == null)
            {
                criteria.add(EJRestrictions.isNull("NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NAME", record.getInitialName()));
            }
            if (record.getInitialPayRate() == null)
            {
                criteria.add(EJRestrictions.isNull("PAY_RATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PAY_RATE", record.getInitialPayRate()));
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
            recordsProcessed += _statementExecutor.executeUpdate(form, "customer_projects", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != updateRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in update. Expected: " + updateRecords.size() + ". Updated: " + recordsProcessed);
        }
    }

    @Override
    public void executeDelete(EJForm form, List<Project> recordsToDelete)
    {
        ArrayList<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (Project record : recordsToDelete)
        {
            parameters.clear();

            EJStatementCriteria criteria = new EJStatementCriteria();

            if (record.getInitialCustomerId() == null)
            {
                criteria.add(EJRestrictions.isNull("CUSTOMER_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CUSTOMER_ID", record.getInitialCustomerId()));
            }
            if (record.getInitialDescription() == null)
            {
                criteria.add(EJRestrictions.isNull("DESCRIPTION"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("DESCRIPTION", record.getInitialDescription()));
            }
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialName() == null)
            {
                criteria.add(EJRestrictions.isNull("NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NAME", record.getInitialName()));
            }
            if (record.getInitialPayRate() == null)
            {
                criteria.add(EJRestrictions.isNull("PAY_RATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PAY_RATE", record.getInitialPayRate()));
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
            recordsProcessed += _statementExecutor.executeDelete(form, "customer_projects", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != recordsToDelete.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in delete. Expected: " + recordsToDelete.size() + ". Deleted: " + recordsProcessed);
        }
    }

}