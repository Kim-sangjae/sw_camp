package com.sangjae.section02.annotation.subsection03.collection;

import com.sangjae.section02.annotation.common.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("pokemonServiceCollection")
public class PokemonService {


    // 컬렉션 타입으로 의존성 주입을하면 빈으로 등록된 모든 하위 객체들 전부 주입된다
    private List<Pokemon> pokemons;

    /* 2. 생성자 주입 */
    public PokemonService(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public void pokemonAttack() {
        pokemons.forEach(Pokemon::attack);
    }

}
