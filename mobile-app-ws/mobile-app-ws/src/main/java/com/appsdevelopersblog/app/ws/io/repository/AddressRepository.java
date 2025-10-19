package com.appsdevelopersblog.app.ws.io.repository;

import com.appsdevelopersblog.app.ws.io.entity.AddressEntity;
import com.appsdevelopersblog.app.ws.io.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {

    //"-UserDetails" from findAllByUserDetails custom crud operation name comes from userDetails variable from AddressEntity.
    //When you create custom crud method, follow this rule!!. UserRepository interface also follows this rule as well.
    List<AddressEntity> findAllByUserDetails(UserEntity userEntity);
    //AddressEntity has an attribute called "addressId".
    AddressEntity findByAddressId(String userId);
}
