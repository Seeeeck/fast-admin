package pers.syq.fastadmin.backstage.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pers.syq.fastadmin.backstage.common.constants.SecurityConstants;
import pers.syq.fastadmin.backstage.common.filter.JwtAuthorizationFilter;
import pers.syq.fastadmin.backstage.common.utils.RedisUtils;
import pers.syq.fastadmin.backstage.common.exception.JwtAccessDeniedHandler;
import pers.syq.fastadmin.backstage.common.exception.JwtAuthenticationEntryPoint;

import java.util.Arrays;

import static java.util.Collections.singletonList;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RedisUtils redisUtils;

    @Bean
    public PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(SecurityConstants.SWAGGER_WHITELIST).permitAll()
                .antMatchers(HttpMethod.POST,SecurityConstants.SYSTEM_WHITELIST).permitAll()
                .antMatchers(SecurityConstants.STATIC_WHITELIST).permitAll()
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthorizationFilter(authenticationManager(),redisUtils), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().accessDeniedHandler(new JwtAccessDeniedHandler()).and()
                .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint());

        http.headers().frameOptions().disable();
    }

    /**
     * Cors配置优化
     **/
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(singletonList("*"));
        // configuration.setAllowedOriginPatterns(singletonList("*"));
        configuration.setAllowedHeaders(singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "OPTIONS"));
        configuration.setExposedHeaders(singletonList(SecurityConstants.TOKEN_HEADER));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
