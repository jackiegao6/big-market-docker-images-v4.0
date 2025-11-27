package cn.bugstack.domain.award.service;

import cn.bugstack.domain.award.model.entity.DistributeAwardEntity;
import cn.bugstack.domain.award.model.entity.UserAwardRecordEntity;

import java.util.List;

/**
 * @author gzc
 * @description 奖品服务接口
 */
public interface IAwardService {

    void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity);

    void saveUserAwardRecordsTen(List<UserAwardRecordEntity> userAwardRecordEntityList);

    List<UserAwardRecordEntity> queryRecentRaffleUsers(Long activityId);

    /**
     * 配送发货奖品
     */
    void distributeAward(DistributeAwardEntity distributeAwardEntity);

}
