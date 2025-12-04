package cn.bugstack.trigger.job;

import cn.bugstack.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import cn.bugstack.domain.strategy.service.IRaffleAward;
import cn.bugstack.domain.strategy.service.IRaffleStock;
import com.xxl.job.core.handler.annotation.XxlJob;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author gzc
 * @description 更新奖品库存任务；为了不让更新库存的压力打到数据库中，这里采用了redis更新缓存库存，异步队列更新数据库，数据库表最终一致即可。
 */
@Slf4j
@Component()
public class UpdateAwardStockJob {

    @Resource
    private IRaffleStock raffleStock;
    @Resource
    private IRaffleAward raffleAward;
    @Resource
    private ThreadPoolExecutor executor;

    @Timed(value = "updateAwardStockJob", description = "更新奖品库存任务")
    @XxlJob("updateAwardStockJob")
    public void exec() {
        try {
            // 拿到所有open活动 的 所有奖品
            List<StrategyAwardStockKeyVO> awards = raffleAward.queryOpenActivityStrategyAwardList();
            if (null == awards)
                return;

            // 更新各个奖品的库存值
            for (StrategyAwardStockKeyVO strategyAwardStockKeyVO : awards) {
                executor.execute(() -> {
                    try {
                        // 从各个奖品库存的阻塞队列中 take
//                        StrategyAwardStockKeyVO queueStrategyAwardStockKeyVO = raffleStock.takeQueueValue(strategyAwardStockKeyVO.getStrategyId(), strategyAwardStockKeyVO.getAwardId());
                        List<StrategyAwardStockKeyVO> strategyAwardStockKeyVOS = raffleStock.takeQueueValueBatch(strategyAwardStockKeyVO.getStrategyId(), strategyAwardStockKeyVO.getAwardId());
                        if (null == strategyAwardStockKeyVOS)
                            return;

                        int totalCount = strategyAwardStockKeyVOS.size();
//                        raffleStock.updateStrategyAwardStock(queueStrategyAwardStockKeyVO.getStrategyId(), queueStrategyAwardStockKeyVO.getAwardId());
                        raffleStock.updateStrategyAwardStockBatch(strategyAwardStockKeyVO.getStrategyId(), strategyAwardStockKeyVO.getAwardId(), totalCount);

                        log.info("定时任务，更新奖品消耗库存 strategyId:{} awardId:{}", strategyAwardStockKeyVO.getStrategyId(), strategyAwardStockKeyVO.getAwardId());
                    } catch (InterruptedException e) {
                        log.error("定时任务，更新奖品消耗库存失败 strategyId:{} awardId:{}", strategyAwardStockKeyVO.getStrategyId(), strategyAwardStockKeyVO.getAwardId());
                    }
                });
            }
        } catch (Exception e) {
            log.error("定时任务，更新奖品消耗库存失败", e);
        }
    }
}
