package com.clark.deduplistings.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO {
    private Integer code;
    private String msg;
    private Object object;

    public static ResultVO success(String message, Object object){
        return new ResultVO(200, message, object);
    }

    public static ResultVO error(String message){
        return new ResultVO(500, message, null);
    }

}
