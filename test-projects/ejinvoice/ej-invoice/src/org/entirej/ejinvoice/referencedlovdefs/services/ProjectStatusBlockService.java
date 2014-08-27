package org.entirej.ejinvoice.referencedlovdefs.services;

import java.util.ArrayList;
import java.util.List;

import org.entirej.ejinvoice.referencedlovdefs.services.pojos.ProjectStatus;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;

public class ProjectStatusBlockService implements EJBlockService<ProjectStatus>
{
    public ProjectStatusBlockService()
    {
    }

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<ProjectStatus> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        ArrayList<ProjectStatus> statuses = new ArrayList<ProjectStatus>();

        if (queryCriteria.containsRestriction("CODE"))
        {
            String code = (String)queryCriteria.getRestriction("CODE").getValue();
            
            switch (code)
            {
                case "NEW":
                    statuses.add(new ProjectStatus("NEW", "New", "A new project that cannot yet be used for time entry"));
                    break;
                case "INWORK":
                    statuses.add(new ProjectStatus("INWORK", "In Work", "The project is now free for time entry"));
                    break;
                case "ONHOLD":
                    statuses.add(new ProjectStatus("ONHOLD", "On Hold", "The project is still open but on hold so cannot be used for time entry"));
                    break;
                case "COMPLETED":
                    statuses.add(new ProjectStatus("COMPLETED", "Completed", "The project has been completed and no longer open for time entry"));
                    break;
                case "DELETED":
                    statuses.add(new ProjectStatus("DELETED", "Deleted", "The project is deleted and not seen in the application unless required"));
                    break;
            }
        }
        else
        {
            statuses.add(new ProjectStatus("NEW", "New", "A new project that cannot yet be used for time entry"));
            statuses.add(new ProjectStatus("INWORK", "In Work", "The project is now free for time entry"));
            statuses.add(new ProjectStatus("ONHOLD", "On Hold", "The project is still open but on hold so cannot be used for time entry"));
            statuses.add(new ProjectStatus("COMPLETED", "Completed", "The project has been completed and no longer open for time entry"));
            statuses.add(new ProjectStatus("DELETED", "Deleted", "The project is deleted and not seen in the application unless required"));
        }
        return statuses;
    }

    @Override
    public void executeInsert(EJForm form, List<ProjectStatus> newRecords)
    {
    }

    @Override
    public void executeUpdate(EJForm form, List<ProjectStatus> updateRecords)
    {
    }

    @Override
    public void executeDelete(EJForm form, List<ProjectStatus> recordsToDelete)
    {
    }

}