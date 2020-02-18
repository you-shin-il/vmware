package vmware.rest.service.vapi;

import com.vmware.content.Library;
import com.vmware.content.LibraryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vmwareLibraryService")
public class VMwareLibraryService {
    @Autowired
    private Library libraryervice;

    public List<String> list() {
        List<String> list = libraryervice.list();
        return list;
    }

    public LibraryModel get(String libraryId) {
        LibraryModel libraryModel = libraryervice.get(libraryId);
        return libraryModel;
    }
}