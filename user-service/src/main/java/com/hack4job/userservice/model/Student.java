package com.hack4job.userservice.model;

public class Student {
	private int Id;
	private String name;
	private int marks;
	public Student(int id, String name, int marks) {
		super();
		Id = id;
		this.name = name;
		this.marks = marks;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMarks() {
		return marks;
	}
	public void setMarks(int marks) {
		this.marks = marks;
	}
	@Override
	public String toString() {
		return "StudentController [Id=" + Id + ", name=" + name + ", marks=" + marks + "]";
	}
}
