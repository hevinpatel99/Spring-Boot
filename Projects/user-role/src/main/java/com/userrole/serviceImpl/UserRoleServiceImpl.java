package com.userrole.serviceImpl;

import com.userrole.entity.RoleEntity;
import com.userrole.entity.UserEntity;
import com.userrole.mappings.UserRoleMapping;
import com.userrole.repository.RoleRepository;
import com.userrole.repository.UserRepository;
import com.userrole.repository.UserRoleRepository;
import com.userrole.requestDto.UserRoleRequestDto;
import com.userrole.responseDto.ApiResponseDto;
import com.userrole.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 *  @author Hevin Mulani
 * Implementation of the UserRoleService interface.
 */
@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    /**
     * Inert a user-role mapping.
     *
     * @param userRoleRequestDto The DTO containing the userId & roleId to be inserted.
     * @return inserted new userRoleMapping details with HttpStatus.CREATED.
     */
    @Override
    public ResponseEntity<ApiResponseDto> insertUserRole(UserRoleRequestDto userRoleRequestDto) {
        if (userRoleRequestDto != null) {

            // Create UserRoleMapping instance.
            UserRoleMapping userRoleMapping = new UserRoleMapping();

            // Get UserEntity is find by userId.
            Optional<UserEntity> optionalUserEntity = userRepository.findById(userRoleRequestDto.getUserId());
            // Get RoleEntity is find by roleId.
            Optional<RoleEntity> optionalRoleEntity = roleRepository.findById(userRoleRequestDto.getRoleId());

            // Set UrlEntity & RoleEntity in UrlRoleMapping.
            optionalUserEntity.ifPresent(userRoleMapping::setUser);
            optionalRoleEntity.ifPresent(userRoleMapping::setRole);

            // save userRoleMapping in database.
            userRoleMapping = userRoleRepository.save(userRoleMapping);

            //Return userRoleMapping if successfully inserted with HttpStatus.CREATED.
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.CREATED, "Roles Fetched Successfully", userRoleMapping));


        }
        return null;
    }

}
