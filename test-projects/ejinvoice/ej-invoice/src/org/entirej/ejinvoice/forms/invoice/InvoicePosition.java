package org.entirej.ejinvoice.forms.invoice;

import java.math.BigDecimal;
import java.sql.Date;
import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class InvoicePosition
{
    private EJPojoProperty<String>     _taskName;
    private EJPojoProperty<BigDecimal> _fixPrice;
    private EJPojoProperty<Integer>    _cuptId;
    private EJPojoProperty<BigDecimal> _amount;
    private EJPojoProperty<BigDecimal> _hoursWorked;
    private EJPojoProperty<Integer>    _companyId;
    private EJPojoProperty<String>     _text;
    private EJPojoProperty<Integer>    _id;
    private EJPojoProperty<String>     _status;
    private EJPojoProperty<Integer>    _cuprId;
    private EJPojoProperty<Date>       _periodFrom;
    private EJPojoProperty<String>     _projectName;
    private EJPojoProperty<Date>       _periodTo;
    private EJPojoProperty<Integer>    _invId;
    private EJPojoProperty<BigDecimal> _payRate;

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

    @EJFieldName("CUPT_ID")
    public Integer getCuptId()
    {
        return EJPojoProperty.getPropertyValue(_cuptId);
    }

    @EJFieldName("CUPT_ID")
    public void setCuptId(Integer cuptId)
    {
        _cuptId = EJPojoProperty.setPropertyValue(_cuptId, cuptId);
    }

    @EJFieldName("CUPT_ID")
    public Integer getInitialCuptId()
    {
        return EJPojoProperty.getPropertyInitialValue(_cuptId);
    }

    @EJFieldName("AMOUNT")
    public BigDecimal getAmount()
    {
        return EJPojoProperty.getPropertyValue(_amount);
    }

    @EJFieldName("AMOUNT")
    public void setAmount(BigDecimal amount)
    {
        _amount = EJPojoProperty.setPropertyValue(_amount, amount);
    }

    @EJFieldName("AMOUNT")
    public BigDecimal getInitialAmount()
    {
        return EJPojoProperty.getPropertyInitialValue(_amount);
    }

    @EJFieldName("HOURS_WORKED")
    public BigDecimal getHoursWorked()
    {
        return EJPojoProperty.getPropertyValue(_hoursWorked);
    }

    @EJFieldName("HOURS_WORKED")
    public void setHoursWorked(BigDecimal hoursWorked)
    {
        _hoursWorked = EJPojoProperty.setPropertyValue(_hoursWorked, hoursWorked);
    }

    @EJFieldName("HOURS_WORKED")
    public BigDecimal getInitialHoursWorked()
    {
        return EJPojoProperty.getPropertyInitialValue(_hoursWorked);
    }

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

    @EJFieldName("TEXT")
    public String getText()
    {
        return EJPojoProperty.getPropertyValue(_text);
    }

    @EJFieldName("TEXT")
    public void setText(String text)
    {
        _text = EJPojoProperty.setPropertyValue(_text, text);
    }

    @EJFieldName("TEXT")
    public String getInitialText()
    {
        return EJPojoProperty.getPropertyInitialValue(_text);
    }

    @EJFieldName("ID")
    public Integer getId()
    {
        return EJPojoProperty.getPropertyValue(_id);
    }

    @EJFieldName("ID")
    public void setId(Integer id)
    {
        _id = EJPojoProperty.setPropertyValue(_id, id);
    }

    @EJFieldName("ID")
    public Integer getInitialId()
    {
        return EJPojoProperty.getPropertyInitialValue(_id);
    }

    @EJFieldName("STATUS")
    public String getStatus()
    {
        return EJPojoProperty.getPropertyValue(_status);
    }

    @EJFieldName("STATUS")
    public void setStatus(String status)
    {
        _status = EJPojoProperty.setPropertyValue(_status, status);
    }

    @EJFieldName("STATUS")
    public String getInitialStatus()
    {
        return EJPojoProperty.getPropertyInitialValue(_status);
    }

    @EJFieldName("CUPR_ID")
    public Integer getCuprId()
    {
        return EJPojoProperty.getPropertyValue(_cuprId);
    }

    @EJFieldName("CUPR_ID")
    public void setCuprId(Integer cuprId)
    {
        _cuprId = EJPojoProperty.setPropertyValue(_cuprId, cuprId);
    }

    @EJFieldName("CUPR_ID")
    public Integer getInitialCuprId()
    {
        return EJPojoProperty.getPropertyInitialValue(_cuprId);
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

    @EJFieldName("INV_ID")
    public Integer getInvId()
    {
        return EJPojoProperty.getPropertyValue(_invId);
    }

    @EJFieldName("INV_ID")
    public void setInvId(Integer invId)
    {
        _invId = EJPojoProperty.setPropertyValue(_invId, invId);
    }

    @EJFieldName("INV_ID")
    public Integer getInitialInvId()
    {
        return EJPojoProperty.getPropertyInitialValue(_invId);
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

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_taskName);
        EJPojoProperty.clearInitialValue(_fixPrice);
        EJPojoProperty.clearInitialValue(_cuptId);
        EJPojoProperty.clearInitialValue(_amount);
        EJPojoProperty.clearInitialValue(_hoursWorked);
        EJPojoProperty.clearInitialValue(_companyId);
        EJPojoProperty.clearInitialValue(_text);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_status);
        EJPojoProperty.clearInitialValue(_cuprId);
        EJPojoProperty.clearInitialValue(_periodFrom);
        EJPojoProperty.clearInitialValue(_projectName);
        EJPojoProperty.clearInitialValue(_periodTo);
        EJPojoProperty.clearInitialValue(_invId);
        EJPojoProperty.clearInitialValue(_payRate);
    }

}