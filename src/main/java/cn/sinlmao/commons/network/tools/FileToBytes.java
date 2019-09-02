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

import java.io.*;

/**
 * @program: Sinlmao Commons Network Utils
 * @description: 文件转换为Bytes数据
 * @author: Sinlmao
 * @create: 2019-09-02 11:48
 */
public class FileToBytes {

    /**
     * 将文件（File）转换成byte数组
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] File2ByteArray(String filePath) throws IOException {

        InputStream in = new FileInputStream(filePath);
        byte[] data = InputStream2ByteArray(in);
        in.close();

        return data;
    }

    /**
     * 将文件（File）转换成byte数组
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] File2ByteArray(File file) throws IOException {

        InputStream in = new FileInputStream(file);
        byte[] data = InputStream2ByteArray(in);
        in.close();

        return data;
    }

    /**
     * 将InputStream（输入流）转换成byte数组
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[] InputStream2ByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

}
