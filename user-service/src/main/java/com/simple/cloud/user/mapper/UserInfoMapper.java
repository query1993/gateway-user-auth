package com.simple.cloud.user.mapper;

import com.simple.cloud.user.mapper.entity.UserInfoPo;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserInfoMapper {

    int insert(UserInfoPo userInfoPo);

    void update(UserInfoPo userInfoPo);

    void deleteUser(@Param("userId") Long userId,
            @Param("time") Date time);

    UserInfoPo selectById(@Param("userId") Long userId);

    UserInfoPo selectByUserName(@Param("userName") String userName);

    List<UserInfoPo> selectByIds(@Param("userIds") List<Long> userIds);

    Integer selectUsersCountByFilter(@Param("userName") String userName);

    List<UserInfoPo> selectUsersByFilter(@Param("userName") String userName,
            @Param("currentIndex") Integer currentIndex,
            @Param("pageSize") Integer pageSize);
}
