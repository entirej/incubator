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
import org.entirej.ejinvoice.forms.blockservices.pojos.PaymentTerm;
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

public class PaymentTermBlockService implements EJBlockService<PaymentTerm>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT ID, USER_ID, PAYMENT_TERMS FROM PAYMENT_INFORMATION";

    public PaymentTermBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<PaymentTerm> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        User usr = (User)form.getApplicationLevelParameter(ApplicationParameters.PARAM_USER).getValue();
        queryCriteria.add(EJRestrictions.equals("USER_ID", usr.getId()));

        return _statementExecutor.executeQuery(PaymentTerm.class, form, _selectStatement, queryCriteria);
    }

    @Override
    public void executeInsert(EJForm form, List<PaymentTerm> newRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;  
        User usr = (User)form.getApplicationLevelParameter(ApplicationParameters.PARAM_USER).getValue();
        for (PaymentTerm record : newRecords)
        {
            // Initialise the value list
            parameters.clear();
            record.setUserId(usr.getId());
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, usr.getId()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("PAYMENT_TERMS", String.class, record.getPaymentTerms()));
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "PAYMENT_INFORMATION", parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != newRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: " + newRecords.size() + ". Inserted: "
                    + recordsProcessed);
        }
    }

    @Override
    public void executeUpdate(EJForm form, List<PaymentTerm> updateRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (PaymentTerm record : updateRecords)
        {
            parameters.clear();

            // First add the new values
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("PAYMENT_TERMS", String.class, record.getPaymentTerms()));

            EJStatementCriteria criteria = new EJStatementCriteria();
            criteria.add(EJRestrictions.equals("USER_ID", record.getInitialUserId()));
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialPaymentTerms() == null)
            {
                criteria.add(EJRestrictions.isNull("PAYMENT_TERMS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PAYMENT_TERMS", record.getInitialPaymentTerms()));
            }
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeUpdate(form, "PAYMENT_INFORMATION", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != updateRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in update. Expected: " + updateRecords.size() + ". Updated: "
                    + recordsProcessed);
        }
    }

    @Override
    public void executeDelete(EJForm form, List<PaymentTerm> recordsToDelete)
    {
        ArrayList<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (PaymentTerm record : recordsToDelete)
        {
            parameters.clear();

            EJStatementCriteria criteria = new EJStatementCriteria();
            criteria.add(EJRestrictions.equals("USER_ID", record.getInitialUserId()));
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            if (record.getInitialPaymentTerms() == null)
            {
                criteria.add(EJRestrictions.isNull("PAYMENT_TERMS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PAYMENT_TERMS", record.getInitialPaymentTerms()));
            }
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeDelete(form, "PAYMENT_INFORMATION", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != recordsToDelete.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in delete. Expected: " + recordsToDelete.size() + ". Deleted: "
                    + recordsProcessed);
        }
    }
    
    
    
    
    public List<PaymentTerm> getPaymentTerms(EJFrameworkConnection fwkConnection, EJQueryCriteria queryCriteria)
    {
       
        
        return _statementExecutor.executeQuery(PaymentTerm.class, fwkConnection, _selectStatement, queryCriteria);
    }


    
    public void insertPaymentTerms(EJFrameworkConnection fwkConnection, List<PaymentTerm> newRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;
        for (PaymentTerm record : newRecords)
        {
            // Initialise the value list
            parameters.clear();
            parameters.add(new EJStatementParameter("USER_ID", Integer.class, record.getUserId()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("PAYMENT_TERMS", String.class, record.getPaymentTerms()));
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(fwkConnection, "PAYMENT_INFORMATION", parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != newRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: " + newRecords.size() + ". Inserted: "
                    + recordsProcessed);
        }
    }

}
