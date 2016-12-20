package com.iie.httparser.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.iie.httparser.po.HttpConfig;

/**
 * HttpParser 程序入口
 * 
 * @author xutao
 * @date 2016年12月20日
 *
 */
public class HttpParser {
	
	private Logger logger = Logger.getLogger(HttpParser.class);
	public HttpParser(){
	}
	
	private final String confDir = "G:\\Experiments\\http\\conf\\";
	
	//private final String urlPath = confDir + "url.conf";
	private final String agentPath = confDir + "agent.conf";
	private final String hostPath = confDir + "host.conf";
	private final String contentPath = confDir + "content.conf";
	
	public void doParse() {
		// load the configure and pass into LabelMarker
		logger.info("Loading configures...");
		Map<String, HttpConfig> map = new HashMap<String, HttpConfig>();
		map.put("agent", new HttpConfig("agent", agentPath));
		map.put("host", new HttpConfig("host", hostPath));
		map.put("content", new HttpConfig("content", contentPath));
		
		logger.info("Start producers and consumers...");
		final int nThreads = 5;
		final String dirPath = "G:\\Experiments\\http\\files\\";
		ExecutorService excutor = Executors.newFixedThreadPool(nThreads + 1);
		excutor.submit(new HttpFileReader(dirPath));
		//for(int i = 0; i < nThreads; i++) {
		//excutor.submit(new HttpLabelMarker(map));
		//}
		excutor.shutdown();
		
		for(Entry<String, HttpConfig> entry : map.entrySet()) {
			logger.debug(entry.getKey());
		}
		
		try {
			excutor.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			logger.debug("Tasks wrong...\n" + e.getMessage());
		}
		logger.info("Calculating the percentage...");
		
		int total = 0;
		for(Entry<String, Integer> entry : MessageQueue.getInstance().getMap().entrySet()) {
			logger.info(entry.getKey() + " : " + entry.getValue());
			total += entry.getValue();
		}
		logger.info("Total : " + total);
		logger.info("All tasks down...");
	}
	
	public static void main(String[] args) {
		HttpParser parser = new HttpParser();
		parser.doParse();
	}
}
