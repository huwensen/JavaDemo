package http.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.minidev.json.JSONObject;

public class HttpDemo {
	static SimpleDateFormat		dateFormatter		= new SimpleDateFormat("yyyyMMdd");
	static SimpleDateFormat		dateTimeFormatter	= new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			public void run() {
				while(true)
				{
				System.out.println("开始执行："+dateTimeFormatter.format(new Date()));	
				// TODO Auto-generated method stub
				WSHttpPost hp=new WSHttpPost("http://122.224.203.221:8184/SalesTrans/rest/salestransaction/salestranslitev61");
				hp.setJsonData(new JSONObject());
				hp.post();
				try {
					System.out.println("结束执行："+dateTimeFormatter.format(new Date()));	
					System.out.println("休眠【"+3000+"】毫秒");
					Thread.sleep(3000);
					System.out.println("休眠结束："+dateTimeFormatter.format(new Date()));	
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		}).start();
		
	}
}
