/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package app.cancelReservation;

import java.util.Date;

import app.AppException;
import app.ManagerFactory;
import domain.reservation.ReservationManager;
import domain.reservation.ReservationException;
import domain.room.RoomManager;
import domain.room.RoomException;

/**
 * Control class for Cancel Reservation
 * 
 */
public class CancelReservationControl {

	public void cancelReservation(String reservationNumber) throws AppException {
        //So far permitting only one night so that change amount of availableQty is always 1
		int availableQtyOfChange = 1;
		try {
            //Cancel reservation
			ReservationManager reservationManager = getReservationManager();
            Date stayingDate = reservationManager.getStayingDate(reservationNumber);
            String roomType = reservationManager.getRoomType(reservationNumber);
			reservationManager.removeReservation(reservationNumber);

			//Update number of available rooms
			RoomManager roomManager = getRoomManager();
			roomManager.updateRoomAvailableQty(stayingDate, roomType, availableQtyOfChange);
		}
		catch (RoomException e) {
			AppException exception = new AppException("Failed to cancel", e);
			exception.getDetailMessages().add(e.getMessage());
			exception.getDetailMessages().addAll(e.getDetailMessages());
			throw exception;
		}
		catch (ReservationException e) {
			AppException exception = new AppException("Failed to cancel", e);
			exception.getDetailMessages().add(e.getMessage());
			exception.getDetailMessages().addAll(e.getDetailMessages());
			throw exception;
		}
	}

	private RoomManager getRoomManager() {
		return ManagerFactory.getInstance().getRoomManager();
	}

	private ReservationManager getReservationManager() {
		return ManagerFactory.getInstance().getReservationManager();
	}
}
