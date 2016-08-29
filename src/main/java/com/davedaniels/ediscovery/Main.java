package com.davedaniels.ediscovery;

import java.io.IOException;

import org.springframework.shell.Bootstrap;
import org.springframework.stereotype.Component;

@Component
public class Main {

   public static void main( String[] args ) throws IOException {
      // This is used to control Spring logging.
      System.setProperty( "java.util.logging.config.file", "logging-spring.properties" );

      Bootstrap.main( args );
   }
}