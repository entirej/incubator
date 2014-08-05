package org.entirej.ejinvoice.forms.constants;

/* AUTO-GENERATED FILE.  DO NOT MODIFY. 
 *
 * This class was automatically generated by the
 * entirej plugin from the form.  It
 * should not be modified by hand.
 */
public class F_INVOICE
{
    public static final String ID = "Invoice";

    public static class B_INVOICE_FILTER
    {
        public static final String ID          = "InvoiceFilter";
        public static final String I__CUSTOMER = "_customer";

    }

    public static class B_INVOICE
    {
        public static final String ID                = "Invoice";
        public static final String I_NR              = "nr";
        public static final String I_AMOUNT_EXCL_VAT = "amountExclVat";
        public static final String I_AMOUNT_INCL_VAT = "amountInclVat";
        public static final String I_ID              = "id";
        public static final String I_INV_DATE        = "invDate";
        public static final String I_USER_ID         = "userId";
        public static final String I_CONF_ID         = "confId";
        public static final String I_PAYINF_ID       = "payinfId";
        public static final String I_PRINTED         = "printed";
        public static final String I_INVOICE_ID      = "invoiceId";
        public static final String I_CUST_ID         = "custId";
        public static final String I_PAID            = "paid";
        public static final String I__POSITIONS      = "_positions";
        public static final String I__PAID_LABEL     = "_paidLabel";
        public static final String I__PRINED_LABEL   = "_prinedLabel";
        public static final String I__VAT_LABEL      = "_vatLabel";

    }

    public static class B_INVOICE_POSITIONS
    {
        public static final String ID                   = "InvoicePositions";
        public static final String I_HOURS_WORKED       = "hoursWorked";
        public static final String I_USER_ID            = "userId";
        public static final String I_POSITION           = "position";
        public static final String I_DESCRIPTION        = "description";
        public static final String I_CUPR_ID            = "cuprId";
        public static final String I_ID                 = "id";
        public static final String I_INV_ID             = "invId";
        public static final String I_VAT_ID             = "vatId";
        public static final String I__DETAILS           = "_details";
        public static final String I__HOURS_LABEL       = "_hoursLabel";
        public static final String I_AMOUNT             = "amount";
        public static final String I_VAT_RATE           = "vatRate";
        public static final String I_CUST_PROJ_PAY_RATE = "custProjPayRate";

    }

    public static class L_CONTACT_TYPES
    {
        public static final String ID            = "ContactTypes";
        public static final String I_USER_ID     = "userId";
        public static final String I_ID          = "id";
        public static final String I_TYPE        = "type";
        public static final String I_DESCRIPTION = "description";

    }

    public static class L_CUSTOMERS
    {
        public static final String ID               = "Customers";
        public static final String I_USER_ID        = "userId";
        public static final String I_ID             = "id";
        public static final String I_ADDRESS_LINE_1 = "addressLine1";
        public static final String I_ADDRESS_LINE_2 = "addressLine2";
        public static final String I_NAME           = "name";
        public static final String I_POST_CODE      = "postCode";
        public static final String I_ADDRESS_LINE_3 = "addressLine3";
        public static final String I_TOWN           = "town";
        public static final String I_COUNTRY        = "country";

    }

    public static class L_COMPANY
    {
        public static final String ID                    = "Company";
        public static final String I_POST_CODE           = "postCode";
        public static final String I_ADDRESS_LINE_2      = "addressLine2";
        public static final String I_BANK_ADDRESS_LINE_2 = "bankAddressLine2";
        public static final String I_ACCOUNT_NUMBER      = "accountNumber";
        public static final String I_ADDRESS_LINE_1      = "addressLine1";
        public static final String I_BANK_ADDRESS_LINE_1 = "bankAddressLine1";
        public static final String I_BANK_POST_CODE      = "bankPostCode";
        public static final String I_IBAN                = "iban";
        public static final String I_ID                  = "id";
        public static final String I_ADDRESS_LINE_3      = "addressLine3";
        public static final String I_USER_ID             = "userId";
        public static final String I_BANK_TOWN           = "bankTown";
        public static final String I_NAME                = "name";
        public static final String I_TOWN                = "town";
        public static final String I_COUNTRY             = "country";
        public static final String I_BANK_NAME           = "bankName";
        public static final String I_BANK_COUNTRY        = "bankCountry";
        public static final String I_VAT_NR              = "vatNr";
        public static final String I_BANK_ADDRESS_LINE_3 = "bankAddressLine3";

    }

    public static class L_PAYMENT_INFO
    {
        public static final String ID              = "PaymentInfo";
        public static final String I_ID            = "id";
        public static final String I_USER_ID       = "userId";
        public static final String I_PAYMENT_TERMS = "paymentTerms";

    }

    public static class L_PROJECTS
    {
        public static final String ID            = "Projects";
        public static final String I_ID          = "id";
        public static final String I_DESCRIPTION = "description";
        public static final String I_NAME        = "name";
        public static final String I_PAY_RATE    = "payRate";
        public static final String I_USER_ID     = "userId";
        public static final String I_CUSTOMER_ID = "customerId";

    }

    public static class L_VAT_RATE
    {
        public static final String ID        = "VatRate";
        public static final String I_USER_ID = "userId";
        public static final String I_RATE    = "rate";
        public static final String I_ID      = "id";
        public static final String I_NOTES   = "notes";
        public static final String I_NAME    = "name";

    }

    public static final String C_INVOICE_FILTER  = "InvoiceFilter";
    public static final String C_INVOICE         = "Invoice";
    public static final String C_POSITIONS_POPUP = "PositionsPopup";
    public static final String C_POSITIONS       = "Positions";

    public static final String AC_DELETE         = "DELETE";
    public static final String AC_EDIT           = "EDIT";
    public static final String AC_NEW            = "NEW";
    public static final String AC_SHOW_POSITIONS = "SHOW_POSITIONS";

}