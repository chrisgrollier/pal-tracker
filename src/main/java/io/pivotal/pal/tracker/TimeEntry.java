package io.pivotal.pal.tracker;

import java.time.LocalDate;

public class TimeEntry {
	private long id;
	private long projectId;
	private long userId;
	private LocalDate date;
	private int hours;

	public TimeEntry(long projectId, long userId, LocalDate date, int hours) {
		super();
		this.projectId = projectId;
		this.userId = userId;
		this.date = date;
		this.hours = hours;
	}

	public TimeEntry(long id, long projectId, long userId, LocalDate date, int hours) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.userId = userId;
		this.date = date;
		this.hours = hours;
	}

	public TimeEntry() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProjectId() {
		return projectId;
	}

	public long getUserId() {
		return userId;
	}

	public LocalDate getDate() {
		return date;
	}

	public int getHours() {
		return hours;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeEntry other = (TimeEntry) obj;
		if (id != other.id)
			return false;
		if (id == 0 || other.id == 0)
			return false;
		return true;
	}
}
