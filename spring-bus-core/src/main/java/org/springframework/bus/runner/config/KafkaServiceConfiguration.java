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

package org.springframework.bus.runner.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.xd.dirt.integration.kafka.KafkaMessageBus;

/**
 * Bind to Kafka locally (no cloud support yet).
 *
 * @author Mark Fisher
 */
@Configuration
@ConditionalOnClass(KafkaMessageBus.class)
@ImportResource({"classpath*:/META-INF/spring-xd/bus/kafka-bus.xml"})
public class KafkaServiceConfiguration {

}
