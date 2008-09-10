package classes {
public class Util {
	public function Util() {
		throw new Error("No instantiation of Util allowed!");
	}

	public static function randomInteger(minInclusive:int, maxInclusive:int):int {
		trace("minInclusive: " + minInclusive + "; maxInclusive: " + maxInclusive);
		var n:Number = Math.random() * maxInclusive;
		trace("n: " + n);
		var r:int = Math.round(n) + minInclusive;
		trace("r: " + r);
		return r;
	}
}
}