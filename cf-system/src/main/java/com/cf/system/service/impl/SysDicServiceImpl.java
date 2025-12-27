package com.cf.system.service.impl;

import com.cf.common.annotation.Lang;
import com.cf.common.constant.MessageCode;
import com.cf.common.core.dic.ICommonDicService;
import com.cf.common.core.domain.entity.SysDic;
import com.cf.common.enums.LangInfoColumnName;
import com.cf.common.enums.LangInfoKeyFieldName;
import com.cf.common.enums.LangInfoTableName;
import com.cf.common.exception.CustomException;
import com.cf.common.utils.DateUtils;
import com.cf.common.utils.ListUtil;
import com.cf.common.utils.MessageUtils;
import com.cf.common.utils.StringUtils;
import com.cf.system.mapper.SysDicMapper;
import com.cf.system.service.ISysDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统字典Service业务层处理
 *
 * @author WesChen
 */
@Service
@Lang(keyFieldName = LangInfoKeyFieldName.DIC_ID_KEY_FILE_NAME, tableName = LangInfoTableName.DIC_TABLE_NAME, columnNames = {LangInfoColumnName.DIC_NAME_COLUMN_NAME})
public class SysDicServiceImpl implements ISysDicService, ICommonDicService {


    @Autowired
    private SysDicMapper sysDicMapper;

    /**
     * 查询系统字典
     *
     * @param dicId 系统字典ID
     * @return 系统字典
     */
    @Override
    public SysDic getSysDicById(Long dicId) {
        return sysDicMapper.getSysDicById(dicId);
    }

    @Override
    public SysDic getSysDicByCode(String dicCode) {
        return sysDicMapper.getSysDicByCode(dicCode);
    }

    @Override
    public List<SysDic> getSysDicListByDicCodes(List<String> dicCodes) {
        dicCodes = ListUtil.getDistinctList(dicCodes);
        if (CollectionUtils.isEmpty(dicCodes)) {
            return new ArrayList<>();
        }
        return sysDicMapper.getSysDicListByDicCodes(dicCodes);
    }

    /**
     * 查询系统字典列表
     *
     * @param sysDic 系统字典
     * @return 系统字典
     */
    @Override
    public List<SysDic> getSysDicList(SysDic sysDic) {
        return sysDicMapper.getSysDicList(sysDic);
    }

    @Override
    public List<SysDic> getSysDicListByDicType(String dicType) {
        SysDic sysDic = new SysDic();
        sysDic.setDicType(dicType);
        return sysDicMapper.getSysDicList(sysDic);
    }

    /**
     * 新增系统字典
     *
     * @param dic 系统字典
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertSysDic(SysDic dic) {
        //check data
        if (StringUtils.isEmpty(dic.getDicCode())) {
            String msg = MessageUtils.message(MessageCode.DIC_DIC_CODE_EMPTY);
            throw new CustomException(msg);
        }

        SysDic sysDic = this.getSysDicByCode(dic.getDicCode());
        if (sysDic != null) {
            String msg = MessageUtils.message(MessageCode.DIC_DIC_CODE_EXISTS, sysDic.getDicCode());
            throw new CustomException(msg);
        }


        dic.setCreateTime(DateUtils.getNowDate());
        return sysDicMapper.insertSysDic(dic);
    }

    /**
     * 修改系统字典
     *
     * @param sysDic 系统字典
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSysDic(SysDic sysDic) {
        sysDic.setUpdateTime(DateUtils.getNowDate());
        return sysDicMapper.updateSysDic(sysDic);
    }

    /**
     * 批量删除系统字典
     *
     * @param dicIds 需要删除的系统字典ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteSysDicByIds(Long[] dicIds) {
        return sysDicMapper.deleteSysDicByIds(dicIds);
    }

    /**
     * 删除系统字典信息
     *
     * @param dicId 系统字典ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteSysDicById(Long dicId) {
        return sysDicMapper.deleteSysDicById(dicId);
    }
}
