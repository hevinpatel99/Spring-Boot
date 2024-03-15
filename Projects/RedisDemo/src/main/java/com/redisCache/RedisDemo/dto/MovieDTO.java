package com.redisCache.RedisDemo.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Builder
public class MovieDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Invalid : Movie name is empty")
    @Size(min = 3, max = 30, message = "Invalid : Must be of 3 - 30 characters")
    private String movie;

    @NotEmpty(message = "Invalid : Actor name is empty")
    @Size(min = 3, max = 30, message = "Invalid : Must be of 3 - 30 characters")
    private String actor;

    @NotEmpty(message = "Invalid : Actress name is empty")
    @Size(min = 3, max = 30, message = "Invalid : Must be of 3 - 30 characters")
    private String actress;

    @NotNull(message = "Invalid : release year is empty")
    private int releaseYear;

    private boolean status;
    private Date createdAt;
    private Date updatedAt;


}
