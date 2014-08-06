package org.entirej.ejinvoice.forms.timeentry;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class TimeEntryProjectBlockService implements EJBlockService<TimeEntryProject>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT VAT_ID, CUSTOMER_ID,DESCRIPTION,END_DATE,ID,NAME,NOTES,START_DATE,STATUS,USER_ID, INVOICEABLE, FIX_PRICE, CCY_ID, (SELECT CODE FROM CURRENCIES WHERE ID = CCY_ID) CCY_CODE FROM customer_projects";

    public TimeEntryProjectBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<TimeEntryProject> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        return _statementExecutor.executeQuery(TimeEntryProject.class, form, _selectStatement, queryCriteria);
    }

    @Override
    public void executeInsert(EJForm form, List<TimeEntryProject> newRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;
        for (TimeEntryProject record : newRecords)
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
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, record.getUserId()));
            parameters.add(new EJStatementParameter("INVOICEABLE", String.class, record.getInvoiceable()));
            parameters.add(new EJStatementParameter("FIX_PRICE", BigDecimal.class, record.getFixPrice()));
            parameters.add(new EJStatementParameter("CCY_ID", Integer.class, record.getCcyId()));
            parameters.add(new EJStatementParameter("VAT_ID", String.class, record.getVatId()));
            
            
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "customer_projects", parameters.toArray(paramArray));
        
            // Initialise the value list
            Integer taskId = PKSequenceService.getPKSequence(form.getConnection());
            parameters.clear();
            parameters.add(new EJStatementParameter("CPR_ID", Integer.class,  record.getId()));
            parameters.add(new EJStatementParameter("ID", Integer.class, taskId));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getTaskName()));
            parameters.add(new EJStatementParameter("NOTES", String.class, record.getTaskNotes()));
            parameters.add(new EJStatementParameter("PAY_RATE", BigDecimal.class, record.getTaskPayRate()));
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, record.getUserId()));
            
            parameters.add(new EJStatementParameter("FIX_PRICE", Integer.class, record.getTaskFixPrice()));
            parameters.add(new EJStatementParameter("STATUS", Integer.class, record.getTaskStatus()));
            parameters.add(new EJStatementParameter("INVOICEABLE", Integer.class, record.getTaskInvoiceable()));
            
            paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "customer_project_tasks", parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        
        if (recordsProcessed != (newRecords.size()*2))
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: " + newRecords.size() + ". Inserted: "
                    + recordsProcessed);
        }
    }

    @Override
    public void executeUpdate(EJForm form, List<TimeEntryProject> updateRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (TimeEntryProject record : updateRecords)
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
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, record.getUserId()));
            
            parameters.add(new EJStatementParameter("INVOICEABLE", String.class, record.getInvoiceable()));
            parameters.add(new EJStatementParameter("FIX_PRICE", BigDecimal.class, record.getFixPrice()));
            parameters.add(new EJStatementParameter("CCY_ID", Integer.class, record.getCcyId()));
            parameters.add(new EJStatementParameter("CCY_CODE", String.class, record.getCcyCode()));
            parameters.add(new EJStatementParameter("VAT_ID", Integer.class, record.getVatId()));

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
            if (record.getInitialUserId() == null)
            {
                criteria.add(EJRestrictions.isNull("USER_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("USER_ID", record.getInitialUserId()));
            }
            if (record.getInitialInvoiceable() == null)
            {
                criteria.add(EJRestrictions.isNull("INVOICEABLE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("INVOICABLE", record.getInitialInvoiceable()));
            }
            if (record.getInitialFixPrice() == null)
            {
                criteria.add(EJRestrictions.isNull("FIX_PRICE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("FIX_PRICE", record.getInitialFixPrice()));
            }
            if (record.getInitialCcyId() == null)
            {
                criteria.add(EJRestrictions.isNull("CCY_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CCY_ID", record.getInitialCcyId()));
            }
            if (record.getInitialCcyCode() == null)
            {
                criteria.add(EJRestrictions.isNull("CCY_CODE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CCY_CODE", record.getInitialCcyCode()));
            }
            if (record.getInitialVatId() == null)
            {
                criteria.add(EJRestrictions.isNull("VAT_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("VAT_ID", record.getInitialVatId()));
            }
            
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeUpdate(form, "customer_projects", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != updateRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in update. Expected: " + updateRecords.size() + ". Updated: "
                    + recordsProcessed);
        }
    }

    @Override
    public void executeDelete(EJForm form, List<TimeEntryProject> recordsToDelete)
    {
        ArrayList<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (TimeEntryProject record : recordsToDelete)
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
            if (record.getInitialUserId() == null)
            {
                criteria.add(EJRestrictions.isNull("USER_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("USER_ID", record.getInitialUserId()));
            }
            if (record.getInitialInvoiceable() == null)
            {
                criteria.add(EJRestrictions.isNull("INVOICEABLE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("INVOICABLE", record.getInitialInvoiceable()));
            }
            if (record.getInitialFixPrice() == null)
            {
                criteria.add(EJRestrictions.isNull("FIX_PRICE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("FIX_PRICE", record.getInitialFixPrice()));
            }
            if (record.getInitialCcyId() == null)
            {
                criteria.add(EJRestrictions.isNull("CCY_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CCY_ID", record.getInitialCcyId()));
            }
            if (record.getInitialCcyCode() == null)
            {
                criteria.add(EJRestrictions.isNull("CCY_CODE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CCY_CODE", record.getInitialCcyCode()));
            }
            if (record.getInitialVatId() == null)
            {
                criteria.add(EJRestrictions.isNull("VAT_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("VAT_ID", record.getInitialVatId()));
            }
            
            
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeDelete(form, "customer_projects", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != recordsToDelete.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in delete. Expected: " + recordsToDelete.size() + ". Deleted: "
                    + recordsProcessed);
        }
    }

}