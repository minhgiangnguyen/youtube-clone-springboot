package com.petproject.youtubeclone.config;

import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.net.ssl.SSLContext;
import java.time.Duration;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.petproject.youtubeclone.repositories.jpa")
@EnableElasticsearchRepositories(basePackages = "com.petproject.youtubeclone.repositories.elasticsearch")
public class ElasticSearchConfig extends ElasticsearchConfiguration {
//public class ElasticSearchConfig {

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("youtubeclone-search-5526459131.us-east-1.bonsaisearch.net:443")
                .usingSsl(buildSSLContext())
                .withBasicAuth("ddd0b98884","if4r0a5rqg")
                .withConnectTimeout(Duration.ofMillis(10000))
                .withSocketTimeout(Duration.ofMillis(60000))
                .build();

    }

    private static SSLContext buildSSLContext() {
        try {
            return new SSLContextBuilder().loadTrustMaterial(null,TrustAllStrategy.INSTANCE).build();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


}
