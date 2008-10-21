package code.model.dao {

import code.model.vo.Clip;
import code.model.vo.Folder;
import code.model.vo.Item;
import code.model.vo.Library;
import code.model.vo.Playlist;

import logging.Logger;

import mx.collections.ArrayCollection;
	

// FIXME original order will be lost!
internal class LibraryDao extends AbstractDao implements ILibraryDao {
	
	private static const LOG:Logger = Logger.getLogger("code.model.dao.LibraryDao");
	
	private static const TBL_ITEM: TableDefinition = new TableDefinition("item",
		"itemId INTEGER PRIMARY KEY AUTOINCREMENT, " +
		"title CHAR(255), " +
		"parentId INTEGER ", // reference to (parent) folder; -1 if top element,
		"INSERT INTO item (title, parentId) VALUES(:title, :parentId)",
		"SELECT itemId, title, parentId FROM item ORDER BY itemId ASC;"
	);
	
	private static const TBL_FOLDER: TableDefinition = new TableDefinition("folder",
		"folderId INTEGER PRIMARY KEY AUTOINCREMENT, " +
		"itemId INTEGER",
		"INSERT INTO folder (itemId) VALUES(:itemId)",
		"SELECT folderId, itemId FROM folder ORDER BY folderId ASC;"
	);	
	
	private static const TBL_PLAYLIST: TableDefinition = new TableDefinition("playlist",
		"playlistId INTEGER PRIMARY KEY AUTOINCREMENT, " +
		"itemId INTEGER",
		"INSERT INTO playlist (itemId) VALUES(:itemId)",
		"SELECT playlistId, itemId FROM playlist ORDER BY playlistId ASC;"
	);	
	
	private static const TBL_CLIP: TableDefinition = new TableDefinition("clip",
		"clipId INTEGER PRIMARY KEY AUTOINCREMENT, " +
		"videoId CHAR(255)," +
		"title CHAR(255)," +
		"duration INTEGER," +
		// FIXME thumbnail => also store locally
		"playlistId INTEGER",
		"INSERT INTO clip (videoId, title, duration, playlistId) VALUES " + 
			           "(:videoId, :title, :duration, :playlistId)",
		"SELECT clipId, videoId, title, duration, playlistId FROM clip ORDER BY videoId ASC;"
	);	
	
	
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
		this.execSql(TBL_ITEM.sqlCreate);
		this.execSql(TBL_FOLDER.sqlCreate);
		this.execSql(TBL_PLAYLIST.sqlCreate);
		this.execSql(TBL_CLIP.sqlCreate);
	}
	
	
	/**
	 * @interface
	 * @param fnFetchedLibrary ... fnFetchedLibrary(library: Library): void
	 */
	public function fetchLibrary(fnFetchedLibrary: Function): void {
		LOG.info("fetchLibrary(fnFetchedLibrary="+fnFetchedLibrary+") invoked");
		this.fnFetchedLibrary = fnFetchedLibrary;
		this.execSql(TBL_ITEM.sqlSelect, this.onFetch1Items);
	}
	
	private function onFetch1Items(result: ArrayCollection): void {
		this.tmpFetchedItems = new Array();
		this.tmpFetchedParentals = new Array();
		
		for each(var item: Object in result) {
			// item.itemId, item.title, item.parentId
			this.tmpFetchedItems[item.itemId] = item;
			if(this.tmpFetchedParentals[item.parentId] == null) {
				this.tmpFetchedParentals[item.parentId] = new ArrayCollection();
			}
			this.tmpFetchedParentals[item.parentId].addItem(item.itemId);
		}
		
		this.execSql(TBL_FOLDER.sqlSelect, this.onFetch2Folders);
	}
	
	private function onFetch2Folders(result: ArrayCollection): void {
		this.tmpFetchedFolders = new Array();
		
		for each(var folder: Object in result) {
			// folder.folderId, folder.itemId
			this.tmpFetchedFolders[folder.itemId] = folder;
		}

		this.execSql(TBL_PLAYLIST.sqlSelect, this.onFetch3Playlists);
	}
	
	private function onFetch3Playlists(result: ArrayCollection): void {
		this.tmpFetchedPlaylists = new Array();
		
		for each(var playlist: Object in result) {
			// playlistId, itemId
			this.tmpFetchedPlaylists[playlist.itemId] = playlist;
		}
		
		this.onFetch4Finish();
	}
	
	private function onFetch4Finish(): void {
		const rootFolder: Folder = Folder.newDefault();
		
		// Array<K ... itemId, V ... folderObject>
		const foldersByItemId: Array = new Array();
		
		for (var itemFolderId: String in this.tmpFetchedFolders) {
			var objFolderItem: Object = this.tmpFetchedItems[itemFolderId]; // Object { itemId, title, parentId }
			var objFolder: Object = this.tmpFetchedFolders[itemFolderId]; // Object { folderId, itemId }
			
			if(objFolderItem.parentId != DEFAULT_PARENT_NON_ID) {
				continue; // skip, if its not directly below root
			}
			
			// add folder in rootFolder
			const newFolder: Folder = Folder.newDefault();
			
			//  TODO newFolder.content
			newFolder.folderId = objFolder.folderId;
			newFolder.itemId = objFolderItem.itemId; // or objFolder.id_item
			newFolder.parentId = objFolderItem.parentId;
			newFolder.title = objFolderItem.title;
			
			rootFolder.content.addItem(newFolder);
			foldersByItemId[newFolder.itemId] = newFolder;
		}
		
		
		for (var itemPlaylistId: String in this.tmpFetchedPlaylists) {
			const objPlaylistItem: Object = this.tmpFetchedItems[itemPlaylistId]; // Object { itemId, title, parentId }
			const objPlaylist: Object = this.tmpFetchedPlaylists[itemPlaylistId]; // Object { playlistId, itemId }
			
			if(objPlaylistItem.parentId != DEFAULT_PARENT_NON_ID) {
				continue; // skip, if its not directly below root
			}
			
			const newPlaylist: Playlist = Playlist.newDefault();
			newPlaylist.itemId = objPlaylistItem.itemId; // or objPlaylist.itemId
			newPlaylist.parentId = objPlaylistItem.parentId;
			newPlaylist.playlistId = objPlaylist.playlistId;
			newPlaylist.title = objPlaylistItem.title;
			// TODO newPlaylist.clips
			
			rootFolder.content.addItem(newPlaylist);
		}
		
		if(rootFolder.content.length == 0) {
			LOG.finer("Seems as nothing was stored in DB (rootFolder.content.length); inserting dummy.");
			rootFolder.content.addItem(Playlist.newDefault());
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
			this.execSql(TBL_FOLDER.sqlInsert, null, params); // FIXME async call?!?!
		
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
		this.execSql(TBL_PLAYLIST.sqlInsert, null, params);
		
		const lastPlaylistId: int = this.lastInsertId;
		for each(var clip: Clip in savePlaylist.clips) {
			this.insertClip(clip, savePlaylist);
		}
	}
	
	private function insertClip(saveClip: Clip, playlist: Playlist): void {
		var params: Array = new Array();
		params[":videoId"] = saveClip.videoId;
		params[":title"] = saveClip.title;
		params[":duration"] = saveClip.duration;
		params[":playlistId"] = playlist.playlistId;
		this.execSql(TBL_CLIP.sqlInsert, null, params);
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
		
		this.execSql(TBL_ITEM.sqlInsert, null, params);
	}
	
	
	/**
	 * @interface
	 */
	public function clearLibrary(): void {
		LOG.info("clearLibrary() invoked");
		this.execSql(TBL_CLIP.sqlDelete, null, null);
		this.execSql(TBL_FOLDER.sqlDelete, null, null);
		this.execSql(TBL_PLAYLIST.sqlDelete, null, null);
		this.execSql(TBL_ITEM.sqlDelete, null, null);
	}
	
}
}