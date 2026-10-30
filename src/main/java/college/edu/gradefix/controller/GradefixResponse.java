package college.edu.gradefix.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

final class GradefixResponse {
    private GradefixResponse() {
    }

    static ResponseEntity<String> reply(String message) {
        if (message.equals("The grade claim with that id does not exist") || message.equals("The lecturer with that id does not exist") || message.equals("The staff room with that roomNumber does not exist") || message.equals("That course does not exist") || message.startsWith("The grade correction with that id")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.equals("Lecturer already has a claim to investigate on that date")
                || message.equals("The lecturer already has that course")
                || message.equals("That staff room is taken by another lecturer")
                || message.equals("That grade claim has grade corrections, delete them first")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        if (message.startsWith("Grade claim date")
                || message.startsWith("Grade corrections are only")
                || message.startsWith("Cannot change status")) {
            return ResponseEntity.badRequest().body(message);
        }
        return ResponseEntity.ok(message);
    }
}
