package net.sourceforge.jpotpourri.learnme.command {

import flash.filesystem.File;

import logging.Logger;

import mx.collections.ArrayCollection;
import mx.controls.Alert;
import mx.events.CloseEvent;

import net.sourceforge.jpotpourri.learnme.FileOpener;
import net.sourceforge.jpotpourri.learnme.dao.DaoLocator;
import net.sourceforge.jpotpourri.learnme.vo.IQuestionCatalog;
import net.sourceforge.jpotpourri.learnme.xml.CatalogParser;
	

public class ImportCatalogCommand {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.jpotpourri.learnme.command.ImportCatalogCommand");
	
	private var currentCatalogRead: IQuestionCatalog;
	
	
	public function ImportCatalogCommand() {
	}

	public function execute():void {
		new FileOpener().openFile(File.desktopDirectory, onXmlContentRead);
	}
	
	private function onXmlContentRead(file: File, fileTextContent: String): void {
		this.currentCatalogRead = CatalogParser.parse(file, fileTextContent);
		LOG.finer("Parsed file content to catalog: " + this.currentCatalogRead);
		
		DaoLocator.instance.catalogDao.selectCatalogsByTitle(currentCatalogRead.title, onSimilarCatalogsReadFromDatabase);
	}
	
	private function onSimilarCatalogsReadFromDatabase(catalogsSameTitle: ArrayCollection): void {
		if(catalogsSameTitle.length > 0) {
			Alert.show("The catalog '"+this.currentCatalogRead.title+"' already exists!\nDo you want to import it anyway?", "Catalog already imported",
				Alert.YES | Alert.NO, null, onConfirmedImportExisting, null, Alert.NO);
		} else {
			this.finallyInsertCatalog();
		}
		
	}
	
	private function onConfirmedImportExisting(event: CloseEvent): void {
		if(event.detail == Alert.YES) {
			LOG.finer("inserting anyway current catalog");
			this.doImportWithNewTitle(this.currentCatalogRead.title + "-2");
		} else {
			this.currentCatalogRead = null;
		}
	}
	
	private function doImportWithNewTitle(newTitle: String): void {
		this.currentCatalogRead.title = newTitle;
		DaoLocator.instance.catalogDao.selectCatalogsByTitle(newTitle, onSimilarCatalogsReadFromDatabase2);
	}
	
	private function onSimilarCatalogsReadFromDatabase2(catalogsSameTitle: ArrayCollection): void {
		if(catalogsSameTitle.length > 0) {
			const oldTitle: String = this.currentCatalogRead.title;
			// assert oldTitle.{last-two-chars} == "-2" | "-3" | "-4" | ...
			var titleIndex: int = Number(oldTitle.substr(oldTitle.length - 1, oldTitle.length));
			var newTitle: String = oldTitle.substring(0, oldTitle.length - 1) + (titleIndex + 1);
			this.doImportWithNewTitle(newTitle);
		} else {
			this.finallyInsertCatalog();
		}
	}
	
	private function finallyInsertCatalog(): void {
		LOG.finer("finallyInsertCatalog with title ["+this.currentCatalogRead.title+"].");
		DaoLocator.instance.catalogDao.insertCatalog(this.currentCatalogRead);
		Alert.show("Successfully imported the catalog '"+this.currentCatalogRead.title+"'!", "Catalog Imported");
		this.currentCatalogRead = null;
	}
}
}