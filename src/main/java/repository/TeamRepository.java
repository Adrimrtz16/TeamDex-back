package repository;

import entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    boolean existsByIdAndUserId(Long teamId, Long userId);
    List<Team> findTop15ByOrderByIdDesc();
}