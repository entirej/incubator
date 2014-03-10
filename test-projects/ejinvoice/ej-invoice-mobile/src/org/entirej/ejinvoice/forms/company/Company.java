package org.entirej.ejinvoice.forms.company;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class Company
{
    private EJPojoProperty<String>  _postCode;
    private EJPojoProperty<String>  _addressLine2;
    private EJPojoProperty<String>  _bankAddressLine2;
    private EJPojoProperty<String>  _accountNumber;
    private EJPojoProperty<String>  _addressLine1;
    private EJPojoProperty<String>  _bankAddressLine1;
    private EJPojoProperty<String>  _bankPostCode;
    private EJPojoProperty<String>  _iban;
    private EJPojoProperty<Integer> _id;
    private EJPojoProperty<String>  _addressLine3;
    private EJPojoProperty<Integer> _userId;
    private EJPojoProperty<String>  _bankTown;
    private EJPojoProperty<String>  _bankCountry;
    private EJPojoProperty<String>  _name;
    private EJPojoProperty<String>  _town;
    private EJPojoProperty<String>  _country;
    private EJPojoProperty<String>  _bankName;
    private EJPojoProperty<String>  _vatNr;
    private EJPojoProperty<String>  _bankAddressLine3;

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

    @EJFieldName("BANK_ADDRESS_LINE2")
    public String getBankAddressLine2()
    {
        return EJPojoProperty.getPropertyValue(_bankAddressLine2);
    }

    @EJFieldName("BANK_ADDRESS_LINE2")
    public void setBankAddressLine2(String bankAddressLine2)
    {
        _bankAddressLine2 = EJPojoProperty.setPropertyValue(_bankAddressLine2, bankAddressLine2);
    }

    @EJFieldName("BANK_ADDRESS_LINE2")
    public String getInitialBankAddressLine2()
    {
        return EJPojoProperty.getPropertyInitialValue(_bankAddressLine2);
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

    @EJFieldName("BANK_ADDRESS_LINE1")
    public String getBankAddressLine1()
    {
        return EJPojoProperty.getPropertyValue(_bankAddressLine1);
    }

    @EJFieldName("BANK_ADDRESS_LINE1")
    public void setBankAddressLine1(String bankAddressLine1)
    {
        _bankAddressLine1 = EJPojoProperty.setPropertyValue(_bankAddressLine1, bankAddressLine1);
    }

    @EJFieldName("BANK_ADDRESS_LINE1")
    public String getInitialBankAddressLine1()
    {
        return EJPojoProperty.getPropertyInitialValue(_bankAddressLine1);
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

    @EJFieldName("BANK_ADDRESS_LINE3")
    public String getBankAddressLine3()
    {
        return EJPojoProperty.getPropertyValue(_bankAddressLine3);
    }

    @EJFieldName("BANK_ADDRESS_LINE3")
    public void setBankAddressLine3(String bankAddressLine3)
    {
        _bankAddressLine3 = EJPojoProperty.setPropertyValue(_bankAddressLine3, bankAddressLine3);
    }

    @EJFieldName("BANK_ADDRESS_LINE3")
    public String getInitialBankAddressLine3()
    {
        return EJPojoProperty.getPropertyInitialValue(_bankAddressLine3);
    }

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_postCode);
        EJPojoProperty.clearInitialValue(_addressLine2);
        EJPojoProperty.clearInitialValue(_bankAddressLine2);
        EJPojoProperty.clearInitialValue(_accountNumber);
        EJPojoProperty.clearInitialValue(_addressLine1);
        EJPojoProperty.clearInitialValue(_bankAddressLine1);
        EJPojoProperty.clearInitialValue(_bankPostCode);
        EJPojoProperty.clearInitialValue(_iban);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_addressLine3);
        EJPojoProperty.clearInitialValue(_userId);
        EJPojoProperty.clearInitialValue(_bankTown);
        EJPojoProperty.clearInitialValue(_name);
        EJPojoProperty.clearInitialValue(_town);
        EJPojoProperty.clearInitialValue(_country);
        EJPojoProperty.clearInitialValue(_bankName);
        EJPojoProperty.clearInitialValue(_bankCountry);
        EJPojoProperty.clearInitialValue(_vatNr);
        EJPojoProperty.clearInitialValue(_bankAddressLine3);
    }

}