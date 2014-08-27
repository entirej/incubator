package org.entirej.ejinvoice.referencedlovdefs.services.pojos;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class TaskStatus
{
    private EJPojoProperty<String>  _description;
    private EJPojoProperty<String>  _code;
    private EJPojoProperty<String>  _name;

    public TaskStatus()
    {
    }
    
    public TaskStatus(String code, String name, String description)
    {
        setCode(code);
        setName(name);
        setDescription(description);
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

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_description);
        EJPojoProperty.clearInitialValue(_name);
        EJPojoProperty.clearInitialValue(_code);
    }

}