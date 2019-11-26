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

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

	private TimeEntryRepository timeEntryRepository;

	public TimeEntryController(TimeEntryRepository timeEntryRepository) {
		super();
		this.timeEntryRepository = timeEntryRepository;
	}

	@PostMapping
	public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
		return new ResponseEntity<TimeEntry>(this.timeEntryRepository.create(timeEntryToCreate), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TimeEntry> read(@PathVariable("id") long timeEntryId) {
		TimeEntry entry = this.timeEntryRepository.find(timeEntryId);
		return new ResponseEntity<TimeEntry>(entry, entry != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@GetMapping
	public ResponseEntity<List<TimeEntry>> list() {
		return new ResponseEntity<List<TimeEntry>>(this.timeEntryRepository.list(), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TimeEntry> update(@PathVariable("id") long timeEntryId, @RequestBody TimeEntry timeEntryToUpdate) {
		TimeEntry updated = this.timeEntryRepository.update(timeEntryId, timeEntryToUpdate);
		return new ResponseEntity<TimeEntry>(updated, updated != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") long timeEntryId) {
		this.timeEntryRepository.delete(timeEntryId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
