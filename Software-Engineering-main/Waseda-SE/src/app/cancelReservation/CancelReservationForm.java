/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package app.cancelReservation;

import app.AppException;

/**
 * Form class for Check-in Customer
 * 
 */
public class CancelReservationForm {

	private CancelReservationControl cancelReservationControl = new CancelReservationControl();

	private CancelReservationControl getCancelReservationControl() {
		return cancelReservationControl;
	}

	private String reservationNumber;

	public void cancel() throws AppException {
		CancelReservationControl cancelReservationControl = getCancelReservationControl();
		cancelReservationControl.cancelReservation(reservationNumber);
	}

    public void setReservationNumber(String reservationNumber) {
		this.reservationNumber = reservationNumber;
	}
}
