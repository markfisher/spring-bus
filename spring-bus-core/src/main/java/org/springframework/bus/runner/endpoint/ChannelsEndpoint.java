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

package org.springframework.bus.runner.endpoint;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.bus.runner.adapter.ChannelsMetadata;
import org.springframework.bus.runner.adapter.MessageBusAdapter;
import org.springframework.bus.runner.adapter.OutputBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dave Syer
 */
@RestController
public class ChannelsEndpoint extends AbstractEndpoint<Map<String, ?>> {
	
	private MessageBusAdapter adapter;

	public ChannelsEndpoint(MessageBusAdapter adapter) {
		super("channels");
		this.adapter = adapter;
	}

	@RequestMapping(value="/channels/taps")
	public List<OutputBinding> taps() {
		List<OutputBinding> list = new ArrayList<OutputBinding>();
		for (OutputBinding binding : adapter.getChannelsMetadata().getOutputBindings()) {
			if (binding.isTapped()) {
				list.add(binding);
			}
		}
		return list;
	}

	@RequestMapping(value="/channels/taps", method=RequestMethod.POST)
	public OutputBinding tap(@RequestParam String channel) {
		adapter.tap(channel);
		return adapter.getOutputBinding(channel);
	}

	@RequestMapping(value="/channels/taps", method=RequestMethod.DELETE)
	public OutputBinding untap(@RequestParam String channel) {
		adapter.untap(channel);
		return adapter.getOutputBinding(channel);
	}

	@Override
	public Map<String, ?> invoke() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		ChannelsMetadata channels = adapter.getChannelsMetadata();
		map.put("inputChannels", channels.getInputBindings());
		map.put("outputChannels", channels.getOutputBindings());
		map.put("module", channels.getModule());
		return map;
	}

}