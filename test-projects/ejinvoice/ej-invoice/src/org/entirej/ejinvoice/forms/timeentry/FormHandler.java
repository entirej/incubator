package org.entirej.ejinvoice.forms.timeentry;

import microsoft.exchange.webservices.data.GetEventsResults;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.forms.constants.F_COMPANY;
import org.entirej.ejinvoice.forms.constants.F_CUSTOMERS;
import org.entirej.ejinvoice.forms.constants.F_CUSTOMER_CONTACTS;
import org.entirej.ejinvoice.forms.constants.F_INVOICE_CREATION;
import org.entirej.ejinvoice.forms.constants.F_INVOICE_PLANNING;
import org.entirej.ejinvoice.forms.constants.F_OUTSTANDING_INVOICES;
import org.entirej.ejinvoice.forms.constants.F_PAID_INVOICES;
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
            openProjectTasks(form, status, null);
        }
        else if (command.equals("OPEN_INVOICE_PLANNING"))
        {
            openInvoicePlanning(form);
        }
        else if (command.equals("OPEN_INVOICE_CREATION"))
        {
            openInvoiceCreation(form);
        }
        else if (command.equals("OPEN_INVOICE_OUTSTANDING"))
        {
            openOutstandingInvoices(form);
        }
        else if (command.equals("OPEN_INVOICE_PAID"))
        {
            openPaidInvoices(form);
        }
    }

    private void openTimeEntryOverview(EJForm form)
    {
        if (form.getEmbeddedForm(F_TIME_ENTRY_OVERVIEW.ID, F_TIME_ENTRY.C_TIME_ENTRY_OVERVIEW_FORM) == null)
        {
            form.openEmbeddedForm(F_TIME_ENTRY_OVERVIEW.ID, F_TIME_ENTRY.C_TIME_ENTRY_OVERVIEW_FORM, null);
        }
        
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
        EJForm projectForm = form.getEmbeddedForm(F_PROJECTS.ID, F_TIME_ENTRY.C_PROJECT_FORM);
        projectForm.getBlock(F_PROJECTS.B_PROJECTS_TOOLBAR.ID).getFocusedRecord().setValue(F_PROJECTS.B_PROJECTS_TOOLBAR.I_STATUS, status);
        projectForm.getBlock(F_PROJECTS.B_PROJECTS.ID).executeQuery(criteria);

        form.showStackedCanvasPage(F_TIME_ENTRY.C_MAIN_STACK, F_TIME_ENTRY.C_MAIN_STACK_PAGES.PROJECTS);
    }

    public void openProjectTasks(EJForm callingForm, String status, Integer iProjectId)
    {
        EJForm form = callingForm;
        if (!F_TIME_ENTRY.ID.equals(callingForm.getName()))
        {
            form = form.getForm(F_TIME_ENTRY.ID);
        }
        
        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
        Integer projectId = iProjectId;
        
        if (form.getEmbeddedForm(F_PROJECT_TASKS.ID, F_TIME_ENTRY.C_PROJECT_TASKS_FORM) == null)
        {
            form.openEmbeddedForm(F_PROJECT_TASKS.ID, F_TIME_ENTRY.C_PROJECT_TASKS_FORM, null);
        }

        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("COMPANY_ID", new Integer(companyId)));
        criteria.add(EJRestrictions.equals("STATUS", status));
        EJForm projectTasksForm = form.getEmbeddedForm(F_PROJECT_TASKS.ID, F_TIME_ENTRY.C_PROJECT_TASKS_FORM);
        projectTasksForm.setFormParameter("STATUS", status);

        if (projectId == null)
        {
            projectId = (Integer) projectTasksForm.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS_TOOLBAR.ID).getFocusedRecord().getValue(F_PROJECT_TASKS.B_PROJECT_TASKS_TOOLBAR.I_PROJECTS);
        }

        if (projectId != null)
        {
            criteria.add(EJRestrictions.equals("CPR_ID", projectId));
            projectTasksForm.getBlock(F_PROJECT_TASKS.B_PROJECT_TASKS_TOOLBAR.ID).getCurrentScreenRecord(EJScreenType.MAIN).setValue(F_PROJECT_TASKS.B_PROJECT_TASKS_TOOLBAR.I_PROJECTS, projectId);
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

    private void openInvoicePlanning(EJForm form)
    {
        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();

        if (form.getEmbeddedForm(F_INVOICE_PLANNING.ID, F_TIME_ENTRY.C_INVOICE_PLANNING_FORM) == null)
        {
            form.openEmbeddedForm(F_INVOICE_PLANNING.ID, F_TIME_ENTRY.C_INVOICE_PLANNING_FORM, null);
        }

        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("COMPANY_ID", new Integer(companyId)));


        form.showStackedCanvasPage(F_TIME_ENTRY.C_MAIN_STACK, F_TIME_ENTRY.C_MAIN_STACK_PAGES.INVOICE_PLANNING);
//        EJForm invoicePlanningForm = form.getEmbeddedForm(F_INVOICE_PLANNING.ID, F_TIME_ENTRY.C_INVOICE_PLANNING_FORM);
//      invoicePlanningForm.getBlock(F_INVOICE_PLANNING.B_PLANNED_PROJECT_ITEMS.ID).executeQuery(criteria);
    }
    
    private void openInvoiceCreation(EJForm form)
    {
        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();

        if (form.getEmbeddedForm(F_INVOICE_CREATION.ID, F_TIME_ENTRY.C_INVOICE_CREATION_FORM) == null)
        {
            form.openEmbeddedForm(F_INVOICE_CREATION.ID, F_TIME_ENTRY.C_INVOICE_CREATION_FORM, null);
        }

        
        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("COMPANY_ID", new Integer(companyId)));
        
        EJForm invoiceCreationForm = form.getEmbeddedForm(F_INVOICE_CREATION.ID, F_TIME_ENTRY.C_INVOICE_CREATION_FORM);
        invoiceCreationForm.getBlock(F_INVOICE_CREATION.B_FILTER.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_FILTER.I_CUSTOMER_ID).refreshItemRenderer();
        
