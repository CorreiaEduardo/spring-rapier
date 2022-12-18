package com.ducorreia.springrapier.web.controller;

import com.ducorreia.springrapier.core.data.DataEntity;
import com.ducorreia.springrapier.core.pagination.Page;
import com.ducorreia.springrapier.core.pagination.PageRequest;
import com.ducorreia.springrapier.web.annotation.HideMethod;
import com.ducorreia.springrapier.web.exception.MethodNotAllowedException;
import com.ducorreia.springrapier.web.logging.ApiLogger;
import com.ducorreia.springrapier.web.service.CrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.ducorreia.springrapier.web.util.CrudMethod.*;

public abstract class CrudController<E extends DataEntity> {
    private final ApiLogger logger;
    private MethodExposure methodExposure = new MethodExposure();

    public CrudController() {
        super();
        HideMethod hideMethod = this.getClass().getAnnotation(HideMethod.class);

        if (hideMethod != null) {
            methodExposure = new MethodExposure(hideMethod.value());
        }

        this.logger = new ApiLogger(this.getClass());
    }

    /**
     * @param parameters Represents query string parameters, and are treated as follows:
     *                   ?name=LK:XXX&category=IN:1,2,3&createdAt=GT:12-02-2022
     */
    @GetMapping(produces = {"application/json"})
    public ResponseEntity<Page<E>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam MultiValueMap<String, String> parameters
    ) {
        if (this.methodExposure.exposes(GET_ONE)) {
            return ResponseEntity.ok(this.getService().findAll(parameters, PageRequest.of(page, size)));
        } else {
            throw new MethodNotAllowedException();
        }
    }

    @GetMapping(
            value = "/{hash}",
            produces = {"application/json"}
    )
    public ResponseEntity<E> getOne(@PathVariable String hash) {
        if (this.methodExposure.exposes(GET_ONE)) {
            final E result = this.getService()
                    .findByHash(hash)
                    .orElseThrow(RuntimeException::new);

            return ResponseEntity.ok(result);
        } else {
            throw new MethodNotAllowedException();
        }
    }

    @PostMapping(
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public ResponseEntity<E> post(@Valid @RequestBody E body) {
        if (this.methodExposure.exposes(POST)) {
            final E result = this.getService().insert(body);

            return ResponseEntity.ok(result);
        } else {
            throw new MethodNotAllowedException();
        }
    }

    @PutMapping(
            value = "/{hash}",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public ResponseEntity<E> put(@PathVariable String hash, @Valid @RequestBody E body) {
        if (this.methodExposure.exposes(PUT)) {
            final E result = this.getService().update(body);

            return ResponseEntity.ok(result);
        } else {
            throw new MethodNotAllowedException();
        }
    }

    @DeleteMapping(value = "/{hash}")
    public ResponseEntity<E> delete(@PathVariable String hash) {
        if (this.methodExposure.exposes(DELETE)) {
            this.getService().deleteByHash(hash);

            return ResponseEntity.ok().build();
        } else {
            throw new MethodNotAllowedException();
        }
    }

    protected abstract CrudService<E> getService();

    protected ApiLogger getLogger() {
        return logger;
    }
}
