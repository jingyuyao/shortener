package com.jingyuyao.cms.core;

import com.jingyuyao.cms.api.Greeting;

public class GreetingService {
    public Greeting getGreeting() {
        Greeting greeting = new Greeting();
        greeting.setText("Yo");
        greeting.setVisits(0);
        return greeting;
    }
}
