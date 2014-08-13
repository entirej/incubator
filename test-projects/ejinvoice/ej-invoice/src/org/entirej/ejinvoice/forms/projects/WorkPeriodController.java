package org.entirej.ejinvoice.forms.projects;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.eclipse.swt.widgets.DateTime;

public class WorkPeriodController
{
    
    private ArrayList<WorkPeriod> _periods = new ArrayList<WorkPeriod>();
    
    
    public void addPeriod(WorkPeriod period)
    {
        _periods.add(period);
    }

    
    
    public static List<Date> getDateRange(Date start, Date end)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        
        Calendar cal = Calendar.getInstance();

        cal.setTime(start);
        while (cal.getTime().before(end))
        {
            dates.add(new Date(cal.getTimeInMillis()));
            cal.add(Calendar.DATE, 1);
        }
        
        return dates;
    }
}
