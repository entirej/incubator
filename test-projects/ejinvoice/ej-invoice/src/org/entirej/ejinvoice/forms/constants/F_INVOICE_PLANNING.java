package org.entirej.ejinvoice.forms.constants;

/* AUTO-GENERATED FILE.  DO NOT MODIFY. 
 *
 * This class was automatically generated by the
 * entirej plugin from the form.  It
 * should not be modified by hand.
 */
public class F_INVOICE_PLANNING
{
    public static final String ID = "InvoicePlanning";

    public static class B_INVOICE_HEADER
    {
        public static final String ID            = "InvoiceHeader";
        public static final String I_CUSTOMER_ID = "customerId";
        public static final String I_PROJECTS    = "projects";

    }

    public static class B_OPEN_PROJECT_ITEMS
    {
        public static final String ID                        = "OpenProjectItems";
        public static final String I_PROJECT_ID              = "projectId";
        public static final String I_TASK_ID                 = "taskId";
        public static final String I_PROJECT_NAME            = "projectName";
        public static final String I_TASK_NAME               = "taskName";
        public static final String I_TE_MONTH                = "teMonth";
        public static final String I_TE_YEAR                 = "teYear";
        public static final String I_TE_LAST_DAY             = "teLastDay";
        public static final String I_TE_FIRST_DAY            = "teFirstDay";
        public static final String I_WORK_HOURS              = "workHours";
        public static final String I_CREATE_INVOICE_POSITION = "createInvoicePosition";
        public static final String I_PAY_RATE                = "payRate";
        public static final String I_COMPANY_ID              = "companyId";
        public static final String I_DISPLAY_TEXT            = "displayText";
        public static final String I_DISPLAY_VALUE_TEXT      = "displayValueText";

    }

    public static class B_OPEN_PROJECT_ITEMS_CONTROL
    {
        public static final String ID            = "OpenProjectItemsControl";
        public static final String I_DESCRIPTION = "description";

    }

    public static class B_NEW_INVOICE_ITEM
    {
        public static final String ID             = "NewInvoiceItem";
        public static final String I_DELETE       = "delete";
        public static final String I_PERIOD_LABEL = "periodLabel";
        public static final String I_PERIOD_FROM  = "periodFrom";
        public static final String I_PERIOD_TO    = "periodTo";
        public static final String I_STATUS_LABEL = "statusLabel";
        public static final String I_STATUS       = "status";
        public static final String I_TEXT         = "text";

    }

    public static class B_PLANNED_PROJECT_ITEMS
    {
        public static final String ID                        = "PlannedProjectItems";
        public static final String I_INVP_ID                 = "invpId";
        public static final String I_PROJECT_ID              = "projectId";
        public static final String I_TASK_ID                 = "taskId";
        public static final String I_PROJECT_NAME            = "projectName";
        public static final String I_TASK_NAME               = "taskName";
        public static final String I_TE_MONTH                = "teMonth";
        public static final String I_TE_YEAR                 = "teYear";
        public static final String I_PERIOD_TO               = "periodTo";
        public static final String I_PERIOD_FROM             = "periodFrom";
        public static final String I_WORK_HOURS              = "workHours";
        public static final String I_CREATE_INVOICE_POSITION = "createInvoicePosition";
        public static final String I_DELETE                  = "delete";
        public static final String I_INVP_TEXT               = "invpText";
        public static final String I_EDIT                    = "edit";
        public static final String I_PAY_RATE                = "payRate";
        public static final String I_COMPANY_ID              = "companyId";
        public static final String I_CUSTOMER_ID             = "customerId";

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

    public static class L_PROJECTS
    {
        public static final String ID            = "Projects";
        public static final String I_NAME        = "name";
        public static final String I_ID          = "id";
        public static final String I_CUST_ID     = "custId";
        public static final String I_DESCRIPTION = "description";
        public static final String I_COMPANY_ID  = "companyId";
        public static final String I_STATUS      = "status";
        public static final String I_FIX_PRICE   = "fixPrice";

    }

    public static final String C_NEW_INVOICE_ITEM_POPUP   = "NewInvoiceItemPopup";
    public static final String C_NEW_INVOICE_ITEM         = "NewInvoiceItem";
    public static final String C_INVOICE_HEADER           = "InvoiceHeader";
    public static final String C_INVOICE_SPLIT            = "invoiceSplit";
    public static final String C_OPEN_PROJECT_ITEMS       = "OpenProjectItems";
    public static final String C_PLANNED_PROJECT_ITEMS    = "PlannedProjectItems";

    public static final String AC_ADD_TO_INV_POS          = "ADD_TO_INV_POS";
    public static final String AC_APPROVE_INV_POS         = "APPROVE_INV_POS";
    public static final String AC_CREATE_INVOICE_POSITION = "CREATE_INVOICE_POSITION";
    public static final String AC_DELETE_PLANNED_ITEM     = "DELETE_PLANNED_ITEM";
    public static final String AC_EDIT_PLANNED_ITEM       = "EDIT_PLANNED_ITEM";
    public static final String AC_REFRESH_BLOCKS          = "REFRESH_BLOCKS";

}