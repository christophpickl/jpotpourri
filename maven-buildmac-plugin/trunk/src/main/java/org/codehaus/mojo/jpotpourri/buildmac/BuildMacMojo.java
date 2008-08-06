package org.codehaus.mojo.jpotpourri.buildmac;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Builds the app-folder by an existing jar-with-dependencies.jar
 * 
 * @author christoph_pickl@users.sourceforge.net
 * @goal buildmac
 * @phase package
 */
public class BuildMacMojo
    extends AbstractMojo
    implements IMojoData, IMojoLogger
{

    /**
     * Either <code>plistInfo</code> file, or <code>plistVersionParams</code> map given
     * 
     * @required
     * @parameter
     */
    private File plistInfo;

    /**
     * Either <code>plistInfo</code> file, or <code>plistVersionParams</code> map given
     * 
     * Map<String, String>
     * 
     * @required
     * @parameter
     */
    private Map plistInfoParams;

    /**
     * Either <code>plistVersion</code> file, or <code>plistVersionParams</code> map given
     * 
     * @required
     * @parameter
     */
    private File plistVersion;

    /**
     * Either <code>plistVersion</code> file, or <code>plistVersionParams</code> map given
     * 
     * Map<String, String>
     * 
     * @required
     * @parameter
     */
    private Map plistVersionParams;

    // ------------ OPTIONAL -------------------

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
     * List of additional files which should be copied to <code>*.app/Contents/MacOS/Resources/</code>. Gets usefull
     * if you want to have some more OS specific possibilities.
     * 
     * List<String>
     * 
     * @parameter
     */
    private List additionalResources;

    // ------------ READ ONLY -------------------

    /**
     * Should NOT be set by user.
     * 
     * @readonly
     * @parameter expression="${project.build.directory}"
     */
    private File targetDirectory;

    /**
     * Should NOT be set by user.
     * 
     * @readonly
     * @parameter expression="${basedir}"
     */
    private File baseDirectory;

    /**
     * Should NOT be set by user.
     * 
     * @readonly
     * @parameter expression="${project.version}"
     */
    private String projectVersion;

    /**
     * Should NOT be set by user.
     * 
     * @readonly
     * @parameter expression="${project.name}"
     */
    private String projectName;

    // AbstractMojo
    public void execute()
        throws MojoExecutionException, MojoFailureException
    {

        if ( this.appName == null )
        {
            this.debug( "No appName defined, using project name '" + this.projectName + "' instead." );
            this.appName = this.projectName;
        }

        if ( this.appVersion == null )
        {
            this.debug( "No appVersion defined, using project version '" + this.projectVersion + "' instead." );
            this.appVersion = this.projectVersion;
        }

        if ( this.plistVersion == null && this.plistVersionParams == null )
        {
            this.info( "Neither plistVersion nor plistVersionParams defined, using default values instead." );
            this.plistVersionParams = new HashMap( 0 ); // <String, String>
        }

        new Executer( this, this ).execute();

    }

    // IMojoLogger
    public void debug( final String msg )
    {
        getLog().debug( msg );
    }

    // IMojoLogger
    public void info( final String msg )
    {
        getLog().info( msg );
    }

    // IMojoData
    public String getAppName()
    {
        return this.appName;
    }

    // IMojoData
    public String getAppVersion()
    {
        return this.appVersion;
    }

    // IMojoData
    public File getAppIcon()
    {
        return this.appIcon;
    }

    // IMojoData
    public File getPlistInfo()
    {
        return this.plistInfo;
    }

    // IMojoData
    public String getPlistInfoParams( final PlistInfoParam param )
    {
        return (String) this.plistInfoParams.get( param.getXmlPomKey() );
    }

    // IMojoData
    public File getPlistVersion()
    {
        return this.plistVersion;
    }

    // IMojoData
    public String getPlistVersionParams( final PlistVersionParam param )
    {
        return (String) this.plistVersionParams.get( param.getXmlPomKey() );
    }

    // IMojoData
    public List getAdditionalResources()
    {
        return this.additionalResources;
    }

    // IMojoData
    public File getTargetDirectory()
    {
        return this.targetDirectory;
    }

    // IMojoData
    public File getBaseDirectory()
    {
        return this.baseDirectory;
    }

    // IMojoData
    public String getProjectName()
    {
        return this.projectName;
    }

    // IMojoData
    public String getProjectVersion()
    {
        return this.projectVersion;
    }

    // IMojoData
    public boolean isPlistInfoParamsSet()
    {
        return this.plistInfoParams != null;
    }

}
