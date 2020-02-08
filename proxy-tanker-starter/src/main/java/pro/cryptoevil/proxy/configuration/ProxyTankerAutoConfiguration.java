package pro.cryptoevil.proxy.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.cryptoevil.proxy.core.IProxyHolder;
import pro.cryptoevil.proxy.core.IProxyValidator;
import pro.cryptoevil.proxy.core.ProxyRepository;
import pro.cryptoevil.proxy.impl.ProxyHolder;
import pro.cryptoevil.proxy.impl.ProxyTanker;
import pro.cryptoevil.proxy.impl.ProxyValidator;
import pro.cryptoevil.proxy.impl.model.Proxy;
import pro.cryptoevil.proxy.integrations.getproxylist.GetProxyListProxyRepository;
import pro.cryptoevil.proxy.web.WebClient;

@Configuration
@ConditionalOnClass({IProxyHolder.class, IProxyValidator.class})
@ConditionalOnMissingBean({ProxyHolder.class, ProxyTanker.class})
public class ProxyTankerAutoConfiguration {

    //TODO: Make config entity bean via @ConfigurationProperties instead of this piece of shit!

    @Value("${proxy.proxy-validator.checkUrl}")
    private String proxyValidatorCheckUrl;

    @Value("${proxy.proxy-holder.checkDelay}")
    private Long proxyHolderCheckDelay;
    @Value("${proxy.proxy-holder.autoStart}")
    private Boolean proxyHolderAutoStart;

    @Value("${proxy.proxy-tanker.proxyLimit}")
    private Integer proxyTankerProxyLimit;
    @Value("${proxy.proxy-tanker.grabDelay}")
    private Long proxyTankerGrabDelay;
    @Value("${proxy.proxy-tanker.autoStart}")
    private Boolean proxyTankerAutoStart;

    @Value("${proxy.integrations.getproxylist.endpointUrl}")
    private String proxyListEndpointUrl;
    @Value("${proxy.integrations.getproxylist.apiKey}")
    private String proxyListApiKey;

    @Bean
    public ProxyValidator proxyValidator() {
        return new ProxyValidator(this.proxyValidatorCheckUrl);
    }

    @Bean
    public ProxyHolder proxyHolder(ProxyValidator proxyValidator) {
        return new ProxyHolder(proxyValidator, this.proxyHolderCheckDelay, this.proxyHolderAutoStart);
    }

    @Bean
    public ProxyTanker proxyTanker(ProxyHolder proxyHolder) {
        ProxyRepository<Proxy> proxyRepository =
                new GetProxyListProxyRepository(
                        this.proxyListEndpointUrl,
                        this.proxyListApiKey,
                        new ObjectMapper(),
                        new WebClient());
        return new ProxyTanker(
                proxyRepository,
                proxyHolder,
                this.proxyTankerProxyLimit,
                this.proxyTankerGrabDelay,
                this.proxyTankerAutoStart);
    }
}
