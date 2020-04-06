package vmware.rest.controller;

import com.vmware.content.ConfigurationModel;
import com.vmware.content.library.ItemModel;
import com.vmware.content.library.ItemTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vmware.rest.service.vapi.VMwareItemService;

import java.util.List;

@RestController
public class VMCreate {

    @Autowired
    private VMwareItemService vmwareItemService;

    @PostMapping("/vmware/vm/create/templateCreate.do")
    @ResponseBody
    public ConfigurationModel templateCreate() {//컨텐츠 라이브러리, 데이터 센터 선택
        return null;
    }

    @GetMapping("/vmware/vm/create/getTemplateList.do")
    public List<ItemModel> getTemplateList(@RequestBody ItemTypes.FindSpec spec) {
        List<ItemModel> itemModels = vmwareItemService.itemModelList(spec);
        return itemModels;
    }
}