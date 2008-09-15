package net.sourceforge.jpotpourri.learnme {

[Bindable]
public class SuccessRate {
	
	public var percent: Number;
	public var countTotal: int;
	public var countCorrect: int;
	
	public function SuccessRate(pPercent: Number = 0, pCountTotal: int = 0, pCountCorrect: int = 0) {
		this.percent = pPercent;
		this.countTotal = pCountTotal;
		this.countCorrect = pCountCorrect;
	}

	public function get toNiceString(): String {
		return this.countCorrect+"/"+this.countTotal + " (" + this.percent + "%)"
	}
}
}