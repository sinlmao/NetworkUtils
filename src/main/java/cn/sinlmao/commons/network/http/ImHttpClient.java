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

import cn.sinlmao.commons.network.bean.*;
import cn.sinlmao.commons.network.callback.ImHttpClientCallback;
import cn.sinlmao.commons.network.callback.ImSessionCallback;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

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
 * @version 1.4.3
 * @program Sinlmao Commons Network Utils
 * @description HTTP Client实现类
 * @create 2019-08-01 11:11
 */
public final class ImHttpClient {

    public final static String VERSION = "1.4.3";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private final static String PREFIX = "--";
    private final static String WRAP = System.getProperty("line.separator");

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static ThreadFactory THREAD_FACTORY = Executors.defaultThreadFactory();

    /**
     * 发起一个带会话状态的请求
     * <p>
     * <font color="#666666">Send a request with a session state</font>
     * <p>
     * 请注意，如果在ImRequest中指示为异步请求，那么此时返回的ImResponse将会是空值，需要在ImRequest中另行实现ImHttpCallback接口才能获得返回值
     * <p>
     * <font color="#666666">Note that if the asynchronous request is indicated in the ImRequest, then the ImResponse returned at this time will be null,
     * and the ImHttpCallback interface needs to be implemented separately in the ImRequest to get the return value.</font>
     *
     * @param imRequest ImRequest会话请求数据 <br/> <font color="#666666">ImRequest Request data</font>
     * @param imSession ImSession会话状态数据 <br/> <font color="#666666">ImSession session state data</font>
     * @return ImResponse会话响应对象 <br/> <font color="#666666">ImResponse Response object</font>
     * @throws SessionException        会话状态控制相关异常/警告 <br/> <font color="#666666">Session state related exception/warning</font>
     * @throws ContentTypeException    内容类型（ContentType）使用相关异常/警告 <br/> <font color="#666666">Content Type (ContentType) uses related exceptions/warnings</font>
     * @throws DataTypeException       数据类型使用相关异常/警告 <br/> <font color="#666666">Data type usage related exceptions/warnings</font>
     * @throws MethodException         方法（Method）使用相关异常/警告 <br/> <font color="#666666">Method uses related exceptions/warnings</font>
     * @throws IgnoreSSLException      忽略SSL相关异常/警告 <br/> <font color="#666666">Ignore SSL related exceptions/warnings</font>
     * @throws QueryParamsException    查询参数（QueryParams）相关异常/警告类 <br/> <font color="#666666">Query parameters (QueryParams) related exception/warning</font>
     * @throws AuthenticationException 身份认证相关异常/警告类 <br/> <font color="#666666">Authentication related exception/warning</font>
     * @throws IOException             IO异常 <br/> <font color="#666666">IO exception</font>
     * @see ImRequest
     * @see ImSession
     * @since 1.4.1
     */
    public static ImResponse send(ImRequest imRequest, ImSession imSession)
            throws SessionException, ContentTypeException, DataTypeException, MethodException, IgnoreSSLException, QueryParamsException, AuthenticationException, IOException {

        //非空判断
        if (imRequest == null || imSession == null) {
            throw new NullPointerException();
        }

        //如果是异步执行
        if (imRequest.isAsync()) {
            //异步执行
            THREAD_FACTORY.newThread(() -> {
                ImHttpClientCallback callback = imRequest.getCallback();
                try {
                    //具体执行
                    ImResponse imResponse = execute(imRequest, imSession);
                    //如果已经设置回调接口，执行回调
                    if (callback != null) {
                        callback.onSuccess(imRequest, imResponse);
                        callback.onComplete(imRequest, imResponse);
                    }
                } catch (ContentTypeException | DataTypeException | MethodException | IgnoreSSLException | QueryParamsException | IOException e) {
                    if (callback != null) {
                        callback.onError(imRequest, e);
                        callback.onComplete(imRequest, null);
                    }
                }
            }).start();
            return null;
        } else {    //如果不是异步执行（同步执行）
            return execute(imRequest, imSession);
        }
    }

