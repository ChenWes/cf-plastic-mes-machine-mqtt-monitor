package com.cf.framework.config;


import com.cf.framework.security.handle.AuthenticationEntryPointImpl;
import com.cf.framework.security.handle.LogoutSuccessHandlerImpl;
import com.cf.framework.security.handle.SecurityAuthenticationProvider;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.keycloak.authorization.client.util.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.filter.CorsFilter;

/**
 * @Description 安全认证配置
 * @Author ccw
 * @Date 2021/5/10
 */
@KeycloakConfiguration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Autowired
    private SecurityAuthenticationProvider authenticationProvider;

    @Autowired
    private KeycloakAuthenticationProcessingFilter keycloakAuthenticationProcessingFilter;

    @Autowired
    private KeycloakPreAuthActionsFilter keycloakPreAuthActionsFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }


    @Bean
    public FilterRegistrationBean keycloakAuthenticationProcessingFilterRegistrationBean(
            KeycloakAuthenticationProcessingFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean keycloakPreAuthActionsFilterRegistrationBean(
            KeycloakPreAuthActionsFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }



    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    /**
     * 跨域过滤器
     */
    @Autowired
    private CorsFilter corsFilter;

    /**
     * 认证失败处理类
     */
    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;
    /**
     * 退出处理类
     */
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * 此处应是检测用户的token，不应该是允许所的有请求，如果允许所有请求，将失去了keycloak的保护意义
     * 当然，部分http请求确实不需要受keycloak保护，则可以将地址加入至白名单中，允许匿名调用
     *
     * /mes/machine/registration/** 机器注册相关的API可以允许不加入token
     * /mes/token/** 通过注册码相关信息获取Token的，允许不加入token
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .cors().and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .antMatchers("/profile/**").anonymous()
                .antMatchers("/common/download**").anonymous()
                .antMatchers("/common/download/resource**").anonymous()
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                .antMatchers("/druid/**").anonymous()
                .antMatchers(
                        "/mes/token/**"
                ).permitAll()
                .antMatchers(
                        "/mes/machineInterface/registration/**"
                ).permitAll()
                .antMatchers(
                        "/mes/machineInterface/validateAutoRegisterServiceRandomCode/**"
                ).permitAll()
                .antMatchers(
                        "/mes/machineInterface/autoRegisterService/**"
                ).permitAll()
                .antMatchers(
                        "/mes/dashboardInterface/autoRegisterService/**"
                ).permitAll()
                .antMatchers(
                        "/mes/dashboardInterface/validateAutoRegisterServiceRandomCode/**"
                ).permitAll()
                .antMatchers(
                        "/mes/dashboardInterface/cancellationAutoRegisterServiceRandomCode/**"
                ).permitAll()
                .antMatchers(
                        "/mes/dashboardInterface/token/**"
                ).permitAll()
                .antMatchers(
                        "/mes/qcClientInterface/autoRegisterService/**"
                ).permitAll()
                .antMatchers(
                        "/mes/qcClientInterface/validateAutoRegisterServiceRandomCode/**"
                ).permitAll()
                .antMatchers(
                        "/mes/qcClientInterface/cancellationAutoRegisterServiceRandomCode/**"
                ).permitAll()
                .antMatchers(
                        "/mes/qcClientInterface/token/**"
                ).permitAll()
                .antMatchers(
                        HttpMethod.POST,
                        "/mes/appInterface/register/employee"
                ).permitAll()
                .antMatchers(
                        HttpMethod.GET,
                        "/minio/**"
                ).permitAll()
                .antMatchers(
                        "/monitor/server/**"
                ).permitAll()
                .anyRequest().authenticated();

        http
                //.addFilterBefore(mySecurityInterceptor, FilterSecurityInterceptor.class)
                .addFilterBefore(keycloakAuthenticationProcessingFilter, FilterSecurityInterceptor.class)
                .addFilterBefore(keycloakPreAuthActionsFilter, KeycloakAuthenticationProcessingFilter.class);

        http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);

    }

}
