package com.max.alfboot;

import com.max.alfboot.alfresco.Alfresco;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Maxim on 16-May-15.
 */
@SpringBootApplication
public class Bootstrap {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Bootstrap.class, args);

        System.out.println("Supported auth providers");
        Map<String, AuthenticationProvider> providerMap = ctx.getBeansOfType(AuthenticationProvider.class);
        providerMap.values().stream().forEach(System.out::println);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());

        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }

    /*@Bean
    public UsernamePasswordAuthenticationFilter filter() {
        UsernamePasswordAuthenticationFilter authenticationFilter = new UsernamePasswordAuthenticationFilter();
        authenticationFilter.setPostOnly(false);
        return authenticationFilter;
    }*/

}
