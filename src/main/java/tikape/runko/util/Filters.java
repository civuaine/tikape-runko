package tikape.runko.util;

import spark.*;

public class Filters {
    
    
    public static Filter addTrailingSlashes = (Request request, Response response) -> {
        
        //Jos menet osoitteeseen esim. reseptit.fi niin tämä lisää osoiteen perään "/", eli osoitteesta tulee nyt reseptit.fi/
        
        if(!request.pathInfo().endsWith("/")){
            response.redirect(request.pathInfo() + "/");
        }
    };
    
}
