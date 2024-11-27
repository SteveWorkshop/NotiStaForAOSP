package io.github.materialapps.notistar.entity.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 用于展示通知信息，设置日程的传递
 * 示例：
 * 【12306】XXX购票成功，8月24日K1109次，北京丰台站13:15开。详情点击s.12306.cn/s/g/aaaaa
 * @author MaterialApps
 */

//todo:联网查详细时刻表
@Data
@ToString
public class TrainTicketVO implements Serializable {
    private Long uuid;
    private String name;
    private String trainName;
    private Date departure;
    private String departureStation;
}
