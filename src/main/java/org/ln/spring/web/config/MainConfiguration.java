package org.ln.spring.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@ComponentScan(basePackages = "org.ln.spring.web")
@Import(SecurityConfig.class)
public class MainConfiguration {
}
