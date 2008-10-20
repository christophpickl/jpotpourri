package code.model.dao {

import code.model.vo.Folder;
import code.model.vo.Item;
import code.model.vo.Library;
import code.model.vo.Playlist;

import logging.Logger;

import mx.collections.ArrayCollection;
	

// FIXME original order will be lost!
internal class LibraryDao extends AbstractDao implements ILibraryDao {
	
	private static const LOG:Logger = Logger.getLogger("code.model.dao.LibraryDao");
	
	/*
	private static const SQL_CREATE_LIBRARY: String = 
		"CREATE TABLE IF NOT EXISTS library (" +
		  "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
		  "title CHAR(255)" +
		")";
	*/
	
	private static const SQL_CREATE_ITEM: String = 
		"CREATE TABLE IF NOT EXISTS item (" +
		  "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
		  "title CHAR(255), " +
		  "id_parent INTEGER " + // reference to (parent) folder; -1 if top element
		")";
	
	private static const SQL_CREATE_FOLDER: String = 
		"CREATE TABLE IF NOT EXISTS folder (" +
		  "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
		  "id_item INTEGER" +
		")";
	
	private static const SQL_CREATE_PLAYLIST: String = 
		"CREATE TABLE IF NOT EXISTS playlist (" +
		  "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
		  "id_item INTEGER" +
		")";
		
	
	private static const SQL_INSERT_ITEM: String =
		"INSERT INTO item (title, id_parent) VALUES(:title, :parentId)";
		
	private static const SQL_INSERT_FOLDER: String =
		"INSERT INTO folder (id_item) VALUES(:itemId)";
		
	private static const SQL_INSERT_PLAYLIST: String =
		"INSERT INTO playlist (id_item) VALUES(:itemId)";
		
		
		
	private static const SQL_SELECT_ITEMS: String =
		"SELECT id, title, id_parent FROM item ORDER BY id ASC;";
		
	private static const SQL_SELECT_FOLDERS: String =
		"SELECT id, id_item FROM folder ORDER BY id ASC;";
		
	private static const SQL_SELECT_PLAYLISTS: String =
		"SELECT id, id_item FROM playlist ORDER BY id ASC;";
	
	
	private static const DEFAULT_PARENT_NON_ID: int = -1;
	
	

	
	/**
	 * @temporary
	 * lambda(library:Library): void
	 **/
	private var fnFetchedLibrary: Function;

	/**
	 * @temporary
	 * Array<K, V>
	 * 	K ... Item.itemId, "parentId"
	 *  V ... ArrayCollection<Object { item_id }>
	 * @see used to map many sub-items to a parent-folder grouped by their itemIDs, see tmpFetchedXy
	 **/
	 private var tmpFetchedParentals: Array;
	
	/**
	 * @temporary
	 * Array<K, V>
	 * 	K ... Item.itemId
	 *  V ... Object { id, title, id_parent }
	 **/
	 private var tmpFetchedItems: Array;
	 
	/**
	 * @temporary
	 * Array<K, V>
	 * 	K ... Item.itemId
	 *  V ... Object { id, id_item }
	 **/
	 private var tmpFetchedFolders: Array;
	 
	/**
	 * @temporary
	 * Array<K, V>
	 * 	K ... Item.itemId
	 *  V ... Object { id, id_item }
	 **/
	 private var tmpFetchedPlaylists: Array;
	
	
	
	public function LibraryDao() {
		this.execSql(SQL_CREATE_ITEM);
		this.execSql(SQL_CREATE_FOLDER);
		this.execSql(SQL_CREATE_PLAYLIST);
	}
	
	
	/**
	 * @interface
	 */
	public function fetchLibrary(fnFetchedLibrary: Function): void {
		this.fnFetchedLibrary = fnFetchedLibrary;
		this.execSql(SQL_SELECT_ITEMS, this.onFetch1Items);
	}
	
	private function onFetch1Items(result: ArrayCollection): void {
		this.tmpFetchedItems = new Array();
		this.tmpFetchedParentals = new Array();
		
		for each(var item: Object in result) {
			// item.id        ==> Item.itemId
			// item.title     ==> Item.title
			// item.id_parent ==> Item.parentId
			this.tmpFetchedItems[item.id] = item;
			if(this.tmpFetchedParentals[item.id_parent] == null) {
				this.tmpFetchedParentals[item.id_parent] = new ArrayCollection();
			}
			this.tmpFetchedParentals[item.id_parent].addItem(item.id);
		}
		
		this.execSql(SQL_SELECT_FOLDERS, this.onFetch2Folders);
	}
	
	private function onFetch2Folders(result: ArrayCollection): void {
		this.tmpFetchedFolders = new Array();
		
		for each(var folder: Object in result) {
			// folder.id      ==> Folder.folderId
			// folder.id_item ==> Item.itemId ... only needed to hold reference
			this.tmpFetchedFolders[folder.id_item] = folder;
		}

		this.execSql(SQL_SELECT_PLAYLISTS, this.onFetch3Playlists);
	}
	
	private function onFetch3Playlists(result: ArrayCollection): void {
		this.tmpFetchedPlaylists = new Array();
		
		for each(var playlist: Object in result) {
			// id, id_item
			this.tmpFetchedPlaylists[playlist.id_item] = playlist;
		}
		
		this.onFetch4Finish();
	}
	
	private function onFetch4Finish(): void {
		const rootFolder: Folder = Folder.newDefault();
		
		// Array<K ... itemId, V ... folderObject>
		const foldersByItemId: Array = new Array();
		
		for (var itemFolderId: String in this.tmpFetchedFolders) {
			var objFolderItem: Object = this.tmpFetchedItems[itemFolderId]; // Object { id, title, id_parent }
			var objFolder: Object = this.tmpFetchedFolders[itemFolderId]; // Object { id, id_item }
			
			if(objFolderItem.id_parent != DEFAULT_PARENT_NON_ID) {
				continue; // skip, if its not directly below root
			}
			
			// add folder in rootFolder
			const newFolder: Folder = Folder.newDefault();
			
			//  TODO newFolder.content
			newFolder.folderId = objFolder.id;
			newFolder.itemId = objFolderItem.id; // or objFolder.id_item
			newFolder.parentId = objFolderItem.id_parent;
			newFolder.title = objFolderItem.title;
			
			rootFolder.content.addItem(newFolder);
			foldersByItemId[newFolder.itemId] = newFolder;
		}
		
		
		for (var itemPlaylistId: String in this.tmpFetchedPlaylists) {
			const objPlaylistItem: Object = this.tmpFetchedItems[itemPlaylistId]; // Object { id, title, id_parent }
			const objPlaylist: Object = this.tmpFetchedPlaylists[itemPlaylistId]; // Object { id, id_item }
			
			if(objPlaylistItem.id_parent != DEFAULT_PARENT_NON_ID) {
				continue; // skip, if its not directly below root
			}
			
			const newPlaylist: Playlist = Playlist.newDefault();
			newPlaylist.itemId = objPlaylistItem.id; // or objPlaylist.id_item
			newPlaylist.parentId = objPlaylistItem.id_parent;
			newPlaylist.playlistId = objPlaylist.id;
			newPlaylist.title = objPlaylistItem.title;
			// TODO newPlaylist.clips
			
			rootFolder.content.addItem(newPlaylist);
		}
		
		const library: Library = new Library(rootFolder);
		
		this.tmpFetchedItems = null;
		this.tmpFetchedFolders = null;
		this.tmpFetchedPlaylists = null;
		
		this.fnFetchedLibrary(library);
	}
	
	
	
	
	

	/**
	 * @interface
	 */
	public function insertLibrary(library: Library): void {
		LOG.info("insertLibrary(library="+library+")");
		this.insertFolder(library.rootFolder, null); // initial root-folder got parent-folder==null
	}
	
	private function insertFolder(saveFolder: Folder, parentFolder: Folder): void {
		LOG.finer("insertFolder(saveFolder="+saveFolder+";parentFolder="+parentFolder+")");
		
		if(parentFolder == null) {
			LOG.fine("ignore inserting rootfolder.");
			saveFolder.itemId = DEFAULT_PARENT_NON_ID;
		} else if(parentFolder != null) { // ignore rootfolder itself
			// insert item
			this.insertItem(saveFolder, parentFolder);
			const lastItemId: int = this.lastInsertId;
			
			// insert folder
			var params: Array = new Array();
			params[":itemId"] = lastItemId;
			this.execSql(SQL_INSERT_FOLDER, null, params); // FIXME async call?!?!
		
			// update saveFolder.parentId
			const lastFolderId: int = this.lastInsertId;
			LOG.finest("setting saveFolder's parentId from ["+saveFolder.parentId+"] to ["+lastFolderId+"].");
			saveFolder.parentId = lastFolderId;
		}
		
		
		// insert sub-objects
		for each(var object: Object in saveFolder.content) {
			LOG.finest("inserting subitem for folder ["+saveFolder.title+"] ==> " + object);
			if(object is Folder) { // ... is a folder
				const folder: Folder = object as Folder;
				this.insertFolder(folder, saveFolder);
				
			} else if(object is Playlist) { // ... is a playlist
				const playlist: Playlist = object as Playlist;
				this.insertPlaylist(playlist, saveFolder);
				
			} else {
				throw new Error("Unhandled folder content [" + object + "]!");
			}
		}
	}
	
	private function insertPlaylist(savePlaylist: Playlist, parentFolder: Folder): void {
		LOG.finer("insertPlaylist(savePlaylist="+savePlaylist+";parentFolder="+parentFolder+")");
		
		// insert item
		this.insertItem(savePlaylist, parentFolder);
		const lastItemId: int = this.lastInsertId;
		
		// insert playlist
		var params: Array = new Array();
		params[":itemId"] = lastItemId;
		this.execSql(SQL_INSERT_PLAYLIST, null, params);
	}
	
	private function insertItem(saveItem: Item, parentFolder: Folder): void {
		LOG.finer("insertItem(saveItem="+saveItem+";parentFolder="+parentFolder+")");
		
		var params: Array = new Array();
		params[":title"] = saveItem.title;
		if(parentFolder == null) {
			params[":parentId"] = DEFAULT_PARENT_NON_ID; // we are adding content to root-folder
		} else {
			params[":parentId"] = parentFolder.itemId;
		}
		
		this.execSql(SQL_INSERT_ITEM, null, params);
	}
	
}
}