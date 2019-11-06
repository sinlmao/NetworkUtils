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
 * <b>方法（Method）相关异常/警告类</b>
 * <p>
 * 有关方法（Method）的使用、调用、解析异常和警告的异常抛出
 * <br/><br/>
 * <b>Method related exception/warning class</b>
 * <p>
 * Exception throws about the use, invocation, parsing exceptions, and warnings of methods
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description 方法（Method）相关异常/警告类
 * @create 2019-09-01 02:24
 */
public final class MethodException extends RuntimeException {

    /**
     * 返回一个方法不恰当使用异常说明
     * <p>
     * <font color="#666666">Return a method improper use exception description</font>
     */
    public final static String MethodInappropriate = "Can't use an inappropriate Method.";

    /**
     * 返回当方法为GET时，同时使用InputData和QueryParams是不标准的异常说明
     * <p>
     * <font color="#666666">Return a when the method is GET, using both InputData and QueryParams is not a standard exception description</font>
     */
    public final static String SimultaneouslySetInputDataAndQueryParamsInappropriate = "If use GET Method, Can't simultaneously set InputData and QueryParams.";

    /**
     * 构建一个方法（Method）相关异常/警告
     * <p>
     * <font color="#666666">Build a Method related exception/warning</font>
     *
     * @param message 异常/警告信息 <br/> <font color="#666666">Exception/warning information</font>
     */
    public MethodException(String message) {
        super(message);
    }
}
