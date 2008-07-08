package net.sourceforge.jpotpourri.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import net.sourceforge.jpotpourri.TestProperties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author christoph.pickl@bmi.gv.at
 */
public class FileUtilTest extends AbstractUtilTestCase {

	private static final Log LOG = LogFactory.getLog(FileUtilTest.class);
	
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

//	public final void testCopyFile() {
//		fail("Not yet implemented");
//	}
//
//	public final void testCopyDirectoryRecursive() {
//		fail("Not yet implemented");
//	}
//
//	public final void testFormatFileSize() {
//		fail("Not yet implemented");
//	}
//
//	public final void testFormatFileSizeGb() {
//		fail("Not yet implemented");
//	}
//
//	public final void testGetGigaByteFromKiloByte() {
//		fail("Not yet implemented");
//	}

	public final void testDeleteDirectoryRecursive() throws Exception {
		final String testRootPath = TestProperties.getInstance().getTestRootPath();
		final File testRootFile = new File(testRootPath);
		if(testRootFile.exists() == false) {
			LOG.debug("Creating test rootfolder: " + testRootPath);
			final boolean created = testRootFile.mkdirs();
			if(created == false) {
				throw new RuntimeException("Could not create test rootfolder [" + testRootPath + "]!");
			}
		}
		final int expectedFileCount = testRootFile.listFiles().length;
		
		final File dir2DeleteParent = new File(testRootFile, "dir2DeleteParent");
		final File dir2DeleteSub = new File(dir2DeleteParent, "dir2DeleteSub");
		if(dir2DeleteSub.mkdirs() == false) {
			throw new RuntimeException("Could not create directory [" + dir2DeleteSub.getAbsolutePath() + "]!");
		}
		
		final BufferedWriter writer1 = new BufferedWriter(new FileWriter(new File(dir2DeleteParent, "file1_delme")));
		try {
			writer1.write("abcd");
		} finally {
			CloseUtil.close(writer1);
		}
		
		final BufferedWriter writer2 = new BufferedWriter(new FileWriter(new File(dir2DeleteSub, "file2_delme")));
		try {
			writer2.write("efgh");
		} finally {
			CloseUtil.close(writer2);
		}
		
		FileUtil.deleteDirectoryRecursive(dir2DeleteParent);
		
		assertEquals(expectedFileCount, testRootFile.listFiles().length);
		
		try {
			FileUtil.deleteDirectoryRecursive(dir2DeleteParent);
			fail("Should have thrown exception");
		} catch (IllegalArgumentException e) {
			// should fail, because directory is already delete
			assertTrue(true);
		}
	}
//	public final void testIsHiddenFile() {
//		fail("Not yet implemented");
//	}
//
//	public final void testGetFileContentsFromJar() {
//		fail("Not yet implemented");
//	}
//
//	public final void testExtractLastFolderName() {
//		fail("Not yet implemented");
//	}
//
//	public final void testGetSizeRecursive() {
//		fail("Not yet implemented");
//	}
//
//	public final void testGetParentByPath() {
//		fail("Not yet implemented");
//	}

}
