package com.sangjae.section02.annotation.subsection04.resource;

import com.sangjae.section02.annotation.common.Pokemon;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("pokemonServicePrimary")
public class PokemonService {

    // @Resource : Java 진영의 DI Annotation
    // @Autowired 와 달리 name 속성 값으로 의존성 주입을 할 수 있고 , 필드주입 , 세터주입이 가능하다
    // List<Pokemon> 타입이라면 name 속성을 생략시 3개의 bean이 모두 담긴다
    // (이름 -> 타입 순으로 처리)
    @Resource(name = "charmander")
    private Pokemon pokemon;

    public PokemonService(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public void pokemonAttack() {
        pokemon.attack();
    }

}
