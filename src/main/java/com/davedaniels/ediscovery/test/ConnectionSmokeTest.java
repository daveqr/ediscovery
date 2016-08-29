package com.davedaniels.ediscovery.test;


import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxUser;

/**
 * Can be used as a quick Box.com connection smoke test. Requires the file ediscovery.properties to be located in the root directory.
 *
 */
public final class ConnectionSmokeTest {

   private ConnectionSmokeTest() {}

   public static void main( String[] args ) throws IOException {
      System.out.println( "Starting Connection Test" );

      try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext( "applicationContext.xml" )) {
         BoxAPIConnection connection = (BoxAPIConnection) context.getBean( "boxConnection" );
         BoxUser.Info userInfo = BoxUser.getCurrentUser( connection ).getInfo();
         
         // If the user name is printed, connection is good. If there's an exception, something is wrong.
         System.out.format( "Welcome, %s <%s>!\n\n", userInfo.getName(), userInfo.getLogin() );
      }
   }
}