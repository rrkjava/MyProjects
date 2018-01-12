/**
 * 
 */
package my.mimos.misos.web.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import my.mimos.misos.admin.misos_admin.CheckAuthenticatonFilter;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */

@EnableCaching
@EnableAutoConfiguration
@SpringBootApplication
// Scan for Spring Component classes
@ComponentScan(basePackages = {"my.mimos.misos.web.controller", "my.mimos.misos.web.service", "my.mimos.misos.web.config",
		"my.mimos.misos.command", "my.mimos.misos.mapper",
		"my.mimos.misos.web.service", "my.mimos.misos.service",
		"my.mimos.misos.domain.specification","my.mimos.misos.web.util",
		"my.mimos.misos.dissemination","my.mimos.misos.config",
		"my.mimos.misos.admin.misos_admin","my.mimos.misos.admin.misos_admin.vo","my.mimos.misos.util"})
// Scan for JPA Entity classes
@EntityScan(basePackages = {"my.mimos.misos.domain.entity"})
// Scan for JPA Repository classes
/*@EnableElasticsearchRepositories(basePackages={"my.mimos.misos.web.service"})*/
@EnableJpaRepositories(basePackages = {"my.mimos.misos.domain.repository"})
public class Application extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(Application.class);
	}
	
	@Bean
	public FilterRegistrationBean authorizationFilter(){
		FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
		CheckAuthenticatonFilter authfilter=new CheckAuthenticatonFilter();
	    filterRegBean.setFilter(authfilter);
	    List<String> urlPatterns = new ArrayList<String>();
	    urlPatterns.add("*.html");
	    //urlPatterns.add("/misos-admin/*");
	    filterRegBean.setUrlPatterns(urlPatterns);
	    return filterRegBean;
	}
	
	@Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("i18n/messages");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

	@Bean
	public LocaleResolver localeResolver() {
	 SessionLocaleResolver slr = new SessionLocaleResolver();
	 slr.setDefaultLocale(Locale.US); // Set default Locale as US
	 return slr;
	}
	
	 @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("Accept-Language");
        return lci;
    }
	
}