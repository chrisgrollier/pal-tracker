package io.pivotal.pal.tracker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

	private long nextId = 1;
	private Map<Long, TimeEntry> timeEntries = new HashMap<>();

	@Override
	public TimeEntry create(TimeEntry timeEntry) {
		TimeEntry newEntry = new TimeEntry(nextId++, timeEntry.getProjectId(), timeEntry.getUserId(),
				timeEntry.getDate(), timeEntry.getHours());
		this.timeEntries.put(newEntry.getId(), newEntry);
		return newEntry;
	}

	@Override
	public TimeEntry find(long timeEntryId) {
		return this.timeEntries.get(timeEntryId);
	}

	@Override
	public List<TimeEntry> list() {
		return this.timeEntries.values().stream().collect(Collectors.toList());
	}

	@Override
	public TimeEntry update(long timeEntryId, TimeEntry timeEntry) {
		final TimeEntry result;
		if (this.timeEntries.get(timeEntryId) != null) {
			this.timeEntries.put(timeEntryId, new TimeEntry(timeEntryId, timeEntry.getProjectId(),
					timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours()));
			result = this.timeEntries.get(timeEntryId);
		} else {
			result = null;
		}
		return result;
	}

	@Override
	public void delete(long timeEntryId) {
		this.timeEntries.remove(timeEntryId);
	}

}
