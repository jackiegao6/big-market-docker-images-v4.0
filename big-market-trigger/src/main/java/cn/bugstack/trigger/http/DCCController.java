package cn.bugstack.trigger.http;

import cn.bugstack.trigger.api.IDCCService;
import cn.bugstack.trigger.api.response.Response;
import cn.bugstack.types.enums.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

/**
 * @author gzc
 * @description 动态配置管理
 */
@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/raffle/dcc/")
public class DCCController implements IDCCService {

    @Autowired(required = false)
    private CuratorFramework client;

    private static final String BASE_CONFIG_PATH = "/big-market-dcc";
    private static final String BASE_CONFIG_PATH_CONFIG = BASE_CONFIG_PATH + "/config";

    /**
     * 更新配置
     */
    @RequestMapping(value = "update_config", method = RequestMethod.GET)
    @Override
    public Response<Boolean> updateConfig(@RequestParam String key, @RequestParam String value) {
        try {
            if (null == client){
                log.warn("DCC 动态配置值变更拒绝，CuratorFramework 未初始化启动「配置未开启」 key:{} value:{}", key, value);
                return Response.<Boolean>builder()
                        .code(ResponseCode.UN_ERROR.getCode())
                        .info(ResponseCode.UN_ERROR.getInfo())
                        .build();
            }
            String keyPath = BASE_CONFIG_PATH_CONFIG.concat("/").concat(key);
            if (null == client.checkExists().forPath(keyPath)) {
                client.create().creatingParentsIfNeeded().forPath(keyPath);
                log.info("DCC 节点监听 base node {} not absent create new done!", keyPath);
            }
            Stat stat = client.setData().forPath(keyPath, value.getBytes(StandardCharsets.UTF_8));
            log.info("DCC 动态配置值变更完成 key:{} value:{} time:{}", key, value, stat.getCtime());
            return Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("DCC 动态配置值变更失败 key:{} value:{}", key, value, e);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

}
