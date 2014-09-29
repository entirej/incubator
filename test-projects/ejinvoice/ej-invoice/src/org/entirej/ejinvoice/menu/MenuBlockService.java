package org.entirej.ejinvoice.menu;

import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.service.EJBlockService;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJSelectResult;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

public class MenuBlockService implements EJBlockService<Menu>
{
    public static String        TIMETRACKING          = "TIME_TRACKING";
    public static String        TRACK_TIME            = "TRACK_TIME";
    public static String        TIME_ENTRY_OVERVIEW   = "TIME_ENTRY_OVERVIEW";
    public static String        CONTACTS              = "CONTACTS";
    public static String        CONTACT_COMPANIES     = "CONTACT_COMPANIES";
    public static String        CONTACT_PEOPLE        = "CONTACT_PEOPLE";
    public static String        PROJECTS              = "PROJECTS";
    public static String        PROJECTS_NEW          = "PROJECTS_NEW";
    public static String        PROJECTS_ONHOLD       = "PROJECTS_ONHOLD";
    public static String        PROJECTS_INWORK       = "PROJECTS_INWORK";
    public static String        PROJECTS_COMPLETED    = "PROJECTS_COMPLETED";
    public static String        PROJECTS_DELETED      = "PROJECTS_DELETED";
    public static String        TASKS                 = "TASKS";
    public static String        TASKS_NEW             = "TASKS_NEW";
    public static String        TASKS_ONHOLD          = "TASKS_ONHOLD";
    public static String        TASKS_INWORK          = "TASKS_INWORK";
    public static String        TASKS_COMPLETED       = "TASKS_COMPLETED";
    public static String        TASKS_DELETED         = "TASKS_DELETED";
    public static String        INVOICE               = "INVOICE";
    public static String        INVOICE_PLANNING      = "INVOICE_PLANNING";
    public static String        INVOICE_CREATION      = "INVOICE_CREATION";
    public static String        INVOICE_OUTSTANDING   = "INVOICE_OUTSTANDING";
    public static String        INVOICE_PAID          = "INVOICE_PAID";
    public static String        SETTINGS              = "SETTINGS";
    public static String        SETTINGS_COMPANY      = "SETTINGS_COMPANY";
    public static String        SETTINGS_USERS        = "SETTINGS_USERS";
    public static String        SETTINGS_VATRAT       = "SETTINGS_VATRATES";
    public static String        SETTINGS_SALUTATIONS  = "SETTINGS_SALUTATIONS";
    public static String        SETTINGS_CONTACTTYPES = "SETTINGS_CONTACTTYPES";

    ArrayList<Menu>             menuItems             = new ArrayList<Menu>();
    private EJStatementExecutor _stmtExecutor         = new EJStatementExecutor();
    private int                 _id                   = 1;

    @Override
    public boolean canQueryInPages()
    {
        return false;
    }

    private URL getURL(String name)
    {
        if (name == null)
        {
            return null;
        }
        return this.getClass().getResource(name);
    }

