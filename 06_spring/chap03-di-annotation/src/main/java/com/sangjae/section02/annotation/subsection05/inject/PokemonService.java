package com.sangjae.section02.annotation.subsection05.inject;

import com.sangjae.section02.annotation.common.Pokemon;
import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.springframework.stereotype.Service;

@Service("pokemonServiceInject")
public class PokemonService {

    // @Autowired 와 유사하게 타입을 통해 의존성 주입 , @Named를 통해 빈이름 을 명시
    // 필드 , 세터 , 생성자 주입 모두 가능
    @Inject
    @Named("pikachu")
    private Pokemon pokemon;

    public void pokemonAttack() {
        pokemon.attack();
    }

}
