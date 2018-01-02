package com.weavernorth.mail.util;

import java.util.List;

public class MailSenderInfo {

   private String mailServerHost;
   private String mailServerPort = "25";
   private String fromAddress;
   private String toAddress;
   private String ccAddress;
   private String bccAddress;
   private String userName;
   private String password;
   private boolean validate = false;
   private String subject;
   private String content;
   private List listFiles;
   private boolean image = false;
   private boolean tls = false;
   private boolean ssl = false;


   public boolean isImage() {
      return this.image;
   }

   public void setImage(boolean image) {
      this.image = image;
   }

   public boolean isTls() {
      return this.tls;
   }

   public void setTls(boolean tls) {
      this.tls = tls;
   }

   public boolean isSsl() {
      return this.ssl;
   }

   public void setSsl(boolean ssl) {
      this.ssl = ssl;
   }

   public List getListFiles() {
      return this.listFiles;
   }

   public void setListFiles(List listFiles) {
      this.listFiles = listFiles;
   }

   public String getMailServerHost() {
      return this.mailServerHost;
   }

   public void setMailServerHost(String mailServerHost) {
      this.mailServerHost = mailServerHost;
   }

   public String getMailServerPort() {
      return this.mailServerPort;
   }

   public void setMailServerPort(String mailServerPort) {
      this.mailServerPort = mailServerPort;
   }

   public boolean isValidate() {
      return this.validate;
   }

   public void setValidate(boolean validate) {
      this.validate = validate;
   }

   public String getFromAddress() {
      return this.fromAddress;
   }

   public void setFromAddress(String fromAddress) {
      this.fromAddress = fromAddress;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getToAddress() {
      return this.toAddress;
   }

   public void setToAddress(String toAddress) {
      this.toAddress = toAddress;
   }

   public String getUserName() {
      return this.userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public String getSubject() {
      return this.subject;
   }

   public void setSubject(String subject) {
      this.subject = subject;
   }

   public String getContent() {
      return this.content;
   }

   public void setContent(String textContent) {
      this.content = textContent;
   }

   public String getCcAddress() {
      return this.ccAddress;
   }

   public void setCcAddress(String ccAddress) {
      this.ccAddress = ccAddress;
   }

   public String getBccAddress() {
      return this.bccAddress;
   }

   public void setBccAddress(String bccAddress) {
      this.bccAddress = bccAddress;
   }
}