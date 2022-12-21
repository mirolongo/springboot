package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/sessions")
public class SpeakersController {
    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Speaker> list(){
        return speakerRepository.findAll();
    }
    @GetMapping
    @RequestMapping("{id}")
    public Optional<Speaker> get(PathVariable Long, @PathVariable String id){
        return speakerRepository.findById(java.lang.Long.valueOf(id));
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Speaker create(@RequestBody final Speaker speaker){
        return speakerRepository.saveAndFlush(speaker);
    }
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        speakerRepository.deleteById(id);
    }
    /*
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Session update(@PathVariable Long id, RequestBody Session session){
        Session existingSession = sessionRepository.findById(id);
        BeanUtils.copyProperties(session, existingSession, "session_id");
        return sessionRepository.saveAndFlush(existingSession);
    }
     */
    @PutMapping("/api/v1/sessions")
    public ResponseEntity<Speaker> update(@PathVariable("id") long id, @RequestBody Speaker speaker) {
        Optional<Speaker> speakerData = speakerRepository.findById(id);
        if (speakerData.isPresent()) {
            Speaker _speaker = speakerData.get();
            _speaker.setCompany(speaker.getCompany());
            _speaker.setFirst_name(speaker.getFirst_name());
            _speaker.setLast_name(speaker.getLast_name());
            _speaker.setSpeaker_bio(speaker.getSpeaker_bio());
            _speaker.setTitle(speaker.getTitle());
            _speaker.setSpeaker_photo(speaker.getSpeaker_photo());
            return new ResponseEntity<>(speakerRepository.save(_speaker), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
