package com.lab516.support.exception;

public class LoginTimeoutException extends RuntimeException {

	public LoginTimeoutException() {
		super("登陆超时，请重新登陆！");
	}

}
