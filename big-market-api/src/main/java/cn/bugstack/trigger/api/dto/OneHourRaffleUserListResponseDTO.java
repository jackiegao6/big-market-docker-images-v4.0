package cn.bugstack.trigger.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gzc
 * @description 1H内参与活动的部分用户列表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OneHourRaffleUserListResponseDTO implements Serializable {

    /** 用户ID */
    private String userId;
    /** 奖品ID */
    private Integer awardId;
    /** 奖品标题（名称） */
    private String awardTitle;
    /** 中奖时间 */
    private Date awardTime;

}
