package cn.promptness.controller;

import cn.promptness.bean.RestResponse;
import cn.promptness.config.HttpClientUtil;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author : Lynn
 * @Date : 2018-04-21 19:05
 */
@RestController
public class HelloController {

    @Autowired
    HttpClientUtil httpClientUtil;


    @RequestMapping(value = "/",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.GET)
    public ResponseEntity<RestResponse<Object>> hello() throws Exception {
        String doGet = httpClientUtil.doGet("http://www.baidu.com/");
        return ResponseEntity.ok(RestResponse.success(doGet));
    }

}
