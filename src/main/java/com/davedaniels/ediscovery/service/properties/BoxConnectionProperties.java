package com.davedaniels.ediscovery.service.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

// This should be a file in the project root provided by the app user. It is not included with the JAR.
@ConfigurationProperties( locations = "file:./ediscovery.properties", prefix = "connection" )
public class BoxConnectionProperties {

   private String enterpriseId;

   // This is the non-enterprise account we want a connection for.
   private String userId;

   private String clientId;

   private String clientSecret;

   private String publicKeyId;

   private String privateKeyFile;

   private String privateKeyPassword;

   private String developerToken;


   public void setUserId( String userId ) {
      this.userId = userId;
   }

   public String getUserId() {
      return userId;
   }

   public String getEnterpriseId() {
      return enterpriseId;
   }

   public void setEnterpriseId( String enterpriseId ) {
      this.enterpriseId = enterpriseId;
   }

   public String getClientId() {
      return clientId;
   }

   public void setClientId( String clientId ) {
      this.clientId = clientId;
   }

   public String getClientSecret() {
      return clientSecret;
   }

   public void setClientSecret( String clientSecret ) {
      this.clientSecret = clientSecret;
   }

   public String getPublicKeyId() {
      return publicKeyId;
   }

   public void setPublicKeyId( String publicKeyId ) {
      this.publicKeyId = publicKeyId;
   }

   public String getPrivateKeyFile() {
      return privateKeyFile;
   }

   public void setPrivateKeyFile( String privateKeyFile ) {
      this.privateKeyFile = privateKeyFile;
   }

   public String getPrivateKeyPassword() {
      return privateKeyPassword;
   }

   public void setPrivateKeyPassword( String privateKeyPassword ) {
      this.privateKeyPassword = privateKeyPassword;
   }

   public void setDeveloperToken( String developerToken ) {
      this.developerToken = developerToken;
   }

   public String getDeveloperToken() {
      return developerToken;
   }
}