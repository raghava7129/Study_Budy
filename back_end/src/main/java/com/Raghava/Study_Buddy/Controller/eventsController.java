package com.Raghava.Study_Buddy.Controller;

import com.Raghava.Study_Buddy.Models.events;
import com.Raghava.Study_Buddy.Models.loginUserRequest;
import com.Raghava.Study_Buddy.Service.eventService;
import com.Raghava.Study_Buddy.Service.jwtUtils;
import com.Raghava.Study_Buddy.Service.userService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/events")

public class eventsController {

    @Autowired
    public eventsController(eventService eventService) {
        this.eventService = eventService;
    }

    private final eventService eventService;

    private jwtUtils jwtUtils = new jwtUtils();

    @PostMapping("/{event_name}")
    public ResponseEntity<events> createEvent(@RequestHeader("Authorization") String token,String event_name, @RequestBody events event) {
        if(!jwtUtils.validateJwtToken(token.split(" ")[1])){
            return ResponseEntity.status(401).body(null);
        }

        loginUserRequest loginUserRequestObj = jwtUtils.decodeJwtToken(token.split(" ")[1]);

        if(loginUserRequestObj == null) return ResponseEntity.status(401).body(null);

        if(eventService.getEvent(event_name) != null) return ResponseEntity.status(409).body(null);

        events eventResponseObj =  eventService.createEvent(event.getEvent_name(), event.getDescription(), event.getStart_time(),
                event.getEnd_time(), event.getTheme(), event.getChallenge(), event.getGoal(), event.getRecurrence());

        if(eventResponseObj == null) return ResponseEntity.status(500).body(null);
        else return ResponseEntity.status(201).body(eventResponseObj);

    }

    @GetMapping("/{event_name}")
    public ResponseEntity<events> getEvent(@RequestHeader("Authorization") String token, @PathVariable String event_name) {
        if(!jwtUtils.validateJwtToken(token.split(" ")[1])){
            return ResponseEntity.status(401).body(null);
        }

        loginUserRequest loginUserRequestObj = jwtUtils.decodeJwtToken(token.split(" ")[1]);

        if(loginUserRequestObj == null) return ResponseEntity.status(401).body(null);

        events eventResponseObj =  eventService.getEvent(event_name);

        if(eventResponseObj == null) return ResponseEntity.status(404).body(null);
        else return ResponseEntity.status(200).body(eventResponseObj);

    }

    @GetMapping("/all")
    public ResponseEntity<List<events>> getAllEvents(@RequestHeader("Authorization") String token) {
        if(!jwtUtils.validateJwtToken(token.split(" ")[1])){
            return ResponseEntity.status(401).body(null);
        }

        loginUserRequest loginUserRequestObj = jwtUtils.decodeJwtToken(token.split(" ")[1]);

        if(loginUserRequestObj == null) return ResponseEntity.status(401).body(null);

        List<events> eventResponseObj =  eventService.getAllEvents();

        if(eventResponseObj == null || eventResponseObj.isEmpty()) return ResponseEntity.status(204).body(null);
        else return ResponseEntity.status(200).body(eventResponseObj);

    }

    @PutMapping("/{event_name}")
    public ResponseEntity<Void> updateEvent(@RequestHeader("Authorization") String token, @PathVariable String event_name, @RequestBody events event) {
        if(!jwtUtils.validateJwtToken(token.split(" ")[1])){
            return ResponseEntity.status(401).body(null);
        }

        loginUserRequest loginUserRequestObj = jwtUtils.decodeJwtToken(token.split(" ")[1]);

        if(loginUserRequestObj == null) return ResponseEntity.status(401).body(null);

        events eventResponseObj =  eventService.getEvent(event_name);

        if(eventResponseObj == null) return ResponseEntity.status(404).body(null);

        String updateResponse = eventService.updateEvent(event.getEvent_name(), event.getDescription(), event.getStart_time(),
                event.getEnd_time(), event.getTheme(), event.getChallenge(), event.getGoal());

        if(updateResponse == null) return ResponseEntity.status(500).body(null);
        else return ResponseEntity.status(200).body(null);

    }

    @DeleteMapping("/{event_name}")
    public ResponseEntity<Void> deleteEvent(@RequestHeader("Authorization") String token, @PathVariable String event_name) {
        if(!jwtUtils.validateJwtToken(token.split(" ")[1])){
            return ResponseEntity.status(401).body(null);
        }

        loginUserRequest loginUserRequestObj = jwtUtils.decodeJwtToken(token.split(" ")[1]);

        if(loginUserRequestObj == null) return ResponseEntity.status(401).body(null);

        events eventResponseObj =  eventService.getEvent(event_name);

        if(eventResponseObj == null) return ResponseEntity.status(404).body(null);

       String Response =  eventService.deleteEvent(event_name);

        if(Response == null) return ResponseEntity.status(500).body(null);
        else return ResponseEntity.status(200).body(null);

    }

}
