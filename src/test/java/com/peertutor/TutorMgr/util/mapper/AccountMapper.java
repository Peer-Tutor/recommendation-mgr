package com.peertutor.TutorMgr.util.mapper;

import com.peertutor.TutorMgr.util.dto.AccountDTO;
import com.peertutor.TutorMgr.util.model.Account;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface AccountMapper extends EntityMapper<AccountDTO, Account> {

    Account toEntity(AccountDTO accountDTO);

    AccountDTO toDto(Account account);

    default Account fromId(Long id) {
        if (id == null) {
            return null;
        }
        Account account = new Account();
        account.setId(id);
        return account;
    }
}
