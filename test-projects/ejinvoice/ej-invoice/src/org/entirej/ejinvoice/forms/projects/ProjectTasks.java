package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class ProjectTasks
{
    private EJPojoProperty<Integer>    _id;
    private EJPojoProperty<Integer>    _cprId;
    private EJPojoProperty<String>     _notes;
    private EJPojoProperty<String>     _name;
    private EJPojoProperty<BigDecimal> _payRate;
    private EJPojoProperty<Integer>    _userId;
    private EJPojoProperty<BigDecimal> _fixPrice;
    private EJPojoProperty<String>     _invoiceable;
    private EJPojoProperty<Integer>    _status;

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

    @EJFieldName("CPR_ID")
    public Integer getCprId()
    {
        return EJPojoProperty.getPropertyValue(_cprId);
    }

    @EJFieldName("CPR_ID")
    public void setCprId(Integer cprId)
    {
        _cprId = EJPojoProperty.setPropertyValue(_cprId, cprId);
    }

    @EJFieldName("CPR_ID")
    public Integer getInitialCprId()
    {
        return EJPojoProperty.getPropertyInitialValue(_cprId);
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

    @EJFieldName("PAY_RATE")
    public BigDecimal getPayRate()
    {
        return EJPojoProperty.getPropertyValue(_payRate);
    }

    @EJFieldName("PAY_RATE")
    public void setPayRate(BigDecimal payRate)
    {
        _payRate = EJPojoProperty.setPropertyValue(_payRate, payRate);
    }

    @EJFieldName("PAY_RATE")
    public BigDecimal getInitialPayRate()
    {
        return EJPojoProperty.getPropertyInitialValue(_payRate);
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
    
    @EJFieldName("INVOICEABLE")
    public String getInvoiceable()
    {
        return EJPojoProperty.getPropertyValue(_invoiceable);
    }

    @EJFieldName("INVOICEABLE")
    public void setInvoiceable(String invoiceable)
    {
        _invoiceable =  EJPojoProperty.setPropertyValue(_invoiceable, invoiceable);
    }

    @EJFieldName("INVOICEABLE")
    public String getInitialInvoiceable()
    {
        return EJPojoProperty.getPropertyInitialValue(_invoiceable);
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
    
    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_cprId);
        EJPojoProperty.clearInitialValue(_notes);
        EJPojoProperty.clearInitialValue(_name);
        EJPojoProperty.clearInitialValue(_payRate);
        EJPojoProperty.clearInitialValue(_userId);
        EJPojoProperty.clearInitialValue(_fixPrice);
        EJPojoProperty.clearInitialValue(_invoiceable);
        EJPojoProperty.clearInitialValue(_status);
    }

}