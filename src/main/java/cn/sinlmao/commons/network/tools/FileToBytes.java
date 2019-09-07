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
 * <b>文件转二进制的工具类</b>
 * <p>
 * 将文件转换成一个二进制数据（byte）
 * <br /><br />
 * <b>File to binary tool class</b>
 * <p>
 * Convert file to a binary data (byte)
 *
 * @program: Sinlmao Commons Network Utils
 * @description: 文件转换为Bytes数据
 * @author: Sinlmao
 * @create: 2019-09-02 11:48
 */
public class FileToBytes {

    /**
     * 将文件（File）转换成二进制数据（byte）
     * <p>
     * Convert a file to binary data
     *
     * @param filePath 文件路径 <br /> file path
     * @return 文件的二进制数据（byte） <br /> Binary data of the file (byte)
     * @throws IOException IO异常 <br /> IO exception
     */
    public static byte[] File2ByteArray(String filePath) throws IOException {

        InputStream in = new FileInputStream(filePath);
        byte[] data = InputStream2ByteArray(in);
        in.close();

        return data;
    }

    /**
     * 将文件（File）转换成二进制数据（byte）
     * <p>
     * Convert a file to binary data
     *
     * @param file 文件实体对象 <br /> File object
     * @return 文件的二进制数据（byte） <br /> Binary data of the file (byte)
     * @throws IOException IO异常 <br /> IO exception
     */
    public static byte[] File2ByteArray(File file) throws IOException {

        InputStream in = new FileInputStream(file);
        byte[] data = InputStream2ByteArray(in);
        in.close();

        return data;
    }

    /**
     * 将InputStream（输入流）转换成byte数组
     * <p>
     * Convert an InputStream to a byte array
     *
     * @param in 输入流 <br /> InputStream
     * @return 文件的二进制数据（byte） <br /> Binary data of the file (byte)
     * @throws IOException IO异常 <br /> IO exception
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
