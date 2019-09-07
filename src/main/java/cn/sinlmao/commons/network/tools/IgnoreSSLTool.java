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

import cn.sinlmao.commons.network.exception.IgnoreSSLException;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.*;

/**
 * HttpsURLConnection SSL 证书忽略类
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description HttpsURLConnection SSL 证书忽略类类
 * @create 2019-08-21 11:11
 */
public class IgnoreSSLTool {

    private static boolean isIgnore = true;
    private static SSLSocketFactory oSSLSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
    private static HostnameVerifier oHostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    static class miTM implements TrustManager, X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @throws Exception
     */
    private static void trustAllHttpsCertificates() throws KeyManagementException, NoSuchAlgorithmException {
        if (isIgnore) {
            TrustManager[] trustAllCerts = new TrustManager[1];
            TrustManager tm = new miTM();
            trustAllCerts[0] = tm;
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } else {
            HttpsURLConnection.setDefaultSSLSocketFactory(oSSLSocketFactory);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * 执行是否忽略HTTPS请求的SSL证书，必须在openConnection之前调用
     *
     * @throws Exception
     */
    private static void ignoreSSL() throws NoSuchAlgorithmException, KeyManagementException {
        if (isIgnore) {
            HostnameVerifier hv = new HostnameVerifier() {
                public boolean verify(String urlHostName, SSLSession session) {
                    return true;
                }
            };
            trustAllHttpsCertificates();
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
        } else {
            trustAllHttpsCertificates();
            HttpsURLConnection.setDefaultHostnameVerifier(oHostnameVerifier);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 设置是否忽略HTTPS请求的SSL证书，必须在openConnection之前调用
     *
     * @throws Exception
     */
    public static void setIsIgnore(boolean isIgnore) throws IgnoreSSLException {
        IgnoreSSLTool.isIgnore = isIgnore;
        try {
            ignoreSSL();
        } catch (NoSuchAlgorithmException e) {
            throw new IgnoreSSLException(IgnoreSSLException.MethodInappropriate, e.getCause());
        } catch (KeyManagementException e) {
            throw new IgnoreSSLException(IgnoreSSLException.MethodInappropriate, e.getCause());
        }
    }
}
