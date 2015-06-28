package com.example.especials.buscaminas;

import java.util.StringTokenizer;

/**
 * Created by Especials on 26/06/2015.
 */
public class PartidaParser {
    private PartidaBuilder builder;

    public PartidaParser(PartidaBuilder builder) {
        this.builder = builder;
    }

    public void parse(String s) {
        System.out.println(s);
        System.out.println("Tokens:");
        String[] tokens = split(s, ";&");
        for (int i = 0; i < tokens.length; i+=2){
            System.out.println(tokens[i] + " : " + tokens[i+1]);
        }
    }
    private String[] split(String string, String delimiters){

        StringTokenizer st = new StringTokenizer(string,delimiters);
        String[] result = new String[st.countTokens()];
        for (int i = 0;st.hasMoreTokens();i++){
            result[i] = st.nextToken();
        }
        return result;
    }
}
