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
 * <b>HTTP Response类</b>
 * <p>
 * 该类为HTTP请求响应（Response）数据封装，所有与Response相关的数据均在该类封装提供。
 * <br/><br/>
 * <b>HTTP Response class</b>
 * <p>
 * This class is HTTP response (Response) data encapsulation, and all data related to Response is provided in this class package.
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description HTTP Response类
 * @create 2019-08-01 11:11
 */
public final class ImResponse {

    private int responseCode;
    private String responseMessage;
    private String stringContent;
    private byte[] bytesContent;

    private Map<String, List<String>> headers = new HashMap<String, List<String>>();
    private String cookieStr;
    private Map<String, String> cookies = new HashMap<String, String>();

    ///////////////////////////////////////////////////////////////////////

    /**
     * [内部] 设置Response状态码
     * <p>
     * <font color="#666666">[Internal] Set the Response status code</font>
     *
     * @param responseCode Response状态码 <br/> <font color="#666666">Response status code</font>
     * @return ImResponse ImResponse响应实体对象 <br/> <font color="#666666">ImResponse response entity object</font>
     */
    protected ImResponse setResponseCode(int responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    /**
     * [内部] 设置Response状态消息
     * <p>
     * <font color="#666666">[Internal] Set the Response status message</font>
     *
     * @param responseMessage Response状态消息 <br/> <font color="#666666">Response status message</font>
     * @return ImResponse ImResponse响应实体对象 <br/> <font color="#666666">ImResponse response entity object</font>
     */
    protected ImResponse setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
        return this;
    }

    /**
     * [内部] 设置Response应答String消息
     * <p>
     * <font color="#666666">[Internal] Set Response Reply String Message</font>
     *
     * @param stringContent Response应答String消息 <br/> <font color="#666666">Response response String message</font>
     * @return ImResponse ImResponse响应实体对象 <br/> <font color="#666666">ImResponse response entity object</font>
     */
    protected ImResponse setStringContent(String stringContent) {
        this.stringContent = stringContent;
        return this;
    }

    /**
     * [内部] 设置Response应答bytes消息
     * <p>
     * <font color="#666666">[Internal] Set Response Reply bytes message</font>
     *
     * @param bytesContent Response应答bytes消息 <br/> <font color="#666666">Response response bytes message</font>
     * @return ImResponse ImResponse响应实体对象 <br/> <font color="#666666">ImResponse response entity object</font>
     */
    protected ImResponse setBytesContent(byte[] bytesContent) {
        this.bytesContent = bytesContent;
        return this;
    }

    /**
     * [内部] 添加Cookie完整数据
     * <p>
     * <font color="#666666">[Internal] Add full data for cookies</font>
     *
     * @param data Cookie完整数据 <br/> <font color="#666666">Cookie full data</font>
     * @return ImResponse ImResponse响应实体对象 <br/> <font color="#666666">ImResponse response entity object</font>
     */
    protected ImResponse setFullCookie(String data) {
        cookieStr = data;
        return this;
    }

    /**
     * [内部] 添加Cookie数据
     * <p>
     * <font color="#666666">[Internal] Add cookie data</font>
     *
     * @param name Cookie键 <br/> <font color="#666666">Cookie key</font>
     * @param data Cookie值 <br/> <font color="#666666">Cookie value</font>
     * @return ImResponse ImResponse响应实体对象 <br/> ImResponse response entity object</font>
     */
    protected ImResponse addCookie(String name, String data) {
        cookies.put(name, data);
        return this;
    }

