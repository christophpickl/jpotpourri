package net.sourceforge.jpotpourri.mvnplugin.buildmac;

import java.io.File;

import net.sourceforge.jpotpourri.PtException;
import net.sourceforge.jpotpourri.tools.PtOperatingSystem;
import net.sourceforge.jpotpourri.tools.PtShellExecuter;
import net.sourceforge.jpotpourri.tools.PtUserSniffer;
import net.sourceforge.jpotpourri.util.PtFileUtil;

import org.apache.maven.plugin.MojoExecutionException;

/**
 * contains the actual logic to build the *.app bundle.
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
class Executer {

	/** absolute path to mac os x JavaApplicationStub file to create an executable *.app bundle */
	private static final File JAVA_APP_STUB = new File("/System/Library/Frameworks/JavaVM.framework/" +
												"Versions/Current/Resources/MacOS/JavaApplicationStub");

	/** constant storing filename of info plist */
	private static final String FILENAME_PLIST_INFO = "Info.plist";

	/** constant storing filename of version plist */
	private static final String FILENAME_PLIST_VERSION = "version.plist";
	
	/** mojo logger */
	private final IMojoLogger logger;

	/** pom data container */
	private final IMojoData data;
	

	/**
	 * single constructor
	 */
	public Executer(final IMojoLogger logger, final IMojoData data) {
		this.logger = logger;
		this.data = data;
	}

	/**
	 * core method which does all the work: create folders, copy files, create plist files, exec some shell commands.
	 * @throws MojoExecutionException if any of the many possible errors occurs :)
	 */
	public void execute() throws MojoExecutionException {		
		this.checkOperatingSystem();

		final File folderApp = new File(this.data.getTargetDirectory(),
				this.data.getAppName() + "-" + this.data.getAppVersion() + ".app");
		final File folderContents = new File(folderApp, "Contents");
		final File folderMacOS = new File(folderContents, "MacOS");
		final File folderResources = new File(folderContents, "Resources");
		final File folderJava = new File(folderResources, "Java");
		
		final File targetJavaAppStub = new File(folderMacOS, JAVA_APP_STUB.getName());
		final File targetInfoPlistFile = new File(folderContents, FILENAME_PLIST_INFO);
		final File targetVersionPlistFile = new File(folderContents, FILENAME_PLIST_VERSION);
		
		
		if(folderApp.exists() == true) {
			try {
				this.logger.info("Deleting already existing application folder at '" + folderApp.getName() + "' ...");
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
		
		final String jarWithDependsFileName = this.data.getAppName() + "-" +
											  this.data.getAppVersion() + "-" +
											  "jar-with-dependencies.jar";
		
		this.executeCopyFiles(folderResources, folderJava, targetJavaAppStub, jarWithDependsFileName);
		this.executePlistFiles(jarWithDependsFileName, targetInfoPlistFile, targetVersionPlistFile);
		this.executeShellCommands(folderApp, targetJavaAppStub);
		
	}
	
	/**
	 * copies all (necessary) files: JavaApplicationStub, jarWithDependencies, appIcon and additional resurces
	 * @param folderResources target folder for resources
	 * @param folderJava target folder for java resources
	 * @param targetJavaAppStub destination for the JavaApplicationStub
	 * @param jarWithDependsFileName name to create proper source/target <code>File</code> instance
	 * @throws MojoExecutionException if jarWithDependencies/JavaApplication not exists, or copying fails
	 */
	private void executeCopyFiles(
			final File folderResources,
			final File folderJava,
			final File targetJavaAppStub,
			final String jarWithDependsFileName
			) throws MojoExecutionException {
		this.logger.info("Copying files ...");
		
		final File jarWithDependsSource = new File(this.data.getTargetDirectory(), jarWithDependsFileName);
		final File jarWithDependsTarget = new File(folderJava, jarWithDependsFileName);
		
		if(jarWithDependsSource.exists() == false) {
			throw new MojoExecutionException("Jar with dependencies file not existing at: " + 
					jarWithDependsSource.getAbsolutePath());
		}
		if(JAVA_APP_STUB.exists() == false) {
			throw new MojoExecutionException("Could not find Java Application stub at: " + 
					JAVA_APP_STUB.getAbsolutePath());
		}
		
		copyFile(JAVA_APP_STUB, targetJavaAppStub);
		copyFile(jarWithDependsSource, jarWithDependsTarget);
		
		if(this.data.getAppIcon() != null) {
			copyFile(this.data.getAppIcon(), new File(folderResources, this.data.getAppIcon().getName()));
		}

		
		if(this.data.getAdditionalResources() != null) {
			for (final String rsrc : this.data.getAdditionalResources()) {
				final File source = new File(this.data.getBaseDirectory(), rsrc);
				final File target = new File(folderResources, source.getName());
				copyFile(source, target);
			}
		}
	}
	
	/**
	 * copies or writes the necessary plist files (version.plist, Info.plist).
	 * @param jarWithDependsFileName used to create content of Info.plist file 
	 * @param infoPlistFile target destination
	 * @param versionPlistFile target destination
	 * @throws MojoExecutionException if copying or creating plists fails, or insufficient configurated
	 */
	private void executePlistFiles(
			final String jarWithDependsFileName,
			final File infoPlistFile,
			final File versionPlistFile
			) throws MojoExecutionException {

		
		final PlistWriter plistWriter = new PlistWriter(this.logger, this.data);
		
		if(this.data.getPlistInfo() != null) {
			this.copyFile(this.data.getPlistInfo(), infoPlistFile);
		} else if(this.data.isPlistInfoParamsSet()) {
			plistWriter.createPlistInfo(infoPlistFile, jarWithDependsFileName);
		} else {
			throw new MojoExecutionException("Neither plistInfo nor plistInfoParams defined!");
		}

		if(this.data.getPlistVersion() != null) {
			this.copyFile(this.data.getPlistVersion(), versionPlistFile);
		} else  {
			plistWriter.createPlistVersion(versionPlistFile);
		}
	}
	
	/**
	 * executes necessary shell commands: set file rights on JavaApplicationStub and set *.app folder attribute.
	 * @param folderApp which will get attribute "Has bundle" by SetFile tool
	 * @param targetJavaAppStub which will be chmod'ed to 775 to be executable
	 * @throws MojoExecutionException if executing any shell command failed
	 */
	private void executeShellCommands(
			final File folderApp,
			final File targetJavaAppStub
			) throws MojoExecutionException {
		this.logger.info("Executing shell commands ...");
		
		assert(targetJavaAppStub.exists() == true);
		shellExec("chmod 775 " + escapeWhitespace(targetJavaAppStub.getAbsolutePath()));
		
		assert(folderApp.exists() == true);
		// set attribute "Has bundle"
		shellExec("/Developer/Tools/SetFile -a B " + escapeWhitespace(folderApp.getAbsolutePath()));
	}
	
	/**
	 * simply escapes every space " " with "\ " to avoid problems with paths containing whitespace.
	 * @param input which should be escaped
	 * @return escpaed string
	 */
	private static String escapeWhitespace(final String input) {
		return input.replaceAll("\\ ", "\\\\ ");
	}
	
	/**
	 * uses <code>PtShellExecuter</code> to start own process and execute the command.
	 * @param shellCommand which will be executed just as it is
	 * @throws MojoExecutionException if executing the command failed
	 */
	private void shellExec(final String shellCommand) throws MojoExecutionException {
		try {
			this.logger.debug("Executing shell command '" + shellCommand + "' ...");
			PtShellExecuter.executeProcess(shellCommand);
		} catch (PtException e) {
			throw new MojoExecutionException("Could not execute command '" + shellCommand + "'!", e);
		}
	}
	
	/**
	 * simply copies a file from source to target by using <code>PtFileUtil</code>.
	 * @param source file which should be copied; must exist
	 * @param target file where source should be copied to; must not exist
	 * @throws MojoExecutionException if preconditions on source/target not fulfilled, or copying failed
	 */
	private void copyFile(final File source, final File target) throws MojoExecutionException {
		if(source.exists() == false) {
			throw new MojoExecutionException("Source file '" + source.getAbsolutePath() + "' does not exist!");
		}
		if(target.exists() == true) {
			throw new MojoExecutionException("Target file '" + source.getAbsolutePath() + "' already existing!");
		}
		
		try {
			this.logger.debug("Copying file from '" + source.getAbsolutePath() + "' to " +
					"'" + target.getAbsolutePath() + "'.");
			PtFileUtil.copyFile(source, target);
		} catch (PtException e) {
			throw new MojoExecutionException("Could not copy file from '" + source.getAbsolutePath() + "' to " +
					"'" + target.getAbsolutePath() + "'!", e);
		}
	}
	
	/**
	 * creates given directory by invoking <code>mkdirs</code>.
	 * @param directoryToCreate may not be null
	 * @throws MojoExecutionException if creating directories failed (<code>mkdirs</code> returned false).
	 */
	private void makeFolders(final File directoryToCreate) throws MojoExecutionException {
		this.logger.debug("Creating folder '" + directoryToCreate.getAbsolutePath() + "' ...");
		if(directoryToCreate.mkdirs() == false) {
			throw new MojoExecutionException("Could not create folder '" + directoryToCreate.getAbsolutePath() + "'!");
		}
	}
	
	/**
	 * checks if user is running proper operating system.
	 * @throws MojoExecutionException if operating system is not equals mac os x.
	 */
	private void checkOperatingSystem() throws MojoExecutionException {
		final PtOperatingSystem opSystem = PtUserSniffer.getOS();
		final boolean isProperOpSystem = opSystem == PtOperatingSystem.MAC;
		 
		this.logger.info("Checking operating system '" + opSystem + "' ... " + (isProperOpSystem ? "ok" : "error"));
		if(isProperOpSystem == false) {
			throw new MojoExecutionException("Invalid Operating System '" + opSystem + "' (Mac OS X required)!");
		}
	}
}
