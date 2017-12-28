package com.sczapla.salon.configuration;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sun.faces.config.ConfigureListener;

@Configuration
public class JsfConfiguration extends WebMvcConfigurerAdapter implements ServletContextAware {

	@Bean
	public ServletRegistrationBean facesServletRegistraiton() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new FacesServlet(),
				new String[] { "*.xhtml" });
		registration.setName("Faces Servlet");
		registration.setLoadOnStartup(1);
		return registration;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/login.xhtml");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		super.addViewControllers(registry);
	}

	@Bean
	public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
		return new ServletListenerRegistrationBean<>(new ConfigureListener());
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
		servletContext.setInitParameter("primefaces.THEME", "admin");
		servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", Boolean.TRUE.toString());
		servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", Boolean.TRUE.toString());
		servletContext.setInitParameter("primefaces.FONT_AWESOME", Boolean.TRUE.toString());
		servletContext.setInitParameter("primefaces.UPLOADER", "native");
	}
}
