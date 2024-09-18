package io.github.bluething.playground.handson.uniqueidgenerator.infrastructure.rest;

import io.github.bluething.playground.handson.uniqueidgenerator.domain.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class UniqueId {

    private final IdGenerator idGenerator;

    @GetMapping("/unique-id")
    ResponseEntity<?> generateUniqueId() {
        return ResponseEntity.ok().body(idGenerator.nextId());
    }
}
