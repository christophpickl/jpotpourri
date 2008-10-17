package code.common {

public class StringUtil {
	public function StringUtil() {
		throw new Error("singleton");
	}
	
	public static function escape(string:String):String {
		var tmp:String = string; // FIXME implement me
		// Note that any spaces, quotes or other punctuation in the parameter value must be URL-escaped
		// e.g.: "spy plane" ... %22spy+plane%22
		// e.g.: videos matching either "boating" or "sailing" but not "fishing" ... boating&7Csailing+-fishing
		return tmp;
	}

}
}