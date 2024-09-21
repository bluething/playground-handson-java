package io.github.bluething.playground.handson.springbooturlshortener.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.sql.Timestamp;

@Entity
@Table(name = "url")
@Getter
public class UrlEntity {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "long_url")
    private String longUrl;
    @Column(name = "short_url")
    private String shortUrl;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
