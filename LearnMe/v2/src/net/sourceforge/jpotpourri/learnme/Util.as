package net.sourceforge.jpotpourri.learnme {
	import mx.collections.ArrayCollection;
	import mx.collections.Sort;
	

public class Util {
	
	public function Util() {
		throw new Error("No instantiation of Util allowed!");
	}

	public static function randomInteger(minInclusive:int, maxInclusive:int):int {
		return Math.round(Math.random() * maxInclusive) + minInclusive;
	}
	
	
	public static function removeLinebreaks(value: String): String {
		var replaced: String = value.replace(/[(\n)(\r)]/g, "");
		return replaced.replace(/[\t]+/g, "");
	}
	
	public static function scrambleArrayCollection(inArray: ArrayCollection): ArrayCollection {
		const outArray: ArrayCollection = new ArrayCollection();
		
		for each(var obj: Object in inArray) {
			outArray.addItem(obj);
		}
		
		var s: Sort = new Sort();
		s.fields = [];
		s.compareFunction = onCompare;
		
		outArray.sort = s;
		outArray.refresh();
		return outArray;
	}
	
	private static function onCompare(obj1: Object, obj2: Object, fields: Array): int {
		return Math.random() < 0.5 ? -1 : 1;
	}
}
}
