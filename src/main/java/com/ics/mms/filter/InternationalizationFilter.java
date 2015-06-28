package com.ics.mms.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;

public class InternationalizationFilter extends OncePerRequestFilter {

	@Override
	public void destroy() {
	    // TODO Auto-generated method stub
	
	}


	@Override
	protected void doFilterInternal(
		final HttpServletRequest request,
        final HttpServletResponse response,
        final FilterChain filterChain)
        throws ServletException, IOException {
    
		logger.info("doFilterInternal() begin");
		//final String newLocale = request.getParameter("locale");
		
		String newLocale;
		
		//String localeLanguage=request.getLocale().getCountry();
		String localeLanguage=request.getLocale().toString();
		
	    logger.info("---locale2----"+localeLanguage) ;
	    //zh, zh_TW, zh_HK, zh_CN
	    if(localeLanguage.equalsIgnoreCase("zh")||localeLanguage.equalsIgnoreCase("zh_TW")||localeLanguage.equalsIgnoreCase("zh_HK")){
	    	newLocale="zh_HK";
	    }else if(localeLanguage.equalsIgnoreCase("zh_CN")){
		    newLocale="zh_CN";
		}else{
	    	newLocale="en_US";	
	    }
    
		if (newLocale != null) {
    	
			ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
    	     

			final LocaleResolver localeResolver = (LocaleResolver) ctx.getBean("localeResolver", LocaleResolver.class);
			request.setAttribute(DispatcherServlet.class.getName() + ".LOCALE_RESOLVER", localeResolver);
			LocaleEditor localeEditor = new LocaleEditor();
			localeEditor.setAsText(newLocale);
			logger.info("--newLocale--"+newLocale);
			localeResolver.setLocale(request, response, (Locale) localeEditor.getValue());
			LocaleContextHolder.setLocaleContext(new LocaleContext() {
				public Locale getLocale() {
					return localeResolver.resolveLocale(request);
				}
			}, false);
		}
    
		filterChain.doFilter(request, response);
		
		logger.info("doFilterInternal() end");
	}

}