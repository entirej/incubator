/*******************************************************************************
 * Copyright 2013 Mojave Innovations GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Contributors:
 *     Mojave Innovations GmbH - initial API and implementation
 ******************************************************************************/
package org.entirej.ejinvoice.forms.timeentry;

import java.math.BigDecimal;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class TimeEntryCustomer
{
    private EJPojoProperty<String>     _customerNumber;
    private EJPojoProperty<Integer>    _companyId;
    private EJPojoProperty<Integer>    _id;
    private EJPojoProperty<String>     _address;
    private EJPojoProperty<String>     _name;
    private EJPojoProperty<String>     _postCode;
    private EJPojoProperty<String>     _town;
    private EJPojoProperty<String>     _country;
    private EJPojoProperty<Integer>    _ccyId;
    private EJPojoProperty<Integer>    _vatId;
    private EJPojoProperty<String>     _ccyCode;
    private EJPojoProperty<BigDecimal> _vatRate;
    private EJPojoProperty<Integer>    _paymentDays;

    private EJPojoProperty<String>     _email;
    private EJPojoProperty<String>     _phone;
    private EJPojoProperty<Integer>    _contactTypesId;
    private EJPojoProperty<Integer>    _customerId;
    private EJPojoProperty<String>     _firstName;
    private EJPojoProperty<Integer>    _salutationsId;
    private EJPojoProperty<String>     _lastName;
    private EJPojoProperty<String>     _mobile;

    @EJFieldName("CUSTOMER_NUMBER")
    public String getCustomerNumber()
    {
        return EJPojoProperty.getPropertyValue(_customerNumber);
    }

    @EJFieldName("CUSTOMER_NUMBER")
    public void setCustomerNumber(String customerNumber)
    {
        _customerNumber = EJPojoProperty.setPropertyValue(_customerNumber, customerNumber);
    }

    @EJFieldName("CUSTOMER_NUMBER")
    public String getInitialCustomerNumber()
    {
        return EJPojoProperty.getPropertyInitialValue(_customerNumber);
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

    @EJFieldName("COUNTRY")
    public String getCountry()
    {
        return EJPojoProperty.getPropertyValue(_country);
    }

    @EJFieldName("COUNTRY")
    public void setCountry(String country)
    {
        _country = EJPojoProperty.setPropertyValue(_country, country);
    }

    @EJFieldName("COUNTRY")
    public String getInitialCountry()
    {
        return EJPojoProperty.getPropertyInitialValue(_country);
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

    @EJFieldName("CCY_ID")
    public Integer getCcyId()
    {
        return EJPojoProperty.getPropertyValue(_ccyId);
    }

    @EJFieldName("CCY_ID")
    public void setCcyId(Integer ccyId)
    {
        _ccyId = EJPojoProperty.setPropertyValue(_ccyId, ccyId);
    }

    @EJFieldName("CCY_ID")
    public Integer getInitialCcyId()
    {
        return EJPojoProperty.getPropertyInitialValue(_ccyId);
    }

    @EJFieldName("VAT_ID")
    public Integer getVatId()
    {
        return EJPojoProperty.getPropertyValue(_vatId);
    }

    @EJFieldName("VAT_ID")
    public void setVatId(Integer vatId)
    {
        _vatId = EJPojoProperty.setPropertyValue(_vatId, vatId);
    }

    @EJFieldName("VAT_ID")
    public Integer getInitialVatId()
    {
        return EJPojoProperty.getPropertyInitialValue(_vatId);
    }

    @EJFieldName("CCY_CODE")
    public String getCcyCode()
    {
        return EJPojoProperty.getPropertyValue(_ccyCode);
    }

    @EJFieldName("CCY_CODE")
    public void setCcyCode(String ccyCode)
    {
        _ccyCode = EJPojoProperty.setPropertyValue(_ccyCode, ccyCode);
    }

    @EJFieldName("CCY_CODE")
    public String getInitialCcyCode()
    {
        return EJPojoProperty.getPropertyInitialValue(_ccyCode);
    }

    @EJFieldName("VAT_RATE")
    public BigDecimal getVatRate()
    {
        return EJPojoProperty.getPropertyValue(_vatRate);
    }

    @EJFieldName("VAT_RATE")
    public void setVatRate(BigDecimal vatRate)
    {
        _vatRate = EJPojoProperty.setPropertyValue(_vatRate, vatRate);
    }

    @EJFieldName("VAT_RATE")
    public BigDecimal getInitialVatRate()
    {
        return EJPojoProperty.getPropertyInitialValue(_vatRate);
    }

    @EJFieldName("PAYMENT_DAYS")
    public Integer getPaymentDays()
    {
        return EJPojoProperty.getPropertyValue(_paymentDays);
    }

    @EJFieldName("PAYMENT_DAYS")
    public void setPaymentDays(Integer paymentDays)
    {
        _paymentDays = EJPojoProperty.setPropertyValue(_paymentDays, paymentDays);
    }

    @EJFieldName("PAYMENT_DAYS")
    public Integer getInitialPaymentDays()
    {
        return EJPojoProperty.getPropertyInitialValue(_paymentDays);
    }

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_customerNumber);
        EJPojoProperty.clearInitialValue(_companyId);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_address);
        EJPojoProperty.clearInitialValue(_name);
        EJPojoProperty.clearInitialValue(_postCode);
        EJPojoProperty.clearInitialValue(_town);
        EJPojoProperty.clearInitialValue(_ccyId);
        EJPojoProperty.clearInitialValue(_vatId);
        EJPojoProperty.clearInitialValue(_ccyCode);
        EJPojoProperty.clearInitialValue(_vatRate);
        EJPojoProperty.clearInitialValue(_paymentDays);

        EJPojoProperty.clearInitialValue(_email);
        EJPojoProperty.clearInitialValue(_phone);
        EJPojoProperty.clearInitialValue(_contactTypesId);
        EJPojoProperty.clearInitialValue(_customerId);
        EJPojoProperty.clearInitialValue(_firstName);
        EJPojoProperty.clearInitialValue(_salutationsId);
        EJPojoProperty.clearInitialValue(_lastName);
        EJPojoProperty.clearInitialValue(_mobile);

    }

}
