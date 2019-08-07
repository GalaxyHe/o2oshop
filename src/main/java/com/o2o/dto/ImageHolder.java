package com.o2o.dto;

import lombok.*;

import java.io.InputStream;

/**
 * @author He
 * @Date 2019/8/1
 * @Time 9:55
 * @Description TODO
 **/

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImageHolder {
    private String imageName;
    private InputStream image;
}
