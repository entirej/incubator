package org.entirej.ejinvoice.forms.timeentry;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.forms.projects.ProjectProcess;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class TimeEntryProjectBlockService implements EJBlockService<TimeEntryProject>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT CUSTOMER_ID,DESCRIPTION,END_DATE,ID,NAME,NOTES,START_DATE,STATUS,USER_ID FROM customer_projects";

    public TimeEntryProjectBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<TimeEntryProject> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        return _statementExecutor.executeQuery(TimeEntryProject.class, form, _selectStatement, queryCriteria);
    }

    @Override
    public void executeInsert(EJForm form, List<TimeEntryProject> newRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;
        for (TimeEntryProject record : newRecords)
        {
            // Initialise the value list
            parameters.clear();
            parameters.add(new EJStatementParameter("CUSTOMER_ID", Integer.class, record.getCustomerId()));
            parameters.add(new EJStatementParameter("DESCRIPTION", String.class, record.getDescription()));
            parameters.add(new EJStatementParameter("END_DATE", Date.class, record.getEndDate()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getName()));
            parameters.add(new EJStatementParameter("NOTES", String.class, record.getNotes()));
            parameters.add(new EJStatementParameter("START_DATE", Date.class, record.getStartDate()));
            parameters.add(new EJStatementParameter("STATUS", String.class, record.getStatus()));
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, record.getUserId()));
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "customer_projects", parameters.toArray(paramArray));
        
            // Initialise the value list
            Integer processId = PKSequenceService.getPKSequence(form.getConnection());
            parameters.clear();
            parameters.add(new EJStatementParameter("CPR_ID", Integer.class,  record.getId()));
            parameters.add(new EJStatementParameter("ID", Integer.class, processId));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getProcessName()));
            parameters.add(new EJStatementParameter("NOTES", String.class, record.getProcessNotes()));
            parameters.add(new EJStatementParameter("PAY_RATE", BigDecimal.class, record.getProcessPayRate()));
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, record.getUserId()));
            parameters.add(new EJStatementParameter("VAT_ID", Integer.class, record.getProcessVatId()));
            paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "customer_project_process", parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        
        if (recordsProcessed != (newRecords.size()*2))
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: " + newRecords.size() + ". Inserted: "
                    + recordsProcessed);
        }
    }

    @Override
    public void executeUpdate(EJForm form, List<TimeEntryProject> updateRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (TimeEntryProject record : updateRecords)
        {
            parameters.clear();

            // First add the new values
            parameters.add(new EJStatementParameter("CUSTOMER_ID", Integer.class, record.getCustomerId()));
            parameters.add(new EJStatementParameter("DESCRIPTION", String.class, record.getDescription()));
            parameters.add(new EJStatementParameter("END_DATE", Date.class, record.getEndDate()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getName()));
            parameters.add(new EJStatementParameter("NOTES", String.class, record.getNotes()));
            parameters.add(new EJStatementParameter("START_DATE", Date.class, record.getStartDate()));
            parameters.add(new EJStatementParameter("STATUS", String.class, record.getStatus()));
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
            if (record.getInitialEndDate() == null)
            {
                criteria.add(EJRestrictions.isNull("END_DATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("END_DATE", record.getInitialEndDate()));
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
            if (record.getInitialNotes() == null)
            {
                criteria.add(EJRestrictions.isNull("NOTES"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NOTES", record.getInitialNotes()));
            }
            if (record.getInitialStartDate() == null)
            {
                criteria.add(EJRestrictions.isNull("START_DATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("START_DATE", record.getInitialStartDate()));
            }
            if (record.getInitialStatus() == null)
            {
                criteria.add(EJRestrictions.isNull("STATUS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("STATUS", record.getInitialStatus()));
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
            throw new EJApplicationException("Unexpected amount of records processed in update. Expected: " + updateRecords.size() + ". Updated: "
                    + recordsProcessed);
        }
    }

    @Override
    public void executeDelete(EJForm form, List<TimeEntryProject> recordsToDelete)
    {
        ArrayList<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (TimeEntryProject record : recordsToDelete)
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
            if (record.getInitialEndDate() == null)
            {
                criteria.add(EJRestrictions.isNull("END_DATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("END_DATE", record.getInitialEndDate()));
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
            if (record.getInitialNotes() == null)
            {
                criteria.add(EJRestrictions.isNull("NOTES"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NOTES", record.getInitialNotes()));
            }
            if (record.getInitialStartDate() == null)
            {
                criteria.add(EJRestrictions.isNull("START_DATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("START_DATE", record.getInitialStartDate()));
            }
            if (record.getInitialStatus() == null)
            {
                criteria.add(EJRestrictions.isNull("STATUS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("STATUS", record.getInitialStatus()));
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
            throw new EJApplicationException("Unexpected amount of records processed in delete. Expected: " + recordsToDelete.size() + ". Deleted: "
                    + recordsProcessed);
        }
    }

}