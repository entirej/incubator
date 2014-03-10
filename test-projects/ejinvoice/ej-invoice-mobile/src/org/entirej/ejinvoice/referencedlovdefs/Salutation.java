package org.entirej.ejinvoice.referencedlovdefs;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class Salutation
{
    private EJPojoProperty<String>  _value;
    private EJPojoProperty<Integer> _id;
    private EJPojoProperty<Integer> _userId;

    @EJFieldName("VALUE")
    public String getValue()
    {
        return EJPojoProperty.getPropertyValue(_value);
    }

    @EJFieldName("VALUE")
    public void setValue(String value)
    {
        _value = EJPojoProperty.setPropertyValue(_value, value);
    }

    @EJFieldName("VALUE")
    public String getInitialValue()
    {
        return EJPojoProperty.getPropertyInitialValue(_value);
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

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_value);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_userId);
    }

}