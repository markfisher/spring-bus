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

package config;

import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

/**
 * @author Mark Fisher
 */
@Configuration
public class ModuleDefinition {

	private final Random r = new Random();

	@Bean
	public MessageChannel output() {
		return new DirectChannel();
	}

	@Bean
	@InboundChannelAdapter(value = "output", autoStartup = "false", poller = @Poller(fixedDelay = "${fixedDelay}", maxMessagesPerPoll = "1"))
	public MessageSource<String> timerMessageSource() {
		return () -> new GenericMessage<>(String.format("{\"id\":\"1\",\"measurement\":\"%.2f\"}", r.nextInt(10000)/100f));
	}

}
