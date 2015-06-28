package com.ics.mms.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	protected final Log logger = LogFactory.getLog(getClass());
	public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
    	logger.error("Error............................................");
    	logger.error("Exception: "+e);
		e.printStackTrace();
    	if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
    		throw e;
    	}
            

        // Otherwise setup and send the user to a default error-view.
        ModelAndView mav = new ModelAndView();
       // mav.addObject("errorMsg", e.getCause().getMessage());
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return null;
    }

}
