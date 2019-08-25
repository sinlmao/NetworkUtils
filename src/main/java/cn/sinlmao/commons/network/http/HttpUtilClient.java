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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * HTTP Client工具类
 *
 * @program: Sinlmao Commons Network Utils
 * @description: HTTP Client工具类
 * @author: Sinlmao
 * @create: 2019-08-01 11:11
 */
public class HttpUtilClient {

    /**
     * 发送请求
     *
     * @param httpUtilRequest
     * @return HttpUtilResponse
     * @throws Exception
     */
    public static HttpUtilResponse send(HttpUtilRequest httpUtilRequest) throws Exception {

        //初始化对象
        HttpUtilResponse httpUtilResponse = new HttpUtilResponse();

        //初始化JDK HTTP对象
        URL restServiceURL = new URL(httpUtilRequest.getUrl());
        HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
        httpConnection.setRequestMethod(httpUtilRequest.getMethod().toString());
        httpConnection.setRequestProperty("Accept", "*/*");
        httpConnection.setRequestProperty("Content-Type", getContentType(httpUtilRequest.getContentType()) + ";charset=" + httpUtilRequest.getCharset());

        //如果存在ContentType定义，则设置ContentType值
        if (httpUtilRequest.getContentTypeStr() != null && !"".equals(httpUtilRequest.getContentTypeStr())) {
            httpConnection.setRequestProperty("Content-Type", httpUtilRequest.getContentTypeStr());
        }

        //如果存在Header定义，则设置Header值
        if (httpUtilRequest.getHeaderSize() > 0) {
            Set<String> headerNames = httpUtilRequest.getHeaderNames();
            for (String headerName : headerNames) {
                httpConnection.setRequestProperty(headerName, httpUtilRequest.getHeaderValue(headerName));
            }
        }

        //如果存在Cookie定义，则设置Cookie值
        if (httpUtilRequest.getCookieSize() > 0) {
            Set<String> cookieNames = httpUtilRequest.getHeaderNames();
            StringBuilder cookieStrs = new StringBuilder();
            for (String cookieName : cookieNames) {
                cookieStrs.append(cookieName + "=" + httpUtilRequest.getCookieData(cookieName));
                cookieStrs.append(";");
            }
            String cookieStr = cookieStrs.substring(0, cookieStrs.length() - 1);
            httpConnection.setRequestProperty("Cookie", cookieStr);
        }

        //如果存在InputData值，则设置InputData值
        if (httpUtilRequest.getInputData() != null) {

            String inputData = "";

            //当使用POST、PUT Method
            if ("POST".equals(httpUtilRequest.getMethod().toString())
                    || "PUT".equals(httpUtilRequest.getMethod().toString())) {

                //如果是String类型
                if (httpUtilRequest.getInputData() instanceof String) {
                    inputData = httpUtilRequest.getInputData(String.class);
                }
                //如果是JSON类型
                if (httpUtilRequest.getInputData() instanceof JSONObject) {
                    inputData = httpUtilRequest.getInputData(JSONObject.class).toJSONString();
                }
                //如果是Map类型
                if (httpUtilRequest.getInputData() instanceof Map) {
                    inputData = JSON.toJSONString(httpUtilRequest.getInputData(Map.class));
                }

                httpConnection.setDoOutput(true);
                httpConnection.setDoInput(true);
                httpConnection.setRequestProperty("Accept-Charset", httpUtilRequest.getCharset());

                // httpConnection.setRequestProperty("Content-Type", getContentType(httpUtilRequest.getContentType()) + ";charset=" + httpUtilRequest.getCharset());
                OutputStream outputStream = httpConnection.getOutputStream();
                outputStream.write(inputData.getBytes(Charset.forName(httpUtilRequest.getCharset())));
                outputStream.flush();
                outputStream.close();

            } else {
                //如果使用GET或者其它Method

                //如果是String类型
                if (httpUtilRequest.getInputData() instanceof String) {
                    inputData = httpUtilRequest.getInputData(String.class);
                }
                //如果是JSON类型
                if (httpUtilRequest.getInputData() instanceof JSONObject) {
                    JSONObject json = httpUtilRequest.getInputData(JSONObject.class);
                    for (String key : json.keySet()) {
                        inputData += (key + "=" + json.getString(key) + "&");
                    }
                    inputData = inputData.substring(0, inputData.length() - 1);
                }
                //如果是Map类型
                if (httpUtilRequest.getInputData() instanceof Map) {
                    Map<String, String> map = httpUtilRequest.getInputData(Map.class);
                    for (String key : map.keySet()) {
                        inputData += (key + "=" + map.get(key) + "&");
                    }
                    inputData = inputData.substring(0, inputData.length() - 1);
                }

                httpConnection.setDoOutput(true);

                DataOutputStream dataOutputStream = new DataOutputStream(httpConnection.getOutputStream());
                // 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
                // String content = "字段名=" + URLEncoder.encode("字符串值", "编码");
                // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
                dataOutputStream.writeBytes(inputData);
                //关闭流
                dataOutputStream.flush();
                dataOutputStream.close();
            }
        }

        //返回 Response Code
        httpUtilResponse.setResponseCode(httpConnection.getResponseCode());

        // if (httpUtilResponse.getResponseCode() != 200) {
        // throw new RuntimeException(
        // "HTTP GET Request Failed with Error code : " +
        // httpConnection.getResponseCode());
        // }

        byte[] bytes = toByteArray(httpConnection.getInputStream(), httpUtilRequest.getBytesLength());

        BufferedReader responseBuffer = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(bytes), httpUtilRequest.getCharset()));

        StringBuffer output = new StringBuffer();
        String output_line;

        while ((output_line = responseBuffer.readLine()) != null) {
            output.append(output_line);
        }
        httpConnection.disconnect();

        httpUtilResponse.setStringContent(output.toString());
        // httpUtilResponse.setBytesContent(output.toString().getBytes(Charset.forName(httpUtilRequest.getCharset())));
        httpUtilResponse.setBytesContent(bytes);

        //获取Cookie
        String cookieStr = "";

        //获得Header和Cookie
        Map<String, List<String>> headers = httpConnection.getHeaderFields();
        Set<String> headerNames = headers.keySet();
        for (Iterator<String> iterator = headerNames.iterator(); iterator.hasNext(); ) {
            String headerName = iterator.next();
            if ("Set-Cookie".equals(headerName)) {
                List<String> headerData = headers.get(headerName);
                StringBuilder builder = new StringBuilder();
                for (String data : headerData) {
                    builder.append(data).toString();
                    String[] strs_arry = data.split("; ");
                    for (String str : strs_arry) {
                        String[] str_arry = str.split("=");
                        httpUtilResponse.addCookie(str_arry[0], str_arry[1]);
                    }
                }
                cookieStr = builder.toString();
            }
        }

        //返回Header和Cookie
        httpUtilResponse.setHeaders(headers);
        httpUtilResponse.setCookie(cookieStr);

        return httpUtilResponse;
    }

    /**
     * @param input
     * @param bytesLength
     * @return
     * @throws IOException
     */
    public static ByteArrayOutputStream toByteArrayOutputStream(InputStream input, int bytesLength) throws IOException {
        if (bytesLength == 0) {
            bytesLength = 4096;
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[bytesLength];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        input.close();
        return output;
    }

    /**
     * @param input
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(InputStream input, int bytesLength) throws IOException {
        if (bytesLength == 0) {
            bytesLength = 4096;
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[bytesLength];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        input.close();
        return output.toByteArray();
    }

    /**
     * @param contentType
     * @return
     */
    private static String getContentType(HttpUtilContentType contentType) {
        switch (contentType) {
            case APPLICATION_JSON:
                return "application/json";
            case APPLICATION_X_WWW_FORM_URLENCODED:
                return "application/x-www-form-urlencoded";
        }
        return "";
    }
}
