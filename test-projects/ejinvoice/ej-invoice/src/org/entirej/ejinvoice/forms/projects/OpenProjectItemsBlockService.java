package org.entirej.ejinvoice.forms.projects;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.entirej.ejinvoice.forms.constants.F_PROJECTS;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJParameterType;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJSelectResult;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class OpenProjectItemsBlockService implements EJBlockService<OpenProjectItem>
{
    private final EJStatementExecutor _statementExecutor;
    private StringBuilder             _selectStatement = new StringBuilder();

    public OpenProjectItemsBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
        
        _selectStatement.append("SELECT  CPR.NAME AS PROJECT_NAME ");
        _selectStatement.append(",       CUPT.ID AS TASK_ID ");
        _selectStatement.append(",       CUPT.CPR_ID AS PROJECT_ID ");
        _selectStatement.append(",       CUPT.NAME AS TASK_NAME ");
        _selectStatement.append(",       MONTH(CPTE.WORK_DATE) AS TE_MONTH ");
        _selectStatement.append(",      YEAR(CPTE.WORK_DATE)  AS TE_YEAR ");
        _selectStatement.append(",      LAST_DAY(CPTE.WORK_DATE)  AS TE_LAST_DAY ");
        _selectStatement.append(",      DATE(CONCAT(YEAR(CPTE.WORK_DATE), '.', MONTH(CPTE.WORK_DATE), '.01')) AS TE_FIRST_DAY ");
        _selectStatement.append(",      ((SUM(TIME_TO_SEC(TIMEDIFF(CPTE.END_TIME,CPTE.START_TIME))) / 60) / 60) WORK_HOURS  ");
        _selectStatement.append("FROM customer_project_timeentry AS CPTE ");
        _selectStatement.append(",    customer_project_tasks AS CUPT ");
        _selectStatement.append(",    customer_projects AS CPR ");
        _selectStatement.append("WHERE CUPT.CPR_ID  = CPR.ID ");
        _selectStatement.append("AND   CPTE.CUPT_ID = CUPT.ID ");
        _selectStatement.append("AND   CPTE.INVP_ID IS NULL ");
        _selectStatement.append("AND   CUPT.INVP_ID IS NULL ");
        _selectStatement.append("AND   CPR.INVP_ID  IS NULL ");
        _selectStatement.append("AND   CPR.ID       = ? ");
        _selectStatement.append("GROUP BY TE_MONTH, TE_YEAR ");
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<OpenProjectItem> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        ArrayList<OpenProjectItem> projecItems = new ArrayList<OpenProjectItem>();
        
        Integer projectId = (Integer)queryCriteria.getRestriction(F_PROJECTS.B_OPEN_POJECT_ITEMS.I_PROJECT_ID).getValue();
        EJStatementParameter projectIdParam = new EJStatementParameter(EJParameterType.IN);
        projectIdParam.setValue(projectId);
        
        List<EJSelectResult> results = _statementExecutor.executeQuery(form.getConnection(), _selectStatement.toString(), projectIdParam);
        for (EJSelectResult result : results)
        {
            OpenProjectItem item = new OpenProjectItem();
            item.setProjectId((Integer)result.getItemValue("PRJECT_ID"));
            item.setProjectName((String)result.getItemValue("PRJECT_NAME"));
            item.setTaskId((Integer)result.getItemValue("TASK_ID"));
            item.setTaskName((String)result.getItemValue("TASK_NAME"));
            item.setTeMonth((Integer)result.getItemValue("TE_MONTH"));
            item.setTeYear((Integer)result.getItemValue("TE_YEAR"));
            item.setTeLastDay((Date)result.getItemValue("TE_LAST_DAY"));
            item.setTeFirstDay((Date)result.getItemValue("TE_FIRST_DAY"));
            
        }
        
        return _statementExecutor.executeQuery(OpenProjectItem.class, form, _selectStatement.toString(), queryCriteria);
    }

    @Override
    public void executeInsert(EJForm form, List<OpenProjectItem> newRecords)
    {
    }

    @Override
    public void executeUpdate(EJForm form, List<OpenProjectItem> updateRecords)
    {
    }

    @Override
    public void executeDelete(EJForm form, List<OpenProjectItem> recordsToDelete)
    {
    }

}