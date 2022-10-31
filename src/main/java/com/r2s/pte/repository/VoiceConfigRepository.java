package com.r2s.pte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.r2s.pte.entity.VoiceConfig;
@Repository
public interface VoiceConfigRepository extends JpaRepository<VoiceConfig, Long>{

}
