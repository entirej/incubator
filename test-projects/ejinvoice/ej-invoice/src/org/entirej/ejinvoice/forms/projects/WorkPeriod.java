package org.entirej.ejinvoice.forms.projects;

import java.sql.Date;
import java.util.ArrayList;

public class WorkPeriod
{
    private Integer               _invpId;
    private Date                  _periodfrom;
    private Date                  _periodTo;
    private InvoicePositionStatus _status;

    private ArrayList<WorkDay>    _workDays = new ArrayList<WorkDay>();

    
    public boolean includes(Date day)
    {
        
        
        if (day == null)
        {
            throw new NullPointerException("The day passed to WorkPeriod.includes cannot be null!");
        }
        if (day.before(_periodTo) && day.after(_periodfrom))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean includes(WorkDay day)
    {
        if (day.getWorkDay().before(_periodTo) && day.getWorkDay().after(_periodfrom))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void add(WorkDay day)
    {
        if (includes(day))
        {
            _workDays.add(day);
        }
        else
        {
            throw new IllegalArgumentException("The day, " + day.getWorkDay() + " does not belong to this period");
        }
    }
    
    public void setInvoicePositionId (Integer invpId)
    {
        _invpId = invpId;
    }
    
    public Integer getInvpoicePositionId()
    {
        return _invpId;
    }
    
    public void setStatus(InvoicePositionStatus status)
    {
        _status = status;
    }

    public InvoicePositionStatus getStatus()
    {
        return _status;
    }
    
    public void setPeriodFrom(Date from)
    {
        if (from != null)
        {
            _periodfrom = from;
        }
    }

    public Date getPeriodFrom()
    {
        return _periodfrom;
    }

    public void setPeriodTo(Date to)
    {
        if (to != null)
        {
            _periodTo = to;
        }
    }

    public Date getPeriodTo()
    {
        return _periodTo;
    }

}
