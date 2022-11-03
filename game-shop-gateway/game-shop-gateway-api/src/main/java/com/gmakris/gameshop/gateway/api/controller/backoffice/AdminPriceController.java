package com.gmakris.gameshop.gateway.api.controller.backoffice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.gmakris.gameshop.gateway.api.controller.AuthenticatedController;
import com.gmakris.gameshop.gateway.api.controller.GenericController;
import com.gmakris.gameshop.gateway.api.util.ParseUtil;
import com.gmakris.gameshop.gateway.mapper.PriceMapper;
import com.gmakris.gameshop.gateway.service.crud.auth.UserService;
import com.gmakris.gameshop.gateway.service.crud.backoffice.PublicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AdminPriceController extends AuthenticatedController implements GenericController {

    private final PriceMapper priceMapper;
    private final PublicationService publicationService;

    public AdminPriceController(
        final UserService userService,
        final PriceMapper priceMapper,
        final PublicationService publicationService
    ) {
        super(userService);
        this.priceMapper = priceMapper;
        this.publicationService = publicationService;
    }

    private Mono<ServerResponse> publishPrice(final ServerRequest serverRequest) {
        return getUserId(serverRequest)
            .flatMap(userId -> ParseUtil.toUUID(serverRequest.pathVariable("unpublishedPriceId"))
                .flatMap(unpublishedPriceId -> publicationService.publish(unpublishedPriceId, userId))
                .doOnError(throwable -> log.error("Could not publish unpublished-price '{}' by user '{}'!",
                    serverRequest.pathVariable("unpublishedPriceId"),
                    userId,
                    throwable)))
            .map(priceMapper::to)
            .flatMap(price -> ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .bodyValue(price))
            .onErrorResume(throwable -> ServerResponse
                .status(INTERNAL_SERVER_ERROR)
                .contentType(APPLICATION_JSON)
                .bodyValue(throwable.getMessage()));
    }

    @Override
    public RouterFunction<ServerResponse> routes() {
        return route(POST("/admin/price/{unpublishedPriceId}"), this::publishPrice);
    }
}
