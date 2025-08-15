package com.tgourouza.library_backend.config;

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
          .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
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
}