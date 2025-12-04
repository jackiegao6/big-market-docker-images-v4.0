package cn.bugstack.domain.strategy.service;

import cn.bugstack.domain.strategy.model.valobj.StrategyAwardStockKeyVO;

import java.util.List;

/**
 * @author gzc
 * @description 抽奖库存相关服务，获取库存消耗队列
 */
public interface IRaffleStock {

    /**
     * 获取奖品库存消耗队列
     *
     * @return 奖品库存Key信息
     * @throws InterruptedException 异常
     */
    StrategyAwardStockKeyVO takeQueueValue(Long strategyId, Integer awardId) throws InterruptedException;

    List<StrategyAwardStockKeyVO> takeQueueValueBatch(Long strategyId, Integer awardId) throws InterruptedException;

    /**
     * 更新奖品库存消耗记录
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     */
    void updateStrategyAwardStock(Long strategyId, Integer awardId);

    void updateStrategyAwardStockBatch(Long strategyId, Integer awardId, int totalCount);

}
