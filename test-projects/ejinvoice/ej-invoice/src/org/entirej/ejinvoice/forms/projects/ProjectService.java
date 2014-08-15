package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.util.List;

import org.entirej.ejinvoice.forms.invoice.InvoicePosition;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.enumerations.EJMessageLevel;
import org.entirej.framework.core.service.EJParameterType;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJSelectResult;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class ProjectService
{

    public static void validateInvoicePeriod(EJForm form, Integer projectId, Date periodFrom, Date periodTo)
    {
        validateInvoicePeriod(form, projectId, null, periodFrom, periodTo);
    }

    public static void validateInvoicePeriod(EJForm form, Integer projectId, Integer invpId, Date periodFrom, Date periodTo)
    {
        StringBuilder selectStmt = new StringBuilder();

        selectStmt.append("select * from invoice_positions ");
        selectStmt.append("where (( ? >= period_from and ? <= period_to) or ( ? >= period_from and ? <= period_to)) ");
        selectStmt.append("and cupr_id = ? ");

        EJStatementExecutor executor = new EJStatementExecutor();

        EJStatementParameter projectIdParam = new EJStatementParameter(EJParameterType.IN);
        projectIdParam.setValue(projectId);

        EJStatementParameter periodFromParam = new EJStatementParameter(EJParameterType.IN);
        periodFromParam.setValue(periodFrom);

        EJStatementParameter periodToParam = new EJStatementParameter(EJParameterType.IN);
        periodToParam.setValue(periodTo);

        List<EJSelectResult> results;
        if (invpId != null)
        {
            selectStmt.append("and id != ? ");
            EJStatementParameter invpIdParam = new EJStatementParameter(EJParameterType.IN);
            invpIdParam.setValue(invpId);

            results = executor.executeQuery(form.getConnection(), selectStmt.toString(), periodFromParam, periodFromParam, periodToParam, periodToParam, projectIdParam, invpIdParam);
        }
        else
        {
            results = executor.executeQuery(form.getConnection(), selectStmt.toString(), periodFromParam, periodFromParam, periodToParam, periodToParam, projectIdParam);
        }

        if (results.size() > 0)
        {

            EJSelectResult result = results.get(0);

            String periodFromResult = DateFormat.getDateInstance(DateFormat.LONG, form.getCurrentLocale()).format(result.getItemValue("PERIOD_FROM"));
            String periodToResult = DateFormat.getDateInstance(DateFormat.LONG, form.getCurrentLocale()).format(result.getItemValue("PERIOD_TO"));

            EJMessage message = new EJMessage(EJMessageLevel.ERROR, "This period overlaps with another period rangig from: " + periodFromResult + " : " + periodToResult + ". Please change your invoice period accordingly.");
            throw new EJApplicationException(message);
        }

    }

    public void deletePlannedPosition(EJForm form, PlannedProjectItem item)
    {
        EJStatementExecutor executor = new EJStatementExecutor();

        EJStatementCriteria criteria = new EJStatementCriteria();
        criteria.add(EJRestrictions.equals("ID", item.getInvpId()));

        executor.executeDelete(form, "INVOICE_POSITIONS", criteria);

    }

    public void deleteApprovedPosition(EJForm form, ApprovedProjectItem item)
    {
        EJStatementExecutor executor = new EJStatementExecutor();

        EJStatementCriteria criteria = new EJStatementCriteria();
        criteria.add(EJRestrictions.equals("INVP_ID", item.getId()));
        
        EJStatementParameter invpIdParam = new EJStatementParameter("INVP_ID", Integer.class);
        invpIdParam.setValue(null);

        executor.executeUpdate(form, "CUSTOMER_PROJECT_TIMEENTRY", criteria, invpIdParam);

        
        criteria = new EJStatementCriteria();
        criteria.add(EJRestrictions.equals("ID", item.getId()));
        
        EJStatementParameter statusParam = new EJStatementParameter("STATUS", Integer.class);
        statusParam.setValue("PLANNED");
        
        executor.executeUpdate(form, "INVOICE_POSITIONS", criteria, statusParam);
    }
    
    public static void planInvoicePosition(EJForm form, InvoicePosition position)
    {
        EJStatementExecutor executor = new EJStatementExecutor();

        EJStatementParameter idParam = new EJStatementParameter("ID", Integer.class);
        idParam.setValue(position.getId());
        EJStatementParameter cuprIdParam = new EJStatementParameter("CUPR_ID", Integer.class);
        cuprIdParam.setValue(position.getCuprId());
        EJStatementParameter cuptIdParam = new EJStatementParameter("CUPT_ID", Integer.class);
        cuptIdParam.setValue(position.getCuptId());
        EJStatementParameter userIdParam = new EJStatementParameter("USER_ID", Integer.class);
        userIdParam.setValue(position.getUserId());
        EJStatementParameter textParam = new EJStatementParameter("TEXT", String.class);
        textParam.setValue(position.getText());
        EJStatementParameter periodFromParam = new EJStatementParameter("PERIOD_FROM", Date.class);
        periodFromParam.setValue(position.getPeriodFrom());
        EJStatementParameter periodToParam = new EJStatementParameter("PERIOD_TO", Date.class);
        periodToParam.setValue(position.getPeriodTo());
        EJStatementParameter statusParam = new EJStatementParameter("STATUS", String.class);
        statusParam.setValue(position.getStatus());
        EJStatementParameter projectNameParam = new EJStatementParameter("PROJECT_NAME", String.class);
        projectNameParam.setValue(position.getProjectName());
        EJStatementParameter taskNameParam = new EJStatementParameter("TASK_NAME", String.class);
        taskNameParam.setValue(position.getTaskName());
        
        executor.executeInsert(form.getConnection(), "INVOICE_POSITIONS", idParam, cuprIdParam, cuptIdParam, userIdParam, textParam, periodFromParam, periodToParam, statusParam, projectNameParam, taskNameParam);

    }

    public void approveInvoicePosition(EJForm form, PlannedProjectItem position)
    {

        EJStatementExecutor executor = new EJStatementExecutor();

        // First update the custoemr_project_timeentry table so that all
        // entries have an invp_id
        EJStatementCriteria criteria = new EJStatementCriteria();
        criteria.add(EJRestrictions.equals("CUPT_ID", position.getTaskId()));
        criteria.add(EJRestrictions.between("WORK_DATE", position.getPeriodFrom(), position.getPeriodTo()));

        EJStatementParameter idParam = new EJStatementParameter("INVP_ID", Integer.class);
        idParam.setValue(position.getInvpId());

        executor.executeUpdate(form, "customer_project_timeentry", criteria, idParam);

        // Now set the invoice positon status to APPRVED
        criteria = new EJStatementCriteria();
        criteria.add(EJRestrictions.equals("ID", position.getInvpId()));

        EJStatementParameter statusParam = new EJStatementParameter("STATUS", String.class);
        statusParam.setValue("APPROVED");

        EJStatementParameter hoursParam = new EJStatementParameter("HOURS_WORKED", BigDecimal.class);
        hoursParam.setValue(position.getWorkHours());
        
        EJStatementParameter payRateParam = new EJStatementParameter("PAY_RATE", BigDecimal.class);
        payRateParam.setValue(position.getPayRate());
        
        EJStatementParameter vatIdParam = new EJStatementParameter("VAT_ID", Integer.class);
        vatIdParam.setValue(position.getVatId());
        
        EJStatementParameter amountParam = new EJStatementParameter("AMOUNT", BigDecimal.class);
        amountParam.setValue(position.getWorkHours().multiply(position.getPayRate()));
        
        executor.executeUpdate(form, "invoice_positions", criteria, statusParam, hoursParam, payRateParam, vatIdParam, amountParam);
    }
}
