package org.entirej.ejinvoice.reports.services;

import java.util.List;

import org.entirej.framework.report.EJReport;
import org.entirej.framework.report.service.EJReportBlockService;
import org.entirej.framework.report.service.EJReportQueryCriteria;
import org.entirej.framework.report.service.EJReportStatementExecutor;

public class ReportInvoicePositionsService implements EJReportBlockService<ReportInvoicePositions>
{
    private final EJReportStatementExecutor _statementExecutor;
    private String                          _selectStatement = new StringBuilder().append("SELECT * FROM INVOICE_POSITIONS ").toString();

    public ReportInvoicePositionsService()
    {
        _statementExecutor = new EJReportStatementExecutor();
    }

    @Override
    public List<ReportInvoicePositions> executeQuery(EJReport report, EJReportQueryCriteria queryCriteria)
    {
        return _statementExecutor.executeQuery(ReportInvoicePositions.class, report, _selectStatement, queryCriteria);
    }

}