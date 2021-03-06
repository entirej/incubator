package org.entirej.ejinvoice.forms.blockservices.pojos;

import java.math.BigDecimal;
import java.sql.Date;
import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class Invoice
{
    private EJPojoProperty<String>     _nr;
    private EJPojoProperty<BigDecimal> _amountExclVat;
    private EJPojoProperty<BigDecimal> _amountInclVat;
    private EJPojoProperty<Integer>    _id;
    private EJPojoProperty<Date>       _invDate;
    private EJPojoProperty<Integer>    _userId;
    private EJPojoProperty<Integer>    _confId;
    private EJPojoProperty<Integer>    _payinfId;
    private EJPojoProperty<Integer>    _printed;
    private EJPojoProperty<Integer>    _invoiceId;
    private EJPojoProperty<Integer>    _custId;
    private EJPojoProperty<Integer>    _paid;

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

    @EJFieldName("CONF_ID")
    public Integer getConfId()
    {
        return EJPojoProperty.getPropertyValue(_confId);
    }

    @EJFieldName("CONF_ID")
    public void setConfId(Integer confId)
    {
        _confId = EJPojoProperty.setPropertyValue(_confId, confId);
    }

    @EJFieldName("CONF_ID")
    public Integer getInitialConfId()
    {
        return EJPojoProperty.getPropertyInitialValue(_confId);
    }

    @EJFieldName("PAYINF_ID")
    public Integer getPayinfId()
    {
        return EJPojoProperty.getPropertyValue(_payinfId);
    }

    @EJFieldName("PAYINF_ID")
    public void setPayinfId(Integer payinfId)
    {
        _payinfId = EJPojoProperty.setPropertyValue(_payinfId, payinfId);
    }

    @EJFieldName("PAYINF_ID")
    public Integer getInitialPayinfId()
    {
        return EJPojoProperty.getPropertyInitialValue(_payinfId);
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

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_nr);
        EJPojoProperty.clearInitialValue(_amountExclVat);
        EJPojoProperty.clearInitialValue(_amountInclVat);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_invDate);
        EJPojoProperty.clearInitialValue(_userId);
        EJPojoProperty.clearInitialValue(_confId);
        EJPojoProperty.clearInitialValue(_payinfId);
        EJPojoProperty.clearInitialValue(_printed);
        EJPojoProperty.clearInitialValue(_invoiceId);
        EJPojoProperty.clearInitialValue(_custId);
        EJPojoProperty.clearInitialValue(_paid);
    }

}