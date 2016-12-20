package com.iie.httparser.po;

/**
 * http分类的类别，枚举类
 * 
 * @author xutao
 * @date 2016年12月19日
 */
public enum HttpLabel {
	WebAccess(0), FileDownload(1), MultiMedia(2), Crawler(3), WebMail(4), Ads(5), WebAttack(7), UnKnown(8);

	private int label;

	private HttpLabel(int label) {
		this.label = label;
	}

	public int getLabel() {
		return label;
	}

	public static HttpLabel enumOf(int i) {
		switch (i) {
		case 0:
			return WebAccess;
		case 1:
			return FileDownload;
		case 2:
			return MultiMedia;
		case 3:
			return Crawler;
		case 4:
			return WebMail;
		case 5:
			return Ads;
		case 6:
			return WebAttack;
		case 7:
			return UnKnown;
		default:
			return null;
		}
	}

}
