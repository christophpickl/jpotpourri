package net.sourceforge.jpotpourri.learnme.xml {

import flash.xml.XMLDocument;
import flash.xml.XMLNode;

import mx.collections.ArrayCollection;

import net.sourceforge.jpotpourri.learnme.Util;
import net.sourceforge.jpotpourri.learnme.vo.IQuestionCatalog;
import net.sourceforge.jpotpourri.learnme.vo.MultipleChoiceSourceAnswer;
import net.sourceforge.jpotpourri.learnme.vo.MultipleChoiceSourceQuestion;
import net.sourceforge.jpotpourri.learnme.vo.QuestionCatalog;

public class CatalogParser {
	
	public function CatalogParser() {
		
	}

	public static function parse(xmlString: String): IQuestionCatalog {
		var xmlDoc: XMLDocument = new XMLDocument();
		xmlDoc.parseXML(xmlString);
		
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