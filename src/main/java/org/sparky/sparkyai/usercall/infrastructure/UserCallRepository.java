package org.sparky.sparkyai.usercall.infrastructure;

import java.util.List;

import org.sparky.sparkyai.usercall.domain.UserCall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCallRepository extends JpaRepository<UserCall, Long> {

    List<UserCall> findByCompanyId(Long companyId);

}
