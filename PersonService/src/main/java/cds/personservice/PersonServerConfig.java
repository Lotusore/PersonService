package cds.personservice;


import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.DiscoveryClient.DiscoveryClientOptionalArgs;
import javax.net.ssl.SSLContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;


@Configuration
public class PersonServerConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServerConfig.class);
    @Value("${server.ssl.key-store}")
    private String trustStore;
    @Value("${server.ssl.key-store-password}")
    private String trustStorePassword;

    public PersonServerConfig() {
    }

    @Bean
    public DiscoveryClient.DiscoveryClientOptionalArgs getTrustStoredEurekaClient(SSLContext sslContext) {
        DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
        args.setSSLContext(sslContext);
        return args;
    }

    @Bean
    public SSLContext sslContext() throws Exception {
        return (new SSLContextBuilder()).loadTrustMaterial(ResourceUtils.getFile(this.trustStore), this.trustStorePassword.toCharArray()).build();
    }
}

