package com.davedaniels.ediscovery.service.cloud;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxFile;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxItem;
import com.box.sdk.BoxItem.Info;
import com.davedaniels.ediscovery.dao.AccountsDao;
import com.google.common.collect.Lists;

/**
 * The Box.com download service.
 * 
 * @since 1.0.0
 */
@Service
public class BoxService implements CloudService {

   @Autowired( required = true )
   private AccountsDao accountsDao;

   @Autowired( required = true )
   private BoxAPIConnection boxConnection;

   @Autowired( required = true )
   private ApplicationContext applicationContext;

//   @Autowired
//   private BoxConnectionProperties connectionProps;

   private static final Logger logger = LogManager.getLogger( BoxService.class );

   @Override
   public void downloadAccounts( File downloadDir, final Collection<String> accounts ) {
      for ( String account : accounts ) {
         File accountDir = createDir( downloadDir, account );

         BoxAPIConnection boxConnection = (BoxAPIConnection) applicationContext.getBean( "boxConnection", account );
         BoxFolder boxRootFolder = BoxFolder.getRootFolder( boxConnection );
         
         downloadAccountItems( account, accountDir, boxConnection, boxRootFolder );
      }
   }


   protected void downloadAccountItems( String account, File parentDir, BoxAPIConnection boxConnection,
         BoxFolder boxParentFolder ) {
      List<BoxItem.Info> children = Lists.newArrayList( boxParentFolder.getChildren( BoxFolder.ALL_FIELDS ) );
      for ( Info child : children ) {
         boolean isOwner = child.getOwnedBy().getLogin().toLowerCase().equals( account.toLowerCase() );
         if ( isOwner ) {
            boolean isAFolder = child instanceof BoxFolder.Info;
            if ( isAFolder ) {
               logger.debug( "Creating folder: " + child.getName() );
               File folderDir = createDir( parentDir, child.getName() );
               downloadAccountItems( account, folderDir, boxConnection, new BoxFolder( boxConnection, child.getID() ) );
            } else {
               logger.debug( "Saving file: " + child.getName() );
               save( parentDir, (BoxFile.Info) child );
               BoxFile thisFile = new BoxFile( boxConnection, child.getID() );
               thisFile.getInfo( BoxFile.ALL_FIELDS ).getName();
               thisFile.getInfo( BoxFile.ALL_FIELDS ).getVersionNumber();
            }
         }
      }
   }

   
   protected File createDir( File parentDir, String dirName ) {
      File accountDir = new File( parentDir, dirName );
      accountsDao.save( accountDir );
      
      return accountDir;
   }

   
   protected void save( File parent, BoxFile.Info info ) {
      accountsDao.save( parent, new BoxFile( boxConnection, info.getID() ) );
   }
}