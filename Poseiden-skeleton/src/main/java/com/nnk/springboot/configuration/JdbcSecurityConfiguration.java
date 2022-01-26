package com.nnk.springboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class JdbcSecurityConfiguration extends WebSecurityConfigurerAdapter{

    private final DataSource dataSource;

    public JdbcSecurityConfiguration(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("SELECT username, password, 'true' as enabled FROM users WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username, role FROM users WHERE username= ?")
                .rolePrefix("ROLE_");
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity/*.regexMatcher("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$?!%*&_#^]){8,}$")*/
                    .authorizeRequests()
                    .antMatchers("/min/**", "/css/**", "/js/**", "/images/**", "/static/**", "/login")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/home", true)
                    .permitAll()
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/error")
                .and()
                    .oauth2Login()
                    .loginPage("/login")
                    //.authorizationEndpoint()
                    //.baseUri("/login/oauth2/authorization/github")
                    //.loginPage("/login/oauth2")

                /*.and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .clearAuthentication(true)*/;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
