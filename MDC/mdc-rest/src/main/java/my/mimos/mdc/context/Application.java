package my.mimos.mdc.context;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@EnableScheduling
@EnableAsync
@EnableCaching
@SpringBootApplication
@ComponentScan(basePackages = {
		"my.mimos.mdc.controller","my.mimos.mdc.configuration",
		"my.mimos.mdc.service","my.mimos.mdc.command",
		"my.mimos.mdc.domain.mapper","my.mimos.mdc.utils",
		"my.mimos.mdc.exceptions",
		"my.mimos.mdc.security",
		"my.mimos.mdc.scheduler"})
@EntityScan(basePackages = {"my.mimos.mdc.domain.entity"})
@EnableJpaRepositories(basePackages = {"my.mimos.mdc.domain.repository"})
public class Application extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}
	
	/**
	 * Push notification runs as a separate thread
	 * Setting up a task executor
	 * @return
	 */
	@Bean(name = "notificationTaskExecutor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        return executor;
    }
	
	/**
	 * Upload of external files 
	 * Allows tomcat server to consumes file uploads with size>1MB(default) 
	 * @return
	 */
	@Bean
	public TomcatEmbeddedServletContainerFactory tomcatEmbedded() {
	    TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
	    tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
	        if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
	            //-1 for unlimited
	            ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
	        }
	    });
	    return tomcat;
	}
	
	// Set maxPostSize of embedded tomcat server to 10 megabytes (default is 2 MB, not large enough to support file uploads > 1.5 MB)
	/*@Bean
	EmbeddedServletContainerCustomizer containerCustomizer() throws Exception {
	    return (ConfigurableEmbeddedServletContainer container) -> {
	        if (container instanceof TomcatEmbeddedServletContainerFactory) {
	            TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
	            tomcat.addConnectorCustomizers(
	                (connector) -> {
	                    connector.setMaxPostSize(10000000); // 10 MB
	                }
	            );
	        }
	    };
	}*/
	
	/*Or in the Tomcat/conf/server.xml for 5MB
	<Connector port="8080" protocol="HTTP/1.1"
	       connectionTimeout="20000"
	       redirectPort="8443"
	       maxSwallowSize="5242880" />*/
	
	
}