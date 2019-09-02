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
 * @program: Sinlmao Commons Network Utils
 * @description: 普通表单数据Bean
 * @author: Sinlmao
 * @create: 2019-09-01 01:57
 */
public class ImFormData {

    private String name;
    private String contentType;
    private String value;

    public ImFormData() {

    }

    public ImFormData(String name, String contentType, String value) {
        this.name = name;
        this.contentType = contentType;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 已过期，请更改为getValue
     * @return
     */
    @Deprecated
    public String getData() {
        return value;
    }
}
