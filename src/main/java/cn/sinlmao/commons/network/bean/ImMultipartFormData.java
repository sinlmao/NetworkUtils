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
 * @program: Sinlmao Commons Network Utils
 * @description: 多部分构成的表单数据Bean
 * @author: Sinlmao
 * @create: 2019-09-01 19:24
 */
public class ImMultipartFormData {

    private int index = -1;

    private List<Object> datas = new ArrayList<>();

    public boolean hasNext() {
        return datas.size() > 0 && index < datas.size() - 1;
    }

    public Object nextData() {
        if (datas.size() > 0 && index < datas.size() - 1) {
            index++;
            return datas.get(index);
        } else {
            throw new NullPointerException("The data list is empty or to end.");
        }
    }

    public void addData(ImFormData data) {
        datas.add(data);
    }

    public void addData(ImFileData data) {
        datas.add(data);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<Object> getDatas() {
        return datas;
    }

    protected void setDatas(List<Object> datas) {
        this.datas = datas;
    }
}
