package com.simplane.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class ImgPathVO {
    private Long imgid;
    private Long boardid;
    private String imagePath;

}