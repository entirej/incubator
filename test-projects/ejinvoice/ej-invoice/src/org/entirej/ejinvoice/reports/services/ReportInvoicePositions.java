package org.entirej.ejinvoice.reports.services;

import java.math.BigDecimal;
import java.sql.Date;
import org.entirej.framework.report.EJReportFieldName;

public class ReportInvoicePositions
{
    private String     _projectName;
    private Date       _periodTo;
    private BigDecimal _amount;
    private BigDecimal _payRate;
    private Date       _periodFrom;
    private String     _text;
    private Integer    _cuptId;
    private Integer    _id;
    private Integer    _companyId;
    private BigDecimal _hoursWorked;
    private Integer    _invId;
    private String     _status;
    private Integer    _cuprId;
    private BigDecimal _fixPrice;
    private String     _taskName;

    @EJReportFieldName("PROJECT_NAME")
    public String getProjectName()
    {
        return _projectName;
    }

    @EJReportFieldName("PROJECT_NAME")
    public void setProjectName(String projectName)
    {
        _projectName = projectName;
    }

    @EJReportFieldName("PERIOD_TO")
    public Date getPeriodTo()
    {
        return _periodTo;
    }

    @EJReportFieldName("PERIOD_TO")
    public void setPeriodTo(Date periodTo)
    {
        _periodTo = periodTo;
    }

    @EJReportFieldName("AMOUNT")
    public BigDecimal getAmount()
    {
        return _amount;
    }

    @EJReportFieldName("AMOUNT")
    public void setAmount(BigDecimal amount)
    {
        _amount = amount;
    }

    @EJReportFieldName("PAY_RATE")
    public BigDecimal getPayRate()
    {
        return _payRate;
    }

    @EJReportFieldName("PAY_RATE")
    public void setPayRate(BigDecimal payRate)
    {
        _payRate = payRate;
    }

    @EJReportFieldName("PERIOD_FROM")
    public Date getPeriodFrom()
    {
        return _periodFrom;
    }

    @EJReportFieldName("PERIOD_FROM")
    public void setPeriodFrom(Date periodFrom)
    {
        _periodFrom = periodFrom;
    }

    @EJReportFieldName("TEXT")
    public String getText()
    {
        return _text!=null? _text.trim() :_text;
    }

    @EJReportFieldName("TEXT")
    public void setText(String text)
    {
        _text = text;
    }

    @EJReportFieldName("CUPT_ID")
    public Integer getCuptId()
    {
        return _cuptId;
    }

    @EJReportFieldName("CUPT_ID")
    public void setCuptId(Integer cuptId)
    {
        _cuptId = cuptId;
    }

    @EJReportFieldName("ID")
    public Integer getId()
    {
        return _id;
    }

    @EJReportFieldName("ID")
    public void setId(Integer id)
    {
        _id = id;
    }

    @EJReportFieldName("COMPANY_ID")
    public Integer getCompanyId()
    {
        return _companyId;
    }

    @EJReportFieldName("COMPANY_ID")
    public void setCompanyId(Integer companyId)
    {
        _companyId = companyId;
    }

    @EJReportFieldName("HOURS_WORKED")
    public BigDecimal getHoursWorked()
    {
        return _hoursWorked;
    }

    @EJReportFieldName("HOURS_WORKED")
    public void setHoursWorked(BigDecimal hoursWorked)
    {
        _hoursWorked = hoursWorked;
    }

    @EJReportFieldName("INV_ID")
    public Integer getInvId()
    {
        return _invId;
    }

    @EJReportFieldName("INV_ID")
    public void setInvId(Integer invId)
    {
        _invId = invId;
    }

    @EJReportFieldName("STATUS")
    public String getStatus()
    {
        return _status;
    }

    @EJReportFieldName("STATUS")
    public void setStatus(String status)
    {
        _status = status;
    }

    @EJReportFieldName("CUPR_ID")
    public Integer getCuprId()
    {
        return _cuprId;
    }

    @EJReportFieldName("CUPR_ID")
    public void setCuprId(Integer cuprId)
    {
        _cuprId = cuprId;
    }

    @EJReportFieldName("FIX_PRICE")
    public BigDecimal getFixPrice()
    {
        return _fixPrice;
    }

    @EJReportFieldName("FIX_PRICE")
    public void setFixPrice(BigDecimal fixPrice)
    {
        _fixPrice = fixPrice;
    }

    @EJReportFieldName("TASK_NAME")
    public String getTaskName()
    {
        return _taskName;
    }

    @EJReportFieldName("TASK_NAME")
    public void setTaskName(String taskName)
    {
        _taskName = taskName;
    }

}