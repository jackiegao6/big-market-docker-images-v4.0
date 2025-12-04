package cn.bugstack.test.infrastructure;

import cn.bugstack.domain.strategy.model.valobj.RuleTreeVO;
import cn.bugstack.domain.strategy.repository.IStrategyRepository;
import cn.bugstack.infrastructure.redis.IRedisService;
import cn.bugstack.types.common.Constants;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 策略仓储测试
 * @create 2024-02-03 09:43
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyRepositoryTest {

    @Resource
    private IStrategyRepository strategyRepository;
    @Resource
    private IRedisService redisService;

    @Test
    public void queryRuleTreeVOByTreeId() {
        RuleTreeVO ruleTreeVO = strategyRepository.queryRuleTreeVOByTreeId("tree_lock");
        log.info("测试结果：{}", JSON.toJSONString(ruleTreeVO));
    }

    @Test
    public void test_cacheStrategyAwardCount() {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_KEY + "300001" + Constants.UNDERLINE + "102";
        strategyRepository.cacheStrategyAwardCount(cacheKey, 10);
    }

    @Test
    public void test_subtractionAwardStock() {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_KEY + "300001" + Constants.UNDERLINE + "102";
        strategyRepository.subtractionAwardStock(cacheKey);
    }

    @Test
    public void test_getValue() {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_KEY + "300001" + Constants.UNDERLINE + "102";
        Integer value = redisService.getValue(cacheKey);
        log.info("测试结果：{}", value);
    }

}
