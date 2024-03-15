package com.userrole.responseDto;


import lombok.Data;
import java.util.Set;

/**
 *
 * @author Hevin Mulani
 * response dto class for url responses.
 */

@Data
public class UrlResponseDto {

    private String urlName;
    private Set<String> roles;
}
