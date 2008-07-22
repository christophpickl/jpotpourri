package net.sourceforge.jpotpourri.mvnplugin.buildmac;

import java.io.File;
import java.util.List;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
interface IMojoData {

	String getAppName();

	String getAppVersion();

	File getAppIcon();

	File getPlistInfo();

	String getPlistInfoParams(final PlistInfoParam paramKey);

	boolean isPlistInfoParamsSet();
	
	File getPlistVersion();

	String getPlistVersionParams(final PlistVersionParam paramKey);

	List<String> getAdditionalResources();

	File getTargetDirectory();

	File getBaseDirectory();

	String getProjectName();

	String getProjectVersion();
	
}
