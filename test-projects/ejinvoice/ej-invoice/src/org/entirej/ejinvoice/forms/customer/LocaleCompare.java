package org.entirej.ejinvoice.forms.customer;

import java.util.Comparator;

class LocaleCompare implements Comparator<CustomerLocale>
{

    @Override
    public int compare(CustomerLocale o1, CustomerLocale o2)
    {
        // write comparison logic here like below , it's just a sample
        return (o1.getCountry() + "-" + o1.getLanguage() + "-" + o1.getCcyCode()).compareTo((o2.getCountry() + "-" + o2.getLanguage() + "-" + o2.getCcyCode()));
    }
}