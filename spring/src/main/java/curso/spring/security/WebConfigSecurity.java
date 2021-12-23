package curso.spring.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private ImplementacaoUserDetailsService detailsService;
	
	@Override //Configura as solicitações de acesso por HTTP
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
		.disable() //Desativa as configurações padrões de memória do Spring
		.authorizeRequests()// Permitir restringir acessos
		.antMatchers(HttpMethod.GET, "/").permitAll() //Qualquer usuário pode acessar
		 .antMatchers("**/materialize/**").permitAll()
		.antMatchers(HttpMethod.GET, "/cadastroPessoa").hasAnyRole("ADMIN")
		.anyRequest().authenticated().and().formLogin().permitAll()//Permite qualquer usuario 
		.loginPage("/login")
		.defaultSuccessUrl("/cadastroPessoa")
		.failureUrl("/login?error=true")
		
		
		.and().logout()	// Mapeia a URL de sair do sistema e invalida o usuario autenticado
		.logoutSuccessUrl("/login")
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		
	}
	
	@Override //Cria autenticacao do usuario com banco de dados ou em memoria
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		
		auth.userDetailsService(detailsService).
		passwordEncoder(new BCryptPasswordEncoder());
		
		
		
		
		
		/*auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).
		withUser("danillo").
		password("$2a$10$TYQJ6MPTyWrmW2SmlgfkaO0xWA95RFWZkxCciq4KEQOJeNsAnD03m")
		.roles("ADMIN");*/
	}
	

	@Override
	public void configure(WebSecurity web) throws Exception {

	          web.ignoring().antMatchers("/materialize/**")

	         .antMatchers(HttpMethod.GET,"/resources/**","/static/**", "/**", "/materialize/**","**/materialize/**");

	}
}
