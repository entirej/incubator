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
package org.entirej.ejinvoice.forms.masterdata;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class Salutation
{
    private EJPojoProperty<String>  _value;
    private EJPojoProperty<Integer> _id;
    private EJPojoProperty<Integer> _companyId;
    private EJPojoProperty<String>  _displayText;

    @EJFieldName("VALUE")
    public String getValue()
    {
        return EJPojoProperty.getPropertyValue(_value);
    }

    @EJFieldName("VALUE")
    public void setValue(String value)
    {
        _value = EJPojoProperty.setPropertyValue(_value, value);
    }

    @EJFieldName("VALUE")
    public String getInitialValue()
    {
        return EJPojoProperty.getPropertyInitialValue(_value);
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

    @EJFieldName("DISPLAY_TEXT")
    public String getDisplayText()
    {
        StringBuilder display = new StringBuilder();
        display.append("<table border=0 cellpadding=0 cellspacing=0 width=100%");
        display.append("<tr>");

        display.append("<td align=left width=100% colspan=3>");
        display.append("<span style =\"font-weight: bold; font-size: 110% \">" + getValue() + "</span>");
        display.append("</td>");
        display.append("</tr>");

        display.append("</table>");

        return display.toString();
    }

    @EJFieldName("DISPLAY_TEXT")
    public void setDisplayText(String displayText)
    {
        _displayText = EJPojoProperty.setPropertyValue(_displayText, displayText);
    }

    @EJFieldName("DISPLAY_TEXT")
    public String getInitialDisplayText()
    {
        return EJPojoProperty.getPropertyInitialValue(_displayText);
    }

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_value);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_companyId);
        EJPojoProperty.clearInitialValue(_displayText);
    }

}
