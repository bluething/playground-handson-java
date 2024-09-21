package io.github.bluething.playground.handson.springbooturlshortener.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
    @Query(value = "SELECT nextval('sequence_url')", nativeQuery = true)
    Long getNextSequenceValue();
}
