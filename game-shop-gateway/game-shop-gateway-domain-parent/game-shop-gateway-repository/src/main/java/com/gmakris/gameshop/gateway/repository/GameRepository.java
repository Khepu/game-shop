package com.gmakris.gameshop.gateway.repository;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.Game;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface GameRepository extends GenericRepository<Game> {

    @Query("""
    select *
    from games
    where id = :id
    order by updated_at desc
    limit 1
    """)
    Mono<Game> findLatestPriceById(@Param("id") UUID id);

}
