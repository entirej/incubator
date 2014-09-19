package org.entirej.ejinvoice.forms.invoice;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Locale.Builder;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.forms.company.Company;
import org.entirej.ejinvoice.forms.constants.F_INVOICE_CREATION;
import org.entirej.ejinvoice.forms.projects.ProjectService;
import org.entirej.ejinvoice.forms.projects.reports.InvoiceReport;
import org.entirej.ejinvoice.forms.timeentry.Customer;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJManagedFrameworkConnection;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.data.controllers.EJQuestion;
import org.entirej.framework.core.enumerations.EJMessageLevel;
import org.entirej.framework.core.enumerations.EJPopupButton;
import org.entirej.framework.core.enumerations.EJQuestionButton;
import org.entirej.framework.core.enumerations.EJScreenType;
import org.entirej.framework.core.service.EJQueryCriteria;

public class InvoiceCreationActionProcessor extends DefaultFormActionProcessor
{
    private BigDecimal markedForInvoiceAmount = new BigDecimal(0);
    private boolean invoiceUpdated       = false;
    private Integer updatedInvoiceId     = null;
    private Locale  updatedInvoiceLocale = null;


    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        form.setFormParameter(F_INVOICE_CREATION.P_ITEMS_TO_INVOICE, 1);
    }

    @Override
    public void validateItem(EJForm form, EJRecord record, String itemName, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_INVOICE_CREATION.B_FILTER.I_CUSTOMER_ID.equals(itemName))
        {
            if (record.getValue(itemName) != null)
            {
                form.getBlock(F_INVOICE_CREATION.B_APPROVED_PROJECT_ITEMS.ID).executeQuery();
                form.getBlock(F_INVOICE_CREATION.B_MARKED_FOR_INVOICE_PROJECT_ITEMS.ID).executeQuery();
            }
            else
            {
                if (form.getBlock(F_INVOICE_CREATION.B_FILTER.ID).getFocusedRecord().getValue(F_INVOICE_CREATION.B_FILTER.I_CUSTOMER_ID) == null)
                {
                    form.getBlock(F_INVOICE_CREATION.B_APPROVED_PROJECT_ITEMS.ID).clear(true);
                    form.getBlock(F_INVOICE_CREATION.B_MARKED_FOR_INVOICE_PROJECT_ITEMS.ID).clear(true);
                }
            }
        }
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_INVOICE_CREATION.AC_DELETE_APPROVED_ITEM.equals(command))
        {
            EJQuestion question = new EJQuestion(form, "ASK_DELETE_APPROVED_POSITION");
            question.setMessage(new EJMessage("Are you sure you want to remove this approved position?"));
            question.setButtonText(EJQuestionButton.ONE, "Yes");
            question.setButtonText(EJQuestionButton.TWO, "Cancel");
            form.askQuestion(question);
        }
        else if (F_INVOICE_CREATION.AC_DELETE_MARKED_FOR_INVOICE__ITEM.equals(command))
        {
            EJQuestion question = new EJQuestion(form, "ASK_DELETE_MARKED_POSITION");
            question.setMessage(new EJMessage("Are you sure you want to remove this position from the invoice?"));
            question.setButtonText(EJQuestionButton.ONE, "Yes");
            question.setButtonText(EJQuestionButton.TWO, "Cancel");
            form.askQuestion(question);
        }
        else if (F_INVOICE_CREATION.AC_ADD_TO_INVOICE.equals(command))
        {
            ApprovedProjectItem projectItem = (ApprovedProjectItem) record.getBlockServicePojo();
            new InvoiceService().addPositionToInvoice(form, projectItem);
            form.getBlock(F_INVOICE_CREATION.B_APPROVED_PROJECT_ITEMS.ID).executeQuery();
            form.getBlock(F_INVOICE_CREATION.B_MARKED_FOR_INVOICE_PROJECT_ITEMS.ID).executeQuery();
        }
        else if (F_INVOICE_CREATION.AC_CREATE_FINAL_INVOICE.equals(command))
        {
            if (form.getBlock(F_INVOICE_CREATION.B_MARKED_FOR_INVOICE_PROJECT_ITEMS.ID).getBlockRecords().size() <= 0)
            {
                throw new EJApplicationException(new EJMessage(EJMessageLevel.ERROR, "Please choose 1 or more positions to be invoiced before continuing"));
            }

            form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID).clear(true);

            Integer companyId = (Integer) form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
            Integer customerId = (Integer) form.getBlock(F_INVOICE_CREATION.B_FILTER.ID).getFocusedRecord().getValue(F_INVOICE_CREATION.B_FILTER.I_CUSTOMER_ID);

            ProjectService projectService = new ProjectService();
            InvoiceService invoiceService = new InvoiceService();
            String lastInvoiceNumber = invoiceService.getLastInvoicNr(form, customerId);
            Customer customer = projectService.getCustomerInfo(form, customerId);
            Company company = projectService.getCompany(form);

            Calendar calendar = Calendar.getInstance(form.getCurrentLocale());
            Date invoiceDate = new Date(calendar.getTime().getTime());
            calendar.add(Calendar.DAY_OF_MONTH, customer.getPaymentDays());
            Date dueDate = new Date(calendar.getTime().getTime());

            StringBuilder address = new StringBuilder();
            address.append(customer.getAddress()).append("\n");
            address.append(customer.getPostCode()).append("  ").append(customer.getTown()).append("\n");
            address.append(customer.getCountry());

            BigDecimal amountExcl = (BigDecimal) form.getBlock(F_INVOICE_CREATION.B_INVOICE_TOTAL.ID).getFocusedRecord()
                    .getValue(F_INVOICE_CREATION.B_INVOICE_TOTAL.I_AMOUNT_EXCL);
            BigDecimal amountIncl = (BigDecimal) form.getBlock(F_INVOICE_CREATION.B_INVOICE_TOTAL.ID).getFocusedRecord()
                    .getValue(F_INVOICE_CREATION.B_INVOICE_TOTAL.I_AMOUNT_INCL);
            BigDecimal vatAmount = (BigDecimal) form.getBlock(F_INVOICE_CREATION.B_INVOICE_TOTAL.ID).getFocusedRecord()
                    .getValue(F_INVOICE_CREATION.B_INVOICE_TOTAL.I_VAT_AMOUNT);

            form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID).getFocusedRecord().setValue(F_INVOICE_CREATION.B_INVOICE_CREATION.I_COMPANY_ID, companyId);
            form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID).getFocusedRecord()
                    .setValue(F_INVOICE_CREATION.B_INVOICE_CREATION.I_CUST_ID, customer.getId());

            form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_AMOUNT_EXCL_VAT)
                    .setValue(amountExcl);
            form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_AMOUNT_INCL_VAT)
                    .setValue(amountIncl);
            form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_VAT_AMOUNT)
                    .setValue(vatAmount);
            form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_DUE_DATE)
                    .setValue(dueDate);
            form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_DUE_DATE_LABEL)
                    .setValue("Due Date");
            form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_DUE_DATE_INFO)
                    .setValue("(Customers standard: " + customer.getPaymentDays() + " days)");
            form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_INV_DATE)
                    .setValue(invoiceDate);
            form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_INVOICE_ADDRESS)
                    .setValue(address.toString());
            form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_NR).setValue(null);
            form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_NR_LABEL)
                    .setValue("Invoice No. (" + lastInvoiceNumber + ")");
            form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_VAT_RATE)
                    .setValue(customer.getVatRate());

            form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_INVOICE_SUMMARY)
                    .setValue(company.getInvoiceSummary());
            form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_INVOICE_FOOTER)
                    .setValue(company.getInvoiceFooter());

            form.showPopupCanvas(F_INVOICE_CREATION.C_INVOICE_CREATION_POPUP);
        }
    }

    @Override
    public void questionAnswered(EJQuestion question) throws EJActionProcessorException
    {
        if (question.getName().equals("ASK_DELETE_MARKED_POSITION") && question.getAnswer().equals(EJQuestionButton.ONE))
        {
            new InvoiceService().deleteMarkedForInvoicedPosition(question.getForm(),
                    (MarkedForInvoiceProjectItem) question.getForm().getBlock(F_INVOICE_CREATION.B_MARKED_FOR_INVOICE_PROJECT_ITEMS.ID).getFocusedRecord()
                            .getBlockServicePojo());
            question.getForm().getBlock(F_INVOICE_CREATION.B_APPROVED_PROJECT_ITEMS.ID).executeQuery();
            question.getForm().getBlock(F_INVOICE_CREATION.B_MARKED_FOR_INVOICE_PROJECT_ITEMS.ID).executeQuery();
        }
        else if (question.getName().equals("ASK_DELETE_APPROVED_POSITION") && question.getAnswer().equals(EJQuestionButton.ONE))
        {
            new InvoiceService().deleteApprovedPosition(question.getForm(),
                    (ApprovedProjectItem) question.getForm().getBlock(F_INVOICE_CREATION.B_APPROVED_PROJECT_ITEMS.ID).getFocusedRecord()
                            .getBlockServicePojo());
            question.getForm().getBlock(F_INVOICE_CREATION.B_APPROVED_PROJECT_ITEMS.ID).executeQuery();
            question.getForm().getBlock(F_INVOICE_CREATION.B_MARKED_FOR_INVOICE_PROJECT_ITEMS.ID).executeQuery();
        }
    }

    @Override
    public void postQuery(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_INVOICE_CREATION.B_MARKED_FOR_INVOICE_PROJECT_ITEMS.ID.equals(record.getBlockName()))
        {
            BigDecimal amount = (BigDecimal) record.getValue(F_INVOICE_CREATION.B_MARKED_FOR_INVOICE_PROJECT_ITEMS.I_AMOUNT);
            if (amount != null)
            {
                markedForInvoiceAmount = markedForInvoiceAmount.add(amount);
            }
        }
    }

    @Override
    public void postBlockQuery(EJForm form, EJBlock block) throws EJActionProcessorException
    {
        if (F_INVOICE_CREATION.B_MARKED_FOR_INVOICE_PROJECT_ITEMS.ID.equals(block.getName()))
        {
            Integer customerId = (Integer) form.getBlock(F_INVOICE_CREATION.B_FILTER.ID).getFocusedRecord().getValue(F_INVOICE_CREATION.B_FILTER.I_CUSTOMER_ID);

            Customer customer = new ProjectService().getCustomerInfo(form, customerId);

            NumberFormat ccyFormat = NumberFormat.getCurrencyInstance(customer.getLocale());

            Double taxValue = markedForInvoiceAmount.multiply(customer.getVatRate().divide(new BigDecimal(100))).doubleValue();
            Double totalValue = markedForInvoiceAmount.add(new BigDecimal(taxValue)).doubleValue();

            form.getBlock(F_INVOICE_CREATION.B_INVOICE_TOTAL.ID).getFocusedRecord()
                    .setValue(F_INVOICE_CREATION.B_INVOICE_TOTAL.I_AMOUNT_EXCL, markedForInvoiceAmount);
            form.getBlock(F_INVOICE_CREATION.B_INVOICE_TOTAL.ID).getFocusedRecord()
                    .setValue(F_INVOICE_CREATION.B_INVOICE_TOTAL.I_AMOUNT_INCL, new BigDecimal(totalValue));
            form.getBlock(F_INVOICE_CREATION.B_INVOICE_TOTAL.ID).getFocusedRecord()
                    .setValue(F_INVOICE_CREATION.B_INVOICE_TOTAL.I_VAT_AMOUNT, new BigDecimal(taxValue));

            form.getBlock(F_INVOICE_CREATION.B_INVOICE_TOTAL.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_TOTAL.I_SUBTOTAL)
                    .setValue(ccyFormat.format(markedForInvoiceAmount).toString());
            form.getBlock(F_INVOICE_CREATION.B_INVOICE_TOTAL.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_TOTAL.I_TAX_LABEL)
                    .setValue("VAT (" + customer.getVatRate() + "%)");
            form.getBlock(F_INVOICE_CREATION.B_INVOICE_TOTAL.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_TOTAL.I_TAX)
                    .setValue(ccyFormat.format(taxValue).toString());
            form.getBlock(F_INVOICE_CREATION.B_INVOICE_TOTAL.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_TOTAL.I_TOTAL)
                    .setValue(ccyFormat.format(totalValue).toString());
        }
    }

    @Override
    public void preQuery(EJForm form, EJQueryCriteria queryCriteria) throws EJActionProcessorException
    {
        if (F_INVOICE_CREATION.B_MARKED_FOR_INVOICE_PROJECT_ITEMS.ID.equals(queryCriteria.getBlockName()))
        {
            markedForInvoiceAmount = BigDecimal.ZERO;
        }
    }
