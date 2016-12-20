package com.iie.httparser.po;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP首部行配置类，用于匹配
 * 
 * @author xutao
 * @date 2016年12月19日
 *
 */
public class HttpConfig {

	private String type; // 配置文件类型，url/host/agent/content
	private String configPath; // 配置文件路径
	private Properties prop;
	
	private Logger logger = LoggerFactory.getLogger(HttpConfig.class);

	public HttpConfig(String type, String configPath) {
		this.configPath = configPath;
		this.type = type;
		loadConfig();
	}

	public String getType(){
		return type;
	}
	
	/**
	 * 加载配置文件
	 */
	public void loadConfig() {
		prop = new Properties();
		FileReader fr = null;
		try {
			fr = new FileReader(configPath);
			prop.load(fr);
		} catch (IOException e) {
			logger.debug("Load config error, path : " + configPath);
		}
	}
	
	/**
	 * 返回配置集合
	 * @return
	 */
	public Properties getSet(){
		return prop;
	}
	
	
	/**
	 * 判断入参是否包含集合中的字符串
	 * @param line
	 * @return
	 */
	public String isContainsConf(String line){
		Set<Object> keySet = prop.keySet();
		for(Object obj : keySet) {
			String key = obj.toString().toLowerCase();
			if(line.toLowerCase().contains(key)) {
				return key;
			}
		}
		return null;
	}

	public String getLabel(String key){
		return prop.getProperty(key);
	}
	
}
