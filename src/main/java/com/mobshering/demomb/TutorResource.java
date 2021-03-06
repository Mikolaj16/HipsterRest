package com.mobshering.demomb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TutorResource {

    private final Logger log = LoggerFactory.getLogger(TutorResource.class);

    private static final String ENTITY_NAME = "tutor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TutorRepository tutorRepository;

    public TutorResource(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    /**
     * {@code POST  /tutors} : Create a new tutor.
     *
     * @param tutor the tutor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tutor, or with status {@code 400 (Bad Request)} if the tutor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tutors")
    public ResponseEntity<Tutor> createTutor(@RequestBody Tutor tutor) throws URISyntaxException {
        log.debug("REST request to save Tutor : {}", tutor);
        if (tutor.getId() != null) {
            throw new RuntimeException("A new tutor cannot already have an ID"+ENTITY_NAME+"idexists");
        }
        Tutor result = tutorRepository.save(tutor);
        return ResponseEntity.created(new URI("/api/tutors/" + result.getId()))
            .header("Response", "Zapisywanie tutora")
            .body(result);
    }

    /**
     * {@code PUT  /tutors} : Updates an existing tutor.
     *
     * @param tutor the tutor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tutor,
     * or with status {@code 400 (Bad Request)} if the tutor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tutor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tutors")
    public ResponseEntity<Tutor> updateTutor(@RequestBody Tutor tutor) throws URISyntaxException {
        log.debug("REST request to update Tutor : {}", tutor);
        if (tutor.getId() == null) {
            throw new RuntimeException("Invalid id"+ENTITY_NAME+ "idnull");
        }
        Tutor result = tutorRepository.save(tutor);
        return ResponseEntity.ok()
            .header("Response", "Zapisywanie studenta")
            .body(result);
    }

    /**
     * {@code GET  /tutors} : get all the tutors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tutors in body.
     */
    @GetMapping("/tutors")
    public List<Tutor> getAllTutors() {
        log.debug("REST request to get all Tutors");
        return tutorRepository.findAll();
    }

    /**
     * {@code GET  /tutors/:id} : get the "id" tutor.
     *
     * @param id the id of the tutor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tutor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tutors/{id}")
    public ResponseEntity<Tutor> getTutor(@PathVariable Long id) {
        log.debug("REST request to get Tutor : {}", id);
        Optional<Tutor> tutor = tutorRepository.findById(id);
        if (tutor.isPresent()) {
            return ResponseEntity.ok().body(tutor.get());
        } else {
            throw new RuntimeException("Nie ma tutora o takim ID");
        }
    }

    /**
     * {@code DELETE  /tutors/:id} : delete the "id" tutor.
     *
     * @param id the id of the tutor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tutors/{id}")
    public ResponseEntity<Void> deleteTutor(@PathVariable Long id) {
        log.debug("REST request to delete Tutor : {}", id);
        tutorRepository.deleteById(id);
        return ResponseEntity.noContent().header("Usuwanie obiektu").build();
    }
}
