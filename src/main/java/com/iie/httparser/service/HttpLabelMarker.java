package com.iie.httparser.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iie.httparser.po.HttpConfig;
import com.iie.httparser.po.HttpFlow;
import com.iie.httparser.po.HttpLabel;

/**
 * 
 * HTTP标定类，匹配多个头部配置文件
 * @author xutao
 * @date 2016年12月20日
 *
 */
public class HttpLabelMarker implements Runnable{

	private Map<String, HttpConfig> confs;
	private Logger logger = LoggerFactory.getLogger(HttpLabelMarker.class);
	private final String outDir = "G:\\Experiments\\http\\out\\";
	
	public HttpLabelMarker(Map<String, HttpConfig> confs) {
		this.confs = confs;
	}

	public void run() {
		
		//HttpConfig urlConf = confs.get("url");
		HttpConfig hostConf = confs.get("host");
		HttpConfig agentConf = confs.get("agent");
		HttpConfig contentConf = confs.get("content");
		String outputPath = outDir + Thread.currentThread().getName() + ".log";
		BufferedWriter fw = null;
		try {
			fw = new BufferedWriter(new FileWriter(outputPath));
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}
		while(!MessageQueue.getInstance().getQueue().isEmpty()) {
			HttpFlow flow = MessageQueue.getInstance().getQueue().poll();
			// match the configure and set the label
			String key = "";
			String label = "";
			if((key = contentConf.isContainsConf(flow.getContentType())) != null) {
				label = contentConf.getLabel(key);
			} else if((key = hostConf.isContainsConf(flow.getHost())) != null) {
				label = hostConf.getLabel(key);
			} else if((key = agentConf.isContainsConf(flow.getUserAgent())) != null) {
				label = agentConf.getLabel(key);
			} else {
				// To-do task : url
				label = HttpLabel.UnKnown.toString();
			}
			flow.setLabel(label);
			MessageQueue.getInstance().getMap().put(label, MessageQueue.getInstance().getMap().get(label)+1);
			
			try {
				fw.write(flow.toString());
			} catch (IOException e) {
				logger.debug(e.getMessage());
			}
		}
		
		try {
			fw.close();
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}
	}
}
