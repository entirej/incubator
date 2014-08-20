package org.entirej.ejinvoice.referencedlovdefs.services.pojos;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class Currency
{
    private EJPojoProperty<Integer> _companyId;
    private EJPojoProperty<String>  _code;
    private EJPojoProperty<String>  _name;
    private EJPojoProperty<Integer> _id;

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
        EJPojoProperty.clearInitialValue(_companyId);
        EJPojoProperty.clearInitialValue(_code);
        EJPojoProperty.clearInitialValue(_name);
        EJPojoProperty.clearInitialValue(_id);
    }

}