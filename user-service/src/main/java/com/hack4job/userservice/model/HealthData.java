package com.hack4job.userservice.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "Health-Data")
public class HealthData {
	@Id
	private String username;
	private int heartRate;
	private double sleepHours;
	private int caloriesBurned;
	private int steps;
	private int systolicBloodPressure;
	private int diastolicBloodPressure;
	private List<CustomField> customFields;

}
