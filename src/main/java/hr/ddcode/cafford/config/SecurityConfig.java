package hr.ddcode.cafford.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.header.HeaderWriterFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
		
	@Autowired
	private UserDetailsService userLoginService;
	
	@Autowired
    private BCryptPasswordEncoder bCryptEncoder;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
          .csrf().disable()
          .authorizeRequests()     
          	//.antMatchers("/ordersnotification/**").permitAll()
            .antMatchers("/user/register", "/login").permitAll()
          	.anyRequest().authenticated()
          .and()
          	.httpBasic()
          .and()
    	    .addFilterBefore(new CORSFilter(), HeaderWriterFilter.class);
          //.and()
          	//.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);   
    }
  
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {	  
	    auth
	      .userDetailsService(userLoginService)
	      .passwordEncoder(bCryptEncoder);
    }
    
}
