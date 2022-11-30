package com.example.demo.entity;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Course {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String thumbnail;
	
	@Column
	private String title;
	
	@Column
	private String content;
	
	@Column
	private Integer realprice;
	
	@Column
	private Integer saleprice;
	
	@Column
	private String instructor;
	
	@Column
	private String link;
	@Column
	private String skill;
	
	@Column
	private Double rating;
}
