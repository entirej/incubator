package org.entirej.ejinvoice.forms.invoice;

import java.math.BigDecimal;
import java.sql.Date;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class ProjectTime
{
    private EJPojoProperty<Integer>    _projectId;
    private EJPojoProperty<String>     _projectDescription;
    private EJPojoProperty<String>     _projectName;
    private EJPojoProperty<Integer>    _projectUserId;
    private EJPojoProperty<Integer>    _customerId;
    private EJPojoProperty<String>     _projectStatus;

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

    @EJFieldName("PROJECT_DESCRIPTION")
    public String getProjectDescription()
    {
        return EJPojoProperty.getPropertyValue(_projectDescription);
    }

    @EJFieldName("PROJECT_DESCRIPTION")
    public void setDescription(String projectDescription)
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


    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_projectId);
        EJPojoProperty.clearInitialValue(_projectDescription);
        EJPojoProperty.clearInitialValue(_projectName);
        EJPojoProperty.clearInitialValue(_projectUserId);
        EJPojoProperty.clearInitialValue(_customerId);
    }

}