package org.entirej.ejinvoice.referencedlovdefs.services;

import java.util.List;

import org.entirej.ejinvoice.referencedlovdefs.services.pojos.CustomerProjectProcess;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;

public class CustomerProjectProcessBlockService implements EJBlockService<CustomerProjectProcess>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT CPP.CPR_ID, CPP.ID, CPP.NAME PROCESS_NAME, CPP.NOTES, CPP.PAY_RATE, CPP.USER_ID, CPP.VAT_ID, CPR.NAME PROJECT_NAME, cpr.DESCRIPTION PROJECT_DESCRIPTION FROM customer_projects cpr, customer_project_process cpp WHERE cpp.CPR_ID = cpr.ID";

    public CustomerProjectProcessBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<CustomerProjectProcess> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        queryCriteria.setRestrictionAlias("ID", "CPP.ID");
        queryCriteria.setRestrictionAlias("CPR_ID", "CPR.ID");
        
        return _statementExecutor.executeQuery(CustomerProjectProcess.class, form, _selectStatement, queryCriteria);
    }

    @Override
    public void executeInsert(EJForm form, List<CustomerProjectProcess> newRecords)
    {

    }

    @Override
    public void executeUpdate(EJForm form, List<CustomerProjectProcess> updateRecords)
    {
        
    }

    @Override
    public void executeDelete(EJForm form, List<CustomerProjectProcess> recordsToDelete)
    {

    }

}