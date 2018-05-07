package cn.promptness.config;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.promptness.bean.HttpResult;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Lynn
 */
@Component
public class HttpClientUtil {

    private final CloseableHttpClient httpClient;

    private final RequestConfig requestConfig;

    @Autowired
    public HttpClientUtil(CloseableHttpClient httpClient, RequestConfig requestConfig) {
        this.httpClient = httpClient;
        this.requestConfig = requestConfig;
    }

    public String doGet(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
            }
        }
        return null;
    }

    public String doGet(String url, Map<String, String> param) throws Exception {
        // 定义请求的参数
        URIBuilder builder = new URIBuilder(url);
        param.forEach(builder::setParameter);
        URI uri = builder.build();
        return doGet(uri.toString());
    }

    public HttpResult doPost(String url, Map<String, String> param) throws Exception {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        if (param != null) {
            setEntityData(param, httpPost);
        }
        httpPost.setConfig(requestConfig);
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            // 执行请求
            if (response.getEntity() != null) {
                return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8")));
            }
        }
        return null;
    }

    public HttpResult doPost(String url) throws Exception {
        return doPost(url, null);
    }

    public HttpResult doPostJson(String url, Map<String, String> param) throws Exception {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(this.requestConfig);
        if (param != null) {
            // 构造一个字符串的实体
            StringEntity stringEntity = new StringEntity(JSON.toJSONString(param), ContentType.APPLICATION_JSON);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(stringEntity);
        }
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            // 执行请求
            if (response.getEntity() != null) {
                return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8")));
            }
        }
        return null;
    }

    public <T> T doPostJson(String url, Map<String, String> param, Class<T> clazz) throws Exception {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(this.requestConfig);
        if (param != null) {
            // 构造一个字符串的实体
            StringEntity stringEntity = new StringEntity(JSON.toJSONString(param), ContentType.APPLICATION_JSON);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(stringEntity);
        }
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            // 执行请求
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK && response.getEntity() != null) {
                return JSON.parseObject(EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8")), clazz);
            }
        }
        return null;
    }

    public HttpResult doPostJson(String url, Map<String, String> param, String token) throws Exception {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(this.requestConfig);
        if (param != null) {
            httpPost.setHeader("Authorization", "JWT " + token);
            // 构造一个字符串的实体
            StringEntity stringEntity = new StringEntity(JSON.toJSONString(param), ContentType.APPLICATION_JSON);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(stringEntity);
        }
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            // 执行请求
            if (response.getEntity() != null) {
                return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8")));
            }
        }
        return null;
    }

    public HttpResult doPut(String url, Map<String, String> param) throws IOException {
        // 创建http POST请求
        HttpPut httpPut = new HttpPut(url);
        httpPut.setConfig(this.requestConfig);
        if (param != null) {
            setEntityData(param, httpPut);
        }
        try (CloseableHttpResponse response = httpClient.execute(httpPut)) {
            // 执行请求
            if (response.getEntity() != null) {
                return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8")));
            }
        }
        return null;
    }

    public HttpResult doPut(String url) throws Exception {
        return this.doPut(url, null);
    }

    public HttpResult doDelete(String url, Map<String, String> param) throws Exception {
        param.put("_method", "DELETE");
        return this.doPost(url, param);
    }

    public HttpResult doDelete(String url) throws IOException {
        // 创建http DELETE请求
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setConfig(this.requestConfig);
        try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
            // 执行请求
            if (response.getEntity() != null) {
                return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8")));
            }
        }
        return null;
    }

    /**
     * 设置Entity数据
     *
     * @param param                          参数
     * @param httpEntityEnclosingRequestBase httpclient
     */
    private void setEntityData(Map<String, String> param, HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase) {
        List<NameValuePair> parameters = new ArrayList<>();
        param.forEach((k, v) -> parameters.add(new BasicNameValuePair(k, v)));
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, Charset.forName("UTF-8"));
        // 将请求实体设置到httpPost对象中
        httpEntityEnclosingRequestBase.setEntity(formEntity);
    }

}
