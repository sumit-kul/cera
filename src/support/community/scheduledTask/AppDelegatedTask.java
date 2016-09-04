package support.community.scheduledTask;

import java.util.*;

public class AppDelegatedTask extends AppTask
{ 
    private List<Runnable> runnables = new ArrayList<Runnable>();
    protected void execute() throws Exception
    {
        for (Runnable runnable : runnables) {
            runnable.run();
        }
    }

    public void setRunnable(Runnable runnable)
    {
        List<Runnable> list = new ArrayList<Runnable>(1);
        list.add(runnable);
        this.runnables = list;
    }

    public final void setRunnables(List<Runnable> runnables)
    {
        this.runnables = runnables;
    }
}