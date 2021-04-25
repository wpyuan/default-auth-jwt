package com.github.wpyuan.jwt.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * </p>
 *
 * @author wangpeiyuan
 * @date 2021/4/25 11:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AuthFailResult {
    private String timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    @Override
    public String toString() {
       return "{" +
               "    \"timestamp\": \"" + this.timestamp + "\",\n" +
               "    \"status\": " + this.status + ",\n" +
               "    \"error\": \"" + this.error + "\",\n" +
               "    \"message\": \"" + this.message + "\",\n" +
               "    \"path\": \"" + this.path + "\"\n" +
               "}";
    }
}
