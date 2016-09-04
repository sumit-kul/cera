package support.community.lucene.search;

import support.community.lucene.index.*;

import org.apache.lucene.analysis.*;
import org.apache.lucene.queryParser.*;
import org.apache.lucene.search.*;

public class EntityIndexQueryParser extends MultiFieldQueryParser
{

    public EntityIndexQueryParser(String[] fields, Analyzer analyzer)
    {
         super(fields, analyzer);
         setDefaultOperator(AND_OPERATOR);
    }

    protected Query getFieldQuery(String field, String queryText, int slop) throws ParseException
    {
        Query query = super.getFieldQuery(field, queryText, slop);
        
        if (field==null)
            ;
        else if (field.equals(EntityIndex.TITLE_FIELD))
            query.setBoost(2.0f);
        else if (field.equals(EntityIndex.DESCRIPTION_FIELD))
            query.setBoost(1.5f);
        
        return query;
    }
}