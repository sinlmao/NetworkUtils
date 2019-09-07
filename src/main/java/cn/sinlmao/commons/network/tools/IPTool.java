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
package cn.sinlmao.commons.network.tools;

import javax.servlet.http.HttpServletRequest;

/**
 * <b>从HttpServletRequest获得IP地址工具类</b>
 * <p>
 * 该类可以帮组从HttpServletRequest取得访问者的IP地址
 * <br /><br />
 * <b>Obtain IP address tool class from HttpServletRequest</b>
 * <p>
 * This class can help the group get the IP address of the visitor from the HttpServletRequest.
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description 从HttpServletRequest获得IP地址类
 * @create 2019-08-01 11:11
 */
public class IPTool {

    /**
     * 通过HttpServletRequest返回客户端IP地址
     * <p>
     * Return the client IP address via HttpServletRequest
     *
     * @param request HttpServletRequest
     * @return IP地址 <br /> IP address
     */
    public static String getRealIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 通过HttpServletRequest返回客户端IP地址（V2）
     * <p>
     * Return the client IP address via HttpServletRequest (v2)
     *
     * @param request HttpServletRequest
     * @return IP地址 <br /> IP address
     */
    public static String getRealIP_V2(HttpServletRequest request) {
        String accessIP = request.getHeader("x-forwarded-for");
        if (null == accessIP)
            return request.getRemoteAddr();
        return accessIP;
    }
}
