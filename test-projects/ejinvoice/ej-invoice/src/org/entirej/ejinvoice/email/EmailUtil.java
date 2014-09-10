package org.entirej.ejinvoice.email;

import java.net.URI;

import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;

import microsoft.exchange.webservices.data.EmailAddress;
import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.ExchangeCredentials;
import microsoft.exchange.webservices.data.ExchangeService;
import microsoft.exchange.webservices.data.ExchangeVersion;
import microsoft.exchange.webservices.data.MessageBody;
import microsoft.exchange.webservices.data.WebCredentials;

public class EmailUtil
{

    public static void sendMailViaDefaultEmail(EJForm form, String subject, String bodyText, String toAddress)
    {
        EmailInfo defaultEmailConfig = EmailConfig.getDefaultEmailConfig(form);
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
