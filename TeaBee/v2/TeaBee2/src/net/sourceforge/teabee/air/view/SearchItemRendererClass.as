package net.sourceforge.teabee.air.view
{
import mx.controls.Button;
import mx.controls.listClasses.IListItemRenderer;
import mx.core.UIComponent;

import net.sourceforge.teabee.air.search.SearchResultVideo;

public class SearchItemRendererClass extends UIComponent implements IListItemRenderer {
	
	private var _data:SearchResultVideo;
	
	public function SearchItemRendererClass() {
		super();
	}
	

	public function set data(value:Object):void {
		if(value is SearchResultVideo == false) {
			return;
		}
		this._data = SearchResultVideo(value);
		trace("got data: " + _data);
		/*
		var lbl:Label = new Label();
		lbl.text = _data.title;
		lbl.setStyle("color", "#FFFFFF");
		this.addChildAt(lbl, 0);
		*/
		var btn:Button = new Button();
		btn.label = "foobar";
		this.addChildAt(btn, 0);
		
		this.invalidateSize();
		this.invalidateDisplayList();
		this.invalidateSize();
		/*
		_loaded = false;
		progress= 0;
		_data = value;
		var url:String = String((_data is String)? _data:_data.screenshot);
		_loader.load(new URLRequest(url));
		_loader.contentLoaderInfo.addEventListener(Event.COMPLETE,loadComplete);	
		_loader.contentLoaderInfo.addEventListener(ProgressEvent.PROGRESS,updateProgress);	
		invalidateDisplayList();
		invalidateSize();
		*/
	}
	

	public function get data():Object { return _data; }
	
	
	

	/* override protected function measure():void {
		measuredWidth = 100;
		measuredHeight = 100;
	} */
		
	/*
	public function get root():DisplayObject
	{
		return null;
	}
	
	public function get stage():Stage
	{
		return null;
	}
	
	public function get name():String
	{
		return null;
	}
	
	public function set name(value:String):void
	{
	}
	
	public function get parent():DisplayObjectContainer
	{
		return null;
	}
	
	public function get mask():DisplayObject
	{
		return null;
	}
	
	public function set mask(value:DisplayObject):void
	{
	}
	
	public function get visible():Boolean
	{
		return false;
	}
	
	public function set visible(value:Boolean):void
	{
	}
	
	public function get baselinePosition():Number
	{
		return 0;
	}
	
	public function get x():Number
	{
		return 0;
	}
	
	public function set x(value:Number):void
	{
	}
	
	public function get y():Number
	{
		return 0;
	}
	
	public function set y(value:Number):void
	{
	}
	
	public function get scaleX():Number
	{
		return 0;
	}
	
	public function set scaleX(value:Number):void
	{
	}
	
	public function get styleName():Object
	{
		return null;
	}
	
	public function get document():Object
	{
		return null;
	}
	
	public function set styleName(value:Object):void
	{
	}
	
	public function get measuredHeight():Number
	{
		return 0;
	}
	
	public function get scaleY():Number
	{
		return 0;
	}
	
	public function set document(value:Object):void
	{
	}
	
	public function set scaleY(value:Number):void
	{
	}
	
	public function get mouseX():Number
	{
		return 0;
	}
	
	public function get initialized():Boolean
	{
		return false;
	}
	
	public function get mouseY():Number
	{
		return 0;
	}
	
	public function set initialized(value:Boolean):void
	{
	}
	
	public function styleChanged(styleProp:String):void
	{
	}
	
	public function get rotation():Number
	{
		return 0;
	}
	
	public function set rotation(value:Number):void
	{
	}
	
	public function get alpha():Number
	{
		return 0;
	}
	
	public function set alpha(value:Number):void
	{
	}
	
	public function get measuredWidth():Number
	{
		return 0;
	}
	
	public function get width():Number
	{
		return 0;
	}
	
	public function set width(value:Number):void
	{
	}
	
	public function get enabled():Boolean
	{
		return false;
	}
	
	public function get height():Number
	{
		return 0;
	}
	
	public function set enabled(value:Boolean):void
	{
	}
	
	public function set height(value:Number):void
	{
	}
	
	public function get data():Object
	{
		return null;
	}
	
	public function set data(value:Object):void
	{
	}
	
	public function get cacheAsBitmap():Boolean
	{
		return false;
	}
	
	public function set cacheAsBitmap(value:Boolean):void
	{
	}
	
	public function move(x:Number, y:Number):void
	{
	}
	
	public function get opaqueBackground():Object
	{
		return null;
	}
	
	public function set opaqueBackground(value:Object):void
	{
	}
	
	public function get explicitHeight():Number
	{
		return 0;
	}
	
	public function get scrollRect():Rectangle
	{
		return null;
	}
	
	public function set explicitHeight(value:Number):void
	{
	}
	
	public function set scrollRect(value:Rectangle):void
	{
	}
	
	public function get filters():Array
	{
		return null;
	}
	
	public function set filters(value:Array):void
	{
	}
	
	public function get nestLevel():int
	{
		return 0;
	}
	
	public function set nestLevel(value:int):void
	{
	}
	
	public function get blendMode():String
	{
		return null;
	}
	
	public function set blendMode(value:String):void
	{
	}
	
	public function get explicitMaxHeight():Number
	{
		return 0;
	}
	
	public function get transform():Transform
	{
		return null;
	}
	
	public function get processedDescriptors():Boolean
	{
		return false;
	}
	
	public function set transform(value:Transform):void
	{
	}
	
	public function setActualSize(newWidth:Number, newHeight:Number):void
	{
	}
	
	public function set processedDescriptors(value:Boolean):void
	{
	}
	
	public function get scale9Grid():Rectangle
	{
		return null;
	}
	
	public function set scale9Grid(innerRectangle:Rectangle):void
	{
	}
	
	public function get explicitMaxWidth():Number
	{
		return 0;
	}
	
	public function globalToLocal(point:Point):Point
	{
		return null;
	}
	
	public function get updateCompletePendingFlag():Boolean
	{
		return false;
	}
	
	public function localToGlobal(point:Point):Point
	{
		return null;
	}
	
	public function set updateCompletePendingFlag(value:Boolean):void
	{
	}
	
	public function getBounds(targetCoordinateSpace:DisplayObject):Rectangle
	{
		return null;
	}
	
	public function get explicitMinHeight():Number
	{
		return 0;
	}
	
	public function getRect(targetCoordinateSpace:DisplayObject):Rectangle
	{
		return null;
	}
	
	public function get loaderInfo():LoaderInfo
	{
		return null;
	}
	
	public function get explicitMinWidth():Number
	{
		return 0;
	}
	
	public function hitTestObject(obj:DisplayObject):Boolean
	{
		return false;
	}
	
	public function validateProperties():void
	{
	}
	
	public function hitTestPoint(x:Number, y:Number, shapeFlag:Boolean=false):Boolean
	{
		return false;
	}
	
	public function get accessibilityProperties():AccessibilityProperties
	{
		return null;
	}
	
	public function set accessibilityProperties(value:AccessibilityProperties):void
	{
	}
	
	public function get explicitWidth():Number
	{
		return 0;
	}
	
	public function set explicitWidth(value:Number):void
	{
	}
	
	public function validateSize(recursive:Boolean=false):void
	{
	}
	
	public function validateDisplayList():void
	{
	}
	
	public function get focusPane():Sprite
	{
		return null;
	}
	
	public function set focusPane(value:Sprite):void
	{
	}
	
	public function get includeInLayout():Boolean
	{
		return false;
	}
	
	public function set includeInLayout(value:Boolean):void
	{
	}
	
	public function get isPopUp():Boolean
	{
		return false;
	}
	
	public function addEventListener(type:String, listener:Function, useCapture:Boolean=false, priority:int=0, useWeakReference:Boolean=false):void
	{
	}
	
	public function set isPopUp(value:Boolean):void
	{
	}
	
	public function get maxHeight():Number
	{
		return 0;
	}
	
	public function get maxWidth():Number
	{
		return 0;
	}
	
	public function get measuredMinHeight():Number
	{
		return 0;
	}
	
	public function removeEventListener(type:String, listener:Function, useCapture:Boolean=false):void
	{
	}
	
	public function set measuredMinHeight(value:Number):void
	{
	}
	
	public function get measuredMinWidth():Number
	{
		return 0;
	}
	
	public function set measuredMinWidth(value:Number):void
	{
	}
	
	public function dispatchEvent(event:Event):Boolean
	{
		return false;
	}
	
	public function get minHeight():Number
	{
		return 0;
	}
	
	public function get minWidth():Number
	{
		return 0;
	}
	
	public function hasEventListener(type:String):Boolean
	{
		return false;
	}
	
	public function get owner():DisplayObjectContainer
	{
		return null;
	}
	
	public function set owner(value:DisplayObjectContainer):void
	{
	}
	
	public function get percentHeight():Number
	{
		return 0;
	}
	
	public function set percentHeight(value:Number):void
	{
	}
	
	public function willTrigger(type:String):Boolean
	{
		return false;
	}
	
	public function get percentWidth():Number
	{
		return 0;
	}
	
	public function set percentWidth(value:Number):void
	{
	}
	
	public function get systemManager():ISystemManager
	{
		return null;
	}
	
	public function set systemManager(value:ISystemManager):void
	{
	}
	
	public function get tweeningProperties():Array
	{
		return null;
	}
	
	public function set tweeningProperties(value:Array):void
	{
	}
	
	public function initialize():void
	{
	}
	
	public function parentChanged(p:DisplayObjectContainer):void
	{
	}
	
	public function getExplicitOrMeasuredWidth():Number
	{
		return 0;
	}
	
	public function getExplicitOrMeasuredHeight():Number
	{
		return 0;
	}
	
	public function setVisible(value:Boolean, noEvent:Boolean=false):void
	{
	}
	
	public function owns(displayObject:DisplayObject):Boolean
	{
		return false;
	}
	*/
}
}