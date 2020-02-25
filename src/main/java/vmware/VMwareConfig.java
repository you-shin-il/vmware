package vmware;

import com.vmware.content.Library;
import com.vmware.content.library.Item;
import com.vmware.content.library.item.Storage;
import com.vmware.vapi.bindings.StubConfiguration;
import com.vmware.vapi.protocol.HttpConfiguration;
import com.vmware.vcenter.*;
import com.vmware.vcenter.guest.CustomizationSpecs;
import com.vmware.vcenter.vm.Hardware;
import com.vmware.vcenter.vm.hardware.Memory;
import com.vmware.vstats.AcqSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import vmware.common.SslUtil;
import vmware.common.authentication.VapiAuthenticationHelper;
import vmware.common.authentication.VimAuthenticationHelper;

@Configuration
@PropertySource("classpath:vmware.properties")
public class VMwareConfig {
    @Value("${vmware.server.ip}")
    private String server;
    @Value("${vmware.server.username}")
    private String username;
    @Value("${vmware.server.password}")
    private String password;

/*    public VM vmservice() throws Exception {
        this.vapiAuthHelper = new VapiAuthenticationHelper();
        HttpConfiguration httpConfig =
                new HttpConfiguration.Builder()
                        .setSslConfiguration(new HttpConfiguration.SslConfiguration.Builder()
                                .disableCertificateValidation()
                                .disableHostnameVerification()
                                .getConfig())
                        .getConfig();

        this.sessionStubConfig = vapiAuthHelper.loginByUsernameAndPassword(this.server, this.username, this.password, httpConfig);

        return vapiAuthHelper.getStubFactory().createStub(VM.class, sessionStubConfig);
        return null;
    }*/

/*    @Bean
    public ServiceInstance serviceInstance() {
        return null;
    }*/

    @Bean
    public StubConfiguration stubConfiguration() throws Exception {
        VapiAuthenticationHelper vapiAuthenticationHelper = this.vapiAuthenticationHelper();
        HttpConfiguration httpConfig = buildHttpConfiguration();
/*        HttpConfiguration httpConfig =
                new HttpConfiguration.Builder()
                        .setSslConfiguration(new HttpConfiguration.SslConfiguration.Builder()
                                .disableCertificateValidation()
                                .disableHostnameVerification()
                                .getConfig())
                        .getConfig();*/

        return vapiAuthenticationHelper.loginByUsernameAndPassword(this.server, this.username, this.password, httpConfig);
    }

    @Bean
    public VimAuthenticationHelper vimAuthenticationHelper() {
        VimAuthenticationHelper vimAuthenticationHelper = new VimAuthenticationHelper();
        vimAuthenticationHelper.loginByUsernameAndPassword(this.server, this.username, this.password);
        return vimAuthenticationHelper;
    }

    @Bean
    public VapiAuthenticationHelper vapiAuthenticationHelper() {
        return new VapiAuthenticationHelper();
    }

    @Bean
    public VM vmservice(@Autowired VapiAuthenticationHelper vapiAuthenticationHelper, @Autowired StubConfiguration stubConfiguration) {
        return vapiAuthenticationHelper.getStubFactory().createStub(VM.class, stubConfiguration);
    }

    @Bean
    public Datastore datastoreservice(@Autowired VapiAuthenticationHelper vapiAuthenticationHelper, @Autowired StubConfiguration stubConfiguration) {
        return vapiAuthenticationHelper.getStubFactory().createStub(Datastore.class, stubConfiguration);
    }

    @Bean
    public Folder folderservice(@Autowired VapiAuthenticationHelper vapiAuthenticationHelper, @Autowired StubConfiguration stubConfiguration) {
        return vapiAuthenticationHelper.getStubFactory().createStub(Folder.class, stubConfiguration);
    }

    @Bean
    public ResourcePool resourcePoolservice(@Autowired VapiAuthenticationHelper vapiAuthenticationHelper, @Autowired StubConfiguration stubConfiguration) {
        return vapiAuthenticationHelper.getStubFactory().createStub(ResourcePool.class, stubConfiguration);
    }

    @Bean
    public com.vmware.vcenter.inventory.Datastore inventoryservice(@Autowired VapiAuthenticationHelper vapiAuthenticationHelper, @Autowired StubConfiguration stubConfiguration) {
        return vapiAuthenticationHelper.getStubFactory().createStub(com.vmware.vcenter.inventory.Datastore.class, stubConfiguration);
    }

    @Bean
    public Library libraryservice(@Autowired VapiAuthenticationHelper vapiAuthenticationHelper, @Autowired StubConfiguration stubConfiguration) {
        return vapiAuthenticationHelper.getStubFactory().createStub(Library.class, stubConfiguration);
    }

