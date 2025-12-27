package com.cf.mes.service;

import com.cf.mes.domain.MachineToken;

import java.io.IOException;
import java.util.Map;

public interface IMachineTokenRunner {

    public MachineToken GetToken() throws Exception;

    /**
     * @throws IOException
     */
    Map<String, Object> watchToken() throws Exception;
}
