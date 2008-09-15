package net.sourceforge.jpotpourri.learnme.xml {

import flash.filesystem.File;
import flash.filesystem.FileMode;
import flash.filesystem.FileStream;
import flash.xml.XMLDocument;
import flash.xml.XMLNode;

import logging.Logger;

import mx.collections.ArrayCollection;

import net.sourceforge.jpotpourri.learnme.Util;
import net.sourceforge.jpotpourri.learnme.vo.IQuestionCatalog;
import net.sourceforge.jpotpourri.learnme.vo.MultipleChoiceSourceAnswer;
import net.sourceforge.jpotpourri.learnme.vo.MultipleChoiceSourceQuestion;
import net.sourceforge.jpotpourri.learnme.vo.QuestionCatalog;

public class CatalogParser {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.jpotpourri.learnme.xml.CatalogParser");
	
	public function CatalogParser() {
		// nothing to do
	}

	public static function parse(file: File, xmlString: String): IQuestionCatalog {
		var xmlDoc: XMLDocument = new XMLDocument();
		xmlDoc.parseXML(preprocessIncludes(file, xmlString));
		
		for each(var child: XMLNode in xmlDoc.childNodes) {
			if(child.nodeName == null) {
				continue; // skip empty text node stuff
			} else if(child.nodeName == "QuestionCatalog") {
				return parseCatalog(child);
			} else {
				throw new Error("invalid child.nodeName ["+child.nodeName+"]!");
			}
		}
		throw new Error("Invalid xml source!");
	}
	
	private static function preprocessIncludes(file: File, withIncludes: String): String {
		var includeIndexStart: int = -1;
		var includeIndexEnd: int = -1;
		
		var resolvedIncludes: String = withIncludes;
		
		includeIndexStart = withIncludes.indexOf("<Include>");
		includeIndexEnd = withIncludes.indexOf("</Include>");
		while(includeIndexStart != -1) {
			// trace("replacing at index ["+includeIndexStart+"-"+includeIndexEnd+"]");
			var includeFileName: String = resolvedIncludes.substr(includeIndexStart + 9, includeIndexEnd - includeIndexStart - 9);
			var includeFile: File = new File();
			includeFile.url = "file:///" + file.parent.nativePath + "/" + includeFileName;
			LOG.finer("includeFile: ["+includeFile.nativePath+"]");
			if(includeFile.exists == false) {
				throw new Error("Include file does not exist ["+includeFile.url+"]!");
			}
			
			var fs:FileStream = new FileStream();
			fs.open(includeFile, FileMode.READ);
			var includeFileContent: String = fs.readUTFBytes(fs.bytesAvailable);
			fs.close();
			
			resolvedIncludes = resolvedIncludes.replace(/<Include>[A-Za-z0-9_\/\.]*<\/Include>/, includeFileContent);
			
			includeIndexStart = resolvedIncludes.indexOf("<Include>");
			includeIndexEnd = resolvedIncludes.indexOf("</Include>");
		}
		return resolvedIncludes;
	}
	
	private static function parseCatalog(rootNode: XMLNode): IQuestionCatalog {
		var sourceQuestions: ArrayCollection = new ArrayCollection();
		var title: String = rootNode.attributes.title;
		
		for each(var child: XMLNode in rootNode.childNodes) {
			if(child.nodeName == null) {
				continue; // skip empty text node stuff
			} else if(child.nodeName == "MultipleChoiceQuestion") {
				sourceQuestions.addItem(parseMultipleChoiceQuestion(child));
			} else {
				throw new Error("invalid child.nodeName ["+child.nodeName+"]!");
			}
		}
		
		return new QuestionCatalog(-1, title, sourceQuestions);
	}
	
	private static function parseMultipleChoiceQuestion(rootNode: XMLNode): MultipleChoiceSourceQuestion {
		var title: String = rootNode.attributes.title;
		var text: String = null;
		var sourceAnswers: ArrayCollection = null;
		
		for each(var child: XMLNode in rootNode.childNodes) {
			if(child.nodeName == null) {
				continue; // skip empty text node stuff
			} else if(child.nodeName == "Text") {
				text = getNodeValue(child);
			} else if(child.nodeName == "Answers") {
				sourceAnswers = parseMultipleChoiceAnswers(child);
			} else {
				throw new Error("invalid child.nodeName ["+child.nodeName+"]!");
			}
		}
		
		
		return new MultipleChoiceSourceQuestion(-1, title, text, sourceAnswers);
	}
	
	private static function parseMultipleChoiceAnswers(rootNode: XMLNode): ArrayCollection {
		var sourceAnswers: ArrayCollection = new ArrayCollection();
		
		for each(var child: XMLNode in rootNode.childNodes) {
			if(child.nodeName == null) {
				continue; // skip empty text node stuff
			} else if(child.nodeName == "Answer") {
				sourceAnswers.addItem(parseMultipleChoiceAnswer(child));
			} else {
				throw new Error("invalid child.nodeName ["+child.nodeName+"]!");
			}
		}
		
		return sourceAnswers;
	}
	
	private static function parseMultipleChoiceAnswer(rootNode: XMLNode): MultipleChoiceSourceAnswer {
		var text: String;
		var feedback: String;
		var correct: Boolean = (rootNode.attributes.correct == "true") ? true : false;
		
		for each(var child: XMLNode in rootNode.childNodes) {
			if(child.nodeName == null) {
				continue; // skip empty text node stuff
			} else if(child.nodeName == "Text") {
				text = getNodeValue(child);
			} else if(child.nodeName == "Feedback") {
				feedback = getNodeValue(child);
			} else {
				throw new Error("invalid child.nodeName ["+child.nodeName+"]!");
			}
		}
		
		return new MultipleChoiceSourceAnswer(-1, text, feedback, correct);
	}

	private static function getNodeValue(node: XMLNode): String {
		return Util.removeLinebreaks(XMLNode(node.childNodes[0]).nodeValue);
	}

}
}