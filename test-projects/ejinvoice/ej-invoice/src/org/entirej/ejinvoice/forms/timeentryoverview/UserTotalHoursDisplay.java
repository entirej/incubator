package org.entirej.ejinvoice.forms.timeentryoverview;

import java.math.BigDecimal;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class UserTotalHoursDisplay
{
    private EJPojoProperty<Integer>    _headerCode;
    private EJPojoProperty<BigDecimal> _hours;
    private EJPojoProperty<String>     _description;

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

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_headerCode);
        EJPojoProperty.clearInitialValue(_hours);
        EJPojoProperty.clearInitialValue(_description);
    }
}