package org.entirej.ejinvoice.forms.invoice;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.List;
import java.util.Locale.Builder;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.forms.company.Company;
import org.entirej.ejinvoice.forms.constants.F_INVOICE;
import org.entirej.ejinvoice.forms.constants.F_PROJECTS;
import org.entirej.ejinvoice.forms.projects.PlannedProjectItem;
import org.entirej.ejinvoice.forms.timeentry.Customer;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.enumerations.EJMessageLevel;
import org.entirej.framework.core.service.EJParameterType;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJSelectResult;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class InvoiceService
{
    public String getLastInvoicNr(EJForm form, Integer customerId)
    {
        EJStatementExecutor executor = new EJStatementExecutor();

        EJStatementParameter custIdParam = new EJStatementParameter(EJParameterType.IN);
        custIdParam.setValue(customerId);

        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();

        EJStatementParameter companyIdParam = new EJStatementParameter(EJParameterType.IN);
        companyIdParam.setValue(companyId);

        String selectStmt = "SELECT NR FROM INVOICE WHERE ID = (SELECT MAX(ID) FROM INVOICE WHERE CUST_ID = ? AND COMPANY_ID = ? )";

        List<EJSelectResult> results = executor.executeQuery(form.getConnection(), selectStmt, custIdParam, companyIdParam);
        if (results.size() > 0)
        {
            return "Last invoice number: " + results.get(0).getItemValue("NR");
        }
        else
        {
            return "No invoices created for this customer";
        }
    }
    
    public byte[] getInvoicPDF(EJForm form, int invNR)
    {
        EJStatementExecutor executor = new EJStatementExecutor();
        
        EJStatementParameter custIdParam = new EJStatementParameter(EJParameterType.IN);
        custIdParam.setValue(invNR);
        
        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
        
        EJStatementParameter companyIdParam = new EJStatementParameter(EJParameterType.IN);
        companyIdParam.setValue(companyId);
        
        String selectStmt = "SELECT INVOICE_FILE FROM INVOICE WHERE ID = ? and COMPANY_ID = ?";
        
        List<EJSelectResult> results = executor.executeQuery(form.getConnection(), selectStmt, custIdParam, companyIdParam);
        if (results.size() > 0)
        {
            return (byte[])results.get(0).getItemValue("INVOICE_FILE");
        }
        else
        {
            return null;
        }
    }
    public byte[] getInvoicDtlPDF(EJForm form, int invNR)
    {
        EJStatementExecutor executor = new EJStatementExecutor();
        
        EJStatementParameter custIdParam = new EJStatementParameter(EJParameterType.IN);
        custIdParam.setValue(invNR);
        
        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
        
        EJStatementParameter companyIdParam = new EJStatementParameter(EJParameterType.IN);
        companyIdParam.setValue(companyId);
        
        String selectStmt = "SELECT INVOICE_DTL_FILE FROM INVOICE WHERE ID = ? and COMPANY_ID = ?";
        
        List<EJSelectResult> results = executor.executeQuery(form.getConnection(), selectStmt, custIdParam, companyIdParam);
        if (results.size() > 0)
        {
            return (byte[])results.get(0).getItemValue("INVOICE_DTL_FILE");
        }
        else
        {
            return null;
        }
    }

    public void updateInvoicPDF(EJForm form, int invNR, byte[] data)
    {
        EJStatementExecutor executor = new EJStatementExecutor();

        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();

        EJStatementCriteria criteria = new EJStatementCriteria();
        criteria.add(EJRestrictions.equals("ID", invNR));
        criteria.add(EJRestrictions.equals("COMPANY_ID", companyId));
        executor.executeUpdate(form, "INVOICE", criteria, new EJStatementParameter[] { new EJStatementParameter("INVOICE_FILE", Object.class, data) });
    }
    
    public void updateInvoicDtlPDF(EJForm form, int invNR, byte[] data)
    {
        EJStatementExecutor executor = new EJStatementExecutor();
        
        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
        
        EJStatementCriteria criteria = new EJStatementCriteria();
        criteria.add(EJRestrictions.equals("ID", invNR));
        criteria.add(EJRestrictions.equals("COMPANY_ID", companyId));
        executor.executeUpdate(form, "INVOICE", criteria, new EJStatementParameter[] { new EJStatementParameter("INVOICE_DTL_FILE", Object.class, data) });
    }

    public static void validateInvoicePeriod(EJForm form, Integer projectId, Integer taskId, Date periodFrom, Date periodTo)
    {
        validateInvoicePeriod(form, projectId, taskId, null, periodFrom, periodTo);
    }

    public static void validateInvoicePeriod(EJForm form, Integer projectId, Integer taskId, Integer invpId, Date periodFrom, Date periodTo)
    {
        StringBuilder selectStmt = new StringBuilder();

        selectStmt.append("select * from invoice_positions ");
        selectStmt.append("where (( ? >= period_from and ? <= period_to) or ( ? >= period_from and ? <= period_to)) ");
        selectStmt.append("and cupr_id = ? ");
        selectStmt.append("and cupt_id = ? ");

        EJStatementExecutor executor = new EJStatementExecutor();

        EJStatementParameter projectIdParam = new EJStatementParameter(EJParameterType.IN);
        projectIdParam.setValue(projectId);

        EJStatementParameter taskIdParam = new EJStatementParameter(EJParameterType.IN);
        taskIdParam.setValue(taskId);

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

            results = executor.executeQuery(form.getConnection(), selectStmt.toString(), periodFromParam, periodFromParam, periodToParam, periodToParam, projectIdParam, taskIdParam, invpIdParam);
        }
        else
        {
            results = executor.executeQuery(form.getConnection(), selectStmt.toString(), periodFromParam, periodFromParam, periodToParam, periodToParam, projectIdParam, taskIdParam);
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

    public void deleteMarkedForInvoicedPosition(EJForm form, MarkedForInvoiceProjectItem item)
    {
        EJStatementExecutor executor = new EJStatementExecutor();

        EJStatementCriteria criteria = new EJStatementCriteria();
        criteria.add(EJRestrictions.equals("ID", item.getId()));

        EJStatementParameter statusParam = new EJStatementParameter("STATUS", Integer.class);
        statusParam.setValue("APPROVED");

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
        EJStatementParameter companyIdParam = new EJStatementParameter("COMPANY_ID", Integer.class);
        companyIdParam.setValue(position.getCompanyId());
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

        executor.executeInsert(form.getConnection(), "INVOICE_POSITIONS", idParam, cuprIdParam, cuptIdParam, companyIdParam, textParam, periodFromParam, periodToParam, statusParam, projectNameParam, taskNameParam);

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

        EJStatementParameter amountParam;
        if (position.getPayRate() != null && position.getWorkHours()!=null)
        {
            amountParam = new EJStatementParameter("AMOUNT", BigDecimal.class);
            amountParam.setValue(position.getWorkHours().multiply(position.getPayRate()));
        }
        else
        {
            amountParam = new EJStatementParameter("AMOUNT", BigDecimal.class);
            amountParam.setValue(null);
        }

        EJStatementParameter fixPriceParam = new EJStatementParameter("FIX_PRICE", BigDecimal.class);
        fixPriceParam.setValue(position.getFixPrice());

        executor.executeUpdate(form, "invoice_positions", criteria, statusParam, hoursParam, payRateParam, amountParam);
    }

    public void addPositionToInvoice(EJForm form, ApprovedProjectItem position)
    {
        EJStatementExecutor executor = new EJStatementExecutor();

        EJStatementCriteria criteria = new EJStatementCriteria();
        criteria.add(EJRestrictions.equals("ID", position.getId()));

        EJStatementParameter statusParam = new EJStatementParameter("STATUS", String.class);
        statusParam.setValue("MARKED_FOR_INVOICE");

        executor.executeUpdate(form, "invoice_positions", criteria, statusParam);
    }

    public void createInvoice(EJForm form, Invoice invoice)
    {
        ArrayList<Invoice> invoices = new ArrayList<Invoice>();
        invoices.add(invoice);
        new InvoiceBlockService().executeInsert(form, invoices);

        // Now update all chosen invoice positions with the new invoice Id
        Collection<EJRecord> records = form.getBlock(F_INVOICE.B_MARKED_FOR_INVOICE_PROJECT_ITEMS.ID).getBlockRecords();

        ArrayList<MarkedForInvoiceProjectItem> items = new ArrayList<MarkedForInvoiceProjectItem>();
        for (EJRecord record : records)
        {
            MarkedForInvoiceProjectItem item = (MarkedForInvoiceProjectItem) record.getBlockServicePojo();
            item.setInvId(invoice.getId());
            item.setStatus("INVOICED");
            items.add(item);
        }

        new MarkedForInvoiceProjectItemBlockService().executeUpdate(form, items);

    }

}
