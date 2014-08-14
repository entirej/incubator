package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.entirej.ejinvoice.forms.constants.F_PROJECTS;
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
    private StringBuilder             _selectStatement = new StringBuilder();

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
        _selectStatement.append("      (SELECT INVP.PERIOD_FROM FROM INVOICE_POSITIONS INVP WHERE INVP.CUPR_ID = CPR.ID AND CPTE.WORK_DATE BETWEEN INVP.PERIOD_FROM AND INVP.PERIOD_TO)  as TE_PLANED_FROM ,");
        _selectStatement.append("      (SELECT INVP.PERIOD_TO FROM INVOICE_POSITIONS INVP WHERE INVP.CUPR_ID = CPR.ID AND CPTE.WORK_DATE BETWEEN INVP.PERIOD_FROM AND INVP.PERIOD_TO)  as TE_PLANED_TO ");
        _selectStatement.append(" FROM ");
        _selectStatement.append("customer_project_timeentry as CPTE,");
        _selectStatement.append(" customer_project_tasks AS CUPT, ");
        _selectStatement.append(" customer_projects AS CPR ");
        _selectStatement.append("where ");
        _selectStatement.append("   CUPT.CPR_ID  = CPR.ID AND");
        _selectStatement.append("   CPTE.CUPT_ID = CUPT.ID AND");
        _selectStatement.append("   CPTE.INVP_ID IS NULL AND");
        _selectStatement.append("   CUPT.INVP_ID IS NULL AND");
        _selectStatement.append("   CPR.ID       = ?  ");
        _selectStatement.append("    ");

        _selectStatement.append(" order by TE_YEAR,TE_MONTH,TE_DAY");
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<OpenProjectItem> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        ArrayList<OpenProjectItem> projectItems = new ArrayList<OpenProjectItem>();

        Integer projectId = (Integer) queryCriteria.getRestriction(F_PROJECTS.B_OPEN_PROJECT_ITEMS.I_PROJECT_ID).getValue();
        EJStatementParameter projectIdParam = new EJStatementParameter(EJParameterType.IN);
        projectIdParam.setValue(projectId);
        
        Map<GroupKey,Map<Integer,List<EJSelectResult>>> groupedResult = new HashMap<OpenProjectItemsBlockService.GroupKey, Map<Integer,List<EJSelectResult>>>();
        
       

        List<EJSelectResult> results = _statementExecutor.executeQuery(form.getConnection(), _selectStatement.toString(), projectIdParam);
        
        
        Calendar calendar = Calendar.getInstance();
        final Calendar FT_CAL = Calendar.getInstance();
        for (EJSelectResult result : results)
        {
            Integer month = (Integer) result.getItemValue("TE_MONTH");
            Integer year = (Integer) result.getItemValue("TE_YEAR");
            
            GroupKey key = new GroupKey(month, year);
            Map<Integer,List<EJSelectResult>> map = groupedResult.get(key);
            if(map==null)
            {
                map = new HashMap<Integer,List<EJSelectResult>>();
               
                groupedResult.put(key, map);
            }
            Integer day = (Integer) result.getItemValue("TE_DAY");
            List<EJSelectResult> list = map.get(day);
            if(list==null)
            {
                list = new ArrayList<EJSelectResult>();
                map.put(day, list);
            }
            list.add(result);
            
        }
        
        for (GroupKey key : groupedResult.keySet())
        {
            
            Map<Integer,List<EJSelectResult>> map = groupedResult.get(key);
            calendar.set(key.year, key.month-1, 1);
            int numberOfdays = calendar.getMaximum(Calendar.DAY_OF_MONTH);
            OpenProjectItem item =null;
            
           Date start   = new Date( calendar.getTime().getTime());
           DAYS: for (int i = 1; i <= numberOfdays; i++)
            {
               calendar.set(key.year, key.month-1, i);
                List<EJSelectResult> list = map.get(i);
                
                
                if(item!=null)
                {
                    item.setTeLastDay(new Date( calendar.getTime().getTime()));
                }
                if(start==null)
                {
                    start = new Date( calendar.getTime().getTime());
                }
                
                if(list==null)
                {
                    
                    continue;
                }
                for (EJSelectResult result : list)
                {
                    
                    if(result.getItemValue("TE_PLANED_FROM")!=null && result.getItemValue("TE_PLANED_TO")!=null )
                    {
                       
                        FT_CAL.setTime((Date)result.getItemValue("TE_PLANED_FROM"));
                        int fday = FT_CAL.get(Calendar.DAY_OF_MONTH);
                        int fMonth = FT_CAL.get(Calendar.MONTH)+1;
                        FT_CAL.setTime((Date)result.getItemValue("TE_PLANED_TO"));
                        int tday = FT_CAL.get(Calendar.DAY_OF_MONTH);
                        int tMonth = FT_CAL.get(Calendar.MONTH)+1;
                        if(item!=null)
                        {
                            
                            calendar.set(Calendar.DAY_OF_MONTH, (fMonth==key.month)?(fday-1):numberOfdays);
                            item.setTeLastDay(new Date( calendar.getTime().getTime()));
                            
                            i = (tMonth==key.month)? tday:numberOfdays;
                        }
                        item=null;
                        start = null;
                       
                        continue DAYS;
                    }
                }
               
                
               
                for (EJSelectResult result : list)
                {
                    if(item==null)
                    {
                        
                        item = new OpenProjectItem();
                        item.setProjectId((Integer) result.getItemValue("PROJECT_ID"));
                        item.setProjectName((String) result.getItemValue("PROJECT_NAME"));
                        item.setTaskId((Integer) result.getItemValue("TASK_ID"));
                        item.setTaskName((String) result.getItemValue("TASK_NAME"));
                        item.setTeMonth((Integer) result.getItemValue("TE_MONTH"));
                        item.setTeYear((Integer) result.getItemValue("TE_YEAR"));
                        item.setCreateInvoicePosition("Plan");
                        item.setTeFirstDay(start);
                        item.setTeLastDay(item.getTeFirstDay());
                        projectItems.add(item);
                    }
                    
                    if(item.getWorkHours()==null)
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

    
    private static class GroupKey
    {
       final  int month ;
        final int year;
        public GroupKey(Integer month, Integer year)
        {
            this.month = month;
            this.year = year;
        }
        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + month;
            result = prime * result + year;
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
            return true;
        }
       
        
        
        
    }
    
    
    
}