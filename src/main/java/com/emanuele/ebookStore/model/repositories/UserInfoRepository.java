package com.emanuele.ebookStore.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emanuele.ebookStore.model.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long>{

}
