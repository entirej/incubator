package org.entirej.ejinvoice.forms.timeentry;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Locale.Builder;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.custom.renderers.WorkWeekBlockRenderer;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.company.User;
import org.entirej.ejinvoice.forms.constants.F_COMPANY;
import org.entirej.ejinvoice.forms.constants.F_CUSTOMER_CONTACTS;
import org.entirej.ejinvoice.forms.constants.F_MASTER_DATA_SALUTATION;
import org.entirej.ejinvoice.forms.constants.F_PROJECTS;
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY;
import org.entirej.ejinvoice.forms.projects.InvoiceHistory;
import org.entirej.ejinvoice.forms.projects.InvoiceHistoryBlockService;
import org.entirej.ejinvoice.forms.projects.ProjectService;
import org.entirej.ejinvoice.forms.projects.reports.InvoiceReport;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJParameterList;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.EJScreenItem;
import org.entirej.framework.core.data.controllers.EJFormParameter;
import org.entirej.framework.core.data.controllers.EJQuestion;
import org.entirej.framework.core.enumerations.EJMessageLevel;
import org.entirej.framework.core.enumerations.EJPopupButton;
import org.entirej.framework.core.enumerations.EJQuestionButton;
import org.entirej.framework.core.enumerations.EJScreenType;
import org.entirej.framework.core.service.EJQueryCriteria;

public class TimeEntryActionProcessor extends DefaultFormActionProcessor
{
    private boolean invoiceUpdated       = false;
    private Integer updatedInvoiceId     = null;
    private Locale  updatedInvoiceLocale = null;
    private boolean customerInserted     = false;
    private boolean customerUpdated      = false;
    private Integer customerId           = null;

    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        form.getBlock(F_TIME_ENTRY.B_COMPANY.ID).executeQuery();
        form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID).executeQuery(TimeEntryBlockService.getWeeKQueryCriteria(new EJQueryCriteria(form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID)), TimeEntryBlockService.getCurrentWeek()));

        EJScreenItem startTime = form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_START_TIME);
        EJScreenItem endTime = form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_END_TIME);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        startTime.setValue(timestamp);
        endTime.setValue(timestamp);

        form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_HOURS).setValue(getDiffMinutesString(timestamp.getTime(), timestamp.getTime()));

        User user = (User)form.getApplicationLevelParameter(EJ_PROPERTIES.P_USER).getValue();
        
