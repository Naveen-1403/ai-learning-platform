package com.mca.learning_platform.model;

import jakarta.persistence.*;

@Entity
public class RoadmapData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String studentName;
    private String topic;
    
    @Column(columnDefinition = "LONGTEXT") // Roadmap பெரிய text என்பதால் LONGTEXT
    private String content;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}