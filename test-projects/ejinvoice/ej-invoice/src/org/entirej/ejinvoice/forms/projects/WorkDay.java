package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.sql.Date;

public class WorkDay
{
    private String     _projectName;
    private String     _taskName;
    private Date       _workDay;
    private BigDecimal _workHours;

    public String getProjectName()
    {
        return _projectName;
    }

    public void setProjectName(String projectName)
    {
        _projectName = projectName;
    }

    public String getTaskName()
    {
        return _taskName;
    }

    public void setTaskName(String taskName)
    {
        _taskName = taskName;
    }

    public Date getWorkDay()
    {
        return _workDay;
    }

    public void setWorkDay(Date workDay)
    {
        _workDay = workDay;
    }

    public BigDecimal getWorkHours()
    {
        return _workHours;
    }

    public void setWorkHours(BigDecimal workHours)
    {
        _workHours = workHours;
    }

}
