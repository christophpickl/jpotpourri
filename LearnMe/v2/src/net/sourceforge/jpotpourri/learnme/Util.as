package net.sourceforge.jpotpourri.learnme {

public class Util {
	
	public function Util() {
		throw new Error("No instantiation of Util allowed!");
	}

	public static function randomInteger(minInclusive:int, maxInclusive:int):int {
		return Math.round(Math.random() * maxInclusive) + minInclusive;
	}
}
}
