package org.entirej.ejinvoice.forms.timeentryoverview;

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
    }
    

    @Override
    public void executeUpdate(EJForm form, List<TimeEntry> updateRecords)
    {
    }

    @Override
    public void executeDelete(EJForm form, List<TimeEntry> recordsToDelete)
    {
    }

}