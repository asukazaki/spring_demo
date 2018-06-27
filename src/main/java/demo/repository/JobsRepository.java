package demo.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import demo.entity.Job;
import demo.entity.JobPk;

@Repository
public interface JobsRepository extends JpaRepository<Job, JobPk>{

	@Query("SELECT j FROM Job j WHERE j.id = :id AND j.date LIKE :yearMonth%")
	public List<Job> findJobsPerMonth(@Param("id") int id, @Param("yearMonth") String yearMonth);
		
	// メソッド名のプレフィックスがfind...By, count...By,等はクエリが自動生成される
	// By の後ろにはAND, OR, BETWEEN, LIKE などが使える
	// これで↑と同じ (この場合は引数に％事前につけて）
	List<Job> findByIdAndDateLike(int id, String yearMonth);

	@Query("SELECT j FROM Job j WHERE j.id = :id")
	public List<Job> getAllById(@Param("id")int id);
	
//	List<Job> findById(JobPk key);
	
}
