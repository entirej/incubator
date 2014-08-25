package org.entirej.ejinvoice.forms.company;

import java.util.List;

import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJParameterType;
import org.entirej.framework.core.service.EJSelectResult;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class UserService
{
    public boolean canDeleteUser(EJForm form, User user)
    {
        String selectStmt = "select count(*) as entry_count from customer_project_timeentry where user_id = ? ";

        EJStatementExecutor executor = new EJStatementExecutor();
        EJStatementParameter userIdParam = new EJStatementParameter(EJParameterType.IN);
        userIdParam.setValue(user.getId());

        List<EJSelectResult> results = executor.executeQuery(form.getConnection(), selectStmt.toString(), userIdParam);
        if (results.size() > 0)
        {
            EJSelectResult result = results.get(0);
            if (((Long)result.getItemValue("entry_count")) > 0)
            {
                return false;
            }
        }

        return true;
    }
    
    public String validatePassword(final String pwd, final String confirmPwd)
    {

        String error = null;
        if (pwd == null || ((String) pwd).trim().length() == 0)
        {
            error = ("Password cannot be Empty!");

        }

        if (pwd != null && (!pwd.equals(confirmPwd)))
        {

            error = ("Entered passwords don't match!, Please Try again.");

        }
        
        return error;
    }
}
