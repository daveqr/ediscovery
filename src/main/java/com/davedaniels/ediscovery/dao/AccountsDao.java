package com.davedaniels.ediscovery.dao;

import java.io.File;
import java.util.Collection;

import com.box.sdk.BoxFile;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxResource;


public interface AccountsDao {

   Collection<String> findAccounts();

   void saveDownloadLog( BoxResource file );

   File save( File dir );

   File save( File parent, BoxFile item );

   File save( File parent, BoxFolder item );
}