package com.redhat.fuse.boosters.cb;

import org.springframework.stereotype.Service;
import org.apache.camel.jsonpath.JsonPath;

@Service("greetingsService")
public class GreetingsServiceImpl implements GreetingsService {

    private static final String THE_GREETINGS = "Hello, ";

    @Override
    public Greetings getGreetings( @JsonPath("$.name") String name ) {
        return new Greetings( THE_GREETINGS + name );
    }

}