package org.entirej.ejinvoice.forms.projects.reports;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

import org.entirej.applicationframework.rwt.file.EJRWTFileDownload;
import org.entirej.framework.core.EJManagedFrameworkConnection;
import org.entirej.reports.EJReportExportType;
import org.entirej.reports.EJReportParameter;
import org.entirej.reports.EJReports;

public class InvoiceReport
{

    
    public static void openInvoice( EJManagedFrameworkConnection connection,final int invId)
    {
        try
        {
            File tempFile = File.createTempFile("tmpejinv",String.valueOf(invId));
            
            EJReportParameter invID = new EJReportParameter("EJ_INV_ID", invId);
            EJReports.exportReport(InvoiceReport.class.getResourceAsStream("INV_A4.jasper"), tempFile.getAbsolutePath(), EJReportExportType.PDF,(Connection) connection.getConnectionObject(), invID);
            EJRWTFileDownload.download(tempFile.getAbsolutePath(), String.valueOf(invId)+".pdf");
            tempFile.deleteOnExit();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public static byte[] generateInvoice( EJManagedFrameworkConnection connection,final int invId)
    {
        try
        {
            File tempFile = File.createTempFile("tmpejinv",String.valueOf(invId));
            
            EJReportParameter invID = new EJReportParameter("EJ_INV_ID", invId);
            EJReports.exportReport(InvoiceReport.class.getResourceAsStream("INV_A4.jasper"), tempFile.getAbsolutePath(), EJReportExportType.PDF,(Connection) connection.getConnectionObject(), invID);
           
            Path path = Paths.get(tempFile.getAbsolutePath());
            byte[] data = Files.readAllBytes(path);
            
            if(!tempFile.delete())
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
}
