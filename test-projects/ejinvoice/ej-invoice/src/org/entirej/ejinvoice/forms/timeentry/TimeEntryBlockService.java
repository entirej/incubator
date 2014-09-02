package org.entirej.ejinvoice.forms.timeentry;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJBlock;
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
    private String                    _selectStatement = "SELECT COMPANY_ID, INVP_ID, CUPT_ID,END_TIME,ID,START_TIME,USER_ID,WORK_DATE,WORK_DESCRIPTION, (SELECT CPR_ID FROM CUSTOMER_PROJECT_TASKS WHERE ID = CUPT_ID) CUPR_ID FROM customer_project_timeentry";

    public TimeEntryBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    public static int getCurrentWeek()
    {
        Calendar c = Calendar.getInstance();
        c.setTime(new java.util.Date());

        return c.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getWeek(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(new java.util.Date(date.getTime()));

        return c.get(Calendar.WEEK_OF_YEAR);
    }

    public static EJQueryCriteria getWeeKQueryCriteria(EJQueryCriteria criteria, int week)
    {

        Calendar c = Calendar.getInstance();
        c.setTime(new java.util.Date());
        c.set(Calendar.WEEK_OF_YEAR, week);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        java.util.Date weekStart = c.getTime();

        // we do not need the same day a week after, that's why use 6, not 7
        c.add(Calendar.DAY_OF_MONTH, 6);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        java.util.Date weekEnd = c.getTime();

        criteria.add(EJRestrictions.between("WORK_DATE", weekStart, weekEnd));

        return criteria;
    }

    private String getDiffMinutesString(Time start, Time end)
    {
        long diff = end.getTime() - start.getTime();
        long diffHours = diff / (60 * 60 * 1000);
        long diffMinutes = (diff / (60 * 1000)) - (diffHours * 60);

        String diffMinutesString = String.format("%02d", diffMinutes);

        return diffHours + ":" + diffMinutesString;
    }

    @Override
    public List<TimeEntry> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        queryCriteria.add(EJQuerySort.ASC("WORK_DATE"));
        queryCriteria.add(EJQuerySort.ASC("START_TIME"));
        List<TimeEntry> timeEntries = _statementExecutor.executeQuery(TimeEntry.class, form, _selectStatement, queryCriteria);
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
            parameters.add(new EJStatementParameter("CUPT_ID", Integer.class, record.getCuptId()));
            parameters.add(new EJStatementParameter("END_TIME", Time.class, removeSeconds(record.getEndTime())));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("START_TIME", Time.class, removeSeconds(record.getStartTime())));
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, record.getUserId()));
            parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, record.getCompanyId()));
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
    
    Time removeSeconds(Time time)
    {
        if( time==null) return null;
        Calendar instance = Calendar.getInstance();
        instance.setTime(time);
        instance.set(Calendar.SECOND, 0);
        instance.set(Calendar.MILLISECOND, 0);
        
        return new Time((instance.getTime().getTime()));//remove seconds 
    }

    public void enterTimeEntry(EJForm form, TimeEntry record)
    {
        EJManagedFrameworkConnection connection = form.getConnection();

        try
        {
            List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

            // Initialise the value list
            parameters.clear();
            parameters.add(new EJStatementParameter("CUPT_ID", Integer.class, record.getCuptId()));
            parameters.add(new EJStatementParameter("END_TIME", Time.class, removeSeconds(record.getEndTime())));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, record.getUserId()));
            parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, record.getCompanyId()));
            parameters.add(new EJStatementParameter("START_TIME", Time.class, removeSeconds(record.getStartTime())));
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
            parameters.add(new EJStatementParameter("CUPT_ID", Integer.class, record.getCuptId()));
            parameters.add(new EJStatementParameter("END_TIME", Time.class, removeSeconds(record.getEndTime())));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("START_TIME", Time.class, removeSeconds(record.getStartTime())));
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, record.getUserId()));
            parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, record.getCompanyId()));
            parameters.add(new EJStatementParameter("WORK_DATE", Date.class, record.getWorkDate()));
            parameters.add(new EJStatementParameter("WORK_DESCRIPTION", String.class, record.getWorkDescription()));

            EJStatementCriteria criteria = new EJStatementCriteria();
            if (record.getInitialCuptId() == null)
            {
                criteria.add(EJRestrictions.isNull("CUPt_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CUPt_ID", record.getInitialCuptId()));
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
            if (record.getInitialCompanyId() == null)
            {
                criteria.add(EJRestrictions.isNull("COMPANY_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("COMPANY_ID", record.getInitialCompanyId()));
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

            if (record.getInitialCuptId() == null)
            {
                criteria.add(EJRestrictions.isNull("CUPT_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CUPT_ID", record.getInitialCuptId()));
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
            if (record.getInitialCompanyId() == null)
            {
                criteria.add(EJRestrictions.isNull("COMPANY_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("COMPANY_ID", record.getInitialCompanyId()));
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