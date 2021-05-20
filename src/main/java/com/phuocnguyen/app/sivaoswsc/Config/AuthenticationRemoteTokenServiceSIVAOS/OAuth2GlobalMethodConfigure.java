package com.phuocnguyen.app.sivaoswsc.Config.AuthenticationRemoteTokenServiceSIVAOS;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sivaos.Configurer.SIVAJDBCConnectAutomation.SIVAJDBCConnectConfigurer;
import com.sivaos.Service.SIVAOSServiceImplement.*;
import com.sivaos.Utils.ConfigGlobalUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Collections;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile(value = {"dev", "local", "prod"})
public class OAuth2GlobalMethodConfigure extends GlobalMethodSecurityConfiguration {

    @Value("${sivaos.geo.time-zone}")
    private String timezone;

    @Bean
    public ObjectMapper objectMapper() {
        return ConfigGlobalUtils.configureDateTimeGlobal(timezone);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList(OAuth2AccessTokenVariable.AUTHENTICATION));
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new OAuth2MethodSecurityExpressionHandler();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    @Primary
    @Resource
    public SIVAJDBCConnectConfigurer connectConfigurer() {
        return new SIVAJDBCConnectConfigurer();
    }

    @Bean
    @Primary
    @Resource(name = "baseService")
    public BaseSIVAOSServiceImplement baseSIVAOSService() {
        return new BaseSIVAOSServiceImplement();
    }

    @Bean
    @Primary
    @Resource(name = "sivaOsAuthenticationService")
    public SIVAOSAuthenticationServiceImplement sivaosAuthenticationServiceImplement() {
        return new SIVAOSAuthenticationServiceImplement();
    }

    @Bean
    @Primary
    @Resource(name = "mailService")
    public SIVAOSMailServiceImplement sivaosMailServiceImplement() {
        return new SIVAOSMailServiceImplement();
    }


    @Bean
    @Primary
    @Resource(name = "globalExceptionService")
    public GlobalExceptionServiceImplement globalExceptionServiceImplement() {
        return new GlobalExceptionServiceImplement();
    }

    @Bean
    @Primary
    @Resource(name = "secretService")
    public SIVAOSSecretServiceImplement sivaosSecretService() {
        return new SIVAOSSecretServiceImplement();
    }

    @Bean
    @Primary
    @Resource(name = "jwtService")
    public SIVAOSJWTsServiceImplement jwtService() {
        return new SIVAOSJWTsServiceImplement(sivaosSecretService());
    }

    @Bean
    @Primary
    @Resource(name = "jdbcService")
    public SIVAOSJDBCServiceImplement jdbcService() {
        return new SIVAOSJDBCServiceImplement();
    }

    @Bean
    @Primary
    @Resource(name = "redisConfigService")
    public RedisConfigServiceImplement redisConfigService() {
        return new RedisConfigServiceImplement();
    }

    @Bean
    @Primary
    @Resource(name = "redisService")
    public SIVAOSRedisServiceImplement redisService() {
        return new SIVAOSRedisServiceImplement();
    }


    private static class OAuth2AccessTokenVariable {
        private static final String AUTHENTICATION = "Authorization";
    }

}
