package org.entirej.ejinvoice.forms.company;

import java.util.ArrayList;
import java.util.List;

import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class CompanyBlockService implements EJBlockService<Company>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT ADDRESS,COUNTRY,ID,LOGO,NAME,POST_CODE,TOWN, VAT_NR, INVOICE_FOOTER, INVOICE_SUMMARY FROM company_information";

    public CompanyBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<Company> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        if (queryCriteria.containsRestriction("COMPANY_ID"))
        {
            queryCriteria.removeRestriction("COMPANY_ID");
        }
        return _statementExecutor.executeQuery(Company.class, form, _selectStatement, queryCriteria);
    }

    @Override
    public void executeInsert(EJForm form, List<Company> newRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;
        for (Company record : newRecords)
        {
            // Initialise the value list
            parameters.clear();
            parameters.add(new EJStatementParameter("ADDRESS", String.class, record.getAddress()));
            parameters.add(new EJStatementParameter("INVOICE_FOOTER", String.class, record.getInvoiceFooter()));
            parameters.add(new EJStatementParameter("INVOICE_SUMMARY", String.class, record.getInvoiceSummary()));
            parameters.add(new EJStatementParameter("COUNTRY", String.class, record.getCountry()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("LOGO", Object.class, record.getLogo()));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getName()));
            parameters.add(new EJStatementParameter("POST_CODE", String.class, record.getPostCode()));
            parameters.add(new EJStatementParameter("TOWN", String.class, record.getTown()));
            parameters.add(new EJStatementParameter("VAT_NR", String.class, record.getVatNr()));
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "company_information", parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != newRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: " + newRecords.size() + ". Inserted: " + recordsProcessed);
        }
    }

    @Override
    public void executeUpdate(EJForm form, List<Company> updateRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (Company record : updateRecords)
        {
            parameters.clear();

            // First add the new values
            parameters.add(new EJStatementParameter("ADDRESS", String.class, record.getAddress()));
            parameters.add(new EJStatementParameter("INVOICE_SUMMARY", String.class, record.getInvoiceSummary()));
            parameters.add(new EJStatementParameter("INVOICE_NOTES", String.class, record.getInvoiceFooter()));
            parameters.add(new EJStatementParameter("COUNTRY", String.class, record.getCountry()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("LOGO", Object.class, record.getLogo()));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getName()));
            parameters.add(new EJStatementParameter("POST_CODE", String.class, record.getPostCode()));
            parameters.add(new EJStatementParameter("TOWN", String.class, record.getTown()));
            parameters.add(new EJStatementParameter("VAT_NR", String.class, record.getVatNr()));

            EJStatementCriteria criteria = new EJStatementCriteria();
            if (record.getInitialAddress() == null)
            {
                criteria.add(EJRestrictions.isNull("ADDRESS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ADDRESS", record.getInitialAddress()));
            }
            if (record.getInitialInvoiceSummary() == null)
            {
                criteria.add(EJRestrictions.isNull("INVOICE_SUMMARY"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("INVOICE_SUMMARY", record.getInitialInvoiceSummary()));
            }
            if (record.getInitialInvoiceFooter() == null)
            {
                criteria.add(EJRestrictions.isNull("INVOICE_FOOTER"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("INVOICE_FOOTER", record.getInitialInvoiceFooter()));
            }
            if (record.getInitialCountry() == null)
            {
                criteria.add(EJRestrictions.isNull("COUNTRY"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("COUNTRY", record.getInitialCountry()));
            }
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialLogo() == null)
            {
                criteria.add(EJRestrictions.isNull("LOGO"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("LOGO", record.getInitialLogo()));
            }
            if (record.getInitialName() == null)
            {
                criteria.add(EJRestrictions.isNull("NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NAME", record.getInitialName()));
            }
            if (record.getInitialPostCode() == null)
            {
                criteria.add(EJRestrictions.isNull("POST_CODE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("POST_CODE", record.getInitialPostCode()));
            }
            if (record.getInitialTown() == null)
            {
                criteria.add(EJRestrictions.isNull("TOWN"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("TOWN", record.getInitialTown()));
            }
            if (record.getInitialVatNr() == null)
            {
                criteria.add(EJRestrictions.isNull("VAT_NR"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("VAT_NR", record.getInitialVatNr()));
            }
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeUpdate(form, "company_information", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != updateRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in update. Expected: " + updateRecords.size() + ". Updated: " + recordsProcessed);
        }
    }

    @Override
    public void executeDelete(EJForm form, List<Company> recordsToDelete)
    {
        ArrayList<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (Company record : recordsToDelete)
        {
            parameters.clear();

            EJStatementCriteria criteria = new EJStatementCriteria();

            if (record.getInitialAddress() == null)
            {
                criteria.add(EJRestrictions.isNull("ADDRESS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ADDRESS", record.getInitialAddress()));
            }
            if (record.getInitialInvoiceSummary() == null)
            {
                criteria.add(EJRestrictions.isNull("INVOICE_SUMMARY"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("INVOICE_SUMMARY", record.getInitialInvoiceSummary()));
            }
            if (record.getInitialInvoiceFooter() == null)
            {
                criteria.add(EJRestrictions.isNull("INVOICE_FOOTER"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("INVOICE_FOOTER", record.getInitialInvoiceFooter()));
            }
            if (record.getInitialCountry() == null)
            {
                criteria.add(EJRestrictions.isNull("COUNTRY"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("COUNTRY", record.getInitialCountry()));
            }
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialLogo() == null)
            {
                criteria.add(EJRestrictions.isNull("LOGO"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("LOGO", record.getInitialLogo()));
            }
            if (record.getInitialName() == null)
            {
                criteria.add(EJRestrictions.isNull("NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NAME", record.getInitialName()));
            }
            if (record.getInitialPostCode() == null)
            {
                criteria.add(EJRestrictions.isNull("POST_CODE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("POST_CODE", record.getInitialPostCode()));
            }
            if (record.getInitialTown() == null)
            {
                criteria.add(EJRestrictions.isNull("TOWN"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("TOWN", record.getInitialTown()));
            }
            if (record.getInitialVatNr() == null)
            {
                criteria.add(EJRestrictions.isNull("VAT_NR"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("VAT_NR", record.getInitialVatNr()));
            }
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeDelete(form, "company_information", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != recordsToDelete.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in delete. Expected: " + recordsToDelete.size() + ". Deleted: " + recordsProcessed);
        }
    }

}