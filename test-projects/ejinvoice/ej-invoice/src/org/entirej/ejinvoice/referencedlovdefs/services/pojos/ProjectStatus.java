package org.entirej.ejinvoice.referencedlovdefs.services.pojos;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class ProjectStatus
{
    private EJPojoProperty<Integer> _orderKey;
    private EJPojoProperty<String>  _description;
    private EJPojoProperty<Integer> _companyId;
    private EJPojoProperty<Integer> _id;
    private EJPojoProperty<String>  _name;

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
        EJPojoProperty.clearInitialValue(_orderKey);
        EJPojoProperty.clearInitialValue(_description);
        EJPojoProperty.clearInitialValue(_companyId);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_name);
    }

}