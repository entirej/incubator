package org.entirej.ejinvoice.forms.timeentryoverview;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.enums.UserRole;
import org.entirej.ejinvoice.forms.company.User;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY_OVERVIEW;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJSelectResult;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class UserTotalHoursBlockService implements EJBlockService<UserTotalHoursDisplay>
{
    private final EJStatementExecutor _statementExecutor;

    public UserTotalHoursBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<UserTotalHoursDisplay> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        User user = (User) form.getApplicationLevelParameter(EJ_PROPERTIES.P_USER).getValue();
        
        StringBuilder selectStatement = new StringBuilder().append("select cupr.name as project_name ").append(",       cupt.name as project_task ")
                .append(",       cpte.user_id ").append(",       user.first_name ").append(",       user.last_name ")
                .append(",       ((SUM(TIME_TO_SEC(TIMEDIFF(cpte.end_time, cpte.start_time))) / 60) / 60 ) as hours ").append("from customer_projects cupr ")
                .append(",    customer_project_tasks cupt ").append(",    customer_project_timeentry cpte ").append(",    user ")
                .append("where cupr.id = cupt.cpr_id ").append("and   cupt.id = cpte.cupt_id ").append("and   cpte.user_id = user.id ")
                .append("and   cupr.company_id = ? ");

        String groupOrderBy = "user.first_name, user.last_name,cupr.name, cupt.name";

        ArrayList<EJStatementParameter> paramList = new ArrayList<EJStatementParameter>();

        Integer userId = null;
        if (queryCriteria.containsRestriction(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS.I_USER_ID))
        {
            userId = (Integer) queryCriteria.getRestriction(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS.I_USER_ID).getValue();
        }

        // No user restriction has been selected so check if the user is an
        // employee, if he is, then restrict the selection to that employee only
        if (userId == null)
        {
            if (user.getRole().equals(UserRole.EMPLOYEE.toString()))
            {
                userId = user.getId();
            }
        }

        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
        EJStatementParameter companyIdParam = new EJStatementParameter(companyId);
        paramList.add(companyIdParam);

        if (userId != null)
        {
            selectStatement.append(" and user.id = ? ");
            paramList.add(new EJStatementParameter(userId));
        }

        if (queryCriteria.containsRestriction("dateFrom") && queryCriteria.containsRestriction("dateTo"))
        {
            selectStatement.append(" and cpte.work_date between ? and ? ");

            paramList.add(new EJStatementParameter(queryCriteria.getRestriction("dateFrom").getValue()));
            paramList.add(new EJStatementParameter(queryCriteria.getRestriction("dateTo").getValue()));

        }
        else if (queryCriteria.containsRestriction("dateFrom"))
        {
            selectStatement.append(" and cpte.work_date >= ? ");

            paramList.add(new EJStatementParameter(queryCriteria.getRestriction("dateFrom").getValue()));
        }
        else if (queryCriteria.containsRestriction("dateTo"))
        {
            selectStatement.append(" and cpte.work_date between <= ? ");
            paramList.add(new EJStatementParameter(queryCriteria.getRestriction("dateTo").getValue()));
        }

        selectStatement.append(" group by " + groupOrderBy);
        selectStatement.append(" order by " + groupOrderBy);

        ArrayList<UserTotalHoursDisplay> returnResults = new ArrayList<UserTotalHoursDisplay>();

        EJStatementParameter paramArray[] = new EJStatementParameter[paramList.size()];
        paramArray = paramList.toArray(paramArray);
        List<EJSelectResult> results = _statementExecutor.executeQuery(form, selectStatement.toString(), paramArray);

        BigDecimal totalHours = BigDecimal.ZERO;
        userId = -1;
        int userIdResult;
        StringBuilder description = new StringBuilder();
        for (EJSelectResult result : results)
        {
            description = new StringBuilder();

            UserTotalHoursDisplay totalHoursDisp = new UserTotalHoursDisplay();

            userIdResult = (Integer) result.getItemValue("user_id");
            
            if (userId != userIdResult)
            {
                if (userId != -1)
                {
                    // Add Totals
                    UserTotalHoursDisplay totals = new UserTotalHoursDisplay();
                    totals.setHeaderCode(2);
                    NumberFormat hoursFormat = NumberFormat.getInstance(user.getLocale());
                    totals.setDescription("Total Hours Worked:  " + hoursFormat.format(totalHours).toString());

                    totalHours = BigDecimal.ZERO;
                    returnResults.add(totals);
                }

                userId = userIdResult;

                UserTotalHoursDisplay userHeader = new UserTotalHoursDisplay();
                userHeader.setHeaderCode(1);
                userHeader.setDescription((result.getItemValue("first_name")) + " " + result.getItemValue("last_name"));
                returnResults.add(userHeader);
            }
            else
            {
                totalHoursDisp.setHeaderCode(0);
            }

            description.append((String) result.getItemValue("project_name"));
            description.append(" (").append((String) result.getItemValue("project_task")).append(") ");

            totalHoursDisp.setDescription(description.toString());
            totalHoursDisp.setLocale(user.getLocale());
            BigDecimal hours = (BigDecimal) result.getItemValue("hours");
            totalHoursDisp.setHours(hours);
            totalHours = totalHours.add(hours);

            returnResults.add(totalHoursDisp);
        }

        if (userId != -1)
        {
            // Add Totals
            UserTotalHoursDisplay totals = new UserTotalHoursDisplay();
            totals.setHeaderCode(2);            
            NumberFormat hoursFormat = NumberFormat.getInstance(user.getLocale());
            hoursFormat.setMinimumFractionDigits(2);

            totals.setDescription("Total Hours Worked:  " + hoursFormat.format(totalHours).toString());

            returnResults.add(totals);
        }
        return returnResults;
    }

    @Override
    public void executeInsert(EJForm form, List<UserTotalHoursDisplay> newRecords)
    {
    }

    @Override
    public void executeUpdate(EJForm form, List<UserTotalHoursDisplay> updateRecords)
    {
    }

    @Override
    public void executeDelete(EJForm form, List<UserTotalHoursDisplay> recordsToDelete)
    {
    }

}
