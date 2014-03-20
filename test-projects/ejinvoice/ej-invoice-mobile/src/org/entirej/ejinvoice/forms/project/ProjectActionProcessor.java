package org.entirej.ejinvoice.forms.project;

import org.entirej.ejinvoice.forms.constants.F_PROJECT;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.actionprocessor.EJDefaultFormActionProcessor;
import org.entirej.framework.core.actionprocessor.interfaces.EJFormActionProcessor;

public class ProjectActionProcessor extends EJDefaultFormActionProcessor implements EJFormActionProcessor
{

    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        form.getBlock(F_PROJECT.B_CUSTOMERS.ID).executeQuery();
    }

}
