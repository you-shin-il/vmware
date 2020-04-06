package vmware.vim;

import vmware.common.authentication.VimAuthenticationHelper;

public class VimTemplate {
    private VimAuthenticationHelper vimAuthenticationHelper;

    public VimTemplate() {
        vimAuthenticationHelper = new VimAuthenticationHelper();
        vimAuthenticationHelper.loginByUsernameAndPassword("192.168.50.22", "administrator@vsphere.local", "admin123!Q");
    }

    public void getTemplate() {

    }
}