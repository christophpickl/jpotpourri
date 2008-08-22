package net.sourceforge.teabee.dao {

import net.sourceforge.teabee.valueobject.Library;

public interface ILibraryDao {
	
	function save(library:Library):void;
	
	function load():Library;
	
	function clear():void;
	
}
}