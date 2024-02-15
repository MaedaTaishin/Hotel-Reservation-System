/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package app.checkin;

import java.util.Date;

import app.AppException;
import app.ManagerFactory;
import domain.payment.PaymentManager;
import domain.payment.PaymentException;
import domain.reservation.ReservationManager;
import domain.reservation.ReservationException;
import domain.room.RoomManager;
import domain.room.RoomException;

/**
 * Control class for Check-in Customer
 * 
 */
public class CheckInRoomControl {

	public String checkIn(String reservationNumber) throws AppException {
		try {
			//Retrieve date and room type
			ReservationManager reservationManager = getReservationManager();
			String roomType = reservationManager.getRoomType(reservationNumber);
			Date stayingDate = reservationManager.getStayingDate(reservationNumber);

			//Assign room
			RoomManager roomManager = getRoomManager();
			String roomNumber = roomManager.assignCustomer(stayingDate, roomType);

			//Consume reservation if successfully assigned
			reservationManager.consumeReservation(reservationNumber);

			//Create payment
			PaymentManager paymentManager = getPaymentManager();
			paymentManager.createPayment(stayingDate, roomNumber, roomType);

			return roomNumber;
		}
		catch (ReservationException e) {
			AppException exception = new AppException("Failed to check-in", e);
			exception.getDetailMessages().add(e.getMessage());
			exception.getDetailMessages().addAll(e.getDetailMessages());
			throw exception;

		}
		catch (RoomException e) {
			AppException exception = new AppException("Failed to check-in", e);
			exception.getDetailMessages().add(e.getMessage());
			exception.getDetailMessages().addAll(e.getDetailMessages());
			throw exception;
		}
		catch (PaymentException e) {
			AppException exception = new AppException("Failed to check-in", e);
			exception.getDetailMessages().add(e.getMessage());
			exception.getDetailMessages().addAll(e.getDetailMessages());
			throw exception;
		}
	}

	private ReservationManager getReservationManager() {
		return ManagerFactory.getInstance().getReservationManager();
	}

	private RoomManager getRoomManager() {
		return ManagerFactory.getInstance().getRoomManager();
	}

	private PaymentManager getPaymentManager() {
		return ManagerFactory.getInstance().getPaymentManager();
	}
}
