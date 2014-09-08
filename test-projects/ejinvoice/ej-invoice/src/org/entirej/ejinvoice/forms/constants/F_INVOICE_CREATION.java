package org.entirej.ejinvoice.forms.constants;

/* AUTO-GENERATED FILE.  DO NOT MODIFY. 
 *
 * This class was automatically generated by the
 * entirej plugin from the form.  It
 * should not be modified by hand.
 */
public class F_INVOICE_CREATION
{
    public static final String ID = "InvoiceCreation";

    public static class B_FILTER
    {
        public static final String ID              = "filter";
        public static final String I_CUSTOMER_ID   = "customerId";
        public static final String I_CUSTOMER_NAME = "customerName";
        public static final String I_REFRESH       = "refresh";

    }

    public static class B_APPROVED_PROJECT_ITEMS
    {
        public static final String ID               = "ApprovedProjectItems";
        public static final String I_HOURS_WORKED   = "hoursWorked";
        public static final String I_TASK_NAME      = "taskName";
        public static final String I_STATUS         = "status";
        public static final String I_CUPR_ID        = "cuprId";
        public static final String I_PERIOD_FROM    = "periodFrom";
        public static final String I_INV_ID         = "invId";
        public static final String I_AMOUNT         = "amount";
        public static final String I_ID             = "id";
        public static final String I_PAY_RATE       = "payRate";
        public static final String I_PROJECT_NAME   = "projectName";
        public static final String I_PERIOD_TO      = "periodTo";
        public static final String I_CUPT_ID        = "cuptId";
        public static final String I_FIX_PRICE      = "fixPrice";
        public static final String I_TEXT           = "text";
        public static final String I_DELETE         = "delete";
        public static final String I_ADD_TO_INVOICE = "addToInvoice";
        public static final String I_COMPANY_ID     = "companyId";

    }

    public static class B_MARKED_FOR_INVOICE_PROJECT_ITEMS
    {
        public static final String ID             = "MarkedForInvoiceProjectItems";
        public static final String I_HOURS_WORKED = "hoursWorked";
        public static final String I_TASK_NAME    = "taskName";
        public static final String I_STATUS       = "status";
        public static final String I_CUPR_ID      = "cuprId";
        public static final String I_PERIOD_FROM  = "periodFrom";
        public static final String I_INV_ID       = "invId";
        public static final String I_AMOUNT       = "amount";
        public static final String I_ID           = "id";
        public static final String I_PAY_RATE     = "payRate";
        public static final String I_COMPANY_ID   = "companyId";
        public static final String I_PROJECT_NAME = "projectName";
        public static final String I_PERIOD_TO    = "periodTo";
        public static final String I_CUPT_ID      = "cuptId";
        public static final String I_FIX_PRICE    = "fixPrice";
        public static final String I_TEXT         = "text";
        public static final String I_DELETE       = "delete";

    }

    public static class B_INVOICE_TOTAL
    {
        public static final String ID               = "InvoiceTotal";
        public static final String I_SUBTOTAL_LABEL = "subtotalLabel";
        public static final String I_TAX_LABEL      = "taxLabel";
        public static final String I_TOTAL_LABEL    = "totalLabel";
        public static final String I_SUBTOTAL       = "subtotal";
        public static final String I_TAX            = "tax";
        public static final String I_TOTAL          = "total";
        public static final String I_CREATE_INVOICE = "createInvoice";
        public static final String I_AMOUNT_EXCL    = "amountExcl";
        public static final String I_VAT_AMOUNT     = "vatAmount";
        public static final String I_AMOUNT_INCL    = "amountIncl";

    }

    public static class B_INVOICE_CREATION
    {
        public static final String ID                = "InvoiceCreation";
        public static final String I_ID              = "id";
        public static final String I_COMPANY_ID      = "companyId";
        public static final String I_CUST_ID         = "custId";
        public static final String I_NR_LABEL        = "nrLabel";
        public static final String I_NR              = "nr";
        public static final String I_INV_DATE_LABEL  = "invDateLabel";
        public static final String I_INV_DATE        = "invDate";
        public static final String I_DUE_DATE        = "dueDate";
        public static final String I_DUE_DATE_LABEL  = "dueDateLabel";
        public static final String I_DUE_DATE_INFO   = "dueDateInfo";
        public static final String I_AMOUNT_EXCL_VAT = "amountExclVat";
        public static final String I_VAT_AMOUNT      = "vatAmount";
        public static final String I_AMOUNT_INCL_VAT = "amountInclVat";
        public static final String I_INVOICE_ADDRESS = "invoiceAddress";
        public static final String I_VAT_RATE        = "vatRate";
        public static final String I_INVOICE_SUMMARY = "invoiceSummary";
        public static final String I_INVOICE_NOTES   = "invoiceNotes";
        public static final String I_INVOICE_FOOTER  = "invoiceFooter";

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

    public static class L_SALUTATIONS
    {
        public static final String ID           = "Salutations";
        public static final String I_VALUE      = "value";
        public static final String I_ID         = "id";
        public static final String I_COMPANY_ID = "companyId";

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

    public static final String C_FILTER                           = "filter";
    public static final String C_APPROVED_PROJECT_ITEMS           = "ApprovedProjectItems";
    public static final String C_MARKED_FOR_INVOICE               = "markedForInvoice";
    public static final String C_MARKED_FOR_INVOICE_PROJECT_ITEMS = "MarkedForInvoiceProjectItems";
    public static final String C_INVOICE_TOTAL                    = "InvoiceTotal";
    public static final String C_INVOICE_CREATION_POPUP           = "InvoiceCreationPopup";
    public static final String C_INVOICE_CREATION                 = "InvoiceCreation";

    public static final String AC_ADD_TO_INVOICE                  = "ADD_TO_INVOICE";
    public static final String AC_CREATE_FINAL_INVOICE            = "CREATE_FINAL_INVOICE";
    public static final String AC_DELETE_APPROVED_ITEM            = "DELETE_APPROVED_ITEM";
    public static final String AC_DELETE_MARKED_FOR_INVOICE__ITEM = "DELETE_MARKED_FOR_INVOICE__ITEM";
    public static final String AC_REFRESH_INVOICE_BLOCKS          = "REFRESH_INVOICE_BLOCKS";

    public static final String P_ITEMS_TO_INVOICE                 = "ITEMS_TO_INVOICE";

}