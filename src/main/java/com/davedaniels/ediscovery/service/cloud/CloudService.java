/* Copyright (c) 2015 Vanderbilt University */
package com.davedaniels.ediscovery.service.cloud;

import java.io.File;
import java.util.Collection;


/**
 * The CloudService.
 * 
 * @since 1.0.0
 */
public interface CloudService {

   void downloadAccountItems( File downloadDir, final Collection<String> accounts );
}