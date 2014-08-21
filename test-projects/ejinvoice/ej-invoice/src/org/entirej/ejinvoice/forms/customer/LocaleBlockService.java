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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;

public class LocaleBlockService implements EJBlockService<CustomerLocale>
{
    public LocaleBlockService()
    {
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<CustomerLocale> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        ArrayList<CustomerLocale> custLocales = new ArrayList<CustomerLocale>();
        
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales)
        {
            if (locale.getDisplayCountry() != null && locale.getDisplayCountry().trim().length() > 0)
            {
                CustomerLocale custLocale = new CustomerLocale();
                custLocale.setCountry(locale.getCountry());
                custLocale.setLanguage(locale.getLanguage());
                custLocale.setCountryDesc(locale.getDisplayCountry(Locale.ENGLISH));
                custLocale.setLanguageDesc(locale.getDisplayLanguage(Locale.ENGLISH));
                custLocale.setLocale(locale);

                custLocales.add(custLocale);
            }
        }
        
        Collections.sort(custLocales);
        

        return custLocales;
    }

    @Override
    public void executeInsert(EJForm form, List<CustomerLocale> newRecords)
    {
    }

    @Override
    public void executeUpdate(EJForm form, List<CustomerLocale> updateRecords)
    {
    }

    @Override
    public void executeDelete(EJForm form, List<CustomerLocale> recordsToDelete)
    {
    }

}
