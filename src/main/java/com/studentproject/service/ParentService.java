package com.studentproject.service;

import com.studentproject.dto.parent.LinkParentStudentRequest;
import com.studentproject.dto.parent.ParentProgressResponse;
import com.studentproject.dto.parent.ParentStudentResponse;

import java.util.List;

public interface ParentService {
    void linkParentToStudent(LinkParentStudentRequest request);
    List<ParentStudentResponse> getMyStudents();
    ParentProgressResponse getStudentProgress(Long studentId);
}
