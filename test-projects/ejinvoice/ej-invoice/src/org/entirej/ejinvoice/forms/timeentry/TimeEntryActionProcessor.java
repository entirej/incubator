package org.entirej.ejinvoice.forms.timeentry;

import org.entirej.ejinvoice.forms.constants.F_COMPANY;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.actionprocessor.EJDefaultFormActionProcessor;
import org.entirej.framework.core.actionprocessor.interfaces.EJFormActionProcessor;
import org.entirej.framework.core.enumerations.EJScreenType;

public class TimeEntryActionProcessor extends EJDefaultFormActionProcessor implements EJFormActionProcessor
{

    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        form.getBlock(F_TIME_ENTRY.B_COMPANY.ID).executeQuery();
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {

        if (F_TIME_ENTRY.AC_EDIT_COMPANY_INFORMATION.equals(command))
        {
            form.openForm(F_COMPANY.ID);
        }

    }

 
    @Override
    public void tabPageChanged(EJForm form, String tabCanvasName, String tabPageName) throws EJActionProcessorException
    {
        if (F_TIME_ENTRY.C_MAIN.equals(tabCanvasName))
        {
            if (F_TIME_ENTRY.C_MAIN_PAGES.PROJECTS.equals(tabPageName))
            {
                form.getBlock(F_TIME_ENTRY.B_PROJECTS.ID).executeQuery();
            }
        }
    }

    @Override
    public void postQuery(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_TIME_ENTRY.B_COMPANY.ID.equals(record.getBlockName()))
        {
            String name = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_NAME);
            String address1 = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_ADDRESS_LINE_1);
            String address2 = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_ADDRESS_LINE_2);
            String address3 = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_ADDRESS_LINE_3);
            String zip = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_POST_CODE);
            String town = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_TOWN);
            String country = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_COUNTRY);
            String vatNr = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_VAT_NR);

            StringBuilder str = new StringBuilder();
            str.append(name);
            str.append("\n");
            if (address1 != null)
            {
                str.append(address1);
                str.append("\n");
            }
            if (address2 != null)
            {
                str.append(address2);
                str.append("\n");
            }
            if (address3 != null)
            {
                str.append(address3);
                str.append("\n");
            }
            str.append(zip);
            str.append(" ");
            str.append(town);
            str.append(" ");
            str.append(country);
            str.append("\n");
            str.append(vatNr);

            record.setValue(F_TIME_ENTRY.B_COMPANY.I_ADDRESS, str.toString());
        }
    }

}
