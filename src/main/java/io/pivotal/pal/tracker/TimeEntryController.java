package io.pivotal.pal.tracker;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

	private final TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

	public TimeEntryController(TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry) {
		super();
		this.timeEntryRepository = timeEntryRepository;
        this.timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        this.actionCounter = meterRegistry.counter("timeEntry.actionCounter");
	}

	@PostMapping
	public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
		TimeEntry created = this.timeEntryRepository.create(timeEntryToCreate);
		this.actionCounter.increment();
        this.timeEntrySummary.record(this.timeEntryRepository.list().size());
        return new ResponseEntity<TimeEntry>(created, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TimeEntry> read(@PathVariable("id") long timeEntryId) {
		final TimeEntry entry = this.timeEntryRepository.find(timeEntryId);
		if (entry != null) {
			this.actionCounter.increment();
		}
		return new ResponseEntity<TimeEntry>(entry, entry != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@GetMapping
	public ResponseEntity<List<TimeEntry>> list() {
		this.actionCounter.increment();
		return new ResponseEntity<List<TimeEntry>>(this.timeEntryRepository.list(), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TimeEntry> update(@PathVariable("id") long timeEntryId, @RequestBody TimeEntry timeEntryToUpdate) {
		TimeEntry updated = this.timeEntryRepository.update(timeEntryId, timeEntryToUpdate);
		if (updated != null) {
			this.actionCounter.increment();
		}
		return new ResponseEntity<TimeEntry>(updated, updated != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") long timeEntryId) {
		this.timeEntryRepository.delete(timeEntryId);
		this.actionCounter.increment();
		this.timeEntrySummary.record(this.timeEntryRepository.list().size());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
