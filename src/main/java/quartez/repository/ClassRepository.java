package quartez.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import quartez.domain.MemberClass;

import java.util.List;

/**
 * created by Atiye Mousavi
 * Date: 8/8/2021
 * Time: 2:15 PM
 */
//JpaRepository  دارای تمام متدهای اینترفیس های CrudRepository  وPagingAndSortingRepository است
//بصورت عمده توابع crud را ارایه میدهد
public interface ClassRepository extends JpaRepository<MemberClass,Long> , JpaSpecificationExecutor {


    List<MemberClass> findAllByNameContainsIgnoreCase(String searchString);
    //این متد در jpa-data هستند
}
