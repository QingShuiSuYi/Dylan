package com.weavernorth.xml;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.weavernorth.file.IOUtil;
/**
 * XMLUTIL
 * @author Dylan
 *
 */
public class XMLUtil {
	
	/**
	 * xml转换json
	 * @param xml
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject XML2Json(String xml) throws JSONException{
		JSONObject xmlJSONObj = null;
		try {
			xmlJSONObj = XML.toJSONObject(xml);
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		return xmlJSONObj;
	}
	

	/**
	 * 解析XML
	 * @return
	 */
	public static JSONArray SAXXml() {
		String readFileByLines = IOUtil.readFileByLines("F:/a.txt");
		JSONArray ja = new JSONArray();
		
		try {
			JSONObject xml2Json = XML2Json(readFileByLines);
			JSONArray jsonObject = xml2Json.getJSONObject("result").getJSONObject("data").getJSONArray("row");
			for (int i = 0; i < jsonObject.length(); i++) {
				JSONObject jo = new JSONObject();
				JSONObject jsonObject2 = jsonObject.getJSONObject(i);
				JSONArray jsonArray = jsonObject2.getJSONArray("field");
				for (int j = 0; j < jsonArray.length(); j++) {
					JSONObject jsonObject3 = jsonArray.getJSONObject(j);
					String key = jsonObject3.getString("name");
					String val  ="";
					if(jsonObject3.has("content")){
						val = jsonObject3.getString("content");
					}
					jo.put(key, val);
					ja.put(jo);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ja;
	}

}
