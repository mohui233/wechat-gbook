

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * 
 * @author hj
 * 2015-03-12
 * 图灵机器人智能回复
 */
public class TulingUtil {
	
	public static String getBaseMsg() throws Exception{
		String getURL = "https://open.weixin.qq.com/xxxx";
		URL getUrl = new URL(getURL);
		HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
		connection.connect();
		// 取得输入流，并使用Reader读取
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
		StringBuffer sb = new StringBuffer();
		String line = "";
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		reader.close();
		String result = sb.toString();
		System.out.println(result);
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		TulingUtil.getBaseMsg();
	}
}