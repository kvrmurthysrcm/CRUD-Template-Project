package com.productapi.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
import static org.springframework.security.config.Customizer.withDefaults;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public final RsaKeyProperties rsaKeys;
    // private final AuthenticationLoggingFilter authenticationLoggingFilter = null;

    public SecurityConfig(RsaKeyProperties rsaKeys) {
        this.rsaKeys = rsaKeys;
    }

    // Datasource is automatically created from the application.properties
    @Bean
    JdbcUserDetailsManager users(DataSource dataSource, PasswordEncoder encoder){
        System.out.println("users(): Creating Security Users in db...");

        System.out.println("dataSource: " + dataSource);

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
        System.out.println("admin password:: " + admin.getPassword());

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        try {
            jdbcUserDetailsManager.createUser(user); // this should happen only once...need to figure it out
            jdbcUserDetailsManager.createUser(admin); // this should happen only once...need to figure it out
        }catch(Exception e){
            System.out.println("Exception occurred: " + e.getMessage());
        }
        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        System.out.println("Inside React APP securityFilterChain()@SecurityConfig:: ....................." + httpSecurity.toString());
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( (auth) -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/loginn").permitAll()
                        .requestMatchers("/api/v1/token").permitAll()
                        //.requestMatchers("/static/*").permitAll()
                        .anyRequest().authenticated())
                .logout((logout) -> logout.logoutSuccessUrl("/empui/index.html"))
                // .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt) deprecated..
                 .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                //.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                .build();
    }

    @Bean
    JwtDecoder jwtDecoder(){
        System.out.println("Inside React APP jwtDecoder()@SecurityConfig:: .....................");
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder(){
        System.out.println("Inside React APP jwtEncoder()@SecurityConfig:: .....................");
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        System.out.println("NimbusJwtEncoder(jwks) = " + new NimbusJwtEncoder(jwks) );
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        System.out.println("passwordEncoder:....." );
        return new BCryptPasswordEncoder();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//    }
}