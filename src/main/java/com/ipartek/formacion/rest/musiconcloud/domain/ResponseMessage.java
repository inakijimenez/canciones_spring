package com.ipartek.formacion.rest.musiconcloud.domain;

public class ResponseMessage {
	private String info;

	public ResponseMessage() {
		super();
		this.info = "";
	}

	public ResponseMessage(String info) {
		super();
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "ResponseMessage [info=" + info + "]";
	}
}
