package net.sourceforge.jpotpourri.tools;

import java.io.File;
import java.io.FileFilter;

public class FileFilters {

	public static final FileFilter FILE_ONLY_FILTER = new FileFilter() {
		public boolean accept(final File file) {
			return file.isFile();
		}
	};
	
}
