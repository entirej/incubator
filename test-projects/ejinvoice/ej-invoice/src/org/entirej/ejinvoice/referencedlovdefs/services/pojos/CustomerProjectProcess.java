package org.entirej.ejinvoice.referencedlovdefs.services.pojos;

import java.math.BigDecimal;
import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class CustomerProjectProcess
{
    private EJPojoProperty<Integer>    _id;
    private EJPojoProperty<Integer>    _cprId;
    private EJPojoProperty<String>     _notes;
    private EJPojoProperty<String>     _projectName;
    private EJPojoProperty<String>     _processName;
    private EJPojoProperty<BigDecimal> _payRate;
    private EJPojoProperty<Integer>    _userId;
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

    @EJFieldName("PROJECT_NAME")
    public String getProjectName()
    {
        return EJPojoProperty.getPropertyValue(_projectName);
    }

    @EJFieldName("PROJECT_NAME")
    public void setProjectName(String projectName)
    {
        _projectName = EJPojoProperty.setPropertyValue(_projectName, projectName);
    }

    @EJFieldName("PROJECT_NAME")
    public String getInitialProjectName()
    {
        return EJPojoProperty.getPropertyInitialValue(_projectName);
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
        EJPojoProperty.clearInitialValue(_cprId);
        EJPojoProperty.clearInitialValue(_notes);
        EJPojoProperty.clearInitialValue(_processName);
        EJPojoProperty.clearInitialValue(_projectName);
        EJPojoProperty.clearInitialValue(_payRate);
        EJPojoProperty.clearInitialValue(_userId);
        EJPojoProperty.clearInitialValue(_vatId);
        EJPojoProperty.clearInitialValue(_vatRate);
    }

}