//        EJForm invoiceCreationForm = form.getEmbeddedForm(F_INVOICE_CREATION.ID, F_TIME_ENTRY.C_INVOICE_CREATION_FORM);
//        invoiceCreationForm.getBlock(F_INVOICE_CREATION.B_MARKED_FOR_INVOICE_PROJECT_ITEMS.ID).executeQuery(criteria);

        form.showStackedCanvasPage(F_TIME_ENTRY.C_MAIN_STACK, F_TIME_ENTRY.C_MAIN_STACK_PAGES.INVOICE_CREATION);
    }
    
    private void openOutstandingInvoices(EJForm form)
    {
        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();

        if (form.getEmbeddedForm(F_OUTSTANDING_INVOICES.ID, F_TIME_ENTRY.C_OUTSTANDING_INVOICES_FORM) == null)
        {
            form.openEmbeddedForm(F_OUTSTANDING_INVOICES.ID, F_TIME_ENTRY.C_OUTSTANDING_INVOICES_FORM, null);
        }

        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("COMPANY_ID", new Integer(companyId)));
//        EJForm outstandingInvoicesForm = form.getEmbeddedForm(F_INVOICE_CREATION.ID, F_TIME_ENTRY.C_OUTSTANDING_INVOICES_FORM);
//        outstandingInvoicesForm.getBlock(F_OUTSTANDING_INVOICES.B_INVOICE_HISTORY.ID).executeQuery(criteria);

        form.showStackedCanvasPage(F_TIME_ENTRY.C_MAIN_STACK, F_TIME_ENTRY.C_MAIN_STACK_PAGES.OUTSTANDING_INVOICES);
    }
    
    private void openPaidInvoices(EJForm form)
    {
        Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();

        if (form.getEmbeddedForm(F_PAID_INVOICES.ID, F_TIME_ENTRY.C_PAID_INVOICES_FORM) == null)
        {
            form.openEmbeddedForm(F_PAID_INVOICES.ID, F_TIME_ENTRY.C_PAID_INVOICES_FORM, null);
        }

        EJQueryCriteria criteria = new EJQueryCriteria();
        criteria.add(EJRestrictions.equals("COMPANY_ID", new Integer(companyId)));
//        EJForm paidInvoicesForm = form.getEmbeddedForm(F_INVOICE_CREATION.ID, F_TIME_ENTRY.C_OUTSTANDING_INVOICES_FORM);
//        paidInvoicesForm.getBlock(F_PAID_INVOICES.B_INVOICE_HISTORY_PAID.ID).executeQuery(criteria);

        form.showStackedCanvasPage(F_TIME_ENTRY.C_MAIN_STACK, F_TIME_ENTRY.C_MAIN_STACK_PAGES.PAID_INVOICES);
    }
}
