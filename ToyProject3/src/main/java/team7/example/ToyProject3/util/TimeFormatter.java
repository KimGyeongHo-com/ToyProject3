package team7.example.ToyProject3.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatter {
	public static String formatTime (LocalDateTime targetDateTime) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		Duration duration = Duration.between(targetDateTime, currentDateTime);
		long minutesAgo = duration.toMinutes();

		if (minutesAgo == 0) {
			return "방금 전";
		}

		if (minutesAgo < 60) {
			return minutesAgo + "분전";
		}

		long hoursAgo = minutesAgo / 60;
		if (hoursAgo < 24) {
			return hoursAgo + "시간전";
		}

		return targetDateTime.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일"));
	}
}
