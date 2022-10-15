package com.peertutor.TutorMgr.service.mapper;

import com.peertutor.TutorMgr.model.Tutor;
import com.peertutor.TutorMgr.service.dto.TutorDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link com.peertutor.TutorMgr.model.Tutor} and its DTO {@link TutorDTO}.
 */
@Mapper(componentModel = "spring")
public interface TutorMapper extends EntityMapper<TutorDTO, Tutor> {

    Tutor toEntity(TutorDTO accountDTO);

    TutorDTO toDto(Tutor account);

    default Tutor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tutor account = new Tutor();
        account.setId(id);
        return account;
    }
}
