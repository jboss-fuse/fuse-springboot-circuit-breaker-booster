package org.jboss.fuse.boosters.cb;

import org.springframework.stereotype.Service;

@Service("greetingsService")
public class GreetingsServiceImpl implements GreetingsService {

    private static final String THE_GREETINGS = "Hello, ";

    @Override
    public Greetings getGreetings( String name ) {
        return new Greetings( THE_GREETINGS + name );
    }

}