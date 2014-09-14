package org.entirej.ejinvoice.forms.projects;

import java.util.List;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementExecutor;

public class ProjectTaskService
{

    public String getStatus(EJForm form, Integer taskId)
    {
        if (taskId == null)
        {
            throw new EJApplicationException("No task id passed ProjectTaskService.getStatus");
        }
        EJStatementExecutor executor = new EJStatementExecutor();

        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("COMPANY_ID", companyId));
        criteria.add(EJRestrictions.equals("ID", taskId));
        List<Project> projects = executor.executeQuery(Project.class, form, "SELECT STATUS FROM CUSTOMER_PROJECT_TASKS", criteria);

        if (projects.size() > 0)
        {
            return projects.get(0).getStatus();
        }
        else
        {
            return "NONE";
        }
    }
}
