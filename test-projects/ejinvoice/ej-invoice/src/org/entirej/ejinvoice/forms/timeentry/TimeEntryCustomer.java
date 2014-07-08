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

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class TimeEntryCustomer
{
    private EJPojoProperty<Integer> _userId;
    private EJPojoProperty<Integer> _id;
    private EJPojoProperty<String>  _addressLine1;
    private EJPojoProperty<String>  _addressLine2;
    private EJPojoProperty<String>  _name;
    private EJPojoProperty<String>  _postCode;
    private EJPojoProperty<String>  _addressLine3;
    private EJPojoProperty<String>  _town;
    private EJPojoProperty<String>  _country;

    private EJPojoProperty<String>  _email;
    private EJPojoProperty<String>  _phone;
    private EJPojoProperty<Integer> _contactTypesId;
    private EJPojoProperty<Integer> _customerId;
    private EJPojoProperty<String>  _firstName;
    private EJPojoProperty<Integer> _salutationsId;
    private EJPojoProperty<String>  _lastName;
    private EJPojoProperty<String>  _mobile;

    
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

    @EJFieldName("ADDRESS_LINE1")
    public String getAddressLine1()
    {
        return EJPojoProperty.getPropertyValue(_addressLine1);
    }

    @EJFieldName("ADDRESS_LINE1")
    public void setAddressLine1(String addressLine1)
    {
        _addressLine1 = EJPojoProperty.setPropertyValue(_addressLine1, addressLine1);
    }

    @EJFieldName("ADDRESS_LINE1")
    public String getInitialAddressLine1()
    {
        return EJPojoProperty.getPropertyInitialValue(_addressLine1);
    }

    @EJFieldName("ADDRESS_LINE2")
    public String getAddressLine2()
    {
        return EJPojoProperty.getPropertyValue(_addressLine2);
    }

    @EJFieldName("ADDRESS_LINE2")
    public void setAddressLine2(String addressLine2)
    {
        _addressLine2 = EJPojoProperty.setPropertyValue(_addressLine2, addressLine2);
    }

    @EJFieldName("ADDRESS_LINE2")
    public String getInitialAddressLine2()
    {
        return EJPojoProperty.getPropertyInitialValue(_addressLine2);
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

    @EJFieldName("ADDRESS_LINE3")
    public String getAddressLine3()
    {
        return EJPojoProperty.getPropertyValue(_addressLine3);
    }

    @EJFieldName("ADDRESS_LINE3")
    public void setAddressLine3(String addressLine3)
    {
        _addressLine3 = EJPojoProperty.setPropertyValue(_addressLine3, addressLine3);
    }

    @EJFieldName("ADDRESS_LINE3")
    public String getInitialAddressLine3()
    {
        return EJPojoProperty.getPropertyInitialValue(_addressLine3);
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
    
    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_userId);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_addressLine1);
        EJPojoProperty.clearInitialValue(_addressLine2);
        EJPojoProperty.clearInitialValue(_name);
        EJPojoProperty.clearInitialValue(_postCode);
        EJPojoProperty.clearInitialValue(_addressLine3);
        EJPojoProperty.clearInitialValue(_town);
        
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
