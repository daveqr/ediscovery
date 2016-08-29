package com.davedaniels.ediscovery.shell;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

import com.davedaniels.ediscovery.service.RequestService;

@Component
public class EdiscoveryCommands implements CommandMarker {

   private static final Logger logger = LogManager.getLogger( EdiscoveryCommands.class );

   @Autowired( required = true )
   private RequestService requestService;

   @CliAvailabilityIndicator( { "download" } )
   public boolean isDownloadAvailable() {
      return true;
   }

   @CliCommand( value = "download", help = "Download files" )
   public String download() {
      String returnMessage = null;

      try {
         logger.debug( "Just before calling download service." );
         requestService.downloadFiles();
         logger.debug( "Just after calling download service." );
      }
      catch ( RuntimeException e ) {
         returnMessage = "There was an error: " + e.getMessage();
      }

      return returnMessage;
   }

   // @CliCommand( value = "hw complex", help = "Print a complex hello world message (run 'hw simple' once first)" )
   // public String hello(
   // @CliOption( key = { "message" }, mandatory = true, help = "The hello world message" ) final String message,
   // @CliOption( key = { "name1" }, mandatory = true, help = "Say hello to the first name" ) final String name1,
   // @CliOption( key = { "name2" }, mandatory = true, help = "Say hello to a second name" ) final String name2,
   // @CliOption( key = {
   // "time" }, mandatory = false, specifiedDefaultValue = "now", help = "When you are saying hello" ) final String time,
   // @CliOption( key = { "location" }, mandatory = false, help = "Where you are saying hello" ) final String location ) {
   // return "Hello " + name1 + " and " + name2 + ". Your special message is " + message + ". time=[" + time + "] location=["
   // + location + "]";
   // }

   // @CliCommand( value = "hw enum", help = "Print a simple hello world message from an enumerated value (run 'hw simple' once
   // first)" )
   // public String eenum(
   // @CliOption( key = { "message" }, mandatory = true, help = "The hello world message" ) final MessageType message ) {
   // return "Hello. Your special enumerated message is " + message;
   // }

   // enum MessageType {
   // Type1( "type1" ), Type2( "type2" ), Type3( "type3" );
   //
   // private String type;
   //
   // private MessageType( String type ) {
   // this.type = type;
   // }
   //
   // public String getType() {
   // return type;
   // }
   // }
}