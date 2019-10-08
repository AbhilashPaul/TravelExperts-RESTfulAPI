package models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the bookings database table.
 * 
 */
@Entity
@Table(name="bookings")
@NamedQuery(name="Booking.findAll", query="SELECT b FROM Booking b")
public class Booking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int bookingId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date bookingDate;

	private String bookingNo;

	private int customerId;

	private int packageId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date passengerDOB;

	private String passengerFName;

	private String passengerLName;

	private String passengerMName;

	private double travelerCount;

	private String tripTypeId;

	public Booking() {
	}

	public int getBookingId() {
		return this.bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public Date getBookingDate() {
		return this.bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getBookingNo() {
		return this.bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}

	public int getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getPackageId() {
		return this.packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	public Date getPassengerDOB() {
		return this.passengerDOB;
	}

	public void setPassengerDOB(Date passengerDOB) {
		this.passengerDOB = passengerDOB;
	}

	public String getPassengerFName() {
		return this.passengerFName;
	}

	public void setPassengerFName(String passengerFName) {
		this.passengerFName = passengerFName;
	}

	public String getPassengerLName() {
		return this.passengerLName;
	}

	public void setPassengerLName(String passengerLName) {
		this.passengerLName = passengerLName;
	}

	public String getPassengerMName() {
		return this.passengerMName;
	}

	public void setPassengerMName(String passengerMName) {
		this.passengerMName = passengerMName;
	}

	public double getTravelerCount() {
		return this.travelerCount;
	}

	public void setTravelerCount(double travelerCount) {
		this.travelerCount = travelerCount;
	}

	public String getTripTypeId() {
		return this.tripTypeId;
	}

	public void setTripTypeId(String tripTypeId) {
		this.tripTypeId = tripTypeId;
	}

}