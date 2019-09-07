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
 * <b>文件数据封装类</b>
 * <p>
 * 当使用上传文件时，需要将文件的相关数据在该类封装
 * <br /><br />
 * <b>File data Bean class</b>
 * <p>
 * When using an upload file, you need to encapsulate the relevant data of the file in this class.
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description 文件数据Bean
 * @create 2019-09-01 01:57
 */
public class ImFileData extends ImBytesData {

    private String name;
    private String fileName;
    private String fileType;

    public ImFileData() {
    }

    /**
     * 封装一个文件数据（文件上传）
     * <p>
     * Build a file data (file upload)
     *
     * @param name     Parameter/行数据名称 <br /> Parameter / row data name
     * @param fileName 文件名称 <br /> File name
     * @param fileType 文件类型（ContentType） <br /> File type (ContentType)
     * @param bytes    文件二进制数据 <br /> File binary data
     */
    public ImFileData(String name, String fileName, String fileType, byte[] bytes) {
        super(bytes);
        this.name = name;
        this.fileType = fileType;
        this.fileName = fileName;
    }

    /**
     * 获取Parameter/行数据名称
     * <p>
     * Get the Parameter/row data name
     *
     * @return Parameter/行数据名称 <br /> Parameter / row data name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置Parameter/行数据名称
     * <p>
     * Set the Parameter/row data name
     *
     * @param name Parameter/行数据名称 <br /> Parameter / row data name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取文件名称
     * <p>
     * Get the File name
     *
     * @return 文件名称 <br /> File name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置文件名称
     * <p>
     * Set the File name
     *
     * @param fileName 文件名称 <br /> File name
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取文件类型（ContentType）
     * <p>
     * Get File type (ContentType)
     *
     * @return 文件类型（ContentType） <br /> File type (ContentType)
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 设置文件类型（ContentType）
     * <p>
     * Set File type (ContentType)
     *
     * @param fileType 文件类型（ContentType） <br /> File type (ContentType)
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
