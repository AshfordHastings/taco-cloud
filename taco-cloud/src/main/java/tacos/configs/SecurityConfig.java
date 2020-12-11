package tacos.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web
.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
		
	// Configures the authorization protocol to be based on the userDetails interface
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
		throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(this.encoder());
	}
	
	// Configures the authorization for a given path based on specific paths and roles
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/design", "/orders")
					.access("hasRole('ROLE_USER')")
				.antMatchers("/", "/**")
					.access("permitALL")
				.and()
					.formLogin()
						.loginPage("/login")
						.loginProcessingUrl("/authenticate")
						.usernameParameter("user")
						.passwordParameter("pwd")
						.defaultSuccessUrl("/design", true)
				.and()
					.logout()
						.logoutSuccessUrl("/")
				;
					
	}
}
