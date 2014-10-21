package org.entirej.ejinvoice.reports.services;

import java.util.List;

import org.entirej.framework.report.EJReport;
import org.entirej.framework.report.service.EJReportBlockService;
import org.entirej.framework.report.service.EJReportQueryCriteria;
import org.entirej.framework.report.service.EJReportStatementExecutor;

public class ReportInvoiceService implements EJReportBlockService<ReportInvoice>
{
    private final EJReportStatementExecutor _statementExecutor;
    private String                          _selectStatement = new StringBuilder()
                                                                     .append("select  i.ID as 'INV_ID' ,i.NR,i.INV_DATE, i.DUE_DATE , i.AMOUNT_EXCL_VAT, i.AMOUNT_INCL_VAT, i.VAT_AMOUNT,i.INVOICE_ADDRESS,i.LOCALE_COUNTRY,i.LOCALE_LANGUAGE,    ci.NAME as 'COMPANY_NAME' , (CONCAT_WS(', ', ci.ADDRESS, ci.POST_CODE, ci.TOWN,ci.COUNTRY) ) as 'COMPANY_ADDRESS', ci.LOGO as 'COMPANY_LOGO', i.FOOTER as 'INVOICE_NOTES' ,i.SUMMARY as 'INVOICE_SUMMARY',ci.VAT_NR,i.VAT_RATE,   c.NAME as 'CUST_NAME'  from invoice i, company_information ci, customer c  where i.COMPANY_ID = ci.ID  and i.CUST_ID = c.ID ")
                                                                     .toString();

    public ReportInvoiceService()
    {
        _statementExecutor = new EJReportStatementExecutor();
    }

    @Override
    public List<ReportInvoice> executeQuery(EJReport report, EJReportQueryCriteria queryCriteria)
    {
        if(queryCriteria.containsRestriction("id"))
        {
            queryCriteria.getRestriction("id").setAlias("i.ID");
        }
        return _statementExecutor.executeQuery(ReportInvoice.class, report, _selectStatement, queryCriteria);
    }

}