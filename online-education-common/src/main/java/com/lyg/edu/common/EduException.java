package com.lyg.edu.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**自定义异常类
 * @author lyg
 * @create 2020-03-04-14:38
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduException extends RuntimeException {

    private Integer code;
    private String message;
    @Override
    public String toString() {
        return "EduException{" +
                "message=" + this.getMessage() +
                ", code=" + code +
                '}';
    }
}