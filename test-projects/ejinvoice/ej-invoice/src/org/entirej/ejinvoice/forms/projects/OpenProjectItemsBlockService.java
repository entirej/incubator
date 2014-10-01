package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.forms.company.User;
import org.entirej.ejinvoice.forms.constants.F_INVOICE_PLANNING;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJParameterType;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJSelectResult;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class OpenProjectItemsBlockService implements EJBlockService<OpenProjectItem>
{
    private final EJStatementExecutor _statementExecutor;
    private StringBuilder             _selectStatement       = new StringBuilder();
    private StringBuilder             _selectPlanedStatement = new StringBuilder();

    public OpenProjectItemsBlockService()
    {
        _statementExecutor = new EJStatementExecutor();
        _selectStatement.append("select CPR.NAME AS PROJECT_NAME ,");
        _selectStatement.append("       CUPT.ID AS TASK_ID , ");
        _selectStatement.append("      CUPT.CPR_ID AS PROJECT_ID , ");
        _selectStatement.append("      CUPT.NAME AS TASK_NAME , ");
        _selectStatement.append("           DAY(CPTE.WORK_DATE)  AS TE_DAY ,     ");
        _selectStatement.append("      MONTH(CPTE.WORK_DATE) AS TE_MONTH ,");
        _selectStatement.append("      YEAR(CPTE.WORK_DATE)  AS TE_YEAR , ");
        _selectStatement.append("          CPTE.END_TIME as END_TIME,");
        _selectStatement.append("          CPTE.START_TIME as START_TIME,");
        _selectStatement.append("      (((TIME_TO_SEC(TIMEDIFF(CPTE.END_TIME,CPTE.START_TIME))) / 60) / 60) WORK_HOURS, ");
        _selectStatement.append("      CUPT.PAY_RATE , ");
        _selectStatement.append("      CPR.COMPANY_ID  ");
        _selectStatement.append(" FROM ");
        _selectStatement.append("customer_project_timeentry as CPTE,");
        _selectStatement.append(" customer_project_tasks AS CUPT, ");
        _selectStatement.append(" customer_projects AS CPR ");
        _selectStatement.append("where ");
        _selectStatement.append("   CUPT.CPR_ID  = CPR.ID AND");
        _selectStatement.append("   CPTE.CUPT_ID = CUPT.ID AND");
        _selectStatement.append("   CPTE.INVP_ID IS NULL AND");
        _selectStatement.append("   CUPT.INVP_ID IS NULL AND");
        _selectStatement.append("   CPR.COMPANY_ID = ? AND ");
        _selectStatement.append("   CPR.CUSTOMER_ID = ? ");
        _selectStatement
                .append("   AND NOT EXISTS (SELECT 1 FROM INVOICE_POSITIONS INVP WHERE INVP.CUPR_ID = CPR.ID AND INVP.CUPT_ID = CUPT.ID AND CPTE.WORK_DATE BETWEEN INVP.PERIOD_FROM AND INVP.PERIOD_TO)  ");
        _selectStatement.append("    ");
        _selectStatement.append(" order by TE_YEAR,TE_MONTH,TE_DAY");

        _selectPlanedStatement = new StringBuilder(
                "SELECT INVP.PERIOD_TO ,INVP.PERIOD_FROM FROM INVOICE_POSITIONS INVP WHERE INVP.CUPT_ID = ? AND ? between YEAR(INVP.PERIOD_FROM) and YEAR(INVP.PERIOD_TO)  and ? between MONTH(INVP.PERIOD_FROM) and MONTH(INVP.PERIOD_TO) AND INVP.COMPANY_ID = ? ORDER by PERIOD_FROM");
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<OpenProjectItem> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        User user = (User)form.getApplicationLevelParameter(EJ_PROPERTIES.P_USER).getValue();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, user.getLocale());
        
        ArrayList<OpenProjectItem> projectItems = new ArrayList<OpenProjectItem>();

        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
        EJStatementParameter companyIdParam = new EJStatementParameter(EJParameterType.IN);
        companyIdParam.setValue(companyId);
        
        Integer customerId = (Integer) queryCriteria.getRestriction(F_INVOICE_PLANNING.B_OPEN_PROJECT_ITEMS.I_CUSTOMER_ID).getValue();
        EJStatementParameter customerIdParam = new EJStatementParameter(EJParameterType.IN);
        customerIdParam.setValue(customerId);
        

        List<EJSelectResult> results = _statementExecutor.executeQuery(form.getConnection(), _selectStatement.toString(), companyIdParam, customerIdParam);

        Map<GroupKey, Map<Integer, List<EJSelectResult>>> groupedResult = new HashMap<OpenProjectItemsBlockService.GroupKey, Map<Integer, List<EJSelectResult>>>();

        Calendar calendar = Calendar.getInstance();
        final Calendar FT_CAL = Calendar.getInstance();
        for (EJSelectResult result : results)
        {
            Integer taskId = toInteger(result.getItemValue("TASK_ID"));
            Integer month = toInteger(result.getItemValue("TE_MONTH"));
            Integer year = toInteger(result.getItemValue("TE_YEAR"));

            GroupKey key = new GroupKey(taskId, month, year);
            Map<Integer, List<EJSelectResult>> map = groupedResult.get(key);
            if (map == null)
            {
                map = new HashMap<Integer, List<EJSelectResult>>();

                groupedResult.put(key, map);
            }
            Integer day = toInteger(result.getItemValue("TE_DAY"));
            List<EJSelectResult> list = map.get(day);
            if (list == null)
            {
                list = new ArrayList<EJSelectResult>();
                map.put(day, list);
            }

            list.add(result);

        }
        List<GroupKey> keySet = new ArrayList<GroupKey>(groupedResult.keySet());
        Collections.sort(keySet);
        for (GroupKey key : keySet)
        {

            EJStatementParameter month = new EJStatementParameter(EJParameterType.IN);
            month.setValue(key.month);
            EJStatementParameter year = new EJStatementParameter(EJParameterType.IN);
            year.setValue(key.year);
            EJStatementParameter taskIdParam = new EJStatementParameter(EJParameterType.IN);
            taskIdParam.setValue(key.taskId);

            List<EJSelectResult> planed = _statementExecutor.executeQuery(form.getConnection(), _selectPlanedStatement.toString(), taskIdParam, year, month, companyIdParam);

            Map<Integer, List<EJSelectResult>> map = groupedResult.get(key);
            calendar.set(key.year, key.month - 1, 1);
            int numberOfdays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            OpenProjectItem item = null;

            Date start = new Date(calendar.getTime().getTime());
            DAYS: for (int i = 1; i <= numberOfdays; i++)
            {
                calendar.set(key.year, key.month - 1, i);

                if (item != null)
                {
                    item.setTeLastDay(new Date(calendar.getTime().getTime()));
                }
                if (start == null)
                {
                    start = new Date(calendar.getTime().getTime());
                }

                for (EJSelectResult result : planed)
                {

                    if (result.getItemValue("PERIOD_FROM") != null && result.getItemValue("PERIOD_TO") != null)
                    {

                        FT_CAL.setTime((Date) result.getItemValue("PERIOD_FROM"));
                        int fday = FT_CAL.get(Calendar.DAY_OF_MONTH);
                        int fMonth = FT_CAL.get(Calendar.MONTH) + 1;
                        FT_CAL.setTime((Date) result.getItemValue("PERIOD_TO"));
                        int tday = FT_CAL.get(Calendar.DAY_OF_MONTH);
                        int tMonth = FT_CAL.get(Calendar.MONTH) + 1;
                        int startRange = (fMonth == key.month) ? fday : 1;
                        int endRange = (tMonth == key.month) ? tday : numberOfdays;
                        if (endRange >= i && startRange <= i)
                        {

                            if (item != null)
                            {

                                calendar.set(Calendar.DAY_OF_MONTH, (fMonth == key.month) ? (fday - 1) : numberOfdays);
                                item.setTeLastDay(new Date(calendar.getTime().getTime()));

                                i = (tMonth == key.month) ? tday : numberOfdays;
                            }
                            item = null;
                            start = null;

                            continue DAYS;
                        }
                    }
                }

                List<EJSelectResult> list = map.get(i);

                if (list == null)
                {

                    continue;
                }

                for (EJSelectResult result : list)
                {
                    if (item == null)
                    {

                        item = new OpenProjectItem();
                        item.setCompanyId(toInteger(result.getItemValue("COMPANY_ID")));
                        item.setProjectId(toInteger(result.getItemValue("PROJECT_ID")));
                        item.setProjectName((String) result.getItemValue("PROJECT_NAME"));
                        item.setTaskId(toInteger(result.getItemValue("TASK_ID")));
                        item.setTaskName((String) result.getItemValue("TASK_NAME"));
                        item.setTeMonth(toInteger(result.getItemValue("TE_MONTH")));
                        item.setTeYear(toInteger(result.getItemValue("TE_YEAR")));
                        item.setPayRate((BigDecimal) result.getItemValue("PAY_RATE"));
                        item.setCreateInvoicePosition("Define Period");
                        item.setTeFirstDay(start);
                        
                        Calendar lastDayCal = Calendar.getInstance();
                        lastDayCal.set(Calendar.YEAR, item.getTeYear());
                        lastDayCal.set(Calendar.MONTH, item.getTeMonth()-1);
                        lastDayCal.set(Calendar.DAY_OF_MONTH, lastDayCal.getActualMaximum(Calendar.DAY_OF_MONTH));  
                        
                        item.setTeLastDay(new Date(lastDayCal.getTimeInMillis()));

                        StringBuilder display = new StringBuilder();
                        display.append("<span style =\"font-weight: bold; font-size: 110% \">" + item.getProjectName() + "  (" + item.getTaskName()
                                + ")</span>");
                        display.append("<br><span style =\"font-weight: normal; font-size: 100% \">" + dateFormat.format(item.getTeFirstDay()) + " - " + dateFormat.format(item.getTeLastDay())
                                + "</span></br>");
                        item.setDisplayText(display.toString());
                        item.setDisplayValueText("Hours: ");
                        projectItems.add(item);
                    }

                    if (item.getWorkHours() == null)
                    {
                        item.setWorkHours((BigDecimal) result.getItemValue("WORK_HOURS"));
                    }
                    else
                    {
                        item.setWorkHours(item.getWorkHours().add((BigDecimal) result.getItemValue("WORK_HOURS")));
                    }
                }
            }

        }

        return projectItems;
    }

    @Override
    public void executeInsert(EJForm form, List<OpenProjectItem> newRecords)
    {
    }

    @Override
    public void executeUpdate(EJForm form, List<OpenProjectItem> updateRecords)
    {
    }

    @Override
    public void executeDelete(EJForm form, List<OpenProjectItem> recordsToDelete)
    {
    }

    private Integer toInteger(Object object)
    {
        if (object instanceof Integer)
        {
            return (Integer) object;
        }
        if (object instanceof Long)
        {
            return ((Long) object).intValue();
        }
        if (object instanceof Number)
        {
            return ((Number) object).intValue();
        }

        return null;

    }

    private static class GroupKey implements Comparable<GroupKey>
    {
        final int month;
        final int year;
        final int taskId;

        public GroupKey(Integer taskId, Integer month, Integer year)
        {
            this.month = month;
            this.year = year;
            this.taskId = taskId;
        }

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + month;
            result = prime * result + year;
            result = prime * result + taskId;
            return result;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            GroupKey other = (GroupKey) obj;
            if (month != other.month)
                return false;
            if (year != other.year)
                return false;
            if (taskId != other.taskId)
                return false;
            return true;
        }

        @Override
        public int compareTo(GroupKey o)
        {
            int i = Integer.compare(year, o.year);
            if (i == 0)
            {
                i = Integer.compare(month, o.month);
                if (i == 0)
                {
                    i = Integer.compare(taskId, o.taskId);
                }
            }
            return i;
        }

    }

}