/* Copyright (c) 2015 Vanderbilt University */
package com.davedaniels.ediscovery.service.cloud;

import java.io.File;
import java.util.ArrayList;
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
import com.davedaniels.ediscovery.service.properties.BoxConnectionProperties;
import com.box.sdk.BoxUser;
import com.google.common.base.Strings;
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

   // TODO replace this w dev/prod props
   @Autowired
   private BoxConnectionProperties connectionProps;

   private static final Logger logger = LogManager.getLogger( BoxService.class );

   @Override
   public void downloadAccountItems( File downloadDir, final Collection<String> accounts ) {
      for ( String account : accounts ) {
         // create user folder
         File accountDir = new File( downloadDir, account );
         accountsDao.save( accountDir );

         BoxAPIConnection acctConnection = (BoxAPIConnection) applicationContext.getBean( "boxConnection", account );
         BoxFolder rootFolder = BoxFolder.getRootFolder( acctConnection );
         List<BoxItem.Info> children = Lists.newArrayList( rootFolder.getChildren( BoxFolder.ALL_FIELDS ) );
         for ( Info child : children ) {
            boolean isOwner = child.getOwnedBy().getLogin().toLowerCase().equals( account.toLowerCase() );
            if ( isOwner ) {

               boolean isAFolder = child instanceof BoxFolder.Info;
               if ( isAFolder ) {
                  // accountsDao.save( accountDir, new BoxFile( boxConnection, info.getID() ) );
               } else {
                  save( accountDir, (BoxFile.Info) child );
                  BoxFile thisFile = new BoxFile( acctConnection, child.getID() );
                  thisFile.getInfo( BoxFile.ALL_FIELDS ).getName();
                  thisFile.getInfo( BoxFile.ALL_FIELDS ).getVersionNumber();
               }

            }
         }


         // save( downloadDir, rootFolder );

         // }
      }
   }

   private Iterable<BoxUser.Info> findUsers( String account ) {
      List<BoxUser.Info> users = new ArrayList<>();

      // TODO replace this with dev/prod env so we don't have to do if/else
      if ( Strings.isNullOrEmpty( connectionProps.getDeveloperToken() ) ) {
         users = (List<BoxUser.Info>) BoxUser.getAllEnterpriseUsers( boxConnection, account );
      } else {
         BoxUser user = BoxUser.getCurrentUser( boxConnection );
         users.add( user.getInfo( BoxUser.ALL_FIELDS ) );
      }

      return users;
   }

   /**
    * Recursively saves the specified {@link BoxFolder} and it's contents, including all subfolders and file versions.
    * 
    * @param parent the base directory of the <code>folder</code>; assumed to exist
    * @param folder the Box folder to be saved
    */
   protected void save( File parent, BoxFolder folder ) {
      File thisDir = accountsDao.save( parent, folder );

      for ( BoxItem.Info itemInfo : folder ) {
         if ( itemInfo instanceof BoxFolder.Info ) {
            // save( thisDir, new BoxFolder( boxConnection, itemInfo.getID() ) );
         } else {
            // save( thisDir, (BoxFile.Info) itemInfo );
         }
      }
   }

   protected void save( File parent, BoxFile.Info info ) {
      accountsDao.save( parent, new BoxFile( boxConnection, info.getID() ) );
   }
}