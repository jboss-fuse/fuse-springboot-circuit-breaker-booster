package org.jboss.fuse.boosters.cb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class IndexRootController {

    @RequestMapping(value = "/")
    public RedirectView handleTestRequest (Model model) {
        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);
        rv.setUrl("/monitor/monitor.html?stream=http://localhost:8080/hystrix.stream&title=greetings-service");
        return rv;
    }

}