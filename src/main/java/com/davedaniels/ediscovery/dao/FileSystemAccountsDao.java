package com.davedaniels.ediscovery.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Repository;

import com.box.sdk.BoxFile;
import com.box.sdk.BoxFileVersion;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxResource;
import com.davedaniels.ediscovery.service.properties.FileSystemProperties;
import com.google.common.base.Strings;

@Repository
@EnableConfigurationProperties( FileSystemProperties.class )
public class FileSystemAccountsDao implements AccountsDao {

   @Autowired
   private FileSystemProperties fsProperties;

   public Collection<String> findAccounts() {
      Set<String> accounts = new TreeSet<>();
      try (BufferedReader br = new BufferedReader( new FileReader( fsProperties.getAccountsFile() ) )) {
         String line;
         while ( (line = br.readLine()) != null ) {
            if ( !Strings.isNullOrEmpty( line.trim() ) ) {
               accounts.add( line );
            }
         }
      }
      catch ( RuntimeException | IOException e ) {
         throw new RuntimeException( "The accounts.txt file could not be loaded.", e );
      }

      return accounts;
   }

   @Override
   public void saveDownloadLog( BoxResource file ) {
      // TODO write to log file
   }

   @Override
   public File save( File dir ) {
      if ( dir.exists() ) throw new RuntimeException( "Download directory [" + dir.getName() + "] already exists." );
      if ( !dir.mkdirs() ) throw new RuntimeException( "Could not make dir: " + dir.getName() );

      return dir;
   }

   @Override
   public File save( File parent, BoxFolder item ) {
      return save( new File( parent, item.getInfo().getName() ) );
   }

   @Override
   public File save( File parent, BoxFile boxFile ) {
      for ( BoxFileVersion boxFileVersion : boxFile.getVersions() ) {
         try (FileOutputStream stream = new FileOutputStream( new File( parent, boxFileVersion.getName() ) )) {
            boxFileVersion.download( new FileOutputStream( new File( parent, boxFileVersion.getName() ) ) );
            saveDownloadLog( boxFileVersion );
         }
         catch ( IOException e ) {
            // TODO write error log
         }
      }

      return parent;
   }
}