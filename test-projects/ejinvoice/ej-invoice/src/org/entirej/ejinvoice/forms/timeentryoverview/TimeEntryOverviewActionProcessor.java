package org.entirej.ejinvoice.forms.timeentryoverview;

import java.sql.Date;
import java.util.Calendar;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.enums.UserRole;
import org.entirej.ejinvoice.forms.company.User;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY_OVERVIEW;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.enumerations.EJScreenType;

public class TimeEntryOverviewActionProcessor extends DefaultFormActionProcessor
{

    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.DAY_OF_MONTH, 1);
        
        User user = (User)form.getApplicationLevelParameter(EJ_PROPERTIES.P_USER).getValue();
        form.getBlock(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_FILTER.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_FILTER.I_USER_ID).setValue(user.getId());
        if (user.getRole().equals(UserRole.EMPLOYEE.toString()))
        {
            form.getBlock(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_FILTER.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_FILTER.I_USER_ID).setVisible(false);
        }
        
        Date dateFrom = new Date(c.getTime().getTime());
        Date dateTo   = new Date(System.currentTimeMillis());
        
        form.getBlock(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_FILTER.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_FILTER.I_DATE_FROM).setValue(dateFrom);
        form.getBlock(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_FILTER.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_FILTER.I_DATE_TO).setValue(dateTo);
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_TIME_ENTRY_OVERVIEW.AC_REFRESH_USER_TOTAL_HOURS.equals(command))
        {
            form.getBlock(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS.ID).executeQuery();
            form.getBlock(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_OVERVIEW.ID).executeQuery();
        }
    }

    @Override
    public void postQuery(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS.ID.equals(record.getBlockName()))
        {
//            Integer headerKey = (Integer) record.getValue(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS.I_HEADER_CODE);
//
//            if (headerKey != null && 1 == headerKey)
//            {
//                record.getItem(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS.I_DESCRIPTION).setVisualAttribute(EJ_PROPERTIES.VA_TABLE_HEADER);
//                record.getItem(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS.I_HOURS).setVisualAttribute(EJ_PROPERTIES.VA_TABLE_HEADER);
//            }
//            else if (headerKey == null)
//            {
//                record.getItem(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS.I_DESCRIPTION).setVisualAttribute(null);
//                record.getItem(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS.I_HOURS).setVisualAttribute(null);
//            }
//            else if (headerKey == 2)
//            {
//                record.getItem(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS.I_DESCRIPTION).setVisualAttribute(EJ_PROPERTIES.VA_DATA_BOLD);
//                record.getItem(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS.I_HOURS).setVisualAttribute(EJ_PROPERTIES.VA_DATA_BOLD);
//            }
        }
        if (F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_OVERVIEW.ID.equals(record.getBlockName()))
        {
            Integer headerKey = (Integer) record.getValue(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_OVERVIEW.I_HEADER_CODE);

            if (headerKey != null && 1 == headerKey)
            {
                record.getItem(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_OVERVIEW.I_PROJECT_DESCRIPTION).setVisualAttribute(EJ_PROPERTIES.VA_TABLE_HEADER);
                record.getItem(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_OVERVIEW.I_WORK_DESCRIPTION).setVisualAttribute(EJ_PROPERTIES.VA_TABLE_HEADER);
                record.getItem(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_OVERVIEW.I_WORK_PERIOD).setVisualAttribute(EJ_PROPERTIES.VA_TABLE_HEADER);
                record.getItem(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_OVERVIEW.I_HOURS).setVisualAttribute(EJ_PROPERTIES.VA_TABLE_HEADER);
                record.getItem(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_OVERVIEW.I_WORK_DATE).setVisualAttribute(EJ_PROPERTIES.VA_TABLE_HEADER);
            }
            else if (headerKey != null && 2 == headerKey)
            {
                record.getItem(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_OVERVIEW.I_PROJECT_DESCRIPTION).setVisualAttribute(EJ_PROPERTIES.VA_TABLE_HEADER_2);
                record.getItem(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_OVERVIEW.I_WORK_DESCRIPTION).setVisualAttribute(EJ_PROPERTIES.VA_TABLE_HEADER_2);
                record.getItem(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_OVERVIEW.I_WORK_PERIOD).setVisualAttribute(EJ_PROPERTIES.VA_TABLE_HEADER_2);
                record.getItem(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_OVERVIEW.I_HOURS).setVisualAttribute(EJ_PROPERTIES.VA_TABLE_HEADER_2);
                record.getItem(F_TIME_ENTRY_OVERVIEW.B_USER_TOTAL_HOURS_OVERVIEW.I_WORK_DATE).setVisualAttribute(EJ_PROPERTIES.VA_TABLE_HEADER_2);
            }
        }
    }

}
