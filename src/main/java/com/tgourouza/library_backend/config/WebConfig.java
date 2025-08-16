package com.tgourouza.library_backend.config;

import java.io.InputStream;
import java.net.http.HttpClient;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
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
      RestClient.Builder builder,
      @Value("${openlibrary.base-url:https://openlibrary.org}") String baseUrl,
      @Value("${openlibrary.user-agent:YourApp/1.0 (contact@example.com)}") String userAgent,
      @Value("${openlibrary.truststore.path}") Resource tsResource,
      @Value("${openlibrary.truststore.password}") String tsPass) throws Exception {

    JdkClientHttpRequestFactory requestFactory = null;
    if (tsResource != null && tsResource.exists()) {
      KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
      try (var in = tsResource.getInputStream()) {
        ks.load(in, (tsPass == null ? "" : tsPass).toCharArray());
      }
      var tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      tmf.init(ks);
      var ssl = SSLContext.getInstance("TLS");
      ssl.init(null, tmf.getTrustManagers(), null);
      requestFactory = new JdkClientHttpRequestFactory(HttpClient.newBuilder().sslContext(ssl).build());
    }

    var b = builder
        .baseUrl(baseUrl)
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader(HttpHeaders.USER_AGENT, userAgent);

    if (requestFactory != null)
      b = b.requestFactory(requestFactory);

    return b.build();
  }

  @Bean
  @Qualifier("mymemoryRestClient")
  public RestClient mymemoryRestClient(
      RestClient.Builder builder,
      @Value("${mymemory.base-url}") String baseUrl,
      @Value("${mymemory.user-agent}") String userAgent,
      @Value("${mymemory.truststore.path}") Resource truststore,
      @Value("${mymemory.truststore.password}") String truststorePassword) throws Exception {

    KeyStore ts = KeyStore.getInstance(KeyStore.getDefaultType());
    try (InputStream is = truststore.getInputStream()) {
      ts.load(is, truststorePassword.toCharArray());
    }
    TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
    tmf.init(ts);

    SSLContext sslContext = SSLContext.getInstance("TLS");
    sslContext.init(null, tmf.getTrustManagers(), null);

    HttpClient httpClient = HttpClient.newBuilder().sslContext(sslContext).build();
    var requestFactory = new JdkClientHttpRequestFactory(httpClient);

    return builder
        .baseUrl(baseUrl)
        .requestFactory(requestFactory)
        .defaultHeader(HttpHeaders.USER_AGENT, userAgent)
        .build();
  }

  @Bean
  RestClient nllbClient(RestClient.Builder builder,
      @Value("${nllb.base-url}") String baseUrl) {
    return builder
        .baseUrl(baseUrl)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }
}