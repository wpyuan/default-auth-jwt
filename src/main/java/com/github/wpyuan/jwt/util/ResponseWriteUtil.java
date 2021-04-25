package com.github.wpyuan.jwt.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>
 *     返回写入工具类
 * </p>
 *
 * @author wangpeiyuan
 * @date 2021/4/25 11:32
 */
@UtilityClass
public class ResponseWriteUtil {

    /**
     * 写入内容
     * @param response 请求返回
     * @param contentType 类型
     * @param content 内容
     * @throws IOException
     */
    public static void write(HttpServletResponse response, HttpStatus status, String contentType, String content) throws IOException {
        response.setStatus(status.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(contentType + "; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(content);
        printWriter.flush();
    }
}
