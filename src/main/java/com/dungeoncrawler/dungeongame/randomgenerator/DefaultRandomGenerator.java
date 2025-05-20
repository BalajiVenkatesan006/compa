package com.dungeoncrawler.dungeongame.randomgenerator;

import com.dungeoncrawler.dungeongame.iservice.RandomGenerator;
import java.util.Random;

public class DefaultRandomGenerator implements RandomGenerator {
    private final Random random = new Random();
    
    @Override
    public double nextDouble() {
        return random.nextDouble();
    }
}
