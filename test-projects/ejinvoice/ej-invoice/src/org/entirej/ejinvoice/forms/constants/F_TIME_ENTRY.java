package org.entirej.ejinvoice.forms.constants;

/* AUTO-GENERATED FILE.  DO NOT MODIFY. 
 *
 * This class was automatically generated by the
 * entirej plugin from the form.  It
 * should not be modified by hand.
 */
public class F_TIME_ENTRY
{
    public static final String ID = "TimeEntry";

    public static class B_COMPANY
    {
        public static final String ID                = "Company";
        public static final String I_POST_CODE       = "postCode";
        public static final String I_ADDRESS         = "address";
        public static final String I_ID              = "id";
        public static final String I_NAME            = "name";
        public static final String I_TOWN            = "town";
        public static final String I_COUNTRY         = "country";
        public static final String I_VAT_NR          = "vatNr";
        public static final String I_LOGO            = "logo";
        public static final String I_DISPLAY_ADDRESS = "displayAddress";

    }

    public static class B_TIME_ENTRY_ENTRY
    {
        public static final String ID                         = "TimeEntryEntry";
        public static final String I_WORK_DATE                = "workDate";
        public static final String I_START_TIME               = "startTime";
        public static final String I_END_TIME                 = "endTime";
        public static final String I_PROJECT                  = "project";
        public static final String I_TASK                     = "task";
        public static final String I_HOURS                    = "hours";
        public static final String I_START_LABEL              = "startLabel";
        public static final String I_END_LABEL                = "endLabel";
        public static final String I_HOURS_LABEL              = "hoursLabel";
        public static final String I_EQUALS_LABEL             = "equalsLabel";
        public static final String I_NOTES                    = "notes";
        public static final String I_ADD__ENTRY               = "Add Entry";
        public static final String I_EDIT_COMPANY_INFORMATION = "editCompanyInformation";
        public static final String I_PROJECT_LABEL            = "projectLabel";
        public static final String I_PROCESS_LABEL            = "processLabel";

    }

    public static class B_TIME_ENTRY
    {
        public static final String ID                 = "TimeEntry";
        public static final String I_ID               = "id";
        public static final String I_COMPANY_ID       = "companyId";
        public static final String I_START_TIME       = "startTime";
        public static final String I_END_TIME         = "endTime";
        public static final String I_WORK_DESCRIPTION = "workDescription";
        public static final String I_CUPT_ID          = "cuptId";
        public static final String I_CUPR_ID          = "cuprId";
        public static final String I_USER_ID          = "userId";
        public static final String I_WORK_DATE        = "workDate";
        public static final String I_HOURS_WORKED     = "hoursWorked";
        public static final String I_PROJECT          = "project";
        public static final String I_TASK             = "task";
        public static final String I_START_LABEL      = "startLabel";
        public static final String I_END_LABEL        = "endLabel";
        public static final String I_HOURS_LABEL      = "hoursLabel";
        public static final String I_EQUALS_LABEL     = "equalsLabel";
        public static final String I__EDIT            = "_edit";
        public static final String I__DELETE          = "_delete";
        public static final String I_INVP_ID          = "invpId";

    }

    public static class B_CUSTOMERS_TOOLBAR
    {
        public static final String ID                        = "CustomersToolbar";
        public static final String I_CREATE_NEW_CUSTOMER     = "createNewCustomer";
        public static final String I_SHOW_INACTIVE_CUSTOMERS = "showInactiveCustomers";

    }

