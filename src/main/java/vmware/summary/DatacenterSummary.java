package vmware.summary;

import com.vmware.vcenter.DatacenterTypes;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.ObjectContent;
import vmware.common.VimUtil;
import vmware.vim.summary.*;
import vmware.vim.util.VimRetrievePropertiesExUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 데이터센터 요약 조회
 * 호스트(갯수)
 * 가상 시스템(갯수)
 * 클러스터(갯수)
 * 네트워크(갯수)
 * 데이터스토어(갯수)
 * CPU(용량, 사용 가능, 사용됨)
 * 메모리(용량, 사용 가능, 사용됨)
 * 스토리지(용량, 사용 가능, 사용됨)
 * @return
 * @throws Exception
 */
public class DatacenterSummary {

    public DatacenterSummary(ManagedObjectReference morDatacenter) {
        this.init(morDatacenter);
    }

    Hosts hosts;
    //Vms vms;
    Clusters clusters;
    //Networks network;
    //Datastores datastores;

    public Hosts getHosts() {
        return hosts;
    }

    public void setHosts(Hosts hosts) {
        this.hosts = hosts;
    }

    public Clusters getClusters() {
        return clusters;
    }

    public void setClusters(Clusters clusters) {
        this.clusters = clusters;
    }

    private void init(ManagedObjectReference morDatacenter) {
/*        VimRetrievePropertiesExUtil vimRetrievePropertiesExUtil = new VimRetrievePropertiesExUtil();
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

        Cpu cpu = Cpu.newCpu(hostSystemObjectContents);
        Memory memory = Memory.newMemory(hostSystemObjectContents);

        DataCenter dataCenter = new DataCenter();
        dataCenter.setHosts(Arrays.asList(new Host(cpu, memory, Datastore.newDatastore(datastoreObjectContents))));
        Map<String, Object> stringObjectMap = convertMap(dataCenter);
        return dataCenter;*/
    }
}