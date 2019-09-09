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
 * <b>忽略SSL相关异常/警告类</b>
 * <p>
 * 有关忽略SSL操作相关的使用、调用、解析异常和警告的异常抛出
 * <br/><br/>
 * <b>Ignore SSL related exceptions/warnings class</b>
 * <p>
 * Exception thrown about ignoring usage, invocation, parsing exceptions, and warnings related to SSL operations
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description 忽略SSL相关异常/警告类
 * @create 2019-09-01 02:24
 */
public final class IgnoreSSLException extends Exception {

    /**
     * 返回一个无法正确忽略SSL异常说明
     * <p>
     * <font color="#666666">Return a description of the SSL exception that cannot be ignored correctly</font>
     */
    public final static String IgnoreSSLCanNot = "Can't set ignore SSL certificates.";

    /**
     * 构建一个忽略SSL相关异常/警告
     * <p>
     * <font color="#666666">Build an ignore SSL related exception/warning</font>
     *
     * @param message 异常/警告信息 <br/> <font color="#666666">Exception/warning information</font>
     */
    public IgnoreSSLException(String message) {
        super(message);
    }

    /**
     * 构建一个忽略SSL相关异常/警告
     * <p>
     * <font color="#666666">Build an ignore SSL related exception/warning</font>
     *
     * @param message 异常/警告信息 <br/> <font color="#666666">Exception/warning information</font>
     * @param cause 异常/警告Throwable <br/> <font color="#666666">Exception/warning Throwable</font>
     */
    public IgnoreSSLException(String message, Throwable cause) {
        super(message, cause);
    }
}