    public static class B_CUSTOMERS
    {
        public static final String ID                 = "Customers";
        public static final String I_COMPANY_ID       = "companyId";
        public static final String I_ID               = "id";
        public static final String I_ADDRESS          = "address";
        public static final String I_NAME             = "name";
        public static final String I_POST_CODE        = "postCode";
        public static final String I_TOWN             = "town";
        public static final String I_COUNTRY          = "country";
        public static final String I_EMAIL            = "email";
        public static final String I_PHONE            = "phone";
        public static final String I_CONTACT_TYPES_ID = "contactTypesId";
        public static final String I_SALUTATIONS_ID   = "salutationsId";
        public static final String I_CUSTOMER_ID      = "customerId";
        public static final String I_FIRST_NAME       = "firstName";
        public static final String I_LAST_NAME        = "lastName";
        public static final String I_MOBILE           = "mobile";
        public static final String I_VAT_ID           = "vatId";
        public static final String I_VAT_RATE         = "vatRate";
        public static final String I_PAYMENT_DAYS     = "paymentDays";
        public static final String I_DAYS_LABEL       = "daysLabel";
        public static final String I_CUSTOMER_NUMBER  = "customerNumber";
        public static final String I_EDIT             = "edit";
        public static final String I_DELETE           = "delete";
        public static final String I_CONTACTS         = "contacts";
        public static final String I_LOCALE           = "locale";
        public static final String I_LOCALE_COUNTRY   = "localeCountry";
        public static final String I_LOCALE_LANGUAGE  = "localeLanguage";
        public static final String I_CCY_CODE         = "ccyCode";
        public static final String I_ACTIVE           = "active";

    }

    public static class B_CUSTOMERS_KEY
    {
        public static final String ID               = "CustomersKey";
        public static final String I_CONTACTS       = "contacts";
        public static final String I_CONTACTS_LABEL = "contactsLabel";

    }

    public static class L_VAT_RATE
    {
        public static final String ID           = "VatRate";
        public static final String I_NOTES      = "notes";
        public static final String I_RATE       = "rate";
        public static final String I_NAME       = "name";
        public static final String I_ID         = "id";
        public static final String I_COMPANY_ID = "companyId";

    }

    public static class L_CUSTOMER
    {
        public static final String ID                 = "Customer";
        public static final String I_ID               = "id";
        public static final String I_ADDRESS          = "address";
        public static final String I_NAME             = "name";
        public static final String I_POST_CODE        = "postCode";
        public static final String I_TOWN             = "town";
        public static final String I_COUNTRY          = "country";
        public static final String I_COMPANY_ID       = "companyId";
        public static final String I_ITEMS_TO_INVOICE = "itemsToInvoice";

    }

    public static class L_CUSTOMER_PROJECTS
    {
        public static final String ID            = "CustomerProjects";
        public static final String I_NAME        = "name";
        public static final String I_ID          = "id";
        public static final String I_CUST_ID     = "custId";
        public static final String I_DESCRIPTION = "description";
        public static final String I_COMPANY_ID  = "companyId";
        public static final String I_STATUS      = "status";

    }

    public static class L_CONTACT_TYPES
    {
        public static final String ID            = "ContactTypes";
        public static final String I_TYPE        = "type";
        public static final String I_ID          = "id";
        public static final String I_DESCRIPTION = "description";
        public static final String I_COMPANY_ID  = "companyId";

    }

    public static class L_SALUTATIONS
    {
        public static final String ID           = "Salutations";
        public static final String I_VALUE      = "value";
        public static final String I_ID         = "id";
        public static final String I_COMPANY_ID = "companyId";

    }

    public static class L_UPDATE_CUSTOMER_PROJECT_TASKS
    {
        public static final String ID                    = "UpdateCustomerProjectTasks";
        public static final String I_ID                  = "id";
        public static final String I_CPR_ID              = "cprId";
        public static final String I_NOTES               = "notes";
        public static final String I_PROJECT_DESCRIPTION = "projectDescription";
        public static final String I_PROJECT_NAME        = "projectName";
        public static final String I_PROCESS_NAME        = "processName";
        public static final String I_PAY_RATE            = "payRate";
        public static final String I_COMPANY_ID          = "companyId";
        public static final String I_STATUS              = "status";

    }

    public static class L_CUSTOMER_PROJECT_TASKS
    {
        public static final String ID                    = "CustomerProjectTasks";
        public static final String I_ID                  = "id";
        public static final String I_CPR_ID              = "cprId";
        public static final String I_NOTES               = "notes";
        public static final String I_PROJECT_DESCRIPTION = "projectDescription";
        public static final String I_PROJECT_NAME        = "projectName";
        public static final String I_PROCESS_NAME        = "processName";
        public static final String I_PAY_RATE            = "payRate";
        public static final String I_COMPANY_ID          = "companyId";
        public static final String I_STATUS              = "status";

    }

    public static class L_PROJECT_STATUS
    {
        public static final String ID            = "ProjectStatus";
        public static final String I_DESCRIPTION = "description";
        public static final String I_NAME        = "name";
        public static final String I_CODE        = "code";

    }

