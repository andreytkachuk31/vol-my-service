package com.volmy.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "donors")
public class Donor {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Column(name = "sum")
	private Double sum;

	@ManyToOne(optional = false)
	private Fundraising fundraising;

	public Long  getId() {
		return id;
	}

	public void setId(Long  id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public Fundraising getFundraising() {
		return fundraising;
	}

	public void setFundraising(Fundraising fundraising) {
		this.fundraising = fundraising;
	}
}