    @Override
    public List<Menu> executeQuery(EJForm form, EJQueryCriteria queryCriteria)
    {
        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();

        // ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Menu timetrackingMenu = new Menu(TIMETRACKING);
        timetrackingMenu.setIcon(getURL("/icons/openhours.png"));
        timetrackingMenu.setName("Time Tracking");
        timetrackingMenu.setId(_id++);
        menuItems.add(timetrackingMenu);

        Menu timeentryMenu = new Menu(TRACK_TIME);
        timeentryMenu.setName("Track Time");
        timeentryMenu.setActionCommand("OPEN_TIME_ENTRY");
        timeentryMenu.setId(_id++);
        timeentryMenu.setParentId(timetrackingMenu.getId());
        menuItems.add(timeentryMenu);

        Menu timeentryOverviewMenu = new Menu(TIME_ENTRY_OVERVIEW);
        timeentryOverviewMenu.setName("Overview");
        timeentryOverviewMenu.setActionCommand("OPEN_TIME_ENTRY_OVERVIEW");
        timeentryOverviewMenu.setId(_id++);
        timeentryOverviewMenu.setParentId(timetrackingMenu.getId());
        menuItems.add(timeentryOverviewMenu);

        // ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Menu customersMenu = new Menu(CONTACTS);
        customersMenu.setIcon(getURL("/icons/customer.png"));
        customersMenu.setName("Contacts");
        customersMenu.setId(_id++);
        menuItems.add(customersMenu);

        Menu companiesMenu = new Menu(CONTACT_COMPANIES);
        companiesMenu.setName("Companies");
        companiesMenu.setActionCommand("OPEN_CUSTOMERS");
        companiesMenu.setId(_id++);
        companiesMenu.setParentId(customersMenu.getId());
        menuItems.add(companiesMenu);

        Menu customerContactsMenu = new Menu(CONTACT_PEOPLE);
        customerContactsMenu.setName("People");
        customerContactsMenu.setActionCommand("OPEN_CUSTOMER_CONTACTS");
        customerContactsMenu.setId(_id++);
        customerContactsMenu.setParentId(customersMenu.getId());
        menuItems.add(customerContactsMenu);

        // ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Menu projectsMenu = new Menu(PROJECTS);
        projectsMenu.setIcon(getURL("/icons/projects.png"));
        projectsMenu.setIconName("/icons/projects.png");
        projectsMenu.setName("Projects");
        projectsMenu.setId(_id++);
        menuItems.add(projectsMenu);

        Hashtable<String, Long> projectCounts = getProjectCounts(form);

        Menu projectsNewMenu = new Menu(PROJECTS_NEW);
        projectsNewMenu.setName("New (" + projectCounts.get("NEW") + ")");
        projectsNewMenu.setActionCommand("OPEN_PROJECTS:NEW");
        projectsNewMenu.setId(_id++);
        projectsNewMenu.setParentId(projectsMenu.getId());
        menuItems.add(projectsNewMenu);
        // addProjectsToMenu(form, companyId, projectsNewMenu, "NEW");

        Menu projectsActiveMenu = new Menu(PROJECTS_INWORK);
        projectsActiveMenu.setName("In Work (" + projectCounts.get("INWORK") + ")");
        projectsActiveMenu.setActionCommand("OPEN_PROJECTS:INWORK");
        projectsActiveMenu.setId(_id++);
        projectsActiveMenu.setParentId(projectsMenu.getId());
        menuItems.add(projectsActiveMenu);
        // addProjectsToMenu(form, companyId, projectsActiveMenu, "INWORK");

        Menu projectsInactiveMenu = new Menu(PROJECTS_ONHOLD);
        projectsInactiveMenu.setName("On Hold (" + projectCounts.get("ONHOLD") + ")");
        projectsInactiveMenu.setActionCommand("OPEN_PROJECTS:ONHOLD");
        projectsInactiveMenu.setId(_id++);
        projectsInactiveMenu.setParentId(projectsMenu.getId());
        menuItems.add(projectsInactiveMenu);
        // addProjectsToMenu(form, companyId, projectsInactiveMenu, "ONHOLD");

        Menu projectsCompletedMenu = new Menu(PROJECTS_COMPLETED);
        projectsCompletedMenu.setName("Completed (" + projectCounts.get("COMPLETED") + ")");
        projectsCompletedMenu.setActionCommand("OPEN_PROJECTS:COMPLETED");
        projectsCompletedMenu.setId(_id++);
        projectsCompletedMenu.setParentId(projectsMenu.getId());
        menuItems.add(projectsCompletedMenu);
        // addProjectsToMenu(form, companyId, projectsCompletedMenu,
        // "COMPLETED");

        Menu projectsDeleteddMenu = new Menu(PROJECTS_DELETED);
        projectsDeleteddMenu.setName("Deleted (" + projectCounts.get("DELETED") + ")");
        projectsDeleteddMenu.setActionCommand("OPEN_PROJECTS:DELETED");
        projectsDeleteddMenu.setId(_id++);
        projectsDeleteddMenu.setParentId(projectsMenu.getId());
        menuItems.add(projectsDeleteddMenu);
        // addProjectsToMenu(form, companyId, projectsDeleteddMenu, "DELETED");

        // ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Menu tasksMenu = new Menu(TASKS);
        tasksMenu.setIcon(getURL("/icons/projectTasks.png"));
        tasksMenu.setName("Tasks");
        tasksMenu.setActionCommand("OPEN_PROJECT_TASKS");
        tasksMenu.setId(_id++);
        menuItems.add(tasksMenu);

        Hashtable<String, Long> taskCounts = getTaskCounts(form);

        Menu tasksAllMenu = new Menu(TASKS_NEW);
        tasksAllMenu.setName("New (" + taskCounts.get("NEW") + ")");
        tasksAllMenu.setActionCommand("OPEN_PROJECT_TASKS:NEW");
        tasksAllMenu.setId(_id++);
        tasksAllMenu.setParentId(tasksMenu.getId());
        menuItems.add(tasksAllMenu);

        Menu tasksInWorkMenu = new Menu(TASKS_INWORK);
        tasksInWorkMenu.setName("In Work (" + taskCounts.get("INWORK") + ")");
        tasksInWorkMenu.setActionCommand("OPEN_PROJECT_TASKS:INWORK");
        tasksInWorkMenu.setId(_id++);
        tasksInWorkMenu.setParentId(tasksMenu.getId());
        menuItems.add(tasksInWorkMenu);

        Menu tasksOnHoldMenu = new Menu(TASKS_ONHOLD);
        tasksOnHoldMenu.setName("On Hold (" + taskCounts.get("ONHOLD") + ")");
        tasksOnHoldMenu.setActionCommand("OPEN_PROJECT_TASKS:ONHOLD");
        tasksOnHoldMenu.setId(_id++);
        tasksOnHoldMenu.setParentId(tasksMenu.getId());
        menuItems.add(tasksOnHoldMenu);

        Menu tasksCompletedMenu = new Menu(TASKS_COMPLETED);
        tasksCompletedMenu.setName("Completed (" + taskCounts.get("COMPLETED") + ")");
        tasksCompletedMenu.setActionCommand("OPEN_PROJECT_TASKS:COMPLETED");
        tasksCompletedMenu.setId(_id++);
        tasksCompletedMenu.setParentId(tasksMenu.getId());
        menuItems.add(tasksCompletedMenu);

        Menu tasksDeletedMenu = new Menu(TASKS_DELETED);
        tasksDeletedMenu.setName("Deleted (" + taskCounts.get("DELETED") + ")");
        tasksDeletedMenu.setActionCommand("OPEN_PROJECT_TASKS:DELETED");
        tasksDeletedMenu.setId(_id++);
        tasksDeletedMenu.setParentId(tasksMenu.getId());
        menuItems.add(tasksDeletedMenu);

        // ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Menu invoiceMenu = new Menu(INVOICE);
        invoiceMenu.setIcon(getURL("/icons/invoice.png"));
        invoiceMenu.setName("Invoice");
        invoiceMenu.setId(_id++);
        menuItems.add(invoiceMenu);

        Menu invoicePlanningMenu = new Menu(INVOICE_PLANNING);
        invoicePlanningMenu.setName("Invoice Planning");
        invoicePlanningMenu.setActionCommand("OPEN_INVOICE_PLANNING");
        invoicePlanningMenu.setId(_id++);
        invoicePlanningMenu.setParentId(invoiceMenu.getId());
        menuItems.add(invoicePlanningMenu);

        Menu invoiceCreationMenu = new Menu(INVOICE_CREATION);
        invoiceCreationMenu.setName("Invoice Creation");
        invoiceCreationMenu.setActionCommand("OPEN_INVOICE_CREATION");
        invoiceCreationMenu.setId(_id++);
        invoiceCreationMenu.setParentId(invoiceMenu.getId());
        menuItems.add(invoiceCreationMenu);

        Menu invoiceOutstandingMenu = new Menu(INVOICE_OUTSTANDING);
        invoiceOutstandingMenu.setName("Outstanding Invoices");
        invoiceOutstandingMenu.setActionCommand("OPEN_INVOICE_OUTSTANDING");
        invoiceOutstandingMenu.setId(_id++);
        invoiceOutstandingMenu.setParentId(invoiceMenu.getId());
        menuItems.add(invoiceOutstandingMenu);

        Menu invoicePaidMenu = new Menu(INVOICE_PAID);
        invoicePaidMenu.setName("Paid Invoices");
        invoicePaidMenu.setActionCommand("OPEN_INVOICE_PAID");
        invoicePaidMenu.setId(_id++);
        invoicePaidMenu.setParentId(invoiceMenu.getId());
        menuItems.add(invoicePaidMenu);

        // ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Menu settingsMenu = new Menu(SETTINGS);
        settingsMenu.setIcon(getURL("/icons/settings.png"));
        settingsMenu.setName("Settings");
        settingsMenu.setId(_id++);
        menuItems.add(settingsMenu);

        Menu settingsCompanyMenu = new Menu(SETTINGS_COMPANY);
        settingsCompanyMenu.setName("Company");
        settingsCompanyMenu.setActionCommand("OPEN_COMPANY");
        settingsCompanyMenu.setId(_id++);
        settingsCompanyMenu.setParentId(settingsMenu.getId());
        menuItems.add(settingsCompanyMenu);

        Menu settingsUsersMenu = new Menu(SETTINGS_USERS);
        settingsUsersMenu.setName("Users");
        settingsUsersMenu.setActionCommand("OPEN_USERS");
        settingsUsersMenu.setId(_id++);
        settingsUsersMenu.setParentId(settingsMenu.getId());
        menuItems.add(settingsUsersMenu);

        Menu contactTypesMenu = new Menu(SETTINGS_USERS);
        contactTypesMenu.setName("Contact Types");
        contactTypesMenu.setActionCommand("OPEN_CONTACTTYPES");
        contactTypesMenu.setId(_id++);
        contactTypesMenu.setParentId(settingsMenu.getId());
        menuItems.add(contactTypesMenu);
        
        Menu salutationsMenu = new Menu(SETTINGS_USERS);
        salutationsMenu.setName("Salutations");
        salutationsMenu.setActionCommand("OPEN_SALUTATIONS");
        salutationsMenu.setId(_id++);
        salutationsMenu.setParentId(settingsMenu.getId());
        menuItems.add(salutationsMenu);
        
        Menu vatRatesMenu = new Menu(SETTINGS_USERS);
        vatRatesMenu.setName("VAT Rates");
        vatRatesMenu.setActionCommand("OPEN_VATRATES");
        vatRatesMenu.setId(_id++);
        vatRatesMenu.setParentId(settingsMenu.getId());
        menuItems.add(vatRatesMenu);
        
        return menuItems;
    }

