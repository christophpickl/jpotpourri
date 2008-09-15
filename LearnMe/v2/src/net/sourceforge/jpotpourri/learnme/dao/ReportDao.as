package net.sourceforge.jpotpourri.learnme.dao {


import logging.Logger;

import mx.collections.ArrayCollection;

import net.sourceforge.jpotpourri.learnme.vo.IQuestionCatalog;

internal class ReportDao extends AbstractDao implements IReportDao {
	
	private static const LOG: Logger = Logger.getLogger("net.sourceforge.jpotpourri.learnme.dao.ReportDao");
	
	/*
		SELECT
		  sq.id AS sq_id,
		  cq.id AS cq_id,
		  ca.id AS ca_id,
		  q.date AS q_date,
		  (ca.checked = sa.correct) AS correct
		FROM c_answer AS ca
		  JOIN s_answer AS sa ON sa.id = ca.id_s_answer
		  JOIN c_question AS cq ON cq.id = ca.id_c_question
		  JOIN s_question AS sq ON sq.id = cq.id_s_question
		  JOIN questionary AS q ON q.id = cq.id_questionary
		WHERE
		  q.id_catalog = 1
		GROUP BY
		  ca.id
		ORDER BY
		  sq.id,
		  cq.id
		*/
	private static const SQL_SELECT_REPORT_DATA: String = 
		"SELECT " +
		  "sq.id AS sq_id, " +
		  "cq.id AS cq_id, " +
		  "ca.id AS ca_id, " +
		  "q.date AS q_date, " +
		  "(ca.checked = sa.correct) AS correct " +
		"FROM c_answer AS ca " +
		  "JOIN s_answer AS sa ON sa.id = ca.id_s_answer " +
		  "JOIN c_question AS cq ON cq.id = ca.id_c_question " +
		  "JOIN s_question AS sq ON sq.id = cq.id_s_question " +
		  "JOIN questionary AS q ON q.id = cq.id_questionary " +
		"WHERE " +
		  "q.id_catalog = :catalogId " +
		"GROUP BY " +
		  "ca.id " +
		"ORDER BY " +
		  "sq.id, " +
		  "cq.id";
		
	private var _fnResult: Function;
	
	private var _recentCatalog: IQuestionCatalog;
	
	public function ReportDao() {
		
	}
	
	public function selectReports(catalog:IQuestionCatalog, fnResult: Function): void{
		this._fnResult = fnResult;
		this._recentCatalog = catalog;
		/*
		SELECT all from catalog.source_question
		+ JOIN data computed for:
			.) count of corresponding existing checked questions
			.) success rate in percent/absolute
			.) date of last execution 
		*/
		var params: Array = new Array();
		params[":catalogId"] = catalog.id;
		execSql(SQL_SELECT_REPORT_DATA, onSelectReportData, params);
	}
	
	private function onSelectReportData(result: ArrayCollection): void {
		LOG.finer("onSelectReportData for "+result.length+" items.");
		
		_fnResult(new ReportConverter().convert(this._recentCatalog, result));
	}
}
}
