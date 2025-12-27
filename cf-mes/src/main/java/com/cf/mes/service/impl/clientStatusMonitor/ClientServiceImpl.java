package com.cf.mes.service.impl.clientStatusMonitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cf.common.annotation.Lang;
import com.cf.common.constant.MessageCode;
import com.cf.common.core.dic.ICommonDicService;
import com.cf.common.core.redis.RedisCache;
import com.cf.common.core.token.ITokenService;
import com.cf.common.enums.LangInfoColumnName;
import com.cf.common.enums.LangInfoKeyFieldName;
import com.cf.common.enums.LangInfoTableName;
import com.cf.common.exception.CustomException;
import com.cf.common.utils.DateUtils;
import com.cf.common.utils.MessageUtils;
import com.cf.common.utils.StringUtils;
import com.cf.common.utils.uuid.IdUtils;
import com.cf.mes.domain.Machine;
import com.cf.mes.domain.MachineFilter;
import com.cf.mes.domain.MachineRegistration;
import com.cf.mes.domain.clientStatusMonitor.Client;
import com.cf.mes.domain.clientStatusMonitor.ClientStatus;
import com.cf.mes.mapper.MachineMapper;
import com.cf.mes.mapper.clientStatusMonitor.ClientMapper;
import com.cf.mes.service.IMachineService;
import com.cf.mes.service.IMachineTokenService;
import com.cf.mes.service.IMachineTypeService;
import com.cf.mes.service.IWorkshopService;
import com.cf.mes.service.clientStatusMonitor.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Description 客户端业务实现
 * @Author WesChen
 * @Date 2021-12-14
 */
@Service
@Lang(keyFieldName = LangInfoKeyFieldName.MACHINE_ID_KEY_FILE_NAME, tableName = LangInfoTableName.MACHINE_TABLE_NAME, columnNames = {LangInfoColumnName.MACHINE_NAME_COLUMN_NAME})
public class ClientServiceImpl implements IClientService {

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private IWorkshopService workshopService;

    @Autowired
    private RedisTemplate redisTemplate;

    private String ClientInfoMATIONRedisKey = "CLIENT_INFOMATION:";

    private String ClientOnLineRedisKey = "CLIENT_ON_LINE:LIST";


    @Override
    @Transactional(readOnly = true)
    public Client getMachineById(Long machineId) {
        Client client = clientMapper.getMachineById(machineId);


        if (client != null) {
            client.setClientStatus(getClientStatusById(machineId));

            client.setWorkshopEntity(workshopService.getWorkshopById((client.getWorkshopId())));

            client.setOnLine(getClineOnLine(client.getWorkshopEntity().getFactoryId(), client.getWorkshopId(), client.getMachineId()));

//            client.setMachineTypeEntity(machineTypeService.getMachineTypeById(client.getMachineTypeId()));
//            if (StringUtils.isNotEmpty(client.getMachineBrand())) {
//                client.setMachineBrandDicEntity(commonDicService.getSysDicByCode(client.getMachineBrand()));
//            }
        }

        return client;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> getMachineListByWorkshopId(Long workshopId) {
        List<Client> clientList = clientMapper.getMachineListByWorkshopId(workshopId);

        for (Client clientItem : clientList) {
            clientItem.setClientStatus(getClientStatusById(clientItem.getMachineId()));

            clientItem.setWorkshopEntity(workshopService.getWorkshopById((clientItem.getWorkshopId())));

            clientItem.setOnLine(getClineOnLine(clientItem.getWorkshopEntity().getFactoryId(), clientItem.getWorkshopId(), clientItem.getMachineId()));

//            clientItem.setMachineTypeEntity(machineTypeService.getMachineTypeById(clientItem.getMachineTypeId()));
//            if (StringUtils.isNotEmpty(clientItem.getMachineBrand())) {
//                clientItem.setMachineBrandDicEntity(commonDicService.getSysDicByCode(clientItem.getMachineBrand()));
//            }

        }

        return clientList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> getMachineListByFactoryId(Long factoryId) {
        List<Client> clientList = clientMapper.getMachineListByFactoryId(factoryId);

        for (Client clientItem : clientList) {
            clientItem.setClientStatus(getClientStatusById(clientItem.getMachineId()));

            clientItem.setWorkshopEntity(workshopService.getWorkshopById((clientItem.getWorkshopId())));

            clientItem.setOnLine(getClineOnLine(clientItem.getWorkshopEntity().getFactoryId(), clientItem.getWorkshopId(), clientItem.getMachineId()));



//            clientItem.setMachineTypeEntity(machineTypeService.getMachineTypeById(clientItem.getMachineTypeId()));
//            if (StringUtils.isNotEmpty(clientItem.getMachineBrand())) {
//                clientItem.setMachineBrandDicEntity(commonDicService.getSysDicByCode(clientItem.getMachineBrand()));
//            }

        }

        return clientList;
    }

    public ClientStatus getClientStatusById(Long machineId) {

        ClientStatus clientStatus = null;

        if (redisTemplate.hasKey(ClientInfoMATIONRedisKey + machineId)) {

            //将最新数据进行转换
            clientStatus = JSON.parseObject(redisTemplate.opsForValue().get(ClientInfoMATIONRedisKey + machineId).toString(), new TypeReference<ClientStatus>() {
            });

        }

        return clientStatus;
    }

    public boolean getClineOnLine(Long factoryId, Long workshopId, Long machineId) {

        boolean result = false;

        if (redisTemplate.hasKey(ClientOnLineRedisKey)) {
            //获取所有在线的值
            List<String> allValueList = redisTemplate.opsForList().range(ClientOnLineRedisKey, 0, -1);

            //是否包含机器的ID
            result = allValueList.contains(factoryId.toString() + "-" + workshopId.toString() + "-" + machineId.toString());
        }

        return result;
    }
}
