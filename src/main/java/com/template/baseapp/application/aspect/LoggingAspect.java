package com.template.baseapp.application.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plugin.logging.services.LoggingUtils;
import com.template.baseapp.domain.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

@Aspect
@Configuration
@DependsOn({"loggingUtils"})
public class LoggingAspect {

    private Logger log = LoggingUtils.getLogger(LoggingAspect.class.getName());

    @Autowired
    private ObjectMapper mapper;

    /**
     * log around controller
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.template.baseapp.application.controller.**.*.*(..)) || execution(* com.template.baseapp.application.controller.*.*(..))")
    public Object controllerAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long inTime = System.currentTimeMillis();

        Object[] argList = joinPoint.getArgs();

        HttpServletRequest httpServletRequest = getHttpServletRequest(argList);
        String function = joinPoint.getSignature().getName() != null ? joinPoint.getSignature().getName() : "N/A";
        String uri = httpServletRequest != null ? httpServletRequest.getRequestURI() : "N/A";

        MDC.put(Constants.FUNCTION_NAME, function);
        MDC.put(Constants.URI, uri);


        try {
            log.info(Constants.LOG_REQUEST_STR, "REQUEST-RECEIVED", Constants.MS_NAME, MDC.get(Constants.FUNCTION_NAME), MDC.get(Constants.URI), mapper.writeValueAsString(getRequestBody(argList)));
        } catch (JsonProcessingException e) {
            log.error(Constants.LOG_ERROR_STR, MDC.get(Constants.FUNCTION_NAME), MDC.get(Constants.URI), "error parsing params while logging request in AOP class");
        }
        Object proceed = joinPoint.proceed();

        long responseTime = System.currentTimeMillis() - inTime;
        if(proceed instanceof ResponseEntity){
            ResponseEntity<HashMap> response = (ResponseEntity<HashMap>) proceed;
            // extract response code from response
            String responseCode = String.valueOf(response.getStatusCode().value());
            log.info(Constants.LOG_RESPONSE_STR,"REQUEST-TERMINATED", Constants.MS_NAME, MDC.get(Constants.FUNCTION_NAME), MDC.get(Constants.URI), responseCode, responseTime, mapper.writeValueAsString(response.getBody()));
        }else{
            log.info(Constants.LOG_RESPONSE_STR,"REQUEST-TERMINATED",  Constants.MS_NAME, MDC.get(Constants.FUNCTION_NAME), MDC.get(Constants.URI), 200, responseTime,  proceed);
        }
        return proceed;
    }

    @AfterThrowing(value = "execution(* com.template.baseapp.external.repository.**.*.*(..))",
            throwing = "ex")
    public void logAfterThrowingRepositories(JoinPoint joinPoint, Throwable ex) {
        log.error(Constants.LOG_ERROR_STR, MDC.get(Constants.FUNCTION_NAME), MDC.get(Constants.URI), ex.getMessage());
    }

    /**
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around( "execution(* com.template.baseapp.external.repository.impl.*.*(..))" )
    public Object logAroundRepositories(ProceedingJoinPoint joinPoint) throws Throwable {
        long inTime = System.currentTimeMillis();
        Object[] argList = joinPoint.getArgs();
        String function = joinPoint.getSignature().getName();
        try {
            log.debug(Constants.LOG_DEBUG_STR, "DB Function-".concat(function), MDC.get(Constants.URI), mapper.writeValueAsString(argList));
        }
        catch ( JsonProcessingException e ) {
            log.error(Constants.LOG_ERROR_STR, MDC.get(Constants.FUNCTION_NAME), MDC.get(Constants.URI),joinPoint.getTarget().getClass().getSimpleName());
        }
        Object proceed = joinPoint.proceed();
        long responseTime = System.currentTimeMillis() - inTime;
        log.debug(Constants.LOG_DEBUG_STR, "DB Function-".concat(function).contains(":"+MDC.get(Constants.URI)), responseTime, mapper.writeValueAsString(proceed));
        return proceed;
    }

    private HttpServletRequest getHttpServletRequest(Object[] argList) {
        Object httpServletRequest = null;
        try {
            if (argList[0] instanceof HttpServletRequest) {
                httpServletRequest = argList[0];
            } else if (argList[1] instanceof HttpServletRequest) {
                httpServletRequest = argList[1];
            } else if(argList[2] instanceof HttpServletRequest){
                httpServletRequest = argList[2];
            }
        } catch (Exception ex) {
            log.error(Constants.LOG_ERROR_STR, MDC.get(Constants.FUNCTION_NAME), "N/A", "Error occurred while parsing to HttpServletRequest");
        }
        return (HttpServletRequest) httpServletRequest;
    }

    private Object getRequestBody(Object[] argList) {
        Object requestBody = null;
        try {
            if (argList[0] instanceof HttpServletRequest && argList.length==1) {
                requestBody = "N/A";
            } else if (argList[0] instanceof HttpServletRequest) {
                requestBody = argList[1];
            } else {
                requestBody = argList[0];
            }
        } catch (Exception ex) {
            log.error(Constants.LOG_ERROR_STR, MDC.get(Constants.FUNCTION_NAME), "N/A", "Error occurred while parsing to RequestBody");
        }
        return requestBody;
    }
}