    public static class L_TASK_STATUS
    {
        public static final String ID            = "TaskStatus";
        public static final String I_DESCRIPTION = "description";
        public static final String I_NAME        = "name";
        public static final String I_CODE        = "code";

    }

    public static class L_CUSTOMER_LOCALE
    {
        public static final String ID              = "CustomerLocale";
        public static final String I_LANGUAGE      = "language";
        public static final String I_COUNTRY       = "country";
        public static final String I_LOCALE        = "locale";
        public static final String I_LANGUAGE_DESC = "languageDesc";
        public static final String I_COUNTRY_DESC  = "countryDesc";
        public static final String I_CCY_CODE      = "ccyCode";

    }

    public static class L_WORK_WEEK_PROJECT_TASKS
    {
        public static final String ID                    = "WorkWeekProjectTasks";
        public static final String I_ID                  = "id";
        public static final String I_CPR_ID              = "cprId";
        public static final String I_NOTES               = "notes";
        public static final String I_PROJECT_DESCRIPTION = "projectDescription";
        public static final String I_PROJECT_NAME        = "projectName";
        public static final String I_PROCESS_NAME        = "processName";
        public static final String I_PAY_RATE            = "payRate";
        public static final String I_COMPANY_ID          = "companyId";
        public static final String I_STATUS              = "status";

    }

    public static class L_UPDATE_CUSTOMER_PROJECTS
    {
        public static final String ID            = "UpdateCustomerProjects";
        public static final String I_NAME        = "name";
        public static final String I_ID          = "id";
        public static final String I_CUST_ID     = "custId";
        public static final String I_DESCRIPTION = "description";
        public static final String I_COMPANY_ID  = "companyId";
        public static final String I_STATUS      = "status";

    }

    public static final String C_COMPANY = "Company";
    public static final String C_MAIN    = "Main";

    public static class C_MAIN_PAGES
    {
        public static final String TIME__ENTRY         = "Time Entry";
        public static final String TIME_ENTRY_OVERVIEW = "TimeEntryOverview";
        public static final String PROJECTS            = "Projects";
        public static final String INVOICE_CREATION    = "InvoiceCreation";
        public static final String CUSTOMERS           = "Customers";
        public static final String COMPANY             = "Company";

    }

    public static final String C_TIME_ENTRY_ENTRY         = "TimeEntryEntry";
    public static final String C_TIME_ENTRY               = "TimeEntry";
    public static final String C_TIME_ENTRY_OVERVIEW_FORM = "TimeEntryOverviewForm";
    public static final String C_PROJECT_FORM             = "ProjectForm";
    public static final String C_INVOICE_FORM             = "InvoiceForm";
    public static final String C_CUSTOMER_STACK           = "CustomerStack";

    public static class C_CUSTOMER_STACK_PAGES
    {
        public static final String CUSTOMER_OVERVIEW = "CustomerOverview";
        public static final String CUSTOMER_DETAILS  = "CustomerDetails";

    }

    public static final String C_CUSTOMERS_TOOLBAR      = "CustomersToolbar";
    public static final String C_CUSTOMER               = "Customer";
    public static final String C_CUSTOMERS_KEY          = "CustomersKey";
    public static final String C_CUSTOMER_DETAILS_FORM  = "CustomerDetailsForm";
    public static final String C_COMPANY_FORM           = "CompanyForm";

    public static final String AC_ADD_TIME_ENTRY        = "ADD_TIME_ENTRY";
    public static final String AC_CREATE_NEW_CUSTOMER   = "CREATE_NEW_CUSTOMER";
    public static final String AC_DELETE_CUSTOMER       = "DELETE_CUSTOMER";
    public static final String AC_DELETE_TIME_ENTRY     = "DELETE_TIME_ENTRY";
    public static final String AC_EDIT_CUSTOMER         = "EDIT_CUSTOMER";
    public static final String AC_EDIT_TIME_ENTRY       = "EDIT_TIME_ENTRY";
    public static final String AC_QUERY_CUSTOMERS       = "QUERY_CUSTOMERS";
    public static final String AC_SHOW_CUSTOMER_DETAILS = "SHOW_CUSTOMER_DETAILS";

}