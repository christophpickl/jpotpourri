package net.sourceforge.teabee.jservice;

import net.sourceforge.teabee.jservice.search.SearchQuery;
import net.sourceforge.teabee.jservice.search.SearchResult;


public interface ISearchService {
	
	SearchResult doSearch(final SearchQuery query);
	
}
