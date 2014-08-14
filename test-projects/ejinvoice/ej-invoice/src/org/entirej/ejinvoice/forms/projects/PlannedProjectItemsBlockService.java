package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.entirej.ejinvoice.forms.constants.F_PROJECTS;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJParameterType;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJSelectResult;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class PlannedProjectItemsBlockService implements EJBlockService<PlannedProjectItem>
{
    private final EJStatementExecutor _statementExecutor;
    private StringBuilder             _selectStatement = new StringBuilder();

    public PlannedProjectItemsBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
        
        _selectStatement.append("SELECT  CPR.NAME AS PROJECT_NAME ");
        _selectStatement.append(",       CUPT.ID AS TASK_ID ");
        _selectStatement.append(",       CUPT.CPR_ID AS PROJECT_ID ");
        _selectStatement.append(",       CUPT.NAME AS TASK_NAME ");
        _selectStatement.append(",       INVP.TEXT AS INVP_TEXT ");
        _selectStatement.append(",       MONTH(CPTE.WORK_DATE) AS TE_MONTH ");
        _selectStatement.append(",      YEAR(CPTE.WORK_DATE)  AS TE_YEAR ");
        _selectStatement.append(",      INVP.PERIOD_TO  AS PERIOD_TO");
        _selectStatement.append(",      INVP.PERIOD_FROM AS PERIOD_FROM ");
        _selectStatement.append(",      ((SUM(TIME_TO_SEC(TIMEDIFF(CPTE.END_TIME,CPTE.START_TIME))) / 60) / 60) WORK_HOURS  ");
        _selectStatement.append(",      INVP.ID INVP_ID ");
        _selectStatement.append("FROM customer_project_timeentry AS CPTE ");
        _selectStatement.append(",    customer_project_tasks AS CUPT ");
        _selectStatement.append(",    customer_projects AS CPR ");
        _selectStatement.append(",    invoice_positions AS INVP ");
        _selectStatement.append("WHERE CUPT.CPR_ID  = CPR.ID ");
        _selectStatement.append("AND   CPTE.CUPT_ID = CUPT.ID ");
        _selectStatement.append("AND   CPTE.INVP_ID IS NULL ");
        _selectStatement.append("AND   CUPT.INVP_ID IS NULL ");
        _selectStatement.append("AND   CPR.INVP_ID  IS NULL ");
        _selectStatement.append("AND   CPR.ID       = ? ");
        _selectStatement.append("AND   CPTE.WORK_DATE BETWEEN INVP.PERIOD_FROM AND INVP.PERIOD_TO ");
        _selectStatement.append("AND   INVP.STATUS = 'PLANNED' ");
        _selectStatement.append("AND   INVP.CUPR_ID = CPR.ID ");
        _selectStatement.append("GROUP BY TE_MONTH, TE_YEAR ");
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<PlannedProjectItem> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        ArrayList<PlannedProjectItem> projectItems = new ArrayList<PlannedProjectItem>();
        
        Integer projectId = (Integer)queryCriteria.getRestriction(F_PROJECTS.B_OPEN_PROJECT_ITEMS.I_PROJECT_ID).getValue();
        EJStatementParameter projectIdParam = new EJStatementParameter(EJParameterType.IN);
        projectIdParam.setValue(projectId);
        
        List<EJSelectResult> results = _statementExecutor.executeQuery(form.getConnection(), _selectStatement.toString(), projectIdParam);
        for (EJSelectResult result : results)
        {
            PlannedProjectItem item = new PlannedProjectItem();
            item.setInvpId((Integer)result.getItemValue("INVP_ID"));
            item.setInvpText((String)result.getItemValue("INVP_TEXT"));
            item.setProjectId((Integer)result.getItemValue("PROJECT_ID"));
            item.setProjectName((String)result.getItemValue("PROJECT_NAME"));
            item.setTaskId((Integer)result.getItemValue("TASK_ID"));
            item.setTaskName((String)result.getItemValue("TASK_NAME"));
            item.setTeMonth((Integer)result.getItemValue("TE_MONTH"));
            item.setTeYear((Integer)result.getItemValue("TE_YEAR"));
            item.setPeriodFrom((Date)result.getItemValue("PERIOD_FROM"));
            item.setPeriodTo((Date)result.getItemValue("PERIOD_TO"));
            item.setWorkHours((BigDecimal)result.getItemValue("WORK_HOURS"));
            item.setCreateInvoicePosition("Approve");
            
            projectItems.add(item);
        }
        
        return projectItems;
    }

    @Override
    public void executeInsert(EJForm form, List<PlannedProjectItem> newRecords)
    {
    }

    @Override
    public void executeUpdate(EJForm form, List<PlannedProjectItem> updateRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (PlannedProjectItem record : updateRecords)
        {
            parameters.clear();

            // First add the new values
            parameters.add(new EJStatementParameter("PROJECT_NAME", String.class, record.getProjectName()));
            parameters.add(new EJStatementParameter("TASK_NAME", String.class, record.getTaskName()));
            parameters.add(new EJStatementParameter("PERIOD_FROM", Date.class, record.getPeriodFrom()));
            parameters.add(new EJStatementParameter("PERIOD_TO", Date.class, record.getPeriodTo()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getInvpId()));

            EJStatementCriteria criteria = new EJStatementCriteria();
            criteria.add(EJRestrictions.equals("ID", record.getInitialInvpId()));
           
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeUpdate(form, "invoice_positions", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != updateRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in update. Expected: " + updateRecords.size() + ". Updated: "
                    + recordsProcessed);
        }
    }

    @Override
    public void executeDelete(EJForm form, List<PlannedProjectItem> recordsToDelete)
    {
    }

}