package net.sourceforge.jpotpourri.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import net.sourceforge.jpotpourri.TestProperties;

/**
 * @author christoph.pickl@bmi.gv.at
 */
public class FileUtilTest extends AbstractUtilTestCase {
	
	public final void testExtractExtensionFile() {
		assertEquals("xml", FileUtil.extractExtension(new File("asdf.xml")));
		assertEquals("xml", FileUtil.extractExtension(new File("/somewhere/asdf.xml")));

		assertEquals("asd", FileUtil.extractExtension(new File("asdf.xml.asd")));
		assertEquals("asd", FileUtil.extractExtension(new File("/somewhere/asdf.xml.asd")));

		assertNull(FileUtil.extractExtension(new File("asdf")));
		assertNull(FileUtil.extractExtension(new File("/somewhere/asdf")));
	}

	public final void testExtractExtensionString() {
		assertEquals("xml", FileUtil.extractExtension("asdf.xml"));
		assertEquals("xml", FileUtil.extractExtension("/somewhere/asdf.xml"));

		assertEquals("asd", FileUtil.extractExtension("asdf.xml.asd"));
		assertEquals("asd", FileUtil.extractExtension("/somewhere/asdf.xml.asd"));
		
		assertNull(FileUtil.extractExtension("asdf"));
		assertNull(FileUtil.extractExtension("/somewhere/asdf"));
	}

	public final void testCopyFile() {
		fail("Not yet implemented");
	}

	public final void testCopyDirectoryRecursive() {
		fail("Not yet implemented");
	}

	public final void testFormatFileSize() {
		fail("Not yet implemented");
	}

	public final void testFormatFileSizeGb() {
		fail("Not yet implemented");
	}

	public final void testGetGigaByteFromKiloByte() {
		fail("Not yet implemented");
	}

	public final void testDeleteDirectoryRecursive() throws Exception {
		final File parent = TestProperties.getInstance().getFolderTestRoot();
		
		final int expectedFileCount = parent.listFiles().length;
		
		final File dir1Delete = new File(parent, "folder1_delme");
		final File dir2Delete = new File(dir1Delete, "folder2_delme");
		if(dir2Delete.mkdirs() == false) {
			throw new RuntimeException("Could not create directory structure [" + dir2Delete.getAbsolutePath() + "]!");
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(dir1Delete, "file1_delme")));
		writer.write("abcd");
		writer.close();
		writer = new BufferedWriter(new FileWriter(new File(dir2Delete, "file2_delme")));
		writer.write("efgh");
		writer.close();
		
		FileUtil.deleteDirectoryRecursive(dir1Delete);
		
		assertEquals(expectedFileCount, parent.listFiles().length);
		
		try {
			FileUtil.deleteDirectoryRecursive(dir1Delete);
			fail("Should have thrown exception");
		} catch(IllegalArgumentException e) {
			// should fail, because directory is already delete
			assertTrue(true);
		}
	}
	public final void testIsHiddenFile() {
		fail("Not yet implemented");
	}

	public final void testGetFileContentsFromJar() {
		fail("Not yet implemented");
	}

	public final void testExtractLastFolderName() {
		fail("Not yet implemented");
	}

	public final void testGetSizeRecursive() {
		fail("Not yet implemented");
	}

	public final void testGetParentByPath() {
		fail("Not yet implemented");
	}

}
