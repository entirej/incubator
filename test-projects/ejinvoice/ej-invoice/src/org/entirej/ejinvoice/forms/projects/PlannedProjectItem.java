package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.sql.Date;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class PlannedProjectItem
{
    private EJPojoProperty<Integer>    _companyId;
    private EJPojoProperty<Integer>    _invpId;
    private EJPojoProperty<String>     _invpText;
    private EJPojoProperty<Integer>    _projectId;
    private EJPojoProperty<Integer>    _taskId;
    private EJPojoProperty<String>     _projectName;
    private EJPojoProperty<String>     _taskName;
    private EJPojoProperty<Integer>    _teMonth;
    private EJPojoProperty<Integer>    _teYear;
    private EJPojoProperty<Date>       _periodFrom;
    private EJPojoProperty<Date>       _periodTo;
    private EJPojoProperty<BigDecimal> _workHours;
    private EJPojoProperty<BigDecimal> _payRate;
    private EJPojoProperty<BigDecimal> _fixPrice;
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

    @EJFieldName("INVP_TEXT")
    public String getInvpText()
    {
        return EJPojoProperty.getPropertyValue(_invpText);
    }

    @EJFieldName("INVP_TEXT")
    public void setInvpText(String invpText)
    {
        _invpText = EJPojoProperty.setPropertyValue(_invpText, invpText);
    }

    @EJFieldName("INVP_TEXT")
    public String getInitialInvpText()
    {
        return EJPojoProperty.getPropertyInitialValue(_invpText);
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

    @EJFieldName("PERIOD_FROM")
    public Date getPeriodFrom()
    {
        return EJPojoProperty.getPropertyValue(_periodFrom);
    }

    @EJFieldName("PERIOD_FROM")
    public void setPeriodFrom(Date periodFrom)
    {
        _periodFrom = EJPojoProperty.setPropertyValue(_periodFrom, periodFrom);
    }

    @EJFieldName("PERIOD_FROM")
    public Date getInitialPeriodFrom()
    {
        return EJPojoProperty.getPropertyInitialValue(_periodFrom);
    }

    @EJFieldName("PERIOD_TO")
    public Date getPeriodTo()
    {
        return EJPojoProperty.getPropertyValue(_periodTo);
    }

    @EJFieldName("PERIOD_TO")
    public void setPeriodTo(Date periodTo)
    {
        _periodTo = EJPojoProperty.setPropertyValue(_periodTo, periodTo);
    }

    @EJFieldName("PERIOD_TO")
    public Date getInitialPeriodTo()
    {
        return EJPojoProperty.getPropertyInitialValue(_periodTo);
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

    @EJFieldName("FIX_PRICE")
    public BigDecimal getFixPrice()
    {
        return EJPojoProperty.getPropertyValue(_fixPrice);
    }

    @EJFieldName("FIX_PRICE")
    public void setFixPrice(BigDecimal fixPrice)
    {
        _fixPrice = EJPojoProperty.setPropertyValue(_fixPrice, fixPrice);
    }

    @EJFieldName("FIX_PRICE")
    public BigDecimal getInitialFixPrice()
    {
        return EJPojoProperty.getPropertyInitialValue(_fixPrice);
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
        EJPojoProperty.clearInitialValue(_fixPrice);
        EJPojoProperty.clearInitialValue(_companyId);
        EJPojoProperty.clearInitialValue(_invpText);
        EJPojoProperty.clearInitialValue(_invpId);
        EJPojoProperty.clearInitialValue(_projectId);
        EJPojoProperty.clearInitialValue(_taskId);
        EJPojoProperty.clearInitialValue(_projectName);
        EJPojoProperty.clearInitialValue(_taskName);
        EJPojoProperty.clearInitialValue(_teMonth);
        EJPojoProperty.clearInitialValue(_teYear);
        EJPojoProperty.clearInitialValue(_periodFrom);
        EJPojoProperty.clearInitialValue(_periodTo);
        EJPojoProperty.clearInitialValue(_workHours);
        EJPojoProperty.clearInitialValue(_payRate);
        EJPojoProperty.clearInitialValue(_createInvoicePosition);
    }

}