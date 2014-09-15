package org.entirej.ejinvoice.menu;

import java.util.ArrayList;
import java.util.List;
import org.entirej.ejinvoice.menu.Menu;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementCriteria;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class MenuBlockService implements EJBlockService<Menu>
{
    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    @Override
    public List<Menu> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        ArrayList<Menu> menuItems = new ArrayList<Menu>();
        
        Menu companiesMenu = new Menu();
        companiesMenu.setIconName("/icons/companies.png");
        companiesMenu.setName("Companies");
        companiesMenu.setActionCommand("OPEN_COMPANIES");
        companiesMenu.setId(1);
        menuItems.add(companiesMenu);
        
        Menu companiesAllMenu = new Menu();
        companiesAllMenu.setName("All");
        companiesAllMenu.setActionCommand("OPEN_ALL_COMPANIES");
        companiesAllMenu.setId(2);
        companiesAllMenu.setParentId(1);
        menuItems.add(companiesAllMenu);
        
        Menu companiesActiveMenu = new Menu();
        companiesActiveMenu.setName("Active");
        companiesActiveMenu.setActionCommand("OPEN_ALL_COMPANIES");
        companiesActiveMenu.setId(3);
        companiesActiveMenu.setParentId(1);
        menuItems.add(companiesActiveMenu);
        
        
        

        Menu projectsMenu = new Menu();
        projectsMenu.setIconName("/icons/projects.png");
        projectsMenu.setName("Projects");
        projectsMenu.setActionCommand("OPEN_PROJECTS");
        projectsMenu.setId(4);
        menuItems.add(projectsMenu);
        
        Menu projectsAllMenu = new Menu();
        projectsAllMenu.setName("All");
        projectsAllMenu.setActionCommand("OPEN_ALL_PROJECTS");
        projectsAllMenu.setId(2);
        projectsAllMenu.setParentId(4);
        menuItems.add(projectsAllMenu);
        
        Menu projectsActiveMenu = new Menu();
        projectsActiveMenu.setName("Active");
        projectsActiveMenu.setActionCommand("OPEN_ACTIVE_PROJECTS");
        projectsActiveMenu.setId(3);
        projectsActiveMenu.setParentId(4);
        menuItems.add(projectsActiveMenu);
        
        return menuItems;
    }

    @Override
    public void executeInsert(EJForm form, List<Menu> newRecords)
    {
    }

    @Override
    public void executeUpdate(EJForm form, List<Menu> updateRecords)
    {
    }

    @Override
    public void executeDelete(EJForm form, List<Menu> recordsToDelete)
    {
    }

}