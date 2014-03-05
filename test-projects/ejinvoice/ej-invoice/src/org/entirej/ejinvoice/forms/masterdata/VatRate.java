package org.entirej.ejinvoice.forms.masterdata;

import java.math.BigDecimal;
import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class VatRate
{
    private EJPojoProperty<Integer>    _userId;
    private EJPojoProperty<BigDecimal> _rate;
    private EJPojoProperty<Integer>    _id;
    private EJPojoProperty<String>     _notes;
    private EJPojoProperty<String>     _name;

    @EJFieldName("USER_ID")
    public Integer getUserId()
    {
        return EJPojoProperty.getPropertyValue(_userId);
    }

    @EJFieldName("USER_ID")
    public void setUserId(Integer userId)
    {
        _userId = EJPojoProperty.setPropertyValue(_userId, userId);
    }

    @EJFieldName("USER_ID")
    public Integer getInitialUserId()
    {
        return EJPojoProperty.getPropertyInitialValue(_userId);
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

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_userId);
        EJPojoProperty.clearInitialValue(_rate);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_notes);
        EJPojoProperty.clearInitialValue(_name);
    }

}