package code.model.vo {

/** @abstract */
[Bindable]
public class Item {
	
	/** only used by dao */
	public var itemId: int;
	
	/** only used by dao */
	public var parentId: int = -1;
	
	public var title: String;
	
	
	public function Item(pTitle: String) {
		if(pTitle == null) throw new Error("pTitle == null");
		
		this.title = pTitle;
	}

	protected function toInnerString(): String {
		return "itemId="+itemId+";parentId="+parentId+";title="+title;
	}

}
}