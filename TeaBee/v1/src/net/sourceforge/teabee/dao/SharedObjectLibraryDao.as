package net.sourceforge.teabee.dao {

import flash.net.SharedObject;

import logging.Logger;

import mx.collections.ArrayCollection;

import net.sourceforge.teabee.valueobject.Clip;
import net.sourceforge.teabee.valueobject.Folder;
import net.sourceforge.teabee.valueobject.Library;
import net.sourceforge.teabee.valueobject.Playlist;
import net.sourceforge.teabee.valueobject.Thumbnail;
import net.sourceforge.teabee.view.libtree.INode;
import net.sourceforge.teabee.view.libtree.INodeContainer;
	

internal class SharedObjectLibraryDao implements ILibraryDao {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.teabee.dao.SharedObjectLibraryDao");
	
	private var _shared:SharedObject;
	
	
	public function SharedObjectLibraryDao() {
		this._shared = SharedObject.getLocal("SharedObjectLibraryDao");
	}

	
	public function save(library:Library):void {
		LOG.info("save(" + library + ")");
		
		this._shared.data.library = serializeLibrary(library);
		this._shared.flush();
		
	}
	
	public function load():Library {
		LOG.info("load()");
		
		var serializedLibrary:Object = this._shared.data.library as Object;
		if(serializedLibrary == null) {
			LOG.fine("Creating new Library for first time.");
			return Library.newDefault();
		}
		
		return deserializeLibrary(serializedLibrary);
	}
	
	public function clear():void {
		LOG.info("clear()");
		this._shared.clear();
	}
	
	
	
	/* ****************************************************************************************************** */
	//    SERIALIZE
	/* ****************************************************************************************************** */
	
	private static function serializeLibrary(library:Library):Object {
		return serializeContainer(library);
	}
	
	private static function serializeContainer(container:INodeContainer):Object {
		var obj:Object = new Object();
		var arr:Array = new Array();
		
		if(container is Folder) {
			/*
			public var title:String;
			public var parent:INodeContainer; !!!!!!!!!!!!!! has to be set while deserializing !!!!!!!!!!!!!!!!!!!!!!
			public var content:ArrayCollection;
			*/
			obj.title = (container as Folder).title;
			obj.className = "Folder";
		} else if(container is Library) {
			obj.className = "Library";
		} else {
			throw new Error("Unhandled node container [" + container + "]!");
		}
		
		for each(var node:INode in container.children) {
			if(node is Playlist) {
				arr.push(serializePlaylist(node as Playlist));
			} else if(node is Folder) {
				arr.push(serializeContainer(node as Folder));
			} else {
				throw new Error("Unhandled library content node [" + node + "]!");
			}
		}
		
		obj.content = arr;
		
		return obj;
	}
	
	private static function serializePlaylist(playlist:Playlist):Object {
		var obj:Object = new Object();
		obj.className = "Playlist";
		/*
		public var title:String;
		public var parent:INodeContainer; !!!!!!!!!!!!!! has to be set while deserializing !!!!!!!!!!!!!!!!!!!!!!
		public var clips:ArrayCollection = new ArrayCollection();
		*/
		obj.title = playlist.title;
		obj.clips = serializeClips(playlist.clips);
		
		return obj;
	}
	
	private static function serializeClips(clips:ArrayCollection):Array {
		var arr:Array = new Array();
		for each (var clip:Clip in clips) {
			var obj:Object = new Object();
			obj.className = "Clip";
			/*
			public var title:String;
			public var url:String;
			public var duration:uint;
			public var thumbnail:Thumbnail;
			*/
			obj.title = clip.title;
			obj.url = clip.url;
			obj.duration = clip.duration;
			obj.thumbnail = serializeThumbnail(clip.thumbnail);
			arr.push(obj);
		}
		return arr;
	}
	
	private static function serializeThumbnail(tn:Thumbnail):Object {
		var obj:Object = new Object();
		obj.className = "Thumbnail";
		/*
		public var url:String;
		public var width:uint;
		public var height:uint;
		*/
		obj.url = tn.url;
		obj.width = tn.width;
		obj.height = tn.height;
		return obj;
	}
	
	
	
	/* ****************************************************************************************************** */
	//    DESERIALIZE
	/* ****************************************************************************************************** */
	
	private static function deserializeLibrary(serializedLibrary:Object):Library {
		if(serializedLibrary.className != "Library") {
			throw new Error("serializedLibrary.className != Library but [" + serializedLibrary.className + "]");
		}
		
		const library:Library = new Library(null);
		const content:ArrayCollection = deserializeContent(library, serializedLibrary.content as Array);
		library.content = content;
		
		if(content.length == 0) {
			content.addItem(Playlist.newDefault());
		}
		
		return library;
	}
	
	private static function deserializeContent(parentNode:INodeContainer, arr:Array):ArrayCollection {
		const content:ArrayCollection = new ArrayCollection();
		
		for each(var obj:Object in arr) {
			
			if(obj.className == "Folder") {
				content.addItem(deserializeFolder(parentNode, obj));
			} else if(obj.className == "Playlist") {
				content.addItem(deserializePlaylist(parentNode, obj));
			} else {
				throw new Error("Unkown object.className ["+obj.className+"]!");
			}
		}
		
		return content;
	}
	
	private static function deserializeFolder(parentNode:INodeContainer, obj:Object):Folder {
		const folder:Folder = new Folder(obj.title as String, parentNode);
		folder.content = deserializeContent(folder, obj.content as Array);
		return folder;
	}
	
	private static function deserializePlaylist(parentNode:INodeContainer, obj:Object):Playlist {
		const playlist:Playlist = new Playlist(obj.title as String, parentNode);
		playlist.clips = deserializeClips(obj.clips as Array);
		return playlist;
	}
	
	private static function deserializeClips(arr:Array):ArrayCollection {
		var clips:ArrayCollection = new ArrayCollection();
		
		for each (var obj:Object in arr) {
			/*
			public var title:String;
			public var url:String;
			public var duration:uint;
			public var thumbnail:Thumbnail;
			*/
			clips.addItem(new Clip(
				obj.title as String,
				obj.url as String,
				obj.duration as uint,
				deserializeThumbnail(obj.thumbnail)
			));
		}
		
		return clips;
	}
	
	private static function deserializeThumbnail(obj:Object):Thumbnail {
		/*
		public var url:String;
		public var width:uint;
		public var height:uint;
		*/
		return new Thumbnail(obj.url as String, obj.width as uint, obj.height as uint);
	}
	
}
}