
package com.davedaniels.ediscovery.shell;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultBannerProvider;
import org.springframework.shell.support.util.OsUtils;
import org.springframework.stereotype.Component;

@Component
@Order( Ordered.HIGHEST_PRECEDENCE )
public class EdiscoveryBannerProvider extends DefaultBannerProvider {

   @Value( "${project.description}" )
   private String projectDescription;

   @Value( "${project.version}" )
   private String projectVersion;

   public String getBanner() {
      StringBuffer buf = new StringBuffer();
      buf.append( OsUtils.LINE_SEPARATOR );
      buf.append( "==============================================================================" + OsUtils.LINE_SEPARATOR );
      buf.append( "*" + OsUtils.LINE_SEPARATOR );
      buf.append( "* " + projectDescription + OsUtils.LINE_SEPARATOR );
      buf.append( "* Version: " + this.getVersion() + OsUtils.LINE_SEPARATOR );
      buf.append( "*" + OsUtils.LINE_SEPARATOR );
      buf.append( "=======================================" + OsUtils.LINE_SEPARATOR );

      return buf.toString();
   }

   @Override
   public String getVersion() {
      return projectVersion;
   }

   public String getWelcomeMessage() {
      return "For help, type help." + OsUtils.LINE_SEPARATOR;
   }

   @Override
   public String getProviderName() {
      return "Ediscovery Banner";
   }
}