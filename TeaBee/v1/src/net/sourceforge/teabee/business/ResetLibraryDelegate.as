package net.sourceforge.teabee.business {

import net.sourceforge.teabee.dao.LibraryDao;
import net.sourceforge.teabee.model.Model;
import net.sourceforge.teabee.valueobject.Library;
	

public class ResetLibraryDelegate {
	public function ResetLibraryDelegate() {
	}

	public static function reset():void {
		LibraryDao.instance.clear();
		
		
		/*
		FIXME funktioniert noch nicht, weil tree komische art&weise pseudo-binding mittels listener hat
		!!! ueberhaupt diese delegate.listeners weg tun, ist gar nicht gut !!!
		*/
		const model:Model = Model.instance;
		model.library = Library.newDefault();
		model.selectedPlaylist = null;
		model.selectedClip = null;
		
		
		TreeChangeProvider.instance.broadcastDidReset();
	}

}
}