package com.nexa.business.service;
import com.nexa.business.domain.Activity;
import com.nexa.business.repository.ActivityRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
@Service
public class AuditService {
 private final ActivityRepository repo;
 public AuditService(ActivityRepository r){repo=r;}
 @Transactional public Activity log(String actor,String action,String entity,String detail){Activity a=new Activity();a.setActor(actor==null?"system":actor);a.setAction(action);a.setEntity(entity);a.setDetail(detail);a.setCreatedAt(Instant.now());return repo.save(a);}
 @Transactional(readOnly=true) public Page<Activity> latest(int size){return repo.findAllByOrderByCreatedAtDesc(PageRequest.of(0,Math.min(Math.max(size,1),200)));}
}
