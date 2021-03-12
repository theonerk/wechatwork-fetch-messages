# wehatwork-fetch-messages

## useage
微信企业会话存档 

https://open.work.weixin.qq.com/api/doc/90000/90135/91361


## how to use 

1. sh build.sh 
2. docker run --env APPID="your appid" --env SECRET="your secret" --env PK="privateKey"  --env SEQ="0"  --name="wechat-fetch-messages" rongk-wechatwork-messages