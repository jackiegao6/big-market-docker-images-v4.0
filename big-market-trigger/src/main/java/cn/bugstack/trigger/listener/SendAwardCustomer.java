package cn.bugstack.trigger.listener;

import cn.bugstack.domain.award.event.SendAwardMessageEvent;
import cn.bugstack.domain.award.model.entity.DistributeAwardEntity;
import cn.bugstack.domain.award.service.IAwardService;
import cn.bugstack.domain.task.service.ITaskService;
import cn.bugstack.types.event.BaseEvent;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gzc
 * @description 用户奖品记录消息消费者
 */
@Slf4j
@Component
public class SendAwardCustomer {

    @Value("${spring.rabbitmq.topic.send_award}")
    private String topic;

    @Resource
    private IAwardService awardService;

    @Resource
    private ITaskService taskService;

    @Timed(value = "SendAwardCustomer", description = "用户奖品记录消息消费者")
    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.send_award}"))
    public void listener(String message) {
        try {
            BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage> eventMessage = JSON.parseObject(message, new TypeReference<BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage>>() {
            }.getType());
            SendAwardMessageEvent.SendAwardMessage sendAwardMessage = eventMessage.getData();
            Integer awardId = sendAwardMessage.getAwardId();
            String messageId = eventMessage.getId();
            String userId = sendAwardMessage.getUserId();
            if (awardId == 101){
                // 发放奖品
                DistributeAwardEntity distributeAwardEntity = new DistributeAwardEntity();
                distributeAwardEntity.setUserId(sendAwardMessage.getUserId());
                distributeAwardEntity.setOrderId(sendAwardMessage.getOrderId());
                distributeAwardEntity.setAwardId(sendAwardMessage.getAwardId());
                distributeAwardEntity.setAwardConfig(sendAwardMessage.getAwardConfig());
                awardService.distributeAward(distributeAwardEntity);

                log.info("监听用户奖品发送消息，随机积分发奖完成 topic: {} message: {}", topic, message);
            }
            // 更新数据库记录，task 任务表
            taskService.updateTaskSendMessageCompleted(userId, messageId);
        } catch (Exception e) {
            log.error("监听用户奖品发送消息，消费失败 topic: {} message: {}", topic, message);
            throw e;
        }
    }

}
