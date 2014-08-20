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
package org.entirej.ejinvoice.forms.timeentry;

import java.util.ArrayList;
import java.util.List;

import org.entirej.ejinvoice.ApplicationParameters;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.forms.login.User;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class TimeEntryCustomerBlockService implements EJBlockService<TimeEntryCustomer>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT COMPANY_ID, CUSTOMER_NUMBER, ADDRESS,ID,NAME,POST_CODE,TOWN, COUNTRY, PAYMENT_DAYS, CCY_ID, (SELECT CODE FROM CURRENCIES WHERE ID = CCY_ID) AS CCY_CODE, VAT_ID, (SELECT RATE FROM VAT_RATES WHERE ID = VAT_ID) AS VAT_RATE FROM CUSTOMER";

    public TimeEntryCustomerBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<TimeEntryCustomer> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        return _statementExecutor.executeQuery(TimeEntryCustomer.class, form, _selectStatement, queryCriteria);
    }

    @Override
    public void executeInsert(EJForm form, List<TimeEntryCustomer> newRecords)
    {
        User usr = (User)form.getApplicationLevelParameter(ApplicationParameters.PARAM_USER).getValue();

        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;
        int custContactRecordsProcessed = 0;
        
        for (TimeEntryCustomer record : newRecords)
        {
            // Initialise the value list
            parameters.clear();
            
            parameters.add(new EJStatementParameter("CUSTOMER_NUMBER", String.class, record.getCustomerNumber()));
            parameters.add(new EJStatementParameter("ADDRESS", String.class, record.getAddress()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, usr.getCompanyId()));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getName()));
            parameters.add(new EJStatementParameter("POST_CODE", String.class, record.getPostCode()));
            parameters.add(new EJStatementParameter("TOWN", String.class, record.getTown()));
            parameters.add(new EJStatementParameter("COUNTRY", String.class, record.getCountry()));
            parameters.add(new EJStatementParameter("VAT_ID", Integer.class, record.getVatId()));
            parameters.add(new EJStatementParameter("CCY_ID", Integer.class, record.getCcyId()));
            parameters.add(new EJStatementParameter("PAYMENT_DAYS", Integer.class, record.getPaymentDays()));
            
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "CUSTOMER", parameters.toArray(paramArray));
            record.clearInitialValues();
            
            //now insert the default contact
            Integer custContactId = PKSequenceService.getPKSequence(form.getConnection());
            parameters.clear();
            parameters.add(new EJStatementParameter("CONTACT_TYPES_ID", Integer.class, record.getContactTypesId()));
            parameters.add(new EJStatementParameter("CUSTOMER_ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("EMAIL", String.class, record.getEmail()));
            parameters.add(new EJStatementParameter("FIRST_NAME", String.class, record.getFirstName()));
            parameters.add(new EJStatementParameter("ID", Integer.class, custContactId));
            parameters.add(new EJStatementParameter("LAST_NAME", String.class, record.getLastName()));
            parameters.add(new EJStatementParameter("MOBILE", String.class, record.getMobile()));
            parameters.add(new EJStatementParameter("PHONE", String.class, record.getPhone()));
            parameters.add(new EJStatementParameter("SALUTATIONS_ID", Integer.class, record.getSalutationsId()));
            parameters.add(new EJStatementParameter("CONPANY_ID", Integer.class, usr.getCompanyId()));
            custContactRecordsProcessed += _statementExecutor.executeInsert(form, "customer_contact", parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != newRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: " + newRecords.size() + ". Inserted: "
                    + recordsProcessed);
        }

    }

    @Override
    public void executeUpdate(EJForm form, List<TimeEntryCustomer> updateRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (TimeEntryCustomer record : updateRecords)
        {
            parameters.clear();

            // First add the new values
            parameters.add(new EJStatementParameter("CUSTOMER_NUMBER", String.class, record.getCustomerNumber()));
            parameters.add(new EJStatementParameter("ADDRESS", String.class, record.getAddress()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, record.getCompanyId()));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getName()));
            parameters.add(new EJStatementParameter("POST_CODE", String.class, record.getPostCode()));
            parameters.add(new EJStatementParameter("TOWN", String.class, record.getTown()));
            parameters.add(new EJStatementParameter("COUNTRY", String.class, record.getCountry()));
            parameters.add(new EJStatementParameter("VAT_ID", Integer.class, record.getVatId()));
            parameters.add(new EJStatementParameter("CCY_ID", Integer.class, record.getCcyId()));
            parameters.add(new EJStatementParameter("PAYMENT_DAYS", Integer.class, record.getPaymentDays()));

            EJStatementCriteria criteria = new EJStatementCriteria();
            criteria.add(EJRestrictions.equals("COMPANY_ID", record.getCompanyId()));
            
            if (record.getInitialId() == null)
            {
                criteria.add(EJRestrictions.isNull("ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ID", record.getInitialId()));
            }
            
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeUpdate(form, "CUSTOMER", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != updateRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in update. Expected: " + updateRecords.size() + ". Updated: "
                    + recordsProcessed);
        }
    }

    @Override
    public void executeDelete(EJForm form, List<TimeEntryCustomer> recordsToDelete)
    {
        ArrayList<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (TimeEntryCustomer record : recordsToDelete)
        {
            parameters.clear();

            EJStatementCriteria criteria = new EJStatementCriteria();
            criteria.add(EJRestrictions.equals("COMPANY_ID", record.getCompanyId()));
            
            if (record.getInitialCustomerNumber() == null)
            {
                criteria.add(EJRestrictions.isNull("CUSTOMER_NUMBER"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CUSTOMER_NUMBER", record.getInitialCustomerNumber()));
            }
            if (record.getInitialAddress() == null)
            {
                criteria.add(EJRestrictions.isNull("ADDRESS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("ADDRESS", record.getInitialAddress()));
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
            if (record.getInitialPostCode() == null)
            {
                criteria.add(EJRestrictions.isNull("POST_CODE"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("POST_CODE", record.getInitialPostCode()));
            }
            if (record.getInitialTown() == null)
            {
                criteria.add(EJRestrictions.isNull("TOWN"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("TOWN", record.getInitialTown()));
            }
            if (record.getInitialCountry() == null)
            {
                criteria.add(EJRestrictions.isNull("COUNTRY"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("COUNTRY", record.getInitialCountry()));
            }
            
            if (record.getInitialCcyId() == null)
            {
                criteria.add(EJRestrictions.isNull("CCY_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CCY_ID", record.getInitialCcyId()));
            }
            if (record.getInitialVatId() == null)
            {
                criteria.add(EJRestrictions.isNull("VAT_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("VAT_ID", record.getInitialVatId()));
            }
            if (record.getInitialPaymentDays() == null)
            {
                criteria.add(EJRestrictions.isNull("PAYMENT_DAYS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("PAYMENT_DAYS", record.getInitialPaymentDays()));
            }

            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeDelete(form, "CUSTOMER", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != recordsToDelete.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in delete. Expected: " + recordsToDelete.size() + ". Deleted: "
                    + recordsProcessed);
        }
    }

}
