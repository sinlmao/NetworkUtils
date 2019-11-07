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
package cn.sinlmao.commons.network.bean;

/**
 * <b>会话响应的Cookie数据封装</b>
 * <p>
 * 该类用于封装会话响应的Cookie数据
 * <br /><br />
 * <b>Cookie response data encapsulation class</b>
 * <p>
 * This class is used to encapsulate cookie data for session responses..
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description 会话响应的Cookie数据封装类
 * @create 2019-11-07 15:50
 * @since 1.4.1
 */
public class ImResponseCookie {

    private String name;
    private String value;
    private String expires;
    private String maxAge;
    private String domain;
    private String path;
    private boolean secure = false;
    private boolean httpOnly = false;

    public void ImResponseCookie() {
    }

    /**
     * 封装一个响应的Cookie数据
     * <p>
     * <font color="#666666">Build a response cookie data</font>
     *
     * @param name  数据名 <br /> <font color="#666666">Data name</font>
     * @param value 数据值 <br /> <font color="#666666">Data value</font>
     */
    public void ImResponseCookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * 获取数据名
     * <p>
     * <font color="#666666">Get Data name</font>
     *
     * @return 数据名 <br /> <font color="#666666">Data name</font>
     */
    public String getName() {
        return name;
    }

    /**
     * 设置数据名
     * <p>
     * <font color="#666666">Set Data name</font>
     *
     * @return 数据名 <br /> <font color="#666666">Data name</font>
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取数据值
     * <p>
     * <font color="#666666">Get Data value</font>
     *
     * @return 数据值 <br /> <font color="#666666">Data value</font>
     */
    public String getValue() {
        return value == null ? "null" : value;
    }

    /**
     * 设置数据值
     * <p>
     * <font color="#666666">Set Data value</font>
     *
     * @param value 数据值 <br /> <font color="#666666">Data value</font>
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取有效期值
     * <p>
     * <font color="#666666">Get valid</font>
     *
     * @return 有效期值  <br /> <font color="#666666">Valid value</font>
     */
    public String getExpires() {
        return expires == null ? "null" : expires;
    }

    /**
     * 设置有效期值
     * <p>
     * <font color="#666666">Set valid</font>
     *
     * @param expires 有效期值  <br /> <font color="#666666">Valid value</font>
     */
    public void setExpires(String expires) {
        this.expires = expires;
    }

    /**
     * 获取MaxAge值
     * <p>
     * <font color="#666666">Get MaxAge value</font>
     *
     * @return MaxAge值 <br /> <font color="#666666">MaxAge value</font>
     */
    public String getMaxAge() {
        return maxAge == null ? "null" : maxAge;
    }

    /**
     * 设置MaxAge值
     * <p>
     * <font color="#666666">Set MaxAge value</font>
     *
     * @param maxAge MaxAge <br /> <font color="#666666">MaxAge value</font>
     */
    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
    }

    /**
     * 获取Domain值
     * <p>
     * <font color="#666666">Get Domain value</font>
     *
     * @return Domain值 <br /> <font color="#666666">Domain value</font>
     */
    public String getDomain() {
        return domain == null ? "null" : domain;
    }

    /**
     * 设置Domain值
     * <p>
     * <font color="#666666">Set Domain value</font>
     *
     * @param domain Domain值  <br /> <font color="#666666">Domain value</font>
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * 获取Path值
     * <p>
     * <font color="#666666">Get Path value</font>
     *
     * @return Path值 <br /> <font color="#666666">Path value</font>
     */
    public String getPath() {
        return path == null ? "null" : path;
    }

    /**
     * 设置Path值
     * <p>
     * <font color="#666666">Set Domain value</font>
     *
     * @param path Path值 <br /> <font color="#666666">Path value</font>
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取Secure值
     * <p>
     * <font color="#666666">Get Path value</font>
     *
     * @return Secure值 <br /> <font color="#666666">Secure value</font>
     */
    public boolean isSecure() {
        return secure;
    }

    /**
     * 设置Secure值
     * <p>
     * <font color="#666666">Set Domain value</font>
     *
     * @param secure Secure值 <br /> <font color="#666666">Secure value</font>
     */
    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    /**
     * 获取HttpOnly值
     * <p>
     * <font color="#666666">Get HttpOnly value</font>
     *
     * @return HttpOnly值 <br /> <font color="#666666">HttpOnly value</font>
     */
    public boolean isHttpOnly() {
        return httpOnly;
    }

    /**
     * 设置HttpOnly值
     * <p>
     * <font color="#666666">Set HttpOnly value</font>
     *
     * @param httpOnly HttpOnly值 <br /> <font color="#666666">HttpOnly value</font>
     */
    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }
}