    /**
     * 发起请求
     * <p>
     * <font color="#666666">Send Request</font>
     * <p>
     * 请注意，如果在ImRequest中指示为异步请求，那么此时返回的ImResponse将会是空值，需要在ImRequest中另行实现ImHttpCallback接口才能获得返回值
     * <p>
     * <font color="#666666">Note that if the asynchronous request is indicated in the ImRequest, then the ImResponse returned at this time will be null,
     * and the ImHttpCallback interface needs to be implemented separately in the ImRequest to get the return value.</font>
     *
     * @param imRequest ImRequest会话请求数据 <br/> <font color="#666666">ImRequest Request data</font>
     * @return ImResponse会话响应对象 <br/> <font color="#666666">ImResponse Response object</font>
     * @throws ContentTypeException 内容类型（ContentType）使用相关异常/警告 <br/> <font color="#666666">Content Type (ContentType) uses related exceptions/warnings</font>
     * @throws DataTypeException    数据类型使用相关异常/警告 <br/> <font color="#666666">Data type usage related exceptions/warnings</font>
     * @throws MethodException      方法（Method）使用相关异常/警告 <br/> <font color="#666666">Method uses related exceptions/warnings</font>
     * @throws IgnoreSSLException   忽略SSL相关异常/警告 <br/> <font color="#666666">Ignore SSL related exceptions/warnings</font>
     * @throws QueryParamsException 查询参数（QueryParams）相关异常/警告类 <br/> <font color="#666666">Query parameters (QueryParams) related exception/warning</font>
     * @throws IOException          IO异常 <br/> <font color="#666666">IO exception</font>
     * @see ImRequest
     */
    public static ImResponse send(ImRequest imRequest)
            throws ContentTypeException, DataTypeException, MethodException, IgnoreSSLException, QueryParamsException, IOException {

        //非空判断
        if (imRequest == null) {
            throw new NullPointerException();
        }

        //如果是异步执行
        if (imRequest.isAsync()) {
            //异步执行
            THREAD_FACTORY.newThread(() -> {
                ImHttpClientCallback callback = imRequest.getCallback();
                try {
                    //具体执行
                    ImResponse imResponse = execute(imRequest);
                    //如果已经设置回调接口，执行回调
                    if (callback != null) {
                        callback.onSuccess(imRequest, imResponse);
                        callback.onComplete(imRequest, imResponse);
                    }
                } catch (ContentTypeException | DataTypeException | MethodException | IgnoreSSLException | QueryParamsException | IOException e) {
                    if (callback != null) {
                        callback.onError(imRequest, e);
                        callback.onComplete(imRequest, null);
                    }
                }
            }).start();
            return null;
        } else {    //如果不是异步执行（同步执行）
            return execute(imRequest);
        }
    }

