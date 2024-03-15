package com.userrole.serviceImpl;

import com.userrole.entity.RoleEntity;
import com.userrole.entity.UrlEntity;
import com.userrole.mappings.UrlRoleMapping;
import com.userrole.repository.RoleRepository;
import com.userrole.repository.UrlRepository;
import com.userrole.repository.UrlRoleRepository;
import com.userrole.requestDto.UrlRequestDto;
import com.userrole.requestDto.UrlRoleRequestDto;
import com.userrole.responseDto.ApiResponseDto;
import com.userrole.responseDto.UrlResponseDto;
import com.userrole.responseDto.UserResponseDto;
import com.userrole.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.*;
import java.util.stream.Collectors;


/**
 *  @author Hevin Mulani
 * Implementation of the UrlService interface.
 */
@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final UrlRoleRepository urlRoleRepository;
    private final ModelMapper modelMapper;


    /**
     * Inserts a new URL.
     *
     * @param urlRequestDto The DTO containing the URL to be inserted.
     * @return inserted Url details with HttpStatus.CREATED.
     */
    @Override
    public ResponseEntity<ApiResponseDto> insertUrl(UrlRequestDto urlRequestDto) {

        //save URl
        UrlEntity urlEntity = saveUrl(urlRequestDto);
        urlEntity = this.urlRepository.save(urlEntity);

       /* Set<RoleEntity> roleEntities = new HashSet<>();
        for (String roleName : urlRequestDto.getRoles()) {
            if (roleName != null && !roleName.isEmpty()) {
                Optional<RoleEntity> byRole = this.roleRepository.findByRoleNameIgnoreCase(roleName);
                byRole.ifPresent(roleEntities::add);
            } else {
                RoleEntity roleEntity = new RoleEntity();
                roleEntity.setRoleName(roleName);
                RoleEntity roleEntity1 = this.roleRepository.save(roleEntity);
                roleEntities.add(roleEntity1);
            }
        }

        UrlEntity finalUrlEntity = urlEntity;
        roleEntities.forEach((roleEntity) -> {
            UrlRoleMapping urlRoleMapping = new UrlRoleMapping();
            urlRoleMapping.setUrl(finalUrlEntity);
            urlRoleMapping.setRole(roleEntity);
            this.urlRoleRepository.save(urlRoleMapping);
        });

        List<UrlRoleMapping> urlRoleMappings = this.urlRoleRepository.findByUrl(urlEntity);
        UrlResponseDto result = this.modelMapper.map(urlEntity, UrlResponseDto.class);
        result.setRoles(urlRoleMappings.stream().map((p) -> p.getRole().getRoleName()).collect(Collectors.toSet()));*/


        //return inserted UrlEntity with HttpStatus.CREATED.
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.CREATED, "URL Inserted Successfully", urlEntity));
    }


    /**
     * Retrieves all URLs along with their associated roles.
     *
     * @return list of urlEntity with HttpStatus.OK.
     */
    @Override
    public ResponseEntity<ApiResponseDto> getAllUrls() {
        //retrieve all Urls.
        List<UrlEntity> allUrls = urlRepository.findAll();
        System.out.println("All URLs : " + allUrls);
        if (!allUrls.isEmpty()) {

            List<UrlResponseDto> urlResponseDtoList = new ArrayList<>();
            // Iterate through each URL entity
            for (UrlEntity urlEntity : allUrls) {
                List<UrlRoleMapping> urlRoleMapping = urlRoleRepository.findByUrl(urlEntity);
                // Map URL entity to response DTO and set associated roles
                UrlResponseDto result = modelMapper.map(urlEntity, UrlResponseDto.class);
                result.setRoles(urlRoleMapping.stream().map((p) -> p.getRole().getRoleName()).collect(Collectors.toSet()));
                urlResponseDtoList.add(result);
            }

            // Return list of URLs along with their associated roles.
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, "User Get Successfully", urlResponseDtoList));
        }

        // Return empty collection if url not found.
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.NOT_FOUND, "Users not found", Collections.EMPTY_LIST));
    }


    /**
     * Save a new UrlEntity.
     *
     * @param urlRequestDto The DTO containing the URL to be saved.
     * @return saved UrlEntity object.
     */
    private UrlEntity saveUrl(UrlRequestDto urlRequestDto) {
        // Create and return a new UrlEntity with the urlName set from the urlRequestDto.
        return UrlEntity.builder().urlName(urlRequestDto.getUrlName()).build();
    }
}
