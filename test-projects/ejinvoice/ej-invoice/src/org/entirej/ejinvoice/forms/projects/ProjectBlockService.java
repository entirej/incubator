package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.forms.company.User;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class ProjectBlockService implements EJBlockService<Project>
{
    private final EJStatementExecutor _statementExecutor;
    private StringBuilder             _selectStatement = new StringBuilder();

    public ProjectBlockService()
    {
        _statementExecutor = new EJStatementExecutor();

        _selectStatement.append("SELECT CUSTOMER_ID ");
        _selectStatement.append(",      (SELECT NAME FROM CUSTOMER WHERE ID = CPR1.CUSTOMER_ID) AS CUSTOMER_NAME ");
        _selectStatement.append(",      DESCRIPTION ");
        _selectStatement.append(",      END_DATE ");
        _selectStatement.append(",      ID ");
        _selectStatement.append(",      NAME ");
        _selectStatement.append(",      NOTES ");
        _selectStatement.append(",      START_DATE ");
        _selectStatement.append(",      STATUS ");
        _selectStatement.append(",      COMPANY_ID ");
        _selectStatement.append(",      INVOICEABLE ");
        _selectStatement.append(",      FIX_PRICE ");
        _selectStatement.append(",      BOOKABLE_HOURS ");
        _selectStatement.append(",      MAXIMUM_HOURS ");
        _selectStatement.append(",      (select count(*) ");
        _selectStatement.append("        from   customer_project_timeentry cpte ");
        _selectStatement.append("        ,      customer_project_tasks cupt ");
        _selectStatement.append("        where  cupt.id     = cpte.cupt_id ");
        _selectStatement.append("        and    cupt.cpr_id = cpr1.id ");
        _selectStatement.append("        and not exists (select id ");
        _selectStatement.append("                        from invoice_positions invp ");
        _selectStatement.append("                        where cupt.cpr_id = invp.cupr_id ");
        _selectStatement.append("                        and   cupt.id = invp.cupt_id ");
        _selectStatement.append("                        and   cpte.work_date between invp.period_from and invp.period_to)) OPEN_ITEMS ");
        _selectStatement.append(",      (select count(*) ");
        _selectStatement.append("        from   customer_project_timeentry cpte ");
        _selectStatement.append("        ,      customer_project_tasks cupt ");
        _selectStatement.append("                where  cupt.id     = cpte.cupt_id ");
        _selectStatement.append("        and    cupt.cpr_id = cpr1.id ");
        _selectStatement.append("        and exists (select id ");
        _selectStatement.append("                    from invoice_positions invp ");
        _selectStatement.append("                                where cupt.cpr_id = invp.cupr_id ");
        _selectStatement.append("                                and   cupt.id = invp.cupt_id ");
        _selectStatement.append("                    and   cpte.work_date between invp.period_from and invp.period_to ");
        _selectStatement.append("                    and   invp.status = 'PLANNED')) PLANNED_ITEMS ");
        _selectStatement.append(",      (select count(*) ");
        _selectStatement.append("        from   customer_project_timeentry cpte ");
        _selectStatement.append("        ,      customer_project_tasks cupt ");
        _selectStatement.append("                where  cupt.id     = cpte.cupt_id ");
        _selectStatement.append("        and    cupt.cpr_id = cpr1.id ");
        _selectStatement.append("        and exists (select id ");
        _selectStatement.append("                    from invoice_positions invp ");
        _selectStatement.append("                                where cupt.cpr_id = invp.cupr_id ");
        _selectStatement.append("                                and   cupt.id = invp.cupt_id ");
        _selectStatement.append("                    and   cpte.work_date between invp.period_from and invp.period_to ");
        _selectStatement.append("                    and   invp.status = 'APPROVED')) APPROVED_ITEMS ");
        _selectStatement.append(",      (select count(*) ");
        _selectStatement.append("        from   customer_project_timeentry cpte ");
        _selectStatement.append("        ,      customer_project_tasks cupt  ");
        _selectStatement.append("                ,      invoice_positions invp ");
        _selectStatement.append("        where  cupt.id      = cpte.cupt_id  ");
        _selectStatement.append("        and    invp.cupr_id = cupt.cpr_id ");
        _selectStatement.append("        and    invp.cupt_id = cupt.id ");
        _selectStatement.append("        and    cpte.invp_id = invp.id ");
        _selectStatement.append("        and    cupt.cpr_id = cpr1.id ");
        _selectStatement.append("        and   invp.status = 'MARKED_FOR_INVOICE') MARKED_FOR_INVOICE_ITEMS ");
        _selectStatement.append("FROM   customer_projects cpr1 ");

    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<Project> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        User user = (User)form.getApplicationLevelParameter(EJ_PROPERTIES.P_USER).getValue();
        
        List<Project> projects = _statementExecutor.executeQuery(Project.class, form, _selectStatement.toString(), queryCriteria);
        for (Project project : projects)
        {
            project.setLocale(user.getLocale());
        }
        
        return projects;
    }

    @Override
    public void executeInsert(EJForm form, List<Project> newRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;
        for (Project record : newRecords)
        {
            // Initialise the value list
            parameters.clear();
            parameters.add(new EJStatementParameter("CUSTOMER_ID", Integer.class, record.getCustomerId()));
            parameters.add(new EJStatementParameter("DESCRIPTION", String.class, record.getDescription()));
            parameters.add(new EJStatementParameter("END_DATE", Date.class, record.getEndDate()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getName()));
            parameters.add(new EJStatementParameter("NOTES", String.class, record.getNotes()));
            parameters.add(new EJStatementParameter("START_DATE", Date.class, record.getStartDate()));
            parameters.add(new EJStatementParameter("STATUS", String.class, record.getStatus()));
            parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, record.getCompanyId()));
            parameters.add(new EJStatementParameter("INVOICEABLE", String.class, record.getInvoiceable()));
            parameters.add(new EJStatementParameter("FIX_PRICE", BigDecimal.class, record.getFixPrice()));
            parameters.add(new EJStatementParameter("MAXIMUM_HOURS", BigDecimal.class, record.getMaximumHours()));
            parameters.add(new EJStatementParameter("BOOKABLE_HOURS", BigDecimal.class, record.getBookableHours()));

            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "customer_projects", parameters.toArray(paramArray));

            // Initialise the value list
            Integer taskId = PKSequenceService.getPKSequence(form.getConnection());
            parameters.clear();
            parameters.add(new EJStatementParameter("CPR_ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("ID", Integer.class, taskId));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getTaskName()));
            parameters.add(new EJStatementParameter("NOTES", String.class, record.getTaskNotes()));
            parameters.add(new EJStatementParameter("PAY_RATE", BigDecimal.class, record.getTaskPayRate()));
            parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, record.getCompanyId()));

            parameters.add(new EJStatementParameter("FIX_PRICE", Integer.class, record.getTaskFixPrice()));
            parameters.add(new EJStatementParameter("STATUS", Integer.class, record.getTaskStatus()));
            parameters.add(new EJStatementParameter("INVOICEABLE", Integer.class, record.getTaskInvoiceable()));

            paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "customer_project_tasks", parameters.toArray(paramArray));
            record.clearInitialValues();
        }

        if (recordsProcessed != (newRecords.size() * 2))
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: " + newRecords.size() + ". Inserted: " + recordsProcessed);
        }
    }

    public void registerProject(EJForm form, List<Project> newRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;
        for (Project record : newRecords)
        {
            // Initialise the value list
            parameters.clear();
            parameters.add(new EJStatementParameter("CUSTOMER_ID", Integer.class, record.getCustomerId()));
            parameters.add(new EJStatementParameter("DESCRIPTION", String.class, record.getDescription()));
            parameters.add(new EJStatementParameter("END_DATE", Date.class, record.getEndDate()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getName()));
            parameters.add(new EJStatementParameter("NOTES", String.class, record.getNotes()));
            parameters.add(new EJStatementParameter("START_DATE", Date.class, record.getStartDate()));
            parameters.add(new EJStatementParameter("STATUS", String.class, record.getStatus()));
            parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, record.getCompanyId()));
            parameters.add(new EJStatementParameter("INVOICEABLE", String.class, record.getInvoiceable()));
            parameters.add(new EJStatementParameter("FIX_PRICE", BigDecimal.class, record.getFixPrice()));
            parameters.add(new EJStatementParameter("MAXIMUM_HOURS", BigDecimal.class, record.getMaximumHours()));
            parameters.add(new EJStatementParameter("BOOKABLE_HOURS", BigDecimal.class, record.getBookableHours()));

            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "customer_projects", parameters.toArray(paramArray));
        }

        if (recordsProcessed != newRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: " + newRecords.size() + ". Inserted: " + recordsProcessed);
        }
    }
    
    @Override
    public void executeUpdate(EJForm form, List<Project> updateRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (Project record : updateRecords)
        {
            parameters.clear();

            // First add the new values
            parameters.add(new EJStatementParameter("CUSTOMER_ID", Integer.class, record.getCustomerId()));
            parameters.add(new EJStatementParameter("DESCRIPTION", String.class, record.getDescription()));
            parameters.add(new EJStatementParameter("END_DATE", Date.class, record.getEndDate()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getName()));
            parameters.add(new EJStatementParameter("NOTES", String.class, record.getNotes()));
            parameters.add(new EJStatementParameter("START_DATE", Date.class, record.getStartDate()));
            parameters.add(new EJStatementParameter("STATUS", String.class, record.getStatus()));
            parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, record.getCompanyId()));

            parameters.add(new EJStatementParameter("INVOICEABLE", String.class, record.getInvoiceable()));
            parameters.add(new EJStatementParameter("FIX_PRICE", BigDecimal.class, record.getFixPrice()));
            parameters.add(new EJStatementParameter("MAXIMUM_HOURS", BigDecimal.class, record.getMaximumHours()));
            parameters.add(new EJStatementParameter("BOOKABLE_HOURS", BigDecimal.class, record.getBookableHours()));

            EJStatementCriteria criteria = new EJStatementCriteria();
            if (record.getInitialCustomerId() == null)
            {
                criteria.add(EJRestrictions.isNull("CUSTOMER_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CUSTOMER_ID", record.getInitialCustomerId()));
            }
            if (record.getInitialDescription() == null)
            {
                criteria.add(EJRestrictions.isNull("DESCRIPTION"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("DESCRIPTION", record.getInitialDescription()));
            }
            if (record.getInitialEndDate() == null)
            {
                criteria.add(EJRestrictions.isNull("END_DATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("END_DATE", record.getInitialEndDate()));
            }
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialName() == null)
            {
                criteria.add(EJRestrictions.isNull("NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NAME", record.getInitialName()));
            }
            if (record.getInitialNotes() == null)
            {
                criteria.add(EJRestrictions.isNull("NOTES"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NOTES", record.getInitialNotes()));
            }
            if (record.getInitialStartDate() == null)
            {
                criteria.add(EJRestrictions.isNull("START_DATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("START_DATE", record.getInitialStartDate()));
            }
            if (record.getInitialStatus() == null)
            {
                criteria.add(EJRestrictions.isNull("STATUS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("STATUS", record.getInitialStatus()));
            }
            if (record.getInitialCompanyId() == null)
            {
                criteria.add(EJRestrictions.isNull("COMPANY_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("COMPANY_ID", record.getInitialCompanyId()));
            }
            if (record.getInitialInvoiceable() == null)
            {
                criteria.add(EJRestrictions.isNull("INVOICEABLE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("INVOICEABLE", record.getInitialInvoiceable()));
            }
            if (record.getInitialFixPrice() == null)
            {
                criteria.add(EJRestrictions.isNull("FIX_PRICE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("FIX_PRICE", record.getInitialFixPrice()));
            }
            if (record.getInitialBookableHours() == null)
            {
                criteria.add(EJRestrictions.isNull("BOOKABLE_HOURS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("BOOKABLE_HOURS", record.getInitialBookableHours()));
            }
            if (record.getInitialMaximumHours() == null)
            {
                criteria.add(EJRestrictions.isNull("MAXIMUM_HOURS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("MAXIMUM_HOURS", record.getInitialMaximumHours()));
            }

            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeUpdate(form, "customer_projects", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != updateRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in update. Expected: " + updateRecords.size() + ". Updated: " + recordsProcessed);
        }
    }

    @Override
    public void executeDelete(EJForm form, List<Project> recordsToDelete)
    {
        ArrayList<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (Project record : recordsToDelete)
        {
            parameters.clear();

            EJStatementCriteria criteria = new EJStatementCriteria();

            if (record.getInitialCustomerId() == null)
            {
                criteria.add(EJRestrictions.isNull("CUSTOMER_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CUSTOMER_ID", record.getInitialCustomerId()));
            }
            if (record.getInitialDescription() == null)
            {
                criteria.add(EJRestrictions.isNull("DESCRIPTION"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("DESCRIPTION", record.getInitialDescription()));
            }
            if (record.getInitialEndDate() == null)
            {
                criteria.add(EJRestrictions.isNull("END_DATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("END_DATE", record.getInitialEndDate()));
            }
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialName() == null)
            {
                criteria.add(EJRestrictions.isNull("NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NAME", record.getInitialName()));
            }
            if (record.getInitialNotes() == null)
            {
                criteria.add(EJRestrictions.isNull("NOTES"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NOTES", record.getInitialNotes()));
            }
            if (record.getInitialStartDate() == null)
            {
                criteria.add(EJRestrictions.isNull("START_DATE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("START_DATE", record.getInitialStartDate()));
            }
            if (record.getInitialStatus() == null)
            {
                criteria.add(EJRestrictions.isNull("STATUS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("STATUS", record.getInitialStatus()));
            }
            if (record.getInitialCompanyId() == null)
            {
                criteria.add(EJRestrictions.isNull("COMPANY_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("COMPANY_ID", record.getInitialCompanyId()));
            }
            if (record.getInitialInvoiceable() == null)
            {
                criteria.add(EJRestrictions.isNull("INVOICEABLE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("INVOICEABLE", record.getInitialInvoiceable()));
            }
            if (record.getInitialFixPrice() == null)
            {
                criteria.add(EJRestrictions.isNull("FIX_PRICE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("FIX_PRICE", record.getInitialFixPrice()));
            }
            if (record.getInitialBookableHours() == null)
            {
                criteria.add(EJRestrictions.isNull("BOOKABLE_HOURS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("BOOKABLE_HOURS", record.getInitialBookableHours()));
            }
            if (record.getInitialMaximumHours() == null)
            {
                criteria.add(EJRestrictions.isNull("MAXIMUM_HOURS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("MAXIMUM_HOURS", record.getInitialMaximumHours()));
            }


            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeDelete(form, "customer_projects", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != recordsToDelete.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in delete. Expected: " + recordsToDelete.size() + ". Deleted: " + recordsProcessed);
        }
    }

}