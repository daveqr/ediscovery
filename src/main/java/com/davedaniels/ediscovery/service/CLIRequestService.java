package com.davedaniels.ediscovery.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.davedaniels.ediscovery.dao.AccountsDao;
import com.davedaniels.ediscovery.service.cloud.CloudService;
import com.davedaniels.ediscovery.service.properties.FileSystemProperties;


/**
 * Command-line interactive service implementation of the {@link AbstractRequestService}.
 * 
 * @since 1.0.0
 */
@Service
@EnableConfigurationProperties( FileSystemProperties.class )
public class CLIRequestService implements RequestService {

   private static final Logger logger = LogManager.getLogger( CLIRequestService.class );

   // Mainly for testing
   @Autowired( required = false )
   private Scanner scanner = new Scanner( System.in );

   @Autowired( required = true )
   private CloudService cloudService;

   @Autowired( required = true )
   private AccountsDao accountsDao;

   @Autowired
   private FileSystemProperties fsProperties;

   @Override
   public final void downloadFiles() {
      displayStartMessage();

      confirmProjectRootExists();

      Collection<String> accounts = findAccounts();

      if ( accounts.isEmpty() ) {
         displayNoAccountsMessage();
         return;
      }

      File downloadDirectory = createDownloadDirectory();
      downloadAccountItems( downloadDirectory, accounts );
      displayEndMessage();
   }

   protected void displayNoAccountsMessage() {
      logger.info( "There are no accounts to download." );
   }

   protected Collection<String> findAccounts() {
      return accountsDao.findAccounts();
   }

   protected void confirmProjectRootExists() {
      if ( !fsProperties.getRootDir().exists() ) {
         throw new RuntimeException( "The directory " + fsProperties.getRootDir().getAbsolutePath() + " doesn't exist." );
      }
   }

   /**
    * Creates a download directory named with the timestamp format yyyyMMdd-HHmmss. ~/ediscovery
    * 
    * @return
    */
   protected File createDownloadDirectory() {

      String dirName = LocalDateTime.now().format( DateTimeFormatter.ofPattern( fsProperties.getDownloadDirFormat() ) );
      File downloadDirectory = new File( fsProperties.getDownloadsDir(), dirName );

      accountsDao.save( downloadDirectory );

      return downloadDirectory;
   }

   protected void downloadAccountItems( File downloadDir, Collection<String> accounts ) {
      logger.info( "Downloading accounts." );
      logger.debug( "There are {" + accounts.size() + "} accounts to download." );
      cloudService.downloadAccounts( downloadDir, accounts );
   }

   protected void displayEndMessage() {
      logger.info( "Downloading finished." );
   }

   protected void displayStartMessage() {
      logger.info( "Beginning downloads." );
   }
}