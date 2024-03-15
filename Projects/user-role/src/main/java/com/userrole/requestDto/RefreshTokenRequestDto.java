package com.userrole.requestDto;

import lombok.*;

/**
 * @author Hevin Mulani
 * DTO class representing Refresh token request containing token.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RefreshTokenRequestDto {
    private String requestToken;
}
