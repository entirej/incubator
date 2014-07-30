package org.entirej.ejinvoice.forms.invoice;

import java.util.List;

import org.entirej.ejinvoice.forms.constants.F_INVOICE_OVERVIEW;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;

public class OpenInvCustBlockService implements EJBlockService<OpenInvCust>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement_OPEN = new StringBuilder().append("SELECT * FROM CUSTOMER CST WHERE EXISTS (SELECT 1 ")
                                                               .append(" FROM customer_project_timeentry cpte ").append(" ,    customer_project_tasks cpt ")
                                                               .append(" ,    customer_projects cpr ").append(" WHERE cpr.ID = cpt.cpr_id ")
                                                               .append(" AND   cpt.id = cpte.cupt_id ").append(" AND   cpte.INVP_ID IS NULL ")
                                                               .append(" AND   cpr.CUSTOMER_ID = CST.ID) ").toString();

    private String                    _selectStatement_ALL = new String("SELECT * FROM CUSTOMER");

    
    public OpenInvCustBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<OpenInvCust> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        String customerChoice = (String)queryCriteria.getRestriction(F_INVOICE_OVERVIEW.B_INVOICE_CUSTOMERS.I_CUSTOMER_CHOICE).getValue();
        
        queryCriteria.removeRestriction(F_INVOICE_OVERVIEW.B_INVOICE_CUSTOMERS.I_CUSTOMER_CHOICE);
        if ("OPEN".equals(customerChoice))
        {
            return _statementExecutor.executeQuery(OpenInvCust.class, form, _selectStatement_OPEN, queryCriteria);    
        }
        else
        {
            return _statementExecutor.executeQuery(OpenInvCust.class, form, _selectStatement_ALL, queryCriteria);
        }
        
    }

    @Override
    public void executeInsert(EJForm form, List<OpenInvCust> newRecords)
    {
    }

    @Override
    public void executeUpdate(EJForm form, List<OpenInvCust> updateRecords)
    {
    }

    @Override
    public void executeDelete(EJForm form, List<OpenInvCust> recordsToDelete)
    {
    }

}