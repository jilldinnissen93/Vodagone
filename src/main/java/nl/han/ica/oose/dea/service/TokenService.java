package nl.han.ica.oose.dea.service;

import java.util.Random;

public class TokenService {
    public static String generateToken(){
        Random r = new Random();
        String tokenDeel1 = String.format("%04d", r.nextInt(1001));
        String tokenDeel2 = String.format("%04d", r.nextInt(1001));
        String tokenDeel3 = String.format("%04d", r.nextInt(1001));
        return tokenDeel1 + "-" + tokenDeel2 + "-" + tokenDeel3;
    }
}
