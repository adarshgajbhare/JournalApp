package com.jounral.jounralapp.controller;

import com.jounral.jounralapp.model.JournalEntry;
import com.jounral.jounralapp.model.User;
import com.jounral.jounralapp.service.JournalEntryService;
import com.jounral.jounralapp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    JournalEntryService service;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllEntries(JournalEntry entry) {
        return new ResponseEntity<>(service.getAllJournalEntries(entry), HttpStatus.OK);
    }
    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @PostMapping("/save/{userName}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry, @PathVariable String userName) {
        service.saveEntry(journalEntry, userName);
        return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{userName}/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable String id, @PathVariable String userName) {
        if (!ObjectId.isValid(id)) {
            return new ResponseEntity<>("Invalid ID format", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.deleteEntry(new ObjectId(id), userName), HttpStatus.OK);
    }

    @PutMapping("/update/{userName}/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable String userName, @PathVariable String id, @RequestBody JournalEntry journalEntry) {

        if (!ObjectId.isValid(id)) {
            return new ResponseEntity<>("Invalid ID format", HttpStatus.BAD_REQUEST);
        }
        ObjectId objectId = new ObjectId(id);
        JournalEntry oldEntry = service.findById(objectId).orElse(null);
        if (oldEntry != null) {
            oldEntry.setTitle(journalEntry.getTitle());
            oldEntry.setContent(journalEntry.getContent());
            oldEntry.setDate(LocalDate.now());

            service.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
