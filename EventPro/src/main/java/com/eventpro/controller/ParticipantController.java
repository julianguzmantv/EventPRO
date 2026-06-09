/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventpro.controller;

import com.eventpro.model.Participant;
import com.eventpro.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    @Autowired
    private ParticipantRepository participantRepository;

    @GetMapping
    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participant> getParticipantById(@PathVariable Long id) {
        return participantRepository.findById(id)
                .map(participant -> ResponseEntity.ok().body(participant))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Participant createParticipant(@RequestBody Participant participant) {
        return participantRepository.save(participant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Participant> updateParticipant(@PathVariable Long id, @RequestBody Participant participantDetails) {
        return participantRepository.findById(id)
                .map(participant -> {
                    participant.setName(participantDetails.getName());
                    participant.setEmail(participantDetails.getEmail());
                    participant.setEventId(participantDetails.getEventId());
                    Participant updatedParticipant = participantRepository.save(participant);
                    return ResponseEntity.ok().body(updatedParticipant);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParticipant(@PathVariable Long id) {
        return participantRepository.findById(id)
                .map(participant -> {
                    participantRepository.delete(participant);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
