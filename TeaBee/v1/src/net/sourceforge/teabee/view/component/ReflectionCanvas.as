package net.sourceforge.teabee.view.component {

import mx.containers.Canvas;
import mx.containers.Panel;
import mx.controls.Image;
import mx.core.UIComponent;

import net.sourceforge.teabee.view.third.ReflectionManager;

[Bindable]
[DefaultProperty("component")]
public class ReflectionCanvas extends Canvas {

	public var component:UIComponent;
	
	private var image:Image = new Image();
	
	private var reflectionManager:ReflectionManager = new ReflectionManager();
	
	private var panel:Panel = new Panel();
	
	
	private var _distance:Number = 50;
	
	public function ReflectionCanvas() { }
	
	
    override protected function createChildren():void {
    	super.createChildren();
    	
    	panel.setStyle("headerHeight", "0");
    	panel.setStyle("roundedBottomCorners", false);
    	panel.setStyle("cornerRadius", 0);
    	panel.setStyle("dropShadowEnabled", false);
    	panel.setStyle("borderThicknessLeft", 1);
    	panel.setStyle("borderThicknessRight", 1);
    	panel.setStyle("borderThicknessBottom", 1);
		
		panel.addChild(this.component);
		this.addChild(panel);
		
		reflectionManager.target = panel;
		reflectionManager.width = panel.width;
		var newHeight:Number = (panel.height / 100) * this._distance;
		trace("newHeight: " + newHeight.toString());
		reflectionManager.height = newHeight;
		reflectionManager.fadeFrom = 0.5;
		reflectionManager.fadeTo = 0.0;
		reflectionManager.blur = 0.3;
		reflectionManager.distance = this._distance;
		this.addChild(reflectionManager);
    }
	
	public function get distance():uint {
		return this._distance;
	}
	public function set distance(value:uint):void {
		this._distance = value;
		reflectionManager.height = (panel.height / 100) * this._distance;
		reflectionManager.distance = this._distance;
	}
	
}
}
