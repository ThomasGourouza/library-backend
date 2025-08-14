package com.tgourouza.library_backend.config;

import java.io.InputStream;
import java.net.http.HttpClient;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class WikidataRestClientConfig {

  @Bean
  public RestClient wikidataRestClient(
      RestClient.Builder builder,
      @Value("${wikidata.base-url}") String baseUrl,
      @Value("${wikidata.user-agent}") String userAgent,
      @Value("${wikidata.truststore.path}") Resource truststore,
      @Value("${wikidata.truststore.password}") String truststorePassword
  ) throws Exception {

    KeyStore ts = KeyStore.getInstance(KeyStore.getDefaultType()); // JKS
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
        .defaultHeader(HttpHeaders.ACCEPT, "application/sparql-results+json")
        .defaultHeader(HttpHeaders.USER_AGENT, userAgent)
        .build();
  }
}
