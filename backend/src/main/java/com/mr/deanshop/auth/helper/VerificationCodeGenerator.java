package com.mr.deanshop.auth.helper;

import java.util.Random;

public class VerificationCodeGenerator {
    public static String generateCode(){
        int code = 100000 + new Random().nextInt(900000);
        return String.valueOf(code);
    }
}
