package org.entirej.ejinvoice.forms.company;

import java.util.ArrayList;
import java.util.List;
import org.entirej.ejinvoice.forms.company.Company;
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
    private String                    _selectStatement = "SELECT ACCOUNT_NUMBER,ADDRESS,BANK_ADDRESS,BANK_COUNTRY,BANK_NAME,BANK_POST_CODE,BANK_TOWN,COUNTRY,IBAN,ID,LOGO,NAME,POST_CODE,TOWN,USER_ID,VAT_NR FROM company_information";

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
            parameters.add(new EJStatementParameter("ACCOUNT_NUMBER", String.class, record.getAccountNumber()));
            parameters.add(new EJStatementParameter("ADDRESS", String.class, record.getAddress()));
            parameters.add(new EJStatementParameter("BANK_ADDRESS", String.class, record.getBankAddress()));
            parameters.add(new EJStatementParameter("BANK_COUNTRY", String.class, record.getBankCountry()));
            parameters.add(new EJStatementParameter("BANK_NAME", String.class, record.getBankName()));
            parameters.add(new EJStatementParameter("BANK_POST_CODE", String.class, record.getBankPostCode()));
            parameters.add(new EJStatementParameter("BANK_TOWN", String.class, record.getBankTown()));
            parameters.add(new EJStatementParameter("COUNTRY", String.class, record.getCountry()));
            parameters.add(new EJStatementParameter("IBAN", String.class, record.getIban()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("LOGO", Object.class, record.getLogo()));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getName()));
            parameters.add(new EJStatementParameter("POST_CODE", String.class, record.getPostCode()));
            parameters.add(new EJStatementParameter("TOWN", String.class, record.getTown()));
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, record.getUserId()));
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
            parameters.add(new EJStatementParameter("ACCOUNT_NUMBER", String.class, record.getAccountNumber()));
            parameters.add(new EJStatementParameter("ADDRESS", String.class, record.getAddress()));
            parameters.add(new EJStatementParameter("BANK_ADDRESS", String.class, record.getBankAddress()));
            parameters.add(new EJStatementParameter("BANK_COUNTRY", String.class, record.getBankCountry()));
            parameters.add(new EJStatementParameter("BANK_NAME", String.class, record.getBankName()));
            parameters.add(new EJStatementParameter("BANK_POST_CODE", String.class, record.getBankPostCode()));
            parameters.add(new EJStatementParameter("BANK_TOWN", String.class, record.getBankTown()));
            parameters.add(new EJStatementParameter("COUNTRY", String.class, record.getCountry()));
            parameters.add(new EJStatementParameter("IBAN", String.class, record.getIban()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("LOGO", Object.class, record.getLogo()));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getName()));
            parameters.add(new EJStatementParameter("POST_CODE", String.class, record.getPostCode()));
            parameters.add(new EJStatementParameter("TOWN", String.class, record.getTown()));
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, record.getUserId()));
            parameters.add(new EJStatementParameter("VAT_NR", String.class, record.getVatNr()));

            EJStatementCriteria criteria = new EJStatementCriteria();
            if (record.getInitialAccountNumber() == null)
            {
                criteria.add(EJRestrictions.isNull("ACCOUNT_NUMBER"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ACCOUNT_NUMBER", record.getInitialAccountNumber()));
            }
            if (record.getInitialAddress() == null)
            {
                criteria.add(EJRestrictions.isNull("ADDRESS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ADDRESS", record.getInitialAddress()));
            }
            if (record.getInitialBankAddress() == null)
            {
                criteria.add(EJRestrictions.isNull("BANK_ADDRESS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("BANK_ADDRESS", record.getInitialBankAddress()));
            }
            if (record.getInitialBankCountry() == null)
            {
                criteria.add(EJRestrictions.isNull("BANK_COUNTRY"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("BANK_COUNTRY", record.getInitialBankCountry()));
            }
            if (record.getInitialBankName() == null)
            {
                criteria.add(EJRestrictions.isNull("BANK_NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("BANK_NAME", record.getInitialBankName()));
            }
            if (record.getInitialBankPostCode() == null)
            {
                criteria.add(EJRestrictions.isNull("BANK_POST_CODE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("BANK_POST_CODE", record.getInitialBankPostCode()));
            }
            if (record.getInitialBankTown() == null)
            {
                criteria.add(EJRestrictions.isNull("BANK_TOWN"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("BANK_TOWN", record.getInitialBankTown()));
            }
            if (record.getInitialCountry() == null)
            {
                criteria.add(EJRestrictions.isNull("COUNTRY"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("COUNTRY", record.getInitialCountry()));
            }
            if (record.getInitialIban() == null)
            {
                criteria.add(EJRestrictions.isNull("IBAN"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("IBAN", record.getInitialIban()));
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
            if (record.getInitialUserId() == null)
            {
                criteria.add(EJRestrictions.isNull("USER_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("USER_ID", record.getInitialUserId()));
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

            if (record.getInitialAccountNumber() == null)
            {
                criteria.add(EJRestrictions.isNull("ACCOUNT_NUMBER"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ACCOUNT_NUMBER", record.getInitialAccountNumber()));
            }
            if (record.getInitialAddress() == null)
            {
                criteria.add(EJRestrictions.isNull("ADDRESS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ADDRESS", record.getInitialAddress()));
            }
            if (record.getInitialBankAddress() == null)
            {
                criteria.add(EJRestrictions.isNull("BANK_ADDRESS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("BANK_ADDRESS", record.getInitialBankAddress()));
            }
            if (record.getInitialBankCountry() == null)
            {
                criteria.add(EJRestrictions.isNull("BANK_COUNTRY"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("BANK_COUNTRY", record.getInitialBankCountry()));
            }
            if (record.getInitialBankName() == null)
            {
                criteria.add(EJRestrictions.isNull("BANK_NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("BANK_NAME", record.getInitialBankName()));
            }
            if (record.getInitialBankPostCode() == null)
            {
                criteria.add(EJRestrictions.isNull("BANK_POST_CODE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("BANK_POST_CODE", record.getInitialBankPostCode()));
            }
            if (record.getInitialBankTown() == null)
            {
                criteria.add(EJRestrictions.isNull("BANK_TOWN"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("BANK_TOWN", record.getInitialBankTown()));
            }
            if (record.getInitialCountry() == null)
            {
                criteria.add(EJRestrictions.isNull("COUNTRY"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("COUNTRY", record.getInitialCountry()));
            }
            if (record.getInitialIban() == null)
            {
                criteria.add(EJRestrictions.isNull("IBAN"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("IBAN", record.getInitialIban()));
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
            if (record.getInitialUserId() == null)
            {
                criteria.add(EJRestrictions.isNull("USER_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("USER_ID", record.getInitialUserId()));
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