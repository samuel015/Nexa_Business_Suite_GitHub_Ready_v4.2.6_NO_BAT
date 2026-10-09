package com.nexa.business.domain;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "app_user")
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true, length=50) private String username;
    @Column(name="password_hash", nullable=false, length=100) private String passwordHash;
    @Column(name="display_name", nullable=false, length=100) private String displayName;
    @Column(name="job_title", nullable=false, length=120) private String jobTitle;
    @Column(nullable=false, length=40) private String role;
    @Column(nullable=false) private boolean enabled = true;
    @Column(name="created_at", nullable=false) private Instant createdAt = Instant.now();
    @Column(name="updated_at", nullable=false) private Instant updatedAt = Instant.now();

    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getUsername(){return username;} public void setUsername(String v){username=v;}
    public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
    public String getDisplayName(){return displayName;} public void setDisplayName(String v){displayName=v;}
    public String getJobTitle(){return jobTitle;} public void setJobTitle(String v){jobTitle=v;}
    public String getRole(){return role;} public void setRole(String v){role=v;}
    public boolean isEnabled(){return enabled;} public void setEnabled(boolean v){enabled=v;}
    public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant v){createdAt=v;}
    public Instant getUpdatedAt(){return updatedAt;} public void setUpdatedAt(Instant v){updatedAt=v;}
}
