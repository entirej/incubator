package org.entirej.ejinvoice.referencedlovdefs.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.entirej.ejinvoice.referencedlovdefs.services.pojos.CustomerProjectProcess;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class CustomerProjectProcessBlockService implements EJBlockService<CustomerProjectProcess>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT CPR_ID,ID,NAME PROCESS_NAME,NOTES,PAY_RATE,USER_ID,VAT_ID, (SELECT NAME FROM customer_projects where id = CPR_ID) PROJECT_NAME FROM customer_project_process";

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