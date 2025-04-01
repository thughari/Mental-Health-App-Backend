package com.hack4job.userservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hack4job.userservice.model.Student;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/auth")
public class StudentController {
	private List<Student> students = new ArrayList<Student>(List.of(new Student(1, "hari", 99), new Student(2,"siva",100)));
	
	@GetMapping("/students")
	public List<Student> getStudents(){
		return students;
	}
	
	@GetMapping("/crf-token")
	public CsrfToken getCsrfToken(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("_csrf");
	}
	
	@PostMapping("/students")
	public Student addStudent(@RequestBody Student student) {
		students.add(student);		
		return student;
	}
	
	
}
