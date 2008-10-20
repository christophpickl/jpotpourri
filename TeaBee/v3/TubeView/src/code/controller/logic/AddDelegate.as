package code.controller.logic {

import code.model.Model;
import code.model.vo.Folder;
import code.model.vo.Clip;

import logging.Logger;
	

public class AddDelegate {
	
	private static const LOG: Logger = Logger.getLogger("code.command.AddDelegate");
	
	public function AddDelegate() {
	}
	
	/**
	 * @param object either of type Playlist or Folder
	 * @param targetFolder
	 */
	public static function addItem(playlistOrFolder: Object, targetFolder: Folder=null): void {
		if(targetFolder == null) {
			targetFolder = Model.instance.library.rootFolder;
		}
		
		LOG.fine("adding item ["+playlistOrFolder.title+"] to target folder ["+targetFolder.title+"]");
		targetFolder.content.addItem(playlistOrFolder);
	}
	
	public static function addSearchResultToPlaylist(newClip: Clip): void {
		new VideoDownloader(newClip).doDownload();
		Model.instance.playlist.clips.addItem(newClip); // TODO do not only add it at last position, but on proper dropped position!
		
	}

}
}