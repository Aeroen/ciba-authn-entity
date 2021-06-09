package org.tnorimat.oss.ciba.endpoint;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationConfig {
    @Bean
    public ApplicationContext cibaConfig() {
        return new ApplicationContext();
    }
}
