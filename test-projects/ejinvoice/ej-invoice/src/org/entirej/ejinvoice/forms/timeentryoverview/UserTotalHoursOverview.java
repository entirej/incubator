package org.entirej.ejinvoice.forms.timeentryoverview;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class UserTotalHoursOverview
{
    private EJPojoProperty<Locale>     _locale;
    private EJPojoProperty<Integer>    _userId;
    private EJPojoProperty<String>     _workDate;
    private EJPojoProperty<String>     _projectDescription;
    private EJPojoProperty<String>     _workPeriod;
    private EJPojoProperty<BigDecimal> _hours;
    private EJPojoProperty<String>     _workDescription;
    private EJPojoProperty<Integer>    _headerCode;
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
    public String getInitialWorkDate()
    {
        return EJPojoProperty.getPropertyInitialValue(_workDate);
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

        display.append("<td align=left width=60 colspan=1 rowspan=2>");

        if (getHeaderCode() != null && getHeaderCode() == 2)
        {
            display.append("<span style =\"font-weight: bold; font-size: 150%; rowspan=2 \">" + getWorkPeriod() + "</span>");
        }
        else
        {
            if (getWorkDate() == null)
            {
                display.append("<span style =\"font-weight: normal; font-size: 100% \">&nbsp;</span>");
            }
            else
            {
                display.append("<span style =\"font-weight: normal; font-size: 100% \">" + getWorkDate() + "  (" + getWorkPeriod() + ")</span>");
            }
        }

        display.append("</td>");

        display.append("<td align=left width=75% colspan=1 rowspan=1>");
        if (getHeaderCode() != null && getHeaderCode() == 2)
        {
            display.append("<span style =\"font-weight: bold; font-size: 100% \">&nbsp;</span>");
        }
        else
        {
            display.append("<span style =\"font-weight: bold; font-size: 100% \">" + getProjectDescription() + "</span>");
        }
        display.append("</td>");

        display.append("<td align=right width=10% colspan=1 rowspan=2>");
        if (getHours() == null || (getHeaderCode() != null && getHeaderCode() == 2))
        {
            display.append("<span style =\"font-weight: normal; font-size: 100% \">&nbsp;</span>");
        }
        else
        {
            display.append("<span style =\"font-weight: normal; font-size: 100% \">" + format.format(getHours()) + " hours</span>");
        }
        display.append("</td>");

        display.append("</tr>");

        display.append("<tr>");
        if (getWorkDescription() == null)
        {
            display.append("<td align=\"left\"  width=\"75%\">&nbsp;</td>");
        }
        else
        {
            display.append("<td align=\"left\"  width=\"75%\">" + getWorkDescription() + "</td>");
        }
        display.append("</tr>");
        display.append("<tr>");

        display.append("</table>");

        return display.toString();
    }

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_locale);
        EJPojoProperty.clearInitialValue(_hours);
        EJPojoProperty.clearInitialValue(_userId);
        EJPojoProperty.clearInitialValue(_workPeriod);
        EJPojoProperty.clearInitialValue(_projectDescription);
        EJPojoProperty.clearInitialValue(_workDescription);
        EJPojoProperty.clearInitialValue(_workDate);
        EJPojoProperty.clearInitialValue(_displayText);
    }

}