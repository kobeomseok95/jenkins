package com.jenkins.cicd;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

@RestController
public class Api {

    @GetMapping("/")
    public String health() throws Exception {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        String hostName = InetAddress.getLocalHost().getHostName();
        /**
         *  TODO
         *      return 변경 > 단순 String에서 JSON으로 변경하기
         */
        return hostAddress + ", " + hostName;
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
