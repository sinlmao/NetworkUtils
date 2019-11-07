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
 * <b>会话状态控制数据对象类</b>
 * <p>
 * 该类用于需要使用会话状态控制的场景，作为数据存储的对象使用
 * <br/><br/>
 * <b>Session state control data object class</b>
 * <p>
 * This class is used for scenarios that require session state control and is used as an object of data storage.
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description 会话状态控制数据对象类
 * @create 2019-11-07 14:55
 */
public class ImSession {

    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> cookies = new HashMap<String, String>();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 添加Header，当数据已经存在，则不再添加
     * <p>
     * <font color="#666666">Add a Header, when the data already exists, it will not be added</font>
     *
     * @param name  Header键 <br/> <font color="#666666">Header Key</font>
     * @param value Header值 <br/> <font color="#666666">Header Value</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     */
    public ImSession addHeader(String name, String value) {
        if (!headers.containsKey(name)) {
            headers.put(name, value);
        }
        return this;
    }

    /**
     * 添加Header，当数据已经存在，则不再添加
     * <p>
     * <font color="#666666">Add a Header, when the data already exists, it will not be added</font>
     *
     * @param headers Map类型的Header数据 <br/> <font color="#666666">Map type Header data</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     */
    public ImSession addHeader(Map<String, String> headers) {
        if (headers != null && headers.size() > 0) {
            for (String key : headers.keySet()) {
                addHeader(key, headers.get(key));
            }
        }
        return this;
    }

    /**
     * 添加Header，当数据已经存在，则不再添加
     * <p>
     * <font color="#666666">Add a Header, when the data already exists, it will not be added</font>
     *
     * @param headers JSONObject类型的Header数据 <br/> <font color="#666666">JSONObject type Header data</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     */
    public ImSession addHeader(JSONObject headers) {
        if (headers != null && headers.size() > 0) {
            for (String key : headers.keySet()) {
                addHeader(key, headers.getString(key));
            }
        }
        return this;
    }

    /**
     * 设置Header，当数据已经存在，则以最后设置的为准
     * <p>
     * <font color="#666666">Set the Header. When the data already exists, the last setting is subject to</font>
     *
     * @param name  Header键 <br/> <font color="#666666">Header Key</font>
     * @param value Header值 <br/> <font color="#666666">Header Value</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     */
    public ImSession setHeader(String name, String value) {
        if (!headers.containsKey(name)) {
            headers.remove(name);
        }
        headers.put(name, value);
        return this;
    }

    /**
     * 设置Header，当数据已经存在，则以最后设置的为准
     * <p>
     * <font color="#666666">Set the Header. When the data already exists, the last setting is subject to</font>
     *
     * @param headers JSONObject类型的Header数据 <br/> <font color="#666666">JSONObject type Header data</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     */
    public ImSession setHeader(JSONObject headers) {
        if (headers != null && headers.size() > 0) {
            for (String key : headers.keySet()) {
                setHeader(key, headers.getString(key));
            }
        }
        return this;
    }

    /**
     * 设置Header，当数据已经存在，则以最后设置的为准
     * <p>
     * <font color="#666666">Set the Header. When the data already exists, the last setting is subject to</font>
     *
     * @param headers Map类型的Header数据 <br/> <font color="#666666">Map type Header data</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     */
    public ImSession setHeader(Map<String, String> headers) {
        if (headers != null && headers.size() > 0) {
            for (String key : headers.keySet()) {
                setHeader(key, headers.get(key));
            }
        }
        return this;
    }

    ///////////////////////////////////////////////////////////////////////

    /**
     * 添加Cookie，当数据已经存在，则不再添加
     * <p>
     * <font color="#666666">Add a Cookie, when the data already exists, it will not be added</font>
     *
     * @param name  Cookie键 <br/> <font color="#666666">Cookie Key</font>
     * @param value Cookie值 <br/> <font color="#666666">Cookie Value</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     */
    public ImSession addCookie(String name, String value) {
        if (!cookies.containsKey(name)) {
            cookies.put(name, value);
        }
        return this;
    }

