package com.davedaniels.ediscovery.shell;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultHistoryFileNameProvider;
import org.springframework.stereotype.Component;

@Component
@Order( Ordered.HIGHEST_PRECEDENCE )
public class EdiscoveryHistoryFileNameProvider extends DefaultHistoryFileNameProvider {

   public String getHistoryFileName() {
      return "ediscovery-history.log";
   }

   @Override
   public String getProviderName() {
      return "Ediscovery history file name provider";
   }
}