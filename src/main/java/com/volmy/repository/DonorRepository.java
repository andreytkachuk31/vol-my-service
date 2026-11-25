package com.volmy.repository;

import com.volmy.model.Donor;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {

    List<Donor> findByFundraisingId(Long fundraisingId);

    @Transactional
    @Query("DELETE FROM Donor donor WHERE donor.id IN :ids")
    void deleteDonorsByIds(List<Long> ids);
}
