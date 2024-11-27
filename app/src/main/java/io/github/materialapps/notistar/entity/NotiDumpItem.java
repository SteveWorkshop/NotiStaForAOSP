package io.github.materialapps.notistar.entity;

import androidx.room.Entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity
public class NotiDumpItem extends BaseEntity implements Serializable {
    private String packageName;
    private String category;
    private String channelId;
    private int importance;
    private String key;//todo:暂时不知道有什么用
    private String iconPath;//实际的文件将被转储到磁盘
    private String title;
    private String content;
    private String subText;
    private long when;
    private String bigText;
}
