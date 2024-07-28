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
                .connectedToLocalhost()
                .usingSsl(buildSSLContext())
                .withBasicAuth("elastic","123456")
                .withConnectTimeout(Duration.ofMillis(10000))
                .withSocketTimeout(Duration.ofMillis(10000))
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
