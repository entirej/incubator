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

import java.util.Locale;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class CustomerLocale implements Comparable<CustomerLocale>
{
    private EJPojoProperty<String> _language;
    private EJPojoProperty<String> _languageDesc;
    private EJPojoProperty<String> _country;
    private EJPojoProperty<String> _countryDesc;
    private EJPojoProperty<Locale> _locale;

    @EJFieldName("LANGUAGE")
    public String getLanguage()
    {
        return EJPojoProperty.getPropertyValue(_language);
    }

    @EJFieldName("LANGUAGE")
    public void setLanguage(String language)
    {
        _language = EJPojoProperty.setPropertyValue(_language, language);
    }

    @EJFieldName("LANGUAGE")
    public String getInitialLanguage()
    {
        return EJPojoProperty.getPropertyInitialValue(_language);
    }
    
    @EJFieldName("LANGUAGE_DESC")
    public String getLanguageDesc()
    {
        return EJPojoProperty.getPropertyValue(_languageDesc);
    }

    @EJFieldName("LANGUAGE_DESC")
    public void setLanguageDesc(String languageDesc)
    {
        _languageDesc = EJPojoProperty.setPropertyValue(_languageDesc, languageDesc);
    }

    @EJFieldName("LANGUAGE_DESC")
    public String getInitialLanguageDesc()
    {
        return EJPojoProperty.getPropertyInitialValue(_languageDesc);
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
    
    @EJFieldName("COUNTRY_DESC")
    public String getCountryDesc()
    {
        return EJPojoProperty.getPropertyValue(_countryDesc);
    }

    @EJFieldName("COUNTRY_DESC")
    public void setCountryDesc(String countryDesc)
    {
        _countryDesc = EJPojoProperty.setPropertyValue(_countryDesc, countryDesc);
    }

    @EJFieldName("COUNTRY_DESC")
    public String getInitialCountryDesc()
    {
        return EJPojoProperty.getPropertyInitialValue(_countryDesc);
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
        EJPojoProperty.clearInitialValue(_country);
        EJPojoProperty.clearInitialValue(_language);
        EJPojoProperty.clearInitialValue(_countryDesc);
        EJPojoProperty.clearInitialValue(_languageDesc);
        EJPojoProperty.clearInitialValue(_locale);
    }

    @Override
    public int compareTo(CustomerLocale o)
    {
        return getCountry().compareTo(o.getCountry());
    }

}
