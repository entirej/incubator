package org.entirej.ejinvoice.reports.constants;

/* AUTO-GENERATED FILE.  DO NOT MODIFY. 
 *
 * This class was automatically generated by the
 * entirej plugin from the report.  It
 * should not be modified by hand.
 */
public class R_INV_A4
{
    public static final String ID = "INV_A4";

    public static class B_INV_POSITIONS
    {
        public static final String ID             = "InvPositions";
        public static final String I_PROJECT_NAME = "projectName";
        public static final String I_PERIOD_TO    = "periodTo";
        public static final String I_AMOUNT       = "amount";
        public static final String I_PAY_RATE     = "payRate";
        public static final String I_PERIOD_FROM  = "periodFrom";
        public static final String I_TEXT         = "text";
        public static final String I_CUPT_ID      = "cuptId";
        public static final String I_ID           = "id";
        public static final String I_COMPANY_ID   = "companyId";
        public static final String I_HOURS_WORKED = "hoursWorked";
        public static final String I_INV_ID       = "invId";
        public static final String I_STATUS       = "status";
        public static final String I_CUPR_ID      = "cuprId";
        public static final String I_FIX_PRICE    = "fixPrice";
        public static final String I_TASK_NAME    = "taskName";

        public static class C_DESCRIPTION
        {
            public static final String ID = "Description";

            public static class HEADER
            {
                public static final String I_DESCRIPTION = "Description";

            }

            public static class DETAIL
            {
                public static final String I_NOTES   = "NOTES";
                public static final String I_FROM_DT = "FROM_DT";
                public static final String I_PRD_SEP = "PRD_SEP";
                public static final String I_TO_DT   = "TO_DT";

            }
        }

        public static class C_HOURS_WORKED
        {
            public static final String ID = "Hours Worked";

            public static class HEADER
            {
                public static final String I_HOURS_WORKED = "Hours Worked";

            }

            public static class DETAIL
            {
                public static final String I_HOURS_WORKED = "hoursWorked";

            }
        }

        public static class C_UNIT_PRICE
        {
            public static final String ID = "Unit Price";

            public static class HEADER
            {
                public static final String I_UNIT_PRICE = "Unit Price";

            }

            public static class DETAIL
            {
                public static final String I_PAY_RATE = "payRate";

            }
        }

        public static class C_FIXED_PRICE
        {
            public static final String ID = "Fixed Price";

            public static class HEADER
            {
                public static final String I_FIXED_PRICE = "Fixed Price";

            }

            public static class DETAIL
            {
                public static final String I_FIX_PRICE = "fixPrice";

            }
        }

        public static class C_AMOUNT
        {
            public static final String ID = "amount";

            public static class HEADER
            {
                public static final String I_AMOUNT = "Amount";

            }

            public static class DETAIL
            {
                public static final String I_AMOUNT = "amount";

            }
        }
    }

    public static class B_INV_SUMMARY
    {
        public static final String ID = "InvSummary";

        public static class DETAIL
        {
            public static final String I_SUB_TOTAL    = "SUB_TOTAL";
            public static final String I_AMOUNT_EX_VT = "AMOUNT_EX_VT";

        }
    }

    public static class B_INVOICE
    {
        public static final String ID                = "Invoice";
        public static final String I_ID              = "id";
        public static final String I_VAT_NR          = "vatNr";
        public static final String I_LOCALE_LANGUAGE = "localeLanguage";
        public static final String I_COMPANY_NAME    = "companyName";
        public static final String I_NR              = "nr";
        public static final String I_CUST_NAME       = "custName";
        public static final String I_INVOICE_ADDRESS = "invoiceAddress";
        public static final String I_INVOICE_SUMMARY = "invoiceSummary";
        public static final String I_INVOICE_NOTES   = "invoiceNotes";
        public static final String I_LOCALE_COUNTRY  = "localeCountry";
        public static final String I_INV_DATE        = "invDate";
        public static final String I_VAT_AMOUNT      = "vatAmount";
        public static final String I_DUE_DATE        = "dueDate";
        public static final String I_COMPANY_ADDRESS = "companyAddress";
        public static final String I_AMOUNT_INCL_VAT = "amountInclVat";
        public static final String I_VAT_RATE        = "vatRate";
        public static final String I_AMOUNT_EXCL_VAT = "amountExclVat";
        public static final String I_COMPANY_LOGO    = "companyLogo";

        public static class DETAIL
        {
            public static final String I_TITLE            = "TITLE";
            public static final String I_COMPANY_NAME     = "COMPANY_NAME";
            public static final String I_COMPANY_ADDRESS  = "COMPANY_ADDRESS";
            public static final String I_VAT_NR           = "VAT_NR";
            public static final String I_TITLE_LINE       = "TITLE_LINE";
            public static final String I_LBL_FROM         = "LBL_FROM";
            public static final String I_LBL_VAT_NO       = "LBL_VAT_NO";
            public static final String I_COMP_LOGO        = "COMP_LOGO";
            public static final String I_INV_ID           = "INV_ID";
            public static final String I_ISSUE_DATE       = "ISSUE_DATE";
            public static final String I_DUE_DATE         = "DUE_DATE";
            public static final String I_INV_LINE         = "INV_LINE";
            public static final String I_NR               = "NR";
            public static final String I_INV_DATE         = "INV_DATE";
            public static final String I_INV_DUE_DATE     = "INV_DUE_DATE";
            public static final String I_DUE_NOTE         = "DUE_NOTE";
            public static final String I_INV_FOR          = "INV_FOR";
            public static final String I_INV_FOR_LINE     = "INV_FOR_LINE";
            public static final String I_CUST_NAME        = "CUST_NAME";
            public static final String I_INV_ADDRESS      = "INV_ADDRESS";
            public static final String I_INV_SUMMARY_LINE = "INV_SUMMARY_LINE";
            public static final String I_INV_SUMMARY      = "INV_SUMMARY";

        }
    }

    public static class B_FOOTER
    {
        public static final String ID = "Footer";

        public static class DETAIL
        {
            public static final String I_FOOTER_LINE = "footerLine";
            public static final String I_INV_NOTES   = "INV_NOTES";
            public static final String I_INV_GEN_BY  = "INV_GEN_BY";
            public static final String I_GEN_BY_IMG  = "GEN_BY_IMG";

        }
    }

    public static final String P_INV_ID        = "INV_ID";
    public static final String P_EJ_BIZIBO_IMG = "EJ_BIZIBO_IMG";

}