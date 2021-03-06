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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.entirej.ejinvoice.ApplicationParameters;
import org.entirej.ejinvoice.forms.company.User;
import org.entirej.ejinvoice.referencedlovdefs.services.pojos.CustomerProjects;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJQuerySort;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class CustomerProjectsLovService implements EJBlockService<CustomerProjects>
{
    private final EJStatementExecutor _statementExecutor;
    private String                    _selectStatement = "SELECT COMPANY_ID, CUSTOMER_ID, (SELECT NAME FROM CUSTOMER WHERE ID = CUSTOMER_PROJECTS.CUSTOMER_ID) AS CUSTOMER_NAME, DESCRIPTION,ID,NAME, STATUS, FIX_PRICE FROM CUSTOMER_PROJECTS";

    public CustomerProjectsLovService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<CustomerProjects> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        User usr = (User)form.getApplicationLevelParameter(ApplicationParameters.PARAM_USER).getValue();
        queryCriteria.add(EJRestrictions.equals("COMPANY_ID", usr.getCompanyId()));
        
        queryCriteria.add(EJQuerySort.ASC("CUSTOMER_NAME"));
        queryCriteria.add(EJQuerySort.ASC("NAME"));
        List<CustomerProjects> projects = _statementExecutor.executeQuery(CustomerProjects.class, form, _selectStatement, queryCriteria);
        for (CustomerProjects project : projects)
        {
            project.setDisplayName(project.getCustomerName()+ "  (" + project.getName() + ")");
        }
        return projects;
    }

    @Override
    public void executeInsert(EJForm form, List<CustomerProjects> newRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();
        int recordsProcessed = 0;
        for (CustomerProjects record : newRecords)
        {
            // Initialise the value list
            parameters.clear();
            parameters.add(new EJStatementParameter("STATUS", String.class, record.getStatus()));
            parameters.add(new EJStatementParameter("CUSTOMER_ID", Integer.class, record.getCustomerId()));
            parameters.add(new EJStatementParameter("DESCRIPTION", String.class, record.getDescription()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getName()));
            parameters.add(new EJStatementParameter("COMPANY_ID", String.class, record.getCompanyId()));
            parameters.add(new EJStatementParameter("FIX_PRICE", BigDecimal.class, record.getFixPrice()));
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeInsert(form, "CUSTOMER_PROJECTS", parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != newRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in insert. Expected: " + newRecords.size() + ". Inserted: "
                    + recordsProcessed);
        }
    }

    @Override
    public void executeUpdate(EJForm form, List<CustomerProjects> updateRecords)
    {
        List<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (CustomerProjects record : updateRecords)
        {
            parameters.clear();

            // First add the new values
            parameters.add(new EJStatementParameter("CUSTOMER_ID", Integer.class, record.getCustomerId()));
            parameters.add(new EJStatementParameter("DESCRIPTION", String.class, record.getDescription()));
            parameters.add(new EJStatementParameter("ID", Integer.class, record.getId()));
            parameters.add(new EJStatementParameter("COMPANY_ID", Integer.class, record.getCompanyId()));
            parameters.add(new EJStatementParameter("NAME", String.class, record.getName()));
            parameters.add(new EJStatementParameter("STATUS", String.class, record.getStatus()));
            parameters.add(new EJStatementParameter("FIX_PRICE", BigDecimal.class, record.getFixPrice()));

            EJStatementCriteria criteria = new EJStatementCriteria();
            if (record.getInitialCustomerId() == null)
            {
                criteria.add(EJRestrictions.isNull("CUSTOMER_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CUSTOMER_ID", record.getInitialCustomerId()));
            }
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
            if (record.getInitialCompanyId() == null)
            {
                criteria.add(EJRestrictions.isNull("COMPANY_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("COMPANY_ID", record.getInitialCompanyId()));
            }            
            if (record.getInitialName() == null)
            {
                criteria.add(EJRestrictions.isNull("NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NAME", record.getInitialName()));
            }
            if (record.getInitialStatus() == null)
            {
                criteria.add(EJRestrictions.isNull("STATUS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("STATUS", record.getInitialStatus()));
            }
            
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeUpdate(form, "CUSTOMER_PROJECTS", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != updateRecords.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in update. Expected: " + updateRecords.size() + ". Updated: "
                    + recordsProcessed);
        }
    }

    @Override
    public void executeDelete(EJForm form, List<CustomerProjects> recordsToDelete)
    {
        ArrayList<EJStatementParameter> parameters = new ArrayList<EJStatementParameter>();

        int recordsProcessed = 0;
        for (CustomerProjects record : recordsToDelete)
        {
            parameters.clear();

            EJStatementCriteria criteria = new EJStatementCriteria();

            if (record.getInitialCustomerId() == null)
            {
                criteria.add(EJRestrictions.isNull("CUSTOMER_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("CUSTOMER_ID", record.getInitialCustomerId()));
            }
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
            if (record.getInitialCompanyId() == null)
            {
                criteria.add(EJRestrictions.isNull("COMPANY_ID"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("COMPANY_ID", record.getInitialCompanyId()));
            }
            if (record.getInitialName() == null)
            {
                criteria.add(EJRestrictions.isNull("NAME"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("NAME", record.getInitialName()));
            }
            if (record.getInitialStatus() == null)
            {
                criteria.add(EJRestrictions.isNull("STATUS"));
            }
            else
            {
                criteria.add(EJRestrictions.equals("STATUS", record.getInitialStatus()));
            }
            EJStatementParameter[] paramArray = new EJStatementParameter[parameters.size()];
            recordsProcessed += _statementExecutor.executeDelete(form, "CUSTOMER_PROJECTS", criteria, parameters.toArray(paramArray));
            record.clearInitialValues();
        }
        if (recordsProcessed != recordsToDelete.size())
        {
            throw new EJApplicationException("Unexpected amount of records processed in delete. Expected: " + recordsToDelete.size() + ". Deleted: "
                    + recordsProcessed);
        }
    }

}
