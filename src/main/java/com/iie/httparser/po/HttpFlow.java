package com.iie.httparser.po;

/**
 * HTTP流类，用五元组标识，值为部分首部行，用于标定HTTP类别，建造者模式
 * 
 * @author xutao
 * @date 2016年12月19日
 *
 */
public class HttpFlow {
	
	private String sip;
	private String dip;
	private String sport;
	private String dport;
	
	private String method; // e.g. GET
	private String url; // e.g. /images/footer.gif
	private String host; // e.g. www.google.com
	private String userAgent; // e.g. Mozilla5.0 (Windows; U; Windows NT 6.1; en-US; rv1.9.1.7) Gecko20091221 Firefox3.5.7
	private String contentType; // e.g. text/html; charset=UTF-8
	private String label; // e.g. http class
	
	private HttpFlow(HttpFlowBuilder builder) {
		this.sip = builder.sip;
		this.dip = builder.dip;
		this.sport = builder.sport;
		this.dport = builder.dport;
		this.method = builder.method;
		this.url = builder.url;
		this.host = builder.host;
		this.userAgent = builder.userAgent;
		this.contentType = builder.contentType;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}

	public String getSip() {
		return sip;
	}

	public String getDip() {
		return dip;
	}

	public String getSport() {
		return sport;
	}

	public String getDport() {
		return dport;
	}

	public String getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}

	public String getHost() {
		return host;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public String getContentType() {
		return contentType;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dip == null) ? 0 : dip.hashCode());
		result = prime * result + ((dport == null) ? 0 : dport.hashCode());
		result = prime * result + ((sip == null) ? 0 : sip.hashCode());
		result = prime * result + ((sport == null) ? 0 : sport.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HttpFlow other = (HttpFlow) obj;
		if (dip == null) {
			if (other.dip != null)
				return false;
		} else if (!dip.equals(other.dip))
			return false;
		if (dport == null) {
			if (other.dport != null)
				return false;
		} else if (!dport.equals(other.dport))
			return false;
		if (sip == null) {
			if (other.sip != null)
				return false;
		} else if (!sip.equals(other.sip))
			return false;
		if (sport == null) {
			if (other.sport != null)
				return false;
		} else if (!sport.equals(other.sport))
			return false;
		return true;
	}
	
	public static class HttpFlowBuilder {

		private String sip;
		private String dip;
		private String sport;
		private String dport;
		
		private String method; // e.g. GET
		private String url; // e.g. /images/footer.gif
		private String host; // e.g. www.google.com
		private String userAgent; // e.g. Mozilla5.0 (Windows; U; Windows NT 6.1; en-US; rv1.9.1.7) Gecko20091221 Firefox3.5.7
		private String contentType; // e.g. texthtml; charset=UTF-8
		
		public HttpFlowBuilder(String sip, String dip, String sport, String dport) {
			super();
			this.sip = sip;
			this.dip = dip;
			this.sport = sport;
			this.dport = dport;
		}
		
		public HttpFlowBuilder setMethod(String method) {
			this.method = method.equals("") ? "" : method;
			return this;
		}
		
		public HttpFlowBuilder setUrl(String url) {
			this.url = url.equals("") ? "" : url;
			return this;
		}
		
		public HttpFlowBuilder setHost(String host) {
			this.host = host.equals("") ? "" : host;
			return this;
		}
		
		public HttpFlowBuilder setUserAgent(String userAgent) {
			this.userAgent = userAgent.equals("") ? "" : userAgent;
			return this;
		} 
		
		public HttpFlowBuilder setContentType(String contentType) {
			this.contentType = contentType.equals("") ? "" : contentType;
			return this;
		}
		
		public HttpFlow builder(){
			return new HttpFlow(this);
		}
	}
}
