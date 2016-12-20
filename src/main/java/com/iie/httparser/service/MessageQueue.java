package com.iie.httparser.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.iie.httparser.po.HttpFlow;

/**
 * 模拟消息队列，队列存放待处理文件路径
 * 
 * @author xutao
 * @date 2016年12月20日
 *
 */
public class MessageQueue {
	
	private static MessageQueue instance = null;
	private static Queue<HttpFlow> queue = new ConcurrentLinkedQueue<HttpFlow>();
	private static Map<String, Integer> map = new HashMap<String, Integer>();
	
	private MessageQueue() {
		
	}

	// 返回单例
	public static MessageQueue getInstance() {
		if(instance == null) {
			synchronized (MessageQueue.class) {
				if(instance == null) {
					instance = new MessageQueue();
					queue = new ConcurrentLinkedQueue<HttpFlow>();
					map = new HashMap<String, Integer>();
				}
			}
		}
		return instance;
	}
	
	// 返回消息队列
	public Queue<HttpFlow> getQueue() {
		return queue;
	}
	
	public Map<String, Integer> getMap() {
		return map;
	}
}
