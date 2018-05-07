package cn.promptness.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Lynn
 */
@Data
@ConfigurationProperties(prefix = "spring.httpclient")
public class HttpClientProperties {

    private String agent = "agent";
    private Integer maxConnTotal = 200;
    private Integer maxConnPerRoute = 50;
    private Integer connectTimeOut = 100000;
    private Integer connectionRequestTimeout = 100000;
    private Integer socketTimeOut = 500000;
    private Integer validateAfterInactivity = 1000;
    private Boolean expectContinueEnabled = true;

}
