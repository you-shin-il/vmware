package vmware.rest.controller;

import com.vmware.vim25.GuestOsDescriptor;
import com.vmware.vim25.InvalidPropertyFaultMsg;
import com.vmware.vim25.NotFoundFaultMsg;
import com.vmware.vim25.RuntimeFaultFaultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import vmware.common.authentication.VimAuthenticationHelper;
import vmware.common.helpers.VimUtil;
import vmware.rest.service.vim.GuestOSService;
import vmware.rest.service.vim.VMwareVIMService;

import java.util.List;

@Controller
public class VMwareVIMController {
    @Autowired
    private VMwareVIMService vmwareVIMService;

    @Autowired
    private VimAuthenticationHelper vimAuthenticationHelper;

    @GetMapping("/vmware/vim/test.do")
    public void test() throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg, NotFoundFaultMsg {
        //vim();
        vmwareVIMService.test();
    }

    public void vim() throws RuntimeFaultFaultMsg {
//        GuestOSService guestOSService = new GuestOSService(vimAuthenticationHelper.getVimPort(), vimAuthenticationHelper.getServiceContent());
//        List<GuestOsDescriptor> guestOsDescriptor = guestOSService.getGuestOsDescriptor();
    }
}