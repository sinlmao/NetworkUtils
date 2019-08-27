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

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * HTTP Request类
 *
 * @program Sinlmao Commons Network Utils
 * @description HTTP Request类
 * @author Sinlmao
 * @create 2019-08-01 11:11
 */
public class HttpUtilRequest {

    private String url;
    private HttpUtilMethod method;
    private String charset = "utf-8";
    private Object inputData;
    private int bytesLength = 4096;
    private HttpUtilContentType contentType = HttpUtilContentType.APPLICATION_X_WWW_FORM_URLENCODED;
    private String contentTypeStr;

    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> cookies = new HashMap<String, String>();

    public HttpUtilRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpUtilMethod getMethod() {
        return method;
    }

    public void setMethod(HttpUtilMethod method) {
        this.method = method;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    ///////////////////////////////////////////////////////////////////////

    public Object getInputData() {
        return inputData;
    }

    public <T> T getInputData(Class<T> type) {
        return (T) inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public void setInputData(Map<String, String> inputData) {
        this.inputData = inputData;
    }

    public void setInputData(JSONObject inputData) {
        this.inputData = inputData;
    }

    ///////////////////////////////////////////////////////////////////////

    public int getBytesLength() {
        return bytesLength;
    }

    public void setBytesLength(int bytesLength) {
        this.bytesLength = bytesLength;
    }

    public HttpUtilContentType getContentType() {
        return contentType;
    }

    public void setContentType(HttpUtilContentType contentType) {
        this.contentType = contentType;
    }

    public void setContentType(String contentType) {
        this.contentTypeStr = contentType;
    }

    public String getContentTypeStr() {
        return this.contentTypeStr;
    }

    ///////////////////////////////////////////////////////////////////////

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public String getHeaderValue(String name) {
        return headers.get(name);
    }

    public Set<String> getHeaderNames() {
        return headers.keySet();
    }

    public int getHeaderSize() {
        return headers.size();
    }

    ///////////////////////////////////////////////////////////////////////

    public void addCookie(String name, String value) {
        cookies.put(name, value);
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

}
