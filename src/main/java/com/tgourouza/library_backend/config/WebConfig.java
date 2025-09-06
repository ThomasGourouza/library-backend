package com.tgourouza.library_backend.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    @Qualifier("openLibraryRestClient")
    public RestClient openLibraryRestClient(
            @Value("${openlibrary.base-url}") String baseUrl,
            @Value("${openlibrary.user-agent}") String userAgent,
            @Value("${openlibrary.truststore.path}") Resource tsResource,
            @Value("${openlibrary.truststore.password}") String tsPass) throws Exception {
        var builder = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.USER_AGENT, userAgent);
        // dev
        javax.net.ssl.TrustManager[] trustAll = new javax.net.ssl.TrustManager[] {
                new javax.net.ssl.X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[0];
                    }

                    public void checkClientTrusted(java.security.cert.X509Certificate[] c, String a) {
                    }

                    public void checkServerTrusted(java.security.cert.X509Certificate[] c, String a) {
                    }
                }
        };
        javax.net.ssl.SSLContext ssl = javax.net.ssl.SSLContext.getInstance("TLS");
        ssl.init(null, trustAll, new java.security.SecureRandom());
        javax.net.ssl.SSLParameters params = new javax.net.ssl.SSLParameters();
        params.setEndpointIdentificationAlgorithm(null);
        java.net.http.HttpClient insecureHttpClient = java.net.http.HttpClient.newBuilder()
                .sslContext(ssl)
                .sslParameters(params)
                .version(java.net.http.HttpClient.Version.HTTP_1_1) // proxy-friendly
                .followRedirects(java.net.http.HttpClient.Redirect.NORMAL)
                .build();
        builder = builder.requestFactory(
                new org.springframework.http.client.JdkClientHttpRequestFactory(insecureHttpClient));
        return builder.build();
    }

    @Bean
    @Qualifier("mymemoryRestClient")
    public RestClient mymemoryRestClient(
            @Value("${mymemory.base-url}") String baseUrl,
            @Value("${mymemory.user-agent}") String userAgent,
            @Value("${mymemory.truststore.path}") Resource truststore,
            @Value("${mymemory.truststore.password}") String truststorePassword) throws Exception {
        var builder = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.USER_AGENT, userAgent);
        // dev
        javax.net.ssl.TrustManager[] trustAll = new javax.net.ssl.TrustManager[] {
                new javax.net.ssl.X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[0];
                    }

                    public void checkClientTrusted(java.security.cert.X509Certificate[] c, String a) {
                    }

                    public void checkServerTrusted(java.security.cert.X509Certificate[] c, String a) {
                    }
                }
        };
        javax.net.ssl.SSLContext ssl = javax.net.ssl.SSLContext.getInstance("TLS");
        ssl.init(null, trustAll, new java.security.SecureRandom());
        javax.net.ssl.SSLParameters params = new javax.net.ssl.SSLParameters();
        params.setEndpointIdentificationAlgorithm(null);
        java.net.http.HttpClient insecureHttpClient = java.net.http.HttpClient.newBuilder()
                .sslContext(ssl)
                .sslParameters(params)
                .version(java.net.http.HttpClient.Version.HTTP_1_1)
                .followRedirects(java.net.http.HttpClient.Redirect.NORMAL)
                .build();
        builder = builder
                .requestFactory(new org.springframework.http.client.JdkClientHttpRequestFactory(insecureHttpClient));
        return builder.build();
    }

    @Bean
    @Qualifier("wikidataRestClient")
    public RestClient wikidataRestClient(
            @Value("${wikidata.base-url}") String baseUrl,
            @Value("${wikidata.user-agent}") String userAgent,
            @Value("${wikidata.truststore.path}") Resource truststore,
            @Value("${wikidata.truststore.password}") String truststorePassword) throws Exception {
        var builder = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, "application/sparql-results+json")
                .defaultHeader(HttpHeaders.USER_AGENT, userAgent);
        // dev
        javax.net.ssl.TrustManager[] trustAll = new javax.net.ssl.TrustManager[] {
                new javax.net.ssl.X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[0];
                    }
                    public void checkClientTrusted(java.security.cert.X509Certificate[] c, String a) {
                    }
                    public void checkServerTrusted(java.security.cert.X509Certificate[] c, String a) {
                    }
                }
        };
        javax.net.ssl.SSLContext ssl = javax.net.ssl.SSLContext.getInstance("TLS");
        ssl.init(null, trustAll, new java.security.SecureRandom());
        javax.net.ssl.SSLParameters params = new javax.net.ssl.SSLParameters();
        params.setEndpointIdentificationAlgorithm(null); // disable hostname verification
        java.net.http.HttpClient insecureHttpClient = java.net.http.HttpClient.newBuilder()
                .sslContext(ssl)
                .sslParameters(params)
                .version(java.net.http.HttpClient.Version.HTTP_1_1)
                .followRedirects(java.net.http.HttpClient.Redirect.NORMAL)
                .build();
        builder = builder
                .requestFactory(new org.springframework.http.client.JdkClientHttpRequestFactory(insecureHttpClient));
        return builder.build();
    }

    @Bean
    @Qualifier("libreTranslateClient")
    public RestClient libreTranslateClient(
            RestClient.Builder builder,
            @Value("${libretranslate.base-url}") String baseUrl) {

        return builder
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
