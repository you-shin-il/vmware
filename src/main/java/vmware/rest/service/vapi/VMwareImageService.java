package vmware.rest.service.vapi;

import com.vmware.vcenter.iso.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("vmwareImageService")
public class VMwareImageService {
    @Autowired
    private Image imageservice;

    /**
     * vm에 이미지 마운트
     * @param libraryItem
     * @param vm
     * @return
     */
    public String mount(String libraryItem, String vm) {
        String mount = imageservice.mount(libraryItem, vm);
        return mount;
    }

}