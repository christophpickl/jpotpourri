package net.sourceforge.jpotpourri.learnme.dao {


import mx.collections.ArrayCollection;
import net.sourceforge.jpotpourri.learnme.vo.IQuestionCatalog;

internal class ReportDao extends AbstractDao implements IReportDao {
	
	public function ReportDao() {
		
	}
	
	public function selectReport(catalog:IQuestionCatalog):ArrayCollection {
		const data: ArrayCollection = new ArrayCollection();
		
		/*
		SELECT all from catalog.source_question
		+ JOIN data computed for:
			.) count of corresponding existing checked questions
			.) success rate in percent/absolute
			.) date of last execution 
		
SELECT sq.id, q.id, sq.title, sa.id, ca.id, (sa.correct = ca.checked) AS isCorrect, COUNT(sq.id) AS countQuestionUsed, MAX(q.date) AS mostRecentDate
FROM s_question AS sq
JOIN c_question AS cq ON cq.id_s_question = sq.id
JOIN questionary AS q ON q.id_catalog=sq.id_catalog AND q.id=cq.id_questionary
JOIN c_answer AS ca ON ca.id_c_question = cq.id
JOIN s_answer AS sa ON sa.id = ca.id_s_answer
WHERE sq.id_catalog=1
GROUP BY ca.id
ORDER BY sq.id, sa.id, ca.id
		*/
		
		return data;
	}
	
}
}