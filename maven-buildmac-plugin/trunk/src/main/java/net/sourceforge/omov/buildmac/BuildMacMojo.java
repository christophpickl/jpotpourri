package net.sourceforge.omov.buildmac;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.jpotpourri.PtException;
import net.sourceforge.jpotpourri.tools.PtOperatingSystem;
import net.sourceforge.jpotpourri.tools.PtShellExecuter;
import net.sourceforge.jpotpourri.tools.PtUserSniffer;
import net.sourceforge.jpotpourri.util.PtFileUtil;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal buildmac
 */
public class BuildMacMojo extends AbstractMojo {

	private static File JAVA_APP_STUB = new File("/System/Library/Frameworks/JavaVM.framework/" +
												"Versions/Current/Resources/MacOS/JavaApplicationStub");
	
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
    
	
	public void execute() throws MojoExecutionException, MojoFailureException {
		
		this.checkOperatingSystem();

		if(this.appName == null) {
			this.debug("No appName defined, using project name '" + this.projectName + "' instead.");
			this.appName = this.projectName;
		}

		if(this.appVersion == null) {
			this.debug("No appVersion defined, using project version '" + this.projectVersion + "' instead.");
			this.appVersion = this.projectVersion;
		}

		
		final File folderApp = new File(this.targetDirectory, this.appName + "-" + this.appVersion + ".app");
		final File folderContents = new File(folderApp, "Contents");
		final File folderMacOS = new File(folderContents, "MacOS");
		final File folderResources = new File(folderContents, "Resources");
		final File folderJava = new File(folderResources, "Java");
		
		final File targetJavaAppStub = new File(folderMacOS, "JavaApplicationStub");
		final File infoPlistFile = new File(folderContents, "Info.plist");
		final File versionPlistFile = new File(folderContents, "version.plist");
		
		
		if(folderApp.exists() == true) {
			try {
				this.info("Deleting already existing application folder at '" + folderApp.getName() + "' ...");
				PtFileUtil.deleteDirectoryRecursive(folderApp);
			} catch (PtException e) {
				throw new MojoExecutionException("Could not delete directory '" + folderApp.getAbsolutePath() + "'!");
			}
		}
		
		makeFolders(folderApp);
		makeFolders(folderContents);
		makeFolders(folderMacOS);
		makeFolders(folderResources);
		makeFolders(folderJava);
		
		final String jarWithDependsFileName =  this.appName + "-" + this.appVersion + "-jar-with-dependencies.jar";
		
		this.executeCopyFiles(folderResources, folderJava, targetJavaAppStub, jarWithDependsFileName);
		this.executePlistFiles(jarWithDependsFileName, infoPlistFile, versionPlistFile);
		this.executeShellCommands(folderApp, targetJavaAppStub);
		
	}
	
	private void executeCopyFiles(
			final File folderResources,
			final File folderJava,
			final File targetJavaAppStub,
			final String jarWithDependsFileName
			) throws MojoExecutionException {
		this.info("Copying files ...");
		
		final File jarWithDependsSource = new File(this.targetDirectory, jarWithDependsFileName);
		final File jarWithDependsTarget = new File(folderJava, jarWithDependsFileName);
		
		if(jarWithDependsSource.exists() == false) {
			throw new MojoExecutionException("Jar with dependencies file not existing at: " + jarWithDependsSource.getAbsolutePath());
		}
		if(JAVA_APP_STUB.exists() == false) {
			throw new MojoExecutionException("Could not find Java Application stub at: " + JAVA_APP_STUB.getAbsolutePath());
		}
		
		copyFile(JAVA_APP_STUB, targetJavaAppStub);
		copyFile(jarWithDependsSource, jarWithDependsTarget);
		
		if(this.appIcon != null) {
			copyFile(this.appIcon, new File(folderResources, this.appIcon.getName()));
		}

		
		if(this.additionalResources != null) {
			for (final String rsrc : this.additionalResources) {
				final File source = new File(this.baseDirectory, rsrc);
				final File target = new File(folderResources, source.getName());
				copyFile(source, target);
			}
		}
	}
	
	private void executePlistFiles(
			final String jarWithDependsFileName,
			final File infoPlistFile,
			final File versionPlistFile
			) throws MojoExecutionException {

		if(this.plistVersion == null && this.plistVersionParams == null) {
			this.info("Neither plistVersion nor plistVersionParams defined, using default values instead.");
			this.plistVersionParams = new HashMap<String, String>(0);
		}
		final PlistWriter plistWriter = new PlistWriter(this, this.appIcon, this.plistInfoParams, this.plistVersionParams);
		
		if(this.plistInfo != null) {
			copyFile(plistInfo, infoPlistFile);
		} else if(this.plistInfoParams != null) {
			plistWriter.createPlistInfo(infoPlistFile, jarWithDependsFileName);
		} else {
			throw new MojoExecutionException("Neither plistInfo nor plistInfoParams defined!");
		}

		if(this.plistVersion != null) {
			copyFile(plistVersion, versionPlistFile);
		} else  {
			
			plistWriter.createPlistVersion(versionPlistFile);
		}
	}
	
	private void executeShellCommands(final File folderApp, final File targetJavaAppStub) throws MojoExecutionException {
		this.info("Executing shell commands ...");
		
		assert(targetJavaAppStub.exists() == true);
		shellExec("chmod 775 " + targetJavaAppStub.getAbsolutePath());
		// TODO escape whitespace in paths path.replaceAll(" ", "\\ ");
		assert(folderApp.exists() == true);
		shellExec("/Developer/Tools/SetFile -a B " + folderApp.getAbsolutePath());
	}
	
	
	private void shellExec(final String shellCommand) throws MojoExecutionException {
		try {
			this.debug("Executing shell command '" + shellCommand + "' ...");
			PtShellExecuter.executeProcess(shellCommand);
		} catch (PtException e) {
			throw new MojoExecutionException("Could not execute command '" + shellCommand + "'!", e);
		}
	}
	
	private void copyFile(final File source, final File target) throws MojoExecutionException {
		try {
			this.debug("Copying file from '" + source.getAbsolutePath() + "' to '" + target.getAbsolutePath() + "'.");
			PtFileUtil.copyFile(source, target);
		} catch (PtException e) {
			throw new MojoExecutionException("Could not copy file from '" + source.getAbsolutePath() + "' to '" + target.getAbsolutePath() + "'!", e);
		}
	}
	
	private void makeFolders(final File dir) throws MojoExecutionException {
		this.debug("Creating folder '" + dir.getAbsolutePath() + "' ...");
		if(dir.mkdirs() == false) {
			throw new MojoExecutionException("Could not create folder '" + dir.getAbsolutePath() + "'!");
		}
	}
	
	private void checkOperatingSystem() throws MojoExecutionException {
		final PtOperatingSystem opSystem = PtUserSniffer.getOS();
		final boolean isProperOpSystem = opSystem == PtOperatingSystem.MAC;
		 
		this.info("Checking operating system '" + opSystem + "' ... " + (isProperOpSystem ? "ok" : "error") );
		if(isProperOpSystem == false) {
			throw new MojoExecutionException("Invalid Operating System '" + opSystem + "' (Mac OS X required)!");
		}
	}

	void debug(String msg) {
		getLog().debug(msg);
	}

	void info(String msg) {
		getLog().info(msg);
	}

	public String getProjectName() {
		return this.projectName;
	}

	public String getProjectVersion() {
		return this.projectVersion;
	}

}
