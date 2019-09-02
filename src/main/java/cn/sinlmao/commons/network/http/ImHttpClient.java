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
import cn.sinlmao.commons.network.exception.ContentTypeException;
import cn.sinlmao.commons.network.exception.DataTypeException;
import cn.sinlmao.commons.network.exception.MethodException;
import cn.sinlmao.commons.network.tools.IgnoreSSLTool;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

/**
 * HTTP Client工具类
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description HTTP Client工具类
 * @create 2019-08-01 11:11
 */
public class ImHttpClient {

    private final static String PREFIX = "--";
    private final static String WRAP = System.getProperty("line.separator");

    /**
     * 发送请求
     *
     * @param imRequest
     * @return ImResponse
     * @throws Exception
     */
    public static ImResponse send(ImRequest imRequest) throws Exception {

        //初始化分隔符（如果为文件上传(multipart/form-data)模式的时候）
        String boundary = "--------------------------" + String.valueOf(System.currentTimeMillis()); // boundary就是request头和上传文件内容的分隔符

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
        }

        //获得URL字符
        String urlStr = imRequest.getUrl();

        //确认在URL中传值
        if (imRequest.isForceInUrlSendData()) {
            if (imRequest.getInputData() != null) {
                //初始化URL值字符串
                String urlPars = "";
                //如果是String类型
                if (imRequest.getInputData() instanceof String) {
                    urlPars = imRequest.getInputData(String.class);
                }
                //如果是JSON类型
                //if (imRequest.getInputData() instanceof JSONObject) {
                if (imRequest.getInputData().getClass() == JSONObject.class) {
                    JSONObject json = imRequest.getInputData(JSONObject.class);
                    for (String key : json.keySet()) {
                        urlPars += (key + "=" + json.getString(key) + "&");
                    }
                    urlPars = urlPars.substring(0, urlPars.length() - 1);
                }
                //如果是Map类型
                //if (imRequest.getInputData() instanceof Map) {
                if (imRequest.getInputData().getClass() != JSONObject.class && imRequest.getInputData() instanceof Map) {
                    Map<String, String> map = imRequest.getInputData(Map.class);
                    for (String key : map.keySet()) {
                        urlPars += (key + "=" + map.get(key) + "&");
                    }
                    urlPars = urlPars.substring(0, urlPars.length() - 1);
                }
                //如果是ImFormData类型
                if (imRequest.getInputData().getClass() == ImFormData.class) {
                    ImFormData imFormData = imRequest.getInputData(ImFormData.class);
                    urlPars = (imFormData.getName() + "=" + imFormData.getValue());
                }

                //组装URL和URL需要传的值数据
                if (urlStr.contains("?")) {
                    urlStr += ("&" + urlPars);
                } else {
                    urlStr += ("?" + urlPars);
                }
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
        httpConnection.setRequestProperty("Content-Type", getContentType(imRequest.getContentType()) + "; charset=" + imRequest.getCharset());

        //如果需要长连接
        if (imRequest.isKeepAlive() || imRequest.getContentType() == ImContentType.MULTIPART_FORM_DATA) {
            //开启长连接可以持续传输
            httpConnection.setRequestProperty("Connection", "keep-alive");
        }

        //如果ContentType是multipart/form-data，则需要分段标记，并且需要设置块大小
        if (imRequest.getContentType() == ImContentType.MULTIPART_FORM_DATA) {
            httpConnection.setRequestProperty("Content-Type", getContentType(imRequest.getContentType())
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

        //如果存在Cookie定义，则设置Cookie值
        if (imRequest.getCookieSize() > 0) {
            Set<String> cookieNames = imRequest.getHeaderNames();
            StringBuilder cookieStrs = new StringBuilder();
            for (String cookieName : cookieNames) {
                cookieStrs.append(cookieName + "=" + imRequest.getCookieData(cookieName));
                cookieStrs.append(";");
            }
            String cookieStr = cookieStrs.substring(0, cookieStrs.length() - 1);
            httpConnection.setRequestProperty("Cookie", cookieStr);
        }

        //如果存在InputData值，则设置InputData值
        if (imRequest.getInputData() != null) {

            //如果不是文件上传内容类型
            if (imRequest.getContentType() != ImContentType.MULTIPART_FORM_DATA) {

                //初始化inputData
                String inputData = "";

                //如果内容类型不为表单（x-www-form-urlencoded）
                if (imRequest.getContentType() != ImContentType.APPLICATION_X_WWW_FORM_URLENCODED) {

                    //如果是String类型
                    if (imRequest.getInputData() instanceof String) {
                        inputData = imRequest.getInputData(String.class);
                    }
                    //如果是JSON类型
                    //if (imRequest.getInputData() instanceof JSONObject) {
                    if (imRequest.getInputData().getClass() == JSONObject.class) {
                        if (imRequest.getContentType() == ImContentType.APPLICATION_JSON) {
                            inputData = imRequest.getInputData(JSONObject.class).toJSONString();
                        } else {
                            JSONObject json = imRequest.getInputData(JSONObject.class);
                            for (String key : json.keySet()) {
                                inputData += (key + "=" + json.getString(key) + "&");
                            }
                            inputData = inputData.substring(0, inputData.length() - 1);
                        }
                    }
                    //如果是Map类型
                    //if (imRequest.getInputData() instanceof Map) {
                    if (imRequest.getInputData().getClass() != JSONObject.class && imRequest.getInputData() instanceof Map) {
                        if (imRequest.getContentType() == ImContentType.APPLICATION_JSON) {
                            inputData = JSON.toJSONString(imRequest.getInputData(Map.class));
                        } else {
                            Map<String, String> map = imRequest.getInputData(Map.class);
                            for (String key : map.keySet()) {
                                inputData += (key + "=" + map.get(key) + "&");
                            }
                            inputData = inputData.substring(0, inputData.length() - 1);
                        }
                    }
                    //如果是ImFormData类型
                    if (imRequest.getInputData().getClass() == ImFormData.class) {
                        ImFormData imFormData = imRequest.getInputData(ImFormData.class);
                        if (imRequest.getContentType() == ImContentType.APPLICATION_JSON) {
                            JSONObject json = new JSONObject();
                            json.put(imFormData.getName(), imFormData.getValue());
                            inputData = json.toJSONString();
                        } else {
                            inputData = (imFormData.getName() + "=" + imFormData.getValue());
                        }
                    }

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

                    //如果是String类型
                    if (imRequest.getInputData() instanceof String) {
                        inputData = imRequest.getInputData(String.class);
                    }
                    //如果是JSON类型
                    //if (imRequest.getInputData() instanceof JSONObject) {
                    if (imRequest.getInputData().getClass() == JSONObject.class) {
                        JSONObject json = imRequest.getInputData(JSONObject.class);
                        for (String key : json.keySet()) {
                            inputData += (key + "=" + json.getString(key) + "&");
                        }
                        inputData = inputData.substring(0, inputData.length() - 1);
                    }
                    //如果是Map类型
                    //if (imRequest.getInputData() instanceof Map) {
                    if (imRequest.getInputData().getClass() != JSONObject.class && imRequest.getInputData() instanceof Map) {
                        Map<String, String> map = imRequest.getInputData(Map.class);
                        for (String key : map.keySet()) {
                            inputData += (key + "=" + map.get(key) + "&");
                        }
                        inputData = inputData.substring(0, inputData.length() - 1);
                    }
                    //如果是ImFormData类型
                    if (imRequest.getInputData().getClass() == ImFormData.class) {
                        ImFormData imFormData = imRequest.getInputData(ImFormData.class);
                        inputData = (imFormData.getName() + "=" + imFormData.getValue());
                    }

                    httpConnection.setDoOutput(true);

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

        // if (httpUtilResponse.getResponseCode() != 200) {
        // throw new RuntimeException(
        // "HTTP GET Request Failed with Error code : " +
        // httpConnection.getResponseCode());
        // }

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
        imResponse.setHeaders(headers);
        imResponse.setCookie(cookieStr);

        return imResponse;
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
    private static String getContentType(ImContentType contentType) {
        switch (contentType) {
            case APPLICATION_JSON:
                return "application/json";
            case APPLICATION_X_WWW_FORM_URLENCODED:
                return "application/x-www-form-urlencoded";
            case MULTIPART_FORM_DATA:
                return "multipart/form-data";
        }
        return "";
    }
}
