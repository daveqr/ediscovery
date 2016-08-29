package com.davedaniels.ediscovery.test;


import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxUser;
import com.davedaniels.ediscovery.service.RequestService;

/**
 * Test harness for quick live interaction. Not meant to run as an automated test.
 *
 */
public final class EdiscoveryTestHarness {

   private static final Logger logger = LogManager.getLogger( EdiscoveryTestHarness.class );

   private EdiscoveryTestHarness() {}

   public static void main( String[] args ) throws IOException {
      System.out.println( "Starting Connection Test" );

      try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext( "applicationContext.xml" )) {
         BoxAPIConnection connection = (BoxAPIConnection) context.getBean( "boxConnection" );
         BoxUser.Info userInfo = BoxUser.getCurrentUser( connection ).getInfo();

         // If the user name is printed, connection is good. If there's an exception, something is wrong.
         System.out.format( "Welcome, %s <%s>!\n\n", userInfo.getName(), userInfo.getLogin() );

         RequestService requestService = (RequestService) context.getBean( "requestService" );
         requestService.downloadFiles();
         
         System.out.println( "Finished downloading" );
      }
   }
}