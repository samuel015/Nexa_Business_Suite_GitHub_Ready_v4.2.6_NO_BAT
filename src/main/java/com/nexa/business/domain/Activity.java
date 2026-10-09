package com.nexa.business.domain;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="activity")
public class Activity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, length=50) private String actor;
    @Column(nullable=false, length=50) private String action;
    @Column(nullable=false, length=50) private String entity;
    @Column(nullable=false, length=700) private String detail;
    @Column(name="created_at", nullable=false) private Instant createdAt = Instant.now();
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getActor(){return actor;} public void setActor(String v){actor=v;}
    public String getAction(){return action;} public void setAction(String v){action=v;}
    public String getEntity(){return entity;} public void setEntity(String v){entity=v;}
    public String getDetail(){return detail;} public void setDetail(String v){detail=v;}
    public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant v){createdAt=v;}
}
