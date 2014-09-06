package org.entirej.ejinvoice.forms.timeentryoverview;

import java.math.BigDecimal;
import java.sql.Date;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class UserTotalHoursOverview
{
    private EJPojoProperty<Integer> _userId;
    private EJPojoProperty<String>  _workDate;
    private EJPojoProperty<String>  _projectDescription;
    private EJPojoProperty<String>  _workPeriod;
    private EJPojoProperty<String>  _hours;
    private EJPojoProperty<String>  _workDescription;
    private EJPojoProperty<Integer> _headerCode;

    @EJFieldName("header_code")
    public Integer getHeaderCode()
    {
        return EJPojoProperty.getPropertyValue(_headerCode);
    }

    @EJFieldName("header_code")
    public void setHeaderCode(Integer headerCode)
    {
        _headerCode = EJPojoProperty.setPropertyValue(_headerCode, headerCode);
    }

    @EJFieldName("header_code")
    public Integer getInitialHeaderCode()
    {
        return EJPojoProperty.getPropertyInitialValue(_headerCode);
    }

    @EJFieldName("hours")
    public String getHours()
    {
        return EJPojoProperty.getPropertyValue(_hours);
    }

    @EJFieldName("hours")
    public void setHours(String hours)
    {
        _hours = EJPojoProperty.setPropertyValue(_hours, hours);
    }

    @EJFieldName("hours")
    public String getInitialHours()
    {
        return EJPojoProperty.getPropertyInitialValue(_hours);
    }

    @EJFieldName("user_id")
    public Integer getUserId()
    {
        return EJPojoProperty.getPropertyValue(_userId);
    }

    @EJFieldName("user_id")
    public void setUserId(Integer userId)
    {
        _userId = EJPojoProperty.setPropertyValue(_userId, userId);
    }

    @EJFieldName("user_id")
    public Integer getInitialUserId()
    {
        return EJPojoProperty.getPropertyInitialValue(_userId);
    }

    @EJFieldName("work_period")
    public String getWorkPeriod()
    {
        return EJPojoProperty.getPropertyValue(_workPeriod);
    }

    @EJFieldName("work_period")
    public void setWorkPeriod(String workPeriod)
    {
        _workPeriod = EJPojoProperty.setPropertyValue(_workPeriod, workPeriod);
    }

    @EJFieldName("work_period")
    public String getInitialWorkPeriod()
    {
        return EJPojoProperty.getPropertyInitialValue(_workPeriod);
    }

    @EJFieldName("project_description")
    public String getProjectDescription()
    {
        return EJPojoProperty.getPropertyValue(_projectDescription);
    }

    @EJFieldName("project_description")
    public void setProjectDescription(String projectDescription)
    {
        _projectDescription = EJPojoProperty.setPropertyValue(_projectDescription, projectDescription);
    }

    @EJFieldName("project_description")
    public String getInitialProjectDescription()
    {
        return EJPojoProperty.getPropertyInitialValue(_projectDescription);
    }

    @EJFieldName("work_description")
    public String getWorkDescription()
    {
        return EJPojoProperty.getPropertyValue(_workDescription);
    }

    @EJFieldName("work_description")
    public void setWorkDescription(String workDescription)
    {
        _workDescription = EJPojoProperty.setPropertyValue(_workDescription, workDescription);
    }

    @EJFieldName("work_description")
    public String getInitialWorkDescription()
    {
        return EJPojoProperty.getPropertyInitialValue(_workDescription);
    }

    @EJFieldName("work_date")
    public String getWorkDate()
    {
        return EJPojoProperty.getPropertyValue(_workDate);
    }

    @EJFieldName("work_date")
    public void setWorkDate(String workDate)
    {
        _workDate = EJPojoProperty.setPropertyValue(_workDate, workDate);
    }

    @EJFieldName("work_date")
    public String getInitialWorDarw()
    {
        return EJPojoProperty.getPropertyInitialValue(_workDate);
    }

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_hours);
        EJPojoProperty.clearInitialValue(_userId);
        EJPojoProperty.clearInitialValue(_workPeriod);
        EJPojoProperty.clearInitialValue(_projectDescription);
        EJPojoProperty.clearInitialValue(_workDescription);
        EJPojoProperty.clearInitialValue(_workDate);
    }

}