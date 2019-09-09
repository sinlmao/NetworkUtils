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

import java.util.ArrayList;
import java.util.List;

/**
 * <b>多部分构成的表单数据封装类</b>
 * <p>
 * 该类用于封装多部分构成的表单的数据，一般用于文件上传或二进制流数据的发送
 * <br /><br />
 * <b>Multi-part form data Bean class</b>
 * <p>
 * This class is used to encapsulate the data of a multi-part form, generally used for file upload or binary stream data transmission.
 *
 * @author Sinlmao
 * @program Sinlmao Commons Network Utils
 * @description 多部分构成的表单数据Bean
 * @create 2019-09-01 19:24
 */
public class ImMultipartFormData {

    private int index = -1;

    private List<Object> datas = new ArrayList<>();

    /**
     * 是否存在下一个数据
     * <p>
     * <font color="#666666">Whether there is next data</font>
     *
     * @return 是否存在下一个数据 <br/> <font color="#666666">Whether there is next data</font>
     */
    public boolean hasNext() {
        return datas.size() > 0 && index < datas.size() - 1;
    }

    /**
     * 获得下一个数据
     * <p>
     * <font color="#666666">Get the next data</font>
     *
     * @return 下一个数据 <br/> <font color="#666666">Next data</font>
     */
    public Object nextData() {
        if (datas.size() > 0 && index < datas.size() - 1) {
            index++;
            return datas.get(index);
        } else {
            throw new NullPointerException("The data list is empty or to end.");
        }
    }

    /**
     * 添加ImFormData类型数据
     * <p>
     * <font color="#666666">Add ImFormData type data</font>
     *
     * @param data ImFormData类型数据 <br/> <font color="#666666">ImFormData type data</font>
     */
    public void addData(ImFormData data) {
        datas.add(data);
    }

    /**
     * 添加ImFileData类型数据
     * <p>
     * <font color="#666666">Add ImFileData type data</font>
     *
     * @param data ImFileData类型数据 <br/> <font color="#666666">ImFileData type data</font>
     */
    public void addData(ImFileData data) {
        datas.add(data);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 获得所有数据
     * <p>
     * <font color="#666666">Get all the data</font>
     *
     * @return 所有数据<br /> <font color="#666666">All data</font>
     */
    public List<Object> getDatas() {
        return datas;
    }

    /**
     * 设置所有数据
     * <p>
     * <font color="#666666">Set all the data</font>
     *
     * @param datas 所有数据<br/> <font color="#666666">All data</font>
     */
    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }
}
