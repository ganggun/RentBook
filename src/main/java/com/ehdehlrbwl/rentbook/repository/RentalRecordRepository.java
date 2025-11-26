package com.ehdehlrbwl.rentbook.repository;

import com.ehdehlrbwl.rentbook.entity.RentalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRecordRepository extends JpaRepository<RentalRecord, Long> {

    List<RentalRecord> findByUserIdAndReturnDateIsNull(Long userId);

    @Query("SELECT r FROM RentalRecord r " +
            "WHERE r.book.id = :bookId " +
            "AND r.user.username = :username " +
            "AND r.returnDate IS NULL")
    Optional<RentalRecord> findOngoingRental(@Param("bookId") Long bookId,
                                             @Param("username") String username);
}
