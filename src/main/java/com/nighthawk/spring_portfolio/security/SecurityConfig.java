package com.nighthawk.spring_portfolio.security;

import com.nighthawk.spring_portfolio.mvc.jwt.*;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.http.HttpMethod;

/*
* To enable HTTP Security in Spring, extend the WebSecurityConfigurerAdapter. 
*/
@Configuration
@EnableWebSecurity  // Beans to enable basic Web security
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
    
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

    // Provide a default configuration using configure(HttpSecurity http)
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
        // httpSecurity.csrf().disable();
		httpSecurity
			// We don't need CSRF for this example
			.csrf().disable()
			// don't authenticate this particular request
			.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/").permitAll()
				.antMatchers(HttpMethod.GET, "/login").permitAll()
				.antMatchers(HttpMethod.POST, "/authenticate").permitAll()
				.antMatchers(HttpMethod.POST, "/api/person/post").permitAll()
				.antMatchers(HttpMethod.POST, "/api/person/**").hasAnyAuthority("ROLE_ADMIN")
				.antMatchers(HttpMethod.GET, "/api/person/**").hasAnyAuthority("ROLE_ADMIN")
				.antMatchers(HttpMethod.DELETE, "/api/person/**").hasAnyAuthority("ROLE_ADMIN")
				.antMatchers(HttpMethod.GET, "/api/role/**").hasAnyAuthority("ROLE_ADMIN")
				.antMatchers(HttpMethod.POST, "/api/role/**").hasAnyAuthority("ROLE_ADMIN")
				.antMatchers(HttpMethod.DELETE, "/api/role/**").hasAnyAuthority("ROLE_ADMIN")
				.antMatchers(HttpMethod.GET, "/blacklist").hasAnyAuthority("ROLE_ADMIN")
				.antMatchers(HttpMethod.GET, "/admin").hasAnyAuthority("ROLE_ADMIN") // For testing purposes
			// all other requests need to be authenticated
				.anyRequest().authenticated()
			.and().cors().and()
			.headers()
				.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Credentials", "true"))
				.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "https://guapbeast.github.io"))
				.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-ExposedHeaders", "*", "Authorization"))
				.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers", "Content-Type", "Authorization", "x-csrf-token"))
				.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-MaxAge", "600"))
				.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "POST", "GET", "OPTIONS", "HEAD", "DELETE"))
				// .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*", "http://localhost:8032"))
			.and()
				// make sure we use stateless session; session won't be used to
				// store user's state.
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
