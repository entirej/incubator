package org.entirej.ejinvoice.forms.company;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class Company
{
    private EJPojoProperty<String>  _postCode;
    private EJPojoProperty<String>  _address;
    private EJPojoProperty<String>  _bankAddress;
    private EJPojoProperty<Object>  _logo;
    private EJPojoProperty<String>  _accountNumber;
    private EJPojoProperty<String>  _bankPostCode;
    private EJPojoProperty<String>  _iban;
    private EJPojoProperty<Integer> _id;
    private EJPojoProperty<Integer> _userId;
    private EJPojoProperty<String>  _bankTown;
    private EJPojoProperty<String>  _bankCountry;
    private EJPojoProperty<String>  _name;
    private EJPojoProperty<String>  _town;
    private EJPojoProperty<String>  _country;
    private EJPojoProperty<String>  _bankName;
    private EJPojoProperty<String>  _vatNr;

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

    @EJFieldName("ACCOUNT_NUMBER")
    public String getAccountNumber()
    {
        return EJPojoProperty.getPropertyValue(_accountNumber);
    }

    @EJFieldName("ACCOUNT_NUMBER")
    public void setAccountNumber(String accountNumber)
    {
        _accountNumber = EJPojoProperty.setPropertyValue(_accountNumber, accountNumber);
    }

    @EJFieldName("ACCOUNT_NUMBER")
    public String getInitialAccountNumber()
    {
        return EJPojoProperty.getPropertyInitialValue(_accountNumber);
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

    
    @EJFieldName("BANK_COUNTRY")
    public String getBankCountry()
    {
        return EJPojoProperty.getPropertyValue(_bankCountry);
    }

    @EJFieldName("BANK_COUNTRY")
    public void setBankCountry(String bankCountry)
    {
        _bankCountry = EJPojoProperty.setPropertyValue(_bankCountry, bankCountry);
    }

    @EJFieldName("BANK_COUNTRY")
    public String getInitialBankCountry()
    {
        return EJPojoProperty.getPropertyInitialValue(_bankCountry);
    }
    
    
    
    @EJFieldName("VAT_NR")
    public String getVatNr()
    {
        return EJPojoProperty.getPropertyValue(_vatNr);
    }

    @EJFieldName("VAT_NR")
    public void setVatNr(String vatNr)
    {
        _vatNr = EJPojoProperty.setPropertyValue(_vatNr, vatNr);
    }

    @EJFieldName("VAT_NR")
    public String getInitialVatNr()
    {
        return EJPojoProperty.getPropertyInitialValue(_vatNr);
    }

    @EJFieldName("LOGO")
    public Object getLogo()
    {
        return EJPojoProperty.getPropertyValue(_logo);
    }

    @EJFieldName("LOGO")
    public void setLogo(Object logo)
    {
        _logo = EJPojoProperty.setPropertyValue(_logo, logo);
    }

    @EJFieldName("LOGO")
    public Object getInitialLogo()
    {
        return EJPojoProperty.getPropertyInitialValue(_logo);
    }
    
    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_postCode);
        EJPojoProperty.clearInitialValue(_address);
        EJPojoProperty.clearInitialValue(_accountNumber);
        EJPojoProperty.clearInitialValue(_bankPostCode);
        EJPojoProperty.clearInitialValue(_iban);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_userId);
        EJPojoProperty.clearInitialValue(_bankTown);
        EJPojoProperty.clearInitialValue(_name);
        EJPojoProperty.clearInitialValue(_town);
        EJPojoProperty.clearInitialValue(_country);
        EJPojoProperty.clearInitialValue(_bankName);
        EJPojoProperty.clearInitialValue(_bankCountry);
        EJPojoProperty.clearInitialValue(_vatNr);
        EJPojoProperty.clearInitialValue(_bankAddress);
        EJPojoProperty.clearInitialValue(_logo);
    }

}