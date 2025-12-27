package com.cf.system.service;

import com.cf.common.core.domain.entity.SysUser;

/**
 * Keycloak的帮助类
 * WesChen
 */

public interface IKeycloakHelper {

    public boolean CheckUserExist(String userName);

    public boolean AddNewUser(SysUser user);

    public void RemoveUser(String userName);

    public void ChangeUserPassword(String userName, String Password);

    public void DisableUser(String userName);

    public void EnableUser(String userName);
}
