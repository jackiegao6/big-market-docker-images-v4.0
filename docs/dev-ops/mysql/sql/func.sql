use `big_market_01`;

delimiter //

create procedure insert_users2()

begin

    declare i int default 1;

    start transaction ;

    while i <= 100000 do
        insert into raffle_activity_account(user_id, activity_id, total_count, total_count_surplus, day_count, day_count_surplus, month_count, month_count_surplus, create_time, update_time)
            VALUES (
                    concat('u_', i), 100301, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000,now(),now()
                   );
        set i = i + 1;
        end while;

    commit ;

end ;

delimiter ;

call insert_users2();


TRUNCATE TABLE big_market_01.raffle_activity_account;
TRUNCATE TABLE big_market_01.raffle_activity_account_day;
TRUNCATE TABLE big_market_01.raffle_activity_account_month;
TRUNCATE TABLE big_market_01.task;

TRUNCATE TABLE big_market_02.raffle_activity_account;
TRUNCATE TABLE big_market_02.raffle_activity_account_day;
TRUNCATE TABLE big_market_02.raffle_activity_account_month;
TRUNCATE TABLE big_market_02.task;




alter table big_market_01.task add index idx_userId_messageId(user_id, message_id);
alter table big_market_02.task add index idx_userId_messageId(user_id, message_id);

SET GLOBAL innodb_flush_log_at_trx_commit = 2;
SET GLOBAL sync_binlog = 0;

