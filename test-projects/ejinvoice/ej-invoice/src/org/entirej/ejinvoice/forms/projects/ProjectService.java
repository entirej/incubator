package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Locale.Builder;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.forms.company.Company;
import org.entirej.ejinvoice.forms.company.User;
import org.entirej.ejinvoice.forms.timeentry.Customer;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJSelectResult;
import org.entirej.framework.core.service.EJStatementExecutor;

public class ProjectService
{
    public Company getCompany(EJForm form)
    {
        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();

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

    public BigDecimal getRemainingBookableHours(EJForm form, Integer companyId, Integer projectId)
    {
        if (companyId == null)
        {
            throw new EJApplicationException("No company id passed ProjectService.getTotalProjectHours");
        }
        if (projectId == null)
        {
            throw new EJApplicationException("No project id passed ProjectService.getTotalProjectHours");
        }

        User user = (User) form.getApplicationLevelParameter(EJ_PROPERTIES.P_USER).getValue();

        StringBuilder stmt = new StringBuilder();
        stmt.append(" select  IFNULL(CUPR.BOOKABLE_HOURS, CUPR.MAXIMUM_HOURS) BOOKABLE_HOURS, ((SUM(TIME_TO_SEC(TIMEDIFF(cpte.end_time, cpte.start_time))) / 60) / 60 ) AS HOURS ");
        stmt.append(" from customer_projects cupr ");
        stmt.append(" ,customer_project_tasks cupt ");
        stmt.append(" ,customer_project_timeentry cpte ");
        stmt.append(" ,user ");
        stmt.append(" where cupr.id = cupt.cpr_id ");
        stmt.append(" and   cupt.id = cpte.cupt_id ");
        stmt.append(" and   cpte.user_id = user.id ");
        stmt.append(" and   cupr.company_id = " + companyId);
        stmt.append(" and   cupr.id         = " + projectId);
        stmt.append(" and   user.id         = " + user.getId());

        EJStatementExecutor executor = new EJStatementExecutor();

        List<EJSelectResult> results = executor.executeQuery(form, stmt.toString());

        if (results.size() <= 0)
        {
            return BigDecimal.ZERO;
        }
        else
        {
            BigDecimal hours = (BigDecimal) results.get(0).getItemValue("HOURS");
            Integer totalBookableHours = (Integer) results.get(0).getItemValue("BOOKABLE_HOURS");

            BigDecimal bookableHours = new BigDecimal(totalBookableHours);
            
            return bookableHours.subtract(hours);
        }
    }
    
    public boolean canBookHoursToProject(EJForm form, BigDecimal enteredHours, Integer companyId, Integer projectId)
    {
        if (companyId == null)
        {
            throw new EJApplicationException("No company id passed ProjectService.getTotalProjectHours");
        }
        if (projectId == null)
        {
            throw new EJApplicationException("No project id passed ProjectService.getTotalProjectHours");
        }

        User user = (User) form.getApplicationLevelParameter(EJ_PROPERTIES.P_USER).getValue();

        StringBuilder stmt = new StringBuilder();
        stmt.append(" select  IFNULL(CUPR.BOOKABLE_HOURS, CUPR.MAXIMUM_HOURS) BOOKABLE_HOURS, ((SUM(TIME_TO_SEC(TIMEDIFF(cpte.end_time, cpte.start_time))) / 60) / 60 ) AS HOURS ");
        stmt.append(" from customer_projects cupr ");
        stmt.append(" ,customer_project_tasks cupt ");
        stmt.append(" ,customer_project_timeentry cpte ");
        stmt.append(" ,user ");
        stmt.append(" where cupr.id = cupt.cpr_id ");
        stmt.append(" and   cupt.id = cpte.cupt_id ");
        stmt.append(" and   cpte.user_id = user.id ");
        stmt.append(" and   cupr.company_id = " + companyId);
        stmt.append(" and   cupr.id         = " + projectId);
        stmt.append(" and   user.id         = " + user.getId());

        EJStatementExecutor executor = new EJStatementExecutor();

        List<EJSelectResult> results = executor.executeQuery(form, stmt.toString());

        if (results.size() <= 0)
        {
            return false;
        }
        else
        {
            BigDecimal hours = (BigDecimal) results.get(0).getItemValue("HOURS");
            if (hours == null)
            {
                hours = BigDecimal.ZERO;
            }
            Integer totalBookableHours = (Integer) results.get(0).getItemValue("BOOKABLE_HOURS");

            
            BigDecimal bookableHours;
            if (totalBookableHours == null)
            {
                bookableHours = BigDecimal.ZERO;
            }
            else
            {
                bookableHours = new BigDecimal(totalBookableHours);
            }
            
            if (bookableHours == null || bookableHours.equals(BigDecimal.ZERO))
            {
                // No bookable hours have been entered, so they can book as much as they want
                return true;
            }
            
            if (hours.add(enteredHours).compareTo(bookableHours) == 1)
            {
                return false;
            }
        }
        
        return true;
    }

    public BigDecimal getTotalProjectBookedHours(EJForm form, Integer companyId, Integer projectId)
    {
        if (companyId == null)
        {
            throw new EJApplicationException("No company id passed ProjectService.getTotalProjectHours");
        }
        if (projectId == null)
        {
            throw new EJApplicationException("No project id passed ProjectService.getTotalProjectHours");
        }

        User user = (User) form.getApplicationLevelParameter(EJ_PROPERTIES.P_USER).getValue();

        StringBuilder stmt = new StringBuilder();
        stmt.append(" select  ((SUM(TIME_TO_SEC(TIMEDIFF(cpte.end_time, cpte.start_time))) / 60) / 60 ) as hours ");
        stmt.append(" from customer_projects cupr ");
        stmt.append(" ,customer_project_tasks cupt ");
        stmt.append(" ,customer_project_timeentry cpte ");
        stmt.append(" ,user ");
        stmt.append(" where cupr.id = cupt.cpr_id ");
        stmt.append(" and   cupt.id = cpte.cupt_id ");
        stmt.append(" and   cpte.user_id = user.id ");
        stmt.append(" and   cupr.company_id = " + companyId);
        stmt.append(" and   cupr.id         = " + projectId);
        stmt.append(" and   user.id         = " + user.getId());

        EJStatementExecutor executor = new EJStatementExecutor();

        List<EJSelectResult> results = executor.executeQuery(form, stmt.toString());

        if (results.size() <= 0)
        {
            return BigDecimal.ZERO;
        }
        else
        {
            return (BigDecimal) results.get(0).getItemValue("hours");
        }
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

    public String getStatus(EJForm form, Integer projectId)
    {
        if (projectId == null)
        {
            throw new EJApplicationException("No project id passed ProjectService.getStatus");
        }
        EJStatementExecutor executor = new EJStatementExecutor();

        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("COMPANY_ID", companyId));
        criteria.add(EJRestrictions.equals("ID", projectId));
        List<Project> projects = executor.executeQuery(Project.class, form, "SELECT STATUS FROM CUSTOMER_PROJECTS ", criteria);

        if (projects.size() > 0)
        {
            return projects.get(0).getStatus();
        }
        else
        {
            return "NONE";
        }
    }
}
