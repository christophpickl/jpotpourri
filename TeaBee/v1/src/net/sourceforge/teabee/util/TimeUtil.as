package net.sourceforge.teabee.util {

public class TimeUtil {
	
	public function TimeUtil() {
		throw new Error("TimeUtil may not be instantiated!");
	}
	
	public static function formatSeconds(sec:uint):String {
		var min:uint = Math.floor(sec / 60);
		sec = sec % 60;
		// StringUtil.substitute("{0}:{1}", minString, secString);
		return (min < 10 ? "0" : "") + min + ":" + (sec < 10 ? "0" : "") + sec;
	}

}
}