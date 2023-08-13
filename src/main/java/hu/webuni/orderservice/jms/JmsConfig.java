package hu.webuni.orderservice.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.ConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

    @Bean
    public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public ConnectionFactory shippingConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61617");
        return connectionFactory;
    }

    @Bean
    public JmsTemplate shippingTemplate(ObjectMapper objectMapper) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(shippingConnectionFactory());
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter(objectMapper));
        return jmsTemplate;
    }

    @Bean
    public JmsListenerContainerFactory<?> shippingFactory(ConnectionFactory shippingConnectionFactory,
                                                         DefaultJmsListenerContainerFactoryConfigurer configurer) {

        return setPubSubAndDurableSubscription(shippingConnectionFactory, configurer, "webshop-app");
    }

    private JmsListenerContainerFactory<?> setPubSubAndDurableSubscription(
            ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer,
            String clientId) {

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setClientId(clientId);
        configurer.configure(factory, connectionFactory);
        factory.setPubSubDomain(true);
        factory.setSubscriptionDurable(true);

        return factory;
    }
}
