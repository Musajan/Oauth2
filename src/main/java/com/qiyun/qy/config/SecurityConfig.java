package com.qiyun.qy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("qiYunSecurityProvider")
	private AuthenticationProvider provider;// 自定义验证
	
	@Autowired
	@Qualifier("qiYunUserService")
    private UserDetailsService userService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()//禁用csrf （csrf会拦截所有post请求）
			.headers().frameOptions().disable()//允许使用iframe
			.and().authorizeRequests()
				.antMatchers("/home", "/register", "/socket").permitAll()//首页、注册、web socket，不需要权限
				.antMatchers("/static/**", "/ueditor/**", "/error/**").permitAll()
				.antMatchers("/oauth/**", "/api/**").permitAll()//当前filter不拦截 OAuth2.0 的路径
				.anyRequest().authenticated()
				.antMatchers("/admin/**").hasRole("admin")//预留管理员权限
			.and().formLogin().defaultSuccessUrl("/home").permitAll()
			.and().logout().logoutUrl("/login?logout").logoutSuccessUrl("/").permitAll();
			/*.and().requiresChannel()
				.regexMatchers("(/)|(/?(home|index))")
				.requiresInsecure()//主页http访问
			.and().requiresChannel().anyRequest().requiresSecure();//其他链接需要https*/	
		}

	@Autowired
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		 /*auth
		 .inMemoryAuthentication()
		 	.withUser("user").password("password").roles("USER");*/
		auth.authenticationProvider(provider);
	}
	
}
