package org.entirej.ejinvoice.forms.customer;

import java.util.Comparator;

class LocaleCompare implements Comparator<CustomerLocale>
{

    @Override
    public int compare(CustomerLocale o1, CustomerLocale o2)
    {
        int compareTo = o1.getCountryDesc().compareTo(o2.getCountryDesc());
        if(compareTo==0)
        {
            compareTo = o1.getLanguageDesc().compareTo(o2.getLanguageDesc());
            if(compareTo==0)
            {
                compareTo = o1.getCcyCode().compareTo(o2.getCcyCode());
            }
        }
        
        return compareTo;
    }
}