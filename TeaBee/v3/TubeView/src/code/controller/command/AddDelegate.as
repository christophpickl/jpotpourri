package code.controller.command {

import code.common.Model;
import code.model.vo.Folder;

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

}
}