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
package cn.sinlmao.commons.network.http;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP 方法枚举类
 *
 * @program Sinlmao Commons Network Utils
 * @description HTTP 方法枚举类
 * @author Sinlmao
 * @create 2019-08-01 11:11
 */
public enum ImMethod {

	GET, POST, PUT, DELETE;

	// Implementing a fromString method on an enum type
	private static final Map<String, ImMethod> stringToEnum = new HashMap<String, ImMethod>();
	static {
		// Initialize map from constant name to enum constant
		for (ImMethod blah : values()) {
			stringToEnum.put(blah.toString(), blah);
		}
	}

	// Returns Blah for string, or null if string is invalid
	public static ImMethod fromString(String symbol) {
		return stringToEnum.get(symbol);
	}
}
