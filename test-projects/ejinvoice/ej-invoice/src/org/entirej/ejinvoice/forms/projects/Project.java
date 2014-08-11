package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.sql.Date;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class Project
{
    private EJPojoProperty<Integer>    _id;
    private EJPojoProperty<String>     _description;
    private EJPojoProperty<String>     _name;
    private EJPojoProperty<Integer>    _userId;
    private EJPojoProperty<Integer>    _customerId;
    private EJPojoProperty<Date>       _startDate;
    private EJPojoProperty<Date>       _endDate;
    private EJPojoProperty<Integer>     _status;
    private EJPojoProperty<String>     _notes;

    private EJPojoProperty<String>     _invoiceable;
    private EJPojoProperty<BigDecimal> _fixPrice;
    private EJPojoProperty<Integer>    _ccyId;
    private EJPojoProperty<String>     _ccyCode;
    private EJPojoProperty<Integer>    _vatId;
    
    private EJPojoProperty<Integer>    _taskCprId;
    private EJPojoProperty<String>     _taskNotes;
    private EJPojoProperty<String>     _taskName;
    private EJPojoProperty<BigDecimal> _taskPayRate;
    private EJPojoProperty<Integer>    _taskUserId;
    private EJPojoProperty<BigDecimal> _taskFixPrice;
    private EJPojoProperty<Integer>    _taskStatus;
    private EJPojoProperty<String>     _taskInvoiceable;

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
    public Integer getStatus()
    {
        return EJPojoProperty.getPropertyValue(_status);
    }

    @EJFieldName("STATUS")
    public void setStatus(Integer status)
    {
        _status = EJPojoProperty.setPropertyValue(_status, status);
    }

    @EJFieldName("STATUS")
    public Integer getInitialStatus()
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

    @EJFieldName("TASK_CPR_ID")
    public Integer getTaskCprId()
    {
        return EJPojoProperty.getPropertyValue(_taskCprId);
    }

    @EJFieldName("TASK_CPR_ID")
    public void setTaskCprId(Integer taskCprId)
    {
        _taskCprId = EJPojoProperty.setPropertyValue(_taskCprId, taskCprId);
    }

    @EJFieldName("TASK_CPR_ID")
    public Integer getInitialTaskCprId()
    {
        return EJPojoProperty.getPropertyInitialValue(_taskCprId);
    }

    @EJFieldName("TASK_NOTES")
    public String getTaskNotes()
    {
        return EJPojoProperty.getPropertyValue(_taskNotes);
    }

    @EJFieldName("TASK_NOTES")
    public void setTaskNotes(String taskNotes)
    {
        _taskNotes = EJPojoProperty.setPropertyValue(_taskNotes, taskNotes);
    }

    @EJFieldName("TASK_NOTES")
    public String getInitialTaskNotes()
    {
        return EJPojoProperty.getPropertyInitialValue(_taskNotes);
    }

    @EJFieldName("TASK_NAME")
    public String getTaskName()
    {
        return EJPojoProperty.getPropertyValue(_taskName);
    }

    @EJFieldName("TASK_NAME")
    public void setTaskName(String taskName)
    {
        _taskName = EJPojoProperty.setPropertyValue(_taskName, taskName);
    }

    @EJFieldName("TASK_NAME")
    public String getInitialTaskName()
    {
        return EJPojoProperty.getPropertyInitialValue(_taskName);
    }

    @EJFieldName("TASK_PAY_RATE")
    public BigDecimal getTaskPayRate()
    {
        return EJPojoProperty.getPropertyValue(_taskPayRate);
    }

    @EJFieldName("TASK_PAY_RATE")
    public void setTaskPayRate(BigDecimal taskPayRate)
    {
        _taskPayRate = EJPojoProperty.setPropertyValue(_taskPayRate, taskPayRate);
    }

    @EJFieldName("TASK_PAY_RATE")
    public BigDecimal getInitialTaskPayRate()
    {
        return EJPojoProperty.getPropertyInitialValue(_taskPayRate);
    }

    @EJFieldName("TASK_USER_ID")
    public Integer getTaskUserId()
    {
        return EJPojoProperty.getPropertyValue(_taskUserId);
    }

    @EJFieldName("TASK_USER_ID")
    public void setTaskUserId(Integer taskUserId)
    {
        _taskUserId = EJPojoProperty.setPropertyValue(_taskUserId, taskUserId);
    }

    @EJFieldName("TASK_USER_ID")
    public Integer getInitialTaskUserId()
    {
        return EJPojoProperty.getPropertyInitialValue(_taskUserId);
    }

    @EJFieldName("VAT_ID")
    public Integer getVatId()
    {
        return EJPojoProperty.getPropertyValue(_vatId);
    }

    @EJFieldName("VAT_ID")
    public void setVatId(Integer vatId)
    {
        _vatId = EJPojoProperty.setPropertyValue(_vatId, vatId);
    }

    @EJFieldName("VAT_ID")
    public Integer getInitialVatId()
    {
        return EJPojoProperty.getPropertyInitialValue(_vatId);
    }

    @EJFieldName("INVOICEABLE")
    public String getInvoiceable()
    {
        return EJPojoProperty.getPropertyValue(_invoiceable);
    }

    @EJFieldName("INVOICEABLE")
    public void setInvoiceable(String invoiceable)
    {
        _invoiceable = EJPojoProperty.setPropertyValue(_invoiceable, invoiceable);
    }

    @EJFieldName("INVOICEABLE")
    public String getInitialInvoiceable()
    {
        return EJPojoProperty.getPropertyInitialValue(_invoiceable);
    }

    @EJFieldName("FIX_PRICE")
    public BigDecimal getFixPrice()
    {
        return EJPojoProperty.getPropertyValue(_fixPrice);
    }

    @EJFieldName("FIX_PRICE")
    public void setFixPrice(BigDecimal fixPrice)
    {
        _fixPrice = EJPojoProperty.setPropertyValue(_fixPrice, fixPrice);
    }

    @EJFieldName("FIX_PRICE")
    public BigDecimal getInitialFixPrice()
    {
        return EJPojoProperty.getPropertyInitialValue(_fixPrice);
    }

    @EJFieldName("CCY_ID")
    public Integer getCcyId()
    {
        return EJPojoProperty.getPropertyValue(_ccyId);
    }

    @EJFieldName("CCY_ID")
    public void setCcyId(Integer ccyId)
    {
        _ccyId = EJPojoProperty.setPropertyValue(_ccyId, ccyId);
    }

    @EJFieldName("CCY_ID")
    public Integer getInitialCcyId()
    {
        return EJPojoProperty.getPropertyInitialValue(_ccyId);
    }

    @EJFieldName("CCY_CODE")
    public String getCcyCode()
    {
        return EJPojoProperty.getPropertyValue(_ccyCode);
    }

    @EJFieldName("CCY_CODE")
    public void setCcyCode(String ccyCode)
    {
        _ccyCode = EJPojoProperty.setPropertyValue(_ccyCode, ccyCode);
    }

    @EJFieldName("CCY_CODE")
    public String getInitialCcyCode()
    {
        return EJPojoProperty.getPropertyInitialValue(_ccyCode);
    }

    @EJFieldName("TASK_FIX_PRICE")
    public BigDecimal getTaskFixPrice()
    {
        return EJPojoProperty.getPropertyValue(_taskFixPrice);
    }

    @EJFieldName("TASK_FIX_PRICE")
    public void setTaskFixPrice(BigDecimal taskFixPrice)
    {
        _taskFixPrice = EJPojoProperty.setPropertyValue(_taskFixPrice, taskFixPrice);
    }

    @EJFieldName("TASK_FIX_PRICE")
    public BigDecimal getInitialTaskFixPrice()
    {
        return EJPojoProperty.getPropertyInitialValue(_taskFixPrice);
    }

    @EJFieldName("TASK_STATUS")
    public Integer getTaskStatus()
    {
        return EJPojoProperty.getPropertyValue(_taskStatus);
    }

    @EJFieldName("TASK_STATUS")
    public void setTaskStatus(Integer taskStatus)
    {
        _taskStatus = EJPojoProperty.setPropertyValue(_taskStatus, taskStatus);
    }

    @EJFieldName("TASK_STATUS")
    public Integer getInitialTaskStatus()
    {
        return EJPojoProperty.getPropertyInitialValue(_taskStatus);
    }

    @EJFieldName("TASK_INVOICEABLE")
    public String getTaskInvoiceable()
    {
        return EJPojoProperty.getPropertyValue(_taskInvoiceable);
    }

    @EJFieldName("TASK_INVOICEABLE")
    public void setTaskInvoiceable(String taskInvoiceable)
    {
        _taskInvoiceable = EJPojoProperty.setPropertyValue(_taskInvoiceable, taskInvoiceable);
    }

    @EJFieldName("TASK_INVOICEABLE")
    public String getInitialTaskInvoiceable()
    {
        return EJPojoProperty.getPropertyInitialValue(_taskInvoiceable);
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
 
        EJPojoProperty.clearInitialValue(_invoiceable);
        EJPojoProperty.clearInitialValue(_fixPrice);
        EJPojoProperty.clearInitialValue(_ccyId);
        EJPojoProperty.clearInitialValue(_ccyCode);

        EJPojoProperty.clearInitialValue(_taskCprId);
        EJPojoProperty.clearInitialValue(_taskNotes);
        EJPojoProperty.clearInitialValue(_taskName);
        EJPojoProperty.clearInitialValue(_taskPayRate);
        EJPojoProperty.clearInitialValue(_taskUserId);
        EJPojoProperty.clearInitialValue(_vatId);
        EJPojoProperty.clearInitialValue(_taskFixPrice);
        EJPojoProperty.clearInitialValue(_taskStatus);
        EJPojoProperty.clearInitialValue(_taskInvoiceable);
    }

}