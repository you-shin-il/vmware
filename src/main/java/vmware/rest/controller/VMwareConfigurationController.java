package vmware.rest.controller;

import com.vmware.content.ConfigurationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareConfigurationService;

@RestController
public class VMwareConfigurationController {
    @Autowired
    private VMwareConfigurationService vmwareConfigurationService;

    @GetMapping("/vmware/configuration/get.do")
    public ConfigurationModel get() {
        ConfigurationModel configurationModel = vmwareConfigurationService.get();
        return configurationModel;
    }

}