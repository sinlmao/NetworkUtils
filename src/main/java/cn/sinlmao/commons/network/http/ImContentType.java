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
 * <b>HTTP 内容类型（ContentType）枚举类</b>
 * <p>
 * 该类限定并明确目前所支持的内容类型（ContentType），并提供枚举选择
 * <br/><br/>
 * <b>HTTP content type (ContentType) enumeration class</b>
 * <p>
 * This class defines and clarifies the currently supported content types (ContentType) and provides enumeration options.
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description HTTP ContentType 枚举类
 * @create 2019-08-01 11:11
 */
public enum ImContentType {

    /**
     * 表单类型
     * <p>
     * Form type
     */
    APPLICATION_X_WWW_FORM_URLENCODED,

    /**
     * 二进制类型（多行表单）
     * <p>
     * Binary type (multi-line form)
     */
    MULTIPART_FORM_DATA,

    /**
     * JSON内容数据类型
     * <p>
     * JSON content data type
     */
    APPLICATION_JSON,

    /**
     * XML内容数据类型
     * <p>
     * XML content data type
     */
    APPLICATION_XML,

    /**
     * 二进制流（未知内容类型）
     * <p>
     * Binary stream (unknown content type)
     */
    APPLICATION_OCTET_STREAM;


    /**
     * 转换内容类型完整描述字符
     * <p>
     * Convert content type full description character
     *
     * @return 内容类型完整描述字符 <br/> Content type full description character
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
