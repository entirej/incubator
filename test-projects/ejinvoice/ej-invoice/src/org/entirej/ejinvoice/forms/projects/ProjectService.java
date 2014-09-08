package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.List;
import java.util.Locale.Builder;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.forms.company.Company;
import org.entirej.ejinvoice.forms.constants.F_PROJECTS;
import org.entirej.ejinvoice.forms.invoice.Invoice;
import org.entirej.ejinvoice.forms.invoice.InvoiceBlockService;
import org.entirej.ejinvoice.forms.invoice.InvoicePosition;
import org.entirej.ejinvoice.forms.timeentry.Customer;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.enumerations.EJMessageLevel;
import org.entirej.framework.core.service.EJParameterType;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJSelectResult;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class ProjectService
{
    public Company getCompany(EJForm form)
    {
        Integer companyId = (Integer)form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
        
        String selectStatement = "SELECT ADDRESS,COUNTRY,ID,LOGO,NAME,POST_CODE,TOWN, VAT_NR, INVOICE_FOOTER, INVOICE_SUMMARY FROM company_information";

        EJQueryCriteria criteria = new EJQueryCriteria();
        List<Company> companies = new EJStatementExecutor().executeQuery(Company.class, form, selectStatement, criteria);
        
        if (companies.size() == 0)
        {
            throw new EJApplicationException("ProjectService.getCompany: Unable to find a company with id " + companyId);
        }
        
        return companies.get(0);
    }

    public Customer getCustomerInfo(EJForm form, Integer customerId)
    {
        if (customerId == null)
        {
            throw new EJApplicationException("No customer id passed ProjectService.getCustomerInfo");
        }
        EJStatementExecutor executor = new EJStatementExecutor();

        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("ID", customerId));
        List<Customer> customers = executor.executeQuery(Customer.class, form, "SELECT ID, COMPANY_ID, NAME, ADDRESS, POST_CODE, TOWN, COUNTRY, VAT_ID, (SELECT RATE FROM VAT_RATES WHERE ID = VAT_ID) AS VAT_RATE, PAYMENT_DAYS, CUSTOMER_NUMBER, LOCALE_LANGUAGE, LOCALE_COUNTRY FROM CUSTOMER ", criteria);

        if (customers.size() == 0)
        {
            throw new EJApplicationException("ProjectService.getCustomerInfo: Unable to find a customer with id " + customerId);
        }

        Customer cust = customers.get(0);

        cust.setLocale(new Builder().setLanguage(cust.getLocaleLanguage()).setRegion(cust.getLocaleCountry()).build());
        cust.setCcyCode(Currency.getInstance(cust.getLocale()).getCurrencyCode());

        return cust;
    }

    public List<Project> getAllProjects(EJForm form, Integer companyId)
    {
        if (companyId == null)
        {
            throw new EJApplicationException("No company id passed ProjectService.getAllProjects");
        }
        EJStatementExecutor executor = new EJStatementExecutor();

        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("COMPANY_ID", companyId));
        List<Project> projects = executor.executeQuery(Project.class, form, "SELECT ID, COMPANY_ID, NAME, DESCRIPTION, CUSTOMER_ID, START_DATE, END_DATE, STATUS, NOTES, INVOICEABLE, FIX_PRICE, INVP_ID FROM CUSTOMER_PROJECTS ", criteria);

        return projects;
    }
    
}
