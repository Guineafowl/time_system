package time.system.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import time.system.persistence.TimeStamp;

import java.util.List;

@Repository
public interface TimeStampRepository extends CrudRepository<TimeStamp, Long> {

    @Query("select count(timestamp) > 0 from TimeStamp timestamp where timestamp.stampOut is null")
    boolean stampedIn();

    @Query("select timestamp from TimeStamp timestamp where timestamp.stampOut is null")
    TimeStamp lastStampIn();

    @Query(value = "SELECT CASE " +
            "WHEN TIME(stamp_in) < '06:00:00' THEN DATE_SUB(DATE(stamp_in), INTERVAL 1 DAY) " +
            "ELSE DATE(stamp_in) " +
            "END AS workday, SUM(TIME_TO_SEC(TIMEDIFF(stamp_out, stamp_in))) AS seconds " +
            "FROM time_stamp " +
            "GROUP BY workday", nativeQuery = true)
    List<TimePerDay> timePerDay();

}
