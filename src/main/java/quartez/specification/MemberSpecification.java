package quartez.specification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import quartez.domain.Member;
import quartez.domain.MemberClass;
import quartez.repository.ClassRepository;
import quartez.web.model.FilterRequest;
import org.springframework.util.CollectionUtils;


import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.SetJoin;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

/**
 * created by Atiye Mousavi
 * Date: 8/8/2021
 * Time: 1:36 PM
 */

@Component
public class MemberSpecification extends BaseSpecification<Member, FilterRequest> {
    @Autowired
    ClassRepository classRepository;

    @Override
    public Specification<Member> getFilter(FilterRequest request) {
        return (root, query, cb) -> {
            query.distinct(true);
            query.orderBy(cb.asc(root.get("lastName")));
            return where(isActive(request.getActive())
                    .and(inZipCode(request.getZipFilter())))
                    .toPredicate(root, query, cb);
        };
    }

    public Specification<Member> hasString(String searchString) {
        return (root, query, cb) -> {
            query.distinct(true);
            if (searchString != null) {
                return cb.like(cb.lower(root.get("interests")), cb.lower(cb.literal("%" + searchString + "%")));
            } else {
                return null;
            }
        };
    }

    public Specification<Member> hasClasses(String searchString) {
        return (root, query, cb) -> {
            query.distinct(true);
            if (searchString != null) {
                List<MemberClass> memberClasses = classRepository.findAllByNameContainsIgnoreCase(searchString);

                if (!CollectionUtils.isEmpty(memberClasses)) {
                    SetJoin<Member, MemberClass> masterClassJoin = root.joinSet("memberClasses", JoinType.LEFT);
                    List<Predicate> predicates = new ArrayList<>();
                    predicates.add(masterClassJoin.in(new HashSet<>(memberClasses)));
                    Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
                    return cb.or(p);
                }
            }

            return null;
        };
    }

    private Specification<Member> isActive(Boolean isActive) {
        return (root, query, cb) -> {
            if (isActive != null) {
                return cb.equal(root.get("active"), isActive);
            } else {
                return null;
            }
        };
    }

    private Specification<Member> inZipCode(String zipFilter) {
        return (root, query, cb) -> {
            if (zipFilter != null) {
                return cb.like(root.get("zipCode"), cb.literal(zipFilter + "%"));
            } else {
                return null;
            }
        };
    }
}