    public Hashtable<String, Long> getProjectCounts(EJForm form)
    {
        StringBuilder stmt = new StringBuilder();

        stmt.append("SELECT (select count(*) from customer_projects where company_id = ? and status = 'NEW') NEW ");
        stmt.append(",      (select count(*) from customer_projects where company_id = ? and status = 'INWORK') INWORK ");
        stmt.append(",      (select count(*) from customer_projects where company_id = ? and status = 'ONHOLD') ONHOLD ");
        stmt.append(",      (select count(*) from customer_projects where company_id = ? and status = 'COMPLETED') COMPLETED ");
        stmt.append(",      (select count(*) from customer_projects where company_id = ? and status = 'DELETED') DELETED ");
        stmt.append(" FROM DUAL ");

        Hashtable<String, Long> values = new Hashtable<String, Long>();

        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
        List<EJSelectResult> results = _stmtExecutor.executeQuery(form, stmt.toString(), new EJStatementParameter(companyId), new EJStatementParameter(
                companyId), new EJStatementParameter(companyId), new EJStatementParameter(companyId), new EJStatementParameter(companyId));
        for (EJSelectResult result : results)
        {
            values.put("NEW", (Long) result.getItemValue("NEW"));
            values.put("INWORK", (Long) result.getItemValue("INWORK"));
            values.put("ONHOLD", (Long) result.getItemValue("ONHOLD"));
            values.put("COMPLETED", (Long) result.getItemValue("COMPLETED"));
            values.put("DELETED", (Long) result.getItemValue("DELETED"));
            return values;
        }

        return values;
    }

