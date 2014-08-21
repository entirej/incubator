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
package org.entirej.ejinvoice.forms.customer;

import java.math.BigDecimal;
import java.util.Locale;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class Customer
{
    private EJPojoProperty<Integer>    _userId;
    private EJPojoProperty<Integer>    _id;
    private EJPojoProperty<String>     _address;
    private EJPojoProperty<String>     _name;
    private EJPojoProperty<String>     _postCode;
    private EJPojoProperty<String>     _town;
    private EJPojoProperty<String>     _country;

    private EJPojoProperty<String>     _customerNumber;
    private EJPojoProperty<Integer>    _ccyId;
    private EJPojoProperty<Integer>    _vatId;
    private EJPojoProperty<String>     _ccyCode;
    private EJPojoProperty<BigDecimal> _vatRate;
    private EJPojoProperty<Integer>    _paymentDays;

    private EJPojoProperty<String>     _localeCountry;
    private EJPojoProperty<String>     _localeLanguage;
    private EJPojoProperty<Locale>     _locale;

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


    @EJFieldName("LOCALE_COUNTRY")
    public String  getLocaleCountry()
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
    
    @EJFieldName("LOCALE_LANGUAGE")
    public String  getLocaleLanguage()
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
    
    @EJFieldName("LOCALE")
    public Locale getLocale()
    {
        return EJPojoProperty.getPropertyValue(_locale);
    }

    @EJFieldName("LOCALE")
    public void setLocale(Locale locale)
    {
        _locale = EJPojoProperty.setPropertyValue(_locale, locale);
    }

    @EJFieldName("LOCALE")
    public Locale getInitialLocale()
    {
        return EJPojoProperty.getPropertyInitialValue(_locale);
    }

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_locale);
        EJPojoProperty.clearInitialValue(_localeCountry);
        EJPojoProperty.clearInitialValue(_localeLanguage);

        EJPojoProperty.clearInitialValue(_customerNumber);
        EJPojoProperty.clearInitialValue(_ccyId);
        EJPojoProperty.clearInitialValue(_vatId);
        EJPojoProperty.clearInitialValue(_ccyCode);
        EJPojoProperty.clearInitialValue(_vatRate);
        EJPojoProperty.clearInitialValue(_paymentDays);
        EJPojoProperty.clearInitialValue(_userId);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_address);
        EJPojoProperty.clearInitialValue(_name);
        EJPojoProperty.clearInitialValue(_postCode);
        EJPojoProperty.clearInitialValue(_town);
    }

}
