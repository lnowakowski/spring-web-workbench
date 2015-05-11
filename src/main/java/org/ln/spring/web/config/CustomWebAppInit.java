package org.ln.spring.web.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

public class CustomWebAppInit extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		super.onStartup(servletContext);

		FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        
		encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, true, "/*");
        
        MessageDispatcherServlet wsServlet = new MessageDispatcherServlet();
        AnnotationConfigWebApplicationContext wsContext = new AnnotationConfigWebApplicationContext();
        
        wsContext.register(WsConfig.class);
        
		wsServlet.setApplicationContext(wsContext);
        wsServlet.setTransformWsdlLocations(true);
        
        Dynamic wsServletRegistration = servletContext.addServlet("ws", wsServlet);
        
        wsServletRegistration.setLoadOnStartup(1);
        wsServletRegistration.addMapping("/ws/*");
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { SecurityConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { MvcConfiguration.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
}
