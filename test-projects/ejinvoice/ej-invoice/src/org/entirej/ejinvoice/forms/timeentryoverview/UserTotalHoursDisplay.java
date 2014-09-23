package org.entirej.ejinvoice.forms.timeentryoverview;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class UserTotalHoursDisplay
{
    private EJPojoProperty<Locale>     _locale;
    private EJPojoProperty<Integer>    _headerCode;
    private EJPojoProperty<BigDecimal> _hours;
    private EJPojoProperty<String>     _description;
    private EJPojoProperty<String>     _displayText;

    @EJFieldName("locale")
    public Locale getLocale()
    {
        return EJPojoProperty.getPropertyValue(_locale);
    }

    @EJFieldName("locale")
    public void setLocale(Locale locale)
    {
        _locale = EJPojoProperty.setPropertyValue(_locale, locale);
    }

    @EJFieldName("locale")
    public Locale getInitialLocale()
    {
        return EJPojoProperty.getPropertyInitialValue(_locale);
    }

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
    public BigDecimal getHours()
    {
        return EJPojoProperty.getPropertyValue(_hours);
    }

    @EJFieldName("hours")
    public void setHours(BigDecimal hours)
    {
        _hours = EJPojoProperty.setPropertyValue(_hours, hours);
    }

    @EJFieldName("hours")
    public BigDecimal getInitialHours()
    {
        return EJPojoProperty.getPropertyInitialValue(_hours);
    }

    @EJFieldName("description")
    public String getDescription()
    {
        return EJPojoProperty.getPropertyValue(_description);
    }

    @EJFieldName("description")
    public void setDescription(String description)
    {
        _description = EJPojoProperty.setPropertyValue(_description, description);
    }

    @EJFieldName("description")
    public String getInitialDescription()
    {
        return EJPojoProperty.getPropertyInitialValue(_description);
    }

    @EJFieldName("DISPLAY_TEXT")
    public String getInitialDisplayText()
    {
        return EJPojoProperty.getPropertyInitialValue(_displayText);
    }

    @EJFieldName("DISPLAY_TEXT")
    public void setDisplayText(String displayText)
    {
        _displayText = EJPojoProperty.setPropertyValue(_displayText, displayText);
    }

    @EJFieldName("DISPLAY_TEXT")
    public String getDisplayText()
    {
        NumberFormat format = null;
        if (getLocale() == null)
        {
            format = NumberFormat.getNumberInstance();
        }
        else
        {
            format = NumberFormat.getNumberInstance(getLocale());
        }
        format.setMinimumFractionDigits(2);
        
        StringBuilder display = new StringBuilder();
        display.append("<table border=0 cellpadding=0 cellspacing=0 width=100%");
        display.append("<tr>");

        display.append("<td align=left width=80%");

        if (getHeaderCode() != null && getHeaderCode() == 1)
        {
            display.append("<span style =\"font-weight: bold; font-size: 150%\">" + getDescription() + "</span>");
        }
        else
        {
            if (getHeaderCode() != null && getHeaderCode() == 2)
            {
                display.append("<span style =\"font-weight: bold; font-size: 120%\">" + getDescription() + "</span>");
            }
            else
            {
                if (getDescription() == null)
                {
                    display.append("<span style =\"font-weight: normal; font-size: 100% \">&nbsp;</span>");
                }
                else
                {
                    display.append("<span style =\"font-weight: normal; font-size: 100%\">" + getDescription() + "</span>");
                }
            }
        }

        display.append("</td>");

        display.append("<td align=right width=20%>");
        if (getHours() == null || (getHeaderCode() != null && getHeaderCode() == 1))
        {
            display.append("<span style =\"font-weight: bold; font-size: 100%; width=20%; \">&nbsp;</span>");
        }
        else
        {
            display.append("<span style =\"font-weight: bold; font-size: 100%; width=20% \">" + format.format(getHours()) + " hours</span>");
        }

        display.append("</td>");

        display.append("</tr>");

        display.append("</table>");

        return display.toString();
    }

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_locale);
        EJPojoProperty.clearInitialValue(_headerCode);
        EJPojoProperty.clearInitialValue(_hours);
        EJPojoProperty.clearInitialValue(_description);
        EJPojoProperty.clearInitialValue(_displayText);
    }
}