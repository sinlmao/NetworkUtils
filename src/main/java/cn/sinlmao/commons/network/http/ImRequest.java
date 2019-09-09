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
     * <font color="#666666">Construct an ImRequest with the incoming URL</font>
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
     * <font color="#666666">Set the URL</font>
     *
     * @param url URL
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
     */
    public ImRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * 设置Method方法
     * <p>
     * 默认为GET
     * <p>
     * <font color="#666666">Set the Method</font>
     * <p>
     * <font color="#666666">Default is GET</font>
     *
     * @param method ImMethod方法 <br/> <font color="#666666">ImMethod method</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
     */
    public ImRequest setMethod(ImMethod method) {
        this.method = method;
        return this;
    }

    /**
     * 设置Charset编码
     * <p>
     * 默认为UTF-8
     * <p>
     * <font color="#666666">Set the Charset</font>
     * <p>
     * <font color="#666666">Default is UTF-8</font>
     *
     * @param charset 编码 <br/> <font color="#666666">Charset</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
     */
    public ImRequest setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    /**
     * 设置String类型输入数据
     * <p>
     * <font color="#666666">Set String type input data</font>
     *
     * @param inputData String类型输入数据 <br/> <font color="#666666">String type input data</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
     */
    public ImRequest setInputData(String inputData) {
        this.inputData = inputData;
        return this;
    }

    /**
     * 设置Map类型输入数据
     * <p>
     * <font color="#666666">Set Map type input data</font>
     *
     * @param inputData Map类型输入数据 <br/> <font color="#666666">Map type input data</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
     */
    public ImRequest setInputData(Map<String, String> inputData) {
        this.inputData = inputData;
        return this;
    }

    /**
     * 设置JSONObject类型输入数据
     * <p>
     * <font color="#666666">Set JSONObject type input data</font>
     *
     * @param inputData JSONObject类型输入数据 <br/> <font color="#666666">JSONObject type input data</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
     */
    public ImRequest setInputData(JSONObject inputData) {
        this.inputData = inputData;
        return this;
    }

    /**
     * 设置ImBytesData类型输入数据
     * <p>
     * <font color="#666666">Set ImBytesData/bytes type input data</font>
     *
     * @param inputData ImBytesData类型输入数据 <br/> <font color="#666666">ImBytesData/bytes type input data</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
     */
    public ImRequest setInputData(ImBytesData inputData) {
        this.inputData = inputData;
        return this;
    }

    /**
     * 设置ImFileData类型输入数据
     * <p>
     * <font color="#666666">Set ImFileData type input data</font>
     *
     * @param inputData ImFileData类型输入数据 <br/> <font color="#666666">ImFileData type input data</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
     */
    public ImRequest setInputData(ImFileData inputData) {
        this.inputData = inputData;
        return this;
    }

    /**
     * 设置ImMultipartFormData类型输入数据
     * <p>
     * <font color="#666666">Set ImMultipartFormData type input data</font>
     *
     * @param inputData ImMultipartFormData类型输入数据 <br/> <font color="#666666">ImMultipartFormData type input data</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
     */
    public ImRequest setInputData(ImMultipartFormData inputData) {
        this.inputData = inputData;
        return this;
    }

    /**
     * 设置BytesLength长度
     * <p>
     * 默认为4096
     * <p>
     * <font color="#666666">Set Request bytes length</font>
     * <p>
     * <font color="#666666">Default is 4096</font>
     *
     * @param bytesLength BytesLength长度 <br/> <font color="#666666">Request bytes Length</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
     */
    public ImRequest setBytesLength(int bytesLength) {
        this.bytesLength = bytesLength;
        return this;
    }

    /**
     * 设置内容类型（ContentType）
     * <p>
     * 默认为application/x-www-form-urlencoded
     * <p>
     * <font color="#666666">Set Request content-type</font>
     * <p>
     * <font color="#666666">Default is application/x-www-form-urlencoded</font>
     *
     * @param contentType ContentType内容类型 <br/> <font color="#666666">ImContentType/content-type</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
     */
    public ImRequest setContentType(ImContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    /**
     * 设置内容类型（ContentType）
     * <p>
     * <font color="#666666">Set Request some String content-type</font>
     *
     * @param contentType String内容类型 <br/> <font color="#666666">String content-type name</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
     */
    public ImRequest setContentType(String contentType) {
        this.contentTypeStr = contentType;
        return this;
    }

    /**
     * 设置是否忽略SSLCertVerify
     * <p>
     * <font color="#666666">Set whether to ignore</font>
     *
     * @param ignoreSSLCertVerify 是否忽略 <br/> <font color="#666666">Whether to ignore</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
     */
    public ImRequest setIgnoreSSLCertVerify(boolean ignoreSSLCertVerify) {
        this.ignoreSSLCertVerify = ignoreSSLCertVerify;
        return this;
    }

    /**
     * 设置是否为长连接
     * <p>
     * <font color="#666666">Set whether to Keep-Alive</font>
     *
     * @param keepAlive 是否为长连接 <br /> <font color="#666666">Whether to Keep-Alive</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
     */
    public ImRequest setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
        return this;
    }

    /**
     * 设置是否使用缓存
     * <p>
     * <font color="#666666">Set whether to use cache</font>
     *
     * @param useCache 是否使用缓存 <br /> <font color="#666666">Whether to use cache</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
     */
    public ImRequest setUseCache(boolean useCache) {
        this.useCache = useCache;
        return this;
    }

    /**
     * 设置是否允许非标准使用
     * <p>
     * <font color="#666666">Set whether to allow no standard</font>
     *
     * @param allowNonStandard 是否允许非标准使用 <br /> <font color="#666666">Whether to allow no standard</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
     */
    public ImRequest setAllowNonStandard(boolean allowNonStandard) {
        this.allowNonStandard = allowNonStandard;
        return this;
    }

    /**
     * 设置是否为Restful模式
     * <p>
     * <font color="#666666">Set whether to restful mode</font>
     *
     * @param restfulMode 是否为Restful模式 <br /> <font color="#666666">Whether to restful mode</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
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
     * <font color="#666666">Set whether to force in url send data</font>
     * <p>
     * <font color="#666666">Please note that some data types will not be available after the data is forced to be sent using the URL.</font>
     *
     * @param forceInUrlSendData 是否强制使用URL发送数据 <br /> <font color="#666666">Whether to force in url send data</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
     */
    public ImRequest setForceInUrlSendData(boolean forceInUrlSendData) {
        this.forceInUrlSendData = forceInUrlSendData;
        return this;
    }

    ///////////////////////////////////////////////////////////////////////

    /**
     * 添加Header，当数据已经存在，则不再添加
     * <p>
     * <font color="#666666">Add a Header, when the data already exists, it will not be added</font>
     *
     * @param name  Header键 <br/> <font color="#666666">Header Key</font>
     * @param value Header值 <br/> <font color="#666666">Header Value</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
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
     * <font color="#666666">Add a Header, when the data already exists, it will not be added</font>
     *
     * @param headers Map类型的Header数据 <br/> <font color="#666666">Map type Header data</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
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
     * <font color="#666666">Add a Header, when the data already exists, it will not be added</font>
     *
     * @param headers JSONObject类型的Header数据 <br/> <font color="#666666">JSONObject type Header data</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
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
     * <font color="#666666">Set the Header. When the data already exists, the last setting is subject to</font>
     *
     * @param name  Header键 <br/> <font color="#666666">Header Key</font>
     * @param value Header值 <br/> <font color="#666666">Header Value</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
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
     * <font color="#666666">Set the Header. When the data already exists, the last setting is subject to</font>
     *
     * @param headers JSONObject类型的Header数据 <br/> <font color="#666666">JSONObject type Header data</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
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
     * <font color="#666666">Set the Header. When the data already exists, the last setting is subject to</font>
     *
     * @param headers Map类型的Header数据 <br/> <font color="#666666">Map type Header data</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
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
     * <font color="#666666">Add a Cookie, when the data already exists, it will not be added</font>
     *
     * @param name  Cookie键 <br/> <font color="#666666">Cookie Key</font>
     * @param value Cookie值 <br/> <font color="#666666">Cookie Value</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
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
     * <font color="#666666">Add a Cookie, when the data already exists, it will not be added</font>
     *
     * @param cookies Map类型的Cookie数据 <br/> <font color="#666666">Map type Cookie data</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
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
     * <font color="#666666">Add a Cookie, when the data already exists, it will not be added</font>
     *
     * @param cookies JSONObject类型的Cookie数据 <br/> <font color="#666666">JSONObject type Cookie data</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
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
     * <font color="#666666">Set the Cookie. When the data already exists, the last setting is subject to</font>
     *
     * @param name  Cookie键 <br/> <font color="#666666">Cookie Key</font>
     * @param value Cookie值 <br/> <font color="#666666">Cookie Value</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
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
     * <font color="#666666">Set the Cookie. When the data already exists, the last setting is subject to</font>
     *
     * @param cookies Map类型的Cookie数据 <br/> <font color="#666666">Map type Cookie data</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
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
     * <font color="#666666">Set the Cookie. When the data already exists, the last setting is subject to</font>
     *
     * @param cookies JSONObject类型的Cookie数据 <br/> <font color="#666666">JSONObject type Cookie data</font>
     * @return ImRequest对象实体 <br/> <font color="#666666">ImRequest object entity</font>
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
     * <font color="#666666">Get whether to SSL certVerify</font>
     *
     * @return 是否忽略SSLCertVerify <br /> <font color="#666666">Whether to SSL certVerify</font>
     */
    public boolean isIgnoreSSLCertVerify() {
        return ignoreSSLCertVerify;
    }

    /**
     * 获取是否需要长连接
     * <p>
     * <font color="#666666">Get whether to Keep-Alive</font>
     *
     * @return 是否需要长连接 <br /> <font color="#666666">Whether to Keep-Alive</font>
     */
    public boolean isKeepAlive() {
        return keepAlive;
    }

    /**
     * 获取是否使用缓存
     * <p>
     * <font color="#666666">Get whether to use cache</font>
     *
     * @return 是否使用缓存 <br /> <font color="#666666">Whether to use cache</font>
     */
    public boolean isUseCache() {
        return useCache;
    }

    /**
     * 获取是否允许非标准使用
     * <p>
     * <font color="#666666">Get whether to allow no standard</font>
     *
     * @return 是否允许非标准使用 <br /> <font color="#666666">Whether to allow no standard</font>
     */
    public boolean isAllowNonStandard() {
        return allowNonStandard;
    }

    /**
     * 获取是否为Restful模式
     * <p>
     * <font color="#666666">Get whether to restful mode</font>
     *
     * @return 是否为Restful模式 <br /> <font color="#666666">Whether to restful mode</font>
     */
    public boolean isRestfulMode() {
        return restfulMode;
    }

    /**
     * 获取是否强制使用URL发送数据
     * <p>
     * <font color="#666666">Get whether to force in url send data</font>
     *
     * @return 是否强制使用URL发送数据 <br /> <font color="#666666">Whether to force in url send data</font>
     */
    public boolean isForceInUrlSendData() {
        return forceInUrlSendData;
    }


    ///////////////////////////////////////////////////////////////////////

    /**
     * 获取Request会话的URL属性
     * <p>
     * <font color="#666666">Get the Request URL attribute</font>
     *
     * @return Request会话的URL属性 <br /> <font color="#666666">Request URL attribute</font>
     */
    public String getUrl() {
        return url;
    }

    /**
     * 获取Request会话的方法（ImMethod）属性
     * <p>
     * <font color="#666666">Get the Request Method(ImMethod) attribute</font>
     *
     * @return ImMethod Request会话的方法（ImMethod）属性 <br /> <font color="#666666">Request Method(ImMethod) attribute</font>
     */
    public ImMethod getMethod() {
        return method;
    }

    /**
     * 获取Request会话的编码属性
     * <p>
     * <font color="#666666">Get the Request Charset attribute</font>
     *
     * @return Request会话的编码属性 <br /> <font color="#666666">Request Charset attribute</font>
     */
    public String getCharset() {
        return charset;
    }

    ///////////////////////////////////////////////////////////////////////

    /**
     * 获取Request会话的输入数据
     * <p>
     * <font color="#666666">Get the Request input data</font>
     *
     * @return Request会话的输入数据 <br /> <font color="#666666">Request input data</font>
     */
    public Object getInputData() {
        return inputData;
    }

    /**
     * 根据指定的类型获取Request会话的输入数据
     * <p>
     * 比如需要获得一个String类型的属性，则代码为：getInputData(String.class)
     * <p>
     * <font color="#666666">According to the specified type get the Request input data</font>
     * <p>
     * <font color="#666666">For example, if you need to get a property of type String, the code is: getInputData(String.class)</font>
     *
     * @param type 指定的类型 <br /> <font color="#666666">Specified type</font>
     * @return Request会话的输入数据 <br /> <font color="#666666">Request input data</font>
     */
    public <T> T getInputData(Class<T> type) {
        return (T) inputData;
    }

    ///////////////////////////////////////////////////////////////////////

    /**
     * 获取Request会话的内容bytes长度属性
     * <p>
     * <font color="#666666">Get the Request bytes length attribute</font>
     *
     * @return Request会话的内容bytes长度属性 <br /> <font color="#666666">Request bytes length attribute</font>
     */
    public int getBytesLength() {
        return bytesLength;
    }

    /**
     * 获取Request会话的内容类型（ImContentType）
     * <p>
     * <font color="#666666">Get the Request content type (ImContentType)</font>
     *
     * @return Request会话的内容类型 <br /> <font color="#666666">Request content type</font>
     */
    public ImContentType getContentType() {
        return contentType;
    }

    /**
     * 获取Request会话的内容类型字符表示（String）
     * <p>
     * 如果存在ContentTypeStr属性则返回ContentTypeStr属性
     * <p>
     * <font color="#666666">Get the Request string content type (String)</font>
     * <p>
     * <font color="#666666">Returns the ContentTypeStr property if there is a ContentTypeStr property</font>
     *
     * @return Request会话的内容类型字符表示（String） <br /> <font color="#666666">Request string content type (String)</font>
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

}
