package pt.bayonne.sensei.decision.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.bayonne.sensei.decision.domain.Decision;

@Repository
public interface DecisionRepository extends JpaRepository<Decision,Long> {
}
