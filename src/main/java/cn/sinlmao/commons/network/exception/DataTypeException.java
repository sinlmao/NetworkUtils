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
 * <b>数据类型相关异常/警告类</b>
 * <p>
 * 有关数据类型的使用、调用、解析异常和警告的异常抛出
 * <br/><br/>
 * <b>Data type related exception/warning class</b>
 * <p>
 * Exception throws about usage, invocation, parsing exceptions, and warnings for data types
 *
 * @program: Sinlmao Commons Network Utils
 * @description: 数据类型相关异常/警告类
 * @author: Sinlmao
 * @create: 2019-09-01 02:24
 */
public final class DataTypeException extends RuntimeException {

    /**
     * 返回一个方法不恰当使用异常说明
     */
    public final static String DataTypeUnSupport = "This data type is not supported.";

    public DataTypeException(String message) {
        super(message);
    }
}
