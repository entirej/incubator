package org.entirej.ejinvoice.forms.invoice;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.entirej.ejinvoice.ApplicationParameters;
import org.entirej.ejinvoice.forms.constants.F_INVOICE_OVERVIEW;
import org.entirej.ejinvoice.forms.login.User;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJParameterType;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJSelectResult;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class ProjectTimeOverviewBlockService implements EJBlockService<ProjectTimeOverview>
{
    private final EJStatementExecutor _statementExecutor;
    private StringBuffer              _selectStatement;

    private void createSelectStatement()
    {
        _selectStatement = new StringBuffer();

        _selectStatement.append("SELECT PROJ.NAME        AS PROJECT_NAME ");
        _selectStatement.append(",      PROJ.DESCRIPTION AS PROJECT_DESCRIPTION ");
        _selectStatement.append(",      PROJ.STATUS      AS PROJECT_STATUS ");
        _selectStatement.append(",      PROJ.ID          AS PROJECT_ID ");
        _selectStatement.append(",      PROT.ID          AS TASK_ID ");
        _selectStatement.append(",      PROT.NAME        AS TASK_NAME ");
        _selectStatement.append(",      PROJ.VAT_ID      AS VAT_ID ");
        _selectStatement.append(",      PROJ.FIXED_PRICE AS FIXED_PRICE ");
        _selectStatement.append(",      VAT.RATE         AS VAT_RATE ");
        _selectStatement.append(",      VAT.NAME         AS VAT_NAME ");
        _selectStatement.append(",      PROT.PAY_RATE    AS TASK_PAY_RATE ");
        _selectStatement.append(",      CUTI.WORK_DATE   AS WORK_DATE ");
        _selectStatement.append(",      CUTI.START_TIME  AS START_TIME ");
        _selectStatement.append(",      CUTI.END_TIME    AS END_TIME ");
        _selectStatement.append(",      CUTI.END_TIME    AS END_TIME ");
        _selectStatement.append(",      (TIME_TO_SEC(TIMEDIFF(END_TIME,START_TIME)) / 60) / 60 WORK_HOURS ");
        _selectStatement.append(",      CUTI.WORK_DESCRIPTION AS WORK_DESCRIPTION ");
        _selectStatement.append("FROM CUSTOMER_PROJECTS PROJ ");
        _selectStatement.append(",    CUSTOMER_PROJECT_TASKS PROT ");
        _selectStatement.append(",    CUSTOMER_PROJECT_TIMEENTRY CUTI ");
        _selectStatement.append(",    VAT_RATES  VAT ");
        _selectStatement.append("WHERE PROJ.ID      = PROT.CPR_ID ");
        _selectStatement.append("AND   PROT.ID      = CUTI.CUPT_ID ");
        _selectStatement.append("AND   PROT.VAT_ID  = VAT.ID ");
        _selectStatement.append("AND   PROJ.USER_ID = ? ");
        _selectStatement.append("AND   PROJ.CUSTOMER_ID = ? ");
        _selectStatement.append("AND   EXISTS (SELECT ID FROM CUSTOMER_PROJECT_TIMEENTRY WHERE INVP_ID IS NULL AND CUPT_ID = PROT.ID) ");
        _selectStatement.append("AND   PROJ.INVOICEABLE = \"Y\" ");
        _selectStatement.append("AND   PROT.INVOICEABLE = \"Y\" ");
        _selectStatement.append("ORDER BY PROJ.NAME, PROT.NAME, CUTI.WORK_DATE, CUTI.START_TIME ");
    }

    public ProjectTimeOverviewBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
        createSelectStatement();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    String taskName   = "";
    float  totalHours = 0;

    private void addTaskTotals(List<ProjectTimeOverview> projectTimes)
    {
        // Add totals line
        ProjectTimeOverview projectTime = new ProjectTimeOverview();
        projectTime.setHeaderRow(2);
        projectTime.setDescription("Total Hours:");
        projectTime.setWorkHours(new BigDecimal(totalHours));

        projectTimes.add(projectTime);

        // Add an empty line after the task totals
        projectTime = new ProjectTimeOverview();
        projectTimes.add(projectTime);

        taskName = null;
        totalHours = 0;

    }

    @Override
    public List<ProjectTimeOverview> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        List<ProjectTimeOverview> projectTimes = new ArrayList<ProjectTimeOverview>();

        String projectName = "";

        Integer customerId = (Integer) queryCriteria.getRestriction(F_INVOICE_OVERVIEW.B_PROJECT_TIME.I_CUSTOMER_ID).getValue();

        User usr = (User) form.getApplicationLevelParameter(ApplicationParameters.PARAM_USER).getValue();

        EJStatementParameter userIdParameter = new EJStatementParameter(EJParameterType.IN);
        userIdParameter.setValue(usr.getId());
        EJStatementParameter custIdParameter = new EJStatementParameter(EJParameterType.IN);
        custIdParameter.setValue(customerId);

        List<EJSelectResult> results = _statementExecutor.executeQuery(form.getConnection(), _selectStatement.toString(), userIdParameter, custIdParameter);

        for (EJSelectResult result : results)
        {
            if (!result.getItemValue("PROJECT_NAME").equals(projectName))
            {
                projectName = (String) result.getItemValue("PROJECT_NAME");

                ProjectTimeOverview projectTime = new ProjectTimeOverview();
                projectTime.setHeaderRow(1);
                projectTime.setDescription(projectName);

                projectTimes.add(projectTime);

                taskName = null;
            }

            if (taskName != null && !result.getItemValue("TASK_NAME").equals(taskName))
            {
                addTaskTotals(projectTimes);
            }

            ProjectTimeOverview projectTime = new ProjectTimeOverview();
            if (taskName == null)
            {
                projectTime.setDescription((String) result.getItemValue("TASK_NAME"));
                taskName = projectTime.getDescription();
            }
            projectTime.setWorkHours((BigDecimal) result.getItemValue("WORK_HOURS"));
            projectTime.setTaskPayRate((BigDecimal) result.getItemValue("TASK_PAY_RATE"));
            projectTime.setTotalExclVat(projectTime.getWorkHours().multiply(projectTime.getTaskPayRate()));
            projectTime.setVatRate((BigDecimal) result.getItemValue("VAT_RATE"));
            projectTime.setStartTime((Time) result.getItemValue("START_TIME"));
            projectTime.setEndTime((Time) result.getItemValue("END_TIME"));
            projectTime.setWorkDate((Date) result.getItemValue("WORK_DATE"));
            projectTime.setWorkHours((BigDecimal) result.getItemValue("WORK_HOURS"));
            projectTime.setWorkDescription((String) result.getItemValue("WORK_DESCRIPTION"));
            projectTime.setVatRate((BigDecimal) result.getItemValue("VAT_RATE"));

            BigDecimal vatAmount = projectTime.getTotalExclVat().multiply((projectTime.getVatRate().divide(new BigDecimal(100))));
            projectTime.setTotalInclVat(projectTime.getTotalExclVat().add(vatAmount));

            totalHours += projectTime.getWorkHours().floatValue();

            projectTimes.add(projectTime);
        }

        if (taskName != null)
        {
            addTaskTotals(projectTimes);
        }
        
        return projectTimes;
    }

    @Override
    public void executeInsert(EJForm form, List<ProjectTimeOverview> newRecords)
    {
    }

    @Override
    public void executeUpdate(EJForm form, List<ProjectTimeOverview> updateRecords)
    {
    }

    @Override
    public void executeDelete(EJForm form, List<ProjectTimeOverview> recordsToDelete)
    {
    }

}