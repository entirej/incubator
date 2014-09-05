package org.entirej.ejinvoice.forms.timeentryoverview;

import java.sql.Date;
import java.sql.Time;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class TimeEntry
{
    private EJPojoProperty<Time>    _endTime;
    private EJPojoProperty<Integer> _id;
    private EJPojoProperty<Time>    _startTime;
    private EJPojoProperty<String>  _workDescription;
    private EJPojoProperty<Integer> _cuptId;
    private EJPojoProperty<Integer> _cuprId;
    private EJPojoProperty<Integer> _companyId;
    private EJPojoProperty<Integer> _userId;
    private EJPojoProperty<Date>    _workDate;
    private EJPojoProperty<String>  _hoursWorked;
    private EJPojoProperty<Integer>  _invpId;
    
    
    @EJFieldName("INVP_ID")
    public Integer getInvpId()
    {
        return EJPojoProperty.getPropertyValue(_invpId);
    }

    @EJFieldName("INVP_ID")
    public void setInvpId(Integer invpId)
    {
        _invpId = EJPojoProperty.setPropertyValue(_invpId, invpId);
    }

    @EJFieldName("INVP_ID")
    public Integer getInitialInvpId()
    {
        return EJPojoProperty.getPropertyInitialValue(_invpId);
    }
    
    
    @EJFieldName("END_TIME")
    public Time getEndTime()
    {
        return EJPojoProperty.getPropertyValue(_endTime);
    }

    @EJFieldName("END_TIME")
    public void setEndTime(Time endTime)
    {
        _endTime = EJPojoProperty.setPropertyValue(_endTime, endTime);
    }

    @EJFieldName("END_TIME")
    public Time getInitialEndTime()
    {
        return EJPojoProperty.getPropertyInitialValue(_endTime);
    }

    @EJFieldName("ID")
    public Integer getId()
    {
        return EJPojoProperty.getPropertyValue(_id);
    }

    @EJFieldName("ID")
    public void setId(Integer id)
    {
        _id = EJPojoProperty.setPropertyValue(_id, id);
    }

    @EJFieldName("ID")
    public Integer getInitialId()
    {
        return EJPojoProperty.getPropertyInitialValue(_id);
    }

    @EJFieldName("START_TIME")
    public Time getStartTime()
    {
        return EJPojoProperty.getPropertyValue(_startTime);
    }

    @EJFieldName("START_TIME")
    public void setStartTime(Time startTime)
    {
        _startTime = EJPojoProperty.setPropertyValue(_startTime, startTime);
    }

    @EJFieldName("START_TIME")
    public Time getInitialStartTime()
    {
        return EJPojoProperty.getPropertyInitialValue(_startTime);
    }

    @EJFieldName("WORK_DESCRIPTION")
    public String getWorkDescription()
    {
        return EJPojoProperty.getPropertyValue(_workDescription);
    }

    @EJFieldName("WORK_DESCRIPTION")
    public void setWorkDescription(String workDescription)
    {
        _workDescription = EJPojoProperty.setPropertyValue(_workDescription, workDescription);
    }

    @EJFieldName("WORK_DESCRIPTION")
    public String getInitialWorkDescription()
    {
        return EJPojoProperty.getPropertyInitialValue(_workDescription);
    }

    @EJFieldName("CUPT_ID")
    public Integer getCuptId()
    {
        return EJPojoProperty.getPropertyValue(_cuptId);
    }

    @EJFieldName("CUPT_ID")
    public void setCuptId(Integer cuptId)
    {
        _cuptId = EJPojoProperty.setPropertyValue(_cuptId, cuptId);
    }

    @EJFieldName("CUPT_ID")
    public Integer getInitialCuptId()
    {
        return EJPojoProperty.getPropertyInitialValue(_cuptId);
    }

    @EJFieldName("CUPR_ID")
    public Integer getCuprId()
    {
        return EJPojoProperty.getPropertyValue(_cuprId);
    }

    @EJFieldName("CUPR_ID")
    public void setCuprId(Integer cuprId)
    {
        _cuprId = EJPojoProperty.setPropertyValue(_cuprId, cuprId);
    }

    @EJFieldName("CUPR_ID")
    public Integer getInitialCuprId()
    {
        return EJPojoProperty.getPropertyInitialValue(_cuprId);
    }

    @EJFieldName("USER_ID")
    public Integer getUserId()
    {
        return EJPojoProperty.getPropertyValue(_userId);
    }

    @EJFieldName("USER_ID")
    public void setUserId(Integer userId)
    {
        _userId = EJPojoProperty.setPropertyValue(_userId, userId);
    }

    @EJFieldName("USER_ID")
    public Integer getInitialUserId()
    {
        return EJPojoProperty.getPropertyInitialValue(_userId);
    }

    
    

    @EJFieldName("COMPANY_ID")
    public Integer getCompanyId()
    {
        return EJPojoProperty.getPropertyValue(_companyId);
    }

    @EJFieldName("COMPANY_ID")
    public void setCompanyId(Integer companyId)
    {
        _companyId = EJPojoProperty.setPropertyValue(_companyId, companyId);
    }

    @EJFieldName("COMPANY_ID")
    public Integer getInitialCompanyId()
    {
        return EJPojoProperty.getPropertyInitialValue(_companyId);
    }
    
    @EJFieldName("WORK_DATE")
    public Date getWorkDate()
    {
        return EJPojoProperty.getPropertyValue(_workDate);
    }

    @EJFieldName("WORK_DATE")
    public void setWorkDate(Date workDate)
    {
        _workDate = EJPojoProperty.setPropertyValue(_workDate, workDate);
    }

    @EJFieldName("WORK_DATE")
    public Date getInitialWorkDate()
    {
        return EJPojoProperty.getPropertyInitialValue(_workDate);
    }

    @EJFieldName("HOURS_WORKED")
    public String getHoursWorked()
    {
        return EJPojoProperty.getPropertyValue(_hoursWorked);
    }

    @EJFieldName("HOURS_WORKED")
    public void setHoursWorked(String hoursWorked)
    {
        _hoursWorked = EJPojoProperty.setPropertyValue(_hoursWorked, hoursWorked);
    }

    @EJFieldName("HOURS_WORKED")
    public String getInitialHoursWorked()
    {
        return EJPojoProperty.getPropertyInitialValue(_hoursWorked);
    }

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_invpId);
        EJPojoProperty.clearInitialValue(_endTime);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_startTime);
        EJPojoProperty.clearInitialValue(_workDescription);
        EJPojoProperty.clearInitialValue(_cuptId);
        EJPojoProperty.clearInitialValue(_cuprId);
        EJPojoProperty.clearInitialValue(_userId);
        EJPojoProperty.clearInitialValue(_workDate);
        EJPojoProperty.clearInitialValue(_hoursWorked);
        EJPojoProperty.clearInitialValue(_companyId);
    }

}