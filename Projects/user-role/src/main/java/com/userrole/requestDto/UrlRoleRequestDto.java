package com.userrole.requestDto;

import lombok.*;

import javax.validation.constraints.NotNull;


/**
 *
 * @author Hevin Mulani
 * DTO class representing Url-role request containing url id & role id.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class UrlRoleRequestDto {


    @NotNull
    Long urlId;

    @NotNull
    Long roleId;
}
