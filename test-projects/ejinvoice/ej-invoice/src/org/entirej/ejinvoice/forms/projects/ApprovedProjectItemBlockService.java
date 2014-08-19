package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJQuerySort;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class ApprovedProjectItemBlockService implements EJBlockService<ApprovedProjectItem>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT AMOUNT,CUPR_ID,CUPT_ID,FIX_PRICE,HOURS_WORKED,ID,INV_ID,PAY_RATE,PERIOD_FROM,PERIOD_TO,PROJECT_NAME,STATUS,TASK_NAME,TEXT,USER_ID, 'Add' AS ADD_TO_INVOICE FROM invoice_positions";

    public ApprovedProjectItemBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<ApprovedProjectItem> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        queryCriteria.add(EJQuerySort.ASC("PERIOD_FROM"));
        queryCriteria.add(EJQuerySort.ASC("PERIOD_TO"));
        queryCriteria.add(EJQuerySort.ASC("TASK_NAME"));
        queryCriteria.add(EJRestrictions.equals("STATUS", "APPROVED"));
        return _statementExecutor.executeQuery(ApprovedProjectItem.class, form, _selectStatement, queryCriteria);
    }

    @Override
    public void executeInsert(EJForm form, List<ApprovedProjectItem> newRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;
        for (ApprovedProjectItem record : newRecords)
        {
            // Initialise the value list
            parameters.clear();
            parameters.add(new EJStatementParameter("AMOUNT", BigDecimal.class, record.getAmount()));
            parameters.add(new EJStatementParameter("CUPR_ID", Integer.class, record.getCuprId()));
            parameters.add(new EJStatementParameter("CUPT_ID", Integer.class, record.getCuptId()));
            parameters.add(new EJStatementParameter("FIX_PRICE", BigDecimal.class, record.getFixPrice()));
            parameters.add(new EJStatementParameter("HOURS_WORKED", BigDecimal.class, record.getHoursWorked()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("INV_ID", Integer.class, record.getInvId()));
            parameters.add(new EJStatementParameter("PAY_RATE", BigDecimal.class, record.getPayRate()));
            parameters.add(new EJStatementParameter("PERIOD_FROM", Date.class, record.getPeriodFrom()));
            parameters.add(new EJStatementParameter("PERIOD_TO", Date.class, record.getPeriodTo()));
            parameters.add(new EJStatementParameter("PROJECT_NAME", String.class, record.getProjectName()));
            parameters.add(new EJStatementParameter("STATUS", String.class, record.getStatus()));
            parameters.add(new EJStatementParameter("TASK_NAME", String.class, record.getTaskName()));
            parameters.add(new EJStatementParameter("TEXT", String.class, record.getText()));
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, record.getUserId()));
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "invoice_positions", parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != newRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: " + newRecords.size() + ". Inserted: " + recordsProcessed);
        }
    }

    @Override
    public void executeUpdate(EJForm form, List<ApprovedProjectItem> updateRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (ApprovedProjectItem record : updateRecords)
        {
            parameters.clear();

            // First add the new values
            parameters.add(new EJStatementParameter("AMOUNT", BigDecimal.class, record.getAmount()));
            parameters.add(new EJStatementParameter("CUPR_ID", Integer.class, record.getCuprId()));
            parameters.add(new EJStatementParameter("CUPT_ID", Integer.class, record.getCuptId()));
            parameters.add(new EJStatementParameter("FIX_PRICE", BigDecimal.class, record.getFixPrice()));
            parameters.add(new EJStatementParameter("HOURS_WORKED", BigDecimal.class, record.getHoursWorked()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("INV_ID", Integer.class, record.getInvId()));
            parameters.add(new EJStatementParameter("PAY_RATE", BigDecimal.class, record.getPayRate()));
            parameters.add(new EJStatementParameter("PERIOD_FROM", Date.class, record.getPeriodFrom()));
            parameters.add(new EJStatementParameter("PERIOD_TO", Date.class, record.getPeriodTo()));
            parameters.add(new EJStatementParameter("PROJECT_NAME", String.class, record.getProjectName()));
            parameters.add(new EJStatementParameter("STATUS", String.class, record.getStatus()));
            parameters.add(new EJStatementParameter("TASK_NAME", String.class, record.getTaskName()));
            parameters.add(new EJStatementParameter("TEXT", String.class, record.getText()));
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, record.getUserId()));

            EJStatementCriteria criteria = new EJStatementCriteria();
            if (record.getInitialAmount() == null)
            {
                criteria.add(EJRestrictions.isNull("AMOUNT"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("AMOUNT", record.getInitialAmount()));
            }
            if (record.getInitialCuprId() == null)
            {
                criteria.add(EJRestrictions.isNull("CUPR_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CUPR_ID", record.getInitialCuprId()));
            }
            if (record.getInitialCuptId() == null)
            {
                criteria.add(EJRestrictions.isNull("CUPT_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CUPT_ID", record.getInitialCuptId()));
            }
            if (record.getInitialFixPrice() == null)
            {
                criteria.add(EJRestrictions.isNull("FIX_PRICE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("FIX_PRICE", record.getInitialFixPrice()));
            }
            if (record.getInitialHoursWorked() == null)
            {
                criteria.add(EJRestrictions.isNull("HOURS_WORKED"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("HOURS_WORKED", record.getInitialHoursWorked()));
            }
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialInvId() == null)
            {
                criteria.add(EJRestrictions.isNull("INV_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("INV_ID", record.getInitialInvId()));
            }
            if (record.getInitialPayRate() == null)
            {
                criteria.add(EJRestrictions.isNull("PAY_RATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PAY_RATE", record.getInitialPayRate()));
            }
            if (record.getInitialPeriodFrom() == null)
            {
                criteria.add(EJRestrictions.isNull("PERIOD_FROM"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PERIOD_FROM", record.getInitialPeriodFrom()));
            }
            if (record.getInitialPeriodTo() == null)
            {
                criteria.add(EJRestrictions.isNull("PERIOD_TO"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PERIOD_TO", record.getInitialPeriodTo()));
            }
            if (record.getInitialProjectName() == null)
            {
                criteria.add(EJRestrictions.isNull("PROJECT_NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PROJECT_NAME", record.getInitialProjectName()));
            }
            if (record.getInitialStatus() == null)
            {
                criteria.add(EJRestrictions.isNull("STATUS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("STATUS", record.getInitialStatus()));
            }
            if (record.getInitialTaskName() == null)
            {
                criteria.add(EJRestrictions.isNull("TASK_NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("TASK_NAME", record.getInitialTaskName()));
            }
            if (record.getInitialText() == null)
            {
                criteria.add(EJRestrictions.isNull("TEXT"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("TEXT", record.getInitialText()));
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
            recordsProcessed += _statementExecutor.executeUpdate(form, "invoice_positions", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != updateRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in update. Expected: " + updateRecords.size() + ". Updated: " + recordsProcessed);
        }
    }

    @Override
    public void executeDelete(EJForm form, List<ApprovedProjectItem> recordsToDelete)
    {
        ArrayList<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (ApprovedProjectItem record : recordsToDelete)
        {
            parameters.clear();

            EJStatementCriteria criteria = new EJStatementCriteria();

            if (record.getInitialAmount() == null)
            {
                criteria.add(EJRestrictions.isNull("AMOUNT"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("AMOUNT", record.getInitialAmount()));
            }
            if (record.getInitialCuprId() == null)
            {
                criteria.add(EJRestrictions.isNull("CUPR_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CUPR_ID", record.getInitialCuprId()));
            }
            if (record.getInitialCuptId() == null)
            {
                criteria.add(EJRestrictions.isNull("CUPT_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CUPT_ID", record.getInitialCuptId()));
            }
            if (record.getInitialFixPrice() == null)
            {
                criteria.add(EJRestrictions.isNull("FIX_PRICE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("FIX_PRICE", record.getInitialFixPrice()));
            }
            if (record.getInitialHoursWorked() == null)
            {
                criteria.add(EJRestrictions.isNull("HOURS_WORKED"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("HOURS_WORKED", record.getInitialHoursWorked()));
            }
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialInvId() == null)
            {
                criteria.add(EJRestrictions.isNull("INV_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("INV_ID", record.getInitialInvId()));
            }
            if (record.getInitialPayRate() == null)
            {
                criteria.add(EJRestrictions.isNull("PAY_RATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PAY_RATE", record.getInitialPayRate()));
            }
            if (record.getInitialPeriodFrom() == null)
            {
                criteria.add(EJRestrictions.isNull("PERIOD_FROM"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PERIOD_FROM", record.getInitialPeriodFrom()));
            }
            if (record.getInitialPeriodTo() == null)
            {
                criteria.add(EJRestrictions.isNull("PERIOD_TO"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PERIOD_TO", record.getInitialPeriodTo()));
            }
            if (record.getInitialProjectName() == null)
            {
                criteria.add(EJRestrictions.isNull("PROJECT_NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PROJECT_NAME", record.getInitialProjectName()));
            }
            if (record.getInitialStatus() == null)
            {
                criteria.add(EJRestrictions.isNull("STATUS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("STATUS", record.getInitialStatus()));
            }
            if (record.getInitialTaskName() == null)
            {
                criteria.add(EJRestrictions.isNull("TASK_NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("TASK_NAME", record.getInitialTaskName()));
            }
            if (record.getInitialText() == null)
            {
                criteria.add(EJRestrictions.isNull("TEXT"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("TEXT", record.getInitialText()));
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
            recordsProcessed += _statementExecutor.executeDelete(form, "invoice_positions", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != recordsToDelete.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in delete. Expected: " + recordsToDelete.size() + ". Deleted: " + recordsProcessed);
        }
    }

}