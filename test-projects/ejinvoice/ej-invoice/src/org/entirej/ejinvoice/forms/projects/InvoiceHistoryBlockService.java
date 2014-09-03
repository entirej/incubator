package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.entirej.ejinvoice.forms.projects.InvoiceHistory;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJQuerySort;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class InvoiceHistoryBlockService implements EJBlockService<InvoiceHistory>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT FOOTER, AMOUNT_EXCL_VAT,AMOUNT_INCL_VAT,COMPANY_ID,CUST_ID,DUE_DATE, SENT_DATE, PAYMENT_DATE,ID,INVOICE_ADDRESS,INV_DATE,LOCALE_COUNTRY,LOCALE_LANGUAGE,NOTES,NR,PAID,SENT,SUMMARY,VAT_AMOUNT,VAT_RATE FROM invoice";

    public InvoiceHistoryBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<InvoiceHistory> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        queryCriteria.add(EJQuerySort.DESC("INV_DATE"));
        queryCriteria.add(EJQuerySort.DESC("DUE_DATE"));

        if (queryCriteria.containsRestriction("STATUS"))
        {
            String status = (String) queryCriteria.getRestriction("STATUS").getValue();
            queryCriteria.removeRestriction("STATUS");
            switch (status)
            {
                case "ALL":
                    queryCriteria.add(EJRestrictions.notEquals("PAID", 1));
                    break;
                case "DRAFT":
                    queryCriteria.add(EJRestrictions.equals("PAID", 0));
                    queryCriteria.add(EJRestrictions.equals("SENT", 0));
                    break;
                case "SENT":
                    queryCriteria.add(EJRestrictions.equals("SENT", 1));
                    queryCriteria.add(EJRestrictions.equals("PAID", 0));
                    break;
                case "LATE":
                    queryCriteria.add(EJRestrictions.lessThan("DUE_DATE", new Date(System.currentTimeMillis())));
                    break;
            }
        }
        else
        {
            queryCriteria.add(EJRestrictions.equals("PAID", 1));
        }
        
        if (queryCriteria.containsRestriction("dateFrom") && queryCriteria.containsRestriction("dateTo"))
        {
            queryCriteria.add(EJRestrictions.between("INV_DATE", (Date)queryCriteria.getRestriction("dateFrom").getValue(), (Date)queryCriteria.getRestriction("dateTo").getValue()));
            queryCriteria.removeRestriction("dateFrom");
            queryCriteria.removeRestriction("dateTo");
        }
        else if (queryCriteria.containsRestriction("dateFrom"))
        {
            queryCriteria.add(EJRestrictions.greaterThanEqualTo("INV_DATE", (Date)queryCriteria.getRestriction("dateFrom").getValue()));
            queryCriteria.removeRestriction("dateFrom");
        }
        else if (queryCriteria.containsRestriction("dateTo"))
        {
            queryCriteria.add(EJRestrictions.lessThanEqualTo("INV_DATE", (Date)queryCriteria.getRestriction("dateTo").getValue()));
            queryCriteria.removeRestriction("dateTo");
        }
        
        if (queryCriteria.containsRestriction("amountFrom") && queryCriteria.containsRestriction("amountTo"))
        {
            queryCriteria.add(EJRestrictions.between("AMOUNT_INCL_VAT", (BigDecimal)queryCriteria.getRestriction("amountFrom").getValue(), (BigDecimal)queryCriteria.getRestriction("amountTo").getValue()));
            queryCriteria.removeRestriction("amountFrom");
            queryCriteria.removeRestriction("amountTo");
        }
        else if (queryCriteria.containsRestriction("amountFrom"))
        {
            queryCriteria.add(EJRestrictions.greaterThanEqualTo("AMOUNT_INCL_VAT", (BigDecimal)queryCriteria.getRestriction("amountFrom").getValue()));
            queryCriteria.removeRestriction("amountFrom");
        }
        else if (queryCriteria.containsRestriction("amountTo"))
        {
            queryCriteria.add(EJRestrictions.lessThanEqualTo("AMOUNT_INCL_VAT", (BigDecimal)queryCriteria.getRestriction("amountTo").getValue()));
            queryCriteria.removeRestriction("amountTo");
        }
        
        List<InvoiceHistory> results = _statementExecutor.executeQuery(InvoiceHistory.class, form, _selectStatement, queryCriteria);
        for (InvoiceHistory result : results)
        {
            if (result.getPaid() == 1)
            {
                result.setStatus("PAID");
            }
            else if (result.getDueDate().before(new Date(System.currentTimeMillis())))
            {
                result.setStatus("LATE");
            }
            else if (result.getSent() == 1)
            {
                result.setStatus("SENT");
            }
            else
            {
                result.setStatus("DRAFT");
            }
        }

        return results;
    }

    @Override
    public void executeInsert(EJForm form, List<InvoiceHistory> newRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;
        for (InvoiceHistory record : newRecords)
        {
            // Initialise the value list
            parameters.clear();
            parameters.add(new EJStatementParameter("AMOUNT_EXCL_VAT", BigDecimal.class, record.getAmountExclVat()));
            parameters.add(new EJStatementParameter("AMOUNT_INCL_VAT", BigDecimal.class, record.getAmountInclVat()));
            parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, record.getCompanyId()));
            parameters.add(new EJStatementParameter("CUST_ID", Integer.class, record.getCustId()));
            parameters.add(new EJStatementParameter("DUE_DATE", Date.class, record.getDueDate()));
            parameters.add(new EJStatementParameter("SENT_DATE", Date.class, record.getSentDate()));
            parameters.add(new EJStatementParameter("PAYMENT_DATE", Date.class, record.getPaymentDate()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("INV_DATE", Date.class, record.getInvDate()));
            parameters.add(new EJStatementParameter("INVOICE_ADDRESS", String.class, record.getInvoiceAddress()));
            parameters.add(new EJStatementParameter("LOCALE_COUNTRY", String.class, record.getLocaleCountry()));
            parameters.add(new EJStatementParameter("LOCALE_LANGUAGE", String.class, record.getLocaleLanguage()));
            parameters.add(new EJStatementParameter("NOTES", String.class, record.getNotes()));
            parameters.add(new EJStatementParameter("FOOTER", String.class, record.getFooter()));
            parameters.add(new EJStatementParameter("NR", String.class, record.getNr()));
            parameters.add(new EJStatementParameter("PAID", Integer.class, record.getPaid()));
            parameters.add(new EJStatementParameter("SENT", Integer.class, record.getSent()));
            parameters.add(new EJStatementParameter("SUMMARY", String.class, record.getSummary()));
            parameters.add(new EJStatementParameter("VAT_AMOUNT", BigDecimal.class, record.getVatAmount()));
            parameters.add(new EJStatementParameter("VAT_RATE", BigDecimal.class, record.getVatRate()));

            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "invoice", parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != newRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: " + newRecords.size() + ". Inserted: "
                    + recordsProcessed);
        }
    }

    @Override
    public void executeUpdate(EJForm form, List<InvoiceHistory> updateRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (InvoiceHistory record : updateRecords)
        {
            parameters.clear();

            // First add the new values
            parameters.add(new EJStatementParameter("AMOUNT_EXCL_VAT", BigDecimal.class, record.getAmountExclVat()));
            parameters.add(new EJStatementParameter("AMOUNT_INCL_VAT", BigDecimal.class, record.getAmountInclVat()));
            parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, record.getCompanyId()));
            parameters.add(new EJStatementParameter("CUST_ID", Integer.class, record.getCustId()));
            parameters.add(new EJStatementParameter("DUE_DATE", Date.class, record.getDueDate()));
            parameters.add(new EJStatementParameter("SENT_DATE", Date.class, record.getSentDate()));
            parameters.add(new EJStatementParameter("PAYMENT_DATE", Date.class, record.getPaymentDate()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("INV_DATE", Date.class, record.getInvDate()));
            parameters.add(new EJStatementParameter("INVOICE_ADDRESS", String.class, record.getInvoiceAddress()));
            parameters.add(new EJStatementParameter("LOCALE_COUNTRY", String.class, record.getLocaleCountry()));
            parameters.add(new EJStatementParameter("LOCALE_LANGUAGE", String.class, record.getLocaleLanguage()));
            parameters.add(new EJStatementParameter("NOTES", String.class, record.getNotes()));
            parameters.add(new EJStatementParameter("FOOTER", String.class, record.getFooter()));
            parameters.add(new EJStatementParameter("NR", String.class, record.getNr()));
            parameters.add(new EJStatementParameter("PAID", Integer.class, record.getPaid()));
            parameters.add(new EJStatementParameter("SENT", Integer.class, record.getSent()));
            parameters.add(new EJStatementParameter("SUMMARY", String.class, record.getSummary()));
            parameters.add(new EJStatementParameter("VAT_AMOUNT", BigDecimal.class, record.getVatAmount()));
            parameters.add(new EJStatementParameter("VAT_RATE", BigDecimal.class, record.getVatRate()));

            EJStatementCriteria criteria = new EJStatementCriteria();
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
            if (record.getInitialNotes() == null)
            {
                criteria.add(EJRestrictions.isNull("NOTES"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NOTES", record.getInitialNotes()));
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
            if (record.getInitialSummary() == null)
            {
                criteria.add(EJRestrictions.isNull("SUMMARY"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("SUMMARY", record.getInitialSummary()));
            }
            if (record.getInitialVatAmount() == null)
            {
                criteria.add(EJRestrictions.isNull("VAT_AMOUNT"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("VAT_AMOUNT", record.getInitialVatAmount()));
            }
            if (record.getInitialVatRate() == null)
            {
                criteria.add(EJRestrictions.isNull("VAT_RATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("VAT_RATE", record.getInitialVatRate()));
            }
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeUpdate(form, "invoice", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != updateRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in update. Expected: " + updateRecords.size() + ". Updated: "
                    + recordsProcessed);
        }
    }

    @Override
    public void executeDelete(EJForm form, List<InvoiceHistory> recordsToDelete)
    {
        ArrayList<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (InvoiceHistory record : recordsToDelete)
        {
            parameters.clear();

            EJStatementCriteria criteria = new EJStatementCriteria();

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
            if (record.getInitialNotes() == null)
            {
                criteria.add(EJRestrictions.isNull("NOTES"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NOTES", record.getInitialNotes()));
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
            if (record.getInitialSummary() == null)
            {
                criteria.add(EJRestrictions.isNull("SUMMARY"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("SUMMARY", record.getInitialSummary()));
            }
            if (record.getInitialVatAmount() == null)
            {
                criteria.add(EJRestrictions.isNull("VAT_AMOUNT"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("VAT_AMOUNT", record.getInitialVatAmount()));
            }
            if (record.getInitialVatRate() == null)
            {
                criteria.add(EJRestrictions.isNull("VAT_RATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("VAT_RATE", record.getInitialVatRate()));
            }
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeDelete(form, "invoice", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != recordsToDelete.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in delete. Expected: " + recordsToDelete.size() + ". Deleted: "
                    + recordsProcessed);
        }
    }

}