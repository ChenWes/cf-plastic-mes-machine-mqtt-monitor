package com.cf.framework.web.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.cf.framework.web.domain.server.Sys;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component("KeycloakService")
public class KeycloakService {

    @Value("${keycloak.auth-server-url}")
    private String AUTHURL;

    @Value("${keycloak.realm}")
    private String REALM;

    @Value("${tokenRequire.secret}")
    private String SECRETKEY;

    @Value("${tokenRequire.resource}")
    private String CLIENTID;


    public String getToken() {

        String responseToken = null;

        try {

            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
            urlParameters.add(new BasicNameValuePair("client_id", CLIENTID));
            urlParameters.add(new BasicNameValuePair("client_secret", SECRETKEY));

            //请求数据
            responseToken = sendPost(urlParameters);


            log.info("获取自动生成Token数据" + responseToken);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseToken;
    }

    public String getByRefreshToken(String refreshToken) {

        String responseToken = null;
        try {

            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("grant_type", "refresh_token"));
            urlParameters.add(new BasicNameValuePair("client_id", CLIENTID));
            urlParameters.add(new BasicNameValuePair("refresh_token", refreshToken));
            urlParameters.add(new BasicNameValuePair("client_secret", SECRETKEY));

            responseToken = sendPost(urlParameters);

        } catch (Exception e) {
            e.printStackTrace();

        }

        return responseToken;
    }

//    public int createUserInKeyCloak(UserDTO userDTO) {
//
//        int statusId = 0;
//        try {
//
//            UsersResource userRessource = getKeycloakUserResource();
//
//            UserRepresentation user = new UserRepresentation();
//            user.setUsername(userDTO.getUserName());
//            user.setEmail(userDTO.getEmailAddress());
//            user.setFirstName(userDTO.getFirstName());
//            user.setLastName(userDTO.getLastName());
//            user.setEnabled(true);
//
//            // Create user
//            Response result = userRessource.create(user);
//            log.info("Keycloak create user response code>>>>" + result.getStatus());
//
//            statusId = result.getStatus();
//
//            if (statusId == 201) {
//
//                String userId = result.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
//
//                log.info("User created with userId:" + userId);
//
//                // Define password credential
//                CredentialRepresentation passwordCred = new CredentialRepresentation();
//                passwordCred.setTemporary(false);
//                passwordCred.setType(CredentialRepresentation.PASSWORD);
//                passwordCred.setValue(userDTO.getPassword());
//
//                // Set password credential
//                userRessource.get(userId).resetPassword(passwordCred);
//
//                // set role
//                RealmResource realmResource = getRealmResource();
//                RoleRepresentation savedRoleRepresentation = realmResource.roles().get("user").toRepresentation();
//                realmResource.users().get(userId).roles().realmLevel().add(Arrays.asList(savedRoleRepresentation));
//
//                log.info("Username==" + userDTO.getUserName() + " created in keycloak successfully");
//
//            }
//
//            else if (statusId == 409) {
//                log.info("Username==" + userDTO.getUserName() + " already present in keycloak");
//
//            } else {
//                log.info("Username==" + userDTO.getUserName() + " could not be created in keycloak");
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//
//        return statusId;
//
//    }

    // after logout user from the keycloak system. No new access token will be
    // issued.
    public void logoutUser(String userId) {

        UsersResource userRessource = getKeycloakUserResource();

        userRessource.get(userId).logout();

    }

    // Reset passowrd
    public void resetPassword(String newPassword, String userId) {

        UsersResource userResource = getKeycloakUserResource();

        // Define password credential
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(newPassword.toString().trim());

        // Set password credential
        userResource.get(userId).resetPassword(passwordCred);

    }

    private UsersResource getKeycloakUserResource() {

        Keycloak kc = KeycloakBuilder.builder().serverUrl(AUTHURL).realm("master").username("admin").password("admin")
                .clientId("admin-cli").resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();

        RealmResource realmResource = kc.realm(REALM);
        UsersResource userRessource = realmResource.users();

        return userRessource;
    }

//    private RealmResource getRealmResource() {
//
//        Keycloak kc = KeycloakBuilder.builder().serverUrl(AUTHURL).realm("master").username("admin").password("admin")
//                .clientId("admin-cli").resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
//                .build();
//
//        RealmResource realmResource = kc.realm(REALM);
//
//        return realmResource;
//
//    }

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
}
