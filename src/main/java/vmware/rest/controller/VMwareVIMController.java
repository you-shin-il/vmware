package vmware.rest.controller;

import com.vmware.vim25.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import vmware.common.authentication.VimAuthenticationHelper;
import vmware.rest.service.vim.VMwareVIMService;
import vmware.vim.VimTemplateDeploy;

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
        List<ObjectContent> template = vimTemplateDeploy.getTemplate();
        ObjectContent objectContent = template.get(0);
        //vimTemplateDeploy.getTemplateSpec(objectContent);
        //vimTemplateDeploy.getTemplateSpec();
        vimTemplateDeploy.cloneVMTask();
    }

    public void vim() throws RuntimeFaultFaultMsg {
//        GuestOSService guestOSService = new GuestOSService(vimAuthenticationHelper.getVimPort(), vimAuthenticationHelper.getServiceContent());
//        List<GuestOsDescriptor> guestOsDescriptor = guestOSService.getGuestOsDescriptor();
    }
}