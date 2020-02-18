package vmware.rest.service.vapi;

import com.vmware.vcenter.guest.CustomizationSpecs;
import com.vmware.vcenter.guest.CustomizationSpecsTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vmwareCustomizationSpecService")
public class VMwareCustomizationSpecService {
    @Autowired
    private CustomizationSpecs customizationSpecs;

    public List<CustomizationSpecsTypes.Summary> list() {
        CustomizationSpecsTypes.FilterSpec filterSpec = new CustomizationSpecsTypes.FilterSpec();
        filterSpec.setOSType(CustomizationSpecsTypes.OsType.WINDOWS);
        List<CustomizationSpecsTypes.Summary> list = customizationSpecs.list(filterSpec);

        System.out.println("========================================");
        System.out.println(list.toString());
        System.out.println("========================================");
        return list;
    }

}