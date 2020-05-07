package com.zcy.cn.vo;

import com.zcy.cn.bean.PrivateLetter;
import com.zcy.cn.bean.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivateLetterVO {

    private PrivateLetter privateLetter;

    private Users users;

}