    public Hashtable<String, Long> getTaskCounts(EJForm form)
    {
        StringBuilder stmt = new StringBuilder();

        stmt.append("SELECT (select count(*) from customer_project_tasks where company_id = ? and status = 'NEW') NEW ");
        stmt.append(",      (select count(*) from customer_project_tasks where company_id = ? and status = 'INWORK') INWORK ");
        stmt.append(",      (select count(*) from customer_project_tasks where company_id = ? and status = 'ONHOLD') ONHOLD ");
        stmt.append(",      (select count(*) from customer_project_tasks where company_id = ? and status = 'COMPLETED') COMPLETED ");
        stmt.append(",      (select count(*) from customer_project_tasks where company_id = ? and status = 'DELETED') DELETED ");
        stmt.append(" FROM DUAL ");

        Hashtable<String, Long> values = new Hashtable<String, Long>();

        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
        List<EJSelectResult> results = _stmtExecutor.executeQuery(form, stmt.toString(), new EJStatementParameter(companyId), new EJStatementParameter(
                companyId), new EJStatementParameter(companyId), new EJStatementParameter(companyId), new EJStatementParameter(companyId));
        for (EJSelectResult result : results)
        {
            values.put("NEW", (Long) result.getItemValue("NEW"));
            values.put("INWORK", (Long) result.getItemValue("INWORK"));
            values.put("ONHOLD", (Long) result.getItemValue("ONHOLD"));
            values.put("COMPLETED", (Long) result.getItemValue("COMPLETED"));
            values.put("DELETED", (Long) result.getItemValue("DELETED"));
            return values;
        }

        return values;
    }

