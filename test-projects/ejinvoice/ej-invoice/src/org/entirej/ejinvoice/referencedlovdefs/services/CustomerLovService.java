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
package org.entirej.ejinvoice.referencedlovdefs.services;

import java.util.ArrayList;
import java.util.List;

import org.entirej.ejinvoice.ApplicationParameters;
import org.entirej.ejinvoice.forms.company.User;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY.L_CUSTOMER;
import org.entirej.ejinvoice.referencedlovdefs.constants.RL_CUSTOMER;
import org.entirej.ejinvoice.referencedlovdefs.services.pojos.Customer;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJSelectResult;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class CustomerLovService implements EJBlockService<Customer>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT COMPANY_ID, ADDRESS,ID,NAME,POST_CODE,TOWN, COUNTRY FROM CUSTOMER";

    public CustomerLovService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<Customer> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        if (queryCriteria.containsRestriction(RL_CUSTOMER.L_CUSTOMER.I_ITEMS_TO_INVOICE) && queryCriteria.getRestriction(RL_CUSTOMER.L_CUSTOMER.I_ITEMS_TO_INVOICE).getValue().equals(1))
        {
            User usr = (User)form.getApplicationLevelParameter(ApplicationParameters.PARAM_USER).getValue();
            queryCriteria.add(EJRestrictions.equals("COMPANY_ID", usr.getCompanyId()));
            
            StringBuilder selectStmt = new StringBuilder(_selectStatement);
            selectStmt.append(" WHERE ID IN (SELECT CUSTOMER_ID ")
                      .append(" FROM   CUSTOMER_PROJECTS CUPR   ")
                      .append(",       INVOICE_POSITIONS INVP   ")
                      .append(" WHERE  CUPR.ID = INVP.CUPR_ID   ")
                      .append(" AND    CUPR.COMPANY_ID = ?     ")
                      .append(" AND    INVP.STATUS IN ('APPROVED','MARKED_FOR_INVOICE')) ")
                      .append(" AND COMPANY_ID = ?              ");
                            
            EJStatementParameter companyIdParam = new EJStatementParameter(usr.getCompanyId());
            EJStatementParameter companyIdParam2 = new EJStatementParameter(usr.getCompanyId());
            
            List<EJSelectResult> results = _statementExecutor.executeQuery(form, selectStmt.toString(), companyIdParam, companyIdParam2);
            
            ArrayList<Customer> customers = new ArrayList<Customer>();
            for (EJSelectResult result : results)
            {
                Customer customer = new Customer();
                customer.setId((Integer)result.getItemValue("ID"));
                customer.setAddress((String)result.getItemValue("ADDRESS"));
                customer.setCompanyId((Integer)result.getItemValue("COMPANY_ID"));
                customer.setCountry((String)result.getItemValue("COUNTRY"));
                customer.setName((String)result.getItemValue("NAME"));
                customer.setPostCode((String)result.getItemValue("POST_CODE"));
                customer.setTown((String)result.getItemValue("TOWN"));
                
                customers.add(customer);
            }
            return customers;
        }
        
        User usr = (User)form.getApplicationLevelParameter(ApplicationParameters.PARAM_USER).getValue();
        queryCriteria.add(EJRestrictions.equals("COMPANY_ID", usr.getCompanyId()));
        return _statementExecutor.executeQuery(Customer.class, form, _selectStatement, queryCriteria);
    }

    @Override
    public void executeInsert(EJForm form, List<Customer> newRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;
        for (Customer record : newRecords)
        {
            // Initialise the value list
            parameters.clear();
            parameters.add(new EJStatementParameter("ADDRESS", String.class, record.getAddress()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getName()));
            parameters.add(new EJStatementParameter("POST_CODE", String.class, record.getPostCode()));
            parameters.add(new EJStatementParameter("TOWN", String.class, record.getTown()));
            parameters.add(new EJStatementParameter("COUNTRY", String.class, record.getCountry()));
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "CUSTOMER", parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != newRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: " + newRecords.size() + ". Inserted: "
                    + recordsProcessed);
        }
    }

    @Override
    public void executeUpdate(EJForm form, List<Customer> updateRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (Customer record : updateRecords)
        {
            parameters.clear();

            // First add the new values
            parameters.add(new EJStatementParameter("ADDRESS", String.class, record.getAddress()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getName()));
            parameters.add(new EJStatementParameter("POST_CODE", String.class, record.getPostCode()));
            parameters.add(new EJStatementParameter("TOWN", String.class, record.getTown()));
            parameters.add(new EJStatementParameter("COUNTRY", String.class, record.getCountry()));

            EJStatementCriteria criteria = new EJStatementCriteria();
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
    public void executeDelete(EJForm form, List<Customer> recordsToDelete)
    {
        ArrayList<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (Customer record : recordsToDelete)
        {
            parameters.clear();

            EJStatementCriteria criteria = new EJStatementCriteria();

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
