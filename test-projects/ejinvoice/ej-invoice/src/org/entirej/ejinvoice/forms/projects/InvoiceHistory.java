package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.sql.Date;
import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class InvoiceHistory
{
    private EJPojoProperty<BigDecimal> _vatAmount;
    private EJPojoProperty<Integer>    _paid;
    private EJPojoProperty<Integer>    _sent;
    private EJPojoProperty<Integer>    _id;
    private EJPojoProperty<String>     _localeCountry;
    private EJPojoProperty<String>     _summary;
    private EJPojoProperty<String>     _footer;
    private EJPojoProperty<String>     _notes;
    private EJPojoProperty<BigDecimal> _amountExclVat;
    private EJPojoProperty<Date>       _invDate;
    private EJPojoProperty<BigDecimal> _amountInclVat;
    private EJPojoProperty<Integer>    _custId;
    private EJPojoProperty<String>     _invoiceAddress;
    private EJPojoProperty<String>     _nr;
    private EJPojoProperty<BigDecimal> _vatRate;
    private EJPojoProperty<Integer>    _companyId;
    private EJPojoProperty<String>     _localeLanguage;
    private EJPojoProperty<Date>       _dueDate;
    private EJPojoProperty<Date>       _paymentDate;
    private EJPojoProperty<Date>       _sentDate;
    private EJPojoProperty<String>     _status;

    @EJFieldName("VAT_AMOUNT")
    public BigDecimal getVatAmount()
    {
        return EJPojoProperty.getPropertyValue(_vatAmount);
    }

    @EJFieldName("VAT_AMOUNT")
    public void setVatAmount(BigDecimal vatAmount)
    {
        _vatAmount = EJPojoProperty.setPropertyValue(_vatAmount, vatAmount);
    }

    @EJFieldName("VAT_AMOUNT")
    public BigDecimal getInitialVatAmount()
    {
        return EJPojoProperty.getPropertyInitialValue(_vatAmount);
    }

    @EJFieldName("PAID")
    public Integer getPaid()
    {
        return EJPojoProperty.getPropertyValue(_paid);
    }

    @EJFieldName("PAID")
    public void setPaid(Integer paid)
    {
        _paid = EJPojoProperty.setPropertyValue(_paid, paid);
    }

    @EJFieldName("PAID")
    public Integer getInitialPaid()
    {
        return EJPojoProperty.getPropertyInitialValue(_paid);
    }

    @EJFieldName("SENT")
    public Integer getSent()
    {
        return EJPojoProperty.getPropertyValue(_sent);
    }

    @EJFieldName("SENT")
    public void setSent(Integer sent)
    {
        _sent = EJPojoProperty.setPropertyValue(_sent, sent);
    }

    @EJFieldName("SENT")
    public Integer getInitialSent()
    {
        return EJPojoProperty.getPropertyInitialValue(_sent);
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

    @EJFieldName("LOCALE_COUNTRY")
    public String getLocaleCountry()
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

    @EJFieldName("SUMMARY")
    public String getSummary()
    {
        return EJPojoProperty.getPropertyValue(_summary);
    }

    @EJFieldName("SUMMARY")
    public void setSummary(String summary)
    {
        _summary = EJPojoProperty.setPropertyValue(_summary, summary);
    }

    @EJFieldName("SUMMARY")
    public String getInitialSummary()
    {
        return EJPojoProperty.getPropertyInitialValue(_summary);
    }

    @EJFieldName("FOOTER")
    public String getFooter()
    {
        return EJPojoProperty.getPropertyValue(_footer);
    }

    @EJFieldName("FOOTER")
    public void setFooter(String footer)
    {
        _footer = EJPojoProperty.setPropertyValue(_footer, footer);
    }

    @EJFieldName("FOOTER")
    public String getInitialFooter()
    {
        return EJPojoProperty.getPropertyInitialValue(_footer);
    }

    @EJFieldName("NOTES")
    public String getNotes()
    {
        return EJPojoProperty.getPropertyValue(_notes);
    }

    @EJFieldName("NOTES")
    public void setNotes(String notes)
    {
        _notes = EJPojoProperty.setPropertyValue(_notes, notes);
    }

    @EJFieldName("NOTES")
    public String getInitialNotes()
    {
        return EJPojoProperty.getPropertyInitialValue(_notes);
    }

    @EJFieldName("AMOUNT_EXCL_VAT")
    public BigDecimal getAmountExclVat()
    {
        return EJPojoProperty.getPropertyValue(_amountExclVat);
    }

    @EJFieldName("AMOUNT_EXCL_VAT")
    public void setAmountExclVat(BigDecimal amountExclVat)
    {
        _amountExclVat = EJPojoProperty.setPropertyValue(_amountExclVat, amountExclVat);
    }

    @EJFieldName("AMOUNT_EXCL_VAT")
    public BigDecimal getInitialAmountExclVat()
    {
        return EJPojoProperty.getPropertyInitialValue(_amountExclVat);
    }

    @EJFieldName("INV_DATE")
    public Date getInvDate()
    {
        return EJPojoProperty.getPropertyValue(_invDate);
    }

    @EJFieldName("INV_DATE")
    public void setInvDate(Date invDate)
    {
        _invDate = EJPojoProperty.setPropertyValue(_invDate, invDate);
    }

    @EJFieldName("INV_DATE")
    public Date getInitialInvDate()
    {
        return EJPojoProperty.getPropertyInitialValue(_invDate);
    }

    @EJFieldName("AMOUNT_INCL_VAT")
    public BigDecimal getAmountInclVat()
    {
        return EJPojoProperty.getPropertyValue(_amountInclVat);
    }

    @EJFieldName("AMOUNT_INCL_VAT")
    public void setAmountInclVat(BigDecimal amountInclVat)
    {
        _amountInclVat = EJPojoProperty.setPropertyValue(_amountInclVat, amountInclVat);
    }

    @EJFieldName("AMOUNT_INCL_VAT")
    public BigDecimal getInitialAmountInclVat()
    {
        return EJPojoProperty.getPropertyInitialValue(_amountInclVat);
    }

    @EJFieldName("CUST_ID")
    public Integer getCustId()
    {
        return EJPojoProperty.getPropertyValue(_custId);
    }

    @EJFieldName("CUST_ID")
    public void setCustId(Integer custId)
    {
        _custId = EJPojoProperty.setPropertyValue(_custId, custId);
    }

    @EJFieldName("CUST_ID")
    public Integer getInitialCustId()
    {
        return EJPojoProperty.getPropertyInitialValue(_custId);
    }

    @EJFieldName("INVOICE_ADDRESS")
    public String getInvoiceAddress()
    {
        return EJPojoProperty.getPropertyValue(_invoiceAddress);
    }

    @EJFieldName("INVOICE_ADDRESS")
    public void setInvoiceAddress(String invoiceAddress)
    {
        _invoiceAddress = EJPojoProperty.setPropertyValue(_invoiceAddress, invoiceAddress);
    }

    @EJFieldName("INVOICE_ADDRESS")
    public String getInitialInvoiceAddress()
    {
        return EJPojoProperty.getPropertyInitialValue(_invoiceAddress);
    }

    @EJFieldName("NR")
    public String getNr()
    {
        return EJPojoProperty.getPropertyValue(_nr);
    }

    @EJFieldName("NR")
    public void setNr(String nr)
    {
        _nr = EJPojoProperty.setPropertyValue(_nr, nr);
    }

    @EJFieldName("NR")
    public String getInitialNr()
    {
        return EJPojoProperty.getPropertyInitialValue(_nr);
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

    @EJFieldName("LOCALE_LANGUAGE")
    public String getLocaleLanguage()
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

    @EJFieldName("DUE_DATE")
    public Date getDueDate()
    {
        return EJPojoProperty.getPropertyValue(_dueDate);
    }

    @EJFieldName("DUE_DATE")
    public void setDueDate(Date dueDate)
    {
        _dueDate = EJPojoProperty.setPropertyValue(_dueDate, dueDate);
    }

    @EJFieldName("DUE_DATE")
    public Date getInitialDueDate()
    {
        return EJPojoProperty.getPropertyInitialValue(_dueDate);
    }

    @EJFieldName("SENT_DATE")
    public Date getSentDate()
    {
        return EJPojoProperty.getPropertyValue(_sentDate);
    }

    @EJFieldName("SENT_DATE")
    public void setSentDate(Date sentDate)
    {
        _sentDate = EJPojoProperty.setPropertyValue(_sentDate, sentDate);
    }

    @EJFieldName("SENT_DATE")
    public Date getInitialSentDate()
    {
        return EJPojoProperty.getPropertyInitialValue(_sentDate);
    }

    @EJFieldName("PAYMENT_DATE")
    public Date getPaymentDate()
    {
        return EJPojoProperty.getPropertyValue(_paymentDate);
    }

    @EJFieldName("PAYMENT_DATE")
    public void setPaymentDate(Date paymentDate)
    {
        _paymentDate = EJPojoProperty.setPropertyValue(_paymentDate, paymentDate);
    }

    @EJFieldName("PAYMENT_DATE")
    public Date getInitialPaymentDate()
    {
        return EJPojoProperty.getPropertyInitialValue(_paymentDate);
    }

    @EJFieldName("STATUS")
    public String getStatus()
    {
        return EJPojoProperty.getPropertyValue(_status);
    }

    @EJFieldName("STATUS")
    public void setStatus(String status)
    {
        _status = EJPojoProperty.setPropertyValue(_status, status);
    }

    @EJFieldName("STATUS")
    public String getInitialStatus()
    {
        return EJPojoProperty.getPropertyInitialValue(_status);
    }

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_vatAmount);
        EJPojoProperty.clearInitialValue(_paid);
        EJPojoProperty.clearInitialValue(_sent);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_localeCountry);
        EJPojoProperty.clearInitialValue(_summary);
        EJPojoProperty.clearInitialValue(_footer);
        EJPojoProperty.clearInitialValue(_notes);
        EJPojoProperty.clearInitialValue(_amountExclVat);
        EJPojoProperty.clearInitialValue(_invDate);
        EJPojoProperty.clearInitialValue(_amountInclVat);
        EJPojoProperty.clearInitialValue(_custId);
        EJPojoProperty.clearInitialValue(_invoiceAddress);
        EJPojoProperty.clearInitialValue(_nr);
        EJPojoProperty.clearInitialValue(_vatRate);
        EJPojoProperty.clearInitialValue(_companyId);
        EJPojoProperty.clearInitialValue(_localeLanguage);
        EJPojoProperty.clearInitialValue(_dueDate);
        EJPojoProperty.clearInitialValue(_sentDate);
        EJPojoProperty.clearInitialValue(_paymentDate);
        EJPojoProperty.clearInitialValue(_status);
    }

}