package demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import demo.entity.Holiday;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Integer>{
	
	@Query("SELECT h FROM Holiday h WHERE h.month = :month")
	public List<Holiday> findByMonth(@Param("month")String month);

}
