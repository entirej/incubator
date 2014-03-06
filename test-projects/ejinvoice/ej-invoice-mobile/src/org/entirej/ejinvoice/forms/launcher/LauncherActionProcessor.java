package org.entirej.ejinvoice.forms.launcher;

import org.entirej.ejinvoice.forms.constants.F_LAUNCH_PAGE;
import org.entirej.ejinvoice.forms.constants.F_MASTER_DATA;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.actionprocessor.EJDefaultFormActionProcessor;
import org.entirej.framework.core.actionprocessor.interfaces.EJFormActionProcessor;
import org.entirej.framework.core.enumerations.EJScreenType;

public class LauncherActionProcessor extends EJDefaultFormActionProcessor implements EJFormActionProcessor
{

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_LAUNCH_PAGE.AC_OPEN_INVOICE_OVERVIEW.equals(command))
        {
//            form.openForm(F_INVOICE_OVERVIEW.ID);
        }
        else if (F_LAUNCH_PAGE.AC_OPEN_MASTER_DATA.equals(command))
        {
            form.openForm(F_MASTER_DATA.ID);
        }
        else if (F_LAUNCH_PAGE.AC_OPEN_COMPANIES.equals(command))
        {
//            form.openForm(F_COMPANY.ID);
        }
    }

}
