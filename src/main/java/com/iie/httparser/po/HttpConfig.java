package com.iie.httparser.po;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

/**
 * HTTP首部行配置类，用于匹配
 * 
 * @author xutao
 * @date 2016年12月19日
 *
 */
public class HttpConfig {

	private String configPath; // 配置文件路径
	private Set<String> configSet; // 配置文件存放集合

	public HttpConfig(String configPath) {
		this.configPath = configPath;
	}

	/**
	 * 加载配置文件
	 */
	public void loadConfig() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(configPath));
			String line = "";
			while ((line = br.readLine()) != null) {
				configSet.add(line.toLowerCase());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 返回配置集合
	 * @return
	 */
	public Set<String> getSet(){
		return configSet;
	}
	
	
	/**
	 * 判断入参是否包含集合中的字符串
	 * @param line
	 * @return
	 */
	public boolean isContainsConnf(String line){
		for(String s : configSet) {
			if(line.toLowerCase().contains(s)) {
				return true;
			}
		}
		return false;
	}

}
