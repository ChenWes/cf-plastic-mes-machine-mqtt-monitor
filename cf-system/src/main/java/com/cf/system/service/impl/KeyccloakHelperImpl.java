package com.cf.system.service.impl;

import com.cf.common.core.domain.entity.SysUser;
import com.cf.system.service.IKeycloakHelper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ContextResolver;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class KeyccloakHelperImpl implements IKeycloakHelper {

    /**
     * Keycloak服务器地址
     */
    @Value("${keycloak.auth-server-url}")
    private String ServerUrl;

    /**
     * 域，操作的域
     */
    @Value("${keycloak.realm}")
    private String Realm;

    /**
     * 超级用户名
     */
    @Value("${mykeycloak.root-user}")
    private String RootUser;

    /**
     * 超级用户密码
     */
    @Value("${mykeycloak.root-password}")
    private String RootPassword;

    /**
     * 根域为master
     */
    private String MasterRealm = "master";

    /**
     * 根域的客户端ID
     */
    private String MasterClientId = "admin-cli";


    /**
     * 获取某个域的用户资源
     *
     * @return
     */
    public UsersResource GetKeycloakUsersResource() {
        /**
         * 超级用户登陆至根域
         */
        Keycloak kc = KeycloakBuilder.builder()
                .serverUrl(ServerUrl)
                .realm(MasterRealm)
                .username(RootUser)
                .password(RootPassword)
                .clientId(MasterClientId)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build().register(new ContextResolver<ObjectMapper>() {
                    // keycloak 兼容 26.3.2
                    private final ObjectMapper mapper = new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 忽略未知字段

                    @Override
                    public ObjectMapper getContext(Class<?> type) {
                        return mapper;
                    }
                }))
                .build();

        /**
         * 用户资源则是某个指定域的
         */
        UsersResource userResource = kc.realm(Realm).users();

        return userResource;
    }

    /**
     * 根据用户名获取用户
     *
     * @param userName
     * @return
     */
    public UserRepresentation GetUserRepresentationByUserName(String userName) {
        /**
         * 返回用户资源
         */
        UsersResource usersResource = GetKeycloakUsersResource();

        /**
         * 找出是否有该用户
         */
        List<UserRepresentation> findUserList = usersResource.search(userName);
        //用户名全量匹配
        if (findUserList != null) {
            for (UserRepresentation userRepresentation : findUserList) {
                if (userRepresentation.getUsername().equals(userName)) {
                    return userRepresentation;
                }
            }
        }
        return null;
    }

    @Override
    public boolean CheckUserExist(String userName) {
        UserRepresentation userRepresentation = GetUserRepresentationByUserName(userName);
        if (userRepresentation != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean AddNewUser(SysUser user) {
        boolean returnFlag = false;

        /**
         * 返回用户资源
         */
        UsersResource usersResource = GetKeycloakUsersResource();

        //创建新用户
        UserRepresentation newUser = new UserRepresentation();

        // 设置用户名
        newUser.setUsername(user.getUserCode());
        // 邮箱
        if (user.getEmail() != null && !user.getEmail().trim().equals("")) {
            newUser.setEmail(user.getEmail());
        }
        // 设置启用该用户
        newUser.setEnabled(true);

        // 证书代表
        CredentialRepresentation credential = new CredentialRepresentation();
        // 证书类型是密码
        credential.setType(CredentialRepresentation.PASSWORD);
        // 设置用户密码到证书
        credential.setValue(user.getPassword());
        // 非临时密码
        credential.setTemporary(false);
        List<CredentialRepresentation> credentials = new ArrayList<>();
        credentials.add(credential);
        // 设置用户证书
        newUser.setCredentials(credentials);


        //创建用户
        Response response = usersResource.create(newUser);


        //创建用户响应
        Response.StatusType createUserStatus = response.getStatusInfo();
        URI location = response.getLocation();
        if ("Created".equals(createUserStatus.toString())) {
            log.info("创建用户成功！");
            log.info("创建用户的URI：" + location);

            returnFlag = true;
        }

        return returnFlag;
    }

    @Override
    public void RemoveUser(String userName) {
        /**
         * 返回用户资源
         */
        UsersResource usersResource = GetKeycloakUsersResource();

        // 查找用户
        UserRepresentation findUser = GetUserRepresentationByUserName(userName);

        if (findUser != null) {
            //更新用户
            usersResource.get(findUser.getId()).remove();
        }
    }

    @Override
    public void ChangeUserPassword(String userName, String Password) {
        /**
         * 返回用户资源
         */
        UsersResource usersResource = GetKeycloakUsersResource();

        //查找用户
        UserRepresentation findUser = GetUserRepresentationByUserName(userName);

        if (findUser != null) {
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            // 设置用户密码
            credential.setValue(Password);
            credential.setTemporary(false);
            List<CredentialRepresentation> credentials = new ArrayList<>();
            credentials.add(credential);
            // 用户验证手段
            findUser.setCredentials(credentials);


            //更新用户
            usersResource.get(findUser.getId()).update(findUser);
        }

    }

    @Override
    public void DisableUser(String userName) {
        /**
         * 返回用户资源
         */
        UsersResource usersResource = GetKeycloakUsersResource();

        //查找用户
        UserRepresentation findUser = GetUserRepresentationByUserName(userName);

        if (findUser != null) {
            // 设置禁用该用户
            findUser.setEnabled(false);

            //更新用户
            usersResource.get(findUser.getId()).update(findUser);
        }

    }

    @Override
    public void EnableUser(String userName) {
        /**
         * 返回用户资源
         */
        UsersResource usersResource = GetKeycloakUsersResource();

        //查找用户
        UserRepresentation findUser = GetUserRepresentationByUserName(userName);

        if (findUser != null) {
            // 设置启用该用户
            findUser.setEnabled(true);

            //更新用户
            usersResource.get(findUser.getId()).update(findUser);
        }
    }
}
