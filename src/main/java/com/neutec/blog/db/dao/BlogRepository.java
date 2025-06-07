package com.neutec.blog.db.dao;

import com.neutec.blog.db.entity.Blog;
import com.neutec.blog.db.entity.GenerateEntity;
import com.neutec.blog.model.blog.BlogDTO;
import com.neutec.blog.model.blog.IBlogCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BlogRepository extends SimpleJpaRepository<Blog, Long> {

    private final EntityManager em;


    public BlogRepository(@Qualifier("mysqlEntityManager") EntityManager em) {
        super(Blog.class, em);
        this.em = em;
    }

    /**
     * 根據用戶ID和是否已刪除的標誌查找Blog列表
     */
    public List<Blog> findByUserIdDeleted(Long userId, Boolean isDeleted) {
        return findAll((Specification<Blog>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            predicateList.add(criteriaBuilder.equal(root.get(Blog.Fields.userId), userId));
            if (isDeleted != null) {
                predicateList.add(criteriaBuilder.equal(root.get(Blog.Fields.deleted), isDeleted));
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        });
    }

    /**
     * 根據用戶ID查找Blog列表，不考慮是否已刪除
     */
    public List<Blog> findByUserId(Long userId) {
        return findByUserIdDeleted(userId, null);
    }

    /**
     * 根據條件計算Blog數量
     */

    public long count(IBlogCriteria criteria) {
        StringBuilder sql = new StringBuilder("SELECT count(*) " +
            "FROM blog b " +
            "JOIN users u ON b.userId = u.id " +
            "WHERE 1=1 ");
        Map<String, Object> params = new HashMap<>();
        prepareQuery(criteria, params, sql);
        Query query = em.createNativeQuery(sql.toString(), Long.class);
        params.forEach(query::setParameter);
        return (long) query.getSingleResult();
    }

    /**
     * 根據條件查找Blog列表
     */
    public List<Blog> find(IBlogCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Blog> cq = cb.createQuery(Blog.class);
        Root<Blog> root = cq.from(Blog.class);
        prepareQuery(root, cq, cb, criteria);

        if (criteria.getSkip() != null && criteria.getLimit() != null) {
            return em.createQuery(cq)
                .setFirstResult(criteria.getSkip())
                .setMaxResults(criteria.getLimit())
                .getResultList();
        } else {
            return em.createQuery(cq).getResultList();
        }
    }


    /**
     * 根據條件查找Blog列表
     */
    public List<BlogDTO> findDTO(IBlogCriteria criteria) {
        StringBuilder sql = new StringBuilder("SELECT b.id, b.title, b.content, b.tags, b.status, b.createDate, b.updateDate, u.name AS author " +
            "FROM blog b " +
            "JOIN users u ON b.userId = u.id " +
            "WHERE 1=1 ");
        Map<String, Object> params = new HashMap<>();
        prepareQuery(criteria, params, sql);
        Query query = em.createNativeQuery(sql.toString(), BlogDTO.class);

        params.forEach(query::setParameter);
        if (criteria.getSkip() != null && criteria.getLimit() != null) {
            return query
                .setFirstResult(criteria.getSkip())
                .setMaxResults(criteria.getLimit())
                .getResultList();
        } else {
            return query.getResultList();
        }
    }

    private void prepareQuery(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb, IBlogCriteria criteria) {
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get(Blog.Fields.id), criteria.getId()));
        }
        if (criteria.getAuthorId() != null) {
            predicates.add(cb.equal(root.get(Blog.Fields.userId), criteria.getAuthorId()));
        }
        if (criteria.getTitle() != null) {
            predicates.add(cb.like(root.get(Blog.Fields.title), "%" + criteria.getTitle() + "%"));
        }
        if (Objects.equals(Boolean.FALSE, criteria.getContainDeleted())) {
            predicates.add(cb.equal(root.get(Blog.Fields.deleted), criteria.getContainDeleted()));
        }
        if (criteria.getStatus() != null) {
            predicates.add(cb.equal(root.get(Blog.Fields.status), criteria.getStatus()));
        }
        if (criteria.getOrderBy() != null) {
            if (criteria.getSort() == null || criteria.getSort() == com.neutec.blog.enums.Sort.ASC) {
                cq.orderBy(cb.asc(root.get(criteria.getOrderBy().getOrderByFieldName())));
            } else {
                cq.orderBy(cb.desc(root.get(criteria.getOrderBy().getOrderByFieldName())));
            }
        }
        cq.where(predicates.toArray(new Predicate[0]));
    }

    private void prepareQuery(IBlogCriteria criteria, Map<String, Object> params, StringBuilder sql) {
        if (criteria.getId() != null) {
            params.put(Blog.Fields.id, criteria.getId());
            sql.append(" AND b.id = :" + Blog.Fields.id);
        }


        if (criteria.getAuthorId() != null) {
            params.put(Blog.Fields.userId, criteria.getAuthorId());
            sql.append(" AND b.userId = :" + Blog.Fields.userId);
        }
        if (criteria.getTitle() != null && !criteria.getTitle().trim().isEmpty()) {
            params.put(Blog.Fields.title, "%" + criteria.getTitle().trim() + "%");
            sql.append(" AND b.title LIKE :" + Blog.Fields.title);
        }
        if (Objects.equals(Boolean.FALSE, criteria.getContainDeleted())) {
            params.put(Blog.Fields.deleted, criteria.getContainDeleted());
            sql.append(" AND b.deleted = :" + Blog.Fields.deleted);
        }
        if (criteria.getStatus() != null) {
            params.put(Blog.Fields.status, criteria.getStatus().name());
            sql.append(" AND b.status = :" + Blog.Fields.status);
        }

        if (criteria.getAuthorName() != null && !criteria.getAuthorName().trim().isEmpty()) {
            params.put("authorName", "%" + criteria.getAuthorName().trim() + "%");
            sql.append(" AND u.name LIKE :authorName");

        }
        if (criteria.getDateFrom() != null) {
            params.put(GenerateEntity.Fields.createDate, criteria.getDateFrom());
            sql.append(" AND b.createDate >= :" + GenerateEntity.Fields.createDate);
        }
        if (criteria.getDateTo() != null) {
            params.put(GenerateEntity.Fields.updateDate, criteria.getDateTo());
            sql.append(" AND b.updateDate < :" + GenerateEntity.Fields.updateDate);
        }

        if (criteria.getOrderBy() != null) {
            // 處理排序
            if (criteria.getSort() == null || criteria.getSort() == com.neutec.blog.enums.Sort.ASC) {
                sql.append(" ORDER BY b." + criteria.getOrderBy().getOrderByFieldName() + " ASC");
            } else {
                sql.append(" ORDER BY b." + criteria.getOrderBy().getOrderByFieldName() + " DESC");
            }
        }
    }


}
