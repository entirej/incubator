package org.entirej.ejinvoice.forms.timeentryoverview;

import java.math.BigDecimal;
import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class UserTotalHours {
	private EJPojoProperty<BigDecimal> _hours;
	private EJPojoProperty<Integer> _userId;
	private EJPojoProperty<String> _firstName;
	private EJPojoProperty<String> _lastName;
	private EJPojoProperty<String> _projectTask;
	private EJPojoProperty<String> _projectName;

	@EJFieldName("hours")
	public BigDecimal getHours() {
		return EJPojoProperty.getPropertyValue(_hours);
	}

	@EJFieldName("hours")
	public void setHours(BigDecimal hours) {
		_hours = EJPojoProperty.setPropertyValue(_hours, hours);
	}

	@EJFieldName("hours")
	public BigDecimal getInitialHours() {
		return EJPojoProperty.getPropertyInitialValue(_hours);
	}

	@EJFieldName("user_id")
	public Integer getUserId() {
		return EJPojoProperty.getPropertyValue(_userId);
	}

	@EJFieldName("user_id")
	public void setUserId(Integer userId) {
		_userId = EJPojoProperty.setPropertyValue(_userId, userId);
	}

	@EJFieldName("user_id")
	public Integer getInitialUserId() {
		return EJPojoProperty.getPropertyInitialValue(_userId);
	}

	@EJFieldName("first_name")
	public String getFirstName() {
		return EJPojoProperty.getPropertyValue(_firstName);
	}

	@EJFieldName("first_name")
	public void setFirstName(String firstName) {
		_firstName = EJPojoProperty.setPropertyValue(_firstName, firstName);
	}

	@EJFieldName("first_name")
	public String getInitialFirstName() {
		return EJPojoProperty.getPropertyInitialValue(_firstName);
	}

	@EJFieldName("last_name")
	public String getLastName() {
		return EJPojoProperty.getPropertyValue(_lastName);
	}

	@EJFieldName("last_name")
	public void setLastName(String lastName) {
		_lastName = EJPojoProperty.setPropertyValue(_lastName, lastName);
	}

	@EJFieldName("last_name")
	public String getInitialLastName() {
		return EJPojoProperty.getPropertyInitialValue(_lastName);
	}

	@EJFieldName("project_task")
	public String getProjectTask() {
		return EJPojoProperty.getPropertyValue(_projectTask);
	}

	@EJFieldName("project_task")
	public void setProjectTask(String projectTask) {
		_projectTask = EJPojoProperty.setPropertyValue(_projectTask,
				projectTask);
	}

	@EJFieldName("project_task")
	public String getInitialProjectTask() {
		return EJPojoProperty.getPropertyInitialValue(_projectTask);
	}

	@EJFieldName("project_name")
	public String getProjectName() {
		return EJPojoProperty.getPropertyValue(_projectName);
	}

	@EJFieldName("project_name")
	public void setProjectName(String projectName) {
		_projectName = EJPojoProperty.setPropertyValue(_projectName,
				projectName);
	}

	@EJFieldName("project_name")
	public String getInitialProjectName() {
		return EJPojoProperty.getPropertyInitialValue(_projectName);
	}

	public void clearInitialValues() {
		EJPojoProperty.clearInitialValue(_hours);
		EJPojoProperty.clearInitialValue(_userId);
		EJPojoProperty.clearInitialValue(_firstName);
		EJPojoProperty.clearInitialValue(_lastName);
		EJPojoProperty.clearInitialValue(_projectTask);
		EJPojoProperty.clearInitialValue(_projectName);
	}

}