package com.userrole.requestDto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Hevin Mulani
 *
 * DTO class representing Url request containing urlName.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class UrlRequestDto {

    @NotEmpty(message = "Invalid : field is empty, please insert role name first !!")
    private String urlName;
//    private List<String> roles;
}
