/**
 * Copyright (c) 2019, Sinlmao (888@1st.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.sinlmao.commons.network.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * HTTP Response类
 *
 * @program Sinlmao Commons Network Utils
 * @description HTTP Response类
 * @author Sinlmao
 * @create 2019-08-01 11:11
 */
public class ImResponse {

	private int responseCode;
	private String stringContent;
	private byte[] bytesContent;

	private Map<String, List<String>> headers = new HashMap<String, List<String>>();
	private String cookieStr;
	private Map<String, String> cookies = new HashMap<String, String>();

	///////////////////////////////////////////////////////////////////////

	public ImResponse setResponseCode(int responseCode) {
		this.responseCode = responseCode;
		return this;
	}
	public ImResponse setStringContent(String stringContent) {
		this.stringContent = stringContent;
		return this;
	}
	public ImResponse setBytesContent(byte[] bytesContent) {
		this.bytesContent = bytesContent;
		return this;
	}


	public ImResponse setCookie(String data) {
		cookieStr = data;
		return this;
	}
	public ImResponse addCookie(String name, String data) {
		cookies.put(name, data);
		return this;
	}


	public ImResponse setHeaders(Map<String, List<String>> headers) {
		this.headers = headers;
		return this;
	}

	///////////////////////////////////////////////////////////////////////

	public int getResponseCode() {
		return responseCode;
	}
	public String getStringContent() {
		return stringContent;
	}
	public byte[] getBytesContent() {
		return bytesContent;
	}

	public void get(byte[] bytesContent) {
		this.bytesContent = bytesContent;
	}

	///////////////////////////////////////////////////////////////////////

	public String getCookieStr() {
		return this.cookieStr;
	}
	public String getCookieData(String name) {
		return cookies.get(name);
	}
	public Set<String> getCookieNames() {
		return cookies.keySet();
	}
	public int getCookieSize() {
		return cookies.size();
	}

	///////////////////////////////////////////////////////////////////////

	public List<String> getHeaderData(String name) {
		return headers.get(name);
	}
	public Set<String> getHeaderNames() {
		return headers.keySet();
	}
	public int getHeaderSize() {
		return headers.size();
	}

	///////////////////////////////////////////////////////////////////////

	protected ImResponse() {
	}
}