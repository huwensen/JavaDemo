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
				System.out.println("��ʼִ�У�"+dateTimeFormatter.format(new Date()));	
				// TODO Auto-generated method stub
				WSHttpPost hp=new WSHttpPost("http://122.224.203.221:8184/SalesTrans/rest/salestransaction/salestranslitev61");
				hp.setJsonData(new JSONObject());
				hp.post();
				try {
					System.out.println("����ִ�У�"+dateTimeFormatter.format(new Date()));	
					System.out.println("���ߡ�"+3000+"������");
					Thread.sleep(3000);
					System.out.println("���߽�����"+dateTimeFormatter.format(new Date()));	
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		}).start();
		
	}
}
