package cn.promptness.config;

import com.google.common.collect.ImmutableList;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author Lynn
 */
@Configuration
@ConditionalOnClass({HttpClient.class})
@EnableConfigurationProperties(HttpClientProperties.class)
public class HttpClientAutoConfiguration {

    private final HttpClientProperties properties;

    public HttpClientAutoConfiguration(HttpClientProperties properties) {
        this.properties = properties;
    }

    /**
     * httpclient bean 的定义
     *
     * @return CloseableHttpClient
     */
    @Bean
    @ConditionalOnMissingBean(HttpClient.class)
    public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager httpClientConnectionManager) {
        return HttpClientBuilder.create()
                .setUserAgent(properties.getAgent())
                .setConnectionManager(httpClientConnectionManager)
                .setConnectionReuseStrategy(new NoConnectionReuseStrategy()).build();
    }

    @Bean
    @ConditionalOnBean(HttpClient.class)
    public RequestConfig requestConfig() {
        //构建requestConfig
        return RequestConfig.custom()
                .setConnectTimeout(properties.getConnectTimeOut())
                .setSocketTimeout(properties.getSocketTimeOut())
                .setConnectionRequestTimeout(properties.getConnectionRequestTimeout())
                .setCookieSpec(CookieSpecs.DEFAULT)
                .setExpectContinueEnabled(properties.getExpectContinueEnabled())
                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(ImmutableList.of(AuthSchemes.BASIC))
                .build();
    }

    /**
     * 定义连接管理器
     *
     * @return PoolingHttpClientConnectionManager
     */
    @Bean(destroyMethod = "close")
    public PoolingHttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(properties.getMaxConnTotal());
        connectionManager.setDefaultMaxPerRoute(properties.getMaxConnPerRoute());
        connectionManager.setValidateAfterInactivity(properties.getValidateAfterInactivity());
        return connectionManager;
    }

    /**
     * 定义清理无效连接
     */
    @Bean(destroyMethod = "shutdown")
    public IdleConnectionEvictor idleConnectionEvictor(PoolingHttpClientConnectionManager httpClientConnectionManager) {
        return new IdleConnectionEvictor(httpClientConnectionManager);
    }

}
