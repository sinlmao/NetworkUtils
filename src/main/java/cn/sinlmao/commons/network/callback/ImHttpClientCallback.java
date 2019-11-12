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
import cn.sinlmao.commons.network.http.ImResponse;

/**
 * <b>HTTP请求回调接口类</b>
 * <p>该接口主要用于在HTTP异步请求时的执行结果回调以及在异步/同步的相关业务控制
 * <br /><br />
 * <b>HTTP request callback interface class</b>
 * <p>This interface is mainly used for execution result callback during HTTP asynchronous request and related business control in asynchronous/synchronous
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description HTTP请求回调接口类
 * @create 2019-11-09 15:16
 * @see ImRequest
 * @see ImResponse
 * @since 1.4.3
 */
public interface ImHttpClientCallback {

    /**
     * HTTP请求时回调
     * <p>
     * <font color="#666666">Callback on HTTP request</font>
     *
     * @param imRequest ImRequest会话请求数据 <br/> <font color="#666666">ImRequest Request data</font>
     */
    void onCallRequest(ImRequest imRequest);

    /**
     * HTTP请求错误时回调
     * <p>
     * 仅在异步时才会执行
     *
     * @param imRequest ImRequest会话请求数据 <br/> <font color="#666666">ImRequest Request data</font>
     * @param throwable 错误/异常数据 <br/> <font color="#666666">Error/abnormal data</font>
     */
    void onError(ImRequest imRequest, Throwable throwable);

    /**
     * HTTP请求成功时回调
     * <p>
     * 当前完成时指未发生任何错误时的完成，错误或异常的HTTP状态码（例如：404、500、401等）也会在此触发
     * <p>
     * 仅在异步时才会执行
     * <p>
     * <font color="#666666">Callback when HTTP request succeeds</font>
     * <p>
     * <font color="#666666">The current completion refers to the completion of any error, the error or abnormal HTTP status code (for example: 404, 500, 401, etc.) will also be triggered here.</font>
     * <p>
     * <font color="#666666">Executed only when asynchronous</font>
     *
     * @param imRequest  ImRequest会话请求数据 <br/> <font color="#666666">ImRequest Request data</font>
     * @param imResponse ImResponse会话响应对象 <br/> <font color="#666666">ImResponse Response object</font>
     */
    void onSuccess(ImRequest imRequest, ImResponse imResponse);

    /**
     * HTTP请求完成时回调
     * <p>
     * 仅在异步时才会执行
     * <p>
     * <font color="#666666">Callback when HTTP request is completed</font>
     * <p>
     * <font color="#666666">Executed only when asynchronous</font>
     *
     * @param imRequest  ImRequest会话请求数据 <br/> <font color="#666666">ImRequest Request data</font>
     * @param imResponse ImResponse会话响应对象 <br/> <font color="#666666">ImResponse Response object</font>
     */
    void onComplete(ImRequest imRequest, ImResponse imResponse);

}
