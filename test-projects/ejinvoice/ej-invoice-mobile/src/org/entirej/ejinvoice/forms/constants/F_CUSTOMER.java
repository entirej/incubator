package org.entirej.ejinvoice.forms.constants;

/*
 * AUTO-GENERATED FILE. DO NOT MODIFY.
 *
 * This class was automatically generated by the entirej plugin from the form.
 * It should not be modified by hand.
 */
public class F_CUSTOMER
{
    public static final String ID = "Customer";

    public static class B_CUSTOMER
    {
        public static final String ID                  = "Customer";
        public static final String I_USER_ID           = "userId";
        public static final String I_ID                = "id";
        public static final String I_ADDRESS_LINE_1    = "addressLine1";
        public static final String I_ADDRESS_LINE_2    = "addressLine2";
        public static final String I_NAME              = "name";
        public static final String I_POST_CODE         = "postCode";
        public static final String I_ADDRESS_LINE_3    = "addressLine3";
        public static final String I_TOWN              = "town";
        public static final String I_COUNTRY           = "country";
        public static final String I_CUSTOMER_CONTACTS = "customerContacts";

    }

    public static class B_CUSTOMER_CONTACTS
    {
        public static final String ID                 = "CustomerContacts";
        public static final String I_EMAIL            = "email";
        public static final String I_PHONE            = "phone";
        public static final String I_USER_ID          = "userId";
        public static final String I_CONTACT_TYPES_ID = "contactTypesId";
        public static final String I_CUSTOMER_ID      = "customerId";
        public static final String I_ID               = "id";
        public static final String I_FIRST_NAME       = "firstName";
        public static final String I_SALUTATIONS_ID   = "salutationsId";
        public static final String I_LAST_NAME        = "lastName";
        public static final String I_MOBILE           = "mobile";
        public static final String I_PHONE_LABEL      = "phoneLabel";
        public static final String I_MOBILE_LABEL     = "mobileLabel";
        public static final String I_EMAIL_LABEL      = "emailLabel";
        public static final String I__DEATILS         = "_deatils";

    }

    public static class L_CONTACT_TYPES
    {
        public static final String ID            = "ContactTypes";
        public static final String I_USER_ID     = "userId";
        public static final String I_ID          = "id";
        public static final String I_TYPE        = "type";
        public static final String I_DESCRIPTION = "description";

    }

    public static class L_SALUTATIONS
    {
        public static final String ID        = "Salutations";
        public static final String I_VALUE   = "value";
        public static final String I_ID      = "id";
        public static final String I_USER_ID = "userId";

    }

    public static final String C_CUSTOMER                = "Customer";
    public static final String C_CUSTOMER_CONTACTS_POPUP = "CustomerContactsPopup";
    public static final String C_CUSTOMER_CONTACTS       = "CustomerContacts";

    public static final String AC_DELETE                 = "DELETE";
    public static final String AC_EDIT                   = "EDIT";
    public static final String AC_NEW                    = "NEW";
    public static final String AC_SHOW_CONTACTS          = "SHOW_CONTACTS";

}