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

import static org.springframework.xd.tuple.TupleBuilder.tuple;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.xd.rxjava.Processor;
import org.springframework.xd.rxjava.SubjectMessageHandler;
import org.springframework.xd.tuple.Tuple;
import org.springframework.xd.tuple.TupleBuilder;

import rx.Observable;

/**
 * @author Mark Fisher
 */
@Configuration
@MessageEndpoint
public class ModuleDefinition {

	private static Logger logger = LoggerFactory.getLogger(ModuleDefinition.class);

	@Autowired
	private MessageHandler messageHandler;

	@Bean
	public MessageChannel input() {
		return new DirectChannel();
	}

	@Bean
	public MessageChannel results() {
		return new PublishSubscribeChannel();
	}

	@ServiceActivator(inputChannel="input")
	public void process(Message<?> message) {
		Tuple tuple = TupleBuilder.fromString(message.getPayload().toString());
		logger.info(String.format("{\"measurement\": %.2f}", tuple.getDouble("measurement")));
		messageHandler.handleMessage(MessageBuilder.withPayload(tuple).build());
	}

	@ServiceActivator(inputChannel="results")
	public void log(Message<?> message) {
		logger.info(message.getPayload().toString());
	}

	@Bean
	public MessageHandler messageHandler() {
		SubjectMessageHandler handler = new SubjectMessageHandler(new MovingAverageProcessor());
		handler.setOutputChannel(results());
		return handler;
	}

	private static class MovingAverageProcessor implements Processor<Tuple, Tuple> {

		@Override
		public Observable<Tuple> process(Observable<Tuple> inputStream) {
			return inputStream.map(tuple -> { return tuple.getDouble("measurement"); })
							  .buffer(10)
							  .map(data -> tuple().of("time", System.currentTimeMillis(), "avg", avg(data)));
		}
	}
	
    public static Double avg(List<Double> data) {
        double sum = 0;
        double count = 0;
        for(Double d : data) {
            count++;
            sum += d;
        }
        return sum/count;
    }
}
