package io.github.bluething.playground.handson.springbooturlshortener.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.sql.Timestamp;

@Entity
@Table(name = "url")
@Getter
@EqualsAndHashCode
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

    public UrlEntity(Long id, String longUrl, String shortUrl) {
        this.id = id;
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    public UrlEntity() {

    }
}
