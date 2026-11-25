package com.volmy.repository;

import com.volmy.model.Fundraising;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FundraisingRepository extends JpaRepository<Fundraising, Long> {

    @Transactional
    @Query("DELETE FROM Fundraising fundraising WHERE fundraising.id IN :ids")
    void deleteFundraisingsByIds(List<Long> ids);
}
