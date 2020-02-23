package com.springboot.apirest.configurations.auth;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter { // se encarga de dar acceso a los clientes a los recursos de nuestros clientes, mientras el token enviado en la cabecera sea valido del lado oauth

    @Override
    public void configure(HttpSecurity http) throws Exception {     // metodo que nos permite implementar toda la regla de seguridad de nuestros endpoints de nuestras rutas hacia los recursos

        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/clients", "/api/clients/page/{page}", "/api/clients/uploads/img/**", "/images/**").permitAll()// damos accesso a api clients para todos
                /*.antMatchers(HttpMethod.GET, "/api/clients/{id}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/clients/upload").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/clients").hasRole("ADMIN")
                .antMatchers("/api/clients/**").hasRole("ADMIN")*/    // se aplica a todos los que no se especificaron arriba, seran con rol admin
                .anyRequest().authenticated()   // siempre al final para todas las rutas que no hayamos dado permisos
                .and().cors().configurationSource(corsConfigurationSource());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // agregamos nuestros dominios
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // configuramos los verbos que vamos a permitir en el backend
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);         // configurar el cors para todos nuestros endpoints del backend

        return source;
    }

    // registrar un filtro en la prioridad mas alta de los filtros de spring, para que se aplica en el server Authorization y en el Resource Server
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(){

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));

        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);    // damos orden bajo, entre mas bajo sea mayor es la proriedad

        return bean;
    }
}
