package org.entirej.ejinvoice.email;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class EmailInfo
{
    private EJPojoProperty<String>  _type;
    private EJPojoProperty<String>  _url;
    private EJPojoProperty<Integer> _id;
    private EJPojoProperty<String>  _address;
    private EJPojoProperty<String>  _password;

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

    @EJFieldName("URL")
    public String getUrl()
    {
        return EJPojoProperty.getPropertyValue(_url);
    }

    @EJFieldName("URL")
    public void setUrl(String url)
    {
        _url = EJPojoProperty.setPropertyValue(_url, url);
    }

    @EJFieldName("URL")
    public String getInitialUrl()
    {
        return EJPojoProperty.getPropertyInitialValue(_url);
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

    @EJFieldName("ADDRESS")
    public String getAddress()
    {
        return EJPojoProperty.getPropertyValue(_address);
    }

    @EJFieldName("ADDRESS")
    public void setAddress(String address)
    {
        _address = EJPojoProperty.setPropertyValue(_address, address);
    }

    @EJFieldName("ADDRESS")
    public String getInitialAddress()
    {
        return EJPojoProperty.getPropertyInitialValue(_address);
    }

    @EJFieldName("PASSWORD")
    public String getPassword()
    {
        return EJPojoProperty.getPropertyValue(_password);
    }

    @EJFieldName("PASSWORD")
    public void setPassword(String password)
    {
        _password = EJPojoProperty.setPropertyValue(_password, password);
    }

    @EJFieldName("PASSWORD")
    public String getInitialPassword()
    {
        return EJPojoProperty.getPropertyInitialValue(_password);
    }

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_type);
        EJPojoProperty.clearInitialValue(_url);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_address);
        EJPojoProperty.clearInitialValue(_password);
    }

}