package org.entirej.ejinvoice.referencedlovdefs.services;

import java.util.ArrayList;
import java.util.List;

import org.entirej.ejinvoice.referencedlovdefs.services.pojos.ProjectStatus;
import org.entirej.ejinvoice.referencedlovdefs.services.pojos.TaskStatus;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;

public class TaskStatusBlockService implements EJBlockService<TaskStatus>
{
    public TaskStatusBlockService()
    {
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<TaskStatus> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        ArrayList<TaskStatus> statuses = new ArrayList<TaskStatus>();
        if (queryCriteria.containsRestriction("CODE"))
        {
            String code = (String) queryCriteria.getRestriction("CODE").getValue();

            switch (code)
            {
                case "NEW":
                    statuses.add(new TaskStatus("NEW", "New", "A new task that cannot yet be used for time entry"));
                    break;
                case "INWORK":
                    statuses.add(new TaskStatus("INWORK", "In Work", "The task is now free for time entry"));
                    break;
                case "ONHOLD":
                    statuses.add(new TaskStatus("ONHOLD", "On Hold", "The task is still open but on hold so cannot be used for time entry"));
                    break;
                case "COMPLETED":
                    statuses.add(new TaskStatus("COMPLETED", "Completed", "The task has been completed and no longer open for time entry"));
                    break;
                case "DELETED":
                    statuses.add(new TaskStatus("DELETED", "Deleted", "The task is deleted and not seen in the application unless required"));
                    break;
            }
        }
        else
        {
            statuses.add(new TaskStatus("NEW", "New", "A new task that cannot yet be used for time entry"));
            statuses.add(new TaskStatus("INWORK", "In Work", "The task is now free for time entry"));
            statuses.add(new TaskStatus("ONHOLD", "On Hold", "The task is still open but on hold so cannot be used for time entry"));
            statuses.add(new TaskStatus("COMPLETED", "Completed", "The task has been completed and no longer open for time entry"));
            statuses.add(new TaskStatus("DELETED", "Deleted", "The task is deleted and not seen in the application unless required"));
        }
        return statuses;
    }

    @Override
    public void executeInsert(EJForm form, List<TaskStatus> newRecords)
    {
    }

    @Override
    public void executeUpdate(EJForm form, List<TaskStatus> updateRecords)
    {
    }

    @Override
    public void executeDelete(EJForm form, List<TaskStatus> recordsToDelete)
    {
    }

}