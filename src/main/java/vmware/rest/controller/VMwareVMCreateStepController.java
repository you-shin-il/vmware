package vmware.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmware.vapi.bindings.type.StructType;
import com.vmware.vapi.data.StructValue;
import com.vmware.vcenter.DatacenterTypes;
import com.vmware.vcenter.DatastoreTypes;
import com.vmware.vcenter.HostTypes;
import com.vmware.vcenter.VMTypes.CreateSpec;
import com.vmware.vim25.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vmware.rest.service.vapi.VMwareDatacenterService;
import vmware.rest.service.vapi.VMwareDatastoreService;
import vmware.rest.service.vapi.VMwareHostService;
import vmware.rest.service.vapi.VMwareVMService;
import vmware.rest.service.vim.VMwareVIMService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class VMwareVMCreateStepController {
    @Autowired
    private VMwareVMService vmwareVMService;
    @Autowired
    private VMwareDatacenterService vmwareDatacenterService;
    @Autowired
    private VMwareDatastoreService vmwareDatastoreService;
    @Autowired
    private VMwareHostService vmwareHostService;
    @Autowired
    private VMwareVIMService vmwareVIMService;
//    @Autowired
//    private VMwareStorageService vmwareHostService;

    /**
     * 새 가상 시스템 생성 step1 이름 및 폴더 선택
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/vmware/vm/create/step1.do")
    public List<DatacenterTypes.Summary> step1(@RequestBody DatacenterTypes.FilterSpec filterSpec) {
        List<DatacenterTypes.Summary> list = vmwareDatacenterService.list(filterSpec);

        for(DatacenterTypes.Summary data : list) {
            StructValue structValue = data._getDataValue();
            Set<String> strings = data._getDynamicFieldNames();
            StructType structType = data._getType();
            String datacenter = data.getDatacenter();
            String s = data._getCanonicalName();
            System.out.println("==============");
        }

/*        list.stream().forEach(x -> {
            System.out.println("=================================");
            System.out.println(x._getDynamicFieldNames().toString());
            System.out.println("=================================");
            System.out.println("=================================");
            System.out.println(x._getDataValue().toString());
            System.out.println("=================================");
            System.out.println("=================================");
            System.out.println(x._getType().toString());
            System.out.println("=================================");
        });*/
        return list;
    }

    /**
     * 새 가상 시스템 생성 step2 계산 리소스 선택
     * @return
     */
    @GetMapping("/vmware/vm/create/step2.do")
    public List<HostTypes.Summary> step2(@RequestBody HostTypes.FilterSpec filterSpec) {
        List<HostTypes.Summary> list = vmwareHostService.list(filterSpec);
        return list;
    }

    /**
     * 새 가상 시스템 생성 step3 스토리지 선택
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/vmware/vm/create/step3.do")
    public List<DatastoreTypes.Summary> step3(@RequestBody ManagedObjectReference mor) throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        RetrieveResult hostDatastore = vmwareVIMService.getHostDatastore(mor);
        List<ObjectContent> objects = hostDatastore.getObjects();

        Set<String> collect = objects.stream().map(ObjectContent::getPropSet)
                .flatMap(dynamicProperties -> dynamicProperties.stream())
                .filter(dynamicProperty -> "datastore".equals(dynamicProperty.getName()))
                .map(DynamicProperty::getVal)
                .map(obj -> (ArrayOfManagedObjectReference) obj)
                .flatMap(managed -> managed.getManagedObjectReference().stream())
                .map(ManagedObjectReference::getValue)
                .collect(Collectors.toSet());

        List<DatastoreTypes.Summary> list = vmwareDatastoreService.list(new DatastoreTypes.FilterSpec.Builder().setDatastores(collect).build());
        return list;
/*                .map(managedObjectReference -> ((ManagedObjectReference)managedObjectReference))
                .map(ManagedObjectReference::getValue)
                .collect(Collectors.toSet());*/

        //List<DatastoreTypes.Summary> list = vmwareDatastoreService.list(new DatastoreTypes.FilterSpec.Builder().setDatastores(collect).build());