//
//    @Override
//    public void postFormSave(EJForm form) throws EJActionProcessorException
//    {
//        if (invoiceUpdated)
//        {
//            invoiceUpdated = false;
//            if (updatedInvoiceId != null)
//            {
//                new InvoiceService().updateInvoicPDF(form, updatedInvoiceId, InvoiceReport.generateInvoicePDF(form.getConnection(), updatedInvoiceId, updatedInvoiceLocale));
//                updatedInvoiceId = null;
//                updatedInvoiceLocale = null;
//            }
//            form.getBlock(F_INVOICE_CREATION.B_INVOICE_HISTORY.ID).executeQuery();
//        }
//    }
    @Override
    public void popupCanvasClosing(EJForm form, String popupCanvasName, EJPopupButton button) throws EJActionProcessorException
    {
        if (F_INVOICE_CREATION.C_INVOICE_CREATION_POPUP.equals(popupCanvasName) && button.equals(EJPopupButton.ONE))
        {
            Invoice invoice = new Invoice();

            final int invId = PKSequenceService.getPKSequence(form.getConnection());

            Integer companyId = (Integer) form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID).getFocusedRecord()
                    .getValue(F_INVOICE_CREATION.B_INVOICE_CREATION.I_COMPANY_ID);
            Integer customerId = (Integer) form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID).getFocusedRecord()
                    .getValue(F_INVOICE_CREATION.B_INVOICE_CREATION.I_CUST_ID);

            BigDecimal amountExcl = (BigDecimal) form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID)
                    .getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_AMOUNT_EXCL_VAT).getValue();
            BigDecimal amountIncl = (BigDecimal) form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID)
                    .getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_AMOUNT_INCL_VAT).getValue();
            BigDecimal vatAmount = (BigDecimal) form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID)
                    .getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_VAT_AMOUNT).getValue();
            Date dueDate = (Date) form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID)
                    .getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_DUE_DATE).getValue();
            Date invDate = (Date) form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID)
                    .getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_INV_DATE).getValue();
            String address = (String) form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID)
                    .getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_INVOICE_ADDRESS).getValue();
            String nr = (String) form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID)
                    .getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_NR).getValue();
            BigDecimal vatRate = (BigDecimal) form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID)
                    .getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_VAT_RATE).getValue();
            String invoiceSummary = (String) form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID)
                    .getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_INVOICE_SUMMARY).getValue();
            String invoiceFooter = (String) form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID)
                    .getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_INVOICE_FOOTER).getValue();
            String invoiceNotes = (String) form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID)
                    .getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_INVOICE_NOTES).getValue();

            if (nr == null)
            {
                form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_NR)
                        .gainFocus();
                throw new EJApplicationException(new EJMessage(EJMessageLevel.ERROR, "Please enter an Invoice Number"));
            }
            if (invDate == null)
            {
                form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_INV_DATE)
                        .gainFocus();
                throw new EJApplicationException(new EJMessage(EJMessageLevel.ERROR, "Please enter an Invoice Date"));
            }
            if (dueDate == null)
            {
                form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_DUE_DATE)
                        .gainFocus();
                throw new EJApplicationException(new EJMessage(EJMessageLevel.ERROR, "Please enter a Due Date"));
            }
            if (address == null)
            {
                form.getBlock(F_INVOICE_CREATION.B_INVOICE_CREATION.ID)
                        .getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_INVOICE_CREATION.I_INVOICE_ADDRESS).gainFocus();
                throw new EJApplicationException(new EJMessage(EJMessageLevel.ERROR, "Please enter an Address"));
            }

            Customer cust = new ProjectService().getCustomerInfo(form, customerId);

            invoice.setId(invId);
            invoice.setCcyCode(cust.getCcyCode());
            invoice.setCompanyId(companyId);
            invoice.setCustId(customerId);
            invoice.setDueDate(dueDate);
            invoice.setInvDate(invDate);
            invoice.setAmountInclVat(amountIncl);
            invoice.setAmountExclVat(amountExcl);
            invoice.setVatAmount(vatAmount);
            invoice.setVatRate(vatRate);
            invoice.setInvoiceAddress(address);
            invoice.setNr(nr);
            invoice.setPaid(0);
            invoice.setSent(0);
            invoice.setLocaleCountry(cust.getLocaleCountry());
            invoice.setLocaleLanguage(cust.getLocaleLanguage());
            invoice.setLocale(new Builder().setLanguage(invoice.getLocaleLanguage()).setRegion(invoice.getLocaleCountry()).build());
            invoice.setSummary(invoiceSummary);
            invoice.setFooter(invoiceFooter);
            invoice.setNotes(invoiceNotes);

            new InvoiceService().createInvoice(form, invoice);
            EJManagedFrameworkConnection connection = form.getConnection();
            new InvoiceService().updateInvoicPDF(form, invId, InvoiceReport.generateInvoicePDF(connection, invId, invoice.getLocale()));
            new InvoiceService().updateInvoicDtlPDF(form, invId, InvoiceReport.generateInvoiceDtlPDF(connection, invId, invoice.getLocale()));

            InvoiceReport.openInvoicePDF(connection, invId, invoice.getLocale(), nr);

            form.getBlock(F_INVOICE_CREATION.B_MARKED_FOR_INVOICE_PROJECT_ITEMS.ID).executeQuery();
            if (form.getBlock(F_INVOICE_CREATION.B_APPROVED_PROJECT_ITEMS.ID).getBlockRecords().size() > 0)
            {
                form.getBlock(F_INVOICE_CREATION.B_FILTER.ID).getScreenItem(EJScreenType.MAIN, F_INVOICE_CREATION.B_FILTER.I_CUSTOMER_ID).refreshItemRenderer();
                form.getBlock(F_INVOICE_CREATION.B_FILTER.ID).clear(true);
            }
        }
    }
}
