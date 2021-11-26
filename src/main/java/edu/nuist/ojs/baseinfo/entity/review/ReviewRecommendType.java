package edu.nuist.ojs.baseinfo.entity.review;

import java.util.HashMap;


public enum ReviewRecommendType {
	ACCEPT("接收", "Accept Submission", 1),
	Min_Revision("小修","Minor Revision", 2),
	Maj_Revision("大修","Major Revision", 3),
	Resubmit_Elsewhere("另投","Resubmit Elsewhere", 4),
	Decline("拒收","Decline Submission", 5),
	See("见评语","See comments",6 );
	
	private int index;
    private String zh;
    private String en;
    
    private ReviewRecommendType(String zh, String en, int index) {
        this.zh = zh;
        this.en = en;
        this.index = index;
    }
    
    public String getZh() {
        return zh;
    }

    public String getEn() {
        return en;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    public static ReviewRecommendType getByIndex(int index) {  
        for (ReviewRecommendType c : ReviewRecommendType.values()) {  
            if (c.getIndex() == index) {  
                return c;
            }  
        }  
        return null;  
    }
    
    
}
