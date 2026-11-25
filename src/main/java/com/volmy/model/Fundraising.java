package com.volmy.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "fundraisings")
public class Fundraising {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "image")
	private String image;

	@Column(name = "tag")
	private String tag;

	@Column(name = "status")
	private String status;

	@Column(name = "progress_percent")
	private Integer progressPercent;

	@Column(name = "current_sum")
	private Double currentSum;

	@Column(name = "total_sum")
	private Double totalSum;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@OneToMany(mappedBy = "fundraising", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Donor> donors = new ArrayList<>();

	@OneToMany(mappedBy = "fundraising", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Draw> draws = new ArrayList<>();

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getProgressPercent() {
		return progressPercent;
	}

	public void setProgressPercent(Integer progressPercent) {
		this.progressPercent = progressPercent;
	}

	public Double getCurrentSum() {
		return currentSum;
	}

	public void setCurrentSum(Double currentSum) {
		this.currentSum = currentSum;
	}

	public Double getTotalSum() {
		return totalSum;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setTotalSum(Double totalSum) {
		this.totalSum = totalSum;
	}

	public void addDonor(Donor donor) {
		donors.add(donor);
		donor.setFundraising(this);
	}

	public List<Draw> getDraws() {
		return draws;
	}
}
