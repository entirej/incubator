package org.entirej.ejinvoice.forms.blockservices;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.entirej.ejinvoice.ApplicationParameters;
import org.entirej.ejinvoice.forms.blockservices.pojos.Invoice;
import org.entirej.ejinvoice.forms.login.User;
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
    private String                    _selectStatement = "SELECT AMOUNT_EXCL_VAT,AMOUNT_INCL_VAT,CONF_ID,CUST_ID,ID,INVOICE_ID,INV_DATE,NR,PAID,PAYINF_ID,PRINTED,USER_ID FROM invoice";

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
        User usr = (User) form.getApplicationLevelParameter(ApplicationParameters.PARAM_USER).getValue();
        queryCriteria.add(EJRestrictions.equals("USER_ID", usr.getId()));
        return _statementExecutor.executeQuery(Invoice.class, form, _selectStatement, queryCriteria);
    }

    @Override
    public void executeInsert(EJForm form, List<Invoice> newRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;
        User usr = (User)form.getApplicationLevelParameter(ApplicationParameters.PARAM_USER).getValue();
        for (Invoice record : newRecords)
        {
            
            // Initialise the value list
            parameters.clear();
            
            record.setUserId(usr.getId());
            parameters.add(new EJStatementParameter("AMOUNT_EXCL_VAT", BigDecimal.class, record.getAmountExclVat()));
            parameters.add(new EJStatementParameter("AMOUNT_INCL_VAT", BigDecimal.class, record.getAmountInclVat()));
            parameters.add(new EJStatementParameter("CONF_ID", Integer.class, record.getConfId()));
            parameters.add(new EJStatementParameter("CUST_ID", Integer.class, record.getCustId()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("INV_DATE", Date.class, record.getInvDate()));
            parameters.add(new EJStatementParameter("INVOICE_ID", Integer.class, record.getInvoiceId()));
            parameters.add(new EJStatementParameter("NR", String.class, record.getNr()));
            parameters.add(new EJStatementParameter("PAID", Integer.class, record.getPaid()));
            parameters.add(new EJStatementParameter("PAYINF_ID", Integer.class, record.getPayinfId()));
            parameters.add(new EJStatementParameter("PRINTED", Integer.class, record.getPrinted()));
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, usr.getId()));
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
            parameters.add(new EJStatementParameter("AMOUNT_EXCL_VAT", BigDecimal.class, record.getAmountExclVat()));
            parameters.add(new EJStatementParameter("AMOUNT_INCL_VAT", BigDecimal.class, record.getAmountInclVat()));
            parameters.add(new EJStatementParameter("CONF_ID", Integer.class, record.getConfId()));
            parameters.add(new EJStatementParameter("CUST_ID", Integer.class, record.getCustId()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("INV_DATE", Date.class, record.getInvDate()));
            parameters.add(new EJStatementParameter("INVOICE_ID", Integer.class, record.getInvoiceId()));
            parameters.add(new EJStatementParameter("NR", String.class, record.getNr()));
            parameters.add(new EJStatementParameter("PAID", Integer.class, record.getPaid()));
            parameters.add(new EJStatementParameter("PAYINF_ID", Integer.class, record.getPayinfId()));
            parameters.add(new EJStatementParameter("PRINTED", Integer.class, record.getPrinted()));
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, record.getUserId()));

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
            if (record.getInitialConfId() == null)
            {
                criteria.add(EJRestrictions.isNull("CONF_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CONF_ID", record.getInitialConfId()));
            }
            if (record.getInitialCustId() == null)
            {
                criteria.add(EJRestrictions.isNull("CUST_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CUST_ID", record.getInitialCustId()));
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
            if (record.getInitialInvoiceId() == null)
            {
                criteria.add(EJRestrictions.isNull("INVOICE_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("INVOICE_ID", record.getInitialInvoiceId()));
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
            if (record.getInitialPayinfId() == null)
            {
                criteria.add(EJRestrictions.isNull("PAYINF_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PAYINF_ID", record.getInitialPayinfId()));
            }
            if (record.getInitialPrinted() == null)
            {
                criteria.add(EJRestrictions.isNull("PRINTED"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PRINTED", record.getInitialPrinted()));
            }
            if (record.getInitialUserId() == null)
            {
                criteria.add(EJRestrictions.isNull("USER_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("USER_ID", record.getInitialUserId()));
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
            if (record.getInitialConfId() == null)
            {
                criteria.add(EJRestrictions.isNull("CONF_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CONF_ID", record.getInitialConfId()));
            }
            if (record.getInitialCustId() == null)
            {
                criteria.add(EJRestrictions.isNull("CUST_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CUST_ID", record.getInitialCustId()));
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
            if (record.getInitialInvoiceId() == null)
            {
                criteria.add(EJRestrictions.isNull("INVOICE_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("INVOICE_ID", record.getInitialInvoiceId()));
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
            if (record.getInitialPayinfId() == null)
            {
                criteria.add(EJRestrictions.isNull("PAYINF_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PAYINF_ID", record.getInitialPayinfId()));
            }
            if (record.getInitialPrinted() == null)
            {
                criteria.add(EJRestrictions.isNull("PRINTED"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PRINTED", record.getInitialPrinted()));
            }
            if (record.getInitialUserId() == null)
            {
                criteria.add(EJRestrictions.isNull("USER_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("USER_ID", record.getInitialUserId()));
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