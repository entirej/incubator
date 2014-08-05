package org.entirej.ejinvoice.forms.masterdata;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class ProjectStatus
{
    private EJPojoProperty<String>  _name;
    private EJPojoProperty<Integer> _orderKey;
    private EJPojoProperty<String>  _description;
    private EJPojoProperty<Integer> _id;
    private EJPojoProperty<Integer> _userId;

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

    @EJFieldName("ORDER_KEY")
    public Integer getOrderKey()
    {
        return EJPojoProperty.getPropertyValue(_orderKey);
    }

    @EJFieldName("ORDER_KEY")
    public void setOrderKey(Integer orderKey)
    {
        _orderKey = EJPojoProperty.setPropertyValue(_orderKey, orderKey);
    }

    @EJFieldName("ORDER_KEY")
    public Integer getInitialOrderKey()
    {
        return EJPojoProperty.getPropertyInitialValue(_orderKey);
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
        EJPojoProperty.clearInitialValue(_name);
        EJPojoProperty.clearInitialValue(_orderKey);
        EJPojoProperty.clearInitialValue(_description);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_userId);
    }

}