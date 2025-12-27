package com.cf.framework.strategy;

import com.cf.common.constant.Constants;
import com.cf.common.enums.RequestClientTypeEnum;
import com.cf.common.utils.ServletUtils;
import com.cf.common.utils.StringUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author coder-ren
 * @date 2024/12/25 10:40
 */
@Slf4j
@Component
public class UserWhiteListStrategyService {

    /**
     * 机边客户端用户白名单，用户名前缀
     */
    private static String WHITE_LIST_USER_CODE_PREFIX = "service-account-";

    /**
     * 路径白名单
     */
    // @Value("${authentication.static.white_list.user_codes}")
    private List<String> WHITE_LIST_URL_PATHS = Lists.newArrayList(
            "/mes/machineInterface",
            "/mes/qcClientInterface",
            "/mes/qcDashboardInterface",
            "/mes/dashboardInterface");

    /**
     * 匹配用户策略
     *
     * @param userCode
     * @return
     */
    public boolean matchStrategy(String userCode) {
        return matchHeaderStrategy() ||
                matchWhiteListUserStrategy(userCode) ||
                matchWhiteListPathStrategy();
    }

    /**
     * 请求头匹配
     *
     * @return
     */
    private boolean matchHeaderStrategy() {
        String clientType = ServletUtils.getRequest().getHeader(Constants.CLIENT_TYPE_HEADER);
        if (StringUtils.isEmpty(clientType)) {
            log.error("==> UserWhiteListStrategyService match strategy failure!! clientType is empty!!");
            return false;
        }
        // 请求客户端类型
        RequestClientTypeEnum clientTypeEnum = RequestClientTypeEnum.getEnum(clientType);
        if (RequestClientTypeEnum.UNKNOWN.equals(clientTypeEnum)) {
            return false;
        }
        return clientTypeEnum.isAllowGenUser();
    }

    /**
     * 匹配用户白名单策略
     *
     * @param userCode
     * @return
     */
    private boolean matchWhiteListUserStrategy(String userCode) {
        if (StringUtils.isEmpty(userCode)) {
            log.error("==> UserWhiteListStrategyService match strategy failure!! userCode is empty!!");
            return false;
        }
        // 用户白名单
        if (!userCode.startsWith(WHITE_LIST_USER_CODE_PREFIX)) {
            return false;
        }
        return true;
    }

    /**
     * 匹配白名单路径
     *
     * @return
     */
    private boolean matchWhiteListPathStrategy() {
        String path = ServletUtils.getRequest().getRequestURI();
        if (StringUtils.isEmpty(path)) {
            log.error("==> UserWhiteListStrategyService match strategy path!! path is empty!!");
            return false;
        }
        // 路径白名单
        long count = WHITE_LIST_URL_PATHS.stream().filter(prefix -> path.startsWith(prefix)).count();
        if (count == 0) {
            return false;
        }
        return true;
    }

}
