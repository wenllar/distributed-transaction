package com.wenllar.dataObject;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@TableName("t_user")
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    Long id;
    String userName;
    String password;
}
