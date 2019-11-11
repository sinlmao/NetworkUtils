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
 * <b>身份认证相关异常/警告类</b>
 * <p>
 * 有关身份认证的使用、调用、解析异常和警告的异常抛出
 * <br/><br/>
 * <b>Authentication related exception/warning class</b>
 * <p>
 * Exception throws about usage, invocation, parsing exceptions, and warnings for authentication
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description 身份认证相关异常/警告类
 * @create 2019-11-11 21:24
 */
public final class AuthenticationException extends RuntimeException {

    /**
     * 返回一个未经过身份认证异常说明
     * <p>
     * <font color="#666666">Return a unauthenticated exception description</font>
     */
    public final static String NotHaveAuthentication = "Not have authentication.";

    /**
     * 构建一个身份认证相关异常/警告
     * <p>
     * <font color="#666666">Build a authentication related exception/warning</font>
     *
     * @param message 异常/警告信息 <br/> <font color="#666666">Exception/warning information</font>
     */
    public AuthenticationException(String message) {
        super(message);
    }
}
