package org.entirej.ejinvoice.email;

import java.util.List;

import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementExecutor;

 class EmailConfig
{
    private String selectStatement = "Select ID, TYPE, ADDRESS, PASSWORD, URL from email_configuration ";

    public EmailInfo getDefaultEmailConfig(EJForm form)
    {
        EJStatementExecutor _statementExecutor = new EJStatementExecutor();
        EJQueryCriteria queryCriteria = new EJQueryCriteria();
        queryCriteria.add(EJRestrictions.equals("TYPE", "DEFAULT"));

        Class<?> em = EmailInfo.class;
        
        List<EmailInfo> executeQuery = _statementExecutor.executeQuery(EmailInfo.class, form, selectStatement, queryCriteria);

        if (executeQuery != null && executeQuery.size() > 0)
        {
            return executeQuery.get(0);
        }
        return null;
    }

}