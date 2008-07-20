package net.sourceforge.omov.buildmac;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import net.sourceforge.jpotpourri.util.PtCloseUtil;

import org.apache.maven.plugin.MojoExecutionException;

public class PlistWriter {

	private static final String DEFAULT_JVM_VERSION = "1.5+";
	
	private final BuildMacMojo mojo;


	private final File appIcon;
	private final Map<String, String> plistInfoParams;
	private final Map<String, String> plistVersionParams;
	

	public PlistWriter(
			final BuildMacMojo mojo,
			final File appIcon,
			final Map<String, String> plistInfoParams,
			final Map<String, String> plistVersionParams
			) {
		
		this.mojo = mojo;
		this.appIcon = appIcon;
		this.plistInfoParams = plistInfoParams;
		this.plistVersionParams = plistVersionParams;
	}

	/**
	 * - MainClass (mandatory)
	 * - CFBundleGetInfoString (optional, default: project.name)
	 * - CFBundleShortVersionString (optional, default: project.version)
	 * - CFBundleName (optional, default: projecct.name)
	 * - JVMVersion (optional, default: 1.5+)
	 */
	public void createPlistInfo(final File targetFile, final String jarDependenciesFileName) throws MojoExecutionException {

		final String mainClass = this.plistInfoParams.get("MainClass");
		if(mainClass == null) {
			throw new MojoExecutionException("Key 'configuration.plistInfoParams.MainClass' not found!");
		}
		
		String infoString = this.plistInfoParams.get("CFBundleGetInfoString");
		if(infoString == null) {
			this.mojo.debug("No CFBundleGetInfoString defined, using project name '" + this.mojo.getProjectName() + "' instead.");
			infoString = this.mojo.getProjectName();
		}
		
		String bundleShortVersion = this.plistInfoParams.get("CFBundleShortVersionString");
		if(bundleShortVersion == null) {
			this.mojo.debug("No CFBundleShortVersionString defined, using project version '" + this.mojo.getProjectVersion() + "' instead.");
			bundleShortVersion = this.mojo.getProjectVersion();
		}
		
		String bundleVersion = this.plistInfoParams.get("CFBundleVersion");
		if(bundleVersion == null) {
			this.mojo.debug("No CFBundleVersion defined, using project version '" + this.mojo.getProjectVersion() + "' instead.");
			bundleVersion = this.mojo.getProjectVersion();
		}
		
		
		String bundleName = this.plistInfoParams.get("CFBundleName");
		if(bundleName == null) {
			this.mojo.debug("No CFBundleName defined, using project name '" + this.mojo.getProjectName() + "' instead.");
			bundleName = this.mojo.getProjectName();
		}
		
		String jvmVersion = this.plistInfoParams.get("JVMVersion");
		if(jvmVersion == null) {
			this.mojo.debug("No JVMVersion defined, using default value '" + DEFAULT_JVM_VERSION + "' instead.");
			jvmVersion = DEFAULT_JVM_VERSION;
		}
		
		final StringBuilder sb = new StringBuilder();
		
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\n");
		sb.append("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">").append("\n");
		sb.append("<plist version=\"1.0\">").append("\n");
		sb.append("<dict>").append("\n");
		sb.append("\t<key>CFBundleDevelopmentRegion</key>").append("\n");
		sb.append("\t<string>English</string>").append("\n");
		sb.append("\t<key>CFBundleExecutable</key>").append("\n");
		sb.append("\t<string>JavaApplicationStub</string>").append("\n");
		
		if(this.appIcon != null) {
			sb.append("\t<key>CFBundleIconFile</key>").append("\n");
			sb.append("\t<string>").append(this.appIcon.getName()).append("</string>").append("\n");
		}
		
		sb.append("\t<key>CFBundleInfoDictionaryVersion</key>").append("\n");
		sb.append("\t<string>6.0</string>").append("\n");
		sb.append("\t<key>CFBundleGetInfoString</key>").append("\n");
		sb.append("\t<string>").append(infoString).append("</string>").append("\n");
		sb.append("\t<key>CFBundleShortVersionString</key>").append("\n");
		sb.append("\t<string>").append(bundleShortVersion).append("</string>").append("\n");
		sb.append("\t<key>CFBundleName</key>").append("\n");
		sb.append("\t<string>").append(bundleName).append("</string>").append("\n");
		sb.append("\t<key>CFBundlePackageType</key>").append("\n");
		sb.append("\t<string>APPL</string>").append("\n");
		sb.append("\t<key>CFBundleSignature</key>").append("\n");
		sb.append("\t<string>????</string>").append("\n");
		sb.append("\t<key>CFBundleVersion</key>").append("\n");
		sb.append("\t<string>").append(bundleVersion).append("</string>").append("\n");
		sb.append("\t<key>Java</key>").append("\n");
		sb.append("\t<dict>").append("\n");
		sb.append("\t\t<key>ClassPath</key>").append("\n");
		sb.append("\t\t<array>").append("\n");
		sb.append("\t\t\t<string>$JAVAROOT/").append(jarDependenciesFileName).append("</string>").append("\n");
		sb.append("\t\t</array>").append("\n");
		sb.append("\t\t<key>JVMVersion</key>").append("\n");
		sb.append("\t\t<string>").append(jvmVersion).append("</string>").append("\n");
		sb.append("\t\t<key>MainClass</key>").append("\n");
		sb.append("\t\t<string>").append(mainClass).append("</string>").append("\n");
		sb.append("\t\t<key>Properties</key>").append("\n");
		sb.append("\t\t<dict>").append("\n");
		sb.append("\t\t\t<key>apple.laf.useScreenMenuBar</key>").append("\n");
		sb.append("\t\t\t<string>true</string>").append("\n");
		sb.append("\t\t</dict>").append("\n");
		sb.append("\t</dict>").append("\n");
		sb.append("</dict>").append("\n");
		sb.append("</plist>").append("\n");

		writeFileContents(targetFile, sb.toString());
	}

	/**
	 * - ProjectName (optional, default: project.name)
	 * - CFBundleShortVersionString (optional, default: project.version)
	 */
	public void createPlistVersion(final File targetFile) throws MojoExecutionException {

		String pProjectName = this.plistVersionParams.get("ProjectName");
		if(pProjectName == null) {
			this.mojo.debug("No ProjectName defined, using project name '" + this.mojo.getProjectName() + "' instead.");
			pProjectName = this.mojo.getProjectName();
		}
		
		String pBundleVersion = this.plistVersionParams.get("CFBundleShortVersionString");
		if(pBundleVersion == null) {
			this.mojo.debug("No CFBundleShortVersionString defined, using project version '" + this.mojo.getProjectVersion() + "' instead.");
			pBundleVersion = this.mojo.getProjectVersion();
		}
		
		final StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\n");
		sb.append("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">").append("\n");
		sb.append("<plist version=\"1.0\">").append("\n");
		sb.append("<dict>").append("\n");
		
		sb.append("\t<key>BuildVersion</key>").append("\n");
		sb.append("\t<string>1</string>").append("\n");
		sb.append("\t<key>CFBundleShortVersionString</key>").append("\n");
		sb.append("\t<string>").append(pBundleVersion).append("</string>").append("\n");
		sb.append("\t<key>CFBundleVersion</key>").append("\n");
		sb.append("\t<string>10</string>").append("\n");
		sb.append("\t<key>ProjectName</key>").append("\n");
		sb.append("\t<string>").append(pProjectName).append("</string>").append("\n");
		
		sb.append("</dict>").append("\n");
		sb.append("</plist>").append("\n");
		
		writeFileContents(targetFile, sb.toString());
		
	}
	
	private void writeFileContents(final File targetFile, final String fileContent) throws MojoExecutionException {
		this.mojo.debug("Writing file contents to file '" + targetFile.getAbsolutePath() + "' ...");
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(targetFile));
			writer.write(fileContent);
		} catch (IOException e) {
			throw new MojoExecutionException("Could not write contents to file '" + targetFile.getAbsolutePath() + "'!", e);
		} finally {
			PtCloseUtil.close(writer);
		}
	}
}
