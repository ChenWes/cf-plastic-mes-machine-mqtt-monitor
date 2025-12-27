package com.cf.common.constant;

/**
 * 返回状态码
 *
 * @author WesChen
 */
public class InspectionStatus {
    /**
     * 正常
     */
    public static final int NORMAL = 0;

    /**
     * 首检中断，重新发起首检时，机台旧的首检记录会被中断
     */
    public static final int FIRST_INSPECTION_INTERRUPT = 1;

    /**
     * 工单暂停
     */
    public static final int JOB_ORDER_SUSPEND = 2;

    /**
     * 工单完成
     */
    public static final int JOB_ORDER_COMPLETED = 3;


    /**
     * 尾检中断,重新发起尾检时，机台旧的尾检记录会被中断
     */
    public static final int TAIL_INSPECTION_INTERRUPT = 4;

}
