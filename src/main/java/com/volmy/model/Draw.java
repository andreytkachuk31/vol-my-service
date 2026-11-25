package com.volmy.model;

import javax.persistence.*;
import java.sql.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "draws")
public class Draw {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	private Fundraising fundraising;

	@ManyToOne(optional = false)
	private Donor winner;

	@Column(name = "status")
	private String status;

	@Column(name = "prize")
	private String prize;

	@Column(name = "draw_date")
	private Date drawDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Fundraising getFundraising() {
		return fundraising;
	}

	public void setFundraising(Fundraising fundraising) {
		this.fundraising = fundraising;
	}

	public Donor getWinner() {
		return winner;
	}

	public void setWinner(Donor winner) {
		this.winner = winner;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public Date getDrawDate() {
		return drawDate;
	}

	public void setDrawDate(Date drawDate) {
		this.drawDate = drawDate;
	}
}
