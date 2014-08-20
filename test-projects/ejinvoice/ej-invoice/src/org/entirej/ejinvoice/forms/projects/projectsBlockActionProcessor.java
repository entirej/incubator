package org.entirej.ejinvoice.forms.projects;

import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.forms.constants.F_PROJECTS;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;

public class projectsBlockActionProcessor extends DefaultFormActionProcessor
{

    @Override
    public void postQuery(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (record.getValue(F_PROJECTS.B_PROJECTS.I_INVOICEABLE).equals("Y"))
        {
            record.setValue(F_PROJECTS.B_PROJECTS.I_INVOICEABLE_IMAGE, "/icons/coins.png");
        }
        else
        {
            record.setValue(F_PROJECTS.B_PROJECT_TASKS.I_INVOICEABLE_IMAGE, null);
        }
    }

}
