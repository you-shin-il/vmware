package vmware.rest.service.vapi;


import com.vmware.content.library.item.Storage;
import com.vmware.content.library.item.StorageTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vmwareStorageService")
public class VMwareStorageService {
    @Autowired
    private Storage storageservice;

    public List<StorageTypes.Info> list(String name) {
        List<StorageTypes.Info> list = storageservice.list(name);
        return list;
    }

}