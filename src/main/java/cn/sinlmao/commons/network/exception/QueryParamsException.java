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
package cn.sinlmao.commons.network.exception;

/**
 * <b>查询参数（QueryParams）相关异常/警告类</b>
 * <p>
 * 有关查询参数（QueryParams）的使用、调用、解析异常和警告的异常抛出
 * <br/><br/>
 * <b>Query parameters (QueryParams) related exception/warning class</b>
 * <p>
 * Exception throws about the use, invocation, parsing exceptions, and warnings of query parameters (QueryParams)
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description 查询参数（QueryParams）相关异常/警告类
 * @create 2019-11-06 17:24
 */
public final class QueryParamsException extends RuntimeException {

    /**
     * 返回一个查询参数（QueryParams）设置后，不规范的URL使用行为
     * <p>
     * <font color="#666666">Return query parameter (QueryParams) after set, non-standard URL usage behavior</font>
     */
    public final static String AfterQueryParamsURLInappropriate = "Improper use of URL after query parameter (QueryParams) setting.";

    /**
     * 返回一个查询参数（QueryParams）设置后，出现与强制URL传值方式的冲突
     * <p>
     * <font color="#666666">Return after set query parameter (QueryParams), there is a conflict with the forced URL value method.</font>
     */
    public final static String AfterSetQueryParamsCanNotUseForceInUrlSendData = "After the query parameter (QueryParams) is set, there is a conflict with the forced URL value.";

    /**
     * 构建一个查询参数（QueryParams）相关异常/警告
     * <p>
     * <font color="#666666">Build a query parameters (QueryParams)  related exception/warning</font>
     *
     * @param message 异常/警告信息 <br/> <font color="#666666">Exception/warning information</font>
     */
    public QueryParamsException(String message) {
        super(message);
    }
}