//        if (user.getRole().equals(UserRole.CONTROLLER.toString()))
//        {
//            form.
//        }
//        
        
        form.openEmbeddedForm(F_PROJECTS.ID, F_TIME_ENTRY.C_PROJECT_FORM, null);
        form.openEmbeddedForm(F_COMPANY.ID, F_TIME_ENTRY.C_COMPANY_FORM, null);
    }

    @Override
    public void validateItem(EJForm form, EJRecord record, String itemName, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID.equals(record.getBlockName()))
        {
            if (F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_PROJECT.equals(itemName))
            {
                form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(screenType, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_TASK).refreshItemRenderer();
            }

            else if (F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_START_TIME.equals(itemName) || F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_END_TIME.equals(itemName))
            {
                Timestamp start = (Timestamp) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_START_TIME).getValue();
                Timestamp end = (Timestamp) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_END_TIME).getValue();

                form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_HOURS).setValue(getDiffMinutesString(start.getTime(), end.getTime()));
            }
            else if (F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_WORK_DATE.equals(itemName))
            {
                Date workDate = (Date) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_WORK_DATE).getValue();

                form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID).executeQuery(TimeEntryBlockService.getWeeKQueryCriteria(new EJQueryCriteria(form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID)), TimeEntryBlockService.getWeek(workDate)));
            }
        }
        else if (F_TIME_ENTRY.B_TIME_ENTRY.ID.equals(record.getBlockName()))
        {
            if (F_TIME_ENTRY.B_TIME_ENTRY.I_START_TIME.equals(itemName) || F_TIME_ENTRY.B_TIME_ENTRY.I_END_TIME.equals(itemName))
            {
                Time start = (Time) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID).getScreenItem(EJScreenType.UPDATE, F_TIME_ENTRY.B_TIME_ENTRY.I_START_TIME).getValue();
                Time end = (Time) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID).getScreenItem(EJScreenType.UPDATE, F_TIME_ENTRY.B_TIME_ENTRY.I_END_TIME).getValue();

                record.setValue(F_TIME_ENTRY.B_TIME_ENTRY.I_HOURS_WORKED, getDiffMinutesString(start.getTime(), end.getTime()));
            }
        }
    }

    private String getDiffMinutesString(Long start, Long end)
    {
        // remove seconds
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(start);
        instance.set(Calendar.SECOND, 0);
        instance.set(Calendar.MILLISECOND, 0);
        start = instance.getTimeInMillis();
        instance.setTimeInMillis(end);
        instance.set(Calendar.SECOND, 0);
        instance.set(Calendar.MILLISECOND, 0);
        end = instance.getTimeInMillis();

        long diff = end - start;
        final long hour = 3600000;
        final long minute = 60000;
        long diffHours = (diff / hour);
        long diffMinutes = ((diff % hour) / minute);
        String diffMinutesString = String.format("%02d", diffMinutes);

        return diffHours + ":" + diffMinutesString;
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        EJBlock timeEntryBlock = form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY.ID);
        if (F_TIME_ENTRY.AC_DELETE_TIME_ENTRY.equals(command))
        {
            timeEntryBlock.askToDeleteCurrentRecord();
            return;
        }
        else if (F_TIME_ENTRY.AC_EDIT_TIME_ENTRY.equals(command))
        {

            timeEntryBlock.enterUpdate();
            return;
        }
        else if (WorkWeekBlockRenderer.isWeekSelectionAction(command))
        {

            timeEntryBlock.executeQuery(TimeEntryBlockService.getWeeKQueryCriteria(new EJQueryCriteria(timeEntryBlock), WorkWeekBlockRenderer.getWeekSelection(command)));
            return;
        }
        else if (WorkWeekBlockRenderer.isDaySelectionAction(command))
        {

            java.util.Date daySelection = WorkWeekBlockRenderer.getDaySelection(command);
            if (daySelection != null)
            {
                form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_WORK_DATE).setValue(new Date(daySelection.getTime()));

            }
            return;
        }
        else if (F_TIME_ENTRY.AC_CREATE_NEW_CUSTOMER.equals(command))
        {
            form.getBlock(F_TIME_ENTRY.B_CUSTOMERS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_CUSTOMERS.I_SALUTATIONS_ID).refreshItemRenderer();
            form.getBlock(F_TIME_ENTRY.B_CUSTOMERS.ID).getScreenItem(EJScreenType.INSERT, F_TIME_ENTRY.B_CUSTOMERS.I_CONTACT_TYPES_ID).refreshItemRenderer();
            form.getBlock(F_TIME_ENTRY.B_CUSTOMERS.ID).enterInsert(false);
        }
        else if (F_TIME_ENTRY.AC_EDIT_CUSTOMER.equals(command))
        {
            form.getBlock(F_TIME_ENTRY.B_CUSTOMERS.ID).enterUpdate();
        }
        else if (F_TIME_ENTRY.AC_DELETE_CUSTOMER.equals(command))
        {
            EJQuestion question = new EJQuestion(form, "ASK_DELETE_CUSTOMER");
            question.setTitle("Delete Customer");
            question.setMessage(new EJMessage("Are you sure you want to delete this customer?"));
            question.setButtonText(EJQuestionButton.ONE, "Yes");
            question.setButtonText(EJQuestionButton.TWO, "Cancel");
            question.setRecord(record);
            form.askQuestion(question);
        }
        else if (F_TIME_ENTRY.AC_SHOW_CUSTOMER_DETAILS.equals(command))
        {
            EJParameterList paramList = new EJParameterList();
            EJFormParameter cstParam = new EJFormParameter(F_CUSTOMER_CONTACTS.P_CST_ID, Integer.class);
            cstParam.setValue(record.getValue(F_TIME_ENTRY.B_CUSTOMERS.I_ID));

            EJFormParameter cstInfoParam = new EJFormParameter(F_CUSTOMER_CONTACTS.P_CUSTOMER_INFORMATION, String.class);
            cstInfoParam.setValue("Customer: " + record.getValue(F_TIME_ENTRY.B_CUSTOMERS.I_CUSTOMER_NUMBER) + " (" + record.getValue(F_TIME_ENTRY.B_CUSTOMERS.I_NAME) + ")");

            paramList.addParameter(cstParam);
            paramList.addParameter(cstInfoParam);

            form.showStackedCanvasPage(F_TIME_ENTRY.C_CUSTOMER_STACK, F_TIME_ENTRY.C_CUSTOMER_STACK_PAGES.CUSTOMER_DETAILS);
            form.openEmbeddedForm(F_CUSTOMER_CONTACTS.ID, F_TIME_ENTRY.C_CUSTOMER_DETAILS_FORM, paramList);
        }
        else if (F_TIME_ENTRY.AC_ADD_TIME_ENTRY.equals(command))
        {
            Integer userId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_USER_ID).getValue();
            Timestamp start = (Timestamp) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_START_TIME).getValue();
            Timestamp end = (Timestamp) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_END_TIME).getValue();
            Date workDay = (Date) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_WORK_DATE).getValue();
            String workDescription = (String) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_NOTES).getValue();
            Integer cuptId = (Integer) form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_TASK).getValue();

            int idSeqNextval = PKSequenceService.getPKSequence(form.getConnection());

            if (start.after(end))
            {
                EJMessage message = new EJMessage(EJMessageLevel.ERROR, "Please enter an End Time later than the given Start Time");
                form.showMessage(message);
                return;
            }

            if (workDescription == null || workDescription.trim().length() == 0)
            {
                EJMessage message = new EJMessage(EJMessageLevel.ERROR, "Please enter a work description");
                form.showMessage(message);
                return;
            }

            if (cuptId == null)
            {
                EJMessage message = new EJMessage(EJMessageLevel.ERROR, "Please choose a project and a project task");
                form.showMessage(message);
                return;
            }

            Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
            TimeEntry timeEntry = new TimeEntry();

            timeEntry.setId(idSeqNextval);
            timeEntry.setUserId(userId);
            timeEntry.setCuptId(cuptId);
            timeEntry.setEndTime(new Time(end.getTime()));
            timeEntry.setStartTime(new Time(start.getTime()));
            timeEntry.setWorkDescription(workDescription);
            timeEntry.setWorkDate(workDay);
            timeEntry.setCompanyId(companyId);

            TimeEntryBlockService service = new TimeEntryBlockService();
            service.enterTimeEntry(form, timeEntry);

            timeEntryBlock.executeLastQuery();

            form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_START_TIME).setValue(end);
            form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_NOTES).setValue(null);
            form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_NOTES).gainFocus();

            form.getBlock(F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_TIME_ENTRY_ENTRY.I_HOURS).setValue(getDiffMinutesString(end.getTime(), end.getTime()));
        }
        else if (F_TIME_ENTRY.AC_QUERY_CUSTOMERS.equals(command))
        {
            form.getBlock(F_TIME_ENTRY.B_CUSTOMERS.ID).executeQuery();
        }
        else if (F_TIME_ENTRY.AC_SHOW_INVOICE.equals(command))
        {
            InvoiceReport.downloadReport(new ProjectService().getInvoicPDF(form, (int) record.getValue(F_TIME_ENTRY.B_INVOICE_HISTORY.I_ID)), record.getValue(F_TIME_ENTRY.B_INVOICE_HISTORY.I_NR) + ".pdf");
        }
        else if (F_TIME_ENTRY.AC_INVOICE_HISTORY_STATUS_CHANGED.equals(command))
        {
            form.getBlock(F_TIME_ENTRY.B_INVOICE_HISTORY.ID).executeQuery();
        }
        else if (F_TIME_ENTRY.AC_REFRESH_PAID_INVOICES.equals(command))
        {
            form.getBlock(F_TIME_ENTRY.B_INVOICE_HISTORY_PAID.ID).executeQuery();
        }
        else if (F_TIME_ENTRY.AC_UPDATE_INVOICE.equals(command))
        {
            form.getBlock(F_TIME_ENTRY.B_INVOICE_HISTORY.ID).enterUpdate();
        }
        else if (F_TIME_ENTRY.AC_INVOICE_ACTION.equals(command))
        {
            if (((String)record.getValue(F_TIME_ENTRY.B_INVOICE_HISTORY.I_ACTION)).equalsIgnoreCase("Mark as Sent"))
            {
                form.getBlock(F_TIME_ENTRY.B_SEND_INVOICE.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_SEND_INVOICE.I_SEND_DATE).setValue(new Date(System.currentTimeMillis()));
                form.getBlock(F_TIME_ENTRY.B_SEND_INVOICE.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_SEND_INVOICE.I_NOTES).setValue(record.getValue(F_TIME_ENTRY.B_INVOICE_HISTORY.I_NOTES));
                form.showPopupCanvas(F_TIME_ENTRY.C_SEND_INVOICE_POPUP);
            }
            else if (((String)record.getValue(F_TIME_ENTRY.B_INVOICE_HISTORY.I_ACTION)).equalsIgnoreCase("Record Payment"))
            {
                form.getBlock(F_TIME_ENTRY.B_PAY_INVOICE.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_PAY_INVOICE.I_PAYMENT_DATE).setValue(new Date(System.currentTimeMillis()));
                form.getBlock(F_TIME_ENTRY.B_PAY_INVOICE.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_PAY_INVOICE.I_NOTES).setValue(record.getValue(F_TIME_ENTRY.B_INVOICE_HISTORY.I_NOTES));
                form.showPopupCanvas(F_TIME_ENTRY.C_PAY_INVOICE_POPUP);
            }

        }
    }

    @Override
    public void questionAnswered(EJQuestion question) throws EJActionProcessorException
    {
        if (question.getName().equals("ASK_DELETE_CUSTOMER") && question.getAnswer().equals(EJQuestionButton.ONE))
        {
            boolean canBeDeleted = true;
            try
            {
                ServiceRetriever.getDBService(question.getForm()).validateDeleteRecordUsage(question.getForm(), question.getForm().getBlock(F_MASTER_DATA_SALUTATION.B_SALUTATIONS.ID).getFocusedRecord(), "CUSTOMER");
            }
            catch (Exception e)
            {
                canBeDeleted = false;
            }

            if (canBeDeleted)
            {
                ArrayList<Customer> customers = new ArrayList<Customer>();
                customers.add((Customer) question.getRecord().getBlockServicePojo());
                new CustomerBlockService().executeDelete(question.getForm(), customers);

                question.getForm().getBlock(F_TIME_ENTRY.B_CUSTOMERS.ID).executeQuery();
            }
            else
            {
                EJQuestion deactivateQuestion = new EJQuestion(question.getForm(), "ASK_DEACTIVATE_CUSTOMER");
                deactivateQuestion.setTitle("Deactivate Customer");
                deactivateQuestion.setMessage(new EJMessage("This customer has data depedencies and cannot be permanently deleted. Do you want to set the active flag of this customer to false?"));
                deactivateQuestion.setButtonText(EJQuestionButton.ONE, "Yes");
                deactivateQuestion.setButtonText(EJQuestionButton.TWO, "Cancel");
                deactivateQuestion.setRecord(question.getRecord());
                deactivateQuestion.getForm().askQuestion(deactivateQuestion);
            }
        }
        if (question.getName().equals("ASK_DEACTIVATE_CUSTOMER") && question.getAnswer().equals(EJQuestionButton.ONE))
        {
            ArrayList<Customer> customers = new ArrayList<Customer>();
            Customer cust = (Customer) question.getRecord().getBlockServicePojo();
            cust.setActive(0);
            customers.add(cust);
            new CustomerBlockService().executeUpdate(question.getForm(), customers);

            question.getForm().getBlock(F_TIME_ENTRY.B_CUSTOMERS.ID).executeQuery();
        }
    }

    @Override
    public void postUpdate(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_TIME_ENTRY.B_CUSTOMERS.ID.equals(record.getBlockName()))
        {
            customerUpdated = true;
        }
        else if (F_TIME_ENTRY.B_INVOICE_HISTORY.ID.equals(record.getBlockName()))
        {
            invoiceUpdated = true;

            if (record.getValue(F_TIME_ENTRY.B_INVOICE_HISTORY.I_SENT).equals(0))
            {
                updatedInvoiceId = (Integer) record.getValue(F_TIME_ENTRY.B_INVOICE_HISTORY.I_ID);
                String localeLanguage = (String) record.getValue(F_TIME_ENTRY.B_INVOICE_HISTORY.I_LOCALE_LANGUAGE);
                String localeCountry = (String) record.getValue(F_TIME_ENTRY.B_INVOICE_HISTORY.I_LOCALE_COUNTRY);
                updatedInvoiceLocale = new Builder().setLanguage(localeLanguage).setRegion(localeCountry).build();
            }
        }
    }

    @Override
    public void postInsert(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_TIME_ENTRY.B_CUSTOMERS.ID.equals(record.getBlockName()))
        {
            customerInserted = true;
            customerId = (Integer) record.getValue(F_TIME_ENTRY.B_CUSTOMERS.I_ID);
        }

    }

    @Override
    public void postDelete(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        form.saveChanges();
    }

    @Override
    public void postFormSave(EJForm form) throws EJActionProcessorException
    {
        if (customerInserted)
        {
            EJParameterList paramList = new EJParameterList();
            EJFormParameter cstParam = new EJFormParameter(F_CUSTOMER_CONTACTS.P_CST_ID, Integer.class);
            cstParam.setValue(customerId);
            paramList.addParameter(cstParam);

            form.showTabCanvasPage(F_TIME_ENTRY.C_MAIN, F_TIME_ENTRY.C_MAIN_PAGES.CUSTOMERS);
            form.showStackedCanvasPage(F_TIME_ENTRY.C_CUSTOMER_STACK, F_TIME_ENTRY.C_CUSTOMER_STACK_PAGES.CUSTOMER_DETAILS);
            form.openEmbeddedForm(F_CUSTOMER_CONTACTS.ID, F_TIME_ENTRY.C_CUSTOMER_DETAILS_FORM, paramList);
            customerInserted = false;
        }
        else if (customerUpdated)
        {
            customerUpdated = false;
            form.getBlock(F_TIME_ENTRY.B_CUSTOMERS.ID).executeQuery();
        }
        else if (invoiceUpdated)
        {
            invoiceUpdated = false;
            if (updatedInvoiceId != null)
            {
                new ProjectService().updateInvoicPDF(form, updatedInvoiceId, InvoiceReport.generateInvoicePDF(form.getConnection(), updatedInvoiceId, updatedInvoiceLocale));
                updatedInvoiceId = null;
                updatedInvoiceLocale = null;
            }
            form.getBlock(F_TIME_ENTRY.B_INVOICE_HISTORY.ID).executeQuery();
        }
    }

    @Override
    public void tabPageChanged(EJForm form, String tabCanvasName, String tabPageName) throws EJActionProcessorException
    {
        if (F_TIME_ENTRY.C_MAIN.equals(tabCanvasName))
        {
            if (F_TIME_ENTRY.C_MAIN_PAGES.CUSTOMERS.equals(tabPageName))
            {
                form.showStackedCanvasPage(F_TIME_ENTRY.C_CUSTOMER_STACK, F_TIME_ENTRY.C_CUSTOMER_STACK_PAGES.CUSTOMER_OVERVIEW);
                form.getBlock(F_TIME_ENTRY.B_CUSTOMERS.ID).executeQuery();
            }
            else if (F_TIME_ENTRY.C_MAIN_PAGES.PROJECTS.equals(tabPageName))
            {
                EJForm projectForm = form.getEmbeddedForm(F_PROJECTS.ID, F_TIME_ENTRY.C_PROJECT_FORM);
                if (projectForm.getDisplayedStackedCanvasPage(F_PROJECTS.C_PROJECT_STACK).equals(F_PROJECTS.C_PROJECT_STACK_PAGES.PROJECTS))
                {
                    projectForm.getBlock(F_PROJECTS.B_PROJECTS.ID).executeQuery();
                }
            }
            else if (F_TIME_ENTRY.C_MAIN_PAGES.COMPANY.equals(tabPageName))
            {
                EJForm companyForm = form.getEmbeddedForm(F_COMPANY.ID, F_TIME_ENTRY.C_COMPANY_FORM);
                if (companyForm.getBlock(F_COMPANY.B_COMPANIES.ID).getBlockRecords().size() <= 0)
                {
                    companyForm.getBlock(F_COMPANY.B_COMPANIES.ID).executeQuery();
                }
            }
            else if (F_TIME_ENTRY.C_MAIN_PAGES.INVOICE_HISTORY.equals(tabPageName))
            {
                form.getBlock(F_TIME_ENTRY.B_INVOICE_HISTORY.ID).executeQuery();
            }
        }
    }

    @Override
    public void postQuery(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_TIME_ENTRY.B_COMPANY.ID.equals(record.getBlockName()))
        {
            String name = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_NAME);
            String address = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_ADDRESS);
            String zip = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_POST_CODE);
            String town = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_TOWN);
            String country = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_COUNTRY);
            String vatNr = (String) record.getValue(F_TIME_ENTRY.B_COMPANY.I_VAT_NR);

            StringBuilder str = new StringBuilder();
            str.append(name);
            str.append("\n");
            if (address != null)
            {
                str.append(address);
                str.append("\n");
            }
            str.append((zip == null ? "" : zip));
            str.append(" ");
            str.append((town == null ? "" : town));
            str.append(" ");
            str.append((country == null ? "" : country));
            str.append("\n");
            str.append((vatNr == null ? "" : vatNr));

            record.setValue(F_TIME_ENTRY.B_COMPANY.I_DISPLAY_ADDRESS, str.toString());
        }
        else if (F_TIME_ENTRY.B_TIME_ENTRY.ID.equals(record.getBlockName()))
        {
            if (record.getValue(F_TIME_ENTRY.B_TIME_ENTRY.I_INVP_ID) == null)
            {
                record.setValue(F_TIME_ENTRY.B_TIME_ENTRY.I__EDIT, "/icons/edit10.gif");
                record.setValue(F_TIME_ENTRY.B_TIME_ENTRY.I__DELETE, "/icons/delete10.png");
            }
        }
        else if (F_TIME_ENTRY.B_INVOICE_HISTORY.ID.equals(record.getBlockName()) || F_TIME_ENTRY.B_INVOICE_HISTORY_PAID.ID.equals(record.getBlockName()))
        {
            if (record.getValue(F_TIME_ENTRY.B_INVOICE_HISTORY.I_STATUS).equals("DRAFT"))
            {
                record.getItem(F_TIME_ENTRY.B_INVOICE_HISTORY.I_STATUS).setVisualAttribute(EJ_PROPERTIES.VA_INVOIEC_STATUS_DRAFT);
            }
            else if (record.getValue(F_TIME_ENTRY.B_INVOICE_HISTORY.I_STATUS).equals("SENT"))
            {
                record.getItem(F_TIME_ENTRY.B_INVOICE_HISTORY.I_STATUS).setVisualAttribute(EJ_PROPERTIES.VA_INVOICE_STATUS_SENT);
            }
            else if (record.getValue(F_TIME_ENTRY.B_INVOICE_HISTORY.I_STATUS).equals("LATE"))
            {
                record.getItem(F_TIME_ENTRY.B_INVOICE_HISTORY.I_STATUS).setVisualAttribute(EJ_PROPERTIES.VA_INVOICE_STATUS_LATE);
            }
            else if (record.getValue(F_TIME_ENTRY.B_INVOICE_HISTORY.I_STATUS).equals("PAID"))
            {
                record.getItem(F_TIME_ENTRY.B_INVOICE_HISTORY.I_STATUS).setVisualAttribute(EJ_PROPERTIES.VA_INVOICE_STATUS_PAID);
            }
            
            if (F_TIME_ENTRY.B_INVOICE_HISTORY.ID.equals(record.getBlockName()))
            {
                if (record.getValue(F_TIME_ENTRY.B_INVOICE_HISTORY.I_STATUS).equals("DRAFT"))
                {
                    record.getItem(F_TIME_ENTRY.B_INVOICE_HISTORY.I_ACTION).setValue("Mark as sent");
                }
                else if (record.getValue(F_TIME_ENTRY.B_INVOICE_HISTORY.I_STATUS).equals("SENT"))
                {
                    record.getItem(F_TIME_ENTRY.B_INVOICE_HISTORY.I_ACTION).setValue("Record Payment");
                }
            }
        }
    }

    @Override
    public void preOpenScreen(EJBlock block, EJRecord record, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_TIME_ENTRY.B_CUSTOMERS.ID.equals(block.getName()))
        {
            block.getScreenItem(screenType, F_TIME_ENTRY.B_CUSTOMERS.I_VAT_ID).refreshItemRenderer();

            if (EJScreenType.INSERT.equals(screenType))
            {
                block.getScreenItem(screenType, F_TIME_ENTRY.B_CUSTOMERS.I_CONTACT_TYPES_ID).refreshItemRenderer();
                block.getScreenItem(screenType, F_TIME_ENTRY.B_CUSTOMERS.I_SALUTATIONS_ID).refreshItemRenderer();
            }
        }
        else if (F_TIME_ENTRY.B_INVOICE_HISTORY.ID.equals(block.getName()))
        {
            if (EJScreenType.UPDATE.equals(screenType))
            {
                Integer sent = (Integer) record.getValue(F_TIME_ENTRY.B_INVOICE_HISTORY.I_SENT);
                if (sent == 0)
                {
                    block.getScreenItem(EJScreenType.UPDATE, F_TIME_ENTRY.B_INVOICE_HISTORY.I_INV_DATE).setEditable(true);
                    block.getScreenItem(EJScreenType.UPDATE, F_TIME_ENTRY.B_INVOICE_HISTORY.I_DUE_DATE).setEditable(true);
                    block.getScreenItem(EJScreenType.UPDATE, F_TIME_ENTRY.B_INVOICE_HISTORY.I_NR).setEditable(true);
                    block.getScreenItem(EJScreenType.UPDATE, F_TIME_ENTRY.B_INVOICE_HISTORY.I_INVOICE_ADDRESS).setEditable(true);
                    block.getScreenItem(EJScreenType.UPDATE, F_TIME_ENTRY.B_INVOICE_HISTORY.I_SUMMARY).setEditable(true);
                    block.getScreenItem(EJScreenType.UPDATE, F_TIME_ENTRY.B_INVOICE_HISTORY.I_FOOTER).setEditable(true);
                }
                else
                {
                    block.getScreenItem(EJScreenType.UPDATE, F_TIME_ENTRY.B_INVOICE_HISTORY.I_INV_DATE).setDisplayProperty("DISPLAY_VALUE_AS_LABEL", "true");
                    
                    block.getScreenItem(EJScreenType.UPDATE, F_TIME_ENTRY.B_INVOICE_HISTORY.I_INV_DATE).setEditable(false);
                    block.getScreenItem(EJScreenType.UPDATE, F_TIME_ENTRY.B_INVOICE_HISTORY.I_DUE_DATE).setEditable(false);
                    block.getScreenItem(EJScreenType.UPDATE, F_TIME_ENTRY.B_INVOICE_HISTORY.I_NR).setEditable(false);
                    block.getScreenItem(EJScreenType.UPDATE, F_TIME_ENTRY.B_INVOICE_HISTORY.I_INVOICE_ADDRESS).setEditable(false);
                    block.getScreenItem(EJScreenType.UPDATE, F_TIME_ENTRY.B_INVOICE_HISTORY.I_SUMMARY).setEditable(false);
                    block.getScreenItem(EJScreenType.UPDATE, F_TIME_ENTRY.B_INVOICE_HISTORY.I_FOOTER).setEditable(false);
                }
            }
        }
    }

    @Override
    public void popupCanvasClosing(EJForm form, String popupCanvasName, EJPopupButton button) throws EJActionProcessorException
    {
        if (F_TIME_ENTRY.C_SEND_INVOICE_POPUP.equals(popupCanvasName) && button.equals(EJPopupButton.ONE))
        {
            Date sendDate = (Date)form.getBlock(F_TIME_ENTRY.B_SEND_INVOICE.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_SEND_INVOICE.I_SEND_DATE).getValue();
            String notes = (String)form.getBlock(F_TIME_ENTRY.B_SEND_INVOICE.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_SEND_INVOICE.I_NOTES).getValue();
            
            if (sendDate == null)
            {
                throw new EJActionProcessorException(new EJMessage(EJMessageLevel.ERROR, "Please enter the date when the invoice was sent"));
            }
            
            InvoiceHistory invPos = (InvoiceHistory)form.getBlock(F_TIME_ENTRY.B_INVOICE_HISTORY.ID).getFocusedRecord().getBlockServicePojo();
            invPos.setSentDate(sendDate);
            invPos.setNotes(notes);
            invPos.setSent(1);
            
            ArrayList<InvoiceHistory> pojos = new ArrayList<InvoiceHistory>();
            pojos.add(invPos);
            
            new InvoiceHistoryBlockService().executeUpdate(form, pojos);
            
            form.getBlock(F_TIME_ENTRY.B_INVOICE_HISTORY.ID).executeQuery();
        }
        else if (F_TIME_ENTRY.C_PAY_INVOICE_POPUP.equals(popupCanvasName) && button.equals(EJPopupButton.ONE))
        {
            Date payDate = (Date)form.getBlock(F_TIME_ENTRY.B_PAY_INVOICE.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_PAY_INVOICE.I_PAYMENT_DATE).getValue();
            String notes = (String)form.getBlock(F_TIME_ENTRY.B_PAY_INVOICE.ID).getScreenItem(EJScreenType.MAIN, F_TIME_ENTRY.B_PAY_INVOICE.I_NOTES).getValue();
            
            if (payDate == null)
            {
                throw new EJActionProcessorException(new EJMessage(EJMessageLevel.ERROR, "Please enter the date when the invoice was paid"));
            }
            
            InvoiceHistory invPos = (InvoiceHistory)form.getBlock(F_TIME_ENTRY.B_INVOICE_HISTORY.ID).getFocusedRecord().getBlockServicePojo();
            invPos.setPaymentDate(payDate);
            invPos.setNotes(notes);
            invPos.setPaid(1);
            
            ArrayList<InvoiceHistory> pojos = new ArrayList<InvoiceHistory>();
            pojos.add(invPos);
            
            new InvoiceHistoryBlockService().executeUpdate(form, pojos);
            
            form.getBlock(F_TIME_ENTRY.B_INVOICE_HISTORY.ID).executeQuery();
        }

    }
    
    

}
