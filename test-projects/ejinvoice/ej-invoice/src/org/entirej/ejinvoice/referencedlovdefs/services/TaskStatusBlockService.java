package org.entirej.ejinvoice.referencedlovdefs.services;

import java.util.ArrayList;
import java.util.List;

import org.entirej.ejinvoice.referencedlovdefs.services.pojos.TaskStatus;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJQuerySort;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class TaskStatusBlockService implements EJBlockService<TaskStatus>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT ASSIGN_AFTER_INVOICE,DESCRIPTION,ID,INVOICEABLE,NAME,ORDER_KEY,COMPANY_ID FROM task_status";

    public TaskStatusBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<TaskStatus> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        queryCriteria.add(EJQuerySort.ASC("ORDER_KEY"));
        return _statementExecutor.executeQuery(TaskStatus.class, form, _selectStatement, queryCriteria);
    }

    @Override
    public void executeInsert(EJForm form, List<TaskStatus> newRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;
        for (TaskStatus record : newRecords)
        {
            // Initialise the value list
            parameters.clear();
            parameters.add(new EJStatementParameter("ASSIGN_AFTER_INVOICE", Integer.class, record.getAssignAfterInvoice()));
            parameters.add(new EJStatementParameter("DESCRIPTION", String.class, record.getDescription()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("INVOICEABLE", Integer.class, record.getInvoiceable()));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getName()));
            parameters.add(new EJStatementParameter("ORDER_KEY", Integer.class, record.getOrderKey()));
            parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, record.getCompanyId()));
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "task_status", parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != newRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: " + newRecords.size() + ". Inserted: " + recordsProcessed);
        }
    }

    @Override
    public void executeUpdate(EJForm form, List<TaskStatus> updateRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (TaskStatus record : updateRecords)
        {
            parameters.clear();

            // First add the new values
            parameters.add(new EJStatementParameter("ASSIGN_AFTER_INVOICE", Integer.class, record.getAssignAfterInvoice()));
            parameters.add(new EJStatementParameter("DESCRIPTION", String.class, record.getDescription()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("INVOICEABLE", Integer.class, record.getInvoiceable()));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getName()));
            parameters.add(new EJStatementParameter("ORDER_KEY", Integer.class, record.getOrderKey()));
            parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, record.getCompanyId()));

            EJStatementCriteria criteria = new EJStatementCriteria();
            if (record.getInitialAssignAfterInvoice() == null)
            {
                criteria.add(EJRestrictions.isNull("ASSIGN_AFTER_INVOICE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ASSIGN_AFTER_INVOICE", record.getInitialAssignAfterInvoice()));
            }
            if (record.getInitialDescription() == null)
            {
                criteria.add(EJRestrictions.isNull("DESCRIPTION"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("DESCRIPTION", record.getInitialDescription()));
            }
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialInvoiceable() == null)
            {
                criteria.add(EJRestrictions.isNull("INVOICEABLE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("INVOICEABLE", record.getInitialInvoiceable()));
            }
            if (record.getInitialName() == null)
            {
                criteria.add(EJRestrictions.isNull("NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NAME", record.getInitialName()));
            }
            if (record.getInitialOrderKey() == null)
            {
                criteria.add(EJRestrictions.isNull("ORDER_KEY"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ORDER_KEY", record.getInitialOrderKey()));
            }
            if (record.getInitialCompanyId() == null)
            {
                criteria.add(EJRestrictions.isNull("COMPANY_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("COMPANY_ID", record.getInitialCompanyId()));
            }
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeUpdate(form, "task_status", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != updateRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in update. Expected: " + updateRecords.size() + ". Updated: " + recordsProcessed);
        }
    }

    @Override
    public void executeDelete(EJForm form, List<TaskStatus> recordsToDelete)
    {
        ArrayList<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (TaskStatus record : recordsToDelete)
        {
            parameters.clear();

            EJStatementCriteria criteria = new EJStatementCriteria();

            if (record.getInitialAssignAfterInvoice() == null)
            {
                criteria.add(EJRestrictions.isNull("ASSIGN_AFTER_INVOICE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ASSIGN_AFTER_INVOICE", record.getInitialAssignAfterInvoice()));
            }
            if (record.getInitialDescription() == null)
            {
                criteria.add(EJRestrictions.isNull("DESCRIPTION"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("DESCRIPTION", record.getInitialDescription()));
            }
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialInvoiceable() == null)
            {
                criteria.add(EJRestrictions.isNull("INVOICEABLE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("INVOICEABLE", record.getInitialInvoiceable()));
            }
            if (record.getInitialName() == null)
            {
                criteria.add(EJRestrictions.isNull("NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NAME", record.getInitialName()));
            }
            if (record.getInitialOrderKey() == null)
            {
                criteria.add(EJRestrictions.isNull("ORDER_KEY"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ORDER_KEY", record.getInitialOrderKey()));
            }
            if (record.getInitialCompanyId() == null)
            {
                criteria.add(EJRestrictions.isNull("COMPANY_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("COMPANY_ID", record.getInitialCompanyId()));
            }
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeDelete(form, "task_status", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != recordsToDelete.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in delete. Expected: " + recordsToDelete.size() + ". Deleted: " + recordsProcessed);
        }
    }

}