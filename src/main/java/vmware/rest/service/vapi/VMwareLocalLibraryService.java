package vmware.rest.service.vapi;

import com.vmware.content.LocalLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vmwareLocalLibraryService")
public class VMwareLocalLibraryService {
    @Autowired
    private LocalLibrary localLibraryservice;

    public List<String> list() {
        List<String> list = localLibraryservice.list();
        return list;
    }

}