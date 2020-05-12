package edache.joe.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmailAndEmailIsNotNullOrPhoneNumberAndPhoneNumberIsNotNull(String email,
                                                                               String phoneNo);
    User findByEmailOrPhoneNumber(String email, String phoneNo);
}
