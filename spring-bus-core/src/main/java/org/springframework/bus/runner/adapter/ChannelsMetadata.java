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

import java.util.Collection;
import java.util.Collections;

import org.springframework.bus.runner.config.ModuleProperties;

/**
 * @author Dave Syer
 */
public class ChannelsMetadata {

	private Collection<OutputBinding> outputBindings = Collections.emptySet();
	private Collection<InputBinding> inputBindings = Collections.emptySet();
	private ModuleProperties module;

	public ModuleProperties getModule() {
		return module;
	}

	public void setModule(ModuleProperties module) {
		this.module = module;
	}

	public Collection<OutputBinding> getOutputBindings() {
		return outputBindings;
	}

	public void setOutputBindings(Collection<OutputBinding> outputBindings) {
		this.outputBindings = outputBindings;
	}

	public Collection<InputBinding> getInputBindings() {
		return inputBindings;
	}

	public void setInputBindings(Collection<InputBinding> inputBindings) {
		this.inputBindings = inputBindings;
	}

}
