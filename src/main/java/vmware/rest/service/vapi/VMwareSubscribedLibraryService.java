package vmware.rest.service.vapi;

import com.vmware.content.SubscribedLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vmwareSubscribedLibraryService")
public class VMwareSubscribedLibraryService {
    @Autowired
    private SubscribedLibrary subscribedItemservice;

    public List<String> list() {
        List<String> list = subscribedItemservice.list();
        return list;
        //fileservice.list();
    }

}