package org.entirej.ejinvoice.referencedlovdefs;

import java.util.List;

import org.entirej.ejinvoice.ApplicationParameters;
import org.entirej.ejinvoice.forms.login.User;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementExecutor;

public class SalutationLovService implements EJBlockService<Salutation>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT ID,USER_ID,VALUE FROM salutations";

    public SalutationLovService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<Salutation> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
       User user = (User)form.getApplicationLevelParameter(ApplicationParameters.PARAM_USER).getValue();
       queryCriteria.add(EJRestrictions.equals("USER_ID", user.getId()));
        return _statementExecutor.executeQuery(Salutation.class, form, _selectStatement, queryCriteria);
    }

    @Override
    public void executeInsert(EJForm form, List<Salutation> newRecords)
    {
    }

    @Override
    public void executeUpdate(EJForm form, List<Salutation> updateRecords)
    {
    }

    @Override
    public void executeDelete(EJForm form, List<Salutation> recordsToDelete)
    {
    }

}