package com.jounral.jounralapp.service;

import com.jounral.jounralapp.model.JournalEntry;
import com.jounral.jounralapp.model.User;
import com.jounral.jounralapp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo repo;
    @Autowired
    private UserService userService;

    public List<JournalEntry> getAllJournalEntries(JournalEntry entry) {
        return repo.findAll();
    }

    public void saveEntry(JournalEntry entry, String userName) {
        User user = userService.findByUserName(userName);
        entry.setDate(LocalDate.now());
        JournalEntry saved = repo.save(entry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);

    }

    public void saveEntry(JournalEntry entry) {
        repo.save(entry);
    }

    public String deleteEntry(ObjectId id, String userName) {
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
        repo.deleteById(id);
        return "Deleted Successfully!!!";
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return repo.findById(id);
    }

}
