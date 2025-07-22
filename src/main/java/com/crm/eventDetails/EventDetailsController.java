package com.crm.eventDetails;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.crm.Exception.Error;
import com.crm.fileHandler.FilesManager;
import com.crm.user.UserServiceException;

@RestController
@RequestMapping("/api/event")
@CrossOrigin(origins = { ("http://localhost:5173"), ("http://localhost:3000"), ("http://localhost:3001"),
		("http://localhost:5174"), ("http://139.84.136.208 ") })
public class EventDetailsController {

//	private String serverDocsUrl = "C:\\CRM\\MediaData\\";
	private String serverDocsUrl = "/root/mediadata/Docs/";

	@Autowired
	private FilesManager filesManager;

	@Autowired
	public EventDetailsRepository detailsRepository;

	@Autowired
	public EventDetailsService eventDetailsService;

	@PostMapping("/addEventDetails/{crManagerId}")
	public ResponseEntity<?> addEventDetails(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable long crManagerId,
			@RequestParam(value = "statusReport", required = false) MultipartFile statusReport,
			@RequestParam(value = "architectsLetter", required = false) MultipartFile architectsLetter,
			@RequestParam(value = "invoice", required = false) MultipartFile invoice,
			@RequestParam(value = "receipt", required = false) MultipartFile receipt,
			@RequestParam String eventDetails) {
		try {
			return eventDetailsService.addEventDetails(token, crManagerId, statusReport, architectsLetter, invoice,
					receipt, eventDetails);
		} catch (UserServiceException e) {
			return ResponseEntity.status(e.getStatusCode()).body(
					new Error(e.getStatusCode(), e.getMessage(), "Unable to add data", System.currentTimeMillis()));
		}
	}

	@GetMapping("/file")
	public ResponseEntity<?> getFile(@RequestParam("fileName") String fileName, @RequestParam("type") String type)
			throws IOException {
		try {
			String path;
			if ("statusReport".equalsIgnoreCase(type)) {
				path = serverDocsUrl + fileName;
			} else if ("architectsLetter".equalsIgnoreCase(type)) {
				path = serverDocsUrl + fileName;
			} else if ("invoice".equalsIgnoreCase(type)) {
				path = serverDocsUrl + fileName;
			} else if ("receipt".equalsIgnoreCase(type)) {
				path = serverDocsUrl + fileName;
			} else {
				
				return ResponseEntity.badRequest().body("Invalid file type");
			}
			Resource file = filesManager.load(path);
			if (file == null || !file.exists()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
			}
			String fname = file.getFilename();
			String mimeType = Files.probeContentType(Paths.get(path));
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fname + "\"")
					.contentType(MediaType.parseMediaType(mimeType)).body(file);

		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error reading the file: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}
	}

	@PutMapping("/updateEvent/{eventId}/{crManagerId}")
	public ResponseEntity<?> updateEventDetails(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable long eventId, @PathVariable long crManagerId,
			@RequestParam(value = "statusReport", required = false) MultipartFile statusReport,
			@RequestParam(value = "architectsLetter", required = false) MultipartFile architectsLetter,
			@RequestParam(value = "invoice", required = false) MultipartFile invoice,
			@RequestParam(value = "receipt", required = false) MultipartFile receipt,
			@RequestParam String eventDetails) {
		try {
			return eventDetailsService.updateEventDetails(token, eventId, crManagerId, statusReport, architectsLetter,
					invoice, receipt, eventDetails);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}
	}

	@GetMapping("/getEventDetails/{eventId}/{crManagerId}")
	public ResponseEntity<?> getEventDetailsByIdAndCRManagerId(@PathVariable long eventId,
			@PathVariable long crManagerId) {
		try {
			return eventDetailsService.getEventDetailsByIdAndCRManagerId(eventId, crManagerId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}
	}

	@GetMapping("/getsingleeventdetails/{eventId}/{leadId}")
	public ResponseEntity<?> getEventDetailsByIdAndLeadId(@PathVariable long eventId, @PathVariable long leadId) {
		try {
			return eventDetailsService.getEventDetailsByIdAndLeadId(eventId, leadId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}
	}

	@GetMapping("/getEventDetails/{crManagerId}")
	public ResponseEntity<?> getEventDetailsCRManagerId(@PathVariable long crManagerId) {
		try {
			return eventDetailsService.getEventDetailsByCRManagerId(crManagerId);
		} catch (UserServiceException e) {
			return ResponseEntity.status(e.getStatusCode()).body(
					new Error(e.getStatusCode(), e.getMessage(), "Unable to find data", System.currentTimeMillis()));
		}
	}

	@GetMapping("/getalleventdetails/{leadId}")
	public ResponseEntity<?> getEventDetailsLeadId(
			@RequestHeader(value = "Authorization", required = true) String token, @PathVariable long leadId) {
		try {
			return eventDetailsService.getEventDetailsByLeadId(token, leadId);
		} catch (UserServiceException e) {
			return ResponseEntity.status(e.getStatusCode()).body(
					new Error(e.getStatusCode(), e.getMessage(), "Unable to find data", System.currentTimeMillis()));
		}
	}

	@GetMapping("/getEventDetails/{eventId}")
	public ResponseEntity<?> getEventDetailsEventId(@PathVariable long eventId) {
		try {
			return eventDetailsService.getEventDetailsByCRManagerId(eventId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}
	}

	@DeleteMapping("/deleteEventById/{eventId}")
	public ResponseEntity<?> deleteEventById(
			@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable long eventId) {
		try {
			return eventDetailsService.deleteDetailsById(token, eventId);
		} catch (UserServiceException e) {
			return ResponseEntity.status(e.getStatusCode()).body(new Error(e.getStatusCode(), e.getMessage(),
					"Unable to delete record data", System.currentTimeMillis()));
		}
	}

	@DeleteMapping("/deleteevents/{clientId}")
	public ResponseEntity<?> deleteAllEventByClient(@PathVariable long clientId) {
		try {
			return eventDetailsService.deleteAllEventByClient(clientId);
		} catch (UserServiceException e) {
			return ResponseEntity.status(e.getStatusCode()).body(new Error(e.getStatusCode(), e.getMessage(),
					"Unable to delete all record data", System.currentTimeMillis()));
		}
	}

}
