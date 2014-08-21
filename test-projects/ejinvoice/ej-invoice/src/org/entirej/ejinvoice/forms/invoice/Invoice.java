package org.entirej.ejinvoice.forms.invoice;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class Invoice
{
    private EJPojoProperty<String>     _ccyCode;
    private EJPojoProperty<Integer>    _paid;
    private EJPojoProperty<Integer>    _printed;
    private EJPojoProperty<Timestamp>  _dueDate;
    private EJPojoProperty<Integer>    _companyId;
    private EJPojoProperty<BigDecimal> _vatRate;
    private EJPojoProperty<BigDecimal> _amountInclVat;
    private EJPojoProperty<String>     _nr;
    private EJPojoProperty<Integer>    _custId;
    private EJPojoProperty<Integer>    _id;
    private EJPojoProperty<Integer>    _invoiceId;
    private EJPojoProperty<BigDecimal> _amountExclVat;
    private EJPojoProperty<Date>       _invDate;

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

    @EJFieldName("PRINTED")
    public Integer getPrinted()
    {
        return EJPojoProperty.getPropertyValue(_printed);
    }

    @EJFieldName("PRINTED")
    public void setPrinted(Integer printed)
    {
        _printed = EJPojoProperty.setPropertyValue(_printed, printed);
    }

    @EJFieldName("PRINTED")
    public Integer getInitialPrinted()
    {
        return EJPojoProperty.getPropertyInitialValue(_printed);
    }

    @EJFieldName("DUE_DATE")
    public Timestamp getDueDate()
    {
        return EJPojoProperty.getPropertyValue(_dueDate);
    }

    @EJFieldName("DUE_DATE")
    public void setDueDate(Timestamp dueDate)
    {
        _dueDate = EJPojoProperty.setPropertyValue(_dueDate, dueDate);
    }

    @EJFieldName("DUE_DATE")
    public Timestamp getInitialDueDate()
    {
        return EJPojoProperty.getPropertyInitialValue(_dueDate);
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

    @EJFieldName("INVOICE_ID")
    public Integer getInvoiceId()
    {
        return EJPojoProperty.getPropertyValue(_invoiceId);
    }

    @EJFieldName("INVOICE_ID")
    public void setInvoiceId(Integer invoiceId)
    {
        _invoiceId = EJPojoProperty.setPropertyValue(_invoiceId, invoiceId);
    }

    @EJFieldName("INVOICE_ID")
    public Integer getInitialInvoiceId()
    {
        return EJPojoProperty.getPropertyInitialValue(_invoiceId);
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

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_ccyCode);
        EJPojoProperty.clearInitialValue(_paid);
        EJPojoProperty.clearInitialValue(_printed);
        EJPojoProperty.clearInitialValue(_dueDate);
        EJPojoProperty.clearInitialValue(_companyId);
        EJPojoProperty.clearInitialValue(_vatRate);
        EJPojoProperty.clearInitialValue(_amountInclVat);
        EJPojoProperty.clearInitialValue(_nr);
        EJPojoProperty.clearInitialValue(_custId);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_invoiceId);
        EJPojoProperty.clearInitialValue(_amountExclVat);
        EJPojoProperty.clearInitialValue(_invDate);
    }

}