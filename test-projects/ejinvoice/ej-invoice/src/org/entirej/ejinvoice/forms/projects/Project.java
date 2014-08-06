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
    private EJPojoProperty<Integer>    _status;
    private EJPojoProperty<String>     _notes;

    private EJPojoProperty<BigDecimal> _fixPrice;
    private EJPojoProperty<String>     _invoiceable;
    private EJPojoProperty<Integer>    _ccyId;
    private EJPojoProperty<String>     _ccyCode;
    private EJPojoProperty<Integer>    _vatId;
    private EJPojoProperty<BigDecimal> _vatRate;

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
        _invoiceable = EJPojoProperty.setPropertyValue(_invoiceable, invoiceable);
    }

    @EJFieldName("INVOICEABLE")
    public String getInitialInvoiceable()
    {
        return EJPojoProperty.getPropertyInitialValue(_invoiceable);
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
    
    @EJFieldName("VAT_RATE")
    public BigDecimal getVatRate()
    {
        return EJPojoProperty.getPropertyValue(_vatRate);
    }

    @EJFieldName("VAT_RATE")
    public void setVatRate(BigDecimal vatRate)
    {
        _vatRate = EJPojoProperty.setPropertyValue(_vatRate, vatRate);
    }

    @EJFieldName("VAT_RATE")
    public BigDecimal getInitialVatRate()
    {
        return EJPojoProperty.getPropertyInitialValue(_vatRate);
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
        EJPojoProperty.clearInitialValue(_fixPrice);
        EJPojoProperty.clearInitialValue(_invoiceable);
        EJPojoProperty.clearInitialValue(_ccyId);
        EJPojoProperty.clearInitialValue(_ccyCode);
        EJPojoProperty.clearInitialValue(_vatId);
        EJPojoProperty.clearInitialValue(_vatRate);
    }

}