package com.bbt.user;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/13 17:10
 */
@Configuration
@Component
public class EsConfig {

    @Value(value = "${es.address}")
    private String address;

    @Value(value = "${es.master.port}")
    private String esMasterPort;

    @Value(value = "${es.slave.port}")
    private String esSlavePort;

    @Value(value = "${es.cluster.name}")
    private String esClusterName;

    @Bean
    public TransportClient client() throws UnknownHostException {

        TransportAddress node = new TransportAddress(
                InetAddress.getByName("localhost"),
                9300
        );

        TransportAddress slave = new TransportAddress(
                InetAddress.getByName("localhost"),
                9301
        );

        Settings settings = Settings.builder()
                .put("cluster.name","wali")
                .build();

        PreBuiltTransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(node);
        client.addTransportAddress(slave);

        return client;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEsMasterPort() {
        return esMasterPort;
    }

    public void setEsMasterPort(String esMasterPort) {
        this.esMasterPort = esMasterPort;
    }

    public String getEsSlavePort() {
        return esSlavePort;
    }

    public void setEsSlavePort(String esSlavePort) {
        this.esSlavePort = esSlavePort;
    }

    public String getEsClusterName() {
        return esClusterName;
    }

    public void setEsClusterName(String esClusterName) {
        this.esClusterName = esClusterName;
    }
}
