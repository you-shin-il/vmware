package vmware.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmware.vcenter.DatacenterTypes;
import com.vmware.vim25.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import vmware.common.VimUtil;
import vmware.rest.service.vapi.VMwareDatacenterService;
import vmware.vim.*;
import vmware.vim.summary.*;
import vmware.vim.util.VimRetrievePropertiesExUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class VMwareDataCenterCpuMemoryStorageInfoController {
    @Autowired
    private VMwareDatacenterService datacenterService;
    private String cpu_description;
    private Integer cpu_cpuMhz = 0;
    private Integer cpu_overallCpuUsage = 0;
    private Short cpu_numCpuCores = 0;
    private Long mem_memorySize = new Long(0L);
    private Integer mem_overallMemoryUsage = 0;
    Host host = null;

    @GetMapping("/vmware/datacenter/cpuMemoryStorageInfo.do")
    @ResponseBody
    public List<ObjectContent> cpuMemoryStorageInfo(@RequestBody List<ManagedObjectReference> mors) throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        host = new Host();
        if (mors.isEmpty()) {
            List<DatacenterTypes.Summary> list = datacenterService.list(new DatacenterTypes.FilterSpec());

            mors = list.stream().collect(Collectors.mapping(datacenter -> {
                ManagedObjectReference datastoreMor = new ManagedObjectReference();
                datastoreMor.setType("Datacenter");
                datastoreMor.setValue(datacenter.getDatacenter());
                return datastoreMor;
            }, Collectors.toList()));
        }

        VimDatacenter vimDatacenter = new VimDatacenter(mors);
        List<ObjectContent> datacenterObjectContents = vimDatacenter.info(Arrays.asList("hostFolder"));
        List<ManagedObjectReference> folderMors = VimUtil.filterManagedObjectReference(datacenterObjectContents);

        VimFolderGroup vimFolderGroup = new VimFolderGroup(folderMors);
        List<ObjectContent> folderGroupObjectContents = vimFolderGroup.Info(Arrays.asList("childEntity"));
        List<ManagedObjectReference> computeAndClusterMors = VimUtil.filterManagedObjectReference(folderGroupObjectContents);

        VimComputeResource vimComputeResource = new VimComputeResource(computeAndClusterMors.stream().filter(x -> "ComputeResource".equals(x.getType())).collect(Collectors.toList()));
        List<ObjectContent> computeGroupObjectContents = vimComputeResource.info(Arrays.asList("host", "datastore"));
        List<ManagedObjectReference> hostAndDatastoreMors = VimUtil.filterManagedObjectReference(computeGroupObjectContents);

        VimHostSystem vimHostSystem = new VimHostSystem(hostAndDatastoreMors);
        List<ObjectContent> hostSystemObjectContents = vimHostSystem.info(Arrays.asList(
                "summary.hardware.cpuMhz", //CPU Mhz
                "summary.hardware.numCpuCores", //CPU 코어 수
                "summary.quickStats.overallCpuUsage", // CPU 사용됨
                "hardware.memorySize", //메모리 용량
                "summary.quickStats.overallMemoryUsage" //메모리 사용됨
                )
        );

        VimDataStore vimDataStore = new VimDataStore(hostAndDatastoreMors.stream().filter(x -> "Datastore".equals(x.getType())).collect(Collectors.toList()));
        List<ObjectContent> dataStoreObjectContents = vimDataStore.info(Arrays.asList(
                "summary.capacity", //스토리지 용량 (수용력)
                "summary.freeSpace"//스토리지 사용가능// (자유공간)
                )
        );

        setSummaryCpu(hostSystemObjectContents);
        setSummaryMemory(hostSystemObjectContents);

        //List<ManagedObjectReference> hostSystemMors = VimUtil.filterManagedObjectReference(hostSystemObjectContents);

        System.out.println("cpuInfo");
        double cpuGhz = host.getCpu().getCpuGhz();
        double availableCpu = host.getCpu().getAvailableCpu();
        double overallCpuUsage = host.getCpu().getCpu_overallCpuUsage();

        System.out.println("memoryInfo");
        long memoryGB = host.getMemory().getMemoryGB();
        double availableMemory = host.getMemory().getAvailableMemory();
        double memoryUse = host.getMemory().getMemoryUse();

        System.out.println("storageInfo");
        host.setDatastores(setDatastore(dataStoreObjectContents));
        Storage allHostStorage = host.getAllHostStorage();
        System.out.println(allHostStorage.getStorage());
        System.out.println(allHostStorage.getAvailableStorage());
        System.out.println(allHostStorage.getStorageUse());

        System.out.println("-summary-");
        System.out.println("CPU 용량 : " + host.getCpu().getCpuGhz());
        System.out.println("CPU 사용됨 : " + host.getCpu().getCpu_overallCpuUsage());
        System.out.println("CPU 사용가능 : " + host.getCpu().getAvailableCpu());
        System.out.println("메모리 용량 : " + host.getMemory().getMemoryGB());
        System.out.println("메모리 사용됨 : " + host.getMemory().getMemoryUse());
        System.out.println("메모리 사용가능 : " + host.getMemory().getAvailableMemory());
        System.out.println("스토리지 용량 : " + host.getAllHostStorage().getStorage());
        System.out.println("스토리지 사용됨 : " + host.getAllHostStorage().getStorageUse());
        System.out.println("스토리지 사용가능 : " + host.getAllHostStorage().getAvailableStorage());

        VimRetrievePropertiesExUtil vimRetrievePropertiesExUtil = new VimRetrievePropertiesExUtil();
        vimRetrievePropertiesExUtil.setMors(mors);
        vimRetrievePropertiesExUtil.setPropertyFilterSpec(
            "Datacenter", Arrays.asList(
                    "hostFolder"
/*                   "summary.hardware.cpuMhz", //CPU Mhz
                   "summary.hardware.numCpuCores", //CPU 코어 수
                   "summary.quickStats.overallCpuUsage", // CPU 사용됨
                   "hardware.memorySize", //메모리 용량
                    "summary.quickStats.overallMemoryUsage" //메모리 사용됨*/
                )
        );
        List<ObjectContent> objectContents1 = vimRetrievePropertiesExUtil.retrievePropertiesEx();

        vimRetrievePropertiesExUtil.setMors(VimUtil.filterManagedObjectReference(objectContents1));
        vimRetrievePropertiesExUtil.setPropertyFilterSpec(
                "Folder", Arrays.asList(
                        "childEntity"
/*                   "summary.hardware.cpuMhz", //CPU Mhz
                   "summary.hardware.numCpuCores", //CPU 코어 수
                   "summary.quickStats.overallCpuUsage", // CPU 사용됨
                   "hardware.memorySize", //메모리 용량
                    "summary.quickStats.overallMemoryUsage" //메모리 사용됨*/
                )
        );
        List<ObjectContent> objectContents2 = vimRetrievePropertiesExUtil.retrievePropertiesEx();

        vimRetrievePropertiesExUtil.setMors(
                VimUtil.filterManagedObjectReference(objectContents2).stream().
                                                                      filter(x -> "ComputeResource".equals(x.getType())).
                                                                      collect(Collectors.toList()
                )
        );
        vimRetrievePropertiesExUtil.setPropertyFilterSpec(
                "ComputeResource", Arrays.asList(
                        "host",
                        "datastore"
/*                   "summary.hardware.cpuMhz", //CPU Mhz
                   "summary.hardware.numCpuCores", //CPU 코어 수
                   "summary.quickStats.overallCpuUsage", // CPU 사용됨
                   "hardware.memorySize", //메모리 용량
                    "summary.quickStats.overallMemoryUsage" //메모리 사용됨*/
                )
        );
        List<ObjectContent> objectContents3 = vimRetrievePropertiesExUtil.retrievePropertiesEx();

        vimRetrievePropertiesExUtil.setMors(VimUtil.filterManagedObjectReference(objectContents3));
        vimRetrievePropertiesExUtil.setPropertyFilterSpec(
                "HostSystem", Arrays.asList(
                        "summary.hardware.cpuMhz", //CPU Mhz
                        "summary.hardware.numCpuCores", //CPU 코어 수
                        "summary.quickStats.overallCpuUsage", // CPU 사용됨
                        "hardware.memorySize", //메모리 용량
                        "summary.quickStats.overallMemoryUsage" //메모리 사용됨
                )
        );
        List<ObjectContent> objectContents4 = vimRetrievePropertiesExUtil.retrievePropertiesEx();

        vimRetrievePropertiesExUtil.setMors(
                VimUtil.filterManagedObjectReference(objectContents3).stream().
                        filter(x -> "Datastore".equals(x.getType())).
                        collect(Collectors.toList()
                        )
        );
        vimRetrievePropertiesExUtil.setPropertyFilterSpec(
                "Datastore", Arrays.asList(
                        "summary.capacity", //스토리지 용량 (수용력)
                        "summary.freeSpace"//스토리지 사용가능// (자유공간)
                )
        );
        List<ObjectContent> objectContents5 = vimRetrievePropertiesExUtil.retrievePropertiesEx();
        System.out.println("END");

        return datacenterObjectContents;
    }

    private void setSummaryCpu(List<ObjectContent> objects) {
        System.out.println("setSummaryCpu");

        for(ObjectContent objs : objects) {
            if(!objs.getPropSet().isEmpty()) {
                for(DynamicProperty dynamicProperty : objs.getPropSet()) {
                    if ("summary.hardware.cpuPkg.description".equals(dynamicProperty.getName())) {
                        cpu_description = (String) dynamicProperty.getVal();
                    } else if ("summary.hardware.cpuMhz".equals(dynamicProperty.getName())) {
                        cpu_cpuMhz = (Integer) dynamicProperty.getVal();
                    } else if("summary.hardware.numCpuCores".equals(dynamicProperty.getName())) {
                        cpu_numCpuCores = (Short) dynamicProperty.getVal();
                    } else if("summary.quickStats.overallCpuUsage".equals(dynamicProperty.getName())) {
                        cpu_overallCpuUsage = (Integer) dynamicProperty.getVal();
                    }
                }
                host.setCpu(new Cpu(cpu_description, cpu_cpuMhz, cpu_overallCpuUsage, cpu_numCpuCores));
            }
        }
    }

    private void setSummaryMemory(List<ObjectContent> objects) {
        System.out.println("setSummaryMemory");

        for(ObjectContent objs : objects) {
            if(!objs.getPropSet().isEmpty()) {
                for(DynamicProperty dynamicProperty : objs.getPropSet()) {
                    if ("hardware.memorySize".equals(dynamicProperty.getName())) {
                        mem_memorySize = (Long) dynamicProperty.getVal();
                    } else if("summary.quickStats.overallMemoryUsage".equals(dynamicProperty.getName())) {
                        mem_overallMemoryUsage = (Integer) dynamicProperty.getVal();
                    }
                }
            }
            host.setMemory(new Memory(mem_memorySize, mem_overallMemoryUsage));
        }

    }

    private List<Datastore> setDatastore(List<ObjectContent> objects) {
        System.out.println("setSummaryStorage");
        List<Datastore> datastores = new ArrayList();

        for(ObjectContent objs : objects) {
            if(!objs.getPropSet().isEmpty()) {
                Storage storage = new Storage();

                for(DynamicProperty dynamicProperty : objs.getPropSet()) {
                    if ("summary.capacity".equals(dynamicProperty.getName())) {
                        storage.setStorage_summaryCapacity((Long) dynamicProperty.getVal());
                        //storage_summaryCapacity = (Long) dynamicProperty.getVal();
                    } else if("summary.freeSpace".equals(dynamicProperty.getName())) {
                        storage.setStorage_summaryFreeSpace((Long) dynamicProperty.getVal());
                        //storage_summaryFreeSpace = (Long) dynamicProperty.getVal();
                    }
                }
                datastores.add(new Datastore(objs.getObj().getValue(), storage));
            }
        }
        return datastores;
    }

    @GetMapping("/vmware/datacenter/datacenterSummary.do")
    @ResponseBody
    public DataCenter datacenterSummary(@RequestBody List<ManagedObjectReference> datacenterMors) throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg, JsonProcessingException {
        if (datacenterMors.isEmpty()) {
            List<DatacenterTypes.Summary> list = datacenterService.list(new DatacenterTypes.FilterSpec());

            datacenterMors = list.stream().collect(Collectors.mapping(datacenter -> {
                ManagedObjectReference datastoreMor = new ManagedObjectReference();
                datastoreMor.setType("Datacenter");
                datastoreMor.setValue(datacenter.getDatacenter());
                return datastoreMor;
            }, Collectors.toList()));
        }

        VimRetrievePropertiesExUtil vimRetrievePropertiesExUtil = new VimRetrievePropertiesExUtil();
        vimRetrievePropertiesExUtil.setMors(datacenterMors);
        vimRetrievePropertiesExUtil.setPropertyFilterSpec(
                "Datacenter", Arrays.asList(
                        "hostFolder"
                )
        );
        List<ObjectContent> datacenterObjectContents = vimRetrievePropertiesExUtil.retrievePropertiesEx();

        vimRetrievePropertiesExUtil.setMors(VimUtil.filterManagedObjectReference(datacenterObjectContents));
        vimRetrievePropertiesExUtil.setPropertyFilterSpec(
                "Folder", Arrays.asList(
                        "childEntity"
                )
        );
        List<ObjectContent> folderObjectContents = vimRetrievePropertiesExUtil.retrievePropertiesEx();

        vimRetrievePropertiesExUtil.setMors(
                VimUtil.filterManagedObjectReference(folderObjectContents).stream().
                        filter(x -> "ComputeResource".equals(x.getType())).
                        collect(Collectors.toList()
                        )
        );
        vimRetrievePropertiesExUtil.setPropertyFilterSpec(
                "ComputeResource", Arrays.asList(
                        "host",
                        "datastore"
                )
        );
        List<ObjectContent> computeResourceObjectContents = vimRetrievePropertiesExUtil.retrievePropertiesEx();

        vimRetrievePropertiesExUtil.setMors(VimUtil.filterManagedObjectReference(computeResourceObjectContents));
        vimRetrievePropertiesExUtil.setPropertyFilterSpec(
                "HostSystem", Arrays.asList(
                        "summary.hardware.cpuModel", //프로세서 모델
                        "summary.hardware.cpuMhz", //CPU Mhz
                        "summary.hardware.numCpuCores", //CPU 코어 수
                        "summary.quickStats.overallCpuUsage", // CPU 사용됨
                        "hardware.memorySize", //메모리 용량
                        "summary.quickStats.overallMemoryUsage" //메모리 사용됨
                )
        );
        List<ObjectContent> hostSystemObjectContents = vimRetrievePropertiesExUtil.retrievePropertiesEx();

        vimRetrievePropertiesExUtil.setMors(
                VimUtil.filterManagedObjectReference(computeResourceObjectContents).stream().
                        filter(x -> "Datastore".equals(x.getType())).
                        collect(Collectors.toList()
                        )
        );
        vimRetrievePropertiesExUtil.setPropertyFilterSpec(
                "Datastore", Arrays.asList(
                        "summary.capacity", //스토리지 용량 (수용력)
                        "summary.freeSpace"//스토리지 사용가능// (자유공간)
                )
        );
        List<ObjectContent> datastoreObjectContents = vimRetrievePropertiesExUtil.retrievePropertiesEx();

        //Datastore.newDatastore(datastoreObjectContents);
        //setSummaryCpu(hostSystemObjectContents);
        //setSummaryMemory(hostSystemObjectContents);
        //host.setDatastores(setDatastore(datastoreObjectContents));

        Cpu cpu = Cpu.newCpu(hostSystemObjectContents);
        Memory memory = Memory.newMemory(hostSystemObjectContents);
        //Hosts hosts = new Hosts(Arrays.asList(new Host(cpu, memory, Datastore.newDatastore(datastoreObjectContents))));

        DataCenter dataCenter = new DataCenter();
        dataCenter.setHosts(Arrays.asList(new Host(cpu, memory, Datastore.newDatastore(datastoreObjectContents))));
        Map<String, Object> stringObjectMap = convertMap(dataCenter);
        return dataCenter;
    }

    private Map<String, Object> convertMap(Object target) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(target);
        Map<String, Object> convert = mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});

        return convert;
    }
}