package org.entirej.ejinvoice.forms.customer;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class CustomerContact
{
    private EJPojoProperty<Integer> _companyId;
    private EJPojoProperty<String>  _email;
    private EJPojoProperty<String>  _phone;
    private EJPojoProperty<Integer> _contactTypesId;
    private EJPojoProperty<String>  _contactType;
    private EJPojoProperty<Integer> _customerId;
    private EJPojoProperty<String>  _customerName;
    private EJPojoProperty<Integer> _id;
    private EJPojoProperty<String>  _firstName;
    private EJPojoProperty<Integer> _salutationsId;
    private EJPojoProperty<String>  _salutation;
    private EJPojoProperty<String>  _lastName;
    private EJPojoProperty<String>  _mobile;
    private EJPojoProperty<String>  _displayText;

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

    @EJFieldName("CONTACT_TYPE")
    public String getContactType()
    {
        return EJPojoProperty.getPropertyValue(_contactType);
    }

    @EJFieldName("CONTACT_TYPE")
    public void setContactTypesId(String contactType)
    {
        _contactType = EJPojoProperty.setPropertyValue(_contactType, contactType);
    }

    @EJFieldName("CONTACT_TYPE")
    public String getInitialContactType()
    {
        return EJPojoProperty.getPropertyInitialValue(_contactType);
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

    @EJFieldName("CUSTOMER_NAME")
    public String getCustomerName()
    {
        return EJPojoProperty.getPropertyValue(_customerName);
    }

    @EJFieldName("CUSTOMER_NAME")
    public void setCustomerId(String customerName)
    {
        _customerName = EJPojoProperty.setPropertyValue(_customerName, customerName);
    }

    @EJFieldName("CUSTOMER_NAME")
    public String getInitialCustomerName()
    {
        return EJPojoProperty.getPropertyInitialValue(_customerName);
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

    @EJFieldName("SALUTATION")
    public String getSalutation()
    {
        return EJPojoProperty.getPropertyValue(_salutation);
    }

    @EJFieldName("SALUTATION")
    public void setSalutation(String salutation)
    {
        _salutation = EJPojoProperty.setPropertyValue(_salutation, salutation);
    }

    @EJFieldName("SALUTATION")
    public String getInitialSalutation()
    {
        return EJPojoProperty.getPropertyInitialValue(_salutation);
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

    @EJFieldName("DISPLAY_TEXT")
    public String getInitialDisplayText()
    {
        return EJPojoProperty.getPropertyInitialValue(_displayText);
    }

    @EJFieldName("DISPLAY_TEXT")
    public void setDisplayText(String displayText)
    {
        _displayText = EJPojoProperty.setPropertyValue(_displayText, displayText);
    }

    @EJFieldName("DISPLAY_TEXT")
    public String getDisplayText()
    {
        StringBuilder display = new StringBuilder();
        display.append("<table border=0 cellpadding=0 cellspacing=0 width=100%");
        display.append("<tr>");

        display.append("<td align=left width=30% colspan=1>");
        display.append("<span style=\"font-weight: bold; font-size: 110% \">" + getSalutation() + " " + getFirstName() + " " + getLastName() + "</span>");
        display.append("</td>");

        display.append("<td align=left width=70% colspan=3><span style=\"font-weight: bold; font-size: 110% \">" + getCustomerName() + "</span></td>");

        display.append("</tr>");

        display.append("<tr>");
        display.append("<td align=\"left\"  width=\"30%\">" + getContactType()+"</td>");
        
        display.append("<td align=\"left\"  width=\"20%\">Mobile: " + (getMobile() == null ? "&nbsp;" : getMobile()) + "</td>");
        display.append("<td align=\"left\"  width=\"20%\">Phone: " + (getPhone() == null ? "&nbsp;" : getPhone())+ "</td>");
        display.append("<td align=\"left\"  width=\"30%\">Email: " + (getEmail() == null ? "&nbsp;" : "<a href=\"mailto:"+getEmail()+"\">"+getEmail()+"</a>")+"</td>");

        display.append("</tr>");

        display.append("</table>");

        return display.toString();
    }

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_companyId);
        EJPojoProperty.clearInitialValue(_email);
        EJPojoProperty.clearInitialValue(_phone);
        EJPojoProperty.clearInitialValue(_contactTypesId);
        EJPojoProperty.clearInitialValue(_contactType);
        EJPojoProperty.clearInitialValue(_customerId);
        EJPojoProperty.clearInitialValue(_customerName);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_firstName);
        EJPojoProperty.clearInitialValue(_salutationsId);
        EJPojoProperty.clearInitialValue(_salutation);
        EJPojoProperty.clearInitialValue(_lastName);
        EJPojoProperty.clearInitialValue(_mobile);
        EJPojoProperty.clearInitialValue(_displayText);
    }

}