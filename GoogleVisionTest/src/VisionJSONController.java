import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VisionJSONController{
	public JSONObject setJson(String picLocation) {
		File f = new File(picLocation);
		//將圖片變成Base64
		String encodstring = new EncodeFileToBase64Binary().getBase64(f);
		//建立JSON物件
		JSONObject jsonO1 = new JSONObject();		//當最底層用
		JSONObject jsonO2 = new JSONObject();		//當第二層用
		JSONObject jsonO3 = new JSONObject();		//當最外層用
		JSONObject jsonO4 = new JSONObject();		//當最外層用
		try {
			/*	 //使用encode
			jsonO1.put("content", encodstring);	//put => {key : value}
			jsonO2.put("image", jsonO1);
			jsonO1 = new JSONObject(); 			//清空，也有remove方法
			jsonO1.put("type", "TEXT_DETECTION");
			jsonO2.append("features", jsonO1);		//append => {key : [value]}
			jsonO3.append("requests", jsonO2);*/
			//使用URL
			jsonO1.put("imageUri", "https://i.imgur.com/a87eDVH.jpg");	//put => {key : value}
			jsonO2.put("source", jsonO1);
			jsonO3.put("image", jsonO2);
			jsonO2 = new JSONObject(); 			//清空，也有remove方法
			jsonO2.put("type", "TEXT_DETECTION");
			jsonO3.put("features", new JSONArray().put(jsonO2));		//append => {key : [value]}
			jsonO4.put("requests", new JSONArray().put(jsonO3));
			
			//JSONArray本方法沒用到
/*	要組合的東西
	{
	  "requests": [
	    {
	      "image": {
	        "content": "/9j/7QBEUGhvdG9zaG9...base64-encoded-image-content...fXNWzvDEeYxxxzj/Coa6Bax//Z"
	      },
	      "features": [
	        {
	          "type": "TEXT_DETECTION"
	        }
	      ]
	    }
	  ]
	}
*/
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//return jsonO3;
		return jsonO4;
	}
	public Object getJSON(String resp) {
		String result = "";
		//拆解JSON
		JSONObject responseJSON = null;
		try {
			responseJSON = new JSONObject(resp);
			//利用 http://jsonviewer.stack.hu/ 等分析網站分析TXT上的JSON資料來拆解
			//[]是JSONArray {}是JSONObject，用標頭進入，找到的文字資料用get取標頭
			result = responseJSON.getJSONArray("responses").getJSONObject(0).getJSONObject("fullTextAnnotation").get("text").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}	
		return result;
	}
}
