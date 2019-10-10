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
 * <b>HTTP 常见编码枚举类</b>
 * <p>
 * 该类明确常见的HTTP编码，并提供枚举选择
 * <br/><br/>
 * <b>HTTP common encoding enumeration class</b>
 * <p>
 * This class specifies common HTTP encodings and provides enumeration options.
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description HTTP 常见编码枚举类
 * @create 2019-10-10 15:07
 */
public enum ImCharset {

    /**
     * [UTF8] 针对Unicode的可变长度字符编码
     * <p>
     * <font color="#666666">[UTF8] Variable length character encoding for Unicode</font>
     */
    UTF_8,
    /**
     * [GB2312] 信息交换用汉字编码字符集
     * <p>
     * <font color="#666666">[GBK] Chinese character coded character set for information exchange</font>
     */
    GB2312,
    /**
     * [GBK] 汉字编码字符集
     * <p>
     * <font color="#666666">[GBK] Chinese Internal Code Specification</font>
     */
    GBK,
    /**
     * [ISO-8859-1] 单字节编码，向下兼容ASCII
     * <p>
     * <font color="#666666">[ISO-8859-1] Single-byte encoding, backward compatible with ASCII</font>
     */
    ISO_8859_1,
    /**
     * [UTF-16] Unicode字符编码五层次模型
     * <p>
     * <font color="#666666">[UTF-16] Unicode character encoding five-level model</font>
     */
    UTF_16,
    /**
     * [Big5] 大五码
     * <p>
     * <font color="#666666">[Big5] Big five yards</font>
     */
    Big5;

    /**
     * 转换编码的完整描述字符
     * <p>
     * <font color="#666666">Convert the full description character of the code</font>
     *
     * @return 编码的完整描述字符 <br/> <font color="#666666">Encoded full description character</font>
     */
    public String toString() {
        switch (this) {
            case UTF_8:
                return "utf-8";
            case GB2312:
                return "gb2312";
            case GBK:
                return "gbk";
            case ISO_8859_1:
                return "iso-8859-1";
            case UTF_16:
                return "utf-16";
            case Big5:
                return "big5";
        }
        return "";
    }
}
