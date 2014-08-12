package org.entirej.ejinvoice.forms.projects;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class OpenItem
{
    private EJPojoProperty<String> _displayValue;
    private EJPojoProperty<Integer> _headerNumber;
    private EJPojoProperty<Integer> _customerId;
    private EJPojoProperty<String>  _customerName;
    private EJPojoProperty<String>  _projectName;
    private EJPojoProperty<String>  _taskName;
    private EJPojoProperty<Integer> _projectId;
    private EJPojoProperty<Integer> _taskId;

    @EJFieldName("DISPLAY_VALUE")
    public String getDisplayValue()
    {
        return EJPojoProperty.getPropertyValue(_displayValue);
    }

    @EJFieldName("DISPLAY_VALUE")
    public void setDisplayValue(String displayValue)
    {
        _displayValue = EJPojoProperty.setPropertyValue(_displayValue, displayValue);
    }

    @EJFieldName("DISPLAY_VALUE")
    public String getInitialDisplayValue()
    {
        return EJPojoProperty.getPropertyInitialValue(_displayValue);
    }
    
    @EJFieldName("HEADER_NUMBER")
    public Integer getHeaderNumber()
    {
        return EJPojoProperty.getPropertyValue(_headerNumber);
    }

    @EJFieldName("HEADER_NUMBER")
    public void setHeaderNumber(Integer headerNumber)
    {
        _headerNumber = EJPojoProperty.setPropertyValue(_headerNumber, headerNumber);
    }

    @EJFieldName("HEADER_NUMBER")
    public Integer getInitialHeaderNumber()
    {
        return EJPojoProperty.getPropertyInitialValue(_headerNumber);
    }

    @EJFieldName("CUSTOMER_NAME")
    public String getCustomerName()
    {
        return EJPojoProperty.getPropertyValue(_customerName);
    }

    @EJFieldName("CUSTOMER_NAME")
    public void setCustomerName(String customerName)
    {
        _customerName = EJPojoProperty.setPropertyValue(_customerName, customerName);
    }

    @EJFieldName("CUSTOMER_NAME")
    public String getInitialCustomerName()
    {
        return EJPojoProperty.getPropertyInitialValue(_customerName);
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

    @EJFieldName("PROJECT_ID")
    public Integer getProjectId()
    {
        return EJPojoProperty.getPropertyValue(_projectId);
    }

    @EJFieldName("PROJECT_ID")
    public void setProjectId(Integer projectId)
    {
        _projectId = EJPojoProperty.setPropertyValue(_projectId, projectId);
    }

    @EJFieldName("PROJECT_ID")
    public Integer getInitialProjectId()
    {
        return EJPojoProperty.getPropertyInitialValue(_projectId);
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

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_displayValue);
        EJPojoProperty.clearInitialValue(_headerNumber);
        EJPojoProperty.clearInitialValue(_customerName);
        EJPojoProperty.clearInitialValue(_customerId);
        EJPojoProperty.clearInitialValue(_projectId);
        EJPojoProperty.clearInitialValue(_taskId);
        EJPojoProperty.clearInitialValue(_projectName);
        EJPojoProperty.clearInitialValue(_taskName);
    }

}