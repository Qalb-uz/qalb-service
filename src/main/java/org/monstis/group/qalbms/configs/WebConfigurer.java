package org.monstis.group.qalbms.configs;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import javax.net.ssl.SSLException;

@Configuration
@RequiredArgsConstructor
public class WebConfigurer implements WebFluxConfigurer {

    @Value("${eskiz.api}")
    private String eskizUrl;

    @Bean
    public WebClient eskizwebClient() throws SSLException {
        SslContext sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        TcpClient tcpClient = TcpClient.create()
                .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 60000)
                .doOnConnected((connection) -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(120)).addHandlerLast(new WriteTimeoutHandler(120));
                });
        HttpClient httpClient = HttpClient.from(tcpClient);
        return WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(eskizUrl)
                .build();
    }



}
