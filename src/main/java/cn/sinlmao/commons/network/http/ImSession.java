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

import cn.sinlmao.commons.network.callback.ImSessionCallback;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <b>会话状态控制对象类</b>
 * <p>
 * 该类用于需要使用会话状态控制的场景，作为数据存储的对象使用
 * <br/><br/>
 * <b>Session state control object class</b>
 * <p>
 * This class is used for scenarios that require session state control and is used as an object of data storage.
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description 会话状态控制对象类
 * @create 2019-11-07 14:55
 */
public class ImSession {

    private boolean needAuthentication = false;
    private boolean autoAuthentication = false;
    private int autoAuthenticationTryCount = 3;

    private ImSessionCallback callback;

    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> cookies = new HashMap<String, String>();

    private Map<String, Object> extras = new HashMap<String, Object>();

    protected boolean ExecCallbackNow = false;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 设置是否需要身份认证
     * <p>
     * 当设置该标记后，还需要设置ImSessionCallback，否则该项不会生效
     * <p>
     * <font color="#666666">Set whether authentication is required</font>
     * <p>
     * <font color="#666666">After setting this flag, you also need to set ImSessionCallback, otherwise the item will not take effect.</font>
     *
     * @param needAuthentication 是否需要身份认证 <br/> <font color="#666666">Whether authentication is required</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     */
    public ImSession setNeedAuthentication(boolean needAuthentication) {
        this.needAuthentication = needAuthentication;
        return this;
    }

    /**
     * 获取是否需要身份认证
     * <p>
     * <font color="#666666">Get whether authentication is required</font>
     *
     * @return needAuthentication 是否需要身份认证 <br/> <font color="#666666">Whether authentication is required</font>
     */
    public boolean isNeedAuthentication() {
        return needAuthentication;
    }

    /**
     * 设置是否需要自动处理身份认证
     * <p>
     * 当设置该标记后，还需要设置NeedAuthentication和ImSessionCallback，否则该项不会生效
     * <p>
     * <font color="#666666">Set the need to automatically handle identity authentication</font>
     * <p>
     * <font color="#666666">After setting this flag, you need to set NeedAuthentication and ImSessionCallback, otherwise the item will not take effect.</font>
     *
     * @param autoAuthentication 是否需要自动处理身份认证 <br/> <font color="#666666">Whether need to handle identity authentication automatically</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     */
    public ImSession setAutoAuthentication(boolean autoAuthentication) {
        this.autoAuthentication = autoAuthentication;
        return this;
    }

    /**
     * 获取是否需要自动处理身份认证
     * <p>
     * <font color="#666666">Get the need to automatically handle identity authentication</font>
     *
     * @return autoAuthentication 是否需要自动处理身份认证 <br/> <font color="#666666">Whether need to handle identity authentication automatically</font>
     */
    public boolean isAutoAuthentication() {
        return autoAuthentication;
    }

    /**
     * 设置自动处理身份认证时最大次数（最小1次，最大5次）
     * <p>
     * 当设置该值后，还需要设置NeedAuthentication、AutoAuthentication和ImSessionCallback，否则该项不会生效
     * <p>
     * <font color="#666666">Set the maximum number of automatic processing of identity authentication (minimum 1 time, maximum 5 times)</font>
     * <p>
     * <font color="#666666">After setting this value, you also need to set NeedAuthentication, AutoAuthentication and ImSessionCallback, otherwise the item will not take effect.</font>
     *
     * @param autoAuthenticationTryCount 自动处理身份认证时最大次数 <br/> <font color="#666666">Maximum number of automatic processing of identity authentication</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     */
    public ImSession setAutoAuthenticationTryCount(int autoAuthenticationTryCount) {
        if (autoAuthenticationTryCount < 1) {
            autoAuthenticationTryCount = 1;
        }
        if (autoAuthenticationTryCount > 5) {
            autoAuthenticationTryCount = 5;
        }
        this.autoAuthenticationTryCount = autoAuthenticationTryCount;
        return this;
    }

