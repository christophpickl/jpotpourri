package net.sourceforge.jpotpourri.mvnplugin.buildmac;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * only represents the interface to maven (holds proper values).
 * 
 * @goal buildmac
 * @phase ?
 * @author christoph_pickl@users.sourceforge.net
 */
public class BuildMacMojo extends AbstractMojo implements IMojoData, IMojoLogger{
	
	/**
	 * Optional; project.name will be used, if null
	 * 
	 * @parameter
	 */
	private String appName;
	
	/**
	 * Optional; project.version will be used, if null
	 * 
	 * @parameter
	 */
	private String appVersion;

	/**
	 * Optional
	 * 
	 * @parameter
	 */
	private File appIcon;
	
	/**
	 * Either plistInfo File, or plistVersionParams map given
	 * 
	 * @parameter
	 */
	private File plistInfo;

	/**
	 * Either plistInfo File, or plistVersionParams map given
	 * 
	 * @parameter
	 */
	private Map<String, String> plistInfoParams;

	/**
	 * Either plistVersion File, or plistVersionParams map given
	 * 
	 * @parameter
	 */
	private File plistVersion;
	
	/**
	 * Either plistVersion File, or plistVersionParams map given
	 * 
	 * @parameter
	 */
	private Map<String, String> plistVersionParams;
	
	/**
	 * Optional
	 * 
	 * @parameter
	 */
	private List<String> additionalResources;
	
	/**
	 * Should NOT be set by user.
	 * 
     * @parameter expression="${project.build.directory}"
     */
    private File targetDirectory;
	
	/**
	 * Should NOT be set by user.
	 * 
     * @parameter expression="${basedir}"
     */
    private File baseDirectory;
	
	/**
	 * Should NOT be set by user.
	 * 
     * @parameter expression="${project.version}"
     */
    private String projectVersion;
	
	/**
	 * Should NOT be set by user.
	 * 
     * @parameter expression="${project.name}"
     */
    private String projectName;
    

	// AbstractMojo
	public void execute() throws MojoExecutionException, MojoFailureException {

		if(this.appName == null) {
			this.debug("No appName defined, using project name '" + this.projectName + "' instead.");
			this.appName = this.projectName;
		}
		
		if(this.appVersion == null) {
			this.debug("No appVersion defined, using project version '" + this.projectVersion + "' instead.");
			this.appVersion = this.projectVersion;
		}
		
		if(this.plistVersion == null && this.plistVersionParams == null) {
			this.info("Neither plistVersion nor plistVersionParams defined, using default values instead.");
			this.plistVersionParams = new HashMap<String, String>(0);
		}
		
		new Executer(this, this).execute();
		
	}
	

	// IMojoLogger
	public void debug(final String msg) {
		getLog().debug(msg);
	}

	// IMojoLogger
	public void info(final String msg) {
		getLog().info(msg);
	}


	// IMojoData
	public String getAppName() {
		return this.appName;
	}

	// IMojoData
	public String getAppVersion() {
		return this.appVersion;
	}

	// IMojoData
	public File getAppIcon() {
		return this.appIcon;
	}

	// IMojoData
	public File getPlistInfo() {
		return this.plistInfo;
	}

	// IMojoData
	public String getPlistInfoParams(final PlistInfoParam param) {
		return this.plistInfoParams.get(param.getXmlPomKey());
	}

	// IMojoData
	public File getPlistVersion() {
		return this.plistVersion;
	}

	// IMojoData
	public String getPlistVersionParams(final PlistVersionParam param) {
		return this.plistVersionParams.get(param.getXmlPomKey());
	}

	// IMojoData
	public List<String> getAdditionalResources() {
		return this.additionalResources;
	}

	// IMojoData
	public File getTargetDirectory() {
		return this.targetDirectory;
	}

	// IMojoData
	public File getBaseDirectory() {
		return this.baseDirectory;
	}

	// IMojoData
	public String getProjectName() {
		return this.projectName;
	}

	// IMojoData
	public String getProjectVersion() {
		return this.projectVersion;
	}

	// IMojoData
	public boolean isPlistInfoParamsSet() {
		return this.plistInfoParams != null;
	}
	

}
