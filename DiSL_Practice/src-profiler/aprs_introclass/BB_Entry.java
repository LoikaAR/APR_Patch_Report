package aprs_introclass;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BB_Entry {
    private int orderIdx;
    private int id;
    private int nBytecodes;
    private int nInvocations;
    private int nAllocations;
    private int nFieldAccesses;
    private Map<String, String> localVars;

    public BB_Entry() {};

    public void setId(int id) {
        this.id = id;
    }

    public void setOrderIdx(int orderIdx) {
        this.orderIdx = orderIdx;
    }

    public void setnBytecodes(int nBytecodes) {
        this.nBytecodes = nBytecodes;
    }

    public void setnAllocations(int nAllocations) {
        this.nAllocations = nAllocations;
    }

    public void setnInvocations(int nInvocations) {
        this.nInvocations = nInvocations;
    }

    public void setnFieldAccesses(int nFieldAccesses) {
        this.nFieldAccesses = nFieldAccesses;
    }

    public void setLocalVars(Map<String, String> localVars) {
        this.localVars = localVars;
    }

    public int getOrderIdx() {
        return orderIdx;
    }

    public int getId() {
        return id;
    }

    public int getnBytecodes() {
        return nBytecodes;
    }

    public int getnAllocations() {
        return nAllocations;
    }

    public int getnInvocations() {
        return nInvocations;
    }

    public int getnFieldAccesses() {
        return nFieldAccesses;
    }

    public Map<String, String> getLocalVars() {
        return localVars;
    }

}