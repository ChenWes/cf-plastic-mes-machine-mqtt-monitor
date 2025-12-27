package com.cf.mes.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.cf.common.annotation.Lang;
import com.cf.common.constant.MessageCode;
import com.cf.common.constant.RabbitMqConstants;
import com.cf.common.core.dic.ICommonDicService;
import com.cf.common.core.domain.entity.SysDic;
import com.cf.common.core.token.ITokenService;
import com.cf.common.enums.LangInfoColumnName;
import com.cf.common.enums.LangInfoKeyFieldName;
import com.cf.common.enums.LangInfoTableName;
import com.cf.common.exception.CustomException;
import com.cf.common.utils.DateUtils;
import com.cf.common.utils.MessageUtils;
import com.cf.common.utils.StringUtils;
import com.cf.common.utils.uuid.IdUtils;
import com.cf.mes.domain.*;
import com.cf.mes.domain.clientStatusMonitor.ClientStatus;
import com.cf.mes.domain.dto.OrganInfoDto;
import com.cf.mes.mapper.MachineMapper;
import com.cf.mes.service.IMachineService;
import com.cf.mes.service.IMachineTokenService;
import com.cf.mes.service.IMachineTypeService;
import com.cf.mes.service.IWorkshopService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description 机器业务实现
 * @Author ccw
 * @Date 2021/5/19
 */
@Slf4j
@Service
@Lang(keyFieldName = LangInfoKeyFieldName.MACHINE_ID_KEY_FILE_NAME, tableName = LangInfoTableName.MACHINE_TABLE_NAME, columnNames = {LangInfoColumnName.MACHINE_NAME_COLUMN_NAME})
public class MachineServiceImpl implements IMachineService {

    @Autowired
    private MachineMapper machineMapper;

    @Autowired
    private IWorkshopService workshopService;

    @Autowired
    private IMachineTypeService machineTypeService;

    @Autowired
    private ITokenService tokenService;

    @Autowired
    private ICommonDicService commonDicService;

    @Autowired
    private IMachineTokenService iMachineTokenService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisTemplate redisTemplate;


    private String ClientInfoMATIONRedisKey = "CLIENT_INFOMATION:";

    private String ClientOnLineRedisKey = "CLIENT_ON_LINE:LIST";

    /**
     * 查询机器
     *
     * @param machineId 机器ID
     * @return 机器
     */
    @Override
    @Transactional(readOnly = true)
    public Machine getMachineById(Long machineId) {
        Machine machine = machineMapper.getMachineById(machineId);
        if (machine != null) {
            machine.setWorkshopEntity(workshopService.getWorkshopById((machine.getWorkshopId())));
            machine.setMachineTypeEntity(machineTypeService.getMachineTypeById(machine.getMachineTypeId()));
            if (StringUtils.isNotEmpty(machine.getMachineBrand())) {
                machine.setMachineBrandDicEntity(commonDicService.getSysDicByCode(machine.getMachineBrand()));
            }

            //获取机器是否在线及状态
            machine.setOnLine(getClineOnLine(machine.getWorkshopEntity().getFactoryId(), machine.getWorkshopId(), machine.getMachineId()));
            machine.setClientStatus(getClientStatusById(machine.getMachineId()));

        }
        return machine;
    }

    @Override
    public Machine getSlimMachineById(Long machineId) {
        return machineMapper.getMachineById(machineId);
    }

    @Override
    public List<Machine> getMachineByIds(Long[] machineIds) {

        List<Machine> machineList = machineMapper.getMachineByIds(machineIds);

//        for (Machine machineItem : machineList) {
//            if (machineItem != null) {
//                machineItem.setWorkshopEntity(workshopService.getWorkshopById((machineItem.getWorkshopId())));
//                machineItem.setMachineTypeEntity(machineTypeService.getMachineTypeById(machineItem.getMachineTypeId()));
//            }
//        }

        return machineList;
    }

    @Override
    public Machine getMachineByIdForJobOrderDetails(Long machineId) {
        Machine machine = machineMapper.getMachineById(machineId);
        return machine;
    }

    @Override
    public Machine getMachineWithDifferentLang(Machine machine) {
        return machine;
    }

