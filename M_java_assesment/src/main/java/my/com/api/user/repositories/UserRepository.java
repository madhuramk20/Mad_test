package my.com.api.user.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import my.com.api.user.model.UserData;

@Repository
public interface UserRepository extends CrudRepository<UserData, String> {

}
