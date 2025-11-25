package cn.bugstack.infrastructure.elasticsearch;

import cn.bugstack.infrastructure.elasticsearch.po.UserAwardRecord;

import java.util.List;

public interface IElasticSearchUserAwardRecordDao {

    List<UserAwardRecord> queryUserAwardRecordList();

}
