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
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description HTTP Request类
 * @create 2019-08-01 11:11
 */
public class ImRequest {

    private String url;
    private ImMethod method = ImMethod.GET;
    private String charset = "utf-8";
    private Object inputData;
    private int bytesLength = 4096;
    private ImContentType contentType = ImContentType.APPLICATION_X_WWW_FORM_URLENCODED;
    private String contentTypeStr;
    private boolean ignoreSSLCertVerify = false;

    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> cookies = new HashMap<String, String>();

    public ImRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    ///////////////////////////////////////////////////////////////////////

    public ImRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public ImRequest setMethod(ImMethod method) {
        this.method = method;
        return this;
    }

    public ImRequest setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public ImRequest setInputData(String inputData) {
        this.inputData = inputData;
        return this;
    }

    public ImRequest setInputData(Map<String, String> inputData) {
        this.inputData = inputData;
        return this;
    }

    public ImRequest setInputData(JSONObject inputData) {
        this.inputData = inputData;
        return this;
    }

    public ImRequest setBytesLength(int bytesLength) {
        this.bytesLength = bytesLength;
        return this;
    }

    public ImRequest setContentType(ImContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public ImRequest setContentType(String contentType) {
        this.contentTypeStr = contentType;
        return this;
    }

    public boolean isIgnoreSSLCertVerify() {
        return ignoreSSLCertVerify;
    }

    public void setIgnoreSSLCertVerify(boolean ignoreSSLCertVerify) {
        this.ignoreSSLCertVerify = ignoreSSLCertVerify;
    }

    ///////////////////////////////////////////////////////////////////////

    public ImMethod getMethod() {
        return method;
    }

    public String getCharset() {
        return charset;
    }

    ///////////////////////////////////////////////////////////////////////

    public Object getInputData() {
        return inputData;
    }

    public <T> T getInputData(Class<T> type) {
        return (T) inputData;
    }

    ///////////////////////////////////////////////////////////////////////

    public int getBytesLength() {
        return bytesLength;
    }

    public ImContentType getContentType() {
        return contentType;
    }

    public String getContentTypeStr() {
        return this.contentTypeStr;
    }

    ///////////////////////////////////////////////////////////////////////

    /**
     * 添加Header，当数据已经存在，则不再添加
     * @param name
     * @param value
     */
    public void addHeader(String name, String value) {
        if (!headers.containsKey(name)) {
            headers.put(name, value);
        }
    }

    /**
     * 添加Header，当数据已经存在，则不再添加
     * @param headers
     */
    public void addHeader(Map<String, String> headers) {
        if (headers != null && headers.size() > 0) {
            for (String key : headers.keySet()) {
                addHeader(key, headers.get(key));
            }
        }
    }

    /**
     * 添加Header，当数据已经存在，则不再添加
     * @param headers
     */
    public void addHeader(JSONObject headers) {
        if (headers != null && headers.size() > 0) {
            for (String key : headers.keySet()) {
                addHeader(key, headers.getString(key));
            }
        }
    }

    /**
     * 设置Header，当数据已经存在，则以最后设置的为准
     * @param name
     * @param value
     */
    public void setHeader(String name, String value) {
        if (!headers.containsKey(name)) {
            headers.remove(name);
        }
        headers.put(name, value);
    }

    /**
     * 设置Header，当数据已经存在，则以最后设置的为准
     * @param headers
     */
    public void setHeader(JSONObject headers) {
        if (headers != null && headers.size() > 0) {
            for (String key : headers.keySet()) {
                setHeader(key, headers.getString(key));
            }
        }
    }

    /**
     * 设置Header，当数据已经存在，则以最后设置的为准
     * @param headers
     */
    public void setHeader(Map<String, String> headers) {
        if (headers != null && headers.size() > 0) {
            for (String key : headers.keySet()) {
                setHeader(key, headers.get(key));
            }
        }
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

    /**
     * 添加Cookie，当数据已经存在，则不再添加
     * @param name
     * @param value
     */
    public void addCookie(String name, String value) {
        if (!cookies.containsKey(name)) {
            cookies.put(name, value);
        }
    }

    /**
     * 添加Cookie，当数据已经存在，则不再添加
     * @param cookies
     */
    public void addCookie(Map<String, String> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (String key : cookies.keySet()) {
                addCookie(key, cookies.get(key));
            }
        }
    }

    /**
     * 添加Cookie，当数据已经存在，则不再添加
     * @param cookies
     */
    public void addCookie(JSONObject cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (String key : cookies.keySet()) {
                addCookie(key, cookies.getString(key));
            }
        }
    }

    /**
     * 添加Cookie，当数据已经存在，则以最后设置的为准
     * @param name
     * @param value
     */
    public void setCookie(String name, String value) {
        if (!cookies.containsKey(name)) {
            cookies.remove(name);
        }
        cookies.put(name, value);
    }

    /**
     * 添加Cookie，当数据已经存在，则以最后设置的为准
     * @param cookies
     */
    public void setCookie(Map<String, String> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (String key : cookies.keySet()) {
                setCookie(key, cookies.get(key));
            }
        }
    }

    /**
     * 添加Cookie，当数据已经存在，则以最后设置的为准
     * @param cookies
     */
    public void setCookie(JSONObject cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (String key : cookies.keySet()) {
                setCookie(key, cookies.getString(key));
            }
        }
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
