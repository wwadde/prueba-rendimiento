package com.william.webflux.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("crime_data_from_2020_to_2024")
public class CrimeDataEntity {
	@Id
	@Column("dr_no")
	private Long drNo;
}
