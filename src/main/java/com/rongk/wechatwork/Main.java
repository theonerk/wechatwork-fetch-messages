package com.rongk.wechatwork;

import java.io.File;
import java.io.FileOutputStream;

import com.google.gson.Gson;
import com.tencent.wework.Finance;

public class Main {

	public static void main(String[] args) throws Exception {

		// System.out.println("privKeyPEM" + privKeyPEM);
		long ret = 0;
		String appid = System.getenv("APPID");
		String secret = System.getenv("SECRET");
		long sdk = Finance.NewSdk();
		ret = Finance.Init(sdk, appid, secret);
		System.out.println("init sdk err ret " + ret);
		if (ret != 0) {	
			System.out.println("return after init" + ret);
			Finance.DestroySdk(sdk);
			System.out.println("init sdk err ret " + ret);
			return;
		}

		// 拉取会话存档

		int seq = Integer.parseInt(System.getenv("SEQ"));
		System.out.println("SEQ:" + seq);
		int limit = 1;
		String proxy = null;
		String passwd = null;
		int timeout = 1000;
		while (true) {
			long slice = Finance.NewSlice();
			System.out.println("slice:" + slice);
			ret = Finance.GetChatData(sdk, seq, limit, proxy, passwd, timeout, slice);
			if (ret != 0) {
				System.out.println("getchatdata ret " + ret);
				Finance.FreeSlice(slice);
				return;
			}
			System.out.println("-----------------------------------------------------");
		
			String messages = Finance.GetContentFromSlice(slice);
			System.out.println("messages:"+messages);
			Gson gson = new Gson();
			// System.out.println("-----------------------------------------------------");
			WechatWorkMessages weworkmessmages = gson.fromJson(messages, WechatWorkMessages.class);
			weworkmessmages.getChatdata();

			for (int i = 0; i < weworkmessmages.getChatdata().size(); i++) {
				Message msg = weworkmessmages.getChatdata().get(i);
				// System.out.println(msg);
				String srtDecryptMsg = decryptMessage(msg.getEncrypt_random_key(), msg.getEncrypt_chat_msg(), sdk);
				System.out.println(srtDecryptMsg);
				DecryptMessage decryptMessage = gson.fromJson(srtDecryptMsg, DecryptMessage.class);
				if (decryptMessage == null || decryptMessage.getMsgtype() == null) {
					continue;
				}
				String fileName = "";
				String sdkfileid = "";
				String storeFileFolder = "";
				switch (decryptMessage.getMsgtype()) {
				case "text":
					System.out.println(decryptMessage.getText().getContent());
					break;
				case "emotion":
					fileName = storeFileFolder + "emotions/" + decryptMessage.getMsgid() + ".gif";
					sdkfileid = decryptMessage.getEmotion().getSdkfileid();
					storeMediafile(sdk, sdkfileid, fileName);
					System.out.println(srtDecryptMsg);
					break;
				case "video":
					System.out.println("video");
					fileName = storeFileFolder + "videos/" + decryptMessage.getMsgid() + ".mp4";
					sdkfileid = decryptMessage.getVideo().getSdkfileid();
					storeMediafile(sdk, sdkfileid, fileName);
					System.out.println(srtDecryptMsg);
					break;
				case "image":
					System.out.println("image");
					System.out.println(srtDecryptMsg);
					sdkfileid = decryptMessage.getImage().getSdkfileid();
					fileName = storeFileFolder + "images/" + decryptMessage.getMsgid() + ".jpg";
					storeMediafile(sdk, sdkfileid, fileName);
					System.out.println(srtDecryptMsg);
					break;
				case "weapp":
					System.out.println(decryptMessage.getWeapp().toString());
					break;
				default: // 可选
					System.out.println(srtDecryptMsg);
				}
				System.out.println("-----------------------------------------------------");

			}
			if (weworkmessmages.getChatdata().size() > 0) {
				System.out.println(seq);
				seq++;
				Finance.FreeSlice(slice);
			} else {
				Finance.FreeSlice(slice);
				Thread.sleep(1000);
			}
		}

	}

	public static String decryptMessage(String randomKey, String encrypt_chat_msg, long sdk) throws Exception {
		String encrypt_key = RSAUtil.getPrivateKey(randomKey);
		long msg = Finance.NewSlice();
		int ret = Finance.DecryptData(sdk, encrypt_key, encrypt_chat_msg, msg);
		if (ret != 0) {
			System.out.println("getchatdata ret " + ret);
			Finance.FreeSlice(msg);
			return "";
		}
		String result = new String(Finance.GetContentFromSlice(msg).getBytes(), "UTF-8");
		// System.out.println("decrypt ret:" + ret + " msg:" + result);
		Finance.FreeSlice(msg);
		return result;
	}

	// save media file
	public static void storeMediafile(long sdk, String sdkfileid, String fileName) throws Exception {
		String indexbuf = "";
		while (true) {
			long media_data = Finance.NewMediaData();
			int ret = Finance.GetMediaData(sdk, indexbuf, sdkfileid, null, null, 1000, media_data);
			if (ret != 0) {
				System.out.println("getmediadata ret:" + ret);
				Finance.FreeMediaData(media_data);
				return;
			}
			System.out.printf("getmediadata outindex len:%d, data_len:%d, is_finis:%d\n",
					Finance.GetIndexLen(media_data), Finance.GetDataLen(media_data),
					Finance.IsMediaDataFinish(media_data));
			try {
				// 大于512k的文件会分片拉取，此处需要使用追加写，避免后面的分片覆盖之前的数据。
				FileOutputStream outputStream = new FileOutputStream(new File(fileName), true);
				outputStream.write(Finance.GetData(media_data));
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (Finance.IsMediaDataFinish(media_data) == 1) {
				// 已经拉取完成最后一个分片
				Finance.FreeMediaData(media_data);
				break;
			} else {
				// 获取下次拉取需要使用的indexbuf
				indexbuf = Finance.GetOutIndexBuf(media_data);
				Finance.FreeMediaData(media_data);
			}
		}
	}
}
