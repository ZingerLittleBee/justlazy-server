package com.beeij.justlazy.ssh.domian;

import lombok.*;

/**
 * 描述
 *
 * @author ye
 * @version 1.0.0, 2021-07-04
 * @since 2021-07-04 22:29
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ServerInfo {
    String ip;
    int port = 22;
    String username = "root";
    String password;
    String secretKey;
}
