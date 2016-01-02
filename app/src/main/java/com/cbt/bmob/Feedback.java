package com.cbt.bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by caobotao on 15/12/31.
 */
public class Feedback extends BmobObject {
    private String name;
    private String feedback;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
