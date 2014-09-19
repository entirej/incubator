package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.entirej.ejinvoice.forms.projects.ProjectTasks;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJQuerySort;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class ProjectTasksBlockService implements EJBlockService<ProjectTasks>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT CPR_ID,ID,NAME,NOTES,PAY_RATE,COMPANY_ID, FIX_PRICE, STATUS,  INVOICEABLE, (SELECT NAME FROM CUSTOMER WHERE ID = (SELECT CUSTOMER_ID FROM CUSTOMER_PROJECTS WHERE ID = CUSTOMER_PROJECT_TASKS.CPR_ID)) CUSTOMER_NAME,(SELECT NAME FROM CUSTOMER_PROJECTS WHERE ID = CUSTOMER_PROJECT_TASKS.CPR_ID) PROJECT_NAME FROM customer_project_tasks";

    public ProjectTasksBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<ProjectTasks> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        queryCriteria.add(EJQuerySort.ASC("CUSTOMER_NAME"));
        queryCriteria.add(EJQuerySort.ASC("NAME"));

        return _statementExecutor.executeQuery(ProjectTasks.class, form, _selectStatement, queryCriteria);
    }

    @Override
    public void executeInsert(EJForm form, List<ProjectTasks> newRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;
        for (ProjectTasks record : newRecords)
        {
            // Initialise the value list
            parameters.clear();
            parameters.add(new EJStatementParameter("CPR_ID", Integer.class, record.getCprId()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getName()));
            parameters.add(new EJStatementParameter("NOTES", String.class, record.getNotes()));
            parameters.add(new EJStatementParameter("PAY_RATE", BigDecimal.class, record.getPayRate()));
            parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, record.getCompanyId()));
            parameters.add(new EJStatementParameter("FIX_PRICE", BigDecimal.class, record.getFixPrice()));
            parameters.add(new EJStatementParameter("INVOICEABLE", String.class, record.getInvoiceable()));
            parameters.add(new EJStatementParameter("STATUS", Integer.class, record.getStatus()));
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "customer_project_tasks", parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != newRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: " + newRecords.size() + ". Inserted: " + recordsProcessed);
        }
    }

    @Override
    public void executeUpdate(EJForm form, List<ProjectTasks> updateRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (ProjectTasks record : updateRecords)
        {
            parameters.clear();

            // First add the new values
            parameters.add(new EJStatementParameter("CPR_ID", Integer.class, record.getCprId()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getName()));
            parameters.add(new EJStatementParameter("NOTES", String.class, record.getNotes()));
            parameters.add(new EJStatementParameter("PAY_RATE", BigDecimal.class, record.getPayRate()));
            parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, record.getCompanyId()));
            parameters.add(new EJStatementParameter("FIX_PRICE", BigDecimal.class, record.getFixPrice()));
            parameters.add(new EJStatementParameter("INVOICEABLE", String.class, record.getInvoiceable()));
            parameters.add(new EJStatementParameter("STATUS", Integer.class, record.getStatus()));

            EJStatementCriteria criteria = new EJStatementCriteria();
            if (record.getInitialCprId() == null)
            {
                criteria.add(EJRestrictions.isNull("CPR_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CPR_ID", record.getInitialCprId()));
            }
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialName() == null)
            {
                criteria.add(EJRestrictions.isNull("NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NAME", record.getInitialName()));
            }
            if (record.getInitialNotes() == null)
            {
                criteria.add(EJRestrictions.isNull("NOTES"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NOTES", record.getInitialNotes()));
            }
            if (record.getInitialPayRate() == null)
            {
                criteria.add(EJRestrictions.isNull("PAY_RATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PAY_RATE", record.getInitialPayRate()));
            }
            if (record.getInitialCompanyId() == null)
            {
                criteria.add(EJRestrictions.isNull("COMPANY_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("COMPANY_ID", record.getInitialCompanyId()));
            }
            if (record.getInitialFixPrice() == null)
            {
                criteria.add(EJRestrictions.isNull("FIX_PRICE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("FIX_PRICE", record.getInitialFixPrice()));
            }
            if (record.getInitialInvoiceable() == null)
            {
                criteria.add(EJRestrictions.isNull("INVOICEABLE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("INVOICEABLE", record.getInitialInvoiceable()));
            }
            if (record.getInitialStatus() == null)
            {
                criteria.add(EJRestrictions.isNull("STATUS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("STATUS", record.getInitialStatus()));
            }

            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeUpdate(form, "customer_project_tasks", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != updateRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in update. Expected: " + updateRecords.size() + ". Updated: " + recordsProcessed);
        }
    }

    @Override
    public void executeDelete(EJForm form, List<ProjectTasks> recordsToDelete)
    {
        ArrayList<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (ProjectTasks record : recordsToDelete)
        {
            parameters.clear();

            EJStatementCriteria criteria = new EJStatementCriteria();

            if (record.getInitialCprId() == null)
            {
                criteria.add(EJRestrictions.isNull("CPR_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CPR_ID", record.getInitialCprId()));
            }
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialName() == null)
            {
                criteria.add(EJRestrictions.isNull("NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NAME", record.getInitialName()));
            }
            if (record.getInitialNotes() == null)
            {
                criteria.add(EJRestrictions.isNull("NOTES"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NOTES", record.getInitialNotes()));
            }
            if (record.getInitialPayRate() == null)
            {
                criteria.add(EJRestrictions.isNull("PAY_RATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PAY_RATE", record.getInitialPayRate()));
            }
            if (record.getInitialCompanyId() == null)
            {
                criteria.add(EJRestrictions.isNull("COMPANY_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("COMPANY_ID", record.getInitialCompanyId()));
            }
            if (record.getInitialFixPrice() == null)
            {
                criteria.add(EJRestrictions.isNull("FIX_PRICE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("FIX_PRICE", record.getInitialFixPrice()));
            }
            if (record.getInitialInvoiceable() == null)
            {
                criteria.add(EJRestrictions.isNull("INVOICEABLE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("INVOICEABLE", record.getInitialInvoiceable()));
            }
            if (record.getInitialStatus() == null)
            {
                criteria.add(EJRestrictions.isNull("STATUS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("STATUS", record.getInitialStatus()));
            }
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeDelete(form, "customer_project_tasks", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != recordsToDelete.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in delete. Expected: " + recordsToDelete.size() + ". Deleted: " + recordsProcessed);
        }
    }

}