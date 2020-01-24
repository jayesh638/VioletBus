//package com.redbus.backend_redbus.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import java.util.Arrays;
//
//@Configuration
//@EnableWebSecurity(debug = true)
//public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//    @Autowired
//    CustomOidcService customOidcService;
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().authorizeRequests()
//                .antMatchers(HttpMethod.POST,"/addBus","/addRoute","/addBooking","/rateMyBus","/cancleTicket","/response").permitAll()
//                .antMatchers(HttpMethod.PUT,"/editBus").permitAll()
//                .antMatchers(HttpMethod.GET,"/city","/cities","/search","/ticket/*").permitAll()
//                .antMatchers("/userlogin").authenticated().and().logout().logoutSuccessUrl("/").and()
//                .oauth2Login()
//                .redirectionEndpoint().baseUri("/oauth2/callback/*")
//                .and()
//                .userInfoEndpoint()
//                .oidcUserService(customOidcService);
//    }
////    @Bean
////    CorsConfigurationSource corsConfigurationSource()
////    {
////        CorsConfiguration configuration = new CorsConfiguration();
////        configuration.setAllowedOrigins(Arrays.asList("*"));
////        configuration.setAllowedMethods(Arrays.asList("*"));
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", configuration);
////        return source;
////    }
//}
