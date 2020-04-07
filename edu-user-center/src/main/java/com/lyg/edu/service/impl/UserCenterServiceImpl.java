package com.lyg.edu.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.lyg.edu.service.UserCenterService;
import com.lyg.edu.entity.UcenterMember;
import com.lyg.edu.mapper.UcenterMemberMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author lyg
 * @since 2020-04-06
 */
@Service
@Transactional
public class UserCenterServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UserCenterService {

    @Override
    @SentinelResource("每日注册统计")
    public Integer countRegisterByDay(String day) {
        String dayStart=day+" 00:00:00";
        String dayEnd=day+" 23:59:59";
        return baseMapper.selectRegisterCount(dayStart,dayEnd);
    }
}
