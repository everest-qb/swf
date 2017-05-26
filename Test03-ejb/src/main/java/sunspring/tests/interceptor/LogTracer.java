package sunspring.tests.interceptor;

import java.io.Serializable;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sunspring.tests.annotation.LogTrace;

@LogTrace
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class LogTracer implements Serializable {

	private static final long serialVersionUID = 1L;

	@AroundInvoke
    public Object logMethodEntry(InvocationContext ctx)
            throws Exception {	
		Logger log=LoggerFactory.getLogger(ctx.getMethod().getDeclaringClass());		
		if(log.isTraceEnabled()){
			String methpdName=ctx.getMethod().getName();		
			Object[] objs=ctx.getParameters();			
			log.trace("{}",methpdName);
			if(objs!=null)
			for(int i=0;i<objs.length;i++){
				log.trace("p{}:{}",i,objs[i]);
			}
		}		
		return ctx.proceed();
	}
	
	public LogTracer() {
		
	}
	
}
