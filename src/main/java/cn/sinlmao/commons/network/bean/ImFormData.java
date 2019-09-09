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
 * <b>普通表单数据封装类</b>
 * <p>
 * 该类用于封装普通表单数据
 * <br /><br />
 * <b>Normal form data Bean class</b>
 * <p>
 * This class is used to wrap ordinary form data
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description 普通表单数据Bean
 * @create 2019-09-01 01:57
 */
public class ImFormData {

    private String name;
    private String contentType;
    private String value;

    public ImFormData() {

    }

    /**
     * 封装一个普通表单数据
     * <p>
     * <font color="#666666">Build a normal form data</font>
     *
     * @param name        数据名 <br /> <font color="#666666">Data name</font>
     * @param contentType 数据类型（内容类型） <br /> <font color="#666666">Data type (content type)</font>
     * @param value       数据值 <br /> <font color="#666666">Data value</font>
     */
    public ImFormData(String name, String contentType, String value) {
        this.name = name;
        this.contentType = contentType;
        this.value = value;
    }

    /**
     * 获取数据名
     * <p>
     * <font color="#666666">Get Data name</font>
     *
     * @return 数据名 <br /> <font color="#666666">Data name</font>
     */
    public String getName() {
        return name;
    }

    /**
     * 设置数据名
     * <p>
     * <font color="#666666">Set Data name</font>
     *
     * @param name 数据名 <br /> <font color="#666666">Data name</font>
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取数据类型（内容类型）
     * <p>
     * <font color="#666666">Get Data type (content type)</font>
     *
     * @return 数据类型（内容类型） <br /> <font color="#666666">Data type (content type)</font>
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * 设置数据类型（内容类型）
     * <p>
     * <font color="#666666">Set Data type (content type)</font>
     *
     * @param contentType 数据类型（内容类型） <br /> <font color="#666666">Data type (content type)</font>
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * 获取数据值
     * <p>
     * <font color="#666666">Get Data value</font>
     *
     * @return 数据值 <br /> <font color="#666666">Data value</font>
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置数据值
     * <p>
     * <font color="#666666">Set Data value</font>
     *
     * @param value 数据值 <br /> <font color="#666666">Data value</font>
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 已过期，请更改为getValue
     * <p>
     * <font color="#666666">Expired, please change to getValue</font>
     *
     * @return 数据值 <br /> <font color="#666666">Data value</font>
     */
    @Deprecated
    public String getData() {
        return value;
    }
}
