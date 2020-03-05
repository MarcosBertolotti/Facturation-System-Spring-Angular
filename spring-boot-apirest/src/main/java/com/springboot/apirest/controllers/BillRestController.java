package com.springboot.apirest.controllers;

import com.springboot.apirest.entity.Bill;
import com.springboot.apirest.services.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/bills")
public class BillRestController {

    @Autowired
    private IBillService billService;

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<Bill> getById(@PathVariable final Integer id) {

        return ok(billService.findById(id));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Bill> deleteById(@PathVariable final Integer id) {

        return ok(billService.deleteById(id));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("")
    public ResponseEntity<Bill> create(@RequestBody Bill bill) {

        return status(CREATED).body(billService.save(bill));
    }
}
