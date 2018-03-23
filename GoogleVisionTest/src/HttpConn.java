import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/*
 *	需要Import 專案資料夾下 lib 中的兩個Jar
 * 	java-json :用來組以及拆JSON格式 (VisionJSONCreater用到)
 * 	commons-codec-1.7 :在加密成Base64使用 (EncodeFileToBase64Binary用到)
 * 
 */
public class HttpConn {
	//google 地址
	private static final String VISION_URL =
            "https://vision.googleapis.com/v1/images:annotate?";
	//金鑰
	private static final String API_KEY =
            "key=YOURKEY";
	//輸出JSON檔案位置
	private static String saveFileLocation = "E:\\output.txt";
	private static String PictureLocation = "E:/pic/temple4.jpg";
	
	public static void main(String[] args) {
		String result = getVisionText(PictureLocation);
		System.out.println(result);		//印出JSON
	}
	public static String getVisionText(String location) {
		URL serverUrl;
		Object result = null;
		VisionJSONController vJSON = new VisionJSONController();
		try {
			//request
			serverUrl = new URL(VISION_URL + API_KEY);
			URLConnection urlConnection = serverUrl.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection)urlConnection;
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setDoOutput(true);
			//檔案路徑
			BufferedWriter httpRequestBodyWriter = new BufferedWriter(new
	                   OutputStreamWriter(httpConnection.getOutputStream()));
			httpRequestBodyWriter.write(vJSON.setJson(location).toString());
			httpRequestBodyWriter.close();
			
			//String response = httpConnection.getResponseMessage();
			FileWriter fw = new FileWriter(saveFileLocation);
			//如果沒有成功
			if (httpConnection.getInputStream() == null) {
				   System.out.println("No stream");
				   fw.close();
				   return "";
			}
			//response成功
			Scanner httpResponseScanner = new Scanner (httpConnection.getInputStream());
			String resp = "";	//儲存字串
			while (httpResponseScanner.hasNext()) {
			   String line = httpResponseScanner.nextLine();
			   resp += line;
			   fw.write(line + System.getProperty("line.separator"));//換行
			   fw.flush();
			}
			fw.close();
			httpResponseScanner.close();
			//拆解JSON
			result = vJSON.getJSON(resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result.toString();
	}
}
