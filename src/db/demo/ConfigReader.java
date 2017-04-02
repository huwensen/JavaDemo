package db.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigReader {
	private Map<Object, Map<Object, List<Object>>> map = null;
	/**
	 * 当前Section的引用
	 */
	private String currentSection = null;

	public static void main(String[] args) {
		ConfigReader cr=new ConfigReader("Topic.ini");
		while(true)
		{
		System.out.println(cr.get()+"");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		
	}
	/**
	 * 读取
	 * 
	 * @param path
	 */
	public ConfigReader(String fileName) {
		map = new HashMap<Object, Map<Object, List<Object>>>();
		try {
			String path = /*this.getClass().getResource("/").getPath()+*/fileName;
			File file=new File(path);
			if(!file.exists())
			{
				OutputStreamWriter pw = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
				pw.write("#配置方法如 Key=Value\r\n#编码为：UTF-8");
				pw.flush();
				pw.close();
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF-8") );
			read(reader);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("IO Exception:" + e);
		}

	}

	/**
	 * 读取文件
	 * 
	 * @param reader
	 * @throws IOException
	 */
	private void read(BufferedReader reader) throws IOException {
		String line = null;
		while ((line = reader.readLine()) != null) {
			parseLine(line);
		}
	}

	/**
	 * 转换
	 * 
	 * @param line
	 */
	private void parseLine(String line) {
		line = line.trim();
		// 此部分为注释
		if (line.matches("^\\#.*$")) {
			return;
		} else if (line.matches("^\\[\\S+\\]$")) {
			// section
			String section = line.replaceFirst("^\\[(\\S+)\\]$", "$1");
			addSection(map, section);
		} else if (line.matches("^\\S+=.*$")) {
			// key ,value
			int i = line.indexOf("=");
			String key = line.substring(0, i).trim();
			String value = line.substring(i + 1).trim();
			addKeyValue(map, currentSection, key, value);
		}
	}

	/**
	 * 增加新的Key和Value
	 * 
	 * @param map
	 * @param currentSection
	 * @param key
	 * @param value
	 */
	private void addKeyValue(Map<Object, Map<Object, List<Object>>> map,
			String currentSection, String key, String value) {
		if (!map.containsKey(currentSection)) {
			return;
		}

		Map<Object, List<Object>> childMap = map.get(currentSection);

		if (!childMap.containsKey(key)) {
			List<Object> list = new ArrayList<Object>();
			list.add(value);
			childMap.put(key, list);
		} else {
			childMap.get(key).add(value);
		}
	}

	/**
	 * 增加Section
	 * 
	 * @param map
	 * @param section
	 */
	private void addSection(Map<Object, Map<Object, List<Object>>> map,
			String section) {
		if (!map.containsKey(section)) {
			currentSection = section;
			Map<Object, List<Object>> childMap = new HashMap<Object, List<Object>>();
			map.put(section, childMap);
		}
	}

	/**
	 * 获取配置文件指定Section和指定子键的值
	 * 
	 * @param section
	 * @param key
	 * @return
	 */
	public List<Object> get(String section, String key) {
		if (map.containsKey(section)) {
			return get(section).containsKey(key) ? get(section).get(key) : null;
		}
		return null;
	}

	/**
	 * 获取配置文件指定Section的子键和值
	 * 
	 * @param section
	 * @return
	 */
	public Map<Object, List<Object>> get(String section) {
		return map.containsKey(section) ? map.get(section) : null;
	}

	/**
	 * 获取这个配置文件的节点和值
	 * 
	 * @return
	 */
	public Map<Object, Map<Object, List<Object>>> get() {
		return map;
	}
}
