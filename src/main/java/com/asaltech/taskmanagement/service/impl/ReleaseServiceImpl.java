package com.asaltech.taskmanagement.service.impl;

import com.asaltech.taskmanagement.domain.Release;
import com.asaltech.taskmanagement.domain.User;
import com.asaltech.taskmanagement.repository.ReleaseRepository;
import com.asaltech.taskmanagement.security.AuthoritiesConstants;
import com.asaltech.taskmanagement.service.ReleaseService;
import com.asaltech.taskmanagement.service.UserService;
import com.asaltech.taskmanagement.service.dto.ReleaseDTO;
import com.asaltech.taskmanagement.service.dto.UserDTO;
import com.asaltech.taskmanagement.service.mapper.ReleaseMapper;
import com.asaltech.taskmanagement.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Release}.
 */
@Service
public class ReleaseServiceImpl implements ReleaseService {

    private final Logger log = LoggerFactory.getLogger(ReleaseServiceImpl.class);

    private final ReleaseRepository releaseRepository;

    private final ReleaseMapper releaseMapper;

    private final UserService userService;

    private final UserMapper userMapper;

    public ReleaseServiceImpl(ReleaseRepository releaseRepository, ReleaseMapper releaseMapper, UserService userService, UserMapper userMapper) {
        this.releaseRepository = releaseRepository;
        this.releaseMapper = releaseMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public ReleaseDTO save(ReleaseDTO releaseDTO) {
        log.debug("Request to save Release : {}", releaseDTO);
        Release release = releaseMapper.toEntity(releaseDTO);
        log.debug("Checking if a user is present : {}", userService.getUserWithAuthorities().isPresent());
        Optional<User> user = userService.getUserWithAuthorities();
        if (user.isPresent() && !release.getTeam().contains(user.get())) {
            log.debug("Adding current user to release property \"Team\". User : {}", user.get());
            release.addTeamMember(user.get());
        }
        release = releaseRepository.save(release);
        return releaseMapper.toDto(release);
    }

    @Override
    public List<ReleaseDTO> findAll() {
        log.debug("Request to get all Releases");
        return releaseRepository.findAllWithEagerRelationships().stream()
            .map(releaseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    public Page<ReleaseDTO> findAllWithEagerRelationships(Pageable pageable) {
        return releaseRepository.findAllWithEagerRelationships(pageable).map(releaseMapper::toDto);
    }

    @Override
    public Optional<ReleaseDTO> findOne(String id) {
        log.debug("Request to get Release : {}", id);
        return releaseRepository.findOneWithEagerRelationships(id)
            .map(releaseMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Release : {}", id);
        releaseRepository.deleteById(id);
    }

    @Override
    public List<ReleaseDTO> findAllByTeamContains(UserDTO userDTO) {
        return releaseMapper.toDto(releaseRepository.findAllByTeamContains(userMapper.userDTOToUser(userDTO)));
    }

    @Override
    public List<ReleaseDTO> findAuthorized() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN);
        if (userDetails.getAuthorities().contains(adminAuthority)) {
            return findAll();
        } else {
            return userService.getUserWithAuthorities()
                .map(user -> findAllByTeamContains(userMapper.userToUserDTO(user)))
                .orElse(new ArrayList<>());
        }
    }

    @Override
    public boolean isReleaseOwnerOrAdmin(ReleaseDTO release) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN);
        return userDetails.getAuthorities().contains(adminAuthority)
            || release.getCreatedBy().equalsIgnoreCase(userDetails.getUsername());
    }
}
