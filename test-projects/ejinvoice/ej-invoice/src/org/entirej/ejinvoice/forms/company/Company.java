package org.entirej.ejinvoice.forms.company;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class Company
{
    private EJPojoProperty<String>  _postCode;
    private EJPojoProperty<String>  _address;
    private EJPojoProperty<Object>  _logo;
    private EJPojoProperty<Integer> _id;
    private EJPojoProperty<String>  _name;
    private EJPojoProperty<String>  _town;
    private EJPojoProperty<String>  _country;
    private EJPojoProperty<String>  _vatNr;
    private EJPojoProperty<String>  _invoiceFooter;
    private EJPojoProperty<String>  _invoiceSummary;

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

    @EJFieldName("INVOICE_FOOTER")
    public String getInvoiceFooter()
    {
        return EJPojoProperty.getPropertyValue(_invoiceFooter);
    }

    @EJFieldName("INVOICE_FOOTER")
    public void setInvoiceFooter(String invoiceFooter)
    {
        _invoiceFooter = EJPojoProperty.setPropertyValue(_invoiceFooter, invoiceFooter);
    }

    @EJFieldName("INVOICE_FOOTER")
    public String getInitialInvoiceFooter()
    {
        return EJPojoProperty.getPropertyInitialValue(_invoiceFooter);
    }

    @EJFieldName("INVOICE_SUMMARY")
    public String getInvoiceSummary()
    {
        return EJPojoProperty.getPropertyValue(_invoiceSummary);
    }

    @EJFieldName("INVOICE_SUMMARY")
    public void setInvoiceSummary(String invoiceSummary)
    {
        _invoiceSummary = EJPojoProperty.setPropertyValue(_invoiceSummary, invoiceSummary);
    }

    @EJFieldName("INVOICE_SUMMARY")
    public String getInitialInvoiceSummary()
    {
        return EJPojoProperty.getPropertyInitialValue(_invoiceSummary);
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
        EJPojoProperty.clearInitialValue(_invoiceSummary);
        EJPojoProperty.clearInitialValue(_invoiceFooter);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_name);
        EJPojoProperty.clearInitialValue(_town);
        EJPojoProperty.clearInitialValue(_country);
        EJPojoProperty.clearInitialValue(_vatNr);
        EJPojoProperty.clearInitialValue(_logo);
    }

}