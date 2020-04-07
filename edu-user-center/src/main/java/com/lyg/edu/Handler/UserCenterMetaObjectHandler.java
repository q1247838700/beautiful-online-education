package com.lyg.edu.Handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author lyg
 * @create 2020-03-04-11:37
 */
@Component
public class UserCenterMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("gmtCreate", new Date(), metaObject); //创建时间自行创建
        this.setFieldValByName("gmtModified", new Date(), metaObject); //修改时间自行创建
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified", new Date(), metaObject); //修改时间自行更新
    }
}
