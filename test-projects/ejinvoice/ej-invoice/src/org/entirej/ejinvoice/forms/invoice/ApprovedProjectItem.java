package org.entirej.ejinvoice.forms.invoice;

import java.math.BigDecimal;
import java.sql.Date;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class ApprovedProjectItem
{
    private EJPojoProperty<String>     _displayText;

    private EJPojoProperty<BigDecimal> _hoursWorked;
    private EJPojoProperty<String>     _taskName;
    private EJPojoProperty<String>     _status;
    private EJPojoProperty<Integer>    _cuprId;
    private EJPojoProperty<Date>       _periodFrom;
    private EJPojoProperty<Integer>    _invId;
    private EJPojoProperty<BigDecimal> _amount;
    private EJPojoProperty<Integer>    _id;
    private EJPojoProperty<BigDecimal> _payRate;
    private EJPojoProperty<Integer>    _companyId;
    private EJPojoProperty<String>     _projectName;
    private EJPojoProperty<Date>       _periodTo;
    private EJPojoProperty<Integer>    _cuptId;
    private EJPojoProperty<BigDecimal> _fixPrice;
    private EJPojoProperty<String>     _text;
    private EJPojoProperty<String>     _addToInvoice;

    @EJFieldName("DISPLAY_TEXT")
    public String getInitialDisplayText()
    {
        return EJPojoProperty.getPropertyInitialValue(_displayText);
    }

    @EJFieldName("DISPLAY_TEXT")
    public void setDisplayText(String displayText)
    {
        _taskName = EJPojoProperty.setPropertyValue(_displayText, displayText);
    }

    @EJFieldName("DISPLAY_TEXT")
    public String getDisplayText()
    {
        StringBuilder display = new StringBuilder();
        display.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"");
        display.append("<tr>");
        
        display.append("<td align=\"left\" width=\"45%\">");
            display.append("<span style =\"font-weight: bold; font-size: 110% \">" + getProjectName() + "  (" + getTaskName() + ")</span>");
        display.append("</td>");
        
        if (getFixPrice() != null)
        {
            display.append("<td align=\"right\"  width=\"45%\">Fix Price:</td>");
        }
        else
        {
            display.append("<td align=\"right\"  width=\"45%\">Hourly Rate:</td>");
        }
        
        if (getFixPrice() != null)
        {
            display.append("<td align=\"right\" width=\"10%\">"+ getFixPrice()  +"</td>");
        }
        else
        {
            if (getPayRate() == null)
            {
                display.append("<td align=\"right\" width=\"10%\">&nbsp;</td>");
            }
            else
            {
                display.append("<td align=\"right\" width=\"10%\">"+ getPayRate()  +"</td>");
            }
        }
        
        display.append("</tr>");

        display.append("<tr>");
        display.append("<td align=\"left\" width=\"65\" height=1>");
        display.append("<span style =\"font-weight: normal; font-size: 100% \">" + getPeriodFrom() + " - "+getPeriodTo()+"</span></td>");
        
        display.append("<td align=\"right\"  width=\"30%\">Hours:</td>");
        
        if (getHoursWorked()== null)
        {
            display.append("<td align=\"right\" width=\"10%\">&nbsp;</td>");
        }
        else
        {
            display.append("<td align=\"right\" width=\"10%\">"+ getHoursWorked()  +"</td>");
        }
        
        display.append("</tr>");
        display.append("</table>");

        return display.toString();
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

    @EJFieldName("ADD_TO_INVOICE")
    public String getAddToInvoice()
    {
        return EJPojoProperty.getPropertyValue(_addToInvoice);
    }

    @EJFieldName("ADD_TO_INVOICE")
    public void setAddToInvoice(String addToInvoice)
    {
        _addToInvoice = EJPojoProperty.setPropertyValue(_addToInvoice, addToInvoice);
    }

    @EJFieldName("ADD_TO_INVOICE")
    public String getInitialAddToInvoice()
    {
        return EJPojoProperty.getPropertyInitialValue(_addToInvoice);
    }

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_hoursWorked);
        EJPojoProperty.clearInitialValue(_taskName);
        EJPojoProperty.clearInitialValue(_status);
        EJPojoProperty.clearInitialValue(_cuprId);
        EJPojoProperty.clearInitialValue(_periodFrom);
        EJPojoProperty.clearInitialValue(_invId);
        EJPojoProperty.clearInitialValue(_amount);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_payRate);
        EJPojoProperty.clearInitialValue(_companyId);
        EJPojoProperty.clearInitialValue(_projectName);
        EJPojoProperty.clearInitialValue(_periodTo);
        EJPojoProperty.clearInitialValue(_cuptId);
        EJPojoProperty.clearInitialValue(_fixPrice);
        EJPojoProperty.clearInitialValue(_text);
        EJPojoProperty.clearInitialValue(_addToInvoice);
    }

}