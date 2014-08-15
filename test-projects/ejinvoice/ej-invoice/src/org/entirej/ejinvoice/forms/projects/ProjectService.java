package org.entirej.ejinvoice.forms.projects;

import java.sql.Date;
import java.util.List;

import org.entirej.ejinvoice.forms.invoice.InvoicePosition;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJManagedFrameworkConnection;
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

        if (invpId != null)
        {
            selectStmt.append("and invp_id != ? ");
        }

        EJStatementExecutor executor = new EJStatementExecutor();

        EJStatementParameter projectIdParam = new EJStatementParameter(EJParameterType.IN);
        projectIdParam.setValue(projectId);

        EJStatementParameter periodFromParam = new EJStatementParameter(EJParameterType.IN);
        periodFromParam.setValue(periodFrom);

        EJStatementParameter periodToParam = new EJStatementParameter(EJParameterType.IN);
        periodToParam.setValue(periodTo);

        List<EJSelectResult> results = executor.executeQuery(form.getConnection(), selectStmt.toString(), periodFromParam, periodFromParam, periodToParam,
                periodToParam, projectIdParam);

        if (results.size() > 0)
        {
            EJSelectResult result = results.get(0);
            Date periodFromResult = (Date) result.getItemValue("PERIOD_FROM");
            Date periodToResult = (Date) result.getItemValue("PERIOD_TO");

            EJMessage message = new EJMessage(EJMessageLevel.ERROR, "This period overlaps with another period rangig from: " + periodFromResult + " : "
                    + periodToResult + ". Please change your invoice period accordingly.");
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

        executor.executeInsert(form.getConnection(), "INVOICE_POSITIONS", idParam, cuprIdParam, cuptIdParam, userIdParam, textParam, periodFromParam,
                periodToParam, statusParam, projectNameParam, taskNameParam);

    }

    public static void approveInvoicePosition(EJForm form, PlannedProjectItem position)
    {
        EJManagedFrameworkConnection con = form.getConnection();
        try
        {
            EJStatementExecutor executor = new EJStatementExecutor();

            // First update the custoemr_project_timeentry table so that all
            // entries have an invp_id
            EJStatementCriteria criteria = new EJStatementCriteria();
            criteria.add(EJRestrictions.equals("CUPT_ID", position.getTaskId()));
            criteria.add(EJRestrictions.between("WORK_DATE", position.getPeriodFrom(), position.getPeriodTo()));

            EJStatementParameter idParam = new EJStatementParameter("INVP_ID", Integer.class);
            idParam.setValue(position.getInvpId());

            executor.executeUpdate(con, "customer_project_timeentry", criteria, idParam);

            // Now set the invoice positon status to APPRVED
            criteria = new EJStatementCriteria();
            criteria.add(EJRestrictions.equals("ID", position.getInvpId()));

            EJStatementParameter statusParam = new EJStatementParameter("STATUS", String.class);
            statusParam.setValue("APPROVED");

            executor.executeUpdate(con, "invoice_positions", criteria, statusParam);

            con.commit();
        }
        catch (Exception e)
        {
            con.rollback();
        }
        finally
        {
            con.close();
        }

    }
}
