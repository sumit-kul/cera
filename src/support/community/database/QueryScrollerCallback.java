package support.community.database;

public interface QueryScrollerCallback 
{
     public void handleRow(Object rowBean, int rowNum) throws Exception;
}