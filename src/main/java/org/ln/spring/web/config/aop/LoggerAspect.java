package org.ln.spring.web.config.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Before("execution(* *.validateDatabase())")
	public void logValidateDb(JoinPoint point) {
		log.debug("Executing: {}", point);
	}

	@Around("execution(public * *(..)) and @annotation(org.ln.spring.web.config.aop.Loggable)")
	public Object logger(ProceedingJoinPoint pjp/* , Loggable a */)
			throws Throwable {
		long start = System.currentTimeMillis();

		Object result;

		try {
			log.debug("Starting execution of: {}", pjp);

			result = pjp.proceed();

			log.debug("Exeuction finished in {} ms", System.currentTimeMillis()
					- start);

			return result;
		} catch (Throwable e) {
			log.warn("Failed to execute: {}", pjp);

			throw e;
		}
	}
}
