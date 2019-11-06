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

import cn.sinlmao.commons.network.bean.ImBytesData;
import cn.sinlmao.commons.network.bean.ImFileData;
import cn.sinlmao.commons.network.bean.ImFormData;
import cn.sinlmao.commons.network.bean.ImMultipartFormData;
import cn.sinlmao.commons.network.exception.*;
import cn.sinlmao.commons.network.tools.IgnoreSSLTool;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

/**
 * <b>HTTP Client实现类</b>
 * <p>
 * 该类用于发送HTTP请求，而所有请求的数据都需要封装在ImRequest中，使用send方法发起请求
 * <br/><br/>
 * <b>HTTP Client implementation class</b>
 * <p>
 * This class is used to send HTTP requests, and all requested data needs to be encapsulated in the ImRequest,
 * using the send method to initiate the request.
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description HTTP Client实现类
 * @create 2019-08-01 11:11
 */
public final class ImHttpClient {

    private final static String PREFIX = "--";
    private final static String WRAP = System.getProperty("line.separator");

    /**
     * 发送请求
     * <p>
     * <font color="#666666">Send Request</font>
     *
     * @param imRequest ImRequest会话请求数据 <br/> <font color="#666666">ImRequest Request data</font>
     * @return ImResponse会话响应对象 <br/> <font color="#666666">ImResponse Response object</font>
     * @throws ContentTypeException 内容类型（ContentType）使用相关异常/警告 <br/> <font color="#666666">Content Type (ContentType) uses related exceptions/warnings</font>
     * @throws DataTypeException    数据类型使用相关异常/警告 <br/> <font color="#666666">Data type usage related exceptions/warnings</font>
     * @throws MethodException      方法（Method）使用相关异常/警告 <br/> <font color="#666666">Method uses related exceptions/warnings</font>
     * @throws IgnoreSSLException   忽略SSL相关异常/警告 <br/> <font color="#666666">Ignore SSL related exceptions/warnings</font>
     * @throws IOException          IO异常 <br/> <font color="#666666">IO exception</font>
     */
    public static ImResponse send(ImRequest imRequest)
            throws ContentTypeException, DataTypeException, MethodException, IgnoreSSLException, IOException {

        //初始化分隔符（如果为文件上传(multipart/form-data)模式的时候）
        String boundary = "--------------------------" + String.valueOf(System.currentTimeMillis()); // boundary就是request头和上传文件内容的分隔符

        //取得系统（JAVA/JDK）原代理服务相关配置
        String system_http_proxyHost = System.getProperty("http.proxyHost", "");
        String system_https_proxyHost = System.getProperty("https.proxyHost", "");
        String system_http_proxyPort = System.getProperty("http.proxyPort", "");
        String system_https_proxyPort = System.getProperty("https.proxyPort", "");

        //检测是否配置代理服务器（或抓包工具）
        if (imRequest.isEnableProxyServer()) {
            System.setProperty("http.proxyHost", imRequest.getProxyServerHost());
            System.setProperty("https.proxyHost", imRequest.getProxyServerHost());
            System.setProperty("http.proxyPort", imRequest.getProxyServerPort());
            System.setProperty("https.proxyPort", imRequest.getProxyServerPort());
        } else {
            System.setProperty("http.proxyHost", system_http_proxyHost);
            System.setProperty("https.proxyHost", system_https_proxyHost);
            System.setProperty("http.proxyPort", system_http_proxyPort);
            System.setProperty("https.proxyPort", system_https_proxyPort);
        }

        //如果没有配置为允许非标准使用，进行标准检查
        if (!imRequest.isAllowNonStandard()) {
            //如果内容类型没有使用表单（x-www-form-urlencoded）
            if (imRequest.getContentType() != ImContentType.APPLICATION_X_WWW_FORM_URLENCODED) {
                //如果在GET、HEAD方法下没有使用application/x-www-form-urlencoded内容类型，则抛出异常告知开发者
                if (imRequest.getMethod() == ImMethod.GET
                        || imRequest.getMethod() == ImMethod.HEAD) {
                    throw new ContentTypeException(ContentTypeException.ContentTypeInappropriate);
                }
            }
            //如果内容类型没有使用多行表单（multipart/form-data）
            if (imRequest.getContentType() != ImContentType.MULTIPART_FORM_DATA) {
                if (imRequest.getInputData() != null) {
                    //非multipart/form-data内容类型不支持直接设置byte数据，如果使用byte数据，则抛出异常告知开发者
                    if (imRequest.getInputData().getClass() == ImBytesData.class
                            || imRequest.getInputData().getClass() == ImFileData.class
                            || imRequest.getInputData().getClass() == ImMultipartFormData.class) {
                        throw new MethodException(MethodException.MethodInappropriate);
                    }
                    //非multipart/form-data内容类型不支持的ImFormData数据类型，如果使用该数据类型，则抛出异常告知开发者
                    if (imRequest.getInputData().getClass() == ImFormData.class) {
                        throw new DataTypeException(DataTypeException.DataTypeUnSupport);
                    }
                }
            }
            //如果内容类型已经使用多行表单（multipart/form-data）
            if (imRequest.getContentType() == ImContentType.MULTIPART_FORM_DATA) {
                //非POST、PUT方法不支持直接设置byte数据，则抛出异常告知开发者
                if (imRequest.getMethod() != ImMethod.POST
                        && imRequest.getMethod() != ImMethod.PUT) {
                    throw new MethodException(MethodException.MethodInappropriate);
                }
            }
            //如果已经传入Query Parameter
            if (imRequest.getQueryParams() != null) {
                //如果URL中包含试图额外传值的行为，抛出异常告知开发者
                if (imRequest.getUrl().contains("?")) {
                    throw new QueryParamsException(QueryParamsException.AfterQueryParamsURLInappropriate);
                }
                //如果方法为GET，且同时InputData，抛出异常告知开发者
                if (imRequest.getMethod() == ImMethod.GET && imRequest.getInputData() != null) {
                    throw new MethodException(MethodException.SimultaneouslySetInputDataAndQueryParamsInappropriate);
                }
            }
        }

        //当时设置为强制在URL中传值时，进行一些必要的强制校验（不可忽略和关闭）
        if (imRequest.isForceInUrlSendData()) {
            //对输入数据（InputData）类型的一些强制校验
            if (imRequest.getInputData() != null) {
                if (imRequest.getInputData().getClass() == ImFileData.class
                        || imRequest.getInputData().getClass() == ImMultipartFormData.class
                        || imRequest.getInputData().getClass() == ImBytesData.class) {
                    throw new DataTypeException(DataTypeException.DataTypeUnSupport);
                }
            }
            //对内容类型的一些强制校验
            if (imRequest.getContentType() != ImContentType.APPLICATION_X_WWW_FORM_URLENCODED) {
                throw new ContentTypeException(ContentTypeException.ContentTypeInappropriate);
            }
            //如果额外已经设置Query Parameter
            if (imRequest.getQueryParams() != null) {
                throw new QueryParamsException(QueryParamsException.AfterSetQueryParamsCanNotUseForceInUrlSendData);
            }
        }

        //获得URL字符
        String urlStr = imRequest.getUrl();

        //确认在URL中传值
        if (imRequest.isForceInUrlSendData()) {
            if (imRequest.getInputData() != null) {
                //初始化URL值字符串
                String urlPars = getInputDataToString(imRequest, true);
                //组装URL和URL需要传的值数据
                if (urlStr.contains("?")) {
                    urlStr += ("&" + urlPars);
                } else {
                    urlStr += ("?" + urlPars);
                }
            }
        }
        //在URL中设置Query Params
        if (imRequest.getQueryParams() != null) {
            //初始化URL值字符串
            String urlPars = getQueryParamsToString(imRequest);
            //组装URL和URL需要传的值数据
            if (urlStr.contains("?")) {
                urlStr += ("&" + urlPars);
            } else {
                urlStr += ("?" + urlPars);
            }
        }

        //初始化对象
        ImResponse imResponse = new ImResponse();

        //配置是否忽略证书可信验证
        IgnoreSSLTool.setIsIgnore(imRequest.isIgnoreSSLCertVerify());

        //初始化JDK HTTP对象
        URL url = new URL(urlStr);
        //获得HttpURLConnection
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        //设置Method
        httpConnection.setRequestMethod(imRequest.getMethod().toString());
        //设置是否使用缓存
        httpConnection.setUseCaches(imRequest.isUseCache());
        //设置接收编码
        httpConnection.setRequestProperty("Accept-Charset", imRequest.getCharset());
        //设置接收内容类型
        httpConnection.setRequestProperty("Accept", "*/*");
        //设置内容类型及编码
        httpConnection.setRequestProperty("Content-Type", imRequest.getContentType().toString() + "; charset=" + imRequest.getCharset());
        //httpConnection.setRequestProperty("Content-Type", imRequest.getContentType().toString());

        //如果需要长连接
        if (imRequest.isKeepAlive() || imRequest.getContentType() == ImContentType.MULTIPART_FORM_DATA) {
            //开启长连接可以持续传输
            httpConnection.setRequestProperty("Connection", "keep-alive");
        }

        //如果ContentType是multipart/form-data，则需要分段标记，并且需要设置块大小
        if (imRequest.getContentType() == ImContentType.MULTIPART_FORM_DATA) {
            httpConnection.setRequestProperty("Content-Type", imRequest.getContentType().toString()
                    + "; charset=" + imRequest.getCharset()
                    + "; boundary=" + boundary);
            httpConnection.setChunkedStreamingMode(0);
        }

        //如果存在ContentType定义，则设置ContentType值
        if (imRequest.getContentTypeStr() != null && !"".equals(imRequest.getContentTypeStr())) {
            httpConnection.setRequestProperty("Content-Type", imRequest.getContentTypeStr());
        }

        //如果存在Header定义，则设置Header值
        if (imRequest.getHeaderSize() > 0) {
            Set<String> headerNames = imRequest.getHeaderNames();
            for (String headerName : headerNames) {
                httpConnection.setRequestProperty(headerName, imRequest.getHeaderValue(headerName));
            }
        }

        //开始处理Cookie
        StringBuilder cookieStrs = new StringBuilder();

        //如果存在Cookie定义，则设置Cookie值
        if (imRequest.getCookieSize() > 0) {
            Set<String> cookieNames = imRequest.getCookieNames();
            for (String cookieName : cookieNames) {
                cookieStrs.append(cookieName + "=" + imRequest.getCookieData(cookieName));
                cookieStrs.append(";");
            }
        }

        //如果需要Tomcat兼容，则需要添加必须的Cookie
        if (imRequest.isTomcatCompatible()) {
            if (imRequest.getCookieData("JSESSIONID") == null || "".equals(imRequest.getCookieData("JSESSIONID").trim())) {
                cookieStrs.append("JSESSIONID=" + UUID.randomUUID().toString().replace("-", "").toUpperCase());
                cookieStrs.append(";");
            }
        }

        //设置Cookie
        if (cookieStrs.length() > 1) {
            httpConnection.setRequestProperty("Cookie", cookieStrs.substring(0, cookieStrs.length() - 1));
        }

        //如果存在InputData值，则设置InputData值
        if (imRequest.getInputData() != null) {

            //如果不是文件上传内容类型
            if (imRequest.getContentType() != ImContentType.MULTIPART_FORM_DATA) {

                //初始化inputData
                String inputData = "";

                //如果内容类型不为表单（x-www-form-urlencoded）
                if (imRequest.getContentType() != ImContentType.APPLICATION_X_WWW_FORM_URLENCODED) {

                    //获取InputData的String形式
                    inputData = getInputDataToString(imRequest);

                    httpConnection.setDoOutput(true);
                    httpConnection.setDoInput(true);

                    //确认不在URL中传值
                    if (!imRequest.isForceInUrlSendData()) {
                        //获取写入流
                        OutputStream outputStream = httpConnection.getOutputStream();
                        outputStream.write(inputData.getBytes(Charset.forName(imRequest.getCharset())));
                        //关闭写入流
                        outputStream.flush();
                        outputStream.close();
                    }
                } else {    //如果内容类型为表单（x-www-form-urlencoded）

                    inputData = getInputDataToString(imRequest, true);

                    httpConnection.setDoOutput(true);
                    httpConnection.setDoInput(true);

                    //确认不在URL中传值
                    if (!imRequest.isForceInUrlSendData()) {
                        //获取写入流
                        OutputStream outputStream = httpConnection.getOutputStream();

                        // 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
                        // String content = "字段名=" + URLEncoder.encode("字符串值", "编码");
                        // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
                        outputStream.write(inputData.getBytes(Charset.forName(imRequest.getCharset())));

                        //关闭写入流
                        outputStream.flush();
                        outputStream.close();
                    }
                }
            } else {    //如果为文件上传(multipart/form-data)模式

                httpConnection.setDoOutput(true);
                httpConnection.setDoInput(true);

                //获取数据写入流
                //DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
                DataOutputStream dataOutputStream = new DataOutputStream(httpConnection.getOutputStream());

                //分隔符头部
                String file_header = PREFIX + boundary + WRAP;
                //分隔符分隔
                String file_separate = WRAP + PREFIX + boundary + WRAP;
                //分隔符尾部
                String file_footer = WRAP + PREFIX + boundary + "--";

                //如果是单文件数据
                if (imRequest.getInputData().getClass() == ImFileData.class) {

                    //获得文件数据
                    ImFileData imFileData = imRequest.getInputData(ImFileData.class);

                    //文件头部信息
                    String file_disposition = "Content-Disposition: form-data;"
                            + " name=\"" + imFileData.getName() + "\";"
                            + " filename=\"" + imFileData.getFileName() + "\""
                            + WRAP;
                    //文件类型信息
                    String file_content_type = "Content-Type: "
                            + imFileData.getFileType()
                            + WRAP + WRAP;

                    //写入分隔符头部
                    dataOutputStream.writeBytes(file_header);
                    //写入文件流信息
                    dataOutputStream.writeBytes(file_disposition);
                    dataOutputStream.writeBytes(file_content_type);
                    //写入文件流数据
                    dataOutputStream.write(imFileData.getBytes());
                    //写入分隔符尾部
                    dataOutputStream.writeBytes(file_footer);
                }

                //如果是多部分构成的表单数据
                if (imRequest.getInputData().getClass() == ImMultipartFormData.class) {

                    //获得文件数据
                    ImMultipartFormData imMultipartFormData = imRequest.getInputData(ImMultipartFormData.class);

                    //写入分隔符头部
                    dataOutputStream.writeBytes(file_header);

                    //循环读取数据
                    while (imMultipartFormData.hasNext()) {

                        //读取数据
                        Object data = imMultipartFormData.nextData();

                        //如果为ImFileData数据
                        if (data.getClass() == ImFileData.class) {

                            //获得数据
                            ImFileData imFileData = (ImFileData) data;

                            //文件头部信息
                            String file_disposition = "Content-Disposition: form-data;"
                                    + " name=\"" + imFileData.getName() + "\";"
                                    + " filename=\"" + imFileData.getFileName() + "\""
                                    + WRAP;
                            //文件类型信息
                            String file_content_type = "Content-Type: "
                                    + imFileData.getFileType()
                                    + WRAP + WRAP;

                            //写入文件流信息
                            dataOutputStream.writeBytes(file_disposition);
                            dataOutputStream.writeBytes(file_content_type);
                            //写入文件流数据
                            dataOutputStream.write(imFileData.getBytes());
                        }

                        //如果为ImFormData数据
                        if (data.getClass() == ImFormData.class) {

                            ImFormData imFormData = (ImFormData) data;

                            //文件头部信息
                            String file_disposition = "Content-Disposition: form-data;"
                                    + " name=\"" + imFormData.getName() + "\""
                                    + WRAP;
                            //文件类型信息
                            String file_content_type = "Content-Type: "
                                    + imFormData.getContentType()
                                    + WRAP + WRAP;

                            //写入文件流信息
                            dataOutputStream.writeBytes(file_disposition);
                            dataOutputStream.writeBytes(file_content_type);
                            //写入文件流数据
                            dataOutputStream.writeBytes(imFormData.getValue());
                        }

                        if (imMultipartFormData.hasNext()) {
                            //写入分隔符号分隔
                            dataOutputStream.writeBytes(file_separate);
                        }
                    }

                    //写入分隔符尾部
                    dataOutputStream.writeBytes(file_footer);
                }

                //如果是纯字节数据
                if (imRequest.getInputData().getClass() == ImBytesData.class) {
                    //获得字节数据
                    ImBytesData imBytesData = imRequest.getInputData(ImBytesData.class);
                    //写入字节数据
                    dataOutputStream.write(imBytesData.getBytes());
                }

                //关闭数据写入流
                dataOutputStream.flush();
                dataOutputStream.close();
            }
        }

        //返回 Response Code
        imResponse.setResponseCode(httpConnection.getResponseCode());
        //返回 Response Message
        imResponse.setResponseMessage(httpConnection.getResponseMessage());

        //获得返回的bytes
        byte[] out_bytes = toByteArray(httpConnection.getInputStream(), imRequest.getBytesLength());

        //创建输入流
        BufferedReader responseBuffer = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(out_bytes), imRequest.getCharset()));

        //初始化StringBuffer
        StringBuffer output = new StringBuffer();
        //初始化String
        String output_line;

        //从输入流获得数据
        while ((output_line = responseBuffer.readLine()) != null) {
            output.append(output_line);
            output.append(System.getProperty("line.separator"));
        }

        //关闭HttpConnection
        httpConnection.disconnect();

        //设置返回Response的StringContent
        imResponse.setStringContent(output.toString());
        // imResponse.setBytesContent(output.toString().getBytes(Charset.forName(httpUtilRequest.getCharset())));

        //设置返回Response的BytesContent
        imResponse.setBytesContent(out_bytes);

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
                        if (str_arry.length == 2) {
                            imResponse.addCookie(str_arry[0], str_arry[1]);
                        } else {
                            imResponse.addCookie(str_arry[0], null);
                        }
                    }
                }
                cookieStr = builder.toString();
            }
        }

        //返回Header和Cookie
        imResponse.setFullHeaders(headers);
        imResponse.setFullCookie(cookieStr);

        //还原系统代理配置
        System.setProperty("http.proxyHost", system_http_proxyHost);
        System.setProperty("https.proxyHost", system_https_proxyHost);
        System.setProperty("http.proxyPort", system_http_proxyPort);
        System.setProperty("https.proxyPort", system_https_proxyPort);

        return imResponse;
    }

    /**
     * 获取InputData的String形式（含JSON字符、KeyValue字符）
     *
     * @param imRequest
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String getInputDataToString(ImRequest imRequest) throws UnsupportedEncodingException {
        return getInputDataToString(imRequest, false);
    }

    /**
     * 获取InputData的String形式（含JSON字符、KeyValue字符）
     *
     * @param imRequest
     * @param forceKeyValueModel
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String getInputDataToString(ImRequest imRequest, boolean forceKeyValueModel) throws UnsupportedEncodingException {
        //当InputData不为空时
        if (imRequest.getInputData() != null) {

            //初始化返回对象
            String inputData = "";

            //如果是String类型
            if (imRequest.getInputData() instanceof String) {
                inputData = imRequest.getInputData(String.class);
            }
            //如果是JSON类型
            //if (imRequest.getInputData() instanceof JSONObject) {
            if (imRequest.getInputData().getClass() == JSONObject.class) {
                if (imRequest.getContentType() == ImContentType.APPLICATION_JSON && !forceKeyValueModel) {
                    inputData = imRequest.getInputData(JSONObject.class).toJSONString();
                } else {
                    JSONObject json = imRequest.getInputData(JSONObject.class);
                    for (String key : json.keySet()) {
                        if (imRequest.isUrlEncode()) {
                            inputData += (key + "=" + URLEncoder.encode(json.getString(key), imRequest.getCharset()) + "&");
                        } else {
                            inputData += (key + "=" + json.getString(key) + "&");
                        }
                    }
                    inputData = inputData.substring(0, inputData.length() - 1);
                }
            }
            //如果是Map类型
            //if (imRequest.getInputData() instanceof Map) {
            if (imRequest.getInputData().getClass() != JSONObject.class && imRequest.getInputData() instanceof Map) {
                if (imRequest.getContentType() == ImContentType.APPLICATION_JSON && !forceKeyValueModel) {
                    inputData = JSON.toJSONString(imRequest.getInputData(Map.class));
                } else {
                    Map<String, String> map = imRequest.getInputData(Map.class);
                    for (String key : map.keySet()) {
                        if (imRequest.isUrlEncode()) {
                            inputData += (key + "=" + URLEncoder.encode(map.get(key), imRequest.getCharset()) + "&");
                        } else {
                            inputData += (key + "=" + map.get(key) + "&");
                        }
                    }
                    inputData = inputData.substring(0, inputData.length() - 1);
                }
            }
            //如果是ImFormData类型
            if (imRequest.getInputData().getClass() == ImFormData.class) {
                ImFormData imFormData = imRequest.getInputData(ImFormData.class);
                if (imRequest.getContentType() == ImContentType.APPLICATION_JSON && !forceKeyValueModel) {
                    JSONObject json = new JSONObject();
                    json.put(imFormData.getName(), imFormData.getValue());
                    inputData = json.toJSONString();
                } else {
                    if (imRequest.isUrlEncode()) {
                        inputData = (imFormData.getName() + "=" + URLEncoder.encode(imFormData.getValue(), imRequest.getCharset()));
                    } else {
                        inputData = (imFormData.getName() + "=" + imFormData.getValue());
                    }
                }
            }

            return inputData;
        }
        return null;
    }

    /**
     * 获取QueryParams的String形式（含JSON字符、KeyValue字符）
     *
     * @param imRequest
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String getQueryParamsToString(ImRequest imRequest) throws UnsupportedEncodingException {
        //当InputData不为空时
        if (imRequest.getQueryParams() != null) {

            //初始化返回对象
            String queryParams = "";

            //如果是String类型
            if (imRequest.getQueryParams() instanceof String) {
                queryParams = imRequest.getQueryParams(String.class);
            }
            //如果是JSON类型
            //if (imRequest.getQueryParams() instanceof JSONObject) {
            if (imRequest.getQueryParams().getClass() == JSONObject.class) {
                JSONObject json = imRequest.getQueryParams(JSONObject.class);
                for (String key : json.keySet()) {
                    if (imRequest.isUrlEncode()) {
                        queryParams += (key + "=" + URLEncoder.encode(json.getString(key), imRequest.getCharset()) + "&");
                    } else {
                        queryParams += (key + "=" + json.getString(key) + "&");
                    }
                }
                queryParams = queryParams.substring(0, queryParams.length() - 1);
            }
            //如果是Map类型
            //if (imRequest.getQueryParams() instanceof Map) {
            if (imRequest.getQueryParams().getClass() != JSONObject.class && imRequest.getQueryParams() instanceof Map) {
                Map<String, String> map = imRequest.getQueryParams(Map.class);
                for (String key : map.keySet()) {
                    if (imRequest.isUrlEncode()) {
                        queryParams += (key + "=" + URLEncoder.encode(map.get(key), imRequest.getCharset()) + "&");
                    } else {
                        queryParams += (key + "=" + map.get(key) + "&");
                    }
                }
                queryParams = queryParams.substring(0, queryParams.length() - 1);
            }

            return queryParams;
        }
        return null;
    }

    /**
     * @param input
     * @param bytesLength
     * @return
     * @throws IOException
     */
    private static ByteArrayOutputStream toByteArrayOutputStream(InputStream input, int bytesLength) throws IOException {
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
    private static byte[] toByteArray(InputStream input, int bytesLength) throws IOException {
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
     * 禁止被实例化
     */
    private ImHttpClient() {
    }
}
