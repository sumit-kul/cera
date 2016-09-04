package support.community.framework;

import org.acegisecurity.intercept.*;


/**
 * 
 */
public class RunAsServerTemplate extends AbstractSecurityInterceptor
{
    RunAsServerDefinitionSource objectDefinitionSource;

    public Class getSecureObjectClass()
    {
        return RunAsServerCallback.class;
    }

    public ObjectDefinitionSource obtainObjectDefinitionSource()
    {
        return objectDefinitionSource;
    }

    /**
     * This method should be used to enforce security on a <code>MethodInvocation</code>.
     *
     * @param mi The method being invoked which requires a security decision
     *
     * @return The returned value from the method invocation
     *
     * @throws Throwable if any error occurs
     */
    public Object execute(RunAsServerCallback callback) throws Exception 
    {
        Object result = null;
        InterceptorStatusToken token = super.beforeInvocation(callback);

        try {
            result = callback.doInSecurityContext();
        } finally {
            result = super.afterInvocation(token, result);
        }

        return result;
    }


    public void setObjectDefinitionSource(RunAsServerDefinitionSource objectDefinitionSource)
    {
        this.objectDefinitionSource = objectDefinitionSource;
    }
}
