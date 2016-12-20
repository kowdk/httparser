package com.iie.httparser.util;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iie.httparser.service.HttpFileReader;

public class DebugUtil {
    private static Logger logger = LoggerFactory.getLogger(DebugUtil.class);
	public static void dumpMapForDebug(Map<String, String> headers){
		for(Entry<String, String> entry : headers.entrySet()) {
			logger.debug(entry.getKey() + ":" + entry.getValue());
		}
	}

}
