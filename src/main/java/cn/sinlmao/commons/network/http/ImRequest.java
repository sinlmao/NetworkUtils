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

import cn.sinlmao.commons.network.bean.ImBytesData;
import cn.sinlmao.commons.network.bean.ImFileData;
import cn.sinlmao.commons.network.bean.ImMultipartFormData;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <b>HTTP Request 会话请求数据封装类</b>
 * <p>
 * 该类为Request会话请求的数据封装类，所有Request会话相关的属性、数据、配置均需要在该类设置
 * <br/><br/>
 * <b>HTTP Request session request data encapsulation class</b>
 * <p>
 * This class is the data encapsulation class of the Request session request. All the properties, data,
 * and configuration related to the Request session need to be set in this class.
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
    private boolean keepAlive = false;
    private boolean useCache = false;
    private boolean allowNonStandard = false;
    private boolean restfulMode = false;
    private boolean forceInUrlSendData = false;

    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> cookies = new HashMap<String, String>();

    /**
     * 传入URL构造一个ImRequest
     * <p>
     * Construct an ImRequest with the incoming URL
     *
     * @param url URL
     */
    public ImRequest(String url) {
        this.url = url;
    }

    ///////////////////////////////////////////////////////////////////////

    /**
     * 设置URL
     * <p>
     * Set the URL
     *
     * @param url URL
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * 设置Method方法
     * <p>
     * Set the Method
     *
     * @param method ImMethod方法 <br/> ImMethod method
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setMethod(ImMethod method) {
        this.method = method;
        return this;
    }

    /**
     * 设置Charset编码
     * <p>
     * Set the Charset
     *
     * @param charset 编码 <br/> Charset
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    /**
     * 设置String类型输入数据
     * <p>
     * Set String type input data
     *
     * @param inputData String类型输入数据 <br/> String type input data
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setInputData(String inputData) {
        this.inputData = inputData;
        return this;
    }

    /**
     * 设置Map类型输入数据
     * <p>
     * Set Map type input data
     *
     * @param inputData Map类型输入数据 <br/> Map type input data
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setInputData(Map<String, String> inputData) {
        this.inputData = inputData;
        return this;
    }

    /**
     * 设置JSONObject类型输入数据
     * <p>
     * Set JSONObject type input data
     *
     * @param inputData JSONObject类型输入数据 <br/> JSONObject type input data
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setInputData(JSONObject inputData) {
        this.inputData = inputData;
        return this;
    }

    /**
     * 设置ImBytesData类型输入数据
     * <p>
     * Set ImBytesData/bytes type input data
     *
     * @param inputData ImBytesData类型输入数据 <br/> ImBytesData/bytes type input data
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setInputData(ImBytesData inputData) {
        this.inputData = inputData;
        return this;
    }

    /**
     * 设置ImFileData类型输入数据
     * <p>
     * Set ImFileData type input data
     *
     * @param inputData ImFileData类型输入数据 <br/> ImFileData type input data
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setInputData(ImFileData inputData) {
        this.inputData = inputData;
        return this;
    }

    /**
     * 设置ImMultipartFormData类型输入数据
     * <p>
     * Set ImMultipartFormData type input data
     *
     * @param inputData ImMultipartFormData类型输入数据 <br/> ImMultipartFormData type input data
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setInputData(ImMultipartFormData inputData) {
        this.inputData = inputData;
        return this;
    }

    /**
     * 设置BytesLength长度
     * <p>
     * Set Request bytes length
     *
     * @param bytesLength BytesLength长度 <br/> Request bytes Length
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setBytesLength(int bytesLength) {
        this.bytesLength = bytesLength;
        return this;
    }

    /**
     * 设置内容类型（ContentType）
     * <p>
     * Set Request content-type
     *
     * @param contentType ContentType内容类型 <br/> ImContentType/content-type
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setContentType(ImContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    /**
     * 设置内容类型（ContentType）
     * <p>
     * Set Request some String content-type
     *
     * @param contentType String内容类型 <br/> String content-type name
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setContentType(String contentType) {
        this.contentTypeStr = contentType;
        return this;
    }

    /**
     * 设置是否忽略SSLCertVerify
     * <p>
     * Set whether to ignore
     *
     * @param ignoreSSLCertVerify 是否忽略 <br/> Whether to ignore
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setIgnoreSSLCertVerify(boolean ignoreSSLCertVerify) {
        this.ignoreSSLCertVerify = ignoreSSLCertVerify;
        return this;
    }

    /**
     * 设置是否为长连接
     * <p>
     * Set whether to Keep-Alive
     *
     * @param keepAlive 是否为长连接 <br /> Whether to Keep-Alive
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
        return this;
    }

    /**
     * 设置是否使用缓存
     * <p>
     * Set whether to use cache
     *
     * @param useCache 是否使用缓存 <br /> Whether to use cache
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setUseCache(boolean useCache) {
        this.useCache = useCache;
        return this;
    }

    /**
     * 设置是否允许非标准使用
     * <p>
     * Set whether to allow no standard
     *
     * @param allowNonStandard 是否允许非标准使用 <br /> Whether to allow no standard
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setAllowNonStandard(boolean allowNonStandard) {
        this.allowNonStandard = allowNonStandard;
        return this;
    }

    /**
     * 设置是否为Restful模式
     * <p>
     * Set whether to restful mode
     *
     * @param restfulMode 是否为Restful模式 <br /> Whether to restful mode
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setRestfulMode(boolean restfulMode) {
        this.restfulMode = restfulMode;
        this.allowNonStandard = restfulMode;
        return this;
    }

    /**
     * 设置是否强制使用URL发送数据
     * <p>
     * 请注意，强制使用URL发送数据后将不能使用部分数据类型
     * <p>
     * Set whether to force in url send data
     * <p>
     * Please note that some data types will not be available after the data is forced to be sent using the URL.
     *
     * @param forceInUrlSendData 是否强制使用URL发送数据 <br /> Whether to force in url send data
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setForceInUrlSendData(boolean forceInUrlSendData) {
        this.forceInUrlSendData = forceInUrlSendData;
        return this;
    }

    ///////////////////////////////////////////////////////////////////////

    /**
     * 添加Header，当数据已经存在，则不再添加
     * <p>
     * Add a Header, when the data already exists, it will not be added
     *
     * @param name  Header键 <br/> Header Key
     * @param value Header值 <br/> Header Value
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest addHeader(String name, String value) {
        if (!headers.containsKey(name)) {
            headers.put(name, value);
        }
        return this;
    }

    /**
     * 添加Header，当数据已经存在，则不再添加
     * <p>
     * Add a Header, when the data already exists, it will not be added
     *
     * @param headers Map类型的Header数据 <br/> Map type Header data
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest addHeader(Map<String, String> headers) {
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
     * Add a Header, when the data already exists, it will not be added
     *
     * @param headers JSONObject类型的Header数据 <br/> JSONObject type Header data
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest addHeader(JSONObject headers) {
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
     * Set the Header. When the data already exists, the last setting is subject to
     *
     * @param name  Header键 <br/> Header Key
     * @param value Header值 <br/> Header Value
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setHeader(String name, String value) {
        if (!headers.containsKey(name)) {
            headers.remove(name);
        }
        headers.put(name, value);
        return this;
    }

    /**
     * 设置Header，当数据已经存在，则以最后设置的为准
     * <p>
     * Set the Header. When the data already exists, the last setting is subject to
     *
     * @param headers JSONObject类型的Header数据 <br/> JSONObject type Header data
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setHeader(JSONObject headers) {
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
     * Set the Header. When the data already exists, the last setting is subject to
     *
     * @param headers Map类型的Header数据 <br/> Map type Header data
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setHeader(Map<String, String> headers) {
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
     * Add a Cookie, when the data already exists, it will not be added
     *
     * @param name  Cookie键 <br/> Cookie Key
     * @param value Cookie值 <br/> Cookie Value
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest addCookie(String name, String value) {
        if (!cookies.containsKey(name)) {
            cookies.put(name, value);
        }
        return this;
    }

    /**
     * 添加Cookie，当数据已经存在，则不再添加
     * <p>
     * Add a Cookie, when the data already exists, it will not be added
     *
     * @param cookies Map类型的Cookie数据 <br/> Map type Cookie data
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest addCookie(Map<String, String> cookies) {
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
     * Add a Cookie, when the data already exists, it will not be added
     *
     * @param cookies JSONObject类型的Cookie数据 <br/> JSONObject type Cookie data
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest addCookie(JSONObject cookies) {
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
     * Set the Cookie. When the data already exists, the last setting is subject to
     *
     * @param name  Cookie键 <br/> Cookie Key
     * @param value Cookie值 <br/> Cookie Value
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setCookie(String name, String value) {
        if (!cookies.containsKey(name)) {
            cookies.remove(name);
        }
        cookies.put(name, value);
        return this;
    }

    /**
     * 添加Cookie，当数据已经存在，则以最后设置的为准
     * <p>
     * Set the Cookie. When the data already exists, the last setting is subject to
     *
     * @param cookies Map类型的Cookie数据 <br/> Map type Cookie data
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setCookie(Map<String, String> cookies) {
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
     * Set the Cookie. When the data already exists, the last setting is subject to
     *
     * @param cookies JSONObject类型的Cookie数据 <br/> JSONObject type Cookie data
     * @return ImRequest对象实体 <br/> ImRequest object entity
     */
    public ImRequest setCookie(JSONObject cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (String key : cookies.keySet()) {
                setCookie(key, cookies.getString(key));
            }
        }
        return this;
    }

    ///////////////////////////////////////////////////////////////////////

    /**
     * 获取是否忽略SSLCertVerify
     * <p>
     * Get whether to SSL certVerify
     *
     * @return 是否忽略SSLCertVerify <br /> Whether to SSL certVerify
     */
    public boolean isIgnoreSSLCertVerify() {
        return ignoreSSLCertVerify;
    }

    /**
     * 获取是否需要长连接
     * <p>
     * Get whether to Keep-Alive
     *
     * @return 是否需要长连接 <br /> Whether to Keep-Alive
     */
    public boolean isKeepAlive() {
        return keepAlive;
    }

    /**
     * 获取是否使用缓存
     * <p>
     * Get whether to use cache
     *
     * @return 是否使用缓存 <br /> Whether to use cache
     */
    public boolean isUseCache() {
        return useCache;
    }

    /**
     * 获取是否允许非标准使用
     * <p>
     * Get whether to allow no standard
     *
     * @return 是否允许非标准使用 <br /> Whether to allow no standard
     */
    public boolean isAllowNonStandard() {
        return allowNonStandard;
    }

    /**
     * 获取是否为Restful模式
     * <p>
     * Get whether to restful mode
     *
     * @return 是否为Restful模式 <br /> Whether to restful mode
     */
    public boolean isRestfulMode() {
        return restfulMode;
    }

    /**
     * 获取是否强制使用URL发送数据
     * <p>
     * Get whether to force in url send data
     *
     * @return 是否强制使用URL发送数据 <br /> Whether to force in url send data
     */
    public boolean isForceInUrlSendData() {
        return forceInUrlSendData;
    }


    ///////////////////////////////////////////////////////////////////////

    /**
     * 获取Request会话的URL属性
     * <p>
     * Get the Request URL attribute
     *
     * @return Request会话的URL属性 <br /> Request URL attribute
     */
    public String getUrl() {
        return url;
    }

    /**
     * 获取Request会话的方法（ImMethod）属性
     * <p>
     * Get the Request Method(ImMethod) attribute
     *
     * @return ImMethod Request会话的方法（ImMethod）属性 <br /> Request Method(ImMethod) attribute
     */
    public ImMethod getMethod() {
        return method;
    }

    /**
     * 获取Request会话的编码属性
     * <p>
     * Get the Request Charset attribute
     *
     * @return Request会话的编码属性 <br /> Request Charset attribute
     */
    public String getCharset() {
        return charset;
    }

    ///////////////////////////////////////////////////////////////////////

    /**
     * 获取Request会话的输入数据
     * <p>
     * Get the Request input data
     *
     * @return Request会话的输入数据 <br /> Request input data
     */
    public Object getInputData() {
        return inputData;
    }

    /**
     * 根据指定的类型获取Request会话的输入数据
     * <p>
     * 比如需要获得一个String类型的属性，则代码为：getInputData(String.class)
     * <p>
     * According to the specified type get the Request input data
     * <p>
     * For example, if you need to get a property of type String, the code is: getInputData(String.class)
     *
     * @param type 指定的类型 <br /> Specified type
     * @return Request会话的输入数据 <br /> Request input data
     */
    public <T> T getInputData(Class<T> type) {
        return (T) inputData;
    }

    ///////////////////////////////////////////////////////////////////////

    /**
     * 获取Request会话的内容bytes长度属性
     * <p>
     * Get the Request bytes length attribute
     *
     * @return Request会话的内容bytes长度属性 <br /> Request bytes length attribute
     */
    public int getBytesLength() {
        return bytesLength;
    }

    /**
     * 获取Request会话的内容类型（ImContentType）
     * <p>
     * Get the Request content type (ImContentType)
     *
     * @return Request会话的内容类型 <br /> Request content type
     */
    public ImContentType getContentType() {
        return contentType;
    }

    /**
     * 获取Request会话的内容类型字符表示（String）
     * <p>
     * 如果存在ContentTypeStr属性则返回ContentTypeStr属性
     * <p>
     * Get the Request string content type (String)
     * <p>
     * Returns the ContentTypeStr property if there is a ContentTypeStr property
     *
     * @return Request会话的内容类型字符表示（String） <br /> Request string content type (String)
     */
    public String getContentTypeStr() {
        if (this.contentTypeStr != null && this.contentTypeStr.length() > 1) {
            return contentTypeStr;
        }
        return contentType.toString();
    }

    ////////////////////////////////////////////////////////////////////

    /**
     * 根据Header键获得对应值
     * <p>
     * Get the corresponding value according to the Header key
     *
     * @param name Header键 <br /> Header Key
     * @return Header值 <br /> Header Value
     */
    public String getHeaderValue(String name) {
        return headers.get(name);
    }

    /**
     * 获得所有Header键
     * <p>
     * Get all Header keys
     *
     * @return 所有Header键 <br /> All Header keys
     */
    public Set<String> getHeaderNames() {
        return headers.keySet();
    }

    /**
     * 获得Header数量
     * <p>
     * Get the count of Headers
     *
     * @return Header数量 <br /> Headers count
     */
    public int getHeaderSize() {
        return headers.size();
    }

    /**
     * 根据Cookie键获得对应值
     * <p>
     * Get the corresponding value according to the Cookie key
     *
     * @param name Cookie键 <br /> Cookie Key
     * @return Cookie值 <br /> Cookie Value
     */
    public String getCookieData(String name) {
        return cookies.get(name);
    }

    /**
     * 获得所有Cookie键
     * <p>
     * Get all Cookie keys
     *
     * @return 所有Cookie键 <br /> All Cookie keys
     */
    public Set<String> getCookieNames() {
        return cookies.keySet();
    }

    /**
     * 获得Cookie数量
     * <p>
     * Get the count of Cookies
     *
     * @return Cookie数量 <br /> Cookies count
     */
    public int getCookieSize() {
        return cookies.size();
    }

}
