package org.entirej.ejinvoice.referencedlovdefs.services.pojos;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class Currency
{
    private EJPojoProperty<String>  _code;
    private EJPojoProperty<Integer> _userId;
    private EJPojoProperty<String>  _name;
    private EJPojoProperty<Integer> _id;

    @EJFieldName("CODE")
    public String getCode()
    {
        return EJPojoProperty.getPropertyValue(_code);
    }

    @EJFieldName("CODE")
    public void setCode(String code)
    {
        _code = EJPojoProperty.setPropertyValue(_code, code);
    }

    @EJFieldName("CODE")
    public String getInitialCode()
    {
        return EJPojoProperty.getPropertyInitialValue(_code);
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

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_code);
        EJPojoProperty.clearInitialValue(_userId);
        EJPojoProperty.clearInitialValue(_name);
        EJPojoProperty.clearInitialValue(_id);
    }

}