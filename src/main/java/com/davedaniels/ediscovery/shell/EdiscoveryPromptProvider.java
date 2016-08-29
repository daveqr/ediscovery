package com.davedaniels.ediscovery.shell;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultPromptProvider;
import org.springframework.stereotype.Component;

@Component
@Order( Ordered.HIGHEST_PRECEDENCE )
public class EdiscoveryPromptProvider extends DefaultPromptProvider {

   @Override
   public String getPrompt() {
      return "ediscovery-shell> ";
   }

   @Override
   public String getProviderName() {
      return "Ediscovery prompt provider";
   }

}