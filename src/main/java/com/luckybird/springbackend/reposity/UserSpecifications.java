package com.luckybird.springbackend.reposity;

import com.luckybird.springbackend.api.req.UserQueryReq;
import com.luckybird.springbackend.po.UserPO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 新云鸟
 */
public class UserSpecifications {
    public static Specification<UserPO> queryByReq(UserQueryReq req) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(req.getKeyword())) {
                // TODO: 防注入
                Predicate accountPredicate = cb.like(root.get("account"), "%" + req.getKeyword() + "%");
                Predicate usernamePredicate = cb.like(root.get("username"), "%" + req.getKeyword() + "%");
                Predicate telephonePredicate = cb.like(root.get("telephone"), "%" + req.getKeyword() + "%");
                Predicate emailPredicate = cb.like(root.get("email"), "%" + req.getKeyword() + "%");
                Predicate remarkPredicate = cb.like(root.get("remark"), "%" + req.getKeyword() + "%");
                predicates.add(cb.or(accountPredicate, usernamePredicate, telephonePredicate, emailPredicate, remarkPredicate));
            }
            if (req.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), req.getStatus()));
            }
            if (req.getOrganizationId() != null){
                predicates.add(cb.equal(root.get("organizationId"), req.getOrganizationId()));
            }
            if (req.getDepartmentId() != null){
                predicates.add(cb.equal(root.get("departmentId"), req.getDepartmentId()));
            }
            if (StringUtils.hasText(req.getOccupation())){
                // TODO: 用JSON方法模糊查询
                predicates.add(cb.like(root.get("occupation"), "%" + req.getOccupation() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
