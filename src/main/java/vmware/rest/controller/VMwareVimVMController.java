package vmware.rest.controller;

import com.vmware.vim25.InvalidPropertyFaultMsg;
import com.vmware.vim25.RuntimeFaultFaultMsg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import vmware.vim.VMwareVimVMService;

@Controller
public class VMwareVimVMController {

    @GetMapping("/vmware/vim/vm/list.do")
    public void list() throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        VMwareVimVMService vmwareVimVMService = new VMwareVimVMService();
        //vmwareVimVMService.list("");
        vmwareVimVMService.test("");
    }

}