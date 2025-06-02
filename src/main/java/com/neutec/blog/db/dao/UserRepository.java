package com.neutec.blog.db.dao;

import com.neutec.blog.db.entity.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends SimpleJpaRepository<User, Long> {

    private final EntityManager em;


    public UserRepository(@Qualifier("mysqlEntityManager") EntityManager em) {
        super(User.class, em);
        this.em = em;
    }

    public User findByEmail(String email) {
        return findAll((Specification<User>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User.Fields.email), email))
            .stream()
            .findFirst()
            .orElse(null);
    }
}
