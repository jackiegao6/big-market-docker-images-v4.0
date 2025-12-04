package cn.bugstack.infrastructure.elasticsearch;

import cn.bugstack.infrastructure.elasticsearch.po.UserAwardRecord;

import java.util.List;

public interface IElasticSearchUserAwardRecordDao {

    // es 的 jdbc试用期只有一个月 及时更新
    List<UserAwardRecord> queryUserAwardRecordList(String activityId);

}
