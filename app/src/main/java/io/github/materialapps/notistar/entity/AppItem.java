package io.github.materialapps.notistar.entity;

import java.io.Serializable;

import lombok.Data;

//统一封装，实际上不需要操作数据库
//todo：或许像libchecker一样也行？
@Data
public class AppItem implements Serializable {
    private String appName;
    private String packageInfoName;
    private String iconFileName;
}
