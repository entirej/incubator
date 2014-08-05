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
package org.entirej.ejinvoice.referencedlovdefs.services.pojos;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class CompanyInformation
{
    private EJPojoProperty<String>  _name;
    private EJPojoProperty<String>  _bankTown;
    private EJPojoProperty<String>  _iban;
    private EJPojoProperty<String>  _town;
    private EJPojoProperty<String>  _address;
    private EJPojoProperty<String>  _bankAddress;
    private EJPojoProperty<String>  _bankPostCode;
    private EJPojoProperty<Integer> _id;
    private EJPojoProperty<String>  _bankName;
    private EJPojoProperty<String>  _postCode;

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

    @EJFieldName("BANK_TOWN")
    public String getBankTown()
    {
        return EJPojoProperty.getPropertyValue(_bankTown);
    }

    @EJFieldName("BANK_TOWN")
    public void setBankTown(String bankTown)
    {
        _bankTown = EJPojoProperty.setPropertyValue(_bankTown, bankTown);
    }

    @EJFieldName("BANK_TOWN")
    public String getInitialBankTown()
    {
        return EJPojoProperty.getPropertyInitialValue(_bankTown);
    }

    @EJFieldName("IBAN")
    public String getIban()
    {
        return EJPojoProperty.getPropertyValue(_iban);
    }

    @EJFieldName("IBAN")
    public void setIban(String iban)
    {
        _iban = EJPojoProperty.setPropertyValue(_iban, iban);
    }

    @EJFieldName("IBAN")
    public String getInitialIban()
    {
        return EJPojoProperty.getPropertyInitialValue(_iban);
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

    @EJFieldName("BANK_ADDRESS")
    public String getBankAddress()
    {
        return EJPojoProperty.getPropertyValue(_bankAddress);
    }

    @EJFieldName("BANK_ADDRESS")
    public void setBankAddress(String bankAddress)
    {
        _bankAddress = EJPojoProperty.setPropertyValue(_bankAddress, bankAddress);
    }

    @EJFieldName("BANK_ADDRESS")
    public String getInitialBankAddress()
    {
        return EJPojoProperty.getPropertyInitialValue(_bankAddress);
    }

    @EJFieldName("BANK_POST_CODE")
    public String getBankPostCode()
    {
        return EJPojoProperty.getPropertyValue(_bankPostCode);
    }

    @EJFieldName("BANK_POST_CODE")
    public void setBankPostCode(String bankPostCode)
    {
        _bankPostCode = EJPojoProperty.setPropertyValue(_bankPostCode, bankPostCode);
    }

    @EJFieldName("BANK_POST_CODE")
    public String getInitialBankPostCode()
    {
        return EJPojoProperty.getPropertyInitialValue(_bankPostCode);
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

    @EJFieldName("BANK_NAME")
    public String getBankName()
    {
        return EJPojoProperty.getPropertyValue(_bankName);
    }

    @EJFieldName("BANK_NAME")
    public void setBankName(String bankName)
    {
        _bankName = EJPojoProperty.setPropertyValue(_bankName, bankName);
    }

    @EJFieldName("BANK_NAME")
    public String getInitialBankName()
    {
        return EJPojoProperty.getPropertyInitialValue(_bankName);
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

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_name);
        EJPojoProperty.clearInitialValue(_bankTown);
        EJPojoProperty.clearInitialValue(_iban);
        EJPojoProperty.clearInitialValue(_town);
        EJPojoProperty.clearInitialValue(_address);
        EJPojoProperty.clearInitialValue(_bankAddress);
        EJPojoProperty.clearInitialValue(_bankPostCode);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_bankName);
        EJPojoProperty.clearInitialValue(_postCode);
    }

}