    /**
     * 添加Cookie，当数据已经存在，则不再添加
     * <p>
     * <font color="#666666">Add a Cookie, when the data already exists, it will not be added</font>
     *
     * @param cookies Map类型的Cookie数据 <br/> <font color="#666666">Map type Cookie data</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     */
    public ImSession addCookie(Map<String, String> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (String key : cookies.keySet()) {
                addCookie(key, cookies.get(key));
            }
        }
        return this;
    }

    /**
     * 添加Cookie，当数据已经存在，则不再添加
     * <p>
     * <font color="#666666">Add a Cookie, when the data already exists, it will not be added</font>
     *
     * @param cookies JSONObject类型的Cookie数据 <br/> <font color="#666666">JSONObject type Cookie data</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     */
    public ImSession addCookie(JSONObject cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (String key : cookies.keySet()) {
                addCookie(key, cookies.getString(key));
            }
        }
        return this;
    }

    /**
     * 添加Cookie，当数据已经存在，则以最后设置的为准
     * <p>
     * <font color="#666666">Set the Cookie. When the data already exists, the last setting is subject to</font>
     *
     * @param name  Cookie键 <br/> <font color="#666666">Cookie Key</font>
     * @param value Cookie值 <br/> <font color="#666666">Cookie Value</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     */
    public ImSession setCookie(String name, String value) {
        if (!cookies.containsKey(name)) {
            cookies.remove(name);
        }
        cookies.put(name, value);
        return this;
    }

    /**
     * 添加Cookie，当数据已经存在，则以最后设置的为准
     * <p>
     * <font color="#666666">Set the Cookie. When the data already exists, the last setting is subject to</font>
     *
     * @param cookies Map类型的Cookie数据 <br/> <font color="#666666">Map type Cookie data</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     */
    public ImSession setCookie(Map<String, String> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (String key : cookies.keySet()) {
                setCookie(key, cookies.get(key));
            }
        }
        return this;
    }

    /**
     * 添加Cookie，当数据已经存在，则以最后设置的为准
     * <p>
     * <font color="#666666">Set the Cookie. When the data already exists, the last setting is subject to</font>
     *
     * @param cookies JSONObject类型的Cookie数据 <br/> <font color="#666666">JSONObject type Cookie data</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     */
    public ImSession setCookie(JSONObject cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (String key : cookies.keySet()) {
                setCookie(key, cookies.getString(key));
            }
        }
        return this;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 根据Header键获得对应值
     * <p>
     * <font color="#666666">Get the corresponding value according to the Header key</font>
     *
     * @param name Header键 <br /> <font color="#666666">Header Key</font>
     * @return Header值 <br /> <font color="#666666">Header Value</font>
     */
    public String getHeaderValue(String name) {
        return headers.get(name);
    }

    /**
     * 获得所有Header键
     * <p>
     * <font color="#666666">Get all Header keys</font>
     *
     * @return 所有Header键 <br /> <font color="#666666">All Header keys</font>
     */
    public Set<String> getHeaderNames() {
        return headers.keySet();
    }

    /**
     * 获得Header数量
     * <p>
     * <font color="#666666">Get the count of Headers</font>
     *
     * @return Header数量 <br /> <font color="#666666">Headers count</font>
     */
    public int getHeaderSize() {
        return headers.size();
    }

    /**
     * 根据Cookie键获得对应值
     * <p>
     * <font color="#666666">Get the corresponding value according to the Cookie key</font>
     *
     * @param name Cookie键 <br /> <font color="#666666">Cookie Key</font>
     * @return Cookie值 <br /> <font color="#666666">Cookie Value</font>
     */
    public String getCookieData(String name) {
        return cookies.get(name);
    }

    /**
     * 获得所有Cookie键
     * <p>
     * <font color="#666666">Get all Cookie keys</font>
     *
     * @return 所有Cookie键 <br /> <font color="#666666">All Cookie keys</font>
     */
    public Set<String> getCookieNames() {
        return cookies.keySet();
    }

    /**
     * 获得Cookie数量
     * <p>
     * <font color="#666666">Get the count of Cookies</font>
     *
     * @return Cookie数量 <br /> <font color="#666666">Cookies count</font>
     */
    public int getCookieSize() {
        return cookies.size();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected Map<String, String> getHeaders() {
        return headers;
    }

    protected void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    protected Map<String, String> getCookies() {
        return cookies;
    }

    protected void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }
}
