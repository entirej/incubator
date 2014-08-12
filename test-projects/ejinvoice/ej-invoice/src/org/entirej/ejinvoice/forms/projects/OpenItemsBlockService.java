package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJSelectResult;
import org.entirej.framework.core.service.EJStatementExecutor;

public class OpenItemsBlockService implements EJBlockService<OpenItem>
{
    private final EJStatementExecutor _statementExecutor;
    private StringBuilder             _selectStatement = new StringBuilder();

    public OpenItemsBlockService()
    {
        _statementExecutor = new EJStatementExecutor();

        _selectStatement.append("SELECT  CUST.ID    CUSTOMER_ID ");
        _selectStatement.append(",       CUST.NAME CUSTOMER_NAME ");
        _selectStatement.append(",       CPR.NAME AS PROJECT_NAME ");
        _selectStatement.append(",       CUPT.ID AS TASK_ID ");
        _selectStatement.append(",       CUPT.CPR_ID AS PROJECT_ID ");
        _selectStatement.append(",       CUPT.NAME AS TASK_NAME ");
        _selectStatement.append("FROM  customer_project_tasks AS CUPT ");
        _selectStatement.append(",     customer_projects AS CPR ");
        _selectStatement.append(",     customer cust ");
        _selectStatement.append("WHERE CUST.ID      = CPR.CUSTOMER_ID ");
        _selectStatement.append("AND   CUPT.CPR_ID  = CPR.ID ");
        _selectStatement.append("AND   CPR.INVP_ID  IS NULL ");
        _selectStatement.append("AND EXISTS (SELECT 1 FROM INVOICE_POSITIONS INVP, customer_project_timeentry CPTE WHERE CPTE.CUPT_ID = CUPT.ID AND CPTE.WORK_DATE BETWEEN INVP.PERIOD_FROM AND INVP.PERIOD_TO) ");
        _selectStatement.append("ORDER BY CUSTOMER_NAME, PROJECT_NAME, TASK_NAME ");
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<OpenItem> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        Integer customerId = -1;
        Integer projectId = -1;
        int recNum = 0;

        ArrayList<OpenItem> openItems = new ArrayList<OpenItem>();

        List<EJSelectResult> results = _statementExecutor.executeQuery(form, _selectStatement.toString());

        for (EJSelectResult result : results)
        {
            if (!customerId.equals(result.getItemValue("CUSTOMER_ID")))
            {
                if (customerId != -1)
                {
                    OpenItem item = new OpenItem();
                    item.setHeaderNumber(-1);
                    openItems.add(item);
                }
                
                OpenItem item = new OpenItem();
                item.setHeaderNumber(1);
                item.setDisplayValue((String) result.getItemValue("CUSTOMER_NAME"));
                item.setCustomerId((Integer) result.getItemValue("CUSTOMER_ID"));
                item.setCustomerName((String) result.getItemValue("CUSTOMER_NAME"));

                openItems.add(item);
                customerId = item.getCustomerId();
                projectId = -1;
            }

            if (!projectId.equals(result.getItemValue("PROJECT_ID")))
            {
                OpenItem item = new OpenItem();
                item.setHeaderNumber(2);
//                item.setDisplayValue("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + (String) result.getItemValue("PROJECT_NAME"));
                item.setDisplayValue((String) result.getItemValue("PROJECT_NAME"));
                item.setProjectId((Integer) result.getItemValue("PROJECT_ID"));
                item.setProjectName((String) result.getItemValue("PROJECT_NAME"));

                openItems.add(item);
                projectId = item.getProjectId();
                recNum = 1;
            }

            
            OpenItem item = new OpenItem();
            if ( (recNum & 1) == 0 )
            {
                // Even
                item.setHeaderNumber(4);
            }
            else
            {
                // Odd
                item.setHeaderNumber(5);
            }
            
            item.setDisplayValue((String) result.getItemValue("TASK_NAME"));
//            item.setDisplayValue("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + (String) result.getItemValue("TASK_NAME"));
            item.setCustomerId((Integer) result.getItemValue("CUSTOMER_ID"));
            item.setCustomerName((String) result.getItemValue("CUSTOMER_NAME"));
            item.setProjectId((Integer) result.getItemValue("PROJECT_ID"));
            item.setProjectName((String) result.getItemValue("PROJECT_NAME"));
            item.setTaskId((Integer) result.getItemValue("TASK_ID"));
            item.setTaskName((String) result.getItemValue("TASK_NAME"));

            openItems.add(item);
            
            recNum++;
        }

        return openItems;
    }

    @Override
    public void executeInsert(EJForm form, List<OpenItem> newRecords)
    {
    }

    @Override
    public void executeUpdate(EJForm form, List<OpenItem> updateRecords)
    {
    }

    @Override
    public void executeDelete(EJForm form, List<OpenItem> recordsToDelete)
    {
    }

}