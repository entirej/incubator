package org.entirej.ejinvoice.forms.timeentry;

import microsoft.exchange.webservices.data.GetEventsResults;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.forms.constants.F_COMPANY;
import org.entirej.ejinvoice.forms.constants.F_CUSTOMERS;
import org.entirej.ejinvoice.forms.constants.F_CUSTOMER_CONTACTS;
import org.entirej.ejinvoice.forms.constants.F_PROJECTS;
import org.entirej.ejinvoice.forms.constants.F_PROJECT_TASKS;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY_OVERVIEW;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.enumerations.EJScreenType;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;

public class FormHandler
{
    public void openForm(EJForm form, String command)
    {
        if (command.equals("OPEN_TIME_ENTRY_OVERVIEW"))
        {
            openTimeEntryOverview(form);
        }
        else if (command.equals("OPEN_TIME_ENTRY"))
        {
            openTimeEntry(form);
        }
        else if (command.equals("OPEN_CUSTOMERS"))
        {
            openCustomers(form);
        }
        else if (command.equals("OPEN_CUSTOMER_CONTACTS"))
        {
            openCustomerContacts(form);
        }
        else if (command.startsWith("OPEN_PROJECTS"))
        {
            String status = command.substring(command.indexOf(":") + 1);
            openProjects(form, status);
        }
        else if (command.startsWith("OPEN_PROJECT_TASKS"))
        {
            String status = command.substring(command.indexOf(":") + 1);
            openProjectTasks(form, status);
        }
    }

    private void openTimeEntryOverview(EJForm form)
    {
        form.showStackedCanvasPage(F_TIME_ENTRY.C_MAIN_STACK, F_TIME_ENTRY.C_MAIN_STACK_PAGES.TIME_ENTRY_OVERVIEW);
        EJForm overviewForm = form.getEmbeddedForm(F_TIME_ENTRY_OVERVIEW.ID, F_TIME_ENTRY.C_TIME_ENTRY_OVERVIEW_FORM);
        overviewForm.getBlock(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS.ID).executeQuery();
        overviewForm.getBlock(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_OVERVIEW.ID).executeQuery();
    }

    private void openTimeEntry(EJForm form)
    {
        form.showStackedCanvasPage(F_TIME_ENTRY.C_MAIN_STACK, F_TIME_ENTRY.C_MAIN_STACK_PAGES.TIME_ENTRY);
        form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID).executeLastQuery();
    }

    private void openCustomers(EJForm form)
    {
        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();

        if (form.getEmbeddedForm(F_CUSTOMERS.ID, F_TIME_ENTRY.C_CUSTOMER_FORM) == null)
        {
            form.openEmbeddedForm(F_CUSTOMERS.ID, F_TIME_ENTRY.C_CUSTOMER_FORM, null);
        }

        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("COMPANY_ID", new Integer(companyId)));
        EJForm customerForm = form.getEmbeddedForm(F_CUSTOMERS.ID, F_TIME_ENTRY.C_CUSTOMER_FORM);
        customerForm.getBlock(F_CUSTOMERS.B_CUSTOMERS.ID).executeQuery(criteria);

        form.showStackedCanvasPage(F_TIME_ENTRY.C_MAIN_STACK, F_TIME_ENTRY.C_MAIN_STACK_PAGES.CUSTOMER);
    }

    private void openProjects(EJForm form, String status)
    {
        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();

        if (form.getEmbeddedForm(F_PROJECTS.ID, F_TIME_ENTRY.C_PROJECT_FORM) == null)
        {
            form.openEmbeddedForm(F_PROJECTS.ID, F_TIME_ENTRY.C_PROJECT_FORM, null);
        }

        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("COMPANY_ID", new Integer(companyId)));
        criteria.add(EJRestrictions.equals("STATUS", status));
        EJForm customerForm = form.getEmbeddedForm(F_PROJECTS.ID, F_TIME_ENTRY.C_PROJECT_FORM);
        customerForm.getBlock(F_PROJECTS.B_PROJECTS.ID).executeQuery(criteria);

        form.showStackedCanvasPage(F_TIME_ENTRY.C_MAIN_STACK, F_TIME_ENTRY.C_MAIN_STACK_PAGES.PROJECTS);
    }

    private void openProjectTasks(EJForm form, String status)
    {
        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();

        if (form.getEmbeddedForm(F_PROJECT_TASKS.ID, F_TIME_ENTRY.C_PROJECT_TASKS_FORM) == null)
        {
            form.openEmbeddedForm(F_PROJECT_TASKS.ID, F_TIME_ENTRY.C_PROJECT_TASKS_FORM, null);
        }

        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("COMPANY_ID", new Integer(companyId)));
        criteria.add(EJRestrictions.equals("STATUS", status));
        EJForm projectTasksForm = form.getEmbeddedForm(F_PROJECT_TASKS.ID, F_TIME_ENTRY.C_PROJECT_TASKS_FORM);
        projectTasksForm.setFormParameter("STATUS", status);
        Integer projectId = (Integer) projectTasksForm.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS_TOOLBAR.ID).getFocusedRecord().getValue(F_PROJECT_TASKS.B_PROJECT_TASKS_TOOLBAR.I_PROJECTS);

        if (projectId != null)
        {
            criteria.add(EJRestrictions.equals("CPR_ID", projectId));
        }

        projectTasksForm.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS.ID).executeQuery(criteria);

        form.showStackedCanvasPage(F_TIME_ENTRY.C_MAIN_STACK, F_TIME_ENTRY.C_MAIN_STACK_PAGES.PROJECT_TASKS);
    }

    public void openCustomerContacts(EJForm form)
    {
        openCustomerContacts(form, null);
    }

    public void openCustomerContacts(EJForm callingForm, Integer customerId)
    {
        EJForm form = callingForm;

        if (!F_TIME_ENTRY.ID.equals(callingForm.getName()))
        {
            form = form.getForm(F_TIME_ENTRY.ID);
        }

        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();

        if (form.getEmbeddedForm(F_CUSTOMER_CONTACTS.ID, F_TIME_ENTRY.C_CUSTOMER_CONTACTS_FORM) == null)
        {
            form.openEmbeddedForm(F_CUSTOMER_CONTACTS.ID, F_TIME_ENTRY.C_CUSTOMER_CONTACTS_FORM, null);
        }

        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("COMPANY_ID", new Integer(companyId)));
        if (customerId != null)
        {
            criteria.add(EJRestrictions.equals("CUSTOMER_ID", new Integer(customerId)));
        }

        EJForm contactsForm = form.getEmbeddedForm(F_CUSTOMER_CONTACTS.ID, F_TIME_ENTRY.C_CUSTOMER_CONTACTS_FORM);

        contactsForm.getBlock(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_TOOLBAR.ID).getScreenItem(EJScreenType.MAIN, F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS_TOOLBAR.I_CUSTOMER_ID).setValue(customerId);

        contactsForm.getBlock(F_CUSTOMER_CONTACTS.B_CUSTOMER_CONTACTS.ID).executeQuery(criteria);

        form.showStackedCanvasPage(F_TIME_ENTRY.C_MAIN_STACK, F_TIME_ENTRY.C_MAIN_STACK_PAGES.CUSTOMER_CONTACTS);
    }

}
