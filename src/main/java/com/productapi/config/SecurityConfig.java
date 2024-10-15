package com.productapi.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    Logger logger = LoggerFactory.getLogger("SecurityConfig.class");
    public final RsaKeyProperties rsaKeys;
    // private final AuthenticationLoggingFilter authenticationLoggingFilter = null;

    public SecurityConfig(RsaKeyProperties rsaKeys) {
        this.rsaKeys = rsaKeys;
    }

    // Datasource is automatically created from the application.properties
    @Bean
    JdbcUserDetailsManager users(DataSource dataSource, PasswordEncoder encoder){
        logger.debug("users(): Creating Security Users in db...");

        logger.debug("dataSource: " + dataSource);

        UserDetails user = User
                .withUsername("KVRM")
                .password(encoder.encode("password"))
                .roles("USER")
                .build();
        UserDetails admin = User
                .withUsername("admin")
                .password(encoder.encode("Myadmin#123"))
                .roles("ADMIN", "USER")
                .build();
        logger.debug("admin password:: " + admin.getPassword());

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        try {
            jdbcUserDetailsManager.createUser(user); // this should happen only once...need to figure it out
            jdbcUserDetailsManager.createUser(admin); // this should happen only once...need to figure it out
        }catch(Exception e){
            logger.debug("Exception occurred: " + e.getMessage());
        }
        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        logger.debug("Inside CRUD Template APP securityFilterChain()@SecurityConfig:: ...." + httpSecurity.toString());
        // logger.debug("Inside CRUD Template APP securityFilterChain()@Filter:: ...." + httpSecurity.getObject().getFilters());

        return httpSecurity
                // .addFilterBefore(new LoggingFilter(), GenericFilterBean.class)
                .csrf(AbstractHttpConfigurer::disable)
                //.addFilterAfter(new MyCustomFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new MyCustomFilter(), BasicAuthenticationFilter.class)
                //.addFilterBefore(new LoggingFilter(), OncePerRequestFilter.class)
                .authorizeHttpRequests( (auth) -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/loginn").permitAll()
                        .requestMatchers("/actuator/*").permitAll()
                        .requestMatchers("/actuator").permitAll()
                       // .requestMatchers("/actuator/info").permitAll()
                       // .requestMatchers("/actuator/health").permitAll()
                       // .requestMatchers("/actuator/loggers/**").permitAll()
                       // .requestMatchers("/actuator/metrics").permitAll()
                        //.requestMatchers("/actuator/prometheus").permitAll()
                        .requestMatchers("/api/v1/token").permitAll()
                        //.requestMatchers("/static/*").permitAll()
                        //.anyRequest().authenticated())
                        .anyRequest().permitAll())
                .logout((logout) -> logout.logoutSuccessUrl("/empui/index.html"))
                // .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt) deprecated..
                 .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                //.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
               .httpBasic(withDefaults())
                // .formLogin(withDefaults())
                .build();
    }

//    // Custom filter to log requested URLs
//    public class LoggingFilter extends OncePerRequestFilter {
//
//        @Override
//        protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
//            // Log the requested URL
//            System.out.println("Requested URL: " + request.getRequestURL().toString());
//            // Continue the filter chain
//            filterChain.doFilter((ServletRequest) request, (ServletResponse) response);
//        }
//    }

//    // Custom filter to log requested URLs
//    public class LoggingFilter extends GenericFilterBean {
//
//        @Override
//        public void doFilter(ServletRequest request,
//                             ServletResponse response,
//                             FilterChain filterChain) throws IOException, jakarta.servlet.ServletException {
//            // Log the requested URL
//            //System.out.println("Requested URL: " + request.getRequestURL().toString());
//            System.out.println("@LoggingFilter:: Requested URL: " + request.getRequestId());
//            // Continue the filter chain
//            // chain.doFilter(request, response);
//        }
//    }

    @Bean
    JwtDecoder jwtDecoder(){
        logger.debug("Inside React APP jwtDecoder()@SecurityConfig:: .....................");
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder(){
        logger.debug("Inside React APP jwtEncoder()@SecurityConfig:: .....................");
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        logger.debug("NimbusJwtEncoder(jwks) = " + new NimbusJwtEncoder(jwks) );
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        // logger.debug("passwordEncoder:....." );
        return new BCryptPasswordEncoder();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//    }
}