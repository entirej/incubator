package org.entirej.ejinvoice.forms.projects;

import java.sql.Date;

import org.entirej.ejinvoice.forms.invoice.InvoicePosition;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class ProjectService
{
    public enum INVOICE_POSITION_STATUS
    {
        PLANNED, RELEASED, INVOICED
    }

    public void deletePlannedPosition(EJForm form, OpenProjectItem item)
    {
        EJStatementExecutor executor = new EJStatementExecutor();
        
        EJStatementCriteria criteria = new EJStatementCriteria();
        criteria.add(EJRestrictions.equals("ID", item.getInvpId()));
            
        executor.executeDelete(form, "INVOICE_POSITIONS", criteria);
        
    }

    public void planInvoicePosition(EJForm form, InvoicePosition position)
    {
        EJStatementExecutor executor = new EJStatementExecutor();

        EJStatementParameter idParam = new EJStatementParameter("ID", String.class);
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

        executor.executeInsert(form.getConnection(), "INVOICE_POSITIONS", idParam, cuprIdParam, cuptIdParam, userIdParam, textParam, periodFromParam,
                periodToParam, statusParam);

    }

    public void approveInvoicePosition(EJForm form, InvoicePosition position)
    {

    }
}
