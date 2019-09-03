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

/**
 * HTTP ContentType枚举类
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description HTTP ContentType枚举类
 * @create 2019-08-01 11:11
 */
public enum ImContentType {

    //表单类型
    APPLICATION_X_WWW_FORM_URLENCODED,

    //二进制类型（多行表单）
    MULTIPART_FORM_DATA,

    //JSON数据类型
    APPLICATION_JSON,

    //XML数据类型
    APPLICATION_XML,

    //二进制流（未知文件类型）
    APPLICATION_OCTET_STREAM;


    /**
     *
     * @return
     */
    public String toString() {
        switch (this) {
            case APPLICATION_X_WWW_FORM_URLENCODED:
                return "application/x-www-form-urlencoded";
            case MULTIPART_FORM_DATA:
                return "multipart/form-data";
            case APPLICATION_JSON:
                return "application/json";
            case APPLICATION_XML:
                return "application/xml";
            case APPLICATION_OCTET_STREAM:
                return "application/octet-stream";
        }
        return "";
    }
}
