package org.entirej.ejinvoice.referencedlovdefs.services.pojos;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class User
{
    private EJPojoProperty<String>  _password;
    private EJPojoProperty<String>  _notes;
    private EJPojoProperty<String>  _town;
    private EJPojoProperty<String>  _localeCountry;
    private EJPojoProperty<String>  _lastName;
    private EJPojoProperty<String>  _address;
    private EJPojoProperty<Integer> _active;
    private EJPojoProperty<String>  _postCode;
    private EJPojoProperty<String>  _localeLanguage;
    private EJPojoProperty<Integer> _id;
    private EJPojoProperty<String>  _role;
    private EJPojoProperty<Integer> _companyId;
    private EJPojoProperty<String>  _firstName;
    private EJPojoProperty<String>  _email;

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

    @EJFieldName("TOWN")
    public String getTown()
    {
        return EJPojoProperty.getPropertyValue(_town);
    }

    @EJFieldName("TOWN")
    public void setTown(String town)
    {
        _town = EJPojoProperty.setPropertyValue(_town, town);
    }

    @EJFieldName("TOWN")
    public String getInitialTown()
    {
        return EJPojoProperty.getPropertyInitialValue(_town);
    }

    @EJFieldName("LOCALE_COUNTRY")
    public String getLocaleCountry()
    {
        return EJPojoProperty.getPropertyValue(_localeCountry);
    }

    @EJFieldName("LOCALE_COUNTRY")
    public void setLocaleCountry(String localeCountry)
    {
        _localeCountry = EJPojoProperty.setPropertyValue(_localeCountry, localeCountry);
    }

    @EJFieldName("LOCALE_COUNTRY")
    public String getInitialLocaleCountry()
    {
        return EJPojoProperty.getPropertyInitialValue(_localeCountry);
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

    @EJFieldName("ACTIVE")
    public Integer getActive()
    {
        return EJPojoProperty.getPropertyValue(_active);
    }

    @EJFieldName("ACTIVE")
    public void setActive(Integer active)
    {
        _active = EJPojoProperty.setPropertyValue(_active, active);
    }

    @EJFieldName("ACTIVE")
    public Integer getInitialActive()
    {
        return EJPojoProperty.getPropertyInitialValue(_active);
    }

    @EJFieldName("POST_CODE")
    public String getPostCode()
    {
        return EJPojoProperty.getPropertyValue(_postCode);
    }

    @EJFieldName("POST_CODE")
    public void setPostCode(String postCode)
    {
        _postCode = EJPojoProperty.setPropertyValue(_postCode, postCode);
    }

    @EJFieldName("POST_CODE")
    public String getInitialPostCode()
    {
        return EJPojoProperty.getPropertyInitialValue(_postCode);
    }

    @EJFieldName("LOCALE_LANGUAGE")
    public String getLocaleLanguage()
    {
        return EJPojoProperty.getPropertyValue(_localeLanguage);
    }

    @EJFieldName("LOCALE_LANGUAGE")
    public void setLocaleLanguage(String localeLanguage)
    {
        _localeLanguage = EJPojoProperty.setPropertyValue(_localeLanguage, localeLanguage);
    }

    @EJFieldName("LOCALE_LANGUAGE")
    public String getInitialLocaleLanguage()
    {
        return EJPojoProperty.getPropertyInitialValue(_localeLanguage);
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

    @EJFieldName("ROLE")
    public String getRole()
    {
        return EJPojoProperty.getPropertyValue(_role);
    }

    @EJFieldName("ROLE")
    public void setRole(String role)
    {
        _role = EJPojoProperty.setPropertyValue(_role, role);
    }

    @EJFieldName("ROLE")
    public String getInitialRole()
    {
        return EJPojoProperty.getPropertyInitialValue(_role);
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

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_password);
        EJPojoProperty.clearInitialValue(_notes);
        EJPojoProperty.clearInitialValue(_town);
        EJPojoProperty.clearInitialValue(_localeCountry);
        EJPojoProperty.clearInitialValue(_lastName);
        EJPojoProperty.clearInitialValue(_address);
        EJPojoProperty.clearInitialValue(_active);
        EJPojoProperty.clearInitialValue(_postCode);
        EJPojoProperty.clearInitialValue(_localeLanguage);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_role);
        EJPojoProperty.clearInitialValue(_companyId);
        EJPojoProperty.clearInitialValue(_firstName);
        EJPojoProperty.clearInitialValue(_email);
    }

}