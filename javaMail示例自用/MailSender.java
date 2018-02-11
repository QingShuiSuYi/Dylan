package com.weavernorth.mail.util;

import com.weavernorth.mail.util.MailSenderInfo;
import com.weavernorth.mail.util.MyAuthenticator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailSender {

   public boolean sendTextMail(MailSenderInfo mailInfo) {
      MyAuthenticator authenticator = null;
      boolean blnValidate = mailInfo.isValidate();
      Properties prop = new Properties();
      prop.put("mail.smtp.host", mailInfo.getMailServerHost());
      prop.put("mail.smtp.port", mailInfo.getMailServerPort());
      prop.put("mail.smtp.auth", blnValidate?"true":"false");
      if(blnValidate) {
         authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
      }

      Session sendMailSession = Session.getInstance(prop, authenticator);

      try {
         MimeMessage ex = new MimeMessage(sendMailSession);
         InternetAddress from = new InternetAddress(mailInfo.getFromAddress());
         ex.setFrom(from);
         InternetAddress[] mailContent;
         if(mailInfo.getToAddress() != null && !"".equals(mailInfo.getToAddress())) {
            mailContent = InternetAddress.parse(mailInfo.getToAddress());
            ex.setRecipients(RecipientType.TO, mailContent);
         }

         if(mailInfo.getCcAddress() != null && !"".equals(mailInfo.getCcAddress())) {
            mailContent = InternetAddress.parse(mailInfo.getCcAddress());
            ex.setRecipients(RecipientType.CC, mailContent);
         }

         if(mailInfo.getBccAddress() != null && !"".equals(mailInfo.getBccAddress())) {
            mailContent = InternetAddress.parse(mailInfo.getBccAddress());
            ex.setRecipients(RecipientType.BCC, mailContent);
         }

         ex.setSubject(mailInfo.getSubject());
         ex.setSentDate(new Date());
         String mailContent1 = mailInfo.getContent();
         ex.setText(mailContent1);
         Transport.send(ex);
         return true;
      } catch (Exception var9) {
         var9.printStackTrace();
         return false;
      }
   }

   public static boolean sendHtmlMail(MailSenderInfo mailInfo) {
      MyAuthenticator authenticator = null;
      boolean blnValidate = mailInfo.isValidate();
      Properties prop = new Properties();
      prop.put("mail.smtp.host", mailInfo.getMailServerHost());
      prop.put("mail.smtp.port", mailInfo.getMailServerPort());
      prop.put("mail.smtp.auth", blnValidate?"true":"false");
      if(blnValidate) {
         authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
      }

      Session sendMailSession = Session.getInstance(prop, authenticator);

      try {
         MimeMessage ex = new MimeMessage(sendMailSession);
         InternetAddress from = new InternetAddress(mailInfo.getFromAddress());
         ex.setFrom(from);
         InternetAddress[] mainPart;
         if(mailInfo.getToAddress() != null && !"".equals(mailInfo.getToAddress())) {
            mainPart = InternetAddress.parse(mailInfo.getToAddress());
            ex.setRecipients(RecipientType.TO, mainPart);
         }

         if(mailInfo.getCcAddress() != null && !"".equals(mailInfo.getCcAddress())) {
            mainPart = InternetAddress.parse(mailInfo.getCcAddress());
            ex.setRecipients(RecipientType.CC, mainPart);
         }

         if(mailInfo.getBccAddress() != null && !"".equals(mailInfo.getBccAddress())) {
            mainPart = InternetAddress.parse(mailInfo.getBccAddress());
            ex.setRecipients(RecipientType.BCC, mainPart);
         }

         ex.setSubject(mailInfo.getSubject());
         MimeMultipart var18 = new MimeMultipart();
         MimeBodyPart html = new MimeBodyPart();
         html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
         var18.addBodyPart(html);
         int i = 0;
         List listFiles = mailInfo.getListFiles();
         if(listFiles != null && listFiles.size() > 0) {
            MimeBodyPart mbp = null;
            FileDataSource fds = null;
            Map map = null;
            String strFileName = "";
            boolean blnIsImage = mailInfo.isImage();
            Iterator it = listFiles.iterator();

            while(it.hasNext()) {
               map = (Map)it.next();
               strFileName = (String)map.get("filepathname");
               fds = new FileDataSource(strFileName);
               mbp = new MimeBodyPart();
               mbp.setDataHandler(new DataHandler(fds));
               if(blnIsImage) {
                  mbp.setHeader("Content-ID", "<image" + i + ">");
                  ++i;
               }

               mbp.setFileName(MimeUtility.encodeWord((String)map.get("filerealname")));
               var18.addBodyPart(mbp);
            }
         }

         ex.setSentDate(new Date());
         ex.setContent(var18);
         Transport.send(ex);
         return true;
      } catch (Exception var17) {
         var17.printStackTrace();
         return false;
      }
   }

   public Properties getProperties(MailSenderInfo mailInfo) {
      Properties prop = new Properties();
      prop.put("mail.smtp.host", mailInfo.getMailServerHost());
      prop.put("mail.smtp.port", mailInfo.getMailServerPort());
      prop.put("mail.smtp.auth", mailInfo.isValidate()?"true":"false");
      boolean blnSsl = mailInfo.isSsl();
      if(blnSsl) {
         prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
         prop.put("mail.smtp.socketFactory.fallback", "false");
         prop.put("mail.smtp.socketFactory.port", mailInfo.getMailServerPort());
      }

      boolean blnTls = mailInfo.isTls();
      if(blnTls) {
         prop.put("mail.smtp.starttls.enable", "true");
         prop.put("mail.smtp.ssl.checkserveridentity", "false");
         prop.put("mail.smtp.ssl.trust", mailInfo.getMailServerHost());
      }

      return prop;
   }
}