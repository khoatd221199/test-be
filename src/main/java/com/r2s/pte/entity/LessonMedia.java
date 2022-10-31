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
@Table(name="VDT.LESSON_MEDIA")
public class LessonMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "TYPE", length = 10)
    private String type;
    @Column(name = "VOICE_NAME",length = 100)
    private  String voiceName;
    @Column(name = "LESSON_MEDIA_LINK",length = 500)
    private  String lessonMediaLink;
    @Column(name = "ZORDER")
    private Long order;
    @Column(name = "Modified_Date",columnDefinition="datetime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;
    @Column(name = "Modified_By")
    private Long modifiedBy;
    @Column(name = "Created_Date",columnDefinition="datetime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @Column(name = "Created_By")
    private Long createdBy;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "LESSON_ID")
    private  Lesson lesson;
}
