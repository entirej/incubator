package org.entirej.ejinvoice.forms.timeentry;

import java.math.BigDecimal;
import java.sql.Date;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class TimeEntryProject
{
    private EJPojoProperty<Integer>    _id;
    private EJPojoProperty<String>     _description;
    private EJPojoProperty<String>     _name;
    private EJPojoProperty<Integer>    _userId;
    private EJPojoProperty<Integer>    _customerId;
    private EJPojoProperty<Date>       _startDate;
    private EJPojoProperty<Date>       _endDate;
    private EJPojoProperty<String>     _status;
    private EJPojoProperty<String>     _notes;

    private EJPojoProperty<Integer>    _processCprId;
    private EJPojoProperty<String>     _processNotes;
    private EJPojoProperty<String>     _processName;
    private EJPojoProperty<BigDecimal> _processPayRate;
    private EJPojoProperty<Integer>    _processUserId;
    private EJPojoProperty<Integer>    _processVatId;
    
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

    @EJFieldName("DESCRIPTION")
    public String getDescription()
    {
        return EJPojoProperty.getPropertyValue(_description);
    }

    @EJFieldName("DESCRIPTION")
    public void setDescription(String description)
    {
        _description = EJPojoProperty.setPropertyValue(_description, description);
    }

    @EJFieldName("DESCRIPTION")
    public String getInitialDescription()
    {
        return EJPojoProperty.getPropertyInitialValue(_description);
    }

    @EJFieldName("NAME")
    public String getName()
    {
        return EJPojoProperty.getPropertyValue(_name);
    }

    @EJFieldName("NAME")
    public void setName(String name)
    {
        _name = EJPojoProperty.setPropertyValue(_name, name);
    }

    @EJFieldName("NAME")
    public String getInitialName()
    {
        return EJPojoProperty.getPropertyInitialValue(_name);
    }

    @EJFieldName("STATUS")
    public String getStatus()
    {
        return EJPojoProperty.getPropertyValue(_status);
    }

    @EJFieldName("STATUS")
    public void setStatus(String status)
    {
        _status = EJPojoProperty.setPropertyValue(_status, status);
    }

    @EJFieldName("STATUS")
    public String getInitialStatus()
    {
        return EJPojoProperty.getPropertyInitialValue(_status);
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

    @EJFieldName("CUSTOMER_ID")
    public Integer getCustomerId()
    {
        return EJPojoProperty.getPropertyValue(_customerId);
    }

    @EJFieldName("CUSTOMER_ID")
    public void setCustomerId(Integer customerId)
    {
        _customerId = EJPojoProperty.setPropertyValue(_customerId, customerId);
    }

    @EJFieldName("CUSTOMER_ID")
    public Integer getInitialCustomerId()
    {
        return EJPojoProperty.getPropertyInitialValue(_customerId);
    }

    @EJFieldName("START_DATE")
    public Date getStartDate()
    {
        return EJPojoProperty.getPropertyValue(_startDate);
    }

    @EJFieldName("START_DATE")
    public void setStartDate(Date startDate)
    {
        _startDate = EJPojoProperty.setPropertyValue(_startDate, startDate);
    }

    @EJFieldName("START_DATE")
    public Date getInitialStartDate()
    {
        return EJPojoProperty.getPropertyInitialValue(_startDate);
    }
    
    @EJFieldName("END_DATE")
    public Date getEndDate()
    {
        return EJPojoProperty.getPropertyValue(_endDate);
    }

    @EJFieldName("END_DATE")
    public void setEndDate(Date endDate)
    {
        _endDate = EJPojoProperty.setPropertyValue(_endDate, endDate);
    }

    @EJFieldName("END_DATE")
    public Date getInitialEndDate()
    {
        return EJPojoProperty.getPropertyInitialValue(_endDate);
    }
    
    @EJFieldName("NOTES")
    public String getNotes()
    {
        return EJPojoProperty.getPropertyValue(_notes);
    }

    @EJFieldName("NOTES")
    public void setNotes(String notes)
    {
        _notes = EJPojoProperty.setPropertyValue(_notes, notes);
    }

    @EJFieldName("NOTES")
    public String getInitialNotes()
    {
        return EJPojoProperty.getPropertyInitialValue(_notes);
    }
    
    
    
    
    
    
    
    
    
    
    
    @EJFieldName("PROCESS_CPR_ID")
    public Integer getProcessCprId()
    {
        return EJPojoProperty.getPropertyValue(_processCprId);
    }

    @EJFieldName("PROCESS_CPR_ID")
    public void setProcessCprId(Integer processCprId)
    {
        _processCprId = EJPojoProperty.setPropertyValue(_processCprId, processCprId);
    }

    @EJFieldName("PROCESS_CPR_ID")
    public Integer getInitialProcessCprId()
    {
        return EJPojoProperty.getPropertyInitialValue(_processCprId);
    }

    @EJFieldName("PROCESS_NOTES")
    public String getProcessNotes()
    {
        return EJPojoProperty.getPropertyValue(_processNotes);
    }

    @EJFieldName("PROCESS_NOTES")
    public void setProcessNotes(String processNotes)
    {
        _processNotes = EJPojoProperty.setPropertyValue(_processNotes, processNotes);
    }

    @EJFieldName("PROCESS_NOTES")
    public String getInitialProcessNotes()
    {
        return EJPojoProperty.getPropertyInitialValue(_processNotes);
    }

    @EJFieldName("PROCESS_NAME")
    public String getProcessName()
    {
        return EJPojoProperty.getPropertyValue(_processName);
    }

    @EJFieldName("PROCESS_NAME")
    public void setProcessName(String processName)
    {
        _processName = EJPojoProperty.setPropertyValue(_processName, processName);
    }

    @EJFieldName("PROCESS_NAME")
    public String getInitialProcessName()
    {
        return EJPojoProperty.getPropertyInitialValue(_processName);
    }

    @EJFieldName("PROCESS_PAY_RATE")
    public BigDecimal getProcessPayRate()
    {
        return EJPojoProperty.getPropertyValue(_processPayRate);
    }

    @EJFieldName("PROCESS_PAY_RATE")
    public void setProcessPayRate(BigDecimal processPayRate)
    {
        _processPayRate = EJPojoProperty.setPropertyValue(_processPayRate, processPayRate);
    }

    @EJFieldName("PROCESS_PAY_RATE")
    public BigDecimal getInitialProcessPayRate()
    {
        return EJPojoProperty.getPropertyInitialValue(_processPayRate);
    }

    @EJFieldName("PROCESS_USER_ID")
    public Integer getProcessUserId()
    {
        return EJPojoProperty.getPropertyValue(_processUserId);
    }

    @EJFieldName("PROCESS_USER_ID")
    public void setProcessUserId(Integer processUserId)
    {
        _processUserId = EJPojoProperty.setPropertyValue(_processUserId, processUserId);
    }

    @EJFieldName("PROCESS_USER_ID")
    public Integer getInitialProcessUserId()
    {
        return EJPojoProperty.getPropertyInitialValue(_processUserId);
    }

    @EJFieldName("PROCESS_VAT_ID")
    public Integer getProcessVatId()
    {
        return EJPojoProperty.getPropertyValue(_processVatId);
    }

    @EJFieldName("PROCESS_VAT_ID")
    public void setProcessVatId(Integer processVatId)
    {
        _processVatId = EJPojoProperty.setPropertyValue(_processVatId, processVatId);
    }

    @EJFieldName("PROCESS_VAT_ID")
    public Integer getInitialProcessVatId()
    {
        return EJPojoProperty.getPropertyInitialValue(_processVatId);
    }
    
    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_description);
        EJPojoProperty.clearInitialValue(_name);
        EJPojoProperty.clearInitialValue(_userId);
        EJPojoProperty.clearInitialValue(_customerId);
        EJPojoProperty.clearInitialValue(_startDate);
        EJPojoProperty.clearInitialValue(_endDate);
        EJPojoProperty.clearInitialValue(_status);
        EJPojoProperty.clearInitialValue(_notes);
        
        EJPojoProperty.clearInitialValue(_processCprId);
        EJPojoProperty.clearInitialValue(_processNotes);
        EJPojoProperty.clearInitialValue(_processName);
        EJPojoProperty.clearInitialValue(_processPayRate);
        EJPojoProperty.clearInitialValue(_processUserId);
        EJPojoProperty.clearInitialValue(_processVatId);
    }

}