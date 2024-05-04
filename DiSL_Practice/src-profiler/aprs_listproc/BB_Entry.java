package aprs_listproc;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BB_Entry {
    private int orderIdx;
    private int id;
    private int nBytecodes;
    private ConcurrentHashMap<String, String> localVars;

    public BB_Entry() {};

    public void setId(int id) {
        this.id = id;
    }

    public void setLocalVars(ConcurrentHashMap<String, String> localVars) {
        this.localVars = localVars;
    }

    public void setOrderIdx(int orderIdx) {
        this.orderIdx = orderIdx;
    }


    public void setnBytecodes(int nBytecodes) {
        this.nBytecodes = nBytecodes;
    }

    public int getId() {
        return id;
    }

    public int getnBytecodes() {
        return nBytecodes;
    }

    public ConcurrentHashMap<String, String> getLocalVars() {
        return localVars;
    }

    public int getOrderIdx() {
        return orderIdx;
    }
}