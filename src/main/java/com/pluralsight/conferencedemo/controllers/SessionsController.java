package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionsController {
    @Autowired
    private SessionRepository sessionRepository;
    
    @GetMapping
    public List<Session> list(){
        return sessionRepository.findAll();
    }
    @GetMapping
    @RequestMapping("{id}")
    public Optional<Session> get(PathVariable Long, @PathVariable String id){
        return sessionRepository.findById(java.lang.Long.valueOf(id));
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Session create(@RequestBody final Session session){
        return sessionRepository.saveAndFlush(session);
    }
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        sessionRepository.deleteById(id);
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
    public ResponseEntity<Session> update(@PathVariable("id") long id, @RequestBody Session session) {
        Optional<Session> sessionData = sessionRepository.findById(id);
        if (sessionData.isPresent()) {
            Session _session = sessionData.get();
            _session.setSession_name(session.getSession_name());
            _session.setSession_length(session.getSession_length());
            _session.setSession_description(session.getSession_description());
            return new ResponseEntity<>(sessionRepository.save(_session), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
