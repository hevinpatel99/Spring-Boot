package com.userrole.serviceImpl;

import com.userrole.entity.RoleEntity;
import com.userrole.entity.UrlEntity;
import com.userrole.entity.UserEntity;
import com.userrole.mappings.UrlRoleMapping;
import com.userrole.mappings.UserRoleMapping;
import com.userrole.repository.RoleRepository;
import com.userrole.repository.UrlRepository;
import com.userrole.repository.UrlRoleRepository;
import com.userrole.requestDto.UrlRoleRequestDto;
import com.userrole.requestDto.UserRoleRequestDto;
import com.userrole.responseDto.ApiResponseDto;
import com.userrole.service.UrlRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *  @author Hevin Mulani
 * Implementation of the UrlRoleService interface.
 */
@Service
@RequiredArgsConstructor
public class UrlRoleServiceImpl implements UrlRoleService {

    private final UrlRepository urlRepository;
    private final RoleRepository roleRepository;
    private final UrlRoleRepository urlRoleRepository;


    /**
     * Inert a url-role mapping.
     *
     * @param urlRoleRequestDto The DTO containing url id & role id to be inserted.
     * @return inserted new urlRoleMapping details with HttpStatus.CREATED.
     */
    @Override
    public ResponseEntity<ApiResponseDto> insertUrlRole(UrlRoleRequestDto urlRoleRequestDto) {
        if (urlRoleRequestDto != null) {

            // Create UrlRoleMapping instance.
            UrlRoleMapping urlRoleMapping = new UrlRoleMapping();

            // Get UrlEntity is find by urlId.
            Optional<UrlEntity> optionalUrlEntity = urlRepository.findById(urlRoleRequestDto.getUrlId());
            // Get RoleEntity is find by roleId.
            Optional<RoleEntity> optionalRoleEntity = roleRepository.findById(urlRoleRequestDto.getRoleId());

            // Set UrlEntity & RoleEntity in UrlRoleMapping.
            optionalUrlEntity.ifPresent(urlRoleMapping::setUrl);
            optionalRoleEntity.ifPresent(urlRoleMapping::setRole);

            // save urlRoleMapping in database.
            urlRoleMapping = urlRoleRepository.save(urlRoleMapping);

            //Return urlRoleMapping if successfully inserted with HttpStatus.CREATED.
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.CREATED, "Insert Url-Role Successfully", urlRoleMapping));

        }

        return null;
    }
}
