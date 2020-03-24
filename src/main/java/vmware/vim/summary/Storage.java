package vmware.vim.summary;

public class Storage {
    private Long storage_summaryCapacity = new Long(0L);
    private Long storage_summaryFreeSpace = new Long(0L);
    public static final String SUMMARY_CAPACITY = "summary.capacity";
    public static final String SUMMARY_FREESPACE = "summary.freeSpace";

    public Storage() { }

    public Storage(Long storage_summaryCapacity, Long storage_summaryFreeSpace) {
        this.storage_summaryCapacity = storage_summaryCapacity;
        this.storage_summaryFreeSpace = storage_summaryFreeSpace;
    }

    public Long getStorage_summaryCapacity() {
        return storage_summaryCapacity;
    }

    public void setStorage_summaryCapacity(Long storage_summaryCapacity) {
        this.storage_summaryCapacity = storage_summaryCapacity;
    }

    public Long getStorage_summaryFreeSpace() {
        return storage_summaryFreeSpace;
    }

    public void setStorage_summaryFreeSpace(Long storage_summaryFreeSpace) {
        this.storage_summaryFreeSpace = storage_summaryFreeSpace;
    }

    public double getAvailableStorage() {
        double storageGB = (double)getStorage_summaryFreeSpace() / 1024d / 1024d / 1024d;
        return Math.round(storageGB * 100d) / 100d;
    }

    public double getStorage() {
        double storageGB = (double)getStorage_summaryCapacity() / 1024d / 1024d / 1024d;
        return Math.round(storageGB * 100d) / 100d;
    }

    public double getStorageUse() {
        return getStorage() - getAvailableStorage();
    }


}