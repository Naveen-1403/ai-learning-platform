package com.mca.learning_platform.repository;

import com.mca.learning_platform.model.RoadmapData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoadmapRepository extends JpaRepository<RoadmapData, Long> {
    // ஒரு மாணவர் உருவாக்கியதில் கடைசியாக உள்ள Roadmap-ஐ எடுப்பதற்கான கமாண்ட்
    RoadmapData findTopByStudentNameOrderByIdDesc(String studentName);
}