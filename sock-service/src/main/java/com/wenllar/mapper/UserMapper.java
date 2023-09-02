package com.wenllar.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wenllar.dataObject.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<User> selectAllFromDefaultDataSource();

    List<User> selectAllFromDataSource();

    List<User> selectAllFromHikariDataSource();
}
