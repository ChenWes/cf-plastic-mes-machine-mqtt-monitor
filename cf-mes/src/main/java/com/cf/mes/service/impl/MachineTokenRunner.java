package com.cf.mes.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cf.common.utils.StringUtils;
import com.cf.common.utils.http.HttpUtils;
import com.cf.mes.domain.MachineToken;
import com.cf.mes.service.IMachineTokenRunner;
import com.cf.mes.service.IMachineTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("MachineTokenRunner")
public class MachineTokenRunner implements IMachineTokenRunner {

    @Value("${keycloak.auth-server-url}")
    private String AUTHURL;

    @Value("${keycloak.realm}")
    private String REALM;

    @Value("${tokenRequire.secret}")
    private String SECRETKEY;

    @Value("${tokenRequire.resource}")
    private String CLIENTID;


    @Autowired
    private IMachineTokenService iMachineTokenService;


    @Override
    @PostConstruct
    public MachineToken GetToken() throws Exception {
        String responseToken = null;
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
        urlParameters.add(new BasicNameValuePair("client_id", CLIENTID));
        urlParameters.add(new BasicNameValuePair("client_secret", SECRETKEY));
        //请求数据
        responseToken = sendPost(urlParameters);
        log.info("获取TOKEN数据:{}", responseToken);
        MachineToken machineToken = JSON.parseObject(responseToken, new TypeReference<MachineToken>() {
        });
        log.info("解析TOKEN数据:{}", machineToken);
        iMachineTokenService.SetToken(machineToken);
        return machineToken;
    }

    private String sendPost(List<NameValuePair> urlParameters) throws Exception {

        //生成http请求
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(AUTHURL + "/realms/" + REALM + "/protocol/openid-connect/token");

        //设置参数
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        //发送post请求
        HttpResponse response = client.execute(post);

        //返回数据流
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        //返回数据实体
        StringBuffer result = new StringBuffer();

        //循环读取数据流
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }

    @Override
    public Map<String, Object> watchToken() throws Exception {
        Map<String, Object> res = new HashMap<>();
        // 获取获取token
        MachineToken machineToken = iMachineTokenService.GetToken();
        // 如缓存内没有token,那么就重新获取
        if (machineToken == null || StringUtils.isEmpty(machineToken.getAccess_token())) {
            machineToken = GetToken();
            res.put("message", "Cache machine token is empty!! exec refresh token!!");
            res.put("refreshFlag", machineToken != null && StringUtils.isNotEmpty(machineToken.getAccess_token()));
            return res;
        }
        // 校验校验token
        Map<String, Object> verifyRes = verifyToken(machineToken.getAccess_token());
        boolean isActive = MapUtils.getBoolean(verifyRes, "active");
        if (isActive) {
            res.put("message", "Cache machine token is active!!");
            return res;
        }
        machineToken = GetToken();
        res.put("message", "Cache machine token is inactive!! exec refresh token!!");
        res.put("refreshFlag", machineToken != null && StringUtils.isNotEmpty(machineToken.getAccess_token()));
        return res;
    }

    /**
     * token 检验
     * 当token不可用或过期时返回 active: false
     *
     * @param token
     * @return
     * @throws IOException
     */
    private Map<String, Object> verifyToken(String token) throws IOException {
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("client_id", CLIENTID));
        urlParameters.add(new BasicNameValuePair("client_secret", SECRETKEY));
        urlParameters.add(new BasicNameValuePair("token", token));
        // "http://10.8.0.2:8080/auth/realms/CF/protocol/openid-connect/token/introspect";
        String url = AUTHURL + "/realms/" + REALM + "/protocol/openid-connect/token/introspect";
        String responseBody = HttpUtils.sendPostFormRequest(url, urlParameters);
        if (StringUtils.isEmpty(responseBody)) {
            return new HashMap<>();
        }
        return JSON.parseObject(responseBody, Map.class);
    }

}
