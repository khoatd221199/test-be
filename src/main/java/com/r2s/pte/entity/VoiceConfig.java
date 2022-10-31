package com.r2s.pte.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="VDT.VOICE_CONFIG")
public class VoiceConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "AUTH_CODE",length = 20)
    private String authCode;
    @Column(name = "VOICE_NAME",length = 50)
    private String voiceName;
    @Column(name = "VOICE_LANGUAGE",length = 50)
    private String voiceLanguage;
    @Column(name = "PITCH")
    private Integer pitch;
    @Column(name = "SPEED")
    private Integer speed;
    @Column(name = "Modified_Date",columnDefinition="datetime",nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;
    @Column(name = "Modified_By",nullable = false)
    private Long modifiedBy;
    @Column(name = "Created_Date",columnDefinition="datetime",nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @Column(name = "Created_By",nullable = false)
    private Long createdBy;
}
