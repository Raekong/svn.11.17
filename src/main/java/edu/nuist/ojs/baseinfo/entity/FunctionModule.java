package edu.nuist.ojs.baseinfo.entity;

import java.util.HashMap;

public enum FunctionModule {

    PAPERPROCESS("论文处理流程", 0), //论文处理流程
    SIMILARCHECK("论文查重流程", 1), //论文查重流程
    PAYMENT("支付", 2),//支付
    PUBLISH("与主站对接", 3),//与主站对接
    EMAILTRACK("邮件追踪", 4),//邮件追踪
    SPECIALISSUEAPPLY("特刊申请", 5);//特刊申请

    private int index;
    private String module;

    FunctionModule(String module, int index) {
        this.module = module;
        this.index = index;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public static String getModule(int index) {
        for (FunctionModule c : FunctionModule.values()) {
            if (c.getIndex() == index) {
                return c.module;
            }
        }
        return null;
    }

    public static FunctionModule getByIndex(int index) {
        for (FunctionModule c : FunctionModule.values()) {
            if (c.getIndex() == index) {
                return c;
            }
        }
        return null;
    }

    public static FunctionModule getByModule(String module) {
        for (FunctionModule c : FunctionModule.values()) {
            if (c.getModule().equals(module)) {
                return c;
            }
        }
        return null;
    }

    public static HashMap<String, Integer> getAll(){
        HashMap<String, Integer> rst = new HashMap<String, Integer>();
        for (FunctionModule c : FunctionModule.values()) {
            rst.put(c.getModule(), c.getIndex());
        }
        return rst;
    }

}
