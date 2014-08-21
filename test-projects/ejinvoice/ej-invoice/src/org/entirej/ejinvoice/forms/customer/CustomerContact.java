package org.entirej.ejinvoice.forms.customer;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class CustomerContact
{
    private EJPojoProperty<Integer> _companyId;
    private EJPojoProperty<String>  _email;
    private EJPojoProperty<String>  _phone;
    private EJPojoProperty<Integer> _contactTypesId;
    private EJPojoProperty<Integer> _customerId;
    private EJPojoProperty<Integer> _id;
    private EJPojoProperty<String>  _firstName;
    private EJPojoProperty<Integer> _salutationsId;
    private EJPojoProperty<String>  _lastName;
    private EJPojoProperty<String>  _mobile;

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

    @EJFieldName("PHONE")
    public String getPhone()
    {
        return EJPojoProperty.getPropertyValue(_phone);
    }

    @EJFieldName("PHONE")
    public void setPhone(String phone)
    {
        _phone = EJPojoProperty.setPropertyValue(_phone, phone);
    }

    @EJFieldName("PHONE")
    public String getInitialPhone()
    {
        return EJPojoProperty.getPropertyInitialValue(_phone);
    }

    @EJFieldName("CONTACT_TYPES_ID")
    public Integer getContactTypesId()
    {
        return EJPojoProperty.getPropertyValue(_contactTypesId);
    }

    @EJFieldName("CONTACT_TYPES_ID")
    public void setContactTypesId(Integer contactTypesId)
    {
        _contactTypesId = EJPojoProperty.setPropertyValue(_contactTypesId, contactTypesId);
    }

    @EJFieldName("CONTACT_TYPES_ID")
    public Integer getInitialContactTypesId()
    {
        return EJPojoProperty.getPropertyInitialValue(_contactTypesId);
    }

    @EJFieldName("CUSTOMER_ID")
    public Integer getCustomerId()
    {
        return EJPojoProperty.getPropertyValue(_customerId);
    }

    @EJFieldName("CUSTOMER_ID")
    public void setCustomerId(Integer customerId)
    {
        _customerId = EJPojoProperty.setPropertyValue(_customerId, customerId);
    }

    @EJFieldName("CUSTOMER_ID")
    public Integer getInitialCustomerId()
    {
        return EJPojoProperty.getPropertyInitialValue(_customerId);
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

    @EJFieldName("SALUTATIONS_ID")
    public Integer getSalutationsId()
    {
        return EJPojoProperty.getPropertyValue(_salutationsId);
    }

    @EJFieldName("SALUTATIONS_ID")
    public void setSalutationsId(Integer salutationsId)
    {
        _salutationsId = EJPojoProperty.setPropertyValue(_salutationsId, salutationsId);
    }

    @EJFieldName("SALUTATIONS_ID")
    public Integer getInitialSalutationsId()
    {
        return EJPojoProperty.getPropertyInitialValue(_salutationsId);
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

    @EJFieldName("MOBILE")
    public String getMobile()
    {
        return EJPojoProperty.getPropertyValue(_mobile);
    }

    @EJFieldName("MOBILE")
    public void setMobile(String mobile)
    {
        _mobile = EJPojoProperty.setPropertyValue(_mobile, mobile);
    }

    @EJFieldName("MOBILE")
    public String getInitialMobile()
    {
        return EJPojoProperty.getPropertyInitialValue(_mobile);
    }

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_companyId);
        EJPojoProperty.clearInitialValue(_email);
        EJPojoProperty.clearInitialValue(_phone);
        EJPojoProperty.clearInitialValue(_contactTypesId);
        EJPojoProperty.clearInitialValue(_customerId);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_firstName);
        EJPojoProperty.clearInitialValue(_salutationsId);
        EJPojoProperty.clearInitialValue(_lastName);
        EJPojoProperty.clearInitialValue(_mobile);
    }

}