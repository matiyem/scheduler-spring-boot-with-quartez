package quartez.specification;

import org.springframework.data.jpa.domain.Specification;

/**
 * created by Atiye Mousavi
 * Date: 8/8/2021
 * Time: 1:36 PM
 */

public abstract class BaseSpecification<T, U> {
    public abstract Specification<T> getFilter(U request);
}
