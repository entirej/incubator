package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.forms.company.User;
import org.entirej.ejinvoice.forms.constants.F_INVOICE_PLANNING;
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
        
        _selectStatement.append("select invp.project_name AS PROJECT_NAME");
        _selectStatement.append(",      invp.id   as INVP_ID ");
        _selectStatement.append(",      invp.cupt_id   as TASK_ID ");
        _selectStatement.append(",      invp.cupr_id     as PROJECT_ID ");
        _selectStatement.append(",      invp.task_name AS TASK_NAME ");
        _selectStatement.append(",      invp.text AS INVP_TEXT");
        _selectStatement.append(",      invp.period_from AS PERIOD_FROM");
        _selectStatement.append(",      invp.period_to AS PERIOD_TO ");
        _selectStatement.append(",      invp.company_id AS COMPANY_ID ");
        _selectStatement.append(",     (select pay_rate from customer_project_tasks where id = invp.cupt_id) AS PAY_RATE ");
        _selectStatement.append(",     (select fix_price from customer_project_tasks where id = invp.cupt_id) AS FIX_PRICE ");
        _selectStatement.append(",     (select (SUM(TIME_TO_SEC(TIMEDIFF(cpte.end_time, cpte.start_time))) / 60) / 60 ");
        _selectStatement.append("       from  customer_project_timeentry cpte "); 
        _selectStatement.append("       where cpte.work_date between invp.period_from and invp.period_to ");
        _selectStatement.append("       and   cpte.cupt_id = invp.cupt_id) AS WORK_HOURS ");
        _selectStatement.append("from invoice_positions invp ");
        _selectStatement.append("where invp.status = 'PLANNED' ");
        _selectStatement.append("and   invp.cupr_id in (select id from customer_projects where customer_id = ?) ");
        _selectStatement.append("and   invp.company_id = ? ");
        _selectStatement.append("order by invp.period_from ");
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<PlannedProjectItem> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        User user = (User)form.getApplicationLevelParameter(EJ_PROPERTIES.P_USER).getValue();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, user.getLocale());

        ArrayList<PlannedProjectItem> projectItems = new ArrayList<PlannedProjectItem>();
        
        Integer customerId = (Integer)queryCriteria.getRestriction(F_INVOICE_PLANNING.B_PLANNED_PROJECT_ITEMS.I_CUSTOMER_ID).getValue();
        EJStatementParameter customerIdParam = new EJStatementParameter(EJParameterType.IN);
        customerIdParam.setValue(customerId);

        Integer companyId = (Integer)form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
        EJStatementParameter companyIdParam = new EJStatementParameter(EJParameterType.IN);
        companyIdParam.setValue(companyId);

        
        List<EJSelectResult> results = _statementExecutor.executeQuery(form.getConnection(), _selectStatement.toString(), customerIdParam, companyIdParam);
        for (EJSelectResult result : results)
        {
            PlannedProjectItem item = new PlannedProjectItem();
            item.setCompanyId((Integer)result.getItemValue("COMPANY_ID"));
            item.setInvpId((Integer)result.getItemValue("INVP_ID"));
            item.setInvpText((String)result.getItemValue("INVP_TEXT"));
            item.setProjectId((Integer)result.getItemValue("PROJECT_ID"));
            item.setProjectName((String)result.getItemValue("PROJECT_NAME"));
            item.setTaskId((Integer)result.getItemValue("TASK_ID"));
            item.setTaskName((String)result.getItemValue("TASK_NAME"));
            item.setPeriodFrom((Date)result.getItemValue("PERIOD_FROM"));
            item.setPeriodTo((Date)result.getItemValue("PERIOD_TO"));
            item.setWorkHours((BigDecimal)result.getItemValue("WORK_HOURS"));
            item.setPayRate((BigDecimal)result.getItemValue("PAY_RATE"));
            item.setFixPrice((BigDecimal)result.getItemValue("FIX_PRICE"));
            item.setCreateInvoicePosition("Approve");
            
            StringBuilder display = new StringBuilder();
            display.append("<span style =\"font-weight: bold; font-size: 110% \">"+item.getProjectName()+"  ("+item.getTaskName()+")</span>");
            display.append("<br><span style =\"font-weight: normal; font-size: 100% \">"+dateFormat.format(item.getPeriodFrom())+" - "+dateFormat.format(item.getPeriodTo())+"</span></br>");
            item.setDisplayText(display.toString());
            item.setDisplayValueText("Hours: ");

            
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
            parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, record.getCompanyId()));
            parameters.add(new EJStatementParameter("PROJECT_NAME", String.class, record.getProjectName()));
            parameters.add(new EJStatementParameter("TASK_NAME", String.class, record.getTaskName()));
            parameters.add(new EJStatementParameter("PERIOD_FROM", Date.class, record.getPeriodFrom()));
            parameters.add(new EJStatementParameter("PERIOD_TO", Date.class, record.getPeriodTo()));
            parameters.add(new EJStatementParameter("TEXT", String.class, record.getInvpText()));
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