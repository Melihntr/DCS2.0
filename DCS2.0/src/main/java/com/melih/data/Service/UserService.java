package com.melih.data.Service;

import com.melih.data.DTO.UserDto;
import com.melih.data.Entity.User;
import com.melih.data.Repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public User createUser(String username) {

        User user = new User();
        user.setUsername(username);

        return userRepository.save(user);
    }

    public List<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Criteria Query
    public List<User> criteriaSearch(String username) {

        CriteriaBuilder cb =
                entityManager.getCriteriaBuilder();

        CriteriaQuery<User> cq =
                cb.createQuery(User.class);

        Root<User> root = cq.from(User.class);

        cq.select(root)
          .where(cb.equal(root.get("username"), username));

        return entityManager
                .createQuery(cq)
                .getResultList();
    }

    // DTO Projection
    public List<UserDto> getDtos() {

        return userRepository.findAll()
                .stream()
                .map(u -> new UserDto(
                        u.getId(),
                        u.getUsername()
                ))
                .toList();
    }
}
