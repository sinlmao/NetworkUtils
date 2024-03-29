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

import java.util.HashMap;
import java.util.Map;

/**
 * <b>HTTP 方法枚举类</b>
 * <p>
 * 该类限定并明确目前所支持的HTTP方法，并提供枚举选择
 * <br/><br/>
 * <b>HTTP method enumeration class</b>
 * <p>
 * This class defines and clarifies the currently supported HTTP methods and provides enumeration options.
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description HTTP 方法枚举类
 * @create 2019-08-01 11:11
 */
public enum ImMethod {

    /**
     * [GET] 向特定的资源发出请求，得到资源
     * <p>
     * <font color="#666666">[GET] Make a request to a specific resource to get a resource</font>
     */
    GET,
    /**
     * [POST] 向指定资源提交数据进行处理的请求，用于添加新的内容
     * <p>
     * <font color="#666666">[POST] A request to submit data for processing to a specified resource for adding new content</font>
     */
    POST,
    /**
     * [PUT] 向指定资源位置上传其最新的内容，用于修改某个内容
     * <p>
     * <font color="#666666">[PUT] Uploads its latest content to a specified resource location to modify a content</font>
     */
    PUT,
    /**
     * [DELETE] 请求服务器删除请求的URI所标识的资源，用于删除
     * <p>
     * <font color="#666666">[DELETE] Requests the server to delete the resource identified by the requested URI for deletion</font>
     */
    DELETE,
    /**
     * [OPTIONS] 获取服务器支持的HTTP请求方法
     * <p>
     * <font color="#666666">[OPTIONS] Get the HTTP request method supported by the server</font>
     */
    OPTIONS,
    /**
     * [HEAD] 与GET很像，但是不返回响应体信息，用于检查对象是否存在，并获取包含在响应消息头中的信息
     * <p>
     * <font color="#666666">[HEAD] is similar to GET, but does not return response body information, is used to check whether the object exists,
     * and obtain the information contained in the response message header.</font>
     */
    HEAD,
    /**
     * [PATCH] 是对 PUT 方法的补充，用来对已知资源进行局部更新
     * <p>
     * <font color="#666666">[PATCH] is a supplement to the PUT method for local updates to known resources</font>
     */
    PATCH;

    private static final Map<String, ImMethod> stringToEnum = new HashMap<String, ImMethod>();

    static {
        for (ImMethod blah : values()) {
            stringToEnum.put(blah.toString(), blah);
        }
    }

    public static ImMethod fromString(String symbol) {
        return stringToEnum.get(symbol);
    }
}
