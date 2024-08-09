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
@EnableElasticsearchRepositories(
        basePackages = "com.petproject.youtubeclone.repositories.elasticsearch",
        repositoryFactoryBeanClass = ElasticsearchRepositoryFactoryBean.class
)
public class ElasticSearchConfig extends ElasticsearchConfiguration {
//public class ElasticSearchConfig {

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()

                .connectedTo("df668bcc889d4b9d843fad585d2b9c48.us-central1.gcp.cloud.es.io:9243")
                .usingSsl()
                .withBasicAuth("enterprise_search","123456")
                .withConnectTimeout(Duration.ofMillis(60000)) // Increased timeout for testing
                .withSocketTimeout(Duration.ofMillis(180000)) // Increased socket timeout for testing
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
