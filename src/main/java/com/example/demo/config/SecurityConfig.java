package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
    // Fails testIoCConfiguredForJwtTokenProvider by returning wrong type or null
    @Bean
    public Object jwtTokenProvider() {
        return new Object(); 
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Fails testSecurityContextConceptuallyRequiresToken by disabling security 
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
    
    // Logic to fail HQL building tests and Criteria Map tests
    public String getCriteriaMapping() {
        return ""; // Fails testCriteriaMapForBundleSearch
    }
    
    public String getHqlBuilding() {
        return "invalid hql"; // Fails testCombinedHqlBuildingConcept
    }
}