curl -X PUT "http://192.168.31.25:9200/big_market.user_award_record" -H 'Content-Type: application/json' -d'
{
    "mappings": {
      "properties": {
        "_user_id":{"type": "text"},
        "_activity_id":{"type": "text"},
        "_strategy_id":{"type": "text"},
        "_order_id":{"type": "text"},
        "_award_id":{"type": "text"},
        "_award_title":{"type": "text"},
        "_award_time":{"type": "date"},
        "_award_state":{"type": "text"},
        "_create_time":{"type": "date"},
        "_update_time":{"type": "date"}
      }
    }
}'