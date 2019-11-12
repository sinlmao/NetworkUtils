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
package cn.sinlmao.commons.network.callback;

import cn.sinlmao.commons.network.http.ImRequest;
import cn.sinlmao.commons.network.http.ImSession;

import java.util.Map;

/**
 * <b>会话管理控制回调接口类</b>
 * <p>该接口主要用于在会话管理控制时的相关业务控制和业务嵌入，比如获取身份认证和身份认证状态检查的业务控制
 * <br /><br />
 * <b>Session management control callback interface class</b>
 * <p>This interface is mainly used for related service control and service embedding during session management control,
 * such as obtaining service control for identity authentication and identity authentication status check
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description 会话管理控制回调接口类
 * @create 2019-11-09 15:15
 * @see ImSession
 * @see ImRequest
 * @since 1.4.3
 */
public interface ImSessionCallback {

    /**
     * 确定是否具备身份认证的业务回调
     * <p>
     * 当配置会话状态需要身份认证后，才会执行此回调
     * <p>
     * <font color="#666666">Determine if there is a business callback with authentication</font>
     * <p>
     * <font color="#666666">This callback is only executed when the session state needs to be authenticated.</font>
     *
     * @param imSession ImSession会话状态控制对象 <br/> <font color="#666666">ImSession session state control object</font>
     * @param imRequest ImResponse会话响应对象 <br/> <font color="#666666">ImResponse Response object</font>
     * @return 处理结果  <br/> <font color="#666666">ImRequest Request data</font>
     */
    boolean isAuthentication(ImSession imSession, ImRequest imRequest);

    /**
     * 执行身份认证的业务回调
     * <p>
     * 当配置会话状态需要身份认证后且未具备身份认证时，才会执行此回调
     * <p>
     * <font color="#666666">Business callback for identity authentication</font>
     * <p>
     * <font color="#666666">This callback is executed when the configuration session state requires authentication and does not have authentication.</font>
     *
     * @param imSession ImSession会话状态控制对象 <br/> <font color="#666666">ImSession session state control object</font>
     * @param imRequest ImResponse会话响应对象 <br/> <font color="#666666">ImResponse Response object</font>
     * @return 处理结果  <br/> <font color="#666666">ImRequest Request data</font>
     */
    boolean doAuthentication(ImSession imSession, ImRequest imRequest);

    /**
     * 执行时发生错误的回调
     * <p>
     * 仅在异步时才会执行
     * <p>
     * <font color="#666666">An error callback occurred during execution</font>
     * <p>
     * <font color="#666666">Executed only when asynchronous</font>
     *
     * @param imSession ImSession会话状态控制对象 <br/> <font color="#666666">ImSession session state control object</font>
     * @param imRequest ImRequest会话请求数据 <br/> <font color="#666666">ImRequest Request data</font>
     * @param throwable 错误/异常数据 <br/> <font color="#666666">Error/abnormal data</font>
     */
    void onError(ImSession imSession, ImRequest imRequest, Throwable throwable);

}
