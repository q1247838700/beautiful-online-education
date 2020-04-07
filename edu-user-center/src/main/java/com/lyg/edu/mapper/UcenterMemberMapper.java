package com.lyg.edu.mapper;

import com.lyg.edu.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author lyg
 * @since 2020-04-06
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

   int selectRegisterCount(@Param("start") String dayStart, @Param("end") String dayEnd);
}
