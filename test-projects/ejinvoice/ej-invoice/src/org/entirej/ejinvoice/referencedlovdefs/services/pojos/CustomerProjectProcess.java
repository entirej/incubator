package org.entirej.ejinvoice.referencedlovdefs.services.pojos;

import java.math.BigDecimal;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class CustomerProjectProcess
{
    private EJPojoProperty<Integer>    _id;
    private EJPojoProperty<Integer>    _cprId;
    private EJPojoProperty<String>     _notes;
    private EJPojoProperty<String>     _projectDescription;
    private EJPojoProperty<String>     _projectName;
    private EJPojoProperty<String>     _processName;
    private EJPojoProperty<BigDecimal> _payRate;
    private EJPojoProperty<Integer>    _companyId;

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

    @EJFieldName("PROJECT_DESCRIPTION")
    public String getProjectDescription()
    {
        return EJPojoProperty.getPropertyValue(_projectDescription);
    }

    @EJFieldName("PROJECT_DESCRIPTION")
    public void setProjectDescription(String projectDescription)
    {
        _projectDescription = EJPojoProperty.setPropertyValue(_projectDescription, projectDescription);
    }

    @EJFieldName("PROJECT_DESCRIPTION")
    public String getInitialProjectDescription()
    {
        return EJPojoProperty.getPropertyInitialValue(_projectDescription);
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


    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_cprId);
        EJPojoProperty.clearInitialValue(_notes);
        EJPojoProperty.clearInitialValue(_processName);
        EJPojoProperty.clearInitialValue(_projectName);
        EJPojoProperty.clearInitialValue(_payRate);
        EJPojoProperty.clearInitialValue(_companyId);
    }

}