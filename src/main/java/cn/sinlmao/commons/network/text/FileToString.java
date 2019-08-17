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
package cn.sinlmao.commons.network.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 从文件读取String工具类
 *
 * @program: Sinlmao Commons Network Utils
 * @description: 从文件读取String工具类
 * @author: Sinlmao
 * @create: 2019-08-01 11:11
 */
public class FileToString {

    /**
     * InputStreamReader+BufferedReader读取字符串 ， InputStreamReader类是从字节流到字符流的桥梁
     * 按行读对于要处理的格式化数据是一种读取的好方式
     *
     * @param FILE_IN
     * @return
     */
    public static String readString(String FILE_IN) {

        int len = 0;

        StringBuffer str = new StringBuffer("");

        File file = new File(FILE_IN);

        try {

            FileInputStream is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader in = new BufferedReader(isr);

            String line = null;

            while ((line = in.readLine()) != null) {
                if (len != 0) // 处理换行符的问题
                {
                    str.append("\r\n" + line);
                } else {
                    str.append(line);
                }
                len++;
            }

            in.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

}
