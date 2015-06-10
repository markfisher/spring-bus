/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.bus.runner.adapter;

/**
 * @author Dave Syer
 * @author Mark Fisher
 */
public class InputBinding {

	private String pipeName;
	private String channelName;

	protected InputBinding() {
		this(null);
	}

	public InputBinding(String channelName) {
		this.channelName = channelName;
	}

	public String getPipeName() {
		return this.pipeName;
	}

	public void setPipeName(String pipeName) {
		this.pipeName = pipeName;
	}

	public String getChannelName() {
		return this.channelName;
	}

}
