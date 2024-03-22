package com.app.cires_tech.Service.Interfaces;

import org.springframework.data.domain.Page;

public interface G_Type<Request, Response, Identifier> {
    Response save(Request request);

    Response update(Request request, Identifier identifier);

    Page<Response> findAll();

    Response find(Identifier identifier);
}
