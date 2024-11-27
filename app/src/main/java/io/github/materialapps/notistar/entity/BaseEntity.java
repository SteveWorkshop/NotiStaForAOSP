package io.github.materialapps.notistar.entity;

import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.Data;

@Data
public class BaseEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private Boolean isDeleted;
    private Long createTime=System.currentTimeMillis();
    private Long updateTime=System.currentTimeMillis();
}
