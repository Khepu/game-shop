package com.gmakris.gameshop.gateway.repository;

import com.gmakris.gameshop.gateway.entity.model.Game;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface GameRepository extends GenericRepository<Game> {

    @Query("""
    select id, name, created_at
    from games
    order by created_at
    offset :offset
    limit :limit
    """)
    Flux<Game> findAllPaginated(
        @Param("offset") int offset,
        @Param("limit") int limit);

    @Query("""
    select id, name, created_at
    from games
    where websearch_to_tsquery('english', :query) @@ name_embeddings
    limit :limit
    """)
    Flux<Game> findPricedGamesByQuery(
        @Param("query") String query,
        @Param("limit") int limit);
}
