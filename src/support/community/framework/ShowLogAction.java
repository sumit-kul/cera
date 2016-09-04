package support.community.framework;

import java.io.*;

import org.springframework.web.servlet.*;

import support.community.application.*;


/**
 * 
 * 
 */
public class ShowLogAction extends AbstractCommandAction 
{
    private AppRequestContextHolder contextManager;
    
    private String logFilePath;
    
    
    /**
     * The execute method receives the mapping used to invoke the action, the form bean containing the
     * request data, the servlet request and servlet response. It returns an action forward (usually pointing
     * to a JSP) to indicate what struts should do next. (If null is returned strust does nothing, assuming the
     * action has written the response itself.) 
     */

    protected ModelAndView handle(Object data) throws Exception
    {
        String sFile = logFilePath;
        if (sFile==null) throw new Exception("No application log file is currently in use");
        
        AppLog.write("Reading log file: "+sFile);

       byte[] buf;
       RandomAccessFile f = new RandomAccessFile(sFile, "r");

        try {
            
            ShowLogBean bean = (ShowLogBean)data;
            
            int nBytes =  bean.getCount()*1024; ////
            buf = new byte[(int)Math.min(nBytes, f.length())];
        
            f.seek(Math.max(0,f.length()-nBytes));
            
            if (f.getFilePointer()>0)
                if(f.readLine()==null) throw new Exception("Empty log file");
    
            if (f.read(buf)==0) throw new Exception("Empty log file");

        }
        finally {
            f.close();
        }

        Writer w = contextManager.getRequestContext().getResponse().getWriter();
        w.write("<body>");
        w.write("<pre>");
        w.write(new String(buf));
        w.write("</pre>");
        w.write("</body>");

        return null;
    }

    
    public static class ShowLogBean extends CommandBeanImpl implements CommandBean 
    {
        private int m_count = 30;
        private String m_file;

        /**
         * @return
         */
        public int getCount()
        {
            return m_count;
        }

        /**
         * @return
         */
        public String getFile()
        {
            return m_file;
        }

        /**
         * @param i
         */
        public void setCount(int i)
        {
            m_count = i;
        }

        /**
         * @param string
         */
        public void setFile(String string)
        {
            m_file = string;
        }

    }
    
    
    public final void setLogFilePath(String logFilePath)
    {
        this.logFilePath = logFilePath;
    }
    public final void setContextHolder(AppRequestContextHolder contextManager)
    {
        this.contextManager = contextManager;
    }
}
