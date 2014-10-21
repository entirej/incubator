package org.entirej.ejinvoice.reports.services;

import java.math.BigDecimal;
import java.sql.Date;
import org.entirej.framework.report.EJReportFieldName;

public class ReportInvoice
{
    private Integer     _id;
    private String     _vatNr;
    private String     _localeLanguage;
    private String     _companyName;
    private String     _nr;
    private String     _custName;
    private String     _invoiceAddress;
    private String     _invoiceSummary;
    private String     _invoiceNotes;
    private String     _localeCountry;
    private Date       _invDate;
    private BigDecimal _vatAmount;
    private Date       _dueDate;
    private String     _companyAddress;
    private BigDecimal _amountInclVat;
    private BigDecimal _vatRate;
    private BigDecimal _amountExclVat;
    private Object     _companyLogo;

    
    
    
    
    
    @EJReportFieldName("INV_ID")
    public void setId(Integer id)
    {
        _id = id;
    }
    @EJReportFieldName("INV_ID")
    public Integer getId()
    {
        return _id;
    }
    
    @EJReportFieldName("VAT_NR")
    public String getVatNr()
    {
        return _vatNr;
    }
    
    @EJReportFieldName("VAT_NR")
    public void setVatNr(String vatNr)
    {
        _vatNr = vatNr;
    }

    @EJReportFieldName("LOCALE_LANGUAGE")
    public String getLocaleLanguage()
    {
        return _localeLanguage;
    }

    @EJReportFieldName("LOCALE_LANGUAGE")
    public void setLocaleLanguage(String localeLanguage)
    {
        _localeLanguage = localeLanguage;
    }

    @EJReportFieldName("COMPANY_NAME")
    public String getCompanyName()
    {
        return _companyName;
    }

    @EJReportFieldName("COMPANY_NAME")
    public void setCompanyName(String companyName)
    {
        _companyName = companyName;
    }

    @EJReportFieldName("NR")
    public String getNr()
    {
        return _nr;
    }

    @EJReportFieldName("NR")
    public void setNr(String nr)
    {
        _nr = nr;
    }

    @EJReportFieldName("CUST_NAME")
    public String getCustName()
    {
        return _custName;
    }

    @EJReportFieldName("CUST_NAME")
    public void setCustName(String custName)
    {
        _custName = custName;
    }

    @EJReportFieldName("INVOICE_ADDRESS")
    public String getInvoiceAddress()
    {
        return _invoiceAddress;
    }

    @EJReportFieldName("INVOICE_ADDRESS")
    public void setInvoiceAddress(String invoiceAddress)
    {
        _invoiceAddress = invoiceAddress;
    }

    @EJReportFieldName("INVOICE_SUMMARY")
    public String getInvoiceSummary()
    {
        return _invoiceSummary;
    }

    @EJReportFieldName("INVOICE_SUMMARY")
    public void setInvoiceSummary(String invoiceSummary)
    {
        _invoiceSummary = invoiceSummary;
    }

    @EJReportFieldName("INVOICE_NOTES")
    public String getInvoiceNotes()
    {
        return _invoiceNotes;
    }

    @EJReportFieldName("INVOICE_NOTES")
    public void setInvoiceNotes(String invoiceNotes)
    {
        _invoiceNotes = invoiceNotes;
    }

    @EJReportFieldName("LOCALE_COUNTRY")
    public String getLocaleCountry()
    {
        return _localeCountry;
    }

    @EJReportFieldName("LOCALE_COUNTRY")
    public void setLocaleCountry(String localeCountry)
    {
        _localeCountry = localeCountry;
    }

    @EJReportFieldName("INV_DATE")
    public Date getInvDate()
    {
        return _invDate;
    }

    @EJReportFieldName("INV_DATE")
    public void setInvDate(Date invDate)
    {
        _invDate = invDate;
    }

    @EJReportFieldName("VAT_AMOUNT")
    public BigDecimal getVatAmount()
    {
        return _vatAmount;
    }

    @EJReportFieldName("VAT_AMOUNT")
    public void setVatAmount(BigDecimal vatAmount)
    {
        _vatAmount = vatAmount;
    }

    @EJReportFieldName("DUE_DATE")
    public Date getDueDate()
    {
        return _dueDate;
    }

    @EJReportFieldName("DUE_DATE")
    public void setDueDate(Date dueDate)
    {
        _dueDate = dueDate;
    }

    @EJReportFieldName("COMPANY_ADDRESS")
    public String getCompanyAddress()
    {
        return _companyAddress;
    }

    @EJReportFieldName("COMPANY_ADDRESS")
    public void setCompanyAddress(String companyAddress)
    {
        _companyAddress = companyAddress;
    }

    @EJReportFieldName("AMOUNT_INCL_VAT")
    public BigDecimal getAmountInclVat()
    {
        return _amountInclVat;
    }

    @EJReportFieldName("AMOUNT_INCL_VAT")
    public void setAmountInclVat(BigDecimal amountInclVat)
    {
        _amountInclVat = amountInclVat;
    }

    @EJReportFieldName("VAT_RATE")
    public BigDecimal getVatRate()
    {
        return _vatRate;
    }

    @EJReportFieldName("VAT_RATE")
    public void setVatRate(BigDecimal vatRate)
    {
        _vatRate = vatRate;
    }

    @EJReportFieldName("AMOUNT_EXCL_VAT")
    public BigDecimal getAmountExclVat()
    {
        return _amountExclVat;
    }

    @EJReportFieldName("AMOUNT_EXCL_VAT")
    public void setAmountExclVat(BigDecimal amountExclVat)
    {
        _amountExclVat = amountExclVat;
    }

    @EJReportFieldName("COMPANY_LOGO")
    public Object getCompanyLogo()
    {
        return _companyLogo;
    }

    @EJReportFieldName("COMPANY_LOGO")
    public void setCompanyLogo(Object companyLogo)
    {
        _companyLogo = companyLogo;
    }

}