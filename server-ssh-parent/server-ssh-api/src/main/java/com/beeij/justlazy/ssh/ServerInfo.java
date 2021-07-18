package com.beeij.justlazy.ssh;

import lombok.*;

/**
 * 要操作的服务器信息
 *
 * @author ye
 * @version 1.0.0, 2021-07-04
 * @since 2021-07-04 22:29
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ServerInfo {
    String ip;
    int port = 22;
    String username = "root";
    String password;
    String secretKey;
}
