package com.nexa.business.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder(12);}
    @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration c) throws Exception{return c.getAuthenticationManager();}

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, ObjectMapper mapper) throws Exception {
        CookieCsrfTokenRepository csrf=CookieCsrfTokenRepository.withHttpOnlyFalse();
        csrf.setCookiePath("/");
        http
          .csrf(c->c.csrfTokenRepository(csrf))
          .authorizeRequests(a->a
            .antMatchers("/login","/styles.css","/*.js","/favicon.svg","/actuator/health","/actuator/info","/api/auth/login","/api/auth/csrf").permitAll()
            .antMatchers("/admin","/owner","/manager","/cashier","/warehouse","/").permitAll()
            .antMatchers("/api/**").authenticated()
            .anyRequest().authenticated())
          .formLogin().disable()
          .httpBasic().disable()
          .logout().disable()
          .sessionManagement(s->{s.sessionFixation().migrateSession();s.maximumSessions(3);})
          .exceptionHandling(e->e
            .authenticationEntryPoint((req,res,ex)->jsonError(mapper,res,401,"Unauthorized","Sesi tidak valid atau sudah berakhir."))
            .accessDeniedHandler((req,res,ex)->jsonError(mapper,res,403,"Forbidden","Anda tidak memiliki izin untuk aksi ini.")))
          .headers(h->{
            h.frameOptions().deny();
            h.contentTypeOptions();
            h.referrerPolicy(r->r.policy(org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy.SAME_ORIGIN));
            h.contentSecurityPolicy("default-src 'self'; script-src 'self'; style-src 'self' 'unsafe-inline'; img-src 'self' data:; connect-src 'self'; object-src 'none'; base-uri 'self'; frame-ancestors 'none'");
          });
        return http.build();
    }

    private static void jsonError(ObjectMapper mapper,HttpServletResponse res,int status,String error,String message) throws java.io.IOException{
        res.setStatus(status);res.setContentType(MediaType.APPLICATION_JSON_VALUE);res.setCharacterEncoding("UTF-8");
        Map<String,Object> body=new LinkedHashMap<>();body.put("status",status);body.put("error",error);body.put("message",message);mapper.writeValue(res.getOutputStream(),body);
    }
}