    /**
     * 【内部方法】 发起会话请求
     *
     * @param imRequest ImRequest会话请求数据 <br/> <font color="#666666">ImRequest Request data</font>
     * @return ImResponse会话响应对象 <br/> <font color="#666666">ImResponse Response object</font>
     * @throws ContentTypeException 内容类型（ContentType）使用相关异常/警告 <br/> <font color="#666666">Content Type (ContentType) uses related exceptions/warnings</font>
     * @throws DataTypeException    数据类型使用相关异常/警告 <br/> <font color="#666666">Data type usage related exceptions/warnings</font>
     * @throws MethodException      方法（Method）使用相关异常/警告 <br/> <font color="#666666">Method uses related exceptions/warnings</font>
     * @throws IgnoreSSLException   忽略SSL相关异常/警告 <br/> <font color="#666666">Ignore SSL related exceptions/warnings</font>
     * @throws QueryParamsException 查询参数（QueryParams）相关异常/警告类 <br/> <font color="#666666">Query parameters (QueryParams) related exception/warning</font>
     * @throws IOException          IO异常 <br/> <font color="#666666">IO exception</font>
     */
    private static ImResponse execute(ImRequest imRequest)
            throws ContentTypeException, DataTypeException, MethodException, IgnoreSSLException, QueryParamsException, IOException {

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

        //获得回调接口
        ImHttpClientCallback callback = imRequest.getCallback();
        //如果已经定义回调接口
        if (callback != null) {
            //执行回调接口
            callback.onCallRequest(imRequest);
        }

        //设置Method
        httpConnection.setRequestMethod(imRequest.getMethod().toString());
        //设置是否使用缓存
        httpConnection.setUseCaches(imRequest.isUseCache());
        //设置User-Agent
        httpConnection.setRequestProperty("User-Agent", imRequest.getUserAgent());
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

        //如果需要Tomcat低版本兼容，则需要添加必须的Cookie
        if (imRequest.isTomcatLowVersionCompatible()) {
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
        //获得Header键数据
        Set<String> headerNames = headers.keySet();
        //遍历Header
        for (Iterator<String> iterator = headerNames.iterator(); iterator.hasNext(); ) {
            //获得Header键值
            String headerName = iterator.next();
            //处理Cookie数据
            if ("Set-Cookie".equals(headerName)) {
                //获得Cookie数据
                List<String> headerData = headers.get(headerName);
                //初始化StringBuilder
                StringBuilder builder = new StringBuilder();
                //遍历Cookie数据
                for (String data : headerData) {
                    //拼接Cookie属性字符串
                    builder.append(data).append(",");
                    //分割数据
                    String[] strs_arry = data.split("; ");
                    //初始化Cookie属性对象
                    ImResponseCookie imResponseCookie = new ImResponseCookie();
                    for (String str : strs_arry) {
                        //构建数组，以存放Cookie分割信息
                        String[] str_arry = new String[2];
                        //获得需要分割的索引
                        int index = str.indexOf("=");
                        //如果没有=号，说此为标记符，此时只需要获得键值
                        if (index < 0) {
                            index = str.length();
                        }
                        //设置Cookie分割后的信息
                        str_arry[0] = str.substring(0, index);
                        //判断是否有=号
                        if (str.indexOf("=") > 0) {
                            str_arry[1] = str.substring(index + 1);
                        } else {
                            //如果没有=号，说此为标记符
                            str_arry[1] = "null";
                        }
                        //开始处理Cookie属性
                        if ("domain".equalsIgnoreCase(str_arry[0])) {   //处理Cookie的domain属性
                            imResponseCookie.setDomain(str_arry[1]);
                        } else if ("path".equalsIgnoreCase(str_arry[0])) {  //处理Cookie的path属性
                            imResponseCookie.setPath(str_arry[1]);
                        } else if ("expires".equalsIgnoreCase(str_arry[0])) {   //处理Cookie的expires属性
                            imResponseCookie.setExpires(str_arry[1]);
                        } else if ("max-age".equalsIgnoreCase(str_arry[0])) {   //处理Cookie的max-age属性
                            imResponseCookie.setMaxAge(str_arry[1]);
                        } else if ("secure".equalsIgnoreCase(str_arry[0])) {    //处理Cookie的secure属性
                            imResponseCookie.setSecure(true);
                        } else if ("httponly".equalsIgnoreCase(str_arry[0])) {  //处理Cookie的httpOnly属性
                            imResponseCookie.setHttpOnly(true);
                        } else {
                            //处理Cookie的Kay-Value属性
                            if (imResponseCookie.getName() == null) {
                                imResponseCookie.setName(str_arry[0]);
                                imResponseCookie.setValue(str_arry.length > 2 ? "null" : str_arry[1]);
                                imResponse.addCookie(str_arry[0], str_arry.length > 2 ? "null" : str_arry[1]);
                            }
                        }
                    }
                    //添加到Cookie属性数据
                    imResponse.addCookieProperty(imResponseCookie);
                }
                //设置Cookie完整字符串数据
                cookieStr = builder.substring(0, builder.length() - 1).toString();
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
     * 【内部方法】 发起一个带会话状态的请求
     *
     * @param imRequest ImRequest会话请求数据 <br/> <font color="#666666">ImRequest Request data</font>
     * @return ImResponse会话响应对象 <br/> <font color="#666666">ImResponse Response object</font>
     * @throws ContentTypeException 内容类型（ContentType）使用相关异常/警告 <br/> <font color="#666666">Content Type (ContentType) uses related exceptions/warnings</font>
     * @throws DataTypeException    数据类型使用相关异常/警告 <br/> <font color="#666666">Data type usage related exceptions/warnings</font>
     * @throws MethodException      方法（Method）使用相关异常/警告 <br/> <font color="#666666">Method uses related exceptions/warnings</font>
     * @throws IgnoreSSLException   忽略SSL相关异常/警告 <br/> <font color="#666666">Ignore SSL related exceptions/warnings</font>
     * @throws QueryParamsException 查询参数（QueryParams）相关异常/警告类 <br/> <font color="#666666">Query parameters (QueryParams) related exception/warning</font>
     * @throws IOException          IO异常 <br/> <font color="#666666">IO exception</font>
     */
    private static ImResponse execute(ImRequest imRequest, ImSession imSession)
            throws ContentTypeException, DataTypeException, MethodException, IgnoreSSLException, QueryParamsException, IOException {

        //处理Header数据和状态
        if (imSession.getHeaders().size() > 0) {
            imRequest.setHeader(imSession.getHeaders());
        }
        //处理Cookie数据和状态
        if (imSession.getCookies().size() > 0) {
            imRequest.setCookie(imSession.getCookies());
        }

        //获得回调接口
        ImSessionCallback callback = imSession.getCallback();

        //如果已经设置回调接口，执行回调
        if (callback != null) {
            //获得是否正在执行回调中
            if (!imSession.ExecCallbackNow) {
                //是否需要身份认证
                if (imSession.isNeedAuthentication()) {
                    //是否需要自动取得身份认证
                    if (imSession.isAutoAuthentication()) {
                        //获得最大尝试次数
                        int count = imSession.getAutoAuthenticationTryCount();
                        //设置为正在执行回调中
                        imSession.ExecCallbackNow = true;
                        //获得是否取得身份认证
                        boolean isAuthentication = callback.isAuthentication(imSession, imRequest);
                        //循环取得身份认证
                        while (!isAuthentication && count > 0) {
                            //设置为已结束执行回调
                            imSession.ExecCallbackNow = false;
                            //设置为正在执行回调中
                            imSession.ExecCallbackNow = true;
                            //开始执行取得身份认证
                            callback.doAuthentication(imSession, imRequest);
                            //设置为已结束执行回调
                            imSession.ExecCallbackNow = false;
                            //尝试次数减少
                            count--;
                            imSession.ExecCallbackNow = true;
                            //获得是否取得身份认证
                            isAuthentication = callback.isAuthentication(imSession, imRequest);
                            //如果已经取得身份认证，则设置为已结束执行回调
                            if (isAuthentication) {
                                imSession.ExecCallbackNow = false;
                            }
                        }
                        //是否取得身份认证，此时如果还未取得身份认证，则抛出异常
                        if (!isAuthentication) {
                            throw new AuthenticationException(AuthenticationException.NotHaveAuthentication);
                        }
                    } else {
                        throw new AuthenticationException(AuthenticationException.NotHaveAuthentication);
                    }
                }
            }
        }

        //重新处理Header数据和状态
        if (imSession.getHeaders().size() > 0) {
            imRequest.setHeader(imSession.getHeaders());
        }
        //重新处理Cookie数据和状态
        if (imSession.getCookies().size() > 0) {
            imRequest.setCookie(imSession.getCookies());
        }

        //获得ImResponse
        ImResponse imResponse = execute(imRequest);

        //处理Cookie数据并管理
        if (imResponse.getCookieSize() > 0) {
            Set<String> cookieNames = imResponse.getCookieNames();
            for (String cookieName : cookieNames) {
                imSession.setCookie(cookieName, imResponse.getCookieData(cookieName));
            }
        }

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
    private static String getInputDataToString(ImRequest imRequest, boolean forceKeyValueModel) throws
            UnsupportedEncodingException {

        //当InputData不为空时
        if (imRequest.getInputData() != null) {

            //初始化返回对象
            String inputData = "";

            //如果是String类型
            if (imRequest.getInputData() instanceof String) {
                if (imRequest.isUrlEncode()) {
                    inputData = URLEncoder.encode(imRequest.getInputData(String.class), imRequest.getCharset());
                } else {
                    inputData = imRequest.getInputData(String.class);
                }
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
                if (imRequest.isUrlEncode()) {
                    queryParams = URLEncoder.encode(imRequest.getQueryParams(String.class), imRequest.getCharset());
                } else {
                    queryParams = imRequest.getQueryParams(String.class);
                }
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
    private static ByteArrayOutputStream toByteArrayOutputStream(InputStream input, int bytesLength) throws
            IOException {
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
