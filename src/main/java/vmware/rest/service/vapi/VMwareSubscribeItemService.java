package vmware.rest.service.vapi;

import com.vmware.content.Type;
import com.vmware.content.TypeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vmwareSubscribeItemService")
public class VMwareSubscribeItemService {
    @Autowired
    private Type typeservice;

    public List<TypeTypes.Info> list() {
        List<TypeTypes.Info> list = typeservice.list();
        return list;
    }

}