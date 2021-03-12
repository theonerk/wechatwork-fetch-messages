package com.rongk.wechatwork;

import java.io.File;
import java.io.FileOutputStream;

import com.google.gson.Gson;
import com.tencent.wework.Finance;

//10000	参数错误，请求参数错误
//10001	网络错误，网络请求错误
//10002	数据解析失败
//10003	系统失败
//10004	密钥错误导致加密失败
//10005	fileid错误
//10006	解密失败
//10007 找不到消息加密版本的私钥，需要重新传入私钥对
//10008 解析encrypt_key出错
//10009 ip非法
//10010 数据过期

public class GetMessages {

	public static int getMessages(long ret, int seq, int limit) throws Exception {
		String encoding = System.getProperty("file.encoding");
		System.out.println("Default System Encoding: " + encoding);
		long sdk = Finance.NewSdk();
		ret = Finance.Init(sdk, "ww68b4548bcf17451a", "1Bo7tx3xg-wIVxBxjSUn9zShRq7B8vplgpiBYtb3mVo");
		if (ret != 0) {
			Finance.DestroySdk(sdk);
			System.out.println("init sdk err ret " + ret);
			return seq;
		}

		// 拉取会话存档
		String proxy = null;
		String passwd = null;
		int timeout = 1000;

		long slice = Finance.NewSlice();
		ret = Finance.GetChatData(sdk, seq, limit, proxy, passwd, timeout, slice);
		if (ret != 0) {
			System.out.println("getchatdata ret " + ret);
			Finance.FreeSlice(slice);
			return seq;
		}
		String messages = Finance.GetContentFromSlice(slice);
		Gson gson = new Gson();
		// System.out.println("-----------------------------------------------------");
		WechatWorkMessages weworkmessmages = gson.fromJson(messages, WechatWorkMessages.class);
		weworkmessmages.getChatdata();

		for (int i = 0; i < weworkmessmages.getChatdata().size(); i++) {
			Message msg = weworkmessmages.getChatdata().get(i);
			// System.out.println(msg);
			String srtDecryptMsg = decryptMessage(msg.getEncrypt_random_key(), msg.getEncrypt_chat_msg(), sdk);
			// System.out.println(srtDecryptMsg);
			DecryptMessage decryptMessage = gson.fromJson(srtDecryptMsg, DecryptMessage.class);
			if (decryptMessage == null || decryptMessage.getMsgtype() == null) {
				continue;
			}
			String fileName = "";
			String sdkfileid = "";
			switch (decryptMessage.getMsgtype()) {
			case "text":

				System.out.println(decryptMessage.getText().getContent());
				break;
			case "emotion":
				fileName = "D:\\message\\files\\emotions\\" + decryptMessage.getMsgid() + ".gif";
				sdkfileid = decryptMessage.getEmotion().getSdkfileid();
				storeMediafile(sdk, sdkfileid, fileName);
				System.out.println(srtDecryptMsg);
				break;
			case "video":
				System.out.println("video");
				fileName = "D:\\message\\files\\videos\\" + decryptMessage.getMsgid() + ".mp4";
				sdkfileid = decryptMessage.getVideo().getSdkfileid();
				storeMediafile(sdk, sdkfileid, fileName);
				System.out.println(srtDecryptMsg);
				break;
			case "image":
				System.out.println("image");
				System.out.println(srtDecryptMsg);
				sdkfileid = decryptMessage.getImage().getSdkfileid();
				fileName = "D:\\message\\files\\images\\" + decryptMessage.getMsgid() + ".jpg";
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
			// System.out.println(seq);
			seq = weworkmessmages.getChatdata().size() + seq;

		}
		Finance.FreeSlice(slice);
		// 每次使用GetChatData拉取存档前需要调用NewSlice获取一个slice，在使用完slice中数据后，还需要调用FreeSlice释放。

		Finance.DestroySdk(sdk);
		return seq;
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
		String result = new String(Finance.GetContentFromSlice(msg).getBytes(), "GB2312");
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
