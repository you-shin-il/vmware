package vmware.rest.service.vapi;

import com.vmware.content.ConfigurationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("vmwareConfigurationService")
public class VMwareConfigurationService {
    @Autowired
    private com.vmware.content.Configuration configurationservice;

    public ConfigurationModel get() {
        ConfigurationModel configurationModel = configurationservice.get();
        return configurationModel;
    }

}