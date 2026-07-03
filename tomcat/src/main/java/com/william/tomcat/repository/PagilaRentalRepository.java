package com.william.tomcat.repository;

import com.william.tomcat.entity.PagilaRentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagilaRentalRepository extends JpaRepository<PagilaRentalEntity, Integer> {
	@Query("SELECT r.inventoryId FROM PagilaRentalEntity r WHERE r.inventoryId IN " +
		   "(SELECT i.inventoryId FROM PagilaInventoryEntity i WHERE i.filmId = :filmId) " +
		   "GROUP BY r.inventoryId, r.rentalId")
	List<Integer> findRentalsByFilmId(Integer filmId);
}
