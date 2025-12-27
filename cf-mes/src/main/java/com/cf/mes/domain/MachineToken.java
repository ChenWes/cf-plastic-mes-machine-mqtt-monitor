package com.cf.mes.domain;


import lombok.Data;

/**
 * 机边客户端使用的Token对象
 *
 *
 * @author weschen
 */
@Data
public class MachineToken {
    private static final long serialVersionUID = 1L;

    public  String access_token;

    public  String token_type;

    public  int expires_in;
}
