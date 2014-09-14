package org.entirej.ejinvoice.forms.timeentryoverview;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.enums.UserRole;
import org.entirej.ejinvoice.forms.company.User;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY_OVERVIEW;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJSelectResult;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class UserTotalHoursOverviewBlockService implements EJBlockService<UserTotalHoursOverview>
{
    private final EJStatementExecutor _statementExecutor;

    public UserTotalHoursOverviewBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<UserTotalHoursOverview> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        User user = (User)form.getApplicationLevelParameter(EJ_PROPERTIES.P_USER).getValue();
        
        StringBuilder selectStatement = new StringBuilder()
                .append("select cupr.name as project_name ")
                .append(",       cupt.name as project_task ")
                .append(",       (select name from customer where id = cupr.customer_id) as customer_name ")
                .append(",       cpte.user_id ")
                .append(",       user.first_name ")
                .append(",       user.last_name ")
                .append(",       (((TIME_TO_SEC(TIMEDIFF(cpte.end_time, cpte.start_time))) / 60) / 60 ) as hours ")
                .append(",       cpte.work_date ")
                .append(",       cpte.start_time ")
                .append(",       cpte.end_time ")
                .append(",       cpte.work_description ")
                .append("from customer_projects cupr ")
                .append(",    customer_project_tasks cupt ")
                .append(",    customer_project_timeentry cpte ")
                .append(",    user ")
                .append("where cupr.id = cupt.cpr_id ")
                .append("and   cupt.id = cpte.cupt_id ")
                .append("and   cpte.user_id = user.id ")
                .append("and   cupr.company_id = ? ");

        String orderBy = " order by user.first_name, user.last_name, cupr.name, cupt.name, work_date, start_time";

        ArrayList<EJStatementParameter> paramList = new ArrayList<EJStatementParameter>();

        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
        EJStatementParameter companyIdParam = new EJStatementParameter(companyId);
        paramList.add(companyIdParam);

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

        selectStatement.append(orderBy);

        ArrayList<UserTotalHoursOverview> returnResults = new ArrayList<UserTotalHoursOverview>();

        EJStatementParameter paramArray[] = new EJStatementParameter[paramList.size()];
        paramArray = paramList.toArray(paramArray);
        List<EJSelectResult> results = _statementExecutor.executeQuery(form, selectStatement.toString(), paramArray);

        if (results.size() > 0)
        {
            UserTotalHoursOverview header = new UserTotalHoursOverview();
            header.setHours("Hours");
            header.setWorkDate("Date");
            header.setWorkPeriod("Period");
            header.setWorkDescription("Work Description");
            header.setHeaderCode(1);
            returnResults.add(header);
        }
        
        
        userId = -1;
        int userIdResult;
        StringBuilder description = new StringBuilder();
        for (EJSelectResult result : results)
        {
            description = new StringBuilder();

            UserTotalHoursOverview totalHoursDisp = new UserTotalHoursOverview();

            userIdResult = (Integer) result.getItemValue("user_id");

            if (userId != userIdResult)
            {
                userId = userIdResult;

                UserTotalHoursOverview userHeader = new UserTotalHoursOverview();
                userHeader.setHeaderCode(2);
                userHeader.setProjectDescription((result.getItemValue("first_name")) + " " + result.getItemValue("last_name"));
                returnResults.add(userHeader);
            }
            else
            {
                totalHoursDisp.setHeaderCode(0);
            }

            description.append((String)result.getItemValue("customer_name"));
            description.append(" : ");
            description.append((String) result.getItemValue("project_name"));
            description.append(" (").append((String) result.getItemValue("project_task")).append(") ");

            totalHoursDisp.setProjectDescription(description.toString());
            
            Time startTime = (Time)result.getItemValue("start_time");
            Time endTime = (Time)result.getItemValue("end_time");
            
            String startTimeString = new SimpleDateFormat("HH:mm").format(startTime);
            String endTimeString = new SimpleDateFormat("HH:mm").format(endTime);
            totalHoursDisp.setWorkPeriod(startTimeString+" - "+endTimeString);
                        
            NumberFormat decimalFormat = DecimalFormat.getIntegerInstance(user.getLocale());
            BigDecimal hours = (BigDecimal) result.getItemValue("hours");
            
            totalHoursDisp.setHours((hours == null ? "" : decimalFormat.format(hours.doubleValue())));
            
            totalHoursDisp.setWorkDescription((String)result.getItemValue("work_description"));
            
            Date workDate = (Date)result.getItemValue("work_date");
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, user.getLocale());
            totalHoursDisp.setWorkDate(dateFormat.format(workDate));
            
            returnResults.add(totalHoursDisp);
        }

        return returnResults;
    }

    @Override
    public void executeInsert(EJForm form, List<UserTotalHoursOverview> newRecords)
    {
    }

    @Override
    public void executeUpdate(EJForm form, List<UserTotalHoursOverview> updateRecords)
    {
    }

    @Override
    public void executeDelete(EJForm form, List<UserTotalHoursOverview> recordsToDelete)
    {
    }

}
