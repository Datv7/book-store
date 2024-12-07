package com.example.bookstore.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.entity.Item;
@Repository
public interface ItemRepository extends JpaRepository<Item, String>{
//	@Query("select i from Item i join fetch i.categories where i.id=?1")
//	Optional<Item> getWithCategories(String id);
	boolean existsByTitle(String title);

	@Query("select i from Item i left join fetch i.categories where i.id=?1")
	Optional<Item> getItemWithCateById(String id);
	
	@Query("select i.id from Item i join i.categories c where c.id in ?1")
	List<String> findIdByCateId(List<Integer> categoryIds);
	
	@Transactional
	@Modifying
	@Query("update Item i set i.quantity=?3,i.updateAt=?1 where i.id in ?2")
	void changeDeleteByListId(Date updateAt,List<String> id,int quantity);
}
