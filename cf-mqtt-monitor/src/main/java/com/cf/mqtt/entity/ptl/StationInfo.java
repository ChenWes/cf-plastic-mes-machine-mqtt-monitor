package com.cf.mqtt.entity.ptl;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class StationInfo implements Serializable {
    /**
     * Client ID - readonly
     */
    @JSONField(name = "ID")
    public String ID = "";

    /**
     * MAC address - readonly
     */
    @JSONField(name = "MAC")
    public String MAC = "";

    /**
     * Alias, only for display
     */
    @JSONField(name = "Alias")
    public String Alias = "";

    /**
     * Client type
     */
    @JSONField(name = "ClientType")
    public int ClientType;

    /**
     * Server IP + port address
     * 默认值为 192.168.1.192:9080
     */
    @JSONField(name = "ServerAddress")
    public String ServerAddress = "";

    /**
     * Connection parameters with connection type
     * 0 - NULL
     * 1 - Certial name, password
     * 2 - User ID, password
     */
    @JSONField(name = "Parameters")
    public String[] Parameters;

    /**
     * Local IP address, keep empty means auto obtain an IP address
     * If local IP address is empty, will ignore subnet mask and gateway
     */
    @JSONField(name = "LocalIP")
    public String LocalIP = "";

    /**
     * Subnet mask, will use empty when LocalIP is empty
     */
    @JSONField(name = "SubnetMask")
    public String SubnetMask = "";

    /**
     * Gateway, will use empty when LocalIP is empty
     */
    @JSONField(name = "Gateway")
    public String Gateway = "";

    /**
     * Heartbeat, less than 15 is auto reset to 15
     */
    @JSONField(name = "Heartbeat")
    public int Heartbeat;

    /**
     * [Readonly]App version
     */
    @JSONField(name = "AppVersion")
    public String AppVersion = "";

    /**
     * [Readonly]Task in working channel
     */
    @JSONField(name = "TotalCount")
    public int TotalCount;

    /**
     * [Readonly]Task in cache
     */
    @JSONField(name = "SendCount")
    public int SendCount;

    @Override
    public String toString() {
        return "StationInfo{" +
                "ID='" + ID + '\'' +
                ", MAC='" + MAC + '\'' +
                ", Alias='" + Alias + '\'' +
                ", ClientType=" + ClientType +
                ", ServerAddress='" + ServerAddress + '\'' +
                ", Parameters=" + (Parameters != null ? String.join(", ", Parameters) : "null") +
                ", LocalIP='" + LocalIP + '\'' +
                ", SubnetMask='" + SubnetMask + '\'' +
                ", Gateway='" + Gateway + '\'' +
                ", Heartbeat=" + Heartbeat +
                ", AppVersion='" + AppVersion + '\'' +
                ", TotalCount=" + TotalCount +
                ", SendCount=" + SendCount +
                '}';
    }
}