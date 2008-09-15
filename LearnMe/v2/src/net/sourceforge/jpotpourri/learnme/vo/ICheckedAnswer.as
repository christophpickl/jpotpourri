package net.sourceforge.jpotpourri.learnme.vo {

[Bindable]
public interface ICheckedAnswer extends ISourceAnswer {
	
	function get sourceId(): int;
	
	function get checkedCorrect(): Boolean;
	
	function get checked(): Boolean;
	
	function set checked(value: Boolean): void;
}
}