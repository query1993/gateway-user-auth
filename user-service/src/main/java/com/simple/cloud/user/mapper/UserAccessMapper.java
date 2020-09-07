package com.simple.cloud.user.mapper;

import com.simple.cloud.user.mapper.entity.UserAccessPo;
import org.apache.ibatis.annotations.Param;

public interface UserAccessMapper {

    void insert(UserAccessPo userAccessPo);

    UserAccessPo selectByUserIdAndIp(@Param("userId") Long userId, @Param("loginIp") String loginIp);

    void update(UserAccessPo userAccessPo);

    void updateUserLogout(UserAccessPo userAccessPo);

    UserAccessPo selectByToken(@Param("token") String token);
}
