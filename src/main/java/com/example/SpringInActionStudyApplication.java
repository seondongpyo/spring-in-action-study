package com.example;

import com.example.domain.Ingredient;
import com.example.repository.IngredientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class SpringInActionStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringInActionStudyApplication.class, args);
    }

    @Bean
    @Profile("dev") // 개발 환경에서만 실행되도록 설정
//    @Profile("!prod") // 운영 환경 이외의 환경에서 실행되도록 설정
    public CommandLineRunner dataLoader(IngredientRepository ingredientRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
                ingredientRepository.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
                ingredientRepository.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
                ingredientRepository.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
                ingredientRepository.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
                ingredientRepository.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
                ingredientRepository.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
                ingredientRepository.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
                ingredientRepository.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
                ingredientRepository.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
            }
        };
    }
}
