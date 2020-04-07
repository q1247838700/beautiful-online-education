package com.lyg.edu.service;

import com.lyg.edu.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author lyg
 * @since 2020-04-06
 */
public interface UserCenterService extends IService<UcenterMember> {

    Integer countRegisterByDay(String day);
}
