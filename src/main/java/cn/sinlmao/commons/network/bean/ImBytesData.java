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
package cn.sinlmao.commons.network.bean;

/**
 * <b>字节数据封装类</b>
 * <p>
 * 该类用于封装二进制（Byte）的数据
 * <br /><br />
 * <b>Byte data Bean class</b>
 * <p>
 * This class is used to encapsulate binary (Byte) data.
 *
 * @program: Sinlmao Commons Network Utils
 * @description: 字节数据Bean
 * @author: Sinlmao
 * @create: 2019-09-01 01:57
 */
public class ImBytesData {

    private byte[] bytes;

    public ImBytesData() {
    }

    /**
     * 封装一个字节数据
     * <p>
     * Build a bytes data
     *
     * @param bytes 字节数据 <br /> Bytes data
     */
    public ImBytesData(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * 获得字节数据
     * <p>
     * Get byte data
     *
     * @return 字节数据 <br /> Byte data
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * 设置字节数据
     * <p>
     * Set byte data
     *
     * @param bytes 字节数据 <br /> Byte data
     */
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
