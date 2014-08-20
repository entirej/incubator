package org.entirej.ejinvoice.forms.login;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class User
{
    private EJPojoProperty<String>  _password;
    private EJPojoProperty<String>  _notes;
    private EJPojoProperty<String>  _email;
    private EJPojoProperty<String>  _lastName;
    private EJPojoProperty<String>  _firstName;
    private EJPojoProperty<Integer> _id;
    private EJPojoProperty<Integer> _companyId;

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

    @EJFieldName("EMAIL")
    public String getEmail()
    {
        return EJPojoProperty.getPropertyValue(_email);
    }

    @EJFieldName("EMAIL")
    public void setEmail(String email)
    {
        _email = EJPojoProperty.setPropertyValue(_email, email);
    }

    @EJFieldName("EMAIL")
    public String getInitialEmail()
    {
        return EJPojoProperty.getPropertyInitialValue(_email);
    }

    @EJFieldName("LAST_NAME")
    public String getLastName()
    {
        return EJPojoProperty.getPropertyValue(_lastName);
    }

    @EJFieldName("LAST_NAME")
    public void setLastName(String lastName)
    {
        _lastName = EJPojoProperty.setPropertyValue(_lastName, lastName);
    }

    @EJFieldName("LAST_NAME")
    public String getInitialLastName()
    {
        return EJPojoProperty.getPropertyInitialValue(_lastName);
    }

    @EJFieldName("FIRST_NAME")
    public String getFirstName()
    {
        return EJPojoProperty.getPropertyValue(_firstName);
    }

    @EJFieldName("FIRST_NAME")
    public void setFirstName(String firstName)
    {
        _firstName = EJPojoProperty.setPropertyValue(_firstName, firstName);
    }

    @EJFieldName("FIRST_NAME")
    public String getInitialFirstName()
    {
        return EJPojoProperty.getPropertyInitialValue(_firstName);
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

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_password);
        EJPojoProperty.clearInitialValue(_notes);
        EJPojoProperty.clearInitialValue(_email);
        EJPojoProperty.clearInitialValue(_lastName);
        EJPojoProperty.clearInitialValue(_firstName);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_companyId);
    }

}