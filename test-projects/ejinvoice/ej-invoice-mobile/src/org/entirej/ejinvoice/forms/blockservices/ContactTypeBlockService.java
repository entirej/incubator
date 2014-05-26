/*******************************************************************************
 * Copyright 2013 Mojave Innovations GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Contributors:
 *     Mojave Innovations GmbH - initial API and implementation
 ******************************************************************************/
package org.entirej.ejinvoice.forms.blockservices;

import java.util.ArrayList;
import java.util.List;

import org.entirej.ejinvoice.ApplicationParameters;
import org.entirej.ejinvoice.forms.blockservices.pojos.ContactType;
import org.entirej.ejinvoice.forms.login.User;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.interfaces.EJFrameworkConnection;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class ContactTypeBlockService implements EJBlockService<ContactType>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT DESCRIPTION, USER_ID, ID,TYPE FROM CONTACT_TYPES";

    public ContactTypeBlockService()
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
        User usr = (User)form.getApplicationLevelParameter(ApplicationParameters.PARAM_USER).getValue();
        queryCriteria.add(EJRestrictions.equals("USER_ID", usr.getId()));
        
        return _statementExecutor.executeQuery(ContactType.class, form, _selectStatement, queryCriteria);
    }

    @Override
    public void executeInsert(EJForm form, List<ContactType> newRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;
        User usr = (User)form.getApplicationLevelParameter(ApplicationParameters.PARAM_USER).getValue();
        for (ContactType record : newRecords)
        {
            
            
            // Initialise the value list
            parameters.clear();
           
            record.setUserId(usr.getId());
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, usr.getId()));
            parameters.add(new EJStatementParameter("DESCRIPTION", String.class, record.getDescription()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("TYPE", String.class, record.getType()));
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "CONTACT_TYPES", parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != newRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: " + newRecords.size() + ". Inserted: "
                    + recordsProcessed);
        }
    }

    @Override
    public void executeUpdate(EJForm form, List<ContactType> updateRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (ContactType record : updateRecords)
        {
            parameters.clear();

            // First add the new values
            parameters.add(new EJStatementParameter("DESCRIPTION", String.class, record.getDescription()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("TYPE", String.class, record.getType()));

            EJStatementCriteria criteria = new EJStatementCriteria();
            
            criteria.add(EJRestrictions.equals("USER_ID", record.getInitialUserId()));
            
            if (record.getInitialDescription() == null)
            {
                criteria.add(EJRestrictions.isNull("DESCRIPTION"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("DESCRIPTION", record.getInitialDescription()));
            }
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialType() == null)
            {
                criteria.add(EJRestrictions.isNull("TYPE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("TYPE", record.getInitialType()));
            }
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeUpdate(form, "CONTACT_TYPES", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != updateRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in update. Expected: " + updateRecords.size() + ". Updated: "
                    + recordsProcessed);
        }
    }

    @Override
    public void executeDelete(EJForm form, List<ContactType> recordsToDelete)
    {
        ArrayList<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (ContactType record : recordsToDelete)
        {
            parameters.clear();

            EJStatementCriteria criteria = new EJStatementCriteria();

            criteria.add(EJRestrictions.equals("USER_ID", record.getInitialUserId()));
            
            if (record.getInitialDescription() == null)
            {
                criteria.add(EJRestrictions.isNull("DESCRIPTION"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("DESCRIPTION", record.getInitialDescription()));
            }
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialType() == null)
            {
                criteria.add(EJRestrictions.isNull("TYPE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("TYPE", record.getInitialType()));
            }
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeDelete(form, "CONTACT_TYPES", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != recordsToDelete.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in delete. Expected: " + recordsToDelete.size() + ". Deleted: "
                    + recordsProcessed);
        }
    }
    
    
    
    public List<ContactType> getContactTypes( EJFrameworkConnection fwkConnection, EJQueryCriteria queryCriteria)
    {
        return _statementExecutor.executeQuery(ContactType.class, fwkConnection, _selectStatement, queryCriteria);
    }
    
    
    
    public void insertContactTypes( EJFrameworkConnection fwkConnection, List<ContactType> newRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;
        for (ContactType record : newRecords)
        {
            // Initialise the value list
            parameters.clear();
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, record.getUserId()));
            parameters.add(new EJStatementParameter("DESCRIPTION", String.class, record.getDescription()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("TYPE", String.class, record.getType()));
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(fwkConnection, "CONTACT_TYPES", parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != newRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: " + newRecords.size() + ". Inserted: "
                    + recordsProcessed);
        }
    }

}
