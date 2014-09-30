package org.entirej.ejinvoice.forms.masterdata;

import java.math.BigDecimal;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class VatRate
{
    private EJPojoProperty<Integer>    _companyId;
    private EJPojoProperty<BigDecimal> _rate;
    private EJPojoProperty<Integer>    _id;
    private EJPojoProperty<String>     _notes;
    private EJPojoProperty<String>     _name;
    private EJPojoProperty<String>     _displayText;

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

    @EJFieldName("RATE")
    public BigDecimal getRate()
    {
        return EJPojoProperty.getPropertyValue(_rate);
    }

    @EJFieldName("RATE")
    public void setRate(BigDecimal rate)
    {
        _rate = EJPojoProperty.setPropertyValue(_rate, rate);
    }

    @EJFieldName("RATE")
    public BigDecimal getInitialRate()
    {
        return EJPojoProperty.getPropertyInitialValue(_rate);
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

    @EJFieldName("DISPLAY_TEXT")
    public String getDisplayText()
    {
        StringBuilder display = new StringBuilder();
        display.append("<table border=0 cellpadding=0 cellspacing=0 width=100%");
        
        display.append("<tr>");
        display.append("<td align=left width=100% colspan=1>");
        display.append("<span style =\"font-weight: bold; font-size: 110% \">" + getName() + ": "+getRate()+"%</span>");
        display.append("</td>");
        display.append("</tr>");
        
        display.append("<tr>");
        display.append("<td align=left width=100% colspan=1>");
        display.append(getNotes());
        display.append("</td>");
        display.append("</tr>");
        
        display.append("</table>");

        return display.toString();
    }

    @EJFieldName("DISPLAY_TEXT")
    public void setDisplayText(String displayText)
    {
        _displayText = EJPojoProperty.setPropertyValue(_displayText, displayText);
    }

    @EJFieldName("DISPLAY_TEXT")
    public String getInitialDisplayText()
    {
        return EJPojoProperty.getPropertyInitialValue(_displayText);
    }

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_companyId);
        EJPojoProperty.clearInitialValue(_rate);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_notes);
        EJPojoProperty.clearInitialValue(_name);
        EJPojoProperty.clearInitialValue(_displayText);
    }

}