package com.vpark.vparkservice.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by kalana.w on 5/17/2020.
 */
@Data
@MappedSuperclass
public abstract class Savable implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, updatable = false)
	private long id;

	@Column(name = "CREATED_DATE", nullable = false, updatable = false)
	@CreatedDate
	private LocalDateTime createdDate = LocalDateTime.now();

	@Column(name = "MODIFIED_DATE", nullable = false)
	@LastModifiedDate
	private LocalDateTime modifiedDate = LocalDateTime.now();

}
