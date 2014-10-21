package org.entirej.ejinvoice.forms.projects.reports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.Locale;

import net.sf.jasperreports.engine.JRParameter;

import org.entirej.applicationframework.rwt.file.EJRWTFileDownload;
import org.entirej.framework.core.EJManagedFrameworkConnection;
import org.entirej.framework.report.EJReport;
import org.entirej.framework.report.EJReportFrameworkInitialiser;
import org.entirej.framework.report.EJReportFrameworkManager;
import org.entirej.framework.report.enumerations.EJReportExportType;
import org.entirej.report.jasper.EJJasperReportParameter;
import org.entirej.report.jasper.EJJasperReports;

public class InvoiceReport
{
    static final EJReportFrameworkManager reportManager;
    static
    {
        reportManager = EJReportFrameworkInitialiser.initialiseFramework("report.ejprop");
    }

    public static void openEJReportInvoicePDF(EJManagedFrameworkConnection connection, final int invId, Locale locale, String exportName)
    {
        try
        {
            File tempFile = File.createTempFile("tmpejinv", String.valueOf(invId));

            reportManager.changeLocale(locale);
            EJReport INV_A4 = reportManager.createReport("INV_A4");
            INV_A4.getReportParameter("INV_ID").setValue(invId);

            // only add Image value if need to show in report
            URL img = InvoiceReport.class.getClassLoader().getResource("icons/Bizibo.png");
            if (img != null)
            {
                try
                {
                    INV_A4.getReportParameter("EJ_BIZIBO_IMG").setValue(Files.readAllBytes(Paths.get(img.toURI())));
                }
                catch (URISyntaxException e)
                {
                    e.printStackTrace();
                }
            }
            EJJasperReports.exportReport(reportManager, INV_A4, tempFile.getAbsolutePath(), EJReportExportType.PDF);
            EJRWTFileDownload.download(tempFile.getAbsolutePath(), exportName + ".pdf");
            tempFile.deleteOnExit();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void openInvoicePDF(EJManagedFrameworkConnection connection, final int invId, Locale locale, String exportName)
    {
        try
        {
            File tempFile = File.createTempFile("tmpejinv", String.valueOf(invId));
            
            EJJasperReportParameter invID = new EJJasperReportParameter("EJ_INV_ID", invId);
            EJJasperReportParameter invBiziboImage = new EJJasperReportParameter("EJ_BIZIBO_IMG", null);
            
            // only add Image value if need to show in report
            URL img = InvoiceReport.class.getClassLoader().getResource("icons/Bizibo.png");
            if (img != null)
            {
                try
                {
                    invBiziboImage.setValue(Files.readAllBytes(Paths.get(img.toURI())));
                }
                catch (URISyntaxException e)
                {
                    e.printStackTrace();
                }
            }
            
            EJJasperReportParameter localeParameter = new EJJasperReportParameter(JRParameter.REPORT_LOCALE, locale);
            EJJasperReports.exportReport(InvoiceReport.class.getResourceAsStream("INV_A4.jasper"), tempFile.getAbsolutePath(), EJReportExportType.PDF, (Connection) connection.getConnectionObject(), invID, invBiziboImage, localeParameter);
            EJRWTFileDownload.download(tempFile.getAbsolutePath(), exportName + ".pdf");
            tempFile.deleteOnExit();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static byte[] generateInvoicePDF(EJManagedFrameworkConnection connection, final int invId, Locale locale)
    {
        try
        {
            File tempFile = File.createTempFile("tmpejinv", String.valueOf(invId));

            EJJasperReportParameter invID = new EJJasperReportParameter("EJ_INV_ID", invId);

            EJJasperReportParameter invBiziboImage = new EJJasperReportParameter("EJ_BIZIBO_IMG", null);

            // only add Image value if need to show in report
            URL img = InvoiceReport.class.getClassLoader().getResource("icons/Bizibo.png");
            if (img != null)
            {
                try
                {
                    invBiziboImage.setValue(Files.readAllBytes(Paths.get(img.toURI())));
                }
                catch (URISyntaxException e)
                {
                    e.printStackTrace();
                }
            }
            EJJasperReportParameter localeParameter = new EJJasperReportParameter(JRParameter.REPORT_LOCALE, locale);
            EJJasperReports.exportReport(InvoiceReport.class.getResourceAsStream("INV_A4.jasper"), tempFile.getAbsolutePath(), EJReportExportType.PDF, (Connection) connection.getConnectionObject(), invID, invBiziboImage, localeParameter);

            Path path = Paths.get(tempFile.getAbsolutePath());
            byte[] data = Files.readAllBytes(path);

            if (!tempFile.delete())
            {
                tempFile.deleteOnExit();
            }
            return data;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] generateEJReportInvoicePDF(EJManagedFrameworkConnection connection, final int invId, Locale locale)
    {
        try
        {
            File tempFile = File.createTempFile("tmpejinv", String.valueOf(invId));

            reportManager.changeLocale(locale);
            EJReport INV_A4 = reportManager.createReport("INV_A4");
            INV_A4.getReportParameter("INV_ID").setValue(invId);

            // only add Image value if need to show in report
            URL img = InvoiceReport.class.getClassLoader().getResource("icons/Bizibo.png");
            if (img != null)
            {
                try
                {
                    INV_A4.getReportParameter("EJ_BIZIBO_IMG").setValue(Files.readAllBytes(Paths.get(img.toURI())));
                }
                catch (URISyntaxException e)
                {
                    e.printStackTrace();
                }
            }

            EJJasperReports.exportReport(reportManager, INV_A4, tempFile.getAbsolutePath(), EJReportExportType.PDF);

            Path path = Paths.get(tempFile.getAbsolutePath());
            byte[] data = Files.readAllBytes(path);

            if (!tempFile.delete())
            {
                tempFile.deleteOnExit();
            }
            return data;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void openInvoiceDtlPDF(EJManagedFrameworkConnection connection, final int invId, Locale locale, String exportName)
    {
        try
        {
            File tempFile = File.createTempFile("tmpejinvdtl", String.valueOf(invId));

            EJJasperReportParameter invID = new EJJasperReportParameter("EJ_INV_ID", invId);
            EJJasperReportParameter localeParameter = new EJJasperReportParameter(JRParameter.REPORT_LOCALE, locale);
            EJJasperReports.exportReport(InvoiceReport.class.getResourceAsStream("INV_DTL_A4.jasper"), tempFile.getAbsolutePath(), EJReportExportType.PDF, (Connection) connection.getConnectionObject(), invID, localeParameter);
            EJRWTFileDownload.download(tempFile.getAbsolutePath(), exportName + ".pdf");
            tempFile.deleteOnExit();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static byte[] generateInvoiceDtlPDF(EJManagedFrameworkConnection connection, final int invId, Locale locale)
    {
        try
        {
            File tempFile = File.createTempFile("tmpejinvdtl", String.valueOf(invId));

            EJJasperReportParameter invID = new EJJasperReportParameter("EJ_INV_ID", invId);
            EJJasperReportParameter localeParameter = new EJJasperReportParameter(JRParameter.REPORT_LOCALE, locale);
            EJJasperReports.exportReport(InvoiceReport.class.getResourceAsStream("INV_DTL_A4.jasper"), tempFile.getAbsolutePath(), EJReportExportType.PDF, (Connection) connection.getConnectionObject(), invID, localeParameter);

            Path path = Paths.get(tempFile.getAbsolutePath());
            byte[] data = Files.readAllBytes(path);

            if (!tempFile.delete())
            {
                tempFile.deleteOnExit();
            }
            return data;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void downloadReport(byte[] data, String outputName)

    {

        try
        {
            File temp = File.createTempFile("tmpejinvpdf", "pdf");
            if (data != null)
                try (FileOutputStream fos = new FileOutputStream(temp))
                {
                    fos.write(data);
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            EJRWTFileDownload.download(temp.getAbsolutePath(), outputName);
            temp.deleteOnExit();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

    }
}
