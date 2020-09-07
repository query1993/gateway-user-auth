package com.simple.cloud.user.mapper;

import com.simple.cloud.user.UserServiceApplicationTests;
import com.simple.cloud.user.mapper.entity.UserInfoPo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserInfoMapperTest extends UserServiceApplicationTests {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    public void testSelectById() {
        Long userId = 15L;
        UserInfoPo userInfoPo = userInfoMapper.selectById(userId);
    }
}
