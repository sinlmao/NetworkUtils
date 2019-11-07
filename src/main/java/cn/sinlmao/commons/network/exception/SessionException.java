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
 * <b>会话状态控制相关异常/警告类</b>
 * <p>
 * 有关会话状态控制的使用、调用、解析异常和警告的异常抛出
 * <br/><br/>
 * <b>Session state related exception/warning class</b>
 * <p>
 * Exception throws about usage, invocation, parsing exceptions, and warnings for session state
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description 数据类型相关异常/警告类
 * @create 2019-09-01 02:24
 */
public final class SessionException extends RuntimeException {

    /**
     * 返回一个数据类型不恰当使用异常说明
     * <p>
     * <font color="#666666">Return a data type inappropriate use exception description</font>
     */
    public final static String DataTypeUnSupport = "This data type is not supported.";

    /**
     * 构建一个数据类型相关异常/警告
     * <p>
     * <font color="#666666">Build a data type related exception/warning</font>
     *
     * @param message 异常/警告信息 <br/> <font color="#666666">Exception/warning information</font>
     */
    public SessionException(String message) {
        super(message);
    }
}
