package net.sourceforge.jpotpourri.learnme.vo {

[Bindable]
public interface ISourceAnswer {
	
	function get text(): String;
	
	function get feedback(): String;
	
	function get correct(): Boolean;
	
}
}