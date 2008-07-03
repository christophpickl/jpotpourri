package net.sourceforge.jpotpourri.tools;

import org.apache.log4j.Logger;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public final class UserSniffer {

    /** class' own logger using log4j */
    private static final Logger LOG = Logger.getLogger(UserSniffer.class);

    /**  */
    private static OperatingSystem os;

    static { // TODO use lazy initializing instead
        final String osname = System.getProperty("os.name");

        if (osname.contains("Mac")) {
            UserSniffer.os = OperatingSystem.MAC;
        } else if (osname.contains("Windows")) {
            UserSniffer.os = OperatingSystem.WIN;
        } else if (osname.contains("Linux")) {
            UserSniffer.os = OperatingSystem.LINUX;
        } else if (osname.contains("Unix") || osname.contains("UNIX")  || osname.contains("HP-UX")
                || osname.contains("AIX") || osname.contains("BSD") || osname.contains("Irix")) {
            UserSniffer.os = OperatingSystem.UNIX;
        } else if (osname.contains("SunOS") || osname.contains("Solaris")) {
            UserSniffer.os = OperatingSystem.SOLARIS;
        } else if (osname.contains("OS/2")) {
            UserSniffer.os = OperatingSystem.OS2;
        } else {
            UserSniffer.LOG.warn("could not determine operating system by name [" + osname + "]");
            UserSniffer.os = OperatingSystem.UNKOWN;
        }
        LOG.debug("seems as user is running '" + UserSniffer.os + "'.");
    }

    
    
    private UserSniffer() {
    	// no instantiation
    }
    
    /**
     *
     * @return
     */
    public static OperatingSystem getOS() {
        return UserSniffer.os;
    }

    /**
     *
     * @param os
     * @return
     */
    public static boolean isOperatingSystem(final OperatingSystem isOs) {
        return UserSniffer.os.equals(isOs);
    }

    public static boolean isWindows() {
        return UserSniffer.os.equals(OperatingSystem.WIN);
    }

    public static boolean isMacOSX() {
        return UserSniffer.os.equals(OperatingSystem.MAC);
    }

    public static boolean isLinux() {
        return UserSniffer.os.equals(OperatingSystem.LINUX);
    }

    public static boolean isUnix() {
        return UserSniffer.os.equals(OperatingSystem.UNIX);
    }

    public static boolean isOS2() {
        return UserSniffer.os.equals(OperatingSystem.OS2);
    }

    public static boolean isUnkown() {
        return UserSniffer.os.equals(OperatingSystem.UNKOWN);
    }

}
