package org.entirej.ejinvoice.forms.invoice;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale.Builder;

import org.entirej.ejinvoice.forms.timeentry.Customer;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class InvoiceBlockService implements EJBlockService<Invoice>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT VAT_AMOUNT, AMOUNT_EXCL_VAT,AMOUNT_INCL_VAT, COMPANY_ID,CUST_ID,DUE_DATE,ID,INV_DATE,NR,PAID,SENT,VAT_RATE, INVOICE_ADDRESS, LOCALE_COUNTRY, LOCALE_LANGUAGE FROM invoice";

    public InvoiceBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<Invoice> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        List<Invoice> invoices = _statementExecutor.executeQuery(Invoice.class, form, _selectStatement, queryCriteria);
        for (Invoice invoice : invoices)
        {
            invoice.setLocale(new Builder().setLanguage(invoice.getLocaleLanguage()).setRegion(invoice.getLocaleCountry()).build());
            invoice.setCcyCode(Currency.getInstance(invoice.getLocale()).getCurrencyCode());
        }
        
        return invoices;
    }

    @Override
    public void executeInsert(EJForm form, List<Invoice> newRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;
        for (Invoice record : newRecords)
        {
            // Initialise the value list
            parameters.clear();
            parameters.add(new EJStatementParameter("VAT_AMOUNT", BigDecimal.class, record.getVatAmount()));
            parameters.add(new EJStatementParameter("AMOUNT_EXCL_VAT", BigDecimal.class, record.getAmountExclVat()));
            parameters.add(new EJStatementParameter("AMOUNT_INCL_VAT", BigDecimal.class, record.getAmountInclVat()));
            parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, record.getCompanyId()));
            parameters.add(new EJStatementParameter("CUST_ID", Integer.class, record.getCustId()));
            parameters.add(new EJStatementParameter("DUE_DATE", Timestamp.class, record.getDueDate()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("INV_DATE", Date.class, record.getInvDate()));
            parameters.add(new EJStatementParameter("NR", String.class, record.getNr()));
            parameters.add(new EJStatementParameter("PAID", Integer.class, record.getPaid()));
            parameters.add(new EJStatementParameter("SENT", Integer.class, record.getSent()));
            parameters.add(new EJStatementParameter("INVOICE_ADDRESS", String.class, record.getInvoiceAddress()));
            parameters.add(new EJStatementParameter("VAT_RATE", BigDecimal.class, record.getVatRate()));
            parameters.add(new EJStatementParameter("LOCALE_COUNTRY", String.class, record.getLocaleCountry()));
            parameters.add(new EJStatementParameter("LOCALE_LANGUAGE", String.class, record.getLocaleLanguage()));
            
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "invoice", parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != newRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: " + newRecords.size() + ". Inserted: " + recordsProcessed);
        }
    }

    @Override
    public void executeUpdate(EJForm form, List<Invoice> updateRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (Invoice record : updateRecords)
        {
            parameters.clear();

            // First add the new values
            parameters.add(new EJStatementParameter("VAT_AMOUNT", BigDecimal.class, record.getVatAmount()));
            parameters.add(new EJStatementParameter("AMOUNT_EXCL_VAT", BigDecimal.class, record.getAmountExclVat()));
            parameters.add(new EJStatementParameter("AMOUNT_INCL_VAT", BigDecimal.class, record.getAmountInclVat()));
            parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, record.getCompanyId()));
            parameters.add(new EJStatementParameter("CUST_ID", Integer.class, record.getCustId()));
            parameters.add(new EJStatementParameter("DUE_DATE", Timestamp.class, record.getDueDate()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("INV_DATE", Date.class, record.getInvDate()));
            parameters.add(new EJStatementParameter("NR", String.class, record.getNr()));
            parameters.add(new EJStatementParameter("PAID", Integer.class, record.getPaid()));
            parameters.add(new EJStatementParameter("SENT", Integer.class, record.getSent()));
            parameters.add(new EJStatementParameter("VAT_RATE", BigDecimal.class, record.getVatRate()));
            parameters.add(new EJStatementParameter("INVOICE_ADDRESS", String.class, record.getInvoiceAddress()));
            parameters.add(new EJStatementParameter("LOCALE_COUNTRY", String.class, record.getLocaleCountry()));
            parameters.add(new EJStatementParameter("LOCALE_LANGUAGE", String.class, record.getLocaleLanguage()));


            EJStatementCriteria criteria = new EJStatementCriteria();
            if (record.getInitialVatAmount() == null)
            {
                criteria.add(EJRestrictions.isNull("VAT_AMOUNT"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("VAT_AMOUNT", record.getInitialVatAmount()));
            }
            if (record.getInitialAmountExclVat() == null)
            {
                criteria.add(EJRestrictions.isNull("AMOUNT_EXCL_VAT"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("AMOUNT_EXCL_VAT", record.getInitialAmountExclVat()));
            }
            if (record.getInitialAmountInclVat() == null)
            {
                criteria.add(EJRestrictions.isNull("AMOUNT_INCL_VAT"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("AMOUNT_INCL_VAT", record.getInitialAmountInclVat()));
            }
            if (record.getInitialCcyCode() == null)
            {
                criteria.add(EJRestrictions.isNull("CCY_CODE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CCY_CODE", record.getInitialCcyCode()));
            }
            if (record.getInitialCompanyId() == null)
            {
                criteria.add(EJRestrictions.isNull("COMPANY_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("COMPANY_ID", record.getInitialCompanyId()));
            }
            if (record.getInitialCustId() == null)
            {
                criteria.add(EJRestrictions.isNull("CUST_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CUST_ID", record.getInitialCustId()));
            }
            if (record.getInitialDueDate() == null)
            {
                criteria.add(EJRestrictions.isNull("DUE_DATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("DUE_DATE", record.getInitialDueDate()));
            }
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialInvDate() == null)
            {
                criteria.add(EJRestrictions.isNull("INV_DATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("INV_DATE", record.getInitialInvDate()));
            }
            if (record.getInitialNr() == null)
            {
                criteria.add(EJRestrictions.isNull("NR"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NR", record.getInitialNr()));
            }
            if (record.getInitialPaid() == null)
            {
                criteria.add(EJRestrictions.isNull("PAID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PAID", record.getInitialPaid()));
            }
            if (record.getInitialSent() == null)
            {
                criteria.add(EJRestrictions.isNull("SENT"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("SENT", record.getInitialSent()));
            }
            if (record.getInitialVatRate() == null)
            {
                criteria.add(EJRestrictions.isNull("VAT_RATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("VAT_RATE", record.getInitialVatRate()));
            }
            if (record.getInitialInvoiceAddress() == null)
            {
                criteria.add(EJRestrictions.isNull("INVOICE_ADDRESS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("INVOICE_ADDRESS", record.getInitialInvoiceAddress()));
            }
            if (record.getInitialLocaleCountry() == null)
            {
                criteria.add(EJRestrictions.isNull("LOCALE_COUNTRY"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("LOCALE_COUNTRY", record.getInitialLocaleCountry()));
            }
            if (record.getInitialLocaleLanguage() == null)
            {
                criteria.add(EJRestrictions.isNull("LOCALE_LANGUAGE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("LOCALE_LANGUAGE", record.getInitialLocaleLanguage()));
            }
            
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeUpdate(form, "invoice", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != updateRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in update. Expected: " + updateRecords.size() + ". Updated: " + recordsProcessed);
        }
    }

    @Override
    public void executeDelete(EJForm form, List<Invoice> recordsToDelete)
    {
        ArrayList<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (Invoice record : recordsToDelete)
        {
            parameters.clear();

            EJStatementCriteria criteria = new EJStatementCriteria();
            if (record.getInitialVatAmount() == null)
            {
                criteria.add(EJRestrictions.isNull("VAT_AMOUNT"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("VAT_AMOUNT", record.getInitialVatAmount()));
            }
            if (record.getInitialAmountExclVat() == null)
            {
                criteria.add(EJRestrictions.isNull("AMOUNT_EXCL_VAT"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("AMOUNT_EXCL_VAT", record.getInitialAmountExclVat()));
            }
            if (record.getInitialAmountInclVat() == null)
            {
                criteria.add(EJRestrictions.isNull("AMOUNT_INCL_VAT"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("AMOUNT_INCL_VAT", record.getInitialAmountInclVat()));
            }
            if (record.getInitialCcyCode() == null)
            {
                criteria.add(EJRestrictions.isNull("CCY_CODE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CCY_CODE", record.getInitialCcyCode()));
            }
            if (record.getInitialCompanyId() == null)
            {
                criteria.add(EJRestrictions.isNull("COMPANY_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("COMPANY_ID", record.getInitialCompanyId()));
            }
            if (record.getInitialCustId() == null)
            {
                criteria.add(EJRestrictions.isNull("CUST_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CUST_ID", record.getInitialCustId()));
            }
            if (record.getInitialDueDate() == null)
            {
                criteria.add(EJRestrictions.isNull("DUE_DATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("DUE_DATE", record.getInitialDueDate()));
            }
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialInvDate() == null)
            {
                criteria.add(EJRestrictions.isNull("INV_DATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("INV_DATE", record.getInitialInvDate()));
            }
            if (record.getInitialNr() == null)
            {
                criteria.add(EJRestrictions.isNull("NR"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NR", record.getInitialNr()));
            }
            if (record.getInitialPaid() == null)
            {
                criteria.add(EJRestrictions.isNull("PAID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PAID", record.getInitialPaid()));
            }
            if (record.getInitialSent() == null)
            {
                criteria.add(EJRestrictions.isNull("SENT"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("SENT", record.getInitialSent()));
            }
            if (record.getInitialVatRate() == null)
            {
                criteria.add(EJRestrictions.isNull("VAT_RATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("VAT_RATE", record.getInitialVatRate()));
            }
            if (record.getInitialInvoiceAddress() == null)
            {
                criteria.add(EJRestrictions.isNull("INVOICE_ADDRESS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("INVOICE_ADDRESS", record.getInitialInvoiceAddress()));
            }
            if (record.getInitialLocaleCountry() == null)
            {
                criteria.add(EJRestrictions.isNull("LOCALE_COUNTRY"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("LOCALE_COUNTRY", record.getInitialLocaleCountry()));
            }
            if (record.getInitialLocaleLanguage() == null)
            {
                criteria.add(EJRestrictions.isNull("LOCALE_LANGUAGE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("LOCALE_LANGUAGE", record.getInitialLocaleLanguage()));
            }
            
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeDelete(form, "invoice", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != recordsToDelete.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in delete. Expected: " + recordsToDelete.size() + ". Deleted: " + recordsProcessed);
        }
    }

}