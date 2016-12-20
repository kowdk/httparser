package com.iie.httparser.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iie.httparser.po.HttpFlow;
import com.iie.httparser.util.DebugUtil;

/**
 * HTTP日志文件解析类
 * 
 * @author xutao
 * @date 2016年12月20日
 *
 */
public class HttpFileReader implements Runnable {

	private Logger logger = LoggerFactory.getLogger(HttpFileReader.class);
	private String folderPath = "";

	public HttpFileReader(String folderPath) {
		this.folderPath = folderPath;
	}

	/**
	 * 遍历文件夹，返回文件绝对路径
	 * 
	 * @param path
	 * @return
	 */
	private List<String> traverseFolder() {
		List<String> res = new ArrayList<String>();
		File rootDir = new File(folderPath);
		File[] files = rootDir.listFiles();

		if (files == null) {
			logger.debug("folder not exist...");
			return null;
		}

		for (int i = 0; i < files.length; i++) {
			String filePath = files[i].getAbsolutePath();
			res.add(filePath);
		}

		return res;
	}

	/**
	 * 对于每个文件，解析文件
	 * 
	 * @param filePath 
	 */
	private HttpFlow doParse(String filePath) {
		// e.g. 1482213643077_172.16.0.122_44955_68.71.208.11_80_http_.SLOG
		String filename = filePath.substring(filePath.lastIndexOf('\\') + 1, filePath.lastIndexOf(".")); 
		final String splitter = "_";
		String[] strs = filename.split(splitter);
		String sip = strs[1];
		String sport = strs[2];
		String dip = strs[3];
		String dport = strs[4];

		Map<String, String> headers = parseHttpLog(filePath);
		DebugUtil.dumpMapForDebug(headers);
		
		
		HttpFlow flow = new HttpFlow.HttpFlowBuilder(sip, sport, dip, dport).setMethod(headers.get("method"))
				.setUrl(headers.get("url")).setHost(headers.get("host")).setUserAgent(headers.get("user-agent"))
				.setContentType(headers.get("content-type")).builder();
		
		return flow;
	}

	
	
	/**
	 * 解析HTTP日志文件头部
	 * @param filePath
	 * @return
	 */
	private Map<String, String> parseHttpLog(String filePath) {
		final String firstLineMark = "HTTP/1.1";
		Map<String, String> map = new HashMap<String, String>();

		BufferedReader br = null;
		int cnt = 0;
		try {
			br = new BufferedReader(new FileReader(filePath));
			logger.info("Processing file :" + filePath);
			String line = "";
			line = br.readLine();
			String[] components = line.split(" ");
			map.put("method", components[0].trim());
			map.put("url", components[1].trim());
			while ((line = br.readLine()) != null) {
				if(line.equals("")) cnt++;
				if(cnt == 2) break; // terminate condition
				if(line.startsWith(firstLineMark)) continue;
				String[] strs = line.split(":");
				map.put(strs[0].trim().toLowerCase(), strs[1].trim());
			}
		} catch (IOException e) {
			logger.debug(e.getMessage());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.debug(e.getMessage());
			}
		}

		return map;
	}

	public void run() {
		List<String> fileList = traverseFolder();
		if (fileList != null) {
			for (String filePath : fileList) {
				HttpFlow flow = doParse(filePath);
				if (flow != null) {
					MessageQueue.getInstance().getQueue().offer(flow);
				}
			}
		}
	}
}
