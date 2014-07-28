package org.entirej.ejinvoice.forms.invoice;

import java.util.List;

import org.entirej.ejinvoice.ApplicationParameters;
import org.entirej.ejinvoice.forms.login.User;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementExecutor;

public class ProjectTimeBlockService implements EJBlockService<ProjectTime>
{
    private final EJStatementExecutor _statementExecutor;
    private StringBuffer              _selectStatement;

    
    private void createSelectStatement()
    {
        _selectStatement = new StringBuffer();
        
        _selectStatement.append("SELECT PROJ.NAME        AS PROJECT_NAME ");
        _selectStatement.append(",      PROJ.DESCRIPTION AS PROJECT_DESCRIPTION");
        _selectStatement.append(",      PROJ.STATUS      AS PROJECT_STATUS");
        _selectStatement.append(",      PROJ.ID          AS PROJECT_ID");
        _selectStatement.append(",      PROT.ID          AS TASK_ID");
        _selectStatement.append(",      PROT.NAME        AS TASK_NAME");
        _selectStatement.append(",      PROT.VAT_ID      AS TASK_VAT_ID");
        _selectStatement.append(",      VAT.RATE         AS VAT_RATE");
        _selectStatement.append(",      VAT.NAME         AS VAT_NAME");
        _selectStatement.append(",      PROT.PAY_RATE    AS TASK_PAY_RATE");
        _selectStatement.append(",      (SELECT ((SUM(TIME_TO_SEC(TIMEDIFF(END_TIME,START_TIME))) / 60) / 60) FROM CUSTOMER_PROJECT_TIMEENTRY WHERE CUPT_ID = PROT.ID) WORK_HOURS");
        _selectStatement.append("FROM CUSTOMER_PROJECTS PROJ");
        _selectStatement.append(",    CUSTOMER_PROJECT_TASKS PROT");
        _selectStatement.append(",    VAT_RATES  VAT");
        _selectStatement.append("WHERE PROJ.ID     = PROT.CPR_ID");
        _selectStatement.append("AND   PROT.VAT_ID = VAT.ID");
    }

    public ProjectTimeBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
        createSelectStatement();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<ProjectTime> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        User usr = (User) form.getApplicationLevelParameter(ApplicationParameters.PARAM_USER).getValue();
        queryCriteria.add(EJRestrictions.equals("USER_ID", usr.getId()));
        return _statementExecutor.executeQuery(ProjectTime.class, form, _selectStatement.toString(), queryCriteria);
    }

    @Override
    public void executeInsert(EJForm form, List<ProjectTime> newRecords)
    {
    }

    @Override
    public void executeUpdate(EJForm form, List<ProjectTime> updateRecords)
    {
    }

    @Override
    public void executeDelete(EJForm form, List<ProjectTime> recordsToDelete)
    {
    }

}