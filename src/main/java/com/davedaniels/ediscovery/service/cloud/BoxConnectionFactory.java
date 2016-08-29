package com.davedaniels.ediscovery.service.cloud;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxAPIException;
import com.box.sdk.BoxDeveloperEditionAPIConnection;
import com.box.sdk.EncryptionAlgorithm;
import com.box.sdk.IAccessTokenCache;
import com.box.sdk.InMemoryLRUAccessTokenCache;
import com.box.sdk.JWTEncryptionPreferences;
import com.davedaniels.ediscovery.service.properties.BoxConnectionProperties;
import com.google.common.base.Strings;

@EnableConfigurationProperties( BoxConnectionProperties.class )
public class BoxConnectionFactory implements FactoryBean<BoxAPIConnection> {

   private static final int MAX_CACHE_ENTRIES = 100;

   private String userId;

   @Autowired
   private BoxConnectionProperties connectionProps;


   public BoxConnectionFactory( String userId ) {
      this.userId = userId;
   }

   @Override
   public BoxAPIConnection getObject() throws IOException {

      BoxAPIConnection api = null;

      if ( Strings.isNullOrEmpty( connectionProps.getDeveloperToken() ) ) {

         String privateKey = new String( Files.readAllBytes( Paths.get( connectionProps.getPrivateKeyFile() ) ) );

         JWTEncryptionPreferences encryptionPref = new JWTEncryptionPreferences();
         encryptionPref.setPublicKeyID( connectionProps.getPublicKeyId() );
         encryptionPref.setPrivateKey( privateKey );
         encryptionPref.setPrivateKeyPassword( connectionProps.getPrivateKeyPassword() );
         encryptionPref.setEncryptionAlgorithm( EncryptionAlgorithm.RSA_SHA_256 );

         IAccessTokenCache accessTokenCache = new InMemoryLRUAccessTokenCache( MAX_CACHE_ENTRIES );

         try {
            if ( null != userId ) {
               api = BoxDeveloperEditionAPIConnection.getAppUserConnection( userId, connectionProps.getClientId(),
                     connectionProps.getClientSecret(), encryptionPref, accessTokenCache );
               userId = null;
            } else {
               api = BoxDeveloperEditionAPIConnection.getAppEnterpriseConnection( connectionProps.getEnterpriseId(),
                     connectionProps.getClientId(), connectionProps.getClientSecret(), encryptionPref, accessTokenCache );
            }
         }
         catch ( BoxAPIException e ) {
            throw new RuntimeException( e.getResponse() );
         }
      } else {
         api = new BoxAPIConnection( connectionProps.getDeveloperToken() );
      }

      return api;
   }

   @Override
   public Class<BoxAPIConnection> getObjectType() {
      return BoxAPIConnection.class;
   }

   @Override
   public boolean isSingleton() {
      return true;
   }
}