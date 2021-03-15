# wehatwork-fetch-messages

## useage
微信企业会话存档 

https://open.work.weixin.qq.com/api/doc/90000/90135/91361


## how to use 

1. sh build.sh 
2. docker run --env APPID="your appid" --env SECRET="your secret" --env PK="privateKey"  --env SEQ="0"  --name="wechat-fetch-messages" rongk-wechatwork-messages


## how to generate private.pem 

```bash
# gereate private.pem and public pem
openssl genrsa -out private.pem 2048  && openssl rsa -in private.pem -pubout -out public.pem
# convert pkcs1 pem to pkcs8 
# in java sdk, pkcs8 is required 
openssl pkcs8 -topk8 -inform PEM -in app_private_key.pem -outform pem -nocrypt -out pkcs8.pem  
```