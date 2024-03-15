package com.userservices.responseDto;


import com.userservices.entity.RatingEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponseDto {

    private Long id;
    private String name;
    private String userName;
    private String password;
    private boolean status;
    private List<RatingEntity> ratings = new ArrayList<>();

}
