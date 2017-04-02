package http.demo;

import java.io.IOException;
import java.net.MalformedURLException;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;



public class WSHttpPost {
	private String mediatype;
	private JSONObject jsonData;
	private HttpPost httpPost;
	private WSHttpPostListener.postCallBackListener postcallback;
	private RequestConfig defaultRequestConfig;
	public WSHttpPost(String uri) {
		// TODO Auto-generated constructor stub
		httpPost=new HttpPost(uri);
		this.mediatype="application/json";
		httpPost.addHeader("encoding","UTF-8");
		defaultRequestConfig = RequestConfig.custom()
			    .setSocketTimeout(5000)
			    .setConnectTimeout(5000)
			    .setConnectionRequestTimeout(5000)
			    .build();
	}
	
	public String getMediatype() {
		return mediatype;
	}

	
	public WSHttpPostListener.postCallBackListener getPostcallback() {
		return postcallback;
	}
	public void setPostcallback(WSHttpPostListener.postCallBackListener postcallback) {
		this.postcallback = postcallback;
	}
	
	public WSHttpPostResult post()
	{
		try {
			WSHttpPostResult result=new WSHttpPostResult();
			CloseableHttpClient hc=HttpClientBuilder.create().build();
			StringEntity body=new StringEntity(jsonData.toJSONString());
			body.setContentType(mediatype);
			httpPost.setEntity(body);
			HttpResponse response=hc.execute(httpPost);
			String josnData=EntityUtils.toString(response.getEntity());
	
			
			Object obj=JSONValue.parse(josnData);
			result.post=this;
			result.response=response;
			result.result=josnData;
			try{
				result.json=(JSONObject)obj;
				//×ª»»Ê§°Ü¾ÍÌø¹ý
			}catch(Exception e){
				
			}
			result.statusCode=response.getStatusLine().getStatusCode();
			if(postcallback!=null)
			{
				postcallback.postCallBack(this,response.getStatusLine().getStatusCode(),josnData,result.json);
			}
			hc.close();
			return result;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
		
	}
	public JSONObject getJsonData() {
		return jsonData;
	}
	public void setJsonData(JSONObject jsonData) {
		this.jsonData = jsonData;
	}
	
}
