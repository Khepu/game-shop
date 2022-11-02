package com.gmakris.gameshop.gateway.repository;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.Game;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface GameRepository extends GenericRepository<Game> {

    @Query("""
    with games_with_status as (select distinct on (g.id)
                                   g.id as id,
                                   g.name as name,
                                   g.created_at as created_at, 
                                   gs.status as status
                               from games g
                                   inner join game_states gs on gs.game_id = g.id
                               order by g.id, gs.created_at desc)
    select id, name, created_at
    from games_with_status
    where status == 'AVAILABLE'
    order by created_at desc
    offset :offset
    limit :limit
    """)
    Flux<Game> findAllPaginated(
        @Param("offset") int offset,
        @Param("limit") int limit);

    @Query("""
    with games_with_status as (select distinct on (g.id)
                                   g.id as id,
                                   g.name as name,
                                   g.created_at as created_at,
                                   g.name_embeddings as name_embeddings,
                                   gs.status as status
                               from games g
                                   inner join game_states gs on gs.game_id = g.id
                               order by g.id, gs.created_at desc)
    select id, name, created_at
    from games_with_status
    where websearch_to_tsquery('english', :query) @@ name_embeddings
        and status == 'AVAILABLE'
    order by created_at desc
    offset :offset
    limit :limit
    """)
    Flux<Game> findAllByQuery(
        @Param("query") String query,
        @Param("offset") int offset,
        @Param("limit") int limit);

    @Query("""
    select g.id, g.name, g.created_at
    from games g
        inner join orders o on o.game_id = g.id
    where o.user_id = :userId
    """)
    Flux<Game> findAllOwnedByUserId(@Param("userId")UUID userId);
}
