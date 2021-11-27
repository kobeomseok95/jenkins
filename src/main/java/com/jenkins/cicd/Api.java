package com.jenkins.cicd;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

@RestController
public class Api {

    @GetMapping("/")
    public ResponseEntity<HostInfoDto> main() throws Exception {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        String hostName = InetAddress.getLocalHost().getHostName();
        return ResponseEntity.ok(HostInfoDto.builder().hostAddress(hostAddress).hostName(hostName).build());
    }
}
