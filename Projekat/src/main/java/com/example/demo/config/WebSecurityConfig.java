package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.demo.security.RestAuthenticationEntryPoint;
import com.example.demo.security.TokenAuthenticationFilter;
import com.example.demo.security.TokenUtils;
import com.example.demo.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	// Implementacija PasswordEncoder-a koriscenjem BCrypt hashing funkcije.

    // BCrypt po defalt-u radi 10 rundi hesiranja prosledjene vrednosti.

    @Bean

    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }
    


    @Autowired
    private CustomUserDetailsService jwtUserDetailsService;

  // Neautorizovani pristup zastcenim resursima

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;


    @Override
    @Bean  
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();

    }

  

    // Definisemo nacin autentifikacije

    @Autowired

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
         

    }

    @Autowired
    TokenUtils tokenUtils;
    
    @Override

	public void configure(HttpSecurity http) throws Exception {

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
				// svim korisnicima dopusti da pristupe putanjama /auth/**, /h2-console/** i
				// /api/foo 
				.authorizeRequests().antMatchers("/api/korisnici**").permitAll()
				.anyRequest().authenticated().and()
				.cors().and()
				.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService),
						BasicAuthenticationFilter.class);

		http.csrf().disable();

	}



	// Generalna bezbednost aplikacije

	@Override

	public void configure(WebSecurity web) {

		// TokenAuthenticationFilter ce ignorisati sve ispod navedene putanjess

		web.ignoring().antMatchers(HttpMethod.POST, "/api/korisnici/**");
		web.ignoring().antMatchers(HttpMethod.GET, "/api/korisnici/**");
		web.ignoring().antMatchers(HttpMethod.PUT, "/api/korisnici/**");
		//web.ignoring().antMatchers(HttpMethod.POST, "/auth/login");

		//web.ignoring().antMatchers(HttpMethod.POST, "/auth/register");



		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",

				"/**/*.css", "/**/*.js");

	}
	


}
