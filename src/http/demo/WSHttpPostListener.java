package http.demo;

import net.minidev.json.JSONObject;

public abstract class WSHttpPostListener {
	public interface postCallBackListener {
		void postCallBack(WSHttpPost wsHttpPost,int status, String result, JSONObject json);
	}
}
