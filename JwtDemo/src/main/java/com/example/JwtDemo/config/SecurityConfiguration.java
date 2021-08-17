package com.example.JwtDemo.config;


import com.example.JwtDemo.filter.JwtFilter;
import com.example.JwtDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{


    @Autowired
    private UserService userService;

    @Autowired
    private JwtFilter jwtFilter;

    private static final String [] URL = {
            "/authenticate",
            "/v2/api-docs",
            "/swagger-resources/**",
            "/swagger-ui.html/**",
            "/webjars/**"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers(URL)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    }


       /* @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/v2/api-docs",
                    "/configuration/ui",
                    "/swagger-resources",
                    "/configuration/security",
                    "/swagger-ui.html",
                    "/webjars/**");
        }
*/

}
