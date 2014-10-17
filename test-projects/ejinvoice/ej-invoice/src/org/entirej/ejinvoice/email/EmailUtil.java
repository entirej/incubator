package org.entirej.ejinvoice.email;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import microsoft.exchange.webservices.data.EmailAddress;
import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.ExchangeCredentials;
import microsoft.exchange.webservices.data.ExchangeService;
import microsoft.exchange.webservices.data.ExchangeVersion;
import microsoft.exchange.webservices.data.MessageBody;
import microsoft.exchange.webservices.data.WebCredentials;

import org.entirej.ejinvoice.forms.login.PasswordHashGen;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.enumerations.EJMessageLevel;

public class EmailUtil
{
    private static String _template = "/org/entirej/ejinvoice/email/RequestPasswordTemplate.txt";
    
    private static String getEmailTemplate(EJForm form)
    {
        try
        {
            URL resource = EmailUtil.class.getResource("RequestPasswordTemplate.txt");
            byte[] encoded = Files.readAllBytes(Paths.get(resource.toURI()));
            return new String(encoded);
        }
        catch (IOException e)
        {
            throw new EJApplicationException(new EJMessage(EJMessageLevel.ERROR, "Unable to read the request password email template"));
        }
        catch (URISyntaxException e)
        {
            throw new EJApplicationException(new EJMessage(EJMessageLevel.ERROR, "Unable to read the request password email template"));
        }
    }

    public static void sendRequestPasswordMail(EJForm form, String toAddress)
    {
        String hashValue = PasswordHashGen.toHash(toAddress+System.currentTimeMillis());
        

        sendMailViaDefaultEmail(form, "BiziBo Password Request", getEmailTemplate(form), toAddress);
    }
    
    public static void sendMailViaDefaultEmail(EJForm form, String subject, String bodyText, String toAddress)
    {
        EmailInfo defaultEmailConfig = new EmailConfig().getDefaultEmailConfig(form);
        if (defaultEmailConfig != null)
        {
            try

            {

                ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);

                ExchangeCredentials credentials = new WebCredentials(defaultEmailConfig.getAddress(), defaultEmailConfig.getPassword());

                service.setCredentials(credentials);

                service.setUrl(new URI(defaultEmailConfig.getUrl()));

                EmailMessage msg = new EmailMessage(service);

                msg.getReplyTo().add(new EmailAddress("BiziBo", "bizibo@entirej.com"));

                msg.setSubject(subject);

                MessageBody body = MessageBody.getMessageBodyFromText(bodyText);

                msg.setBody(body);

                msg.getToRecipients().add(toAddress);

                msg.send();

            }

            catch (Exception e)
            {

                throw new EJApplicationException(e);

            }
        }
    }

}
