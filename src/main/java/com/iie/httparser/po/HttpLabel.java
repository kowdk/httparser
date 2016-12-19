package com.iie.httparser.po;

/**
 * http分类的类别，枚举类
 * 
 * @author xutao
 * @date 2016年12月19日
 */
public enum HttpLabel {
	WebAccess(1), FileDownload(2), MultiMedia(3), Crawler(4), WebMail(5), Ad(6), WebAttack(7);

	private int label;

	private HttpLabel(int label) {
		this.label = label;
	}

	public int getLabel() {
		return label;
	}

	public static HttpLabel enumOf(int i) {
		switch (i) {
		case 1:
			return WebAccess;
		case 2:
			return FileDownload;
		case 3:
			return MultiMedia;
		case 4:
			return Crawler;
		case 5:
			return WebMail;
		case 6:
			return Ad;
		case 7:
			return WebAttack;
		default:
			return null;
		}
	}

}
