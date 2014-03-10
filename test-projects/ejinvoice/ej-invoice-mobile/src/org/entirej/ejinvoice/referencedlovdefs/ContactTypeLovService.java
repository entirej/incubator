package org.entirej.ejinvoice.referencedlovdefs;

import java.util.ArrayList;
import java.util.List;

import org.entirej.ejinvoice.ApplicationParameters;
import org.entirej.ejinvoice.forms.constants.F_CUSTOMER;
import org.entirej.ejinvoice.forms.login.User;
import org.entirej.ejinvoice.referencedlovdefs.ContactType;
import org.entirej.ejinvoice.referencedlovdefs.constants.F_CONTACT_TYPES;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class ContactTypeLovService implements EJBlockService<ContactType>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT DESCRIPTION,ID,TYPE,USER_ID FROM contact_types";

    public ContactTypeLovService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<ContactType> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        User user = (User)form.getApplicationLevelParameter(ApplicationParameters.PARAM_USER).getValue();
        queryCriteria.add(EJRestrictions.equals("USER_ID", user.getId()));
        return _statementExecutor.executeQuery(ContactType.class, form, _selectStatement, queryCriteria);
    }

    @Override
    public void executeInsert(EJForm form, List<ContactType> newRecords)
    {
    }

    @Override
    public void executeUpdate(EJForm form, List<ContactType> updateRecords)
    {
    }

    @Override
    public void executeDelete(EJForm form, List<ContactType> recordsToDelete)
    {
    }

}