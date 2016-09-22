package com.davedaniels.ediscovery;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.davedaniels.ediscovery.service.RequestService;

public class SmokeTest {

   private static final Logger logger = LogManager.getLogger( SmokeTest.class );

   public static void main( String[] args ) throws IOException {
      ApplicationContext context = new ClassPathXmlApplicationContext( "applicationContext.xml" );

      try {
         RequestService requestService = context.getBean( com.davedaniels.ediscovery.service.CLIRequestService.class );
         requestService.downloadFiles();
      }
      finally {
         ((ConfigurableApplicationContext) context).close();
      }

      logger.info( "Finished downloading." );
   }
}
