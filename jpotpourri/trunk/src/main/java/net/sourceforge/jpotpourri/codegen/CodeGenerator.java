package net.sourceforge.jpotpourri.codegen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import net.sourceforge.jpotpourri.util.CloseUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
class CodeGenerator implements ICodeGenerator {

	private static final Log LOG = LogFactory.getLog(CodeGenerator.class);

	private static final boolean DEBUG = false;
	
	private final File sourceFolder;
	
	public CodeGenerator(final File sourceFolder) {
		this.sourceFolder = sourceFolder;
	}
	
	public void process(final AbstractGenClass genClass) throws CodeGeneratorException {
		if(DEBUG == true) {
			System.out.println("==========================");
			System.out.println(genClass.toCode());
			System.out.println("==========================");
			return;
		}
		
		this.processClass(genClass, false);
		
		if(genClass.isGenerateManClass() == true) {
			this.processClass(AbstractGenClass.newManClass(genClass), true);
		}
		LOG.info("Finished generating code.");
	}
	
	private void processClass(final AbstractGenClass clazz, final boolean isManClass) throws CodeGeneratorException {
		final File targetFile = this.getTargetFile(clazz.getPackageName(), clazz.getClassName());
		
		if(isManClass == false) { // only do for genClasses
			checkFileAndDirectory(targetFile);
		} else {
			assert(targetFile.getParentFile().exists() == true);
		}
		
		if(isManClass == false || (isManClass == true && targetFile.exists() == false)) {
			// only write if genClass, or manClass is not yet existing
			writeCodeToFile(clazz.toCode(), targetFile);
		}
	}
	
	private static void checkFileAndDirectory(final File targetFile) throws CodeGeneratorException {
		if(targetFile.exists() == true) {
			LOG.info("Deleting existing genFile at [" + targetFile.getAbsolutePath() + "].");
			if(targetFile.delete() == false) {
				throw new CodeGeneratorException("Could not delete target file at " +
						"[" + targetFile.getAbsolutePath() + "]!");
			}
		}
		
		final File targetDirectory = targetFile.getParentFile();
		if(targetDirectory.exists() == false) {
			LOG.info("Creating target directory: " + targetDirectory.getAbsolutePath());
			if(targetDirectory.mkdirs() == false) {
				throw new CodeGeneratorException("Could not create directories for " +
						"[" + targetDirectory.getAbsolutePath() + "]!");
			}
		}
	}
	
	private static void writeCodeToFile(final String code, final File file) throws CodeGeneratorException {
		LOG.debug("Writing code to file: " + file.getAbsolutePath());
		assert(file.isFile());
		assert(file.exists() == false);
		
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(code);
		} catch(IOException e) {
			throw new CodeGeneratorException("Could not write code to file [" + file.getAbsolutePath() + "]!", e);
		} finally {
			CloseUtil.close(writer);
		}
	}
	
	
	private File getTargetFile(final String packageName, final String className) {
		final String packageFolder = packageName.replaceAll("\\.", "/");
		final File folder = new File(this.sourceFolder, packageFolder);
		final String fileName = className + ".java";
		return new File(folder, fileName);
	}
}
