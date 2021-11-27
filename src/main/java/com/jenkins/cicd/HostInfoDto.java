package com.jenkins.cicd;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HostInfoDto {
    private String hostName;
    private String hostAddress;
}
