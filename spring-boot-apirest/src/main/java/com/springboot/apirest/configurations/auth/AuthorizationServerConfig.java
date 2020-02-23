package com.springboot.apirest.configurations.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {   // se encarga del proceso de autenticacion, desde el proceso de login, crear el token y validarlo

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    @Autowired
    private InfoAditionalToken infoAditionalToken;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {    // configuramos los permisos de nuestros endpoints, rutas de accesso de spring security oauth2.

        security.tokenKeyAccess("permitAll()") // damos permisos a cualquier user para poder autenticarse. para generar el token. ruta = /oauth/token
        .checkTokenAccess("isAuthenticated()");   // endpoint que se encarga de validar el token que se esta enviando
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {    // registramos al cliente con sus credenciales y las credenciales de la aplicacion

        clients.inMemory().withClient("angularapp") // tipo de almacenamiento, registramos un cliente con su clientid
                .secret(passwordEncoder.encode("12345"))
                .scopes("read", "write")// permisos de lectura y escritura
                .authorizedGrantTypes("password", "refresh_token")   // usamos password cuando el user existe en el sistema backend. Authorization code intercambiar un codigo de auotorizacion por un token de accesso por redireccionamiento de url. Implicita enviamos el client id y el password. Refresh_token nos permite conseguir un token de accesso renovado
                .accessTokenValiditySeconds(3600)   // duracion de token
                .refreshTokenValiditySeconds(3600); // renovacion del token
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {  // proceso de autenticacion y validar el token, cada vez que inicamos sesion ingresamos el username y password si todo sale bien realiza la autenticacion, genera el token, se lo entrega al usuario.

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAditionalToken, accessTokenConverter()));

        endpoints.authenticationManager(authenticationManager)  // se encarga del proceso de autenticar el token
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter()) // encargado de manejar de almacenar los datos de autenticacion del user e informacion extra (claims) en el token. se encarga de traducir los datos de acceso para la autenticacion y validar que el token sea valido y su firmas
                .tokenEnhancer(tokenEnhancerChain);
    }

    @Bean
    public JwtTokenStore tokenStore() {     // componente que se encarga de crear el token y almacenar los datos

        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){

        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(JwtConfig.RSA_PRIVATE);    // el que firma ,  con la llave secreta se crea el token. y cada vez que el cliente lo envia al servidor de recursos, este lo verifica el token con la llave secreta para saber si es autentico
        jwtAccessTokenConverter.setVerifierKey(JwtConfig.RSA_PUBLIC);   // el que valida

        return jwtAccessTokenConverter;
    }

}