    // private void addProjectsToMenu(EJForm form, Integer companyId, Menu
    // projectsMenu, String status)
    // {
    // String stmt =
    // "SELECT * FROM CUSTOMER_PROJECTS WHERE COMPANY_ID = ? AND STATUS = ? ORDER BY NAME";
    //
    // List<EJSelectResult> results = _stmtExecutor.executeQuery(form, stmt, new
    // EJStatementParameter(companyId), new EJStatementParameter(status));
    // for (EJSelectResult result : results)
    // {
    // Menu customerMenu = new Menu();
    // customerMenu.setName((String) result.getItemValue("NAME"));
    // customerMenu.setActionCommand("OPEN_PROJECT:" + (Integer)
    // result.getItemValue("ID"));
    // customerMenu.setId(_id++);
    // customerMenu.setParentId(projectsMenu.getId());
    // menuItems.add(customerMenu);
    // }
    // }
    //
    // private void addCustomerProjectsToMenu(EJForm form, Integer companyId,
    // Menu projectsMenu, String status, int customerId)
    // {
    // String stmt =
    // "SELECT * FROM CUSTOMER_PROJECTS WHERE COMPANY_ID = ? AND STATUS = ? AND CUSTOMER_ID = ? ORDER BY NAME";
    //
    // List<EJSelectResult> results = _stmtExecutor.executeQuery(form, stmt, new
    // EJStatementParameter(companyId), new EJStatementParameter(status), new
    // EJStatementParameter(customerId));
    //
    // if (results.size() > 0)
    // {
    // Menu customerProjetsMenu = new Menu();
    // customerProjetsMenu.setName("Projects");
    // customerProjetsMenu.setId(_id++);
    // customerProjetsMenu.setParentId(projectsMenu.getId());
    // menuItems.add(customerProjetsMenu);
    //
    // for (EJSelectResult result : results)
    // {
    // Menu customerMenu = new Menu();
    // customerMenu.setName((String) result.getItemValue("NAME"));
    // customerMenu.setActionCommand("OPEN_PROJECT:" + (Integer)
    // result.getItemValue("ID"));
    // customerMenu.setId(_id++);
    // customerMenu.setParentId(customerProjetsMenu.getId());
    // menuItems.add(customerMenu);
    // }
    // }
    //
    // }

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