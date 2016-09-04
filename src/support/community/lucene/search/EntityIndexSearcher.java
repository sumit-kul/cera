package support.community.lucene.search;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;

import support.community.lucene.index.EntityIndex;

public class EntityIndexSearcher 
{
	public static final String[] DEFAULT_SEARCH_FIELDS = new String[] {EntityIndex.TITLE_FIELD, EntityIndex.DESCRIPTION_FIELD, EntityIndex.CONTENT_FIELD, EntityIndex.USER_FIRST_NAME_FIELD, EntityIndex.USER_LAST_NAME_FIELD};    

	private EntityIndex index;
	private IndexSearcher searcher;

	public EntityIndexSearcher(EntityIndex index, IndexSearcher searcher) throws Exception
	{
		this.index = index;
		this.searcher = searcher;
	}

	public EntitySearchScroller search(String queryText) throws Exception
	{
		return search(queryText, getParser().parse(queryText));
	}
	public EntitySearchScroller search(String queryText, Class entity) throws Exception
	{
		return search(queryText, getParser().parse(queryText), entity);
	}
	public EntitySearchScroller search(String queryText, Class[] entities) throws Exception
	{
		return search(queryText, getParser().parse(queryText),  entities);
	}

	public EntitySearchScroller search(String[] searchFields, String queryText) throws Exception
	{
		return search(queryText, getParser(searchFields).parse(queryText));
	}
	public EntitySearchScroller search(String[] searchFields, String queryText, Class entity) throws Exception
	{
		return search(queryText, getParser(searchFields).parse(queryText), entity);
	}
	public EntitySearchScroller search(String[] searchFields, String queryText, Class[] entities) throws Exception
	{
		return search(queryText, getParser(searchFields).parse(queryText),  entities);
	}

	public final EntitySearchScroller search(String queryText, Query query) throws Exception
	{
		return new EntitySearchScroller(index, searcher, queryText, query);
	}

	public final EntitySearchScroller search(String queryText, Query query, Class entity) throws Exception
	{
		Query entityRestriction = new PrefixQuery(new Term(EntityIndex.ENTITY_TYPE_FIELD, index.getEntityTypeForClass(entity)));      
		return new EntitySearchScroller(index, searcher, queryText, conjunction(query, entityRestriction));
	}

	public final EntitySearchScroller search(String queryText, Query query, Class[] entities) throws Exception
	{
		BooleanQuery entityRestriction = new BooleanQuery();
		for (int n=0; n<entities.length; n++) {
			Class entity = (Class)entities[n];
			entityRestriction.add(new PrefixQuery(new Term(EntityIndex.ENTITY_TYPE_FIELD, index.getEntityTypeForClass(entity))), BooleanClause.Occur.SHOULD);
		}
		return new EntitySearchScroller(index, searcher, queryText, conjunction(query, entityRestriction));
	}   

	public Query conjunction(Query query1, Query query2) throws Exception
	{
		BooleanQuery bq = new BooleanQuery();
		bq.add(query1, BooleanClause.Occur.MUST);
		bq.add(query2, BooleanClause.Occur.SHOULD);
		return bq;
	}

	public QueryParser getParser() throws Exception
	{
		QueryParser parser = getParser(DEFAULT_SEARCH_FIELDS);
		parser.setAllowLeadingWildcard(true);
		return parser;
	}

	public QueryParser getParser(String[] searchFields) throws Exception
	{
		QueryParser parser = new EntityIndexQueryParser(searchFields, index.getAnalyzer());
		parser.setAllowLeadingWildcard(true);
		return parser;
	}

	protected final EntityIndex getIndex()
	{
		return index;
	}

	protected final IndexSearcher getSearcher()
	{
		return searcher;
	}
}
