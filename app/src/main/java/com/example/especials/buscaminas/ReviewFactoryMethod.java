package com.example.especials.buscaminas;

/**
 * Created by Gary on 28/06/2015.
 */
public class ReviewFactoryMethod {

    public ReviewOption getInstance(String type, String toParse){
        if(type.equals("nc")){
            return new NumeroCasillasOpcion(toParse);
        }else if(type.equals("ncr")) {
            return new NumeroCasillasRestanteOpcion(toParse);
        }else if(type.equals("nbr")){
            return new NumeroBombasRestanteOpcion(toParse);
        }else if(type.equals("t")){
            return new TiempoOpcion(toParse);
        }else if(type.equals("allLog")){
            return new AllLogOpcion(toParse);
        }else if(type.equals("casilla")){
            return new CasillaOpcion(toParse);
        }
        return null;
    }
}
