package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class ProjectTasks
{
    private EJPojoProperty<Integer>    _id;
    private EJPojoProperty<Integer>    _cprId;
    private EJPojoProperty<String>     _notes;
    private EJPojoProperty<String>     _name;
    private EJPojoProperty<BigDecimal> _payRate;
    private EJPojoProperty<Integer>    _companyId;
    private EJPojoProperty<BigDecimal> _fixPrice;
    private EJPojoProperty<String>     _invoiceable;
    private EJPojoProperty<String>     _status;

    private EJPojoProperty<String>     _customerName;
    private EJPojoProperty<String>     _projectName;
    private EJPojoProperty<String>     _displayText1;
    private EJPojoProperty<String>     _displayText2;
    private EJPojoProperty<BigDecimal> _price;

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

    @EJFieldName("CPR_ID")
    public Integer getCprId()
    {
        return EJPojoProperty.getPropertyValue(_cprId);
    }

    @EJFieldName("CPR_ID")
    public void setCprId(Integer cprId)
    {
        _cprId = EJPojoProperty.setPropertyValue(_cprId, cprId);
    }

    @EJFieldName("CPR_ID")
    public Integer getInitialCprId()
    {
        return EJPojoProperty.getPropertyInitialValue(_cprId);
    }

    @EJFieldName("NOTES")
    public String getNotes()
    {
        return EJPojoProperty.getPropertyValue(_notes);
    }

    @EJFieldName("NOTES")
    public void setNotes(String notes)
    {
        _notes = EJPojoProperty.setPropertyValue(_notes, notes);
    }

    @EJFieldName("NOTES")
    public String getInitialNotes()
    {
        return EJPojoProperty.getPropertyInitialValue(_notes);
    }

    @EJFieldName("NAME")
    public String getName()
    {
        return EJPojoProperty.getPropertyValue(_name);
    }

    @EJFieldName("NAME")
    public void setName(String name)
    {
        _name = EJPojoProperty.setPropertyValue(_name, name);
    }

    @EJFieldName("NAME")
    public String getInitialName()
    {
        return EJPojoProperty.getPropertyInitialValue(_name);
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

    @EJFieldName("PRICE")
    public BigDecimal getPrice()
    {
        return EJPojoProperty.getPropertyValue(_price);
    }

    @EJFieldName("PRICE")
    public void setPrice(BigDecimal price)
    {
        _price = EJPojoProperty.setPropertyValue(_price, price);
    }

    @EJFieldName("PRICE")
    public BigDecimal getInitialPrice()
    {
        return EJPojoProperty.getPropertyInitialValue(_price);
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

    @EJFieldName("INVOICEABLE")
    public String getInvoiceable()
    {
        return EJPojoProperty.getPropertyValue(_invoiceable);
    }

    @EJFieldName("INVOICEABLE")
    public void setInvoiceable(String invoiceable)
    {
        _invoiceable = EJPojoProperty.setPropertyValue(_invoiceable, invoiceable);
    }

    @EJFieldName("INVOICEABLE")
    public String getInitialInvoiceable()
    {
        return EJPojoProperty.getPropertyInitialValue(_invoiceable);
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

    @EJFieldName("DISPLAY_TEXT_1")
    public String getDisplayText1()
    {
        StringBuilder display = new StringBuilder();
        display.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"");
        display.append("<tr>");
        display.append("<td align=\"left\" width=\"30%\"><span style =\"font-weight: bold; font-size: 110% \">" + getProjectName() + "  (" + getName() + ")</span></td>");
        display.append("<td align=\"center\" width=\"30%\">");
        display.append("<span style =\"font-weight: normal; font-size: 110% \">" + getCustomerName() + "</span>");
        display.append("</td>");
        
        if (getFixPrice() != null)
        {
            display.append("<td align=\"right\"  width=\"30%\">Fix Price:</td>");
        }
        else
        {
            display.append("<td align=\"right\"  width=\"30%\">Hourly Rate:</td>");
        }
        
        if (getFixPrice() != null)
        {
            display.append("<td align=\"right\" width=\"5%\">"+ getFixPrice()  +"</td>");
        }
        else
        {
            if (getPayRate() == null)
            {
                display.append("<td align=\"right\" width=\"5%\">&nbsp;</td>");
            }
            else
            {
                display.append("<td align=\"right\" width=\"5%\">"+ getPayRate()  +"</td>");
            }
        }
        
        display.append("</tr>");

        display.append("<tr>");
        display.append("<td align=\"left\" colspan=4; height=1>");
        if (getNotes() != null)
        {
            display.append("<span style =\"font-weight: normal; font-size: 100% \">" + getNotes() + "</span>");
        }
        else
        {
            display.append("<span style =\"font-weight: normal; font-size: 100% \">&nbsp;</span>");
        }
        
        display.append("</tr>");
        display.append("</table>");

        return display.toString();
        
//        return EJPojoProperty.getPropertyValue(_displayText1);
    }

    @EJFieldName("DISPLAY_TEXT_1")
    public void setDisplayText1(String displayText1)
    {
        _displayText1 = EJPojoProperty.setPropertyValue(_displayText1, displayText1);
    }

    @EJFieldName("DISPLAY_TEXT_1")
    public String getInitialDisplayText1()
    {
        return EJPojoProperty.getPropertyInitialValue(_displayText1);
    }

    @EJFieldName("DISPLAY_TEXT_2")
    public String getDisplayText2()
    {
        return EJPojoProperty.getPropertyValue(_displayText2);
    }

    @EJFieldName("DISPLAY_TEXT_2")
    public void setDisplayText2(String displayText2)
    {
        _displayText2 = EJPojoProperty.setPropertyValue(_displayText2, displayText2);
    }

    @EJFieldName("DISPLAY_TEXT_2")
    public String getInitialDisplayText2()
    {
        return EJPojoProperty.getPropertyInitialValue(_displayText2);
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

    @EJFieldName("CUSTOMER_NAME")
    public String getCustomerName()
    {
        return EJPojoProperty.getPropertyValue(_customerName);
    }

    @EJFieldName("CUSTOMER_NAME")
    public void setCustomerName(String customerName)
    {
        _customerName = EJPojoProperty.setPropertyValue(_customerName, customerName);
    }

    @EJFieldName("CUSTOMER_NAME")
    public String getInitialCustomerName()
    {
        return EJPojoProperty.getPropertyInitialValue(_customerName);
    }

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_cprId);
        EJPojoProperty.clearInitialValue(_notes);
        EJPojoProperty.clearInitialValue(_name);
        EJPojoProperty.clearInitialValue(_payRate);
        EJPojoProperty.clearInitialValue(_companyId);
        EJPojoProperty.clearInitialValue(_fixPrice);
        EJPojoProperty.clearInitialValue(_invoiceable);
        EJPojoProperty.clearInitialValue(_status);

        EJPojoProperty.clearInitialValue(_price);
        EJPojoProperty.clearInitialValue(_customerName);
        EJPojoProperty.clearInitialValue(_projectName);
        EJPojoProperty.clearInitialValue(_displayText1);
        EJPojoProperty.clearInitialValue(_displayText2);
    }

}