    /**
     * [内部] 添加Header完整数据
     * <p>
     * <font color="#666666">[Internal] Add Header full data</font>
     *
     * @param headers Header完整数据 <br/> <font color="#666666">Header full data</font>
     * @return ImResponse ImResponse响应实体对象 <br/> <font color="#666666">ImResponse response entity object</font>
     */
    protected ImResponse setFullHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
        return this;
    }

    ///////////////////////////////////////////////////////////////////////

    /**
     * 获得Response状态码
     * <p>
     * <font color="#666666">Get the Response status code</font>
     *
     * @return Response状态码 <br/> <font color="#666666">Response status code</font>
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * 获得Response状态消息
     * <p>
     * <font color="#666666">Get the Response status message</font>
     *
     * @return Response状态消息 <br/> <font color="#666666">Response status message</font>
     */
    public String getResponseMessage() {
        return responseMessage;
    }

    /**
     * 获得Response应答String消息
     * <p>
     * <font color="#666666">Get the Response response String message</font>
     *
     * @return Response应答String消息 <br/> <font color="#666666">Response response String message</font>
     */
    public String getStringContent() {
        return stringContent;
    }

    /**
     * 获得Response应答Bytes消息
     * <p>
     * <font color="#666666">Get the Response response bytes message</font>
     *
     * @return Response应答bytes消息 <br/> <font color="#666666">Response response bytes message</font>
     */
    public byte[] getBytesContent() {
        return bytesContent;
    }


    /**
     * 使用传入参数Response应答Bytes消息
     * <p>
     * 必须确定传入的bytes数组参数是空的，否则会被覆盖
     * <p>
     * <font color="#666666">Reply to Bytes message with incoming parameter Response</font>
     * <p>
     * <font color="#666666">Must determine that the incoming bytes array parameter is empty, otherwise it will be overwritten</font>
     *
     * @param bytesContent 空的Response应答Bytes接收对象 <br/> <font color="#666666">Empty Response Answers Bytes Receive Object</font>
     */
    public void get(byte[] bytesContent) {
        // this.bytesContent = bytesContent;
        bytesContent = this.bytesContent;
    }

    ///////////////////////////////////////////////////////////////////////

    /**
     * 获得Cookie数据完整字符
     * <p>
     * <font color="#666666">Get the full character of the cookie data</font>
     *
     * @return Cookie数据完整字符 <br/> <font color="#666666">Full character of the cookie data</font>
     */
    public String getCookieStr() {
        return this.cookieStr;
    }

    /**
     * 根据Cookie键获取对象值
     * <p>
     * <font color="#666666">Get the object value according to the Cookie key</font>
     *
     * @param name Cookie键 <br/> <font color="#666666">Cookie key</font>
     * @return Cookie值 <br/> <font color="#666666">Cookie value</font>
     */
    public String getCookieData(String name) {
        return cookies.get(name);
    }

    /**
     * 获取Cookie的所有键
     * <p>
     * <font color="#666666">Get all the keys of the cookie</font>
     *
     * @return Cookie的所有键 <br/> <font color="#666666">All the keys of the cookie</font>
     */
    public Set<String> getCookieNames() {
        return cookies.keySet();
    }

    /**
     * 获取Cookie长度
     * <p>
     * <font color="#666666">Get the size of the cookie</font>
     *
     * @return Cookie长度 <br/> <font color="#666666">Cookie size</font>
     */
    public int getCookieSize() {
        return cookies.size();
    }

    ///////////////////////////////////////////////////////////////////////

    /**
     * 根据Header键获取对象值
     * <p>
     * <font color="#666666">Get the object value according to the Header key</font>
     *
     * @param name Header键 <br/> <font color="#666666">Header key</font>
     * @return Header值 <br/> <font color="#666666">Header value</font>
     */
    public List<String> getHeaderData(String name) {
        return headers.get(name);
    }

    /**
     * 获取Header的所有键
     * <p>
     * <font color="#666666">Get all the keys of the header</font>
     *
     * @return Header的所有键 <br/> <font color="#666666">All the keys of the header</font>
     */
    public Set<String> getHeaderNames() {
        return headers.keySet();
    }

    /**
     * 获取Header长度
     * <p>
     * <font color="#666666">Get the size of the header</font>
     *
     * @return Header长度 <br/> <font color="#666666">Header size</font>
     */
    public int getHeaderSize() {
        return headers.size();
    }

    ///////////////////////////////////////////////////////////////////////

    /**
     * 禁止外部实例化
     */
    protected ImResponse() {
    }
}
