package vmware.rest.controller;

import com.vmware.vim25.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import vmware.common.authentication.VimAuthenticationHelper;
import vmware.common.helpers.VimUtil;
import vmware.rest.service.vim.GuestOSService;
import vmware.rest.service.vim.VMwareVIMService;
import vmware.vim.GetPerfStats;
import vmware.vim.VimTemplateDeploy;
import vmware.vim.util.VimMetricsUtil;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.List;

@Controller
public class VMwareVIMController {
    @Autowired
    private VMwareVIMService vmwareVIMService;

    @Autowired
    private VimAuthenticationHelper vimAuthenticationHelper;

    @GetMapping("/vmware/vim/test.do")
    public void test() throws RuntimeFaultFaultMsg, CustomizationFaultFaultMsg, InvalidDatastoreFaultMsg, InsufficientResourcesFaultFaultMsg, FileFaultFaultMsg, VmConfigFaultFaultMsg, InvalidStateFaultMsg, MigrationFaultFaultMsg, TaskInProgressFaultMsg, InvalidPropertyFaultMsg {
        //vim();
        //vmwareVIMService.test();
/*        VimMetricsUtil metrics = new VimMetricsUtil();
        metrics.metricsTest();*/
/*        GetPerfStats getPerfStats = new GetPerfStats();
        getPerfStats.run();*/
        VimTemplateDeploy vimTemplateDeploy = new VimTemplateDeploy();
        //vimTemplateDeploy.getTemplateSpec();
        vimTemplateDeploy.cloneVMTask();
    }

    public void vim() throws RuntimeFaultFaultMsg {
//        GuestOSService guestOSService = new GuestOSService(vimAuthenticationHelper.getVimPort(), vimAuthenticationHelper.getServiceContent());
//        List<GuestOsDescriptor> guestOsDescriptor = guestOSService.getGuestOsDescriptor();
    }
}