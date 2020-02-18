package vmware.rest.controller;

import com.vmware.content.LibraryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareLibraryService;

import java.util.List;

@RestController
public class VMwareLibraryController {
    @Autowired
    private VMwareLibraryService vmwareLibraryService;

    @GetMapping("/vmware/library/list.do")
    public List<String> list() {
        List<String> list = vmwareLibraryService.list();
        System.out.println("======");
        System.out.println(list.toString());
        System.out.println("======");

        return list;
    }

    @GetMapping("/vmware/library/get.do")
    public LibraryModel get(String libraryId) {
        LibraryModel libraryModel = vmwareLibraryService.get(libraryId);
        System.out.println("======");
        System.out.println(libraryModel.toString());
        System.out.println("======");

        return libraryModel;
    }
}