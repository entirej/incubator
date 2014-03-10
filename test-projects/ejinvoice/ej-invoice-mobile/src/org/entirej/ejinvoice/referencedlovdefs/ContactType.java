package org.entirej.ejinvoice.referencedlovdefs;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class ContactType
{
    private EJPojoProperty<Integer> _userId;
    private EJPojoProperty<Integer> _id;
    private EJPojoProperty<String>  _type;
    private EJPojoProperty<String>  _description;

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

    @EJFieldName("TYPE")
    public String getType()
    {
        return EJPojoProperty.getPropertyValue(_type);
    }

    @EJFieldName("TYPE")
    public void setType(String type)
    {
        _type = EJPojoProperty.setPropertyValue(_type, type);
    }

    @EJFieldName("TYPE")
    public String getInitialType()
    {
        return EJPojoProperty.getPropertyInitialValue(_type);
    }

    @EJFieldName("DESCRIPTION")
    public String getDescription()
    {
        return EJPojoProperty.getPropertyValue(_description);
    }

    @EJFieldName("DESCRIPTION")
    public void setDescription(String description)
    {
        _description = EJPojoProperty.setPropertyValue(_description, description);
    }

    @EJFieldName("DESCRIPTION")
    public String getInitialDescription()
    {
        return EJPojoProperty.getPropertyInitialValue(_description);
    }

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_userId);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_type);
        EJPojoProperty.clearInitialValue(_description);
    }

}