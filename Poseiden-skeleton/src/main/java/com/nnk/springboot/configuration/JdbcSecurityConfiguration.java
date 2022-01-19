package com.nnk.springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private DataSource dataSource;

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
        httpSecurity.authorizeRequests()
                .antMatchers("/min/**", "/css/**", "/js/**", "/images/**", "/static/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/home", true)
                .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /*public class UserLoginValidator implements Validator {

        private static final int MINIMUM_PASSWORD_LENGTH = 6;
        private static Pattern pattern;
        private static Matcher matcher;


        public boolean supports(Class clazz) {
            return UserLogin.class.isAssignableFrom(clazz);
        }

        public void validate(Object target, Errors errors) {
            pattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$?!%*&_#^]){8,}$");
            matcher = pattern.matcher("ADD LINK TO PASSWORD IN DATABASE");

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "field.required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required");
            UserLogin login = (UserLogin) target;
            if (login.getPassword() != null || matcher(login.getPassword).isFalse()) {
                errors.rejectValue("password", "field.min.length",
                        new Object[]{Integer.valueOf(MINIMUM_PASSWORD_LENGTH)},
                        "The password must be at least [" + MINIMUM_PASSWORD_LENGTH + "] characters in length.");
            }
        }
    }*/
}
