package org.entirej.ejinvoice.referencedlovdefs.services;

import java.util.List;

import org.entirej.ejinvoice.referencedlovdefs.services.pojos.CustomerProjectTask;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementExecutor;

public class CustomerProjectProcessBlockService implements EJBlockService<CustomerProjectTask>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT CPT.STATUS, CPT.CPR_ID, CPT.ID, CPT.NAME TASK_NAME, CPT.NOTES, CPT.PAY_RATE, CPR.COMPANY_ID, CPR.NAME PROJECT_NAME, CPR.DESCRIPTION PROJECT_DESCRIPTION FROM CUSTOMER_PROJECTS CPR, CUSTOMER_PROJECT_TASKS CPT WHERE CPT.CPR_ID = CPR.ID";

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
    public List<CustomerProjectTask> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        queryCriteria.setRestrictionAlias("id", "CPT.ID");
        queryCriteria.setRestrictionAlias("cprId", "CPR.ID");
        queryCriteria.setRestrictionAlias("companyId", "CPR.COMPANY_ID");
        queryCriteria.setRestrictionAlias("status", "CPT.STATUS");
        
        if (!queryCriteria.containsRestriction("cprId"))
        {
            queryCriteria.add(EJRestrictions.isNull("cprId"));
        }
        
        return _statementExecutor.executeQuery(CustomerProjectTask.class, form, _selectStatement, queryCriteria);
    }

    @Override
    public void executeInsert(EJForm form, List<CustomerProjectTask> newRecords)
    {

    }

    @Override
    public void executeUpdate(EJForm form, List<CustomerProjectTask> updateRecords)
    {
        
    }

    @Override
    public void executeDelete(EJForm form, List<CustomerProjectTask> recordsToDelete)
    {

    }

}