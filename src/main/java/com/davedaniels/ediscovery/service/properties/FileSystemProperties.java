package com.davedaniels.ediscovery.service.properties;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties( locations = "classpath:application.properties", prefix = "fs" )
public class FileSystemProperties {

   // The directory the app is running in.
   @Value( "#{ systemProperties['user.dir'] }" )
   private String rootDirName;

   private String downloadsDirName;

   private String accountsFileName;

   private String downloadDirFormat;

   private String downloadLogsDirName;


   public File getRootDir() {
      return new File( rootDirName );
   }

   public File getDownloadsDir() {
      return new File( getRootDir(), downloadsDirName );
   }

   public File getDownloadLogsDir() {
      return new File( getRootDir(), downloadLogsDirName );
   }

   public File getAccountsFile() {
      return new File( getRootDir(), accountsFileName );
   }

   public String getDownloadDirFormat() {
      return downloadDirFormat;
   }

   public void setDownloadsDirName( String downloadsDirName ) {
      this.downloadsDirName = downloadsDirName;
   }

   public void setDownloadLogsDirName( String downloadLogsDirName ) {
      this.downloadLogsDirName = downloadLogsDirName;
   }

   public void setAccountsFileName( String accountsFileName ) {
      this.accountsFileName = accountsFileName;
   }

   public void setDownloadDirFormat( String downloadDirFormat ) {
      this.downloadDirFormat = downloadDirFormat;
   }
}