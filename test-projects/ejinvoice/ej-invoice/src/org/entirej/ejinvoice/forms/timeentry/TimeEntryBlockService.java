package org.entirej.ejinvoice.forms.timeentry;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.entirej.ejinvoice.forms.timeentry.TimeEntry;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJManagedFrameworkConnection;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJQuerySort;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class TimeEntryBlockService implements EJBlockService<TimeEntry>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT CUPP_ID,END_TIME,ID,START_TIME,USER_ID,WORK_DATE,WORK_DESCRIPTION FROM customer_project_timeentry";

    public TimeEntryBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    private String getDiffMinutesString( Time start, Time end)
    {
        long diff = end.getTime() - start.getTime();
        long diffHours = diff / (60 * 60 * 1000);
        long diffMinutes = (diff / (60 * 1000))- (diffHours*60);
        
        String diffMinutesString = String.format("%02d", diffMinutes);
        
        return diffHours+":"+diffMinutesString;
    }

    @Override
    public List<TimeEntry> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        queryCriteria.add(EJQuerySort.ASC("WORK_DATE"));
        queryCriteria.add(EJQuerySort.ASC("START_TIME"));
        List<TimeEntry> timeEntries =  _statementExecutor.executeQuery(TimeEntry.class, form, _selectStatement, queryCriteria);
        for (TimeEntry entry : timeEntries)
        {
            entry.setHoursWorked(getDiffMinutesString(entry.getStartTime(), entry.getEndTime()));
        }
        
        return timeEntries;
    }

    @Override
    public void executeInsert(EJForm form, List<TimeEntry> newRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;
        for (TimeEntry record : newRecords)
        {
            // Initialise the value list
            parameters.clear();
            parameters.add(new EJStatementParameter("CUPP_ID", Integer.class, record.getCuppId()));
            parameters.add(new EJStatementParameter("END_TIME", Time.class, record.getEndTime()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("START_TIME", Time.class, record.getStartTime()));
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, record.getUserId()));
            parameters.add(new EJStatementParameter("WORK_DATE", Date.class, record.getWorkDate()));
            parameters.add(new EJStatementParameter("WORK_DESCRIPTION", String.class, record.getWorkDescription()));
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "customer_project_timeentry", parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != newRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: " + newRecords.size() + ". Inserted: " + recordsProcessed);
        }
    }

    public void enterTimeEntry(EJForm form, TimeEntry record)
    {
        EJManagedFrameworkConnection connection = form.getConnection();

        try
        {
            List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

            // Initialise the value list
            parameters.clear();
            parameters.add(new EJStatementParameter("CUPP_ID", Integer.class, record.getCuppId()));
            parameters.add(new EJStatementParameter("END_TIME", Time.class, record.getEndTime()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("START_TIME", Time.class, record.getStartTime()));
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, record.getUserId()));
            parameters.add(new EJStatementParameter("WORK_DATE", Date.class, record.getWorkDate()));
            parameters.add(new EJStatementParameter("WORK_DESCRIPTION", String.class, record.getWorkDescription()));
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            _statementExecutor.executeInsert(connection, "customer_project_timeentry", parameters.toArray(paramArray));

            connection.commit();
        }
        catch (Exception e) 
        {
            connection.rollback();
        }
        finally
        {
            connection.close();
        }

    }

    @Override
    public void executeUpdate(EJForm form, List<TimeEntry> updateRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (TimeEntry record : updateRecords)
        {
            parameters.clear();

            // First add the new values
            parameters.add(new EJStatementParameter("CUPP_ID", Integer.class, record.getCuppId()));
            parameters.add(new EJStatementParameter("END_TIME", Time.class, record.getEndTime()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("START_TIME", Time.class, record.getStartTime()));
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, record.getUserId()));
            parameters.add(new EJStatementParameter("WORK_DATE", Date.class, record.getWorkDate()));
            parameters.add(new EJStatementParameter("WORK_DESCRIPTION", String.class, record.getWorkDescription()));

            EJStatementCriteria criteria = new EJStatementCriteria();
            if (record.getInitialCuppId() == null)
            {
                criteria.add(EJRestrictions.isNull("CUPP_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CUPP_ID", record.getInitialCuppId()));
            }
            if (record.getInitialEndTime() == null)
            {
                criteria.add(EJRestrictions.isNull("END_TIME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("END_TIME", record.getInitialEndTime()));
            }
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialStartTime() == null)
            {
                criteria.add(EJRestrictions.isNull("START_TIME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("START_TIME", record.getInitialStartTime()));
            }
            if (record.getInitialUserId() == null)
            {
                criteria.add(EJRestrictions.isNull("USER_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("USER_ID", record.getInitialUserId()));
            }
            if (record.getInitialWorkDate() == null)
            {
                criteria.add(EJRestrictions.isNull("WORK_DATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("WORK_DATE", record.getInitialWorkDate()));
            }
            if (record.getInitialWorkDescription() == null)
            {
                criteria.add(EJRestrictions.isNull("WORK_DESCRIPTION"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("WORK_DESCRIPTION", record.getInitialWorkDescription()));
            }
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeUpdate(form, "customer_project_timeentry", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != updateRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in update. Expected: " + updateRecords.size() + ". Updated: " + recordsProcessed);
        }
    }

    @Override
    public void executeDelete(EJForm form, List<TimeEntry> recordsToDelete)
    {
        ArrayList<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (TimeEntry record : recordsToDelete)
        {
            parameters.clear();

            EJStatementCriteria criteria = new EJStatementCriteria();

            if (record.getInitialCuppId() == null)
            {
                criteria.add(EJRestrictions.isNull("CUPP_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CUPP_ID", record.getInitialCuppId()));
            }
            if (record.getInitialEndTime() == null)
            {
                criteria.add(EJRestrictions.isNull("END_TIME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("END_TIME", record.getInitialEndTime()));
            }
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialStartTime() == null)
            {
                criteria.add(EJRestrictions.isNull("START_TIME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("START_TIME", record.getInitialStartTime()));
            }
            if (record.getInitialUserId() == null)
            {
                criteria.add(EJRestrictions.isNull("USER_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("USER_ID", record.getInitialUserId()));
            }
            if (record.getInitialWorkDate() == null)
            {
                criteria.add(EJRestrictions.isNull("WORK_DATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("WORK_DATE", record.getInitialWorkDate()));
            }
            if (record.getInitialWorkDescription() == null)
            {
                criteria.add(EJRestrictions.isNull("WORK_DESCRIPTION"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("WORK_DESCRIPTION", record.getInitialWorkDescription()));
            }
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeDelete(form, "customer_project_timeentry", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != recordsToDelete.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in delete. Expected: " + recordsToDelete.size() + ". Deleted: " + recordsProcessed);
        }
    }

}