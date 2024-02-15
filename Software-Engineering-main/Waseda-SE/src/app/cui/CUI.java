/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package app.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import util.DateUtil;
import app.AppException;
import app.cancelReservation.CancelReservationForm;
import app.checkin.CheckInRoomForm;
import app.checkout.CheckOutRoomForm;
import app.reservation.ReserveRoomForm;

/**
 * CUI class for Hotel Reservation Systems
 * 
 */
public class CUI {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	private BufferedReader reader;

	CUI() {
		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	private void execute() throws IOException {
		try {
			while (true) {
				int selectMenu;
				System.out.println("");
				System.out.println("Menu");
				System.out.println("1: Reservation");
				System.out.println("2: Check-in");
				System.out.println("3: Check-out");
				System.out.println("4: Cancel reservation");
				System.out.println("9: End");
				System.out.print("> ");

				try {
					String menu = reader.readLine();
					selectMenu = Integer.parseInt(menu);
				}
				catch (NumberFormatException e) {
					selectMenu = 9;
				}

				if (selectMenu == 9) {
					break;
				}

				switch (selectMenu) {
					case 1:
						reserveRoom();
						break;
					case 2:
						checkInRoom();
						break;
					case 3:
						checkOutRoom();
						break;
					case 4:
						cancelReservationRoom();
						break;
				}
			}
			System.out.println("Ended");
		}
		catch (AppException e) {
			System.err.println("Error");
			System.err.println(e.getFormattedDetailMessages(LINE_SEPARATOR));
		}
		finally {
			reader.close();
		}
	}

	private void reserveRoom() throws IOException, AppException {
		System.out.println("Input arrival date in the form of yyyy/mm/dd");
		System.out.print("> ");

		String dateStr = reader.readLine();

		// Validate input
		Date stayingDate = DateUtil.convertToDate(dateStr);
		if (stayingDate == null) {
			System.out.println("Invalid input");
			return;
		}

		showRoomTypes();

		System.out.println("Input room type (1 - 7)");
		System.out.print("> ");

		int roomType;
		try {
			roomType = Integer.parseInt(reader.readLine());
		} catch (NumberFormatException e) {
			System.out.println("Invalid input for room type number");
			return;
		}

		if (roomType < 1 || roomType > 7) {
			System.out.println("Invalid input for room type number");
			return;
		} 

		String roomTypeStr = getRoomTypeString(roomType);
		ReserveRoomForm reserveRoomForm = new ReserveRoomForm();
		reserveRoomForm.setStayingDate(stayingDate);
		reserveRoomForm.setRoomType(roomTypeStr);
		String reservationNumber = reserveRoomForm.submitReservation();

		System.out.println("Reservation has been completed.");
		System.out.println("Arrival (staying) date is " + DateUtil.convertToString(stayingDate) + ".");
		System.out.println("Reservation number is " + reservationNumber + ".");
	}

	private void checkInRoom() throws IOException, AppException {
		System.out.println("Input reservation number");
		System.out.print("> ");

		String reservationNumber = reader.readLine();

		if (reservationNumber == null || reservationNumber.length() == 0) {
			System.out.println("Invalid reservation number");
			return;
		}

		CheckInRoomForm checkInRoomForm = new CheckInRoomForm();
		checkInRoomForm.setReservationNumber(reservationNumber);

		String roomNumber = checkInRoomForm.checkIn();
		System.out.println("Check-in has been completed.");
		System.out.println("Room number is " + roomNumber + ".");

	}

	private void checkOutRoom() throws IOException, AppException {
		System.out.println("Input room number");
		System.out.print("> ");

		String roomNumber = reader.readLine();

		if (roomNumber == null || roomNumber.length() == 0) {
			System.out.println("Invalid room number");
			return;
		}

		CheckOutRoomForm checkoutRoomForm = new CheckOutRoomForm();
		checkoutRoomForm.setRoomNumber(roomNumber);
		int fee = checkoutRoomForm.checkOut();
		System.out.println("Payment of " + fee + " yen has been made");
		System.out.println("Check-out has been completed.");
	}

	private void cancelReservationRoom() throws IOException, AppException {
		System.out.println("Input reservation number");
		System.out.print("> ");

		String reservationNumber = reader.readLine();

		if (reservationNumber == null || reservationNumber.length() == 0) {
			System.out.println("Invalid reservation number");
			return;
		}

		CancelReservationForm cancelReservationForm = new CancelReservationForm();
		cancelReservationForm.setReservationNumber(reservationNumber);
		cancelReservationForm.cancel();

		System.out.println("Cancellation has been completed.");
	}

	private void showRoomTypes() {
		System.out.println("Room Types");
		System.out.println("1: Single Room (JPY 8000 / night)");
		System.out.println("2: Twin Room (JPY 12000 / night)");
		System.out.println("3: Triple Room (JPY 18000 / night)");
		System.out.println("4: King Room (JPY 12000 / night)");
		System.out.println("5: Presidential Suite (JPY 30000 / night)");
		System.out.println("6: Japanese Style Room (JPY 10000 / night)");
		System.out.println("7: Disabled Room (JPY 10000 / night)");
	}

	private String getRoomTypeString(int roomTypeNumber) {
    switch (roomTypeNumber) {
        case 1:
            return "SingleRoom";
        case 2:
            return "TwinRoom";
        case 3:
            return "TripleRoom";
        case 4:
            return "KingRoom";
        case 5:
            return "PresidentialSuite";
        case 6:
            return "JapaneseRoom";
        case 7:
            return "DisabledRoom";
        default:
            return "Error";
    }
}

	public static void main(String[] args) throws Exception {
		CUI cui = new CUI();
		cui.execute();
	}
}