    /**
     * 查询机器
     *
     * @param machineCode
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Machine getMachineByCode(String machineCode) {
        Machine machine = machineMapper.getMachineByCode(machineCode);
        if (machine != null) {
            machine.setWorkshopEntity(workshopService.getWorkshopById((machine.getWorkshopId())));
            machine.setMachineTypeEntity(machineTypeService.getMachineTypeById(machine.getMachineTypeId()));
            if (StringUtils.isNotEmpty(machine.getMachineBrand())) {
                machine.setMachineBrandDicEntity(commonDicService.getSysDicByCode(machine.getMachineBrand()));
            }
        }
        return machine;
    }

    /**
     * 通过机器注册码查询机器
     *
     * @param registrationCode
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Machine getMachineByRegistrationCode(String registrationCode) {
        /**
         * 处理注册码
         */
        String processRegistrationCode = registrationCode.replaceAll("-", "").toLowerCase();
        Machine machine = machineMapper.getMachineByRegistrationCode(processRegistrationCode);
        if (machine != null) {
            machine.setWorkshopEntity(workshopService.getWorkshopById((machine.getWorkshopId())));
            machine.setMachineTypeEntity(machineTypeService.getMachineTypeById(machine.getMachineTypeId()));
            if (StringUtils.isNotEmpty(machine.getMachineBrand())) {
                machine.setMachineBrandDicEntity(commonDicService.getSysDicByCode(machine.getMachineBrand()));
            }
        }
        return machine;
    }

    /**
     * 查询机器列表
     *
     * @param machine 机器
     * @return 机器集合
     */
    @Override
    @Transactional(readOnly = true)
    public List<Machine> getMachineList(Machine machine) {
        List<Machine> machineList = machineMapper.getMachineList(machine);
        for (Machine machineItem : machineList) {
            machineItem.setWorkshopEntity(workshopService.getWorkshopById((machineItem.getWorkshopId())));
            machineItem.setMachineTypeEntity(machineTypeService.getMachineTypeById(machineItem.getMachineTypeId()));
            if (StringUtils.isNotEmpty(machineItem.getMachineBrand())) {
                machineItem.setMachineBrandDicEntity(commonDicService.getSysDicByCode(machineItem.getMachineBrand()));
            }

            //获取机器是否在线及状态
            machineItem.setOnLine(getClineOnLine(machineItem.getWorkshopEntity().getFactoryId(), machineItem.getWorkshopId(), machineItem.getMachineId()));
            machineItem.setClientStatus(getClientStatusById(machineItem.getMachineId()));
        }
        return machineList;
    }

    @Override
    public List<Machine> getMachineListByIds(List<Long> machineIds) {
        List<Machine> machineList = machineMapper.getMachineListByIds(machineIds);

        // 获取车间信息
        List<Long> workshopIds = machineList.stream().map(Machine::getWorkshopId).collect(Collectors.toList());
        List<Workshop> workshopList = workshopService.getWorkshopWithFactoryListByIds(workshopIds);
        Map<Long, Workshop> workshopMap = workshopList.stream().collect(Collectors.toMap(Workshop::getWorkshopId, Function.identity()));

        // 获取机器类型信息
        List<Long> machineTypeIds = machineList.stream().map(Machine::getMachineTypeId).collect(Collectors.toList());
        List<MachineType> machineTypeList = machineTypeService.getMachineTypeListByIds(machineTypeIds);
        Map<Long, MachineType> machineTypeMap = machineTypeList.stream().collect(Collectors.toMap(MachineType::getMachineTypeId, Function.identity()));
        // 获取品牌信息
        List<String> dicCodes = machineList.stream().map(Machine::getMachineBrand).collect(Collectors.toList());
        List<SysDic> brandList = commonDicService.getSysDicListByDicCodes(dicCodes);
        Map<String, SysDic> brandMap = brandList.stream().collect(Collectors.toMap(SysDic::getDicCode, Function.identity()));

        // 封装附带信息
        for (Machine machineItem : machineList) {
            machineItem.setWorkshopEntity(workshopMap.get((machineItem.getWorkshopId())));
            machineItem.setMachineTypeEntity(machineTypeMap.get(machineItem.getMachineTypeId()));
            if (StringUtils.isNotEmpty(machineItem.getMachineBrand())) {
                machineItem.setMachineBrandDicEntity(brandMap.get(machineItem.getMachineBrand()));
            }
            //获取机器是否在线及状态
            machineItem.setOnLine(getClineOnLine(machineItem.getWorkshopEntity().getFactoryId(), machineItem.getWorkshopId(), machineItem.getMachineId()));
            machineItem.setClientStatus(getClientStatusById(machineItem.getMachineId()));
        }

        return machineList;
    }

    @Override
    public List<Machine> getMachineListWithSingleByIds(List<Long> machineIds) {
        return machineMapper.getMachineListByIds(machineIds);
    }

    @Override
    public List<Machine> getMachines(Machine machine) {
        List<Machine> machineList = machineMapper.getMachineList(machine);
        if (CollectionUtils.isEmpty(machineList)) {
            return new ArrayList<>();
        }

        // 获取车间信息
        List<Long> workshopIds = machineList.stream().map(Machine::getWorkshopId).collect(Collectors.toList());
        List<Workshop> workshopList = workshopService.getWorkshopWithFactoryListByIds(workshopIds);
        // 获取机器类型信息
        List<Long> machineTypeIds = machineList.stream().map(Machine::getMachineTypeId).collect(Collectors.toList());
        List<MachineType> machineTypeList = machineTypeService.getMachineTypeListByIds(machineTypeIds);
        // 获取品牌信息
        List<String> dicCodes = machineList.stream().map(Machine::getMachineBrand).collect(Collectors.toList());
        List<SysDic> brandList = commonDicService.getSysDicListByDicCodes(dicCodes);

        // 封装附带信息
        Workshop workshop;
        MachineType machineType;
        SysDic sysDic;
        for (Machine machineItem : machineList) {
            workshop = workshopList.stream().filter(r -> r.getWorkshopId().equals(machineItem.getWorkshopId()))
                    .findFirst().orElse(null);
            machineItem.setWorkshopEntity(workshop);
            machineType = machineTypeList.stream().filter(r -> r.getMachineTypeId().equals(machineItem.getMachineTypeId()))
                    .findFirst().orElse(null);
            machineItem.setMachineTypeEntity(machineType);
            if (StringUtils.isNotEmpty(machineItem.getMachineBrand())) {
                sysDic = brandList.stream().filter(r -> r.getDicCode().equals(machineItem.getMachineBrand()))
                        .findFirst().orElse(null);
                machineItem.setMachineBrandDicEntity(sysDic);
            }
        }

        return machineList;
    }

    @Override
    public List<Machine> getMachineListWithClientStatus(Machine machine) {
        // long start = System.currentTimeMillis();
        // List<Machine> machineList = getMachines(machine);
        // long end = System.currentTimeMillis();
        // log.debug("==> getMachineList time: {}", end - start);
        // // redis获取机器状态
        // List<Long> machineIdList = machineList.stream().map(Machine::getMachineId).collect(Collectors.toList());
        // List<ClientStatus> clientStatusList = clientStatusService.getClientStatusListByIds(machineIdList);
        // ClientStatus clientStatus;
        // for (Machine machineItem : machineList) {
        //     clientStatus = clientStatusList.stream().filter(r -> r.getMachineId().equals(String.valueOf(machineItem.getMachineId())))
        //             .findFirst().orElse(null);
        //     machineItem.setClientStatus(clientStatus);
        // }
        // // redis中获取机器是否在线,并设置 machine.online属性
        // clientStatusService.getMachineClientsOnLineStatus(machineList);
        // long end1 = System.currentTimeMillis();
        // log.debug("==> getMachineListWithClientStatus time: {}", end1 - end);
        // return machineList;
        return null;
    }

    @Override
    public List<Machine> getRegisterMachineList() {
        return machineMapper.getRegisterMachineList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Machine> getMachineListByFilter(MachineFilter machineFilter) {
        List<Machine> machineList = machineMapper.getMachineListByFilter(machineFilter);
        for (Machine machineItem : machineList) {
            machineItem.setWorkshopEntity(workshopService.getWorkshopById((machineItem.getWorkshopId())));
            machineItem.setMachineTypeEntity(machineTypeService.getMachineTypeById(machineItem.getMachineTypeId()));
            if (StringUtils.isNotEmpty(machineItem.getMachineBrand())) {
                machineItem.setMachineBrandDicEntity(commonDicService.getSysDicByCode(machineItem.getMachineBrand()));
            }
        }
        return machineList;
    }


    /**
     * 初始化数据
     * 第一步清除列表，第二步将数据库中的加入至数据中
     */
    @Override
    @PostConstruct
    public void getMachineListIntoCache() {
        List<Machine> machineList = machineMapper.getMachineList(null);
        //清除
        iMachineTokenService.InitializeMachineRegistrationCodeList();
        for (Machine machineItem : machineList) {
            //重新插入
            iMachineTokenService.InsertMachineRegistrationCodeList(machineItem.getMachineId(), machineItem.getRegistrationCode());
        }
    }

    /**
     * 根据车间ids查询机器信息列表
     *
     * @param workshopIds 车间ids
     * @return 机器信息集合
     */
    @Override
    public List<Machine> getMachineListByWorkshopIds(Long[] workshopIds) {
        return machineMapper.getMachineListByWorkshopIds(workshopIds);
    }

    @Override
    public List<Machine> getMachineListByWorkshopId(Long workshopId) {
        List<Machine> machineList = machineMapper.getMachineListByWorkshopId(workshopId);
        for (Machine machineItem : machineList) {
            machineItem.setWorkshopEntity(workshopService.getWorkshopById((machineItem.getWorkshopId())));
            machineItem.setMachineTypeEntity(machineTypeService.getMachineTypeById(machineItem.getMachineTypeId()));
            if (StringUtils.isNotEmpty(machineItem.getMachineBrand())) {
                machineItem.setMachineBrandDicEntity(commonDicService.getSysDicByCode(machineItem.getMachineBrand()));
            }
        }
        return machineList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Machine> getMachineListByFactoryId(Long factoryId) {
        List<Machine> machineList = machineMapper.getMachineListByFactoryId(factoryId);
        for (Machine machineItem : machineList) {
            machineItem.setWorkshopEntity(workshopService.getWorkshopById((machineItem.getWorkshopId())));
            machineItem.setMachineTypeEntity(machineTypeService.getMachineTypeById(machineItem.getMachineTypeId()));
            if (StringUtils.isNotEmpty(machineItem.getMachineBrand())) {
                machineItem.setMachineBrandDicEntity(commonDicService.getSysDicByCode(machineItem.getMachineBrand()));
            }

            //获取机器是否在线及状态
            machineItem.setOnLine(getClineOnLine(machineItem.getWorkshopEntity().getFactoryId(), machineItem.getWorkshopId(), machineItem.getMachineId()));
            machineItem.setClientStatus(getClientStatusById(machineItem.getMachineId()));
        }
        return machineList;
    }

    /**
     * 根据机器类型ids查询机器信息列表
     *
     * @param machineTypeIds 机器类型ids
     * @return 机器信息集合
     */
    @Override
    public List<Machine> getMachineListByMachineTypeIds(Long[] machineTypeIds) {
        return machineMapper.getMachineListByMachineTypeIds(machineTypeIds);
    }

    @Override
    public List<Machine> getMachineListByMachineTypeId(Long machineTypeId) {
        List<Machine> machineList = machineMapper.getMachineListByMachineTypeId(machineTypeId);
        for (Machine machineItem : machineList) {
            machineItem.setWorkshopEntity(workshopService.getWorkshopById((machineItem.getWorkshopId())));
            machineItem.setMachineTypeEntity(machineTypeService.getMachineTypeById(machineItem.getMachineTypeId()));
            if (StringUtils.isNotEmpty(machineItem.getMachineBrand())) {
                machineItem.setMachineBrandDicEntity(commonDicService.getSysDicByCode(machineItem.getMachineBrand()));
            }
        }
        return machineList;
    }

    /**
     * 新增机器
     *
     * @param machine 机器
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertMachine(Machine machine) {
        //check data
        if (StringUtils.isEmpty(machine.getMachineCode())) {
            String msg = MessageUtils.message(MessageCode.MACHINE_MACHINE_CODE_EMPTY);
            throw new CustomException(msg);
        }
        Machine checkObj = machineMapper.getMachineByCode(machine.getMachineCode());
        if (checkObj != null) {
            String msg = MessageUtils.message(MessageCode.MACHINE_MACHINE_CODE_EXISTS, checkObj.getMachineCode());
            throw new CustomException(msg);
        }
        /**
         * 生成机器注册码
         */
        String getRegistrationCodeId = IdUtils.fastSimpleUuid();
        machine.setRegistrationCode(getRegistrationCodeId);
        machine.setCreateBy(tokenService.getUserCode());
        machine.setCreateTime(DateUtils.getNowDate());
        int rows = machineMapper.insertMachine(machine);

        //插入后，将机器与注册码数据插入至缓存中
        Machine findMachine = machineMapper.getMachineByCode(machine.getMachineCode());
        boolean resultFlag = iMachineTokenService.InsertMachineRegistrationCodeList(findMachine.getMachineId(), findMachine.getRegistrationCode());

        return rows;
    }

    /**
     * 修改机器
     *
     * @param machine 机器
     * @return 结果
     */
    @Override
    public int updateMachine(Machine machine) {
        /**
         * 新增时已经插入注册码，修改时一定存在注册码，所以该逻辑取消
         */
//        if(machine.getRegistrationCode() == null || !machine.getRegistrationCode().trim().equals("")){
//            /**
//             * 生成机器注册码
//             */
//            String getRegistrationCodeId = IdUtils.fastSimpleUuid();
//            machine.setRegistrationCode(getRegistrationCodeId);
//        }
        machine.setUpdateBy(tokenService.getUserCode());
        machine.setUpdateTime(DateUtils.getNowDate());
        return machineMapper.updateMachine(machine);
    }

    /**
     * 注册机器
     *
     * @param machine
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int registrationMachine(MachineRegistration machine) throws IOException, TimeoutException {
        String processRegistrationCode = machine.getRegistrationCode().replaceAll("-", "").toLowerCase();
        machine.setRegistrationCode(processRegistrationCode);
        machine.setMachineIp(machine.getMachineIp());
        machine.setMacAddress(machine.getMacAddress());
        //没有就增加队列
        ConnectionFactory connectionFactory = rabbitTemplate.getConnectionFactory();
        Connection connection = connectionFactory.createConnection();
        Channel channel = connection.createChannel(false);
        String queueName = RabbitMqConstants.CLIENT_QUEUE_NAME + machine.getMachineId();
        String routingKey = RabbitMqConstants.SEND_CLIENT_COMMAND_ROUTING_KEY + machine.getMachineId();
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, RabbitMqConstants.SEND_CLIENT_COMMAND_EXCHANGE_NAME, routingKey);
        channel.close();
        connection.close();

        // 初始化机器状态日志
        //machineStatusLogService.initMachineStatusLog(machine.getMachineId());

        return machineMapper.registrationMachine(machine);
    }

    /**
     * 删除机器
     *
     * @param machineId 机器ID
     * @return 结果
     */
    @Override
    public int deleteMachineById(Long machineId) {

        Machine findMachine = machineMapper.getMachineById(machineId);
        //删除
        boolean resultFlag = iMachineTokenService.RemoveMachineRegistrationCodeList(findMachine.getMachineId(), findMachine.getRegistrationCode());

        return machineMapper.deleteMachineById(machineId);
    }

    /**
     * 批量删除机器
     *
     * @param machineIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMachineByIds(Long[] machineIds) {

        for (Long machineId : machineIds) {

            Machine findMachine = machineMapper.getMachineById(machineId);
            //删除
            boolean resultFlag = iMachineTokenService.RemoveMachineRegistrationCodeList(findMachine.getMachineId(), findMachine.getRegistrationCode());
        }


        return machineMapper.deleteMachineByIds(machineIds);
    }

    @Override
    public List<Machine> getReportMachineList(Long factoryId, Long workshopId, Long machineTypeId, List<Long> machineIds) {
        return machineMapper.getReportMachineList(factoryId, workshopId, machineTypeId, machineIds);
    }


    @Cached(name = "MACHINE_ORGAN_INFO:MACHINE:", key = "#machineId", expire = 12, timeUnit = TimeUnit.HOURS, cacheType = CacheType.REMOTE)
    @Override
    public OrganInfoDto selectOrganizationInfo(Long machineId) {
        return machineMapper.selectOrganizationInfo(machineId);
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