    /**
     * 获取自动处理身份认证时最大次数
     * <p>
     * <font color="#666666">Get the maximum number of automatic processing of identity authentication</font>
     *
     * @return autoAuthenticationTryCount 自动处理身份认证时最大次数 <br/> <font color="#666666">Maximum number of automatic processing of identity authentication</font>
     */
    public int getAutoAuthenticationTryCount() {
        return autoAuthenticationTryCount;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 设置ImSessionCallback的实现接口类
     * <p>
     * <font color="#666666">Set the implementation interface class of ImSessionCallback</font>
     *
     * @param callback ImSessionCallback的实现接口类 <br/> <font color="#666666">ImSessionCallback implementation interface class</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     * @see ImSessionCallback
     */
    public void setCallback(ImSessionCallback callback) {
        this.callback = callback;
    }

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
        if (headers.containsKey(name)) {
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
        if (cookies.containsKey(name)) {
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
     * 添加Extra（额外/附加）数据，当数据已经存在，则不再添加
     * <p>
     * <font color="#666666">Add Extra (extra/additional) data, when the data already exists, it will not be added</font>
     *
     * @param name  Extra键 <br/> <font color="#666666">Extra Key</font>
     * @param value Extra值 <br/> <font color="#666666">Extra Value</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     */
    public ImSession addExtra(String name, Object value) {
        if (!extras.containsKey(name)) {
            extras.put(name, value);
        }
        return this;
    }

    /**
     * 添加Extra（额外/附加）数据，当数据已经存在，则不再添加
     * <p>
     * <font color="#666666">Add Extra (extra/additional) data, when the data already exists, it will not be added</font>
     *
     * @param extras Map类型的Header数据 <br/> <font color="#666666">Map type Extra data</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     */
    public ImSession addExtra(Map<String, Object> extras) {
        if (extras != null && extras.size() > 0) {
            for (String key : extras.keySet()) {
                addExtra(key, extras.get(key));
            }
        }
        return this;
    }

    /**
     * 添加Extra（额外/附加）数据，当数据已经存在，则不再添加
     * <p>
     * <font color="#666666">Add Extra (extra/additional) data, when the data already exists, it will not be added</font>
     *
     * @param extras JSONObject类型的Header数据 <br/> <font color="#666666">JSONObject type Extra data</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     */
    public ImSession addExtra(JSONObject extras) {
        if (extras != null && extras.size() > 0) {
            for (String key : extras.keySet()) {
                addExtra(key, extras.get(key));
            }
        }
        return this;
    }

    /**
     * 设置Extra（额外/附加）数据，当数据已经存在，则以最后设置的为准
     * <p>
     * <font color="#666666">Set the Extra (extra/additional) data. When the data already exists, the last setting is subject to</font>
     *
     * @param name  Header键 <br/> <font color="#666666">Extra Key</font>
     * @param value Header值 <br/> <font color="#666666">Extra Value</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     */
    public ImSession setExtra(String name, Object value) {
        if (!extras.containsKey(name)) {
            extras.remove(name);
        }
        extras.put(name, value);
        return this;
    }

    /**
     * 设置Extra（额外/附加）数据，当数据已经存在，则以最后设置的为准
     * <p>
     * <font color="#666666">Set the Extra (extra/additional) data. When the data already exists, the last setting is subject to</font>
     *
     * @param extras JSONObject类型的Header数据 <br/> <font color="#666666">JSONObject type Extra data</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     */
    public ImSession setExtra(JSONObject extras) {
        if (extras != null && extras.size() > 0) {
            for (String key : extras.keySet()) {
                setExtra(key, extras.get(key));
            }
        }
        return this;
    }

    /**
     * 设置Extra（额外/附加）数据，当数据已经存在，则以最后设置的为准
     * <p>
     * <font color="#666666">Set the Extra (extra/additional) data. When the data already exists, the last setting is subject to</font>
     *
     * @param extras Map类型的Header数据 <br/> <font color="#666666">Map type Extra data</font>
     * @return ImSession对象实体 <br/> <font color="#666666">ImSession object entity</font>
     */
    public ImSession setExtra(Map<String, Object> extras) {
        if (extras != null && extras.size() > 0) {
            for (String key : extras.keySet()) {
                setExtra(key, extras.get(key));
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

    /**
     * 根据Extra（额外/附加）数据键获得对应值
     * <p>
     * <font color="#666666">Get the corresponding value according to the Extra (extra/additional) data key</font>
     *
     * @param name Extra键 <br /> <font color="#666666">Extra Key</font>
     * @return Extra值 <br /> <font color="#666666">Extra Value</font>
     */
    public Object getExtraValue(String name) {
        return extras.get(name);
    }

    /**
     * 获得所有Extra（额外/附加）数据键
     * <p>
     * <font color="#666666">Get all Extra (extra/additional) data keys</font>
     *
     * @return 所有Extra键 <br /> <font color="#666666">All Extra keys</font>
     */
    public Set<String> getExtraNames() {
        return extras.keySet();
    }

    /**
     * 获得Extra（额外/附加）数据数量
     * <p>
     * <font color="#666666">Get the count of Extra (extra/additional) datas</font>
     *
     * @return Extra数量 <br /> <font color="#666666">Extra count</font>
     */
    public int getExtraSize() {
        return extras.size();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected ImSessionCallback getCallback() {
        return callback;
    }

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
