package quartez.repository;
import quartez.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * created by Atiye Mousavi
 * Date: 8/8/2021
 * Time: 12:50 PM
 */

@Repository
public interface MemberRepository extends JpaRepository<Member , Long> , JpaSpecificationExecutor {}
