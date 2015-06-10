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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.bus.runner.adapter.discovery.DiscoveryClientChannelLocator;
import org.springframework.bus.runner.config.ModuleProperties;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestOperations;

/**
 * @author Dave Syer
 */
public class DiscoveryClientChannelLocatorTests {

	private DiscoveryClient client = Mockito.mock(DiscoveryClient.class);

	private RestOperations restTemplate = Mockito.mock(RestOperations.class);

	private DiscoveryClientChannelLocator locator = new DiscoveryClientChannelLocator(this.client, "service");

	private ChannelsMetadata metadata = new ChannelsMetadata();

	@Before
	public void init() {
		this.locator.setRestTemplate(this.restTemplate);
		this.metadata.setModule(new ModuleProperties());
		this.metadata.setInputBindings(new HashSet<InputBinding>());
		this.metadata.setOutputBindings(new HashSet<OutputBinding>());
		Mockito.when(
				this.restTemplate.getForObject(Mockito.any(URI.class), anyChannels()))
				.thenReturn(this.metadata);
		Mockito.when(this.client.getInstances(Mockito.anyString())).thenReturn(
				Arrays.asList(new DefaultServiceInstance("service", "example.com", 888,
						false)));
	}

	@Test
	public void locateInputFromOutput() {
		OutputBinding output = new OutputBinding("output");
		output.setPipeName("foo.0");
		this.metadata.getOutputBindings().add(output);
		assertEquals("foo.0", this.locator.locate("input"));
	}

	@Test
	public void locateOutputFromInput() {
		InputBinding input = new InputBinding("input");
		input.setPipeName("foo.0");
		this.metadata.getInputBindings().add(input);
		assertEquals("foo.0", this.locator.locate("output"));
	}

	@SuppressWarnings({ "unchecked" })
	private Class<ChannelsMetadata> anyChannels() {
		return any(Class.class);
	}
}
