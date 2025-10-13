package com.sangjae.section02.annotation.subsection04.resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(
                        "com.sangjae.section02"
        );

        PokemonService pokemonService
                = applicationContext.getBean("pokemonServiceResource", PokemonService.class);
        pokemonService.pokemonAttack();
    }
}
