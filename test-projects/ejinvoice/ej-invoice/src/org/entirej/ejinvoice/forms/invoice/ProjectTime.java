package org.entirej.ejinvoice.forms.invoice;

import java.math.BigDecimal;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class ProjectTime
{
    private EJPojoProperty<Integer>    _headerRow;
    private EJPojoProperty<Integer>    _projectId;
    private EJPojoProperty<String>     _description;
    private EJPojoProperty<String>     _projectStatus;
    private EJPojoProperty<Integer>    _projectUserId;
    private EJPojoProperty<Integer>    _customerId;
    private EJPojoProperty<Integer>    _taskId;
    private EJPojoProperty<String>     _taskName;
    private EJPojoProperty<Integer>    _taskVatId;
    private EJPojoProperty<BigDecimal> _vatRate;
    private EJPojoProperty<String>     _vatName;
    private EJPojoProperty<BigDecimal> _taskPayRate;
    private EJPojoProperty<BigDecimal> _workHours;
    private EJPojoProperty<BigDecimal> _totalExclVat;
    private EJPojoProperty<BigDecimal> _totalInclVat;

    @EJFieldName("HEADER_ROW")
    public Integer getHeaderRow()
    {
        return EJPojoProperty.getPropertyValue(_headerRow);
    }

    @EJFieldName("HEADER_ROW")
    public void setHeaderRow(Integer headerRow)
    {
        _headerRow = EJPojoProperty.setPropertyValue(_headerRow, headerRow);
    }

    @EJFieldName("HEADER_ROW")
    public Integer getInitialHeaderRow()
    {
        return EJPojoProperty.getPropertyInitialValue(_headerRow);
    }

    @EJFieldName("PROJECT_ID")
    public Integer getProjectId()
    {
        return EJPojoProperty.getPropertyValue(_projectId);
    }

    @EJFieldName("PROJECT_ID")
    public void setId(Integer projectId)
    {
        _projectId = EJPojoProperty.setPropertyValue(_projectId, projectId);
    }

    @EJFieldName("PROJECT_ID")
    public Integer getInitialProjectId()
    {
        return EJPojoProperty.getPropertyInitialValue(_projectId);
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

    @EJFieldName("PROJECT_STATUS")
    public String getProjectStatus()
    {
        return EJPojoProperty.getPropertyValue(_projectStatus);
    }

    @EJFieldName("PROJECT_STATUS")
    public void setProjectStatus(String projectStatus)
    {
        _projectStatus = EJPojoProperty.setPropertyValue(_projectStatus, projectStatus);
    }

    @EJFieldName("PROJECT_STATUS")
    public String getInitialProjectStatus()
    {
        return EJPojoProperty.getPropertyInitialValue(_projectStatus);
    }

    @EJFieldName("PROJECT_USER_ID")
    public Integer getProjectUserId()
    {
        return EJPojoProperty.getPropertyValue(_projectUserId);
    }

    @EJFieldName("PROJECT_USER_ID")
    public void setProjectUserId(Integer projectUserId)
    {
        _projectUserId = EJPojoProperty.setPropertyValue(_projectUserId, projectUserId);
    }

    @EJFieldName("PROJECT_USER_ID")
    public Integer getInitialProjectUserId()
    {
        return EJPojoProperty.getPropertyInitialValue(_projectUserId);
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

    @EJFieldName("TASK_ID")
    public Integer getTaskId()
    {
        return EJPojoProperty.getPropertyValue(_taskId);
    }

    @EJFieldName("TASK_ID")
    public void setTaskId(Integer taskId)
    {
        _taskId = EJPojoProperty.setPropertyValue(_taskId, taskId);
    }

    @EJFieldName("TASK_ID")
    public Integer getInitialTaskId()
    {
        return EJPojoProperty.getPropertyInitialValue(_taskId);
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

    @EJFieldName("TASK_VAT_ID")
    public Integer getTaskVatId()
    {
        return EJPojoProperty.getPropertyValue(_taskVatId);
    }

    @EJFieldName("TASK_VAT_ID")
    public void setTaskVatId(Integer taskVatId)
    {
        _taskVatId = EJPojoProperty.setPropertyValue(_taskVatId, taskVatId);
    }

    @EJFieldName("TASK_VAT_ID")
    public Integer getInitialTaskVatId()
    {
        return EJPojoProperty.getPropertyInitialValue(_taskVatId);
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

    @EJFieldName("VAT_NAME")
    public String getVatName()
    {
        return EJPojoProperty.getPropertyValue(_vatName);
    }

    @EJFieldName("VAT_NAME")
    public void setVatName(String vatName)
    {
        _vatName = EJPojoProperty.setPropertyValue(_vatName, vatName);
    }

    @EJFieldName("VAT_NAME")
    public String getInitialVatName()
    {
        return EJPojoProperty.getPropertyInitialValue(_vatName);
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

    @EJFieldName("WORK_HOURS")
    public BigDecimal getWorkHours()
    {
        return EJPojoProperty.getPropertyValue(_workHours);
    }

    @EJFieldName("WORK_HOURS")
    public void setWorkHours(BigDecimal workHours)
    {
        _workHours = EJPojoProperty.setPropertyValue(_workHours, workHours);
    }

    @EJFieldName("WORK_HOURS")
    public BigDecimal getInitialWorkHours()
    {
        return EJPojoProperty.getPropertyInitialValue(_workHours);
    }

    @EJFieldName("TOTAL_EXCL_VAT")
    public BigDecimal getTotalExclVat()
    {
        return EJPojoProperty.getPropertyValue(_totalExclVat);
    }

    @EJFieldName("TOTAL_EXCL_VAT")
    public void setTotalExclVat(BigDecimal totalExclVat)
    {
        _totalExclVat = EJPojoProperty.setPropertyValue(_totalExclVat, totalExclVat);
    }

    @EJFieldName("TOTAL_EXCL_VAT")
    public BigDecimal getInitialTotalExclVat()
    {
        return EJPojoProperty.getPropertyInitialValue(_totalExclVat);
    }

    @EJFieldName("TOTAL_INCL_VAT")
    public BigDecimal getTotalInclVat()
    {
        return EJPojoProperty.getPropertyValue(_totalInclVat);
    }

    @EJFieldName("TOTAL_INCL_VAT")
    public void setTotalInclVat(BigDecimal totalInclVat)
    {
        _totalInclVat = EJPojoProperty.setPropertyValue(_totalInclVat, totalInclVat);
    }

    @EJFieldName("TOTAL_INCL_VAT")
    public BigDecimal getInitialTotalInclVat()
    {
        return EJPojoProperty.getPropertyInitialValue(_totalInclVat);
    }

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_headerRow);
        EJPojoProperty.clearInitialValue(_projectId);
        EJPojoProperty.clearInitialValue(_description);
        EJPojoProperty.clearInitialValue(_projectUserId);
        EJPojoProperty.clearInitialValue(_customerId);
        EJPojoProperty.clearInitialValue(_taskId);
        EJPojoProperty.clearInitialValue(_taskName);
        EJPojoProperty.clearInitialValue(_taskVatId);
        EJPojoProperty.clearInitialValue(_vatRate);
        EJPojoProperty.clearInitialValue(_vatName);
        EJPojoProperty.clearInitialValue(_taskPayRate);
        EJPojoProperty.clearInitialValue(_workHours);
        EJPojoProperty.clearInitialValue(_totalExclVat);
        EJPojoProperty.clearInitialValue(_totalInclVat);
    }

}