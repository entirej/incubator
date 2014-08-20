package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.sql.Date;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class OpenProjectItem
{
    private EJPojoProperty<Integer>    _companyId;
    private EJPojoProperty<Integer>    _invpId;
    private EJPojoProperty<Integer>    _projectId;
    private EJPojoProperty<Integer>    _taskId;
    private EJPojoProperty<String>     _projectName;
    private EJPojoProperty<String>     _taskName;
    private EJPojoProperty<Integer>    _teMonth;
    private EJPojoProperty<Integer>    _teYear;
    private EJPojoProperty<Date>       _teLastDay;
    private EJPojoProperty<Date>       _teFirstDay;
    private EJPojoProperty<BigDecimal> _workHours;
    private EJPojoProperty<BigDecimal> _payRate;
    private EJPojoProperty<String>     _createInvoicePosition;

    @EJFieldName("COMPANY_ID")
    public Integer getCompanyId()
    {
        return EJPojoProperty.getPropertyValue(_companyId);
    }

    @EJFieldName("COMPANY_ID")
    public void setCompanyId(Integer companyId)
    {
        _companyId = EJPojoProperty.setPropertyValue(_companyId, companyId);
    }

    @EJFieldName("COMPANY_ID")
    public Integer getInitialCompanyId()
    {
        return EJPojoProperty.getPropertyInitialValue(_companyId);
    }

    @EJFieldName("INVP_ID")
    public Integer getInvpId()
    {
        return EJPojoProperty.getPropertyValue(_invpId);
    }

    @EJFieldName("INVP_ID")
    public void setInvpId(Integer invpId)
    {
        _invpId = EJPojoProperty.setPropertyValue(_invpId, invpId);
    }

    @EJFieldName("INVP_ID")
    public Integer getInitialInvpId()
    {
        return EJPojoProperty.getPropertyInitialValue(_invpId);
    }

    @EJFieldName("PROJECT_ID")
    public Integer getProjectId()
    {
        return EJPojoProperty.getPropertyValue(_projectId);
    }

    @EJFieldName("PROJECT_ID")
    public void setProjectId(Integer projectId)
    {
        _projectId = EJPojoProperty.setPropertyValue(_projectId, projectId);
    }

    @EJFieldName("PROJECT_ID")
    public Integer getInitialProjectId()
    {
        return EJPojoProperty.getPropertyInitialValue(_projectId);
    }

    @EJFieldName("TASK_ID")
    public Integer getTaskId()
    {
        return EJPojoProperty.getPropertyValue(_taskId);
    }

    @EJFieldName("TASK_ID")
    public void setTaskId(Integer taskId)
    {
        _taskId = EJPojoProperty.setPropertyValue(_taskId, taskId);
    }

    @EJFieldName("TASK_ID")
    public Integer getInitialTaskId()
    {
        return EJPojoProperty.getPropertyInitialValue(_taskId);
    }

    @EJFieldName("PROJECT_NAME")
    public String getProjectName()
    {
        return EJPojoProperty.getPropertyValue(_projectName);
    }

    @EJFieldName("PROJECT_NAME")
    public void setProjectName(String projectName)
    {
        _projectName = EJPojoProperty.setPropertyValue(_projectName, projectName);
    }

    @EJFieldName("PROJECT_NAME")
    public String getInitialProjectName()
    {
        return EJPojoProperty.getPropertyInitialValue(_projectName);
    }

    @EJFieldName("TASK_NAME")
    public String getTaskName()
    {
        return EJPojoProperty.getPropertyValue(_taskName);
    }

    @EJFieldName("TASK_NAME")
    public void setTaskName(String taskName)
    {
        _taskName = EJPojoProperty.setPropertyValue(_taskName, taskName);
    }

    @EJFieldName("TASK_NAME")
    public String getInitialTaskName()
    {
        return EJPojoProperty.getPropertyInitialValue(_taskName);
    }

    @EJFieldName("TE_MONTH")
    public Integer getTeMonth()
    {
        return EJPojoProperty.getPropertyValue(_teMonth);
    }

    @EJFieldName("TE_MONTH")
    public void setTeMonth(Integer teMonth)
    {
        _teMonth = EJPojoProperty.setPropertyValue(_teMonth, teMonth);
    }

    @EJFieldName("TE_MONTH")
    public Integer getInitialTeMonth()
    {
        return EJPojoProperty.getPropertyInitialValue(_teMonth);
    }

    @EJFieldName("TE_YEAR")
    public Integer getTeYear()
    {
        return EJPojoProperty.getPropertyValue(_teYear);
    }

    @EJFieldName("TE_YEAR")
    public void setTeYear(Integer teYear)
    {
        _teYear = EJPojoProperty.setPropertyValue(_teYear, teYear);
    }

    @EJFieldName("TE_YEAR")
    public Integer getInitialTeYear()
    {
        return EJPojoProperty.getPropertyInitialValue(_teYear);
    }

    @EJFieldName("TE_LAST_DAY")
    public Date getTeLastDay()
    {
        return EJPojoProperty.getPropertyValue(_teLastDay);
    }

    @EJFieldName("TE_LAST_DAY")
    public void setTeLastDay(Date teLastDay)
    {
        _teLastDay = EJPojoProperty.setPropertyValue(_teLastDay, teLastDay);
    }

    @EJFieldName("TE_LAST_DAY")
    public Date getInitialTeLastDay()
    {
        return EJPojoProperty.getPropertyInitialValue(_teLastDay);
    }

    @EJFieldName("TE_FIRST_DAY")
    public Date getTeFirstDay()
    {
        return EJPojoProperty.getPropertyValue(_teFirstDay);
    }

    @EJFieldName("TE_FIRST_DAY")
    public void setTeFirstDay(Date teFirstDay)
    {
        _teFirstDay = EJPojoProperty.setPropertyValue(_teFirstDay, teFirstDay);
    }

    @EJFieldName("TE_FIRST_DAY")
    public Date getInitialTeFirstDay()
    {
        return EJPojoProperty.getPropertyInitialValue(_teFirstDay);
    }

    @EJFieldName("WORK_HOURS")
    public BigDecimal getWorkHours()
    {
        return EJPojoProperty.getPropertyValue(_workHours);
    }

    @EJFieldName("WORK_HOURS")
    public void setWorkHours(BigDecimal workHours)
    {
        _workHours = EJPojoProperty.setPropertyValue(_workHours, workHours);
    }

    @EJFieldName("WORK_HOURS")
    public BigDecimal getInitialWorkHours()
    {
        return EJPojoProperty.getPropertyInitialValue(_workHours);
    }

    @EJFieldName("PAY_RATE")
    public BigDecimal getPayRate()
    {
        return EJPojoProperty.getPropertyValue(_payRate);
    }

    @EJFieldName("PAY_RATE")
    public void setPayRate(BigDecimal payRate)
    {
        _payRate = EJPojoProperty.setPropertyValue(_payRate, payRate);
    }

    @EJFieldName("PAY_RATE")
    public BigDecimal getInitialPayRate()
    {
        return EJPojoProperty.getPropertyInitialValue(_payRate);
    }

    @EJFieldName("CREATE_INVOICE_POSITION")
    public String getCreateInvoicePosition()
    {
        return EJPojoProperty.getPropertyValue(_createInvoicePosition);
    }

    @EJFieldName("CREATE_INVOICE_POSITION")
    public void setCreateInvoicePosition(String createInvoicePosition)
    {
        _createInvoicePosition = EJPojoProperty.setPropertyValue(_createInvoicePosition, createInvoicePosition);
    }

    @EJFieldName("CREATE_INVOICE_POSITION")
    public String getInitialCreateInvoicePosition()
    {
        return EJPojoProperty.getPropertyInitialValue(_createInvoicePosition);
    }

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_companyId);
        EJPojoProperty.clearInitialValue(_invpId);
        EJPojoProperty.clearInitialValue(_payRate);
        EJPojoProperty.clearInitialValue(_projectId);
        EJPojoProperty.clearInitialValue(_taskId);
        EJPojoProperty.clearInitialValue(_projectName);
        EJPojoProperty.clearInitialValue(_taskName);
        EJPojoProperty.clearInitialValue(_teMonth);
        EJPojoProperty.clearInitialValue(_teYear);
        EJPojoProperty.clearInitialValue(_teFirstDay);
        EJPojoProperty.clearInitialValue(_teLastDay);
        EJPojoProperty.clearInitialValue(_workHours);
        EJPojoProperty.clearInitialValue(_createInvoicePosition);
    }

}