    @Bean
    public Item itemsservice(@Autowired VapiAuthenticationHelper vapiAuthenticationHelper, @Autowired StubConfiguration stubConfiguration) {
        return vapiAuthenticationHelper.getStubFactory().createStub(Item.class, stubConfiguration);
    }

    @Bean
    public Memory memoryservice(@Autowired VapiAuthenticationHelper vapiAuthenticationHelper, @Autowired StubConfiguration stubConfiguration) {
        return vapiAuthenticationHelper.getStubFactory().createStub(Memory.class, stubConfiguration);
    }

    @Bean
    public AcqSpecs acqSpecsservice(@Autowired VapiAuthenticationHelper vapiAuthenticationHelper, @Autowired StubConfiguration stubConfiguration) {
        return vapiAuthenticationHelper.getStubFactory().createStub(AcqSpecs.class, stubConfiguration);
    }

    @Bean
    public Hardware hardwareservice(@Autowired VapiAuthenticationHelper vapiAuthenticationHelper, @Autowired StubConfiguration stubConfiguration) {
        return vapiAuthenticationHelper.getStubFactory().createStub(Hardware.class, stubConfiguration);
    }

    @Bean
    public CustomizationSpecs customizationSpecsservice(@Autowired VapiAuthenticationHelper vapiAuthenticationHelper, @Autowired StubConfiguration stubConfiguration) {
        return vapiAuthenticationHelper.getStubFactory().createStub(CustomizationSpecs.class, stubConfiguration);
    }

    @Bean
    public Host hostservice(@Autowired VapiAuthenticationHelper vapiAuthenticationHelper, @Autowired StubConfiguration stubConfiguration) {
        return vapiAuthenticationHelper.getStubFactory().createStub(Host.class, stubConfiguration);
    }

    @Bean
    public Cluster clusterservice(@Autowired VapiAuthenticationHelper vapiAuthenticationHelper, @Autowired StubConfiguration stubConfiguration) {
        return vapiAuthenticationHelper.getStubFactory().createStub(Cluster.class, stubConfiguration);
    }

    @Bean
    public Datacenter datacenterservice(@Autowired VapiAuthenticationHelper vapiAuthenticationHelper, @Autowired StubConfiguration stubConfiguration) {
        return vapiAuthenticationHelper.getStubFactory().createStub(Datacenter.class, stubConfiguration);
    }

    @Bean
    public Storage storageservice(@Autowired VapiAuthenticationHelper vapiAuthenticationHelper, @Autowired StubConfiguration stubConfiguration) {
        return vapiAuthenticationHelper.getStubFactory().createStub(Storage.class, stubConfiguration);
    }

    private HttpConfiguration buildHttpConfiguration() throws Exception {
        HttpConfiguration httpConfig =
                new HttpConfiguration.Builder()
                        .setSslConfiguration(buildSslConfiguration())
                        .getConfig();

        return httpConfig;
    }

    private HttpConfiguration.SslConfiguration buildSslConfiguration() throws Exception {
        HttpConfiguration.SslConfiguration sslConfig;
            /*
             * Below method enables all VIM API connections to the server
             * without validating the server certificates.
             *
             * Note: Below code is to be used ONLY IN DEVELOPMENT ENVIRONMENTS.
             * Circumventing SSL trust is unsafe and should not be used in
             * production software.
             */
            SslUtil.trustAllHttpsCertificates();

            /*
             * Below code enables all vAPI connections to the server
             * without validating the server certificates..
             *
             * Note: Below code is to be used ONLY IN DEVELOPMENT ENVIRONMENTS.
             * Circumventing SSL trust is unsafe and should not be used in
             * production software.
             */
            sslConfig = new HttpConfiguration.SslConfiguration.Builder()
                    .disableCertificateValidation()
                    .disableHostnameVerification()
                    .getConfig();

        return sslConfig;
    }
/*
    @Bean
    public DynamicData customizationSpecsservice(@Autowired VapiAuthenticationHelper vapiAuthenticationHelper, @Autowired StubConfiguration stubConfiguration) {
        return vapiAuthenticationHelper.getStubFactory().createStub(CustomizationSpecs.class, stubConfiguration);
    }

/*    @Bean
    public ResourcePool resourcePoolservice(@Autowired VapiAuthenticationHelper vapiAuthenticationHelper, @Autowired StubConfiguration stubConfiguration) {
        return vapiAuthenticationHelper.getStubFactory().createStub(ResourcePool.class, stubConfiguration);
    }*/
}