/*        List<Object> datastores = objects.stream().map(ObjectContent::getPropSet)
                .flatMap(dynamicProperties -> dynamicProperties.stream())
                .filter(z -> "datastore".equals(z.getName()))
                .map(DynamicProperty::getVal)
                .collect(Collectors.toList());*/

/*        ObjectContent objectContent = objects.get(0);
        List<DynamicProperty> propSet = objectContent.getPropSet();*/

/*        List<DynamicProperty> propSet = objects.get(0).getPropSet();
        List<Object> datastores = new ArrayList<>();

        for(DynamicProperty dynamicProperty : propSet) {
            if("datastore".equals(dynamicProperty.getName())) {
                datastores.add(dynamicProperty.getVal());
            }
        }*/

/*        List<DynamicProperty> collect = objects.stream().map(ObjectContent::getPropSet)
                .flatMap(dynamicProperties -> dynamicProperties.stream())
                .filter(z -> "datastore".equals(z.getName()))
                .collect(Collectors.toList());*/

/*        List<ObjectContent> result = objects.stream()
                .filter(ele ->
                    ele.getPropSet().stream().anyMatch(element -> "name".equals(element.getName()) && "192.168.50.8".equals(element.getVal()))
                )
                .collect(Collectors.toList());*/

    }

    /**
     * 새 가상 시스템 생성 step4 호환성 선택
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/vmware/vm/create/step4.do")
    public List<VirtualMachineConfigOptionDescriptor> step4(@RequestBody ManagedObjectReference mor) throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        List<VirtualMachineConfigOptionDescriptor> environmentBrowser = vmwareVIMService.queryConfigOptionDescriptor(mor);

        return environmentBrowser;
    }

    /**
     * 새 가상 시스템 생성 step5 게스트 운영체제 선택
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/vmware/vm/create/step5.do")
    public Map<String, List<GuestOsDescriptor>> step5(@RequestBody HashMap<String, ManagedObjectReference> param, @RequestParam String vmxId) throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        ManagedObjectReference morEnv = (ManagedObjectReference)param.get("morEnv");
        ManagedObjectReference morHost = (ManagedObjectReference)param.get("morHost");

        VirtualMachineConfigOption guestMachineConfigOption = vmwareVIMService.getGuestMachineConfigOption(morEnv, morHost, vmxId);

        List<GuestOsDescriptor> guestOSDescriptor = guestMachineConfigOption.getGuestOSDescriptor();
        Map<String, List<GuestOsDescriptor>> collect
                = guestOSDescriptor.stream().filter(GuestOsDescriptor::isSupportedForCreate)
                                            .collect(Collectors.groupingBy(guestOsDescriptor -> {
                                                if ("windowsGuest".equals(guestOsDescriptor.getFamily()) || "linuxGuest".equals(guestOsDescriptor.getFamily())) {
                                                    return guestOsDescriptor.getFamily();
                                                } else {
                                                    return "otherGuest";
                                                }
                                            }));

        return collect;
    }

    @PostMapping("/vmware/vm/create/create.do")
    public String create(@RequestBody CreateSpec createSpec) {
        return  vmwareVMService.create(createSpec);
    }

    private List<Map<String, Object>> convertMap(Object target) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(target);
        List<Map<String, Object>> convert = mapper.readValue(jsonString, new TypeReference<List<Map<String, Object>>>() {});

        return convert;
    }

    private List<Object> filterDatastore(List<ObjectContent> objectContents) {
        return objectContents.stream().map(ObjectContent::getPropSet)
                .flatMap(dynamicProperties -> dynamicProperties.stream())
                .filter(z -> "datastore".equals(z.getName()))
                .map(DynamicProperty::getVal)
                .collect(Collectors.toList());